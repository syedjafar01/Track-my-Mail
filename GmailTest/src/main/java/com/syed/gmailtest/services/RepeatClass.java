package com.syed.gmailtest.services;

import com.syed.gmailtest.pojos.ThreadsDataPojo;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by syedjafar on 3/1/17.
 */
@Service
public class RepeatClass {

    public String repeat(List<ThreadsDataPojo> threadIDList, String  accessToken) throws Exception {

        int res_count = 0;
        int unread_count = 0;
        int Inbox = threadIDList.size();

        for (ThreadsDataPojo t : threadIDList) {
            URL urlToGetFrom = new URL("https://www.googleapis.com/gmail/v1/users/me/threads/" + t.getId() + "?alt=json&access_token=" + accessToken);
            URLConnection urlConnToGetFrom = urlToGetFrom.openConnection();
            urlConnToGetFrom.setDoOutput(true);
            String line3 = "";
            String outputString3 = "";
            BufferedReader reader3 = new BufferedReader(new InputStreamReader(urlConnToGetFrom.getInputStream()));
            while ((line3 = reader3.readLine()) != null) {
                outputString3 += line3;
            }
        /*System.out.println(outputString3);*/

            ThreadRes tr = new ThreadRes();

            boolean ans = tr.getStatus(outputString3);

            if (ans == true) {
                res_count++;
            /*System.out.println("result : Responded ");*/
            } else {
                unread_count++;
           /* System.out.println("result :  Unread");*/
            }
        }

        System.out.println("Inbox Mails : " + Inbox);
        System.out.println("Responded Mails : " + res_count);
        System.out.println("Unread Mails : " + unread_count);

        return Inbox+";"+res_count+";"+unread_count;
    }



}
