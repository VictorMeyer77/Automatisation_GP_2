package com.esgi.gptwo.formater

import org.apache.spark.sql.functions._
import com.esgi.gptwo.bean.common.{ArtistTracking, TrackTracking}
import com.esgi.gptwo.bean.spotify.{SimpleArtist, TrackList}
import com.google.gson.Gson
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

object SpotifyFormater {

  val spark: SparkSession = SparkSession
    .builder()
    .appName("automatisation_load_data")
    .getOrCreate()


  def tlToDf(trackList: TrackList): DataFrame ={

    import spark.implicits._
    val gson = new Gson
    spark.read.json(Seq(gson.toJson(trackList.tracks)).toDS)

  }

  def tlToOutputJson(df: DataFrame, artistId: String, countryCode: String): Dataset[TrackTracking] ={

    import spark.implicits._
    df.map(row => TrackTracking(id_source = row.getAs[String]("id"),
      id_artist = artistId,
      country_code = countryCode,
      popularity = row.getAs[Long]("popularity"),
      feat = row.getAs[Seq[SimpleArtist]]("artists").length > 1,
      duration = row.getAs[Long]("duration_ms") / 1000,
      source = "spotify",
      0))
  }


  def artistDfToJson(df: DataFrame): Dataset[ArtistTracking] ={

    import spark.implicits._

    df.map(row => ArtistTracking(id_artist = row.getAs[String]("id"),
      nb_fan = row.getAs("followers").toString.replace("[", "").replace("]", "").toLong,
      popularity = row.getAs[Long]("popularity"),
      "spotify",
      0))
  }

  def addDateMaj(df: Dataset[_]): DataFrame ={

    df.withColumn("date_maj", current_timestamp().as("date_maj"))

  }

}
