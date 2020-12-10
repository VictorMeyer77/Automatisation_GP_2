package com.esgi.gptwo

import com.esgi.gptwo.bean.conf.Postgres
import org.apache.spark.sql.{DataFrame, SparkSession}

class PostgresManager(postgresConf: Postgres) {

  val spark: SparkSession = SparkSession
    .builder()
    .getOrCreate()


  def getJdbc(tableName: String): DataFrame ={

    spark.read
      .format("jdbc")
      .option("driver", "org.postgresql.Driver")
      .option("url", "jdbc:postgresql://%s:%s/%s".format(postgresConf.address, postgresConf.port.toString, postgresConf.database))
      .option("dbtable", "%s".format(tableName))
      .option("user", postgresConf.user)
      .option("password", postgresConf.password)
      .load()

  }


  def getArtistToTrack: DataFrame ={

    getJdbc("artist")

  }
}
