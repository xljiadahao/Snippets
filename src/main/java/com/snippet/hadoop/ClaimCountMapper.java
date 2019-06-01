package com.snippet.hadoop;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;

/**
 *
 * @author xulei
 */
public class ClaimCountMapper extends Mapper<LongWritable, Text, Text, ClaimDTO> {

    @Override
    protected void map(LongWritable key, Text value, Context context) 
            throws IOException, InterruptedException {
        String line = value.toString();
        String[] words = line.split(" ");
        ClaimDTO claim = new ClaimDTO(new Text(words[0]), new Text(words[1]), 
                new Text(words[2]), new IntWritable(Integer.parseInt(words[3])));
        // accumulate by location
        context.write(new Text(words[1]), claim);
    }
    
}
