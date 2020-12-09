package com.esgi.gptwo.bean

import java.util.ArrayList

class DeezerArtist {

  var id: Int = 0
  var name: String = ""
  var link: String = ""
  var share: String = ""
  var picture: String = ""
  var nb_album: Int = 0
  var nb_fan: Int = 0
  var radio: Boolean = false
  var tracklist: String = ""

  override def toString: String ={

    ("{id : %d" +
      "name : %s" +
      "link : %s" +
      "share : %s" +
      "picture : %s" +
      "nb_album : %d" +
      "nb_fan : %d" +
      "radio %s" +
      "tracklist : %s}")
      .format(
        id,
        name,
        link,
        share,
        picture,
        nb_album,
        nb_fan,
        radio.toString,
        tracklist
      )
  }
}
