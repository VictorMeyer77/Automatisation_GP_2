package com.esgi.gptwo

import com.esgi.gptwo.api.{DeezerApi, SpotifyApi}
import com.esgi.gptwo.bean.common.{ArtistTracking, TrackTracking}
import com.esgi.gptwo.bean.conf.Configuration
import com.esgi.gptwo.bean._
import com.esgi.gptwo.formater.{DeezerFormater, SpotifyFormater}
import com.google.gson.Gson
import org.apache.spark.sql._

class Storer(idDeezer: String, idSpotify: String, hangarPath: String, conf: Configuration) {

  val spark: SparkSession = SparkSession
    .builder()
    .getOrCreate()

  val gson = new Gson

  def storeInHangar(df: Dataset[_], tablePath: String): Unit ={

    df.write
      .partitionBy("id_artist")
      .mode("append")
      .format("org.apache.spark.sql.execution.datasources.v2.json.JsonDataSourceV2")
      .save(tablePath)

  }

  def storeDeezerArtistTracking: String ={

    import spark.implicits._
    val deezerApi = new DeezerApi(idDeezer)
    val artist: deezer.Artist = deezerApi.artistsRequest
    val artistDf: DataFrame = Seq(artist).toDF()
    val artistDfToJson: Dataset[ArtistTracking] = DeezerFormater.artistDfToJson(artistDf)
    val dfWithDatMaj: DataFrame = DeezerFormater.addDateMaj(artistDfToJson)
    storeInHangar(dfWithDatMaj, hangarPath + "/artist_tracking")
    artist.tracklist

  }

  def storeDeezerTrackTracking(url: String): Unit ={

    import spark.implicits._
    val deezerApi = new DeezerApi(idDeezer)
    val data: deezer.Data = deezerApi.trackListRequest(url)
    val dfData: DataFrame = spark.read.json(Seq(gson.toJson(data.data)).toDS)
    val dfDataJson: Dataset[TrackTracking] = DeezerFormater.tlToOutputJson(dfData, idDeezer)
    val dfWithDatMaj: DataFrame = DeezerFormater.addDateMaj(dfDataJson)
    storeInHangar(dfWithDatMaj, hangarPath + "/music_tracking")

  }

  def storeSpotifyArtistTracking(): Unit ={

    import spark.implicits._
    val api: SpotifyApi = new SpotifyApi(conf.confSpotify, idSpotify)
    val artist: spotify.Artist = api.artistsRequest
    val artDf: DataFrame = spark.read.json(Seq(gson.toJson(artist)).toDS)
    val artfDfJson: Dataset[ArtistTracking] = SpotifyFormater.artistDfToJson(artDf)
    val dfWithDatMaj: DataFrame = SpotifyFormater.addDateMaj(artfDfJson)
    storeInHangar(dfWithDatMaj, hangarPath + "/artist_tracking")

  }

  def storeSpotifyTrackTracking(countryCode: String): Unit ={

    import spark.implicits._
    val api: SpotifyApi = new SpotifyApi(conf.confSpotify, idSpotify)
    val trackList: spotify.TrackList = api.trackListRequest
    val tlDf: DataFrame = spark.read.json(Seq(gson.toJson(trackList.tracks)).toDS)
    val tlJsonDf: Dataset[TrackTracking] = SpotifyFormater.tlToOutputJson(tlDf, idSpotify, countryCode)
    val tlDfWithDate: DataFrame = SpotifyFormater.addDateMaj(tlJsonDf)
    storeInHangar(tlDfWithDate, hangarPath + "/music_tracking")

  }

  def run: Unit ={

    val tlUrl: String = storeDeezerArtistTracking
    storeDeezerTrackTracking(tlUrl)
    storeSpotifyArtistTracking()
    conf.confSpotify.countries.forEach(code => storeSpotifyTrackTracking(code))

  }
}
