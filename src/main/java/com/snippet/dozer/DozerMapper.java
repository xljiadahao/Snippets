package com.snippet.dozer;

import com.snippet.dozer.businessobject.BirthDetailBO;
import com.snippet.dozer.businessobject.rest.BirthDetailRest;
import com.snippet.dozer.businessobject.rest.PlaceRest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author leixu2
 */
public class DozerMapper {
 
    private static Logger logger = LoggerFactory.getLogger(DozerMapper.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static DozerBeanMapper mapper = new DozerBeanMapper();
    
    public static void main(String[] args) {
        // prepare dozer configuration files
        List dozerMappingFiles = new ArrayList();
        dozerMappingFiles.add("dozerMapper.xml");
        mapper.setMappingFiles(dozerMappingFiles);
        // prepare Rest Request BO data
        PlaceRest placeReq = new PlaceRest();
        placeReq.setCityReq("Singapore");
        placeReq.setCountryReq("SG");
        placeReq.setStateReq("North East");
        BirthDetailRest birthDetailReq = new BirthDetailRest();
        birthDetailReq.setDateReq(new Date());
        birthDetailReq.setPlaceReq(placeReq);
        birthDetailReq.setDescrpReq("Rest Request to BO");
        
        BirthDetailBO birthDetailBO = null;
        try {
            birthDetailBO = mapper.map(birthDetailReq, BirthDetailBO.class);
            if (birthDetailBO != null && birthDetailBO.getPlace() != null) {
                System.out.println("---------Dozer Mapper---------");
                System.out.println("BirthDetailBO: {" + sdf.format(birthDetailBO.getDate()) + ", " + birthDetailBO.getDescrp() 
                    + ", PlaceBO: {" + birthDetailBO.getPlace().getCountry() + ", " + birthDetailBO.getPlace().getCity() 
                    + ", " + birthDetailBO.getPlace().getState() + "}}");
            } else {
                System.out.println("dozer mapper error");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
