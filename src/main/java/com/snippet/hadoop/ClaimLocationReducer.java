package com.snippet.hadoop;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 *
 * @author xulei
 */
public class ClaimLocationReducer extends Reducer<Text,ClaimDTO,Text,ClaimDTO> {

    @Override
    protected void reduce(Text key, Iterable<ClaimDTO> values, Context context) 
            throws IOException, InterruptedException {
        int spendPp = 0;
        StringBuilder typePp = new StringBuilder("");
        int spendVk = 0;
        StringBuilder typeVk = new StringBuilder("");
        for (ClaimDTO claim : values) {
            if("PayPal".equalsIgnoreCase(claim.getCompanyName().toString())) {
                spendPp += claim.getAmount().get();
                if (!typePp.toString().contains(claim.getMethod().toString())) {
                    typePp.append(",").append(claim.getMethod().toString());
                }
            } else if ("V-Key".equalsIgnoreCase(claim.getCompanyName().toString())) {
                spendVk += claim.getAmount().get();
                if (!typeVk.toString().contains(claim.getMethod().toString())) {
                    typeVk.append(",").append(claim.getMethod().toString());
                }
            }
        }
        if (spendPp !=0) {
            context.write(new Text(key.toString() + "-PayPal"),
                new ClaimDTO(new Text("PayPal"), key, 
                new Text(typePp.toString().substring(1)), new IntWritable(spendPp)));
        }
        if (spendVk != 0) {
            context.write(new Text(key.toString() + "-V-Key"),
                new ClaimDTO(new Text("V-Key"), key, 
                new Text(typeVk.toString().substring(1)), new IntWritable(spendVk)));
        }
    }
    
}
