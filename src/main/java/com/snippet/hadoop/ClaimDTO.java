package com.snippet.hadoop;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

/**
 *
 * @author xulei
 */
public class ClaimDTO implements Writable, WritableComparable<ClaimDTO> {
    
    private Text companyName;
    private Text location;
    private Text method;
    private IntWritable amount;

    public ClaimDTO(Text companyName, Text location, 
            Text method, IntWritable amount) {
        this.companyName = companyName;
        this.location = location;
        this.method = method;
        this.amount = amount;
    }
    
    public ClaimDTO() {}

    @Override
    public void write(DataOutput out) throws IOException {
        this.companyName.write(out);
        this.location.write(out);
        this.method.write(out);
        this.amount.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        companyName = new Text();
        companyName.readFields(in);
        location = new Text();
        location.readFields(in);
        method = new Text();
        method.readFields(in);
        amount = new IntWritable();
        amount.readFields(in);
    }

    public Text getCompanyName() {
        return companyName;
    }

    public Text getLocation() {
        return location;
    }

    public Text getMethod() {
        return method;
    }

    public IntWritable getAmount() {
        return amount;
    }

    public void setCompanyName(Text companyName) {
        this.companyName = companyName;
    }

    public void setLocation(Text location) {
        this.location = location;
    }

    public void setMethod(Text method) {
        this.method = method;
    }

    public void setAmount(IntWritable amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ClaimDTO{" + "companyName=" + companyName.toString() 
                + ", location=" + location.toString() 
                + ", method=" + method.toString() 
                + ", amount=" + amount.get() + '}';
    }

    @Override
    public int compareTo(ClaimDTO o) {
        return o.getAmount().get() - this.getAmount().get();
    }
    
}