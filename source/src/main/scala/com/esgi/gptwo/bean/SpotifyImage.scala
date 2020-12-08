package com.esgi.gptwo.bean

class SpotifyImage{

  var height: Int = _
  var url: String = ""
  var width: Int = _

  override def toString: String ={

    "{\"height\": %s, \"url\": \"%s\", \"width\": %s}".format(height.toString, url, width.toString)

  }
}
