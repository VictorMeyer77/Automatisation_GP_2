#!/bin/sh

sh /usr/bin/spark-submit  --class com.esgi.gptwo.Main target/scala-2.12/source-assembly-0.1.jar conf/configuration.json