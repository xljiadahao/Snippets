package com.snippet.dozer.businessobject;

import java.util.Date;

/**
 *
 * @author xulei
 */
public class BirthDetailBO {
    
    private Date date;
    private String descrp;
    private PlaceBO place;

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the descrp
     */
    public String getDescrp() {
        return descrp;
    }

    /**
     * @param descrp the descrp to set
     */
    public void setDescrp(String descrp) {
        this.descrp = descrp;
    }

    /**
     * @return the place
     */
    public PlaceBO getPlace() {
        return place;
    }

    /**
     * @param place the place to set
     */
    public void setPlace(PlaceBO place) {
        this.place = place;
    }
 
}
