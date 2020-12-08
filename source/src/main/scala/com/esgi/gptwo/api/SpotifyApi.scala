package com.esgi.gptwo.api

import com.google.gson.Gson
import com.esgi.gptwo.bean.{ConfSpotify, SpotifyArtist, SpotifyToken}
import scalaj.http._

class SpotifyApi(conf: ConfSpotify) {

  val accessToken: String = getAccesToken(getAuthorization)

  def getAuthorization: HttpResponse[String] ={

    val res: HttpResponse[String] = Http("https://accounts.spotify.com/api/token")
      .postForm(Seq("grant_type" -> conf.grant_type,
        "client_id" -> conf.client_id,
        "client_secret" -> conf.client_secret)).asString

    res

  }

  def getAccesToken(res: HttpResponse[String]): String ={

    val gson: Gson = new Gson()
    val token: SpotifyToken = gson.fromJson(res.body, classOf[SpotifyToken])
    token.access_token

  }


  def artistsRequest(artistId: String): SpotifyArtist ={

    val res: HttpResponse[String] = Http("https://api.spotify.com/v1/artists/" + artistId)
      .header("Authorization",  "Bearer " + accessToken)
      .asString
    val gson: Gson = new Gson()

    gson.fromJson(res.body, classOf[SpotifyArtist])

  }



}
