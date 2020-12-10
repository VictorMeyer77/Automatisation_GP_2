package com.esgi.gptwo.formater

import com.esgi.gptwo.bean.common.{ArtistTracking, TrackTracking}
import com.esgi.gptwo.bean.deezer.Contributor
import org.apache.spark.sql.functions.current_timestamp
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

object DeezerFormater {

  val spark: SparkSession = SparkSession
    .builder()
    .getOrCreate()

  def tlToOutputJson(df: DataFrame, artistId: String): Dataset[TrackTracking] ={

    import spark.implicits._
    df.map(row => TrackTracking(id_source = row.getAs[Long]("id").toString,
      id_artist = artistId,
      country_code = "WO",
      popularity = row.getAs[String]("rank").toLong,
      feat = row.getAs[Seq[Contributor]]("contributors").length > 1,
      duration = row.getAs[String]("duration").toLong,
      source = "deezer",
      0))
  }

  def artistDfToJson(df: DataFrame): Dataset[ArtistTracking] ={

    import spark.implicits._

    df.map(row => ArtistTracking(id_artist = row.getAs[String]("id"),
      nb_fan = row.getAs[Long]("nb_fan"),
      popularity = -1,
      "deezer",
      0))

  }

  def addDateMaj(df: Dataset[_]): DataFrame ={

    df.withColumn("date_maj", current_timestamp().as("date_maj"))

  }

}
