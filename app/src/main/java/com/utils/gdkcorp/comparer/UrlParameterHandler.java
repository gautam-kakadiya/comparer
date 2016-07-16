package com.utils.gdkcorp.comparer;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by Gautam Kakadiya on 03-06-2016.
 */
public class UrlParameterHandler {
    public static UrlParameterHandler paramHandler;
    private UrlParameterHandler() {

    }
    public static synchronized UrlParameterHandler getInstance(){
        if(paramHandler==null){
            paramHandler=new UrlParameterHandler();
            return paramHandler;
        }
        return paramHandler;
    }

    public  Map<String,String> buildMapForItemSearch(String keyword,String Pageno){
        Map<String, String> myparams = new HashMap<String, String>();
        myparams.put("Service", "AWSECommerceService");
        myparams.put("Operation", "ItemSearch");
        myparams.put("Version", "2013-08-01");
        //myparams.put("ContentType", "text/xml");
        //myparams.put("SearchIndex", "MobileApps");//for searching mobile apps
        myparams.put("Keywords", keyword);
        myparams.put("AssociateTag", "compare05b-21");
        //myparams.put("MaximumPrice","1000");
        //myparams.put("Sort","price");
        myparams.put("ResponseGroup", "Images,ItemAttributes");
        myparams.put("SearchIndex","All");
        if(Pageno!=null){
            myparams.put("ItemPage",Pageno);
        }
        return myparams;
    }
}
