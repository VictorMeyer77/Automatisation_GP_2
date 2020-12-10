package com.esgi.gptwo.conf

case class Postgres (

                address: String,
                user: String,
                database: String,
                password: String,
                port: Int,
                createTablePath: String

                )
