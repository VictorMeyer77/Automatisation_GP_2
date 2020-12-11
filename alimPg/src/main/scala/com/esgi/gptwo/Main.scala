package com.esgi.gptwo

import com.google.gson.Gson
import org.apache.spark.sql.SparkSession
import scala.io.{BufferedSource, Source}
import org.apache.spark.sql._

//import org.apache.hadoop.fs
//import org.apache.hadoop
//import org.apache.hadoop.fs.Path

object Main {

  val spark: SparkSession = SparkSession
    .builder()
    .getOrCreate()

  def main(args: Array[String]): Unit ={

    val conf: bean.conf.Configuration = readConf(args(0))

    new Alim(conf).run()

  }

 /* def readConf(confPath: String): bean.conf.Configuration ={

    val hdfs = fs.FileSystem.get(new hadoop.conf.Configuration)
    val path: Path = new Path(confPath)
    val stream = hdfs.open(path)
    val streamStr = Source.fromInputStream(stream).mkString
    val gson = new Gson
    val conf: bean.conf.Configuration = gson.fromJson(streamStr, classOf[bean.conf.Configuration])
    conf

  }*/

  def readConf(confPath: String): bean.conf.Configuration ={

    val gson: Gson = new Gson()
    val confFile: BufferedSource = Source.fromFile(confPath)
    val conf: bean.conf.Configuration = gson.fromJson(confFile.mkString, classOf[bean.conf.Configuration])
    confFile.close()
    conf

  }
}
