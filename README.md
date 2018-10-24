# Inverted Index Map Reduce Code 

## Objective : 
- Creating an Inverted Index of words occuring in set of web pages
- Getting Hands on exercise in GCP App Engine

## DataSet :
Instructions for downloading the dataset are provided in the file Hadoop Exercise.pdf 

## Technologies : 
- Java 
- Hadoop 
- Google Cloud Platform

## Functionality :
In the code, Mapper class gave the output as the Word: docID. The reducer class took this as input and gave the output in the formal word docID: count. 
The merge function of google cloud was used to combine the output of reducer class to give the final output.


## Usage

Before compiling the code , it is important to set the environment variables as follows  

> export JAVA_HOME=/usr/java/default
> export PATH=${JAVA_HOME}/bin:${PATH}
> export HADOOP_CLASSPATH=${JAVA_HOME}/lib/tools.jar

## Compile the Java code and Create jar file:


```
bin/hadoop com.sun.tools.javac.Main InvertedIndexJob.java
```
```
jar cf invertedindex.jar InvertedIndexJob*.class
```

Assuming that:
- /user/niharika/invertedindex/input - input directory in HDFS
- /user/niharika/invertedindex/output - output directory in HDFS

## Run the Application 

```
 bin/hadoop jar invertedindex.jar.jar InvertedIndexJob /user/niharika/invertedindex/input /user/niharika/invertedindex/output
```

## Sample output
```
 bin/hadoop fs -cat /user/niharika/invertedindex/output/part-r-00000
```
* adamantinous	5722018508:1	
* adaptability	5722018508:1	
* adaptationist	5722018235:1	5722018301:1	
* adaptor	5722018235:1	5722018101:1	5722018508:2	
* adaptors	5722018508:1	
* adaptés	5722018508:1	
* adar	5722018508:2	
* adbusters	5722018235:16	5722018496:4
