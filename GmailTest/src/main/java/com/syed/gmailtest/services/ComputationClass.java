package com.syed.gmailtest.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.syed.gmailtest.pojos.ThreadsDataPojo;
import com.syed.gmailtest.pojos.ThreadsPojo;
import com.syed.gmailtest.pojos.TokenPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by syedjafar on 3/1/17.
 */
@Service
public class ComputationClass {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputationClass.class);
    private String clientId = "1062469629027-pbbqkhtv7ikq2jfj60v5luj82o8dqda1.apps.googleusercontent.com";
    private String clientSecret = "p_qzI6oVGNNtYWgc0bgPmwCk";
    private String redirectUri = "http%3A%2F%2Flocalhost%3A8080%2Ffinal.html";

    public String callbackmethod(String code) throws Exception {



        String urlParameters = "code=" + code +
                "&approval_prompt=force&access_type=offline&client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&redirect_uri=" + redirectUri
                + "&grant_type=authorization_code";

        URL url = new URL("https://www.googleapis.com/oauth2/v4/token");
        URLConnection urlConn = url.openConnection();
        urlConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(urlConn.getOutputStream());
        writer.write(urlParameters);//sending parameters to the URL as 'post' method
        writer.flush();

        //get output in outputString
        String line;
        String tokenString = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        while ((line = reader.readLine()) != null) {
            tokenString += line;
        }
        LOGGER.info(tokenString);

        Gson gson = new GsonBuilder().create();
        TokenPojo pojo = gson.fromJson(tokenString, TokenPojo.class);

        String accessToken = pojo.getAccess_token();
        LOGGER.info("AccessToken :" + accessToken);
        LOGGER.info("**************************************||  Token Extraction  ||**************************************");
        LOGGER.info("**************************************||        NEXT...     ||**************************************");
        LOGGER.info("**************************************||   Data Extraction  ||**************************************");

        URL accesstokenvalidatingurl = new URL("https://www.googleapis.com/oauth2/v1/tokeninfo?access_token="+accessToken);
        URLConnection urlConn3 = accesstokenvalidatingurl.openConnection();
        String line3 = "";
        StringBuilder sb2=new StringBuilder(line3);
        String outputString3 = "";
        BufferedReader reader3 = new BufferedReader(new InputStreamReader(urlConn3.getInputStream()));
        while ((line3 = reader3.readLine()) != null) {
            sb2.append(line3);
        }
        outputString3 = sb2.toString();

        LOGGER.info("Validating : " + outputString3);

        System.out.println(outputString3.contains("error"));

        URL url2 = new URL("https://www.googleapis.com/gmail/v1/users/me/threads?q=in:inbox&alt=json&access_token=" + accessToken);
        URLConnection urlConn2 = url2.openConnection();
        urlConn2.setDoOutput(true);
        String line2 = "";
        StringBuilder sb=new StringBuilder(line2);
        String outputString2 = "";
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(urlConn2.getInputStream()));
        while ((line2 = reader2.readLine()) != null) {
            sb.append(line2);
        }
        outputString2=sb.toString();

       /* System.out.println(outputString2);*/


        ThreadsPojo pojo2 = gson.fromJson(outputString2, ThreadsPojo.class);
        System.out.println(pojo2.getNextPageToken());
        List<ThreadsDataPojo> threadIDList = pojo2.getThreads();
       /* for(ThreadsDataPojo t:threadIDList)
        {
            System.out.println(t.getId());
        }
*/
       while(true) {
           url2 = new URL("https://www.googleapis.com/gmail/v1/users/me/threads?q=in:inbox&alt=json&maxResults=150&pageToken=" + pojo2.getNextPageToken()+"&access_token=" + accessToken);
           urlConn2 = url2.openConnection();
           urlConn2.setDoOutput(true);
           line2 = "";
           outputString2 = "";
           reader2 = new BufferedReader(new InputStreamReader(urlConn2.getInputStream()));
           while ((line2 = reader2.readLine()) != null) {
               outputString2 += line2;
           }
           pojo2 = gson.fromJson(outputString2, ThreadsPojo.class);
           LOGGER.info("NextPageToken : " + pojo2.getNextPageToken());
           List<ThreadsDataPojo> threadIDListnew = pojo2.getThreads();
           threadIDList.addAll(threadIDListnew);
           if(threadIDListnew.size()<100)
               break;
           System.out.println("Thread list size: "+threadIDList.size());
           threadIDListnew.clear();

       }

        System.out.println("****************************************************************************");
        for(ThreadsDataPojo s:threadIDList)
        {
            System.out.println(s.getId());
        }
        RepeatClass rc = new RepeatClass();

  return rc.repeat(threadIDList,accessToken);
    }
}