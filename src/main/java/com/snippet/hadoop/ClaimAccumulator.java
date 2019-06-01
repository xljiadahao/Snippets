package com.snippet.hadoop;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
/**
 * Command: 
 * hadoop jar ~/Desktop/hadoop101.jar com.snippet.hadoop.ClaimAccumulator 
 * -Dhdfs.file.input=/claimaccumulator/input -Dhdfs.file.output=/claimaccumulator/output
 * 
 * Result with Partition: 
 * hdfs dfs -cat /claimaccumulator/output5/part-r-00000
 * Singapore-PayPal	ClaimDTO{companyName=PayPal, location=Singapore, method=Grab, amount=270}
 * Singapore-V-Key	ClaimDTO{companyName=V-Key, location=Singapore, method=Taxi,Grab,Uber, amount=310}
 * hdfs dfs -cat /claimaccumulator/output5/part-r-00001
 * Hangzhou-V-Key	ClaimDTO{companyName=V-Key, location=Hangzhou, method=Hotel, amount=2400}
 * hdfs dfs -cat /claimaccumulator/output5/part-r-00002
 * Shanghai-PayPal	ClaimDTO{companyName=PayPal, location=Shanghai, method=Didi, amount=50}
 * 
 * Result without Partition: 
 * hdfs dfs -cat /claimaccumulator/output/part-r-00000
 * Hangzhou-V-Key	ClaimDTO{companyName=V-Key, location=Hangzhou, method=Hotel, amount=2400}
 * Shanghai-PayPal	ClaimDTO{companyName=PayPal, location=Shanghai, method=Didi, amount=50}
 * Singapore-PayPal	ClaimDTO{companyName=PayPal, location=Singapore, method=Grab, amount=270}
 * Singapore-V-Key	ClaimDTO{companyName=V-Key, location=Singapore, method=Taxi,Grab,Uber, amount=310}
 * 
 * @author xulei
 */
public class ClaimAccumulator {
    
    private static final String HDFS_PREFIX = "hdfs://";
    private static final String INPUT_PATH = "hdfs.file.input";
    private static final String OUTPUT_PATH = "hdfs.file.output";
    
    public static void main(String[] args) throws 
            IOException, InterruptedException, ClassNotFoundException {
        Configuration configuration = new Configuration();
        new GenericOptionsParser(configuration, args);
        Job job = Job.getInstance(configuration);
        job.setJarByClass(ClaimAccumulator.class);
        
        // difine partition rule
        job.setPartitionerClass(ClaimLocationPartitioner.class);
        job.setNumReduceTasks(3);
        
        // define combiner, reducing in mapper task phase
        // job.setCombinerClass(SortCombiner.class);
        
        job.setMapperClass(ClaimCountMapper.class);
        job.setReducerClass(ClaimLocationReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(ClaimDTO.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(ClaimDTO.class);
        String inputPath = configuration.get(INPUT_PATH);
        System.out.println("inputPath: " + HDFS_PREFIX + inputPath);
        FileInputFormat.setInputPaths(job, new Path(HDFS_PREFIX + inputPath));
        String outputPath = configuration.get(OUTPUT_PATH);
        System.out.println("outputPath: " + HDFS_PREFIX + outputPath);
        FileOutputFormat.setOutputPath(job, new Path(HDFS_PREFIX + outputPath));
        if (job.waitForCompletion(true)) {
            System.out.println("Claim Accumulation Job Completed!");
        }
    }
    
}
