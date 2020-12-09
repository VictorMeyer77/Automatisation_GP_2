package com.esgi.gptwo.api

import com.google.gson.Gson
import scalaj.http.{Http, HttpResponse}
import com.esgi.gptwo.bean.{DeezerArtist}

class DeezerApi {

  def getArtist(idArtist : Int): DeezerArtist = {
    val res: HttpResponse[String] = Http("https://api.deezer.com/artist/" + idArtist).asString
    val gson = new Gson()
    gson.fromJson(res.body, classOf[DeezerArtist])
  }

}
