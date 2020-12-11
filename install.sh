#!/bin/sh

cd alimPg
sbt clean assembly

cd ../source
sbt clean assembly

cd ../

if $(hadoop fs -test -d "/user/groupe2/data")  then echo "ok"; else hdfs dfs -mkdir "/user/groupe2/data"; fi
if $(hadoop fs -test -d "/user/groupe2/data/hangar")  then echo "ok"; else hdfs dfs -mkdir "/user/groupe2/data/hangar"; fi
if $(hadoop fs -test -d "/user/groupe2/app/")  then echo "ok"; else hdfs dfs -mkdir "/user/groupe2/app"; fi
if $(hadoop fs -test -d "/user/groupe2/app/alimPg")  then echo "ok"; else hdfs dfs -mkdir "/user/groupe2/app/alimPg"; fi
if $(hadoop fs -test -d "/user/groupe2/app/alimPg/conf")  then echo "ok"; else hdfs dfs -mkdir "/user/groupe2/app/alimPg/conf"; fi
if $(hadoop fs -test -d "/user/groupe2/app/source")  then echo "ok"; else hdfs dfs -mkdir "/user/groupe2/app/source"; fi
if $(hadoop fs -test -d "/user/groupe2/app/source/conf")  then echo "ok"; else hdfs dfs -mkdir "/user/groupe2/app/source/conf"; fi
if $(hadoop fs -test -d "/user/groupe2/app/alimPg/lib")  then echo "ok"; else hdfs dfs -mkdir "/user/groupe2/app/alimPg/lib"; fi
if $(hadoop fs -test -d "/user/groupe2/app/source/lib")  then echo "ok"; else hdfs dfs -mkdir "/user/groupe2/app/source/lib"; fi

hdfs dfs -put -f alimPg/target/scala-2.12/alimPg-assembly-0.1.jar /user/groupe2/app/alimPg/lib
hdfs dfs -put -f alimPg/conf/configuration.json /user/groupe2/app/alimPg/conf

hdfs dfs -put -f source/target/scala-2.12/source-assembly-0.1.jar /user/groupe2/app/source/lib
hdfs dfs -put -f source/conf/configuration.json /user/groupe2/app/source/conf