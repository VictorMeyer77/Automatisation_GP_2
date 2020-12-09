package com.esgi.gptwo

import java.nio.charset.StandardCharsets

import com.esgi.gptwo.api.SpotifyApi
import com.google.gson.Gson

import scala.io.Source
import com.esgi.gptwo.bean.{Configuration, SpotifyArtist}
import com.esgi.gptwo.spark.SaveData
import org.apache.spark.sql._

object Main {

  def main(args: Array[String]): Unit ={


    val spark: SparkSession = SparkSession
      .builder()
      .appName("automatisation_load_data")
      .getOrCreate()

    if(args.length < 1){

      println("ERROR: Passer en argument le chemin du configuration.json")

    }
    else{

      val conf: Configuration = readConf(args(0))
      val api: SpotifyApi = new SpotifyApi(conf.confSpotify)
      val test: SpotifyArtist = api.artistsRequest("0OdUWJ0sBjDrqHygGUXeCF")
      println(test.toString)
      val g = new Gson
      val saver: SaveData = new SaveData(g.toJson(test))
      saver.test()

    }

  }

  def readConf(confPath: String): Configuration ={

    val gson: Gson = new Gson()
    gson.fromJson(Source.fromFile(confPath).mkString, classOf[Configuration])

  }


}
