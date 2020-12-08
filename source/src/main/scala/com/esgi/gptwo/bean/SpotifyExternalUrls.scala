package com.esgi.gptwo.bean

class SpotifyExternalUrls{

  var spotify: String = ""

  override def toString: String ={

    "{\"spotify\": \"%s\"}".format(spotify)

  }

}