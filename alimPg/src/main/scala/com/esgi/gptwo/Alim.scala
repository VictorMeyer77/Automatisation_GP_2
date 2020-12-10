package com.esgi.gptwo

import com.esgi.gptwo.bean.conf.{Configuration}
import com.google.gson.Gson
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

class Alim(conf: Configuration) {

  val spark: SparkSession = SparkSession
    .builder()
    .appName("automatisation_load_data")
    .getOrCreate()

  val gson: Gson = new Gson

  def loadJson(path: String): DataFrame ={

    spark.read
      .format("org.apache.spark.sql.execution.datasources.v2.json.JsonDataSourceV2")
      .load(path)

  }

  def readCheckpoint(path: String): String ={

    try{

      val checkpointDf = spark.read
                        .format("org.apache.spark.sql.execution.datasources.v2.json.JsonDataSourceV2")
                        .load(path)
      checkpointDf.collect()(0)(0).toString.replace("T", " ")

    } catch {

      case e: Exception => "1970-01-01 00:00:00.000+01:10"

    }
  }

  def writeCheckpoint(path: String, df: DataFrame): Unit ={

    df.agg(max(col("date_maj")))
      .withColumnRenamed("max(date_maj)", "lastTrt")
      .write
      .format("org.apache.spark.sql.execution.datasources.v2.json.JsonDataSourceV2")
      .mode("overwrite")
      .save(path)

  }

  def formatDate(df: DataFrame, colName: String): DataFrame ={

    df.withColumn(colName, regexp_replace(col(colName), "T", " "))
      .withColumn(colName, to_timestamp(col(colName), "yyyy-MM-dd HH:mm:ss.SSS+01:00"))

  }

  def insertDf(df: DataFrame, tableName: String, checkpointValue: String): Unit ={

      df.filter(col("date_maj").gt(lit(checkpointValue)))
        .write
        .format("jdbc")
        .mode("append")
        .option("driver", "org.postgresql.Driver")
        .option("url", "jdbc:postgresql://%s:%s/%s".format(conf.postgres.address,
                                                           conf.postgres.port.toString,
                                                           conf.postgres.database))
        .option("dbtable", "%s".format(tableName))
        .option("user", conf.postgres.user)
        .option("password", conf.postgres.password)
        .save()

  }

  def run(): Unit ={

      conf.tablesToAlim.forEach(table => {

        val df: DataFrame = loadJson(conf.hangar + "/" + table)
        val dfFormatDatMaj = formatDate(df, "datetablemaj")
        val checkpoint: String = readCheckpoint(conf.checkpointPath + "/" + table + ".json")
        insertDf(dfFormatDatMaj, table, checkpoint)
        writeCheckpoint(conf.checkpointPath + "/" + table + ".json", dfFormatDatMaj)

      })
  }
}
