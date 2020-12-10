package com.esgi.gptwo.bean.common

case class TrackTracking(

                          id_source: String,
                          id_artist: String,
                          country_code: String,
                          popularity: Long,
                          feat: Boolean,
                          duration: Long,
                          source: String,
                          date_maj: Long

                        )