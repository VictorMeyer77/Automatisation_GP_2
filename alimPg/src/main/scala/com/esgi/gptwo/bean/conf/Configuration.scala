package com.esgi.gptwo.bean.conf

import java.util.ArrayList

case class Configuration (

                          postgres: Postgres,
                          hangar: String,
                          checkpointPath: String,
                          tablesToAlim: ArrayList[String]

                         )