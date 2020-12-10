package com.esgi.gptwo.bean.deezer

import java.util.ArrayList

case class Track(

                id: Long,
                title: String,
                duration: String,
                rank: String,
                artist: Artist,
                contributors: ArrayList[Contributor]

                )