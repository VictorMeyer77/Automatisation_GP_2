package com.esgi.gptwo.conf

import java.util.ArrayList

case class Spotify(

                  grant_type: String,
                  client_id: String,
                  client_secret: String,
                  countries: ArrayList[String]

                  )