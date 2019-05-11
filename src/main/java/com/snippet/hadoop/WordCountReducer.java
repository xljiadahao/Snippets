package com.snippet.hadoop;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 *
 * @author xulei
 */
public class WordCountReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) 
            throws IOException, InterruptedException {
        long count = 0;
        for (LongWritable num : values) {
            count += num.get();
        }
        context.write(key, new LongWritable(count));
    }
    
}
