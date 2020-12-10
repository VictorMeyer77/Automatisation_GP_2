package com.esgi.gptwo

import com.esgi.gptwo.conf.Configuration
import com.google.gson.Gson
import scalaj.http.HttpResponse
import scala.io.{BufferedSource, Source}

object Main {

  val confPath: String = "conf/configuration.json"

  def main(args: Array[String]): Unit ={

    if(args.length < 1){

      println("ERROR: Passer en argument au moins un artiste à traiter.")

    }
    else{

      val conf: Configuration = readConf(confPath)

      val alim: Alim = new Alim(conf.postgres)
      alim.createTable()

      args.map(
        artist => {

          val artistWithSpace: String = artist.replace("%20", " ")
          val idDeezer: String = Api.getDeezerIdByArtsit(artistWithSpace)
          val authSpotify: String = Api.getAuthorization(conf.confSpotify)
          val accessToken: String = Api.getAccesToken(authSpotify)
          val idSpotify: String = Api.getSpotifyIdByArtist(accessToken, artistWithSpace)

          if(idDeezer != "" && idSpotify != ""){
            alim.insertArtist(artistWithSpace, idDeezer,idSpotify)
            artist
          }
        }
      )
    }
  }

  def readConf(confPath: String): Configuration ={

    val gson: Gson = new Gson()
    val confFile: BufferedSource = Source.fromFile(confPath)
    val conf: Configuration = gson.fromJson(confFile.mkString, classOf[Configuration])
    confFile.close()
    conf

  }
}
