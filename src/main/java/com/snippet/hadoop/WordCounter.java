package com.snippet.hadoop;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * 1. preparation, upload the test file to the HDFS
 * hadoop fs -put hadoop-text1.txt hadoop-text2.txt /wordcount/input
 * 
 * 2. execute the hadoop mapred job
 * hadoop jar hadoop101.jar com.snippet.hadoop.WordCounter -Dhdfs.file.input=/wordcount/input -Dhdfs.file.output=/wordcount/output
 * 
 * 3. check out the output file
 * (hadoop fs or ) hdfs dfs -cat /wordcount/output/part-r-00000
 * 
 * @author xulei
 */
public class WordCounter {
    
    private static final String HDFS_PREFIX = "hdfs://";
    private static final String INPUT_PATH = "hdfs.file.input";
    private static final String OUTPUT_PATH = "hdfs.file.output";
    
    public static void main(String[] args) 
            throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        new GenericOptionsParser(conf, args);
        Job job = Job.getInstance(conf);
        job.setJarByClass(WordCounter.class);
        // set mapper task
        job.setMapperClass(WordCountMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        // set reducer task
        job.setReducerClass(WordCountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        // set input and output
        String inputPath = conf.get(INPUT_PATH);
        System.out.println("inputPath: " + HDFS_PREFIX + inputPath);
        FileInputFormat.setInputPaths(job, new Path(HDFS_PREFIX + inputPath));
        String outputPath = conf.get(OUTPUT_PATH);
        System.out.println("outputPath: " + HDFS_PREFIX + outputPath);
        FileOutputFormat.setOutputPath(job, new Path(HDFS_PREFIX + outputPath));
        // execute mapred task
        job.waitForCompletion(true);
    }
    
}
