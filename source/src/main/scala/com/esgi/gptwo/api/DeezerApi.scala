package com.esgi.gptwo.api

import com.google.gson.Gson
import scalaj.http.{Http, HttpResponse}
import com.esgi.gptwo.bean.deezer.{Artist, Data}

class DeezerApi(artistId: String) {

  def artistsRequest: Artist = {

    val res: HttpResponse[String] = Http("https://api.deezer.com/artist/" + artistId).asString
    val gson = new Gson()
    gson.fromJson(res.body, classOf[Artist])

  }


  def trackListRequest(url: String): Data = {

    val res: HttpResponse[String] = Http(url).asString
    val gson = new Gson()
    gson.fromJson(res.body, classOf[Data])

  }
}