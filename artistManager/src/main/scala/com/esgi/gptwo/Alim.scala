package com.esgi.gptwo

import com.esgi.gptwo.conf.Postgres
import java.sql.{Connection, DriverManager}
import scala.io.Source

class Alim(postgresConf: Postgres) {

  val connection: Connection = getConnection

  def getConnection: Connection ={

    classOf[org.postgresql.Driver]
    val url: String = "jdbc:postgresql://%s:%s/%s?user=%s&password=%s"
      .format(postgresConf.address, postgresConf.port, postgresConf.database, postgresConf.user, postgresConf.password)

    DriverManager.getConnection(url)
  }

  def createTable(): Unit ={

    try {

      val creeTable = Source.fromFile(postgresConf.createTablePath)
      val creeTableStr = creeTable.mkString
      val stm = connection.createStatement()
      stm.executeUpdate(creeTableStr)
      creeTable.close()
      println("INFO: Tables créées avec succès.")

    } catch {

      case e: Exception => println(e)

    }
  }


 def insertArtist(artistName: String, idDeezer: String, idSpotify: String): Unit ={

   try {

     val stm = connection.createStatement()
     stm.executeUpdate("""insert into artist (id_deezer, id_spotify, name) values ('%s', '%s', '%S');"""
       .format(idDeezer, idSpotify, artistName))
     println("INFO: Artist: %s bien inséré dans la base.".format(artistName))

   } catch {

     case e: Exception =>
       println("ERROR: Impossible d'insérer les ids de %s.".format(artistName))
       println(e)

   }
 }
}
