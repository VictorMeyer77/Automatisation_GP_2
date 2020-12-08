package com.esgi.gptwo.bean

import java.util.ArrayList

class SpotifyArtist{

  var external_urls: SpotifyExternalUrls = _
  var followers: SpotifyFollowers = _
  var genres: ArrayList[String] = _
  var href: String = ""
  var id: String = ""
  var images: ArrayList[SpotifyImage] = _
  var name: String = ""
  var popularity: Int = _
  var `type`: String = ""
  var uri: String  = ""

  override def toString: String ={

    ("{\"external_urls\": %s," +
      " \"followers\": %s," +
      "\"genres\": %s," +
      " \"href\": \"%s\"," +
      "\"id\": \"%s\"," +
      " \"images\": %s," +
      "\"name\": \"%s\"," +
      "\"popularity\": %s," +
      " \"type\": \"%s\"," +
      "\"uri\": \"%s\"," +
      "}").format(external_urls.toString,
      followers.toString,
      genres.toString,
      href,
      id,
      images.toString,
      name,
      popularity.toString,
      `type`,
      uri)

  }

}