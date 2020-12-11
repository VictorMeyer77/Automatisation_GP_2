#!/bin/sh

sh /usr/bin/spark-submit  --class com.esgi.gptwo.Main hdfs://d271ee89-3c06-4d40-b9d6-d3c1d65feb57.priv.instances.scw.cloud:8020/target/scala-2.12/source-assembly-0.1.jar conf/configuration.json