package com.esgi.gptwo

import java.util
import com.esgi.gptwo.bean.{DeezerData, DeezerTrack, SpotifyData, SpotifyItem, SpotifyToken}
import com.esgi.gptwo.conf.Spotify
import com.google.gson.Gson
import scalaj.http.{Http, HttpResponse}

object Api {

  val gson = new Gson()

  def getDeezerIdByArtsit(artistName: String): String ={

    try{

      val res: HttpResponse[String] = Http("https://api.deezer.com/search?q=" + artistName.replace(" ", "%20")).asString
      val data: DeezerData = gson.fromJson(res.body, classOf[DeezerData])
      searchIdDeezer(data.data, artistName)

    }
    catch {
      case e: Exception =>
        println("ERROR: Impossible de récupérer l'id deezer de " + artistName)
        println(e)
        ""
    }
  }

  def searchIdDeezer(tracks: util.ArrayList[DeezerTrack], artistName: String): String ={

    tracks.forEach(track => {
      if(track.artist.name.toLowerCase == artistName.toLowerCase) return track.artist.id
    })

    ""

  }

  def searchIdSpotify(items: util.ArrayList[SpotifyItem], artistName: String): String ={

    items.forEach(item => {
      if(item.name.toLowerCase == artistName.toLowerCase) return item.id
    })

    ""

  }

  def getAuthorization(spotifyConf: Spotify): String ={

    val res: HttpResponse[String] = Http("https://accounts.spotify.com/api/token")
      .postForm(Seq("grant_type" -> spotifyConf.grant_type,
        "client_id" -> spotifyConf.client_id,
        "client_secret" -> spotifyConf.client_secret)).asString

    res.body

  }

  def getAccesToken(res: String): String ={

    val gson: Gson = new Gson()
    val token: SpotifyToken = gson.fromJson(res, classOf[SpotifyToken])
    token.access_token

  }

  def getSpotifyIdByArtist(accessToken: String, artistName: String): String ={

    try{

      val res: HttpResponse[String] = Http("https://api.spotify.com/v1/search?q=" +
        artistName.replace(" ", "%20") + "&type=artist")
        .header("Authorization",  "Bearer " + accessToken)
        .asString

      val artists: SpotifyData = gson.fromJson(res.body, classOf[SpotifyData])
      searchIdSpotify(artists.artists.items, artistName)

    }
    catch {
      case e: Exception =>
        println("ERROR: Impossible de récupérer l'id spotify de " + artistName)
        println(e)
        ""
    }
  }
}
