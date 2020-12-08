package com.esgi.gptwo.bean


class SpotifyFollowers{

  var href: String = ""
  var total: Int = _

  override def toString: String ={

    "{\"href\": \"%s\", \"total\": %s}".format(href, total.toString)

  }

}