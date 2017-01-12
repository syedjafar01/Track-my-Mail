package com.syed.gmailtest.services;

import com.jayway.jsonpath.JsonPath;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by syedjafar on 3/1/17.
 */
@Service
public class ThreadRes {

    public static boolean getStatus(String os) throws Exception{

        int i;
        int responded=0;
        int unread=1;

        String jsonInput = os;

        ArrayList<String> from = JsonPath.read(jsonInput, "$..headers[?(@.name=='From')].value");
        String head=from.get(0);
        String client=from.get(0);

        /*
        * if((from.get(0)) == (from.get(from.size()-1))){
        *
        *
        *
        * */

        for (i = 1; i < from.size(); i++) {
            if (!(head.equals(from.get(i)))&&!(client.equals(from.get(i)))) {
                head=from.get(i) ;
                responded++;
                unread--;
            }
            else if(!(head.equals(from.get(i)))&&client.equals(from.get(i)))
            {
                head=from.get(i) ;
                responded--;
                unread++;
            }

        }
        if(unread==0&&responded==1)
            return true;
        else if (unread==1&&responded==0)
            return false;
        return false;
    }
}
