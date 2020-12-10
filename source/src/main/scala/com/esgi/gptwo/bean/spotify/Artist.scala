package com.esgi.gptwo.bean.spotify

import java.util.ArrayList

case class Artist (

                    followers: Followers,
                    genres: ArrayList[String],
                    href: String,
                    id: String,
                    name: String,
                    popularity: Int,
                    `type`: String,
                    uri: String

                  
                  )

