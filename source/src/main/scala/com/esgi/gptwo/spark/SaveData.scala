package com.esgi.gptwo.spark

import org.apache.spark.sql._

class SaveData(spark: SparkSession, data: String){

  def test(): Unit ={

    spark.read
      .format("org.apache.spark.sql.execution.datasources.v2.json.JsonDataSourceV2").load(data).show()

  }

}
