package com.snippet.dozer.businessobject.rest;

import java.util.Date;

/**
 *
 * @author xulei
 */
public class BirthDetailRest {
    
    private Date dateReq;
    private String descrpReq;
    private PlaceRest placeReq;

    /**
     * @return the dateReq
     */
    public Date getDateReq() {
        return dateReq;
    }

    /**
     * @param dateReq the dateReq to set
     */
    public void setDateReq(Date dateReq) {
        this.dateReq = dateReq;
    }

    /**
     * @return the descrpReq
     */
    public String getDescrpReq() {
        return descrpReq;
    }

    /**
     * @param descrpReq the descrpReq to set
     */
    public void setDescrpReq(String descrpReq) {
        this.descrpReq = descrpReq;
    }

    /**
     * @return the placeReq
     */
    public PlaceRest getPlaceReq() {
        return placeReq;
    }

    /**
     * @param placeReq the placeReq to set
     */
    public void setPlaceReq(PlaceRest placeReq) {
        this.placeReq = placeReq;
    }

}
