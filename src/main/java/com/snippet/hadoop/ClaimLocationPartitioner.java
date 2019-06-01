package com.snippet.hadoop;

import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 *
 * @author xulei
 */
public class ClaimLocationPartitioner extends Partitioner<Text, ClaimDTO> {

    private Map<String,Integer> location = new HashMap<>();
    
    public ClaimLocationPartitioner() {
        location.put("Singapore",0);
        location.put("Hangzhou",1);
        location.put("Shanghai",2);
    }
    
    @Override
    public int getPartition(Text key, ClaimDTO claimBo, int partitionTag) {
        return location.get(claimBo.getLocation().toString());
    }
    
}
