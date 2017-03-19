package com.snippet.dozer.businessobject.rest;

/**
 *
 * @author xulei
 */
public class PlaceRest {
    
    private String cityReq;
    private String stateReq;
    private String countryReq;
    
    private String description;

    /**
     * @return the cityReq
     */
    public String getCityReq() {
        return cityReq;
    }

    /**
     * @param cityReq the cityReq to set
     */
    public void setCityReq(String cityReq) {
        this.cityReq = cityReq;
    }

    /**
     * @return the stateReq
     */
    public String getStateReq() {
        return stateReq;
    }

    /**
     * @param stateReq the stateReq to set
     */
    public void setStateReq(String stateReq) {
        this.stateReq = stateReq;
    }

    /**
     * @return the countryReq
     */
    public String getCountryReq() {
        return countryReq;
    }

    /**
     * @param countryReq the countryReq to set
     */
    public void setCountryReq(String countryReq) {
        this.countryReq = countryReq;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
