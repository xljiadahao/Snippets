package com.snippet.messagequeue;

/**
 *
 * @author leixu2
 */
public enum MQTypeEnum {
    
    ACTIVEMQ("ActiveMQ");
    
    private String mqType;

    private MQTypeEnum(String mqType) {
        this.mqType = mqType;
    }

    /**
     * @return the mqType
     */
    public String getMqType() {
        return mqType;
    }

    /**
     * @param mqType the mqType to set
     */
    public void setMqType(String mqType) {
        this.mqType = mqType;
    }

}
