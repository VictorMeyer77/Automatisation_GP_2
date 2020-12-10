package com.esgi.gptwo.bean.deezer

case class Contributor(
                        id: Long,
                        name: String,
                        link: String,
                        share: String,
                        picture: String,
                        picture_small: String,
                        picture_medium: String,
                        picture_big: String,
                        picture_xl: String,
                        radio: Boolean,
                        tracklist: String,
                        `type`: String,
                        role: String
                      )
