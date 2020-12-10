package com.esgi.gptwo


import com.google.gson.Gson
import org.apache.hadoop.fs
import org.apache.spark.sql.SparkSession
import org.apache.hadoop
import org.apache.hadoop.fs.Path
import scala.io.Source
import org.apache.spark.sql._

object Main {

  def main(args: Array[String]): Unit ={


    val spark: SparkSession = SparkSession
      .builder()
      .appName("automatisation_load_data")
      .getOrCreate()

    if(args.length < 1){

      println("ERROR: Passer en argument le chemin du configuration.json")

    }
    else{

      val conf: bean.conf.Configuration = readConf(args(0))
      val hangarPath: String = conf.hangar
      val postgres: PostgresManager = new PostgresManager(conf.postgres)
      val artistToTrack: DataFrame = postgres.getArtistToTrack

      artistToTrack.collect.foreach(artist =>
        new Storer(artist.getString(1),
          artist.getString(2),
          hangarPath,
          conf).run
      )
    }
  }

  def readConf(confPath: String): bean.conf.Configuration ={

    val hdfs = fs.FileSystem.get(new hadoop.conf.Configuration)
    val path: Path = new Path(confPath)
    val stream = hdfs.open(path)
    val streamStr = Source.fromInputStream(stream).mkString
    val gson = new Gson
    val conf: bean.conf.Configuration = gson.fromJson(streamStr, classOf[bean.conf.Configuration])
    conf

  }

}
