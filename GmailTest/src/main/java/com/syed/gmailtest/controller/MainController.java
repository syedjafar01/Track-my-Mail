package com.syed.gmailtest.controller;

import com.syed.gmailtest.services.ComputationClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.QueryParam;

/**
 * Created by syedjafar on 29/12/16.
 */

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    ComputationClass computationClass;

    @RequestMapping(method = RequestMethod.GET)
    public String getToken() {
        return "loginpage.html";
    }

    @RequestMapping(value = "nextpage", method = RequestMethod.GET)
    public String google() {
        return "index.html";
    }



    @RequestMapping(value = "/compute", method = RequestMethod.GET)
    public ResponseEntity callBack(@QueryParam("code") String code) throws Exception {
        String c = code;
        String data = computationClass.callbackmethod(c);
        return  new ResponseEntity( data,HttpStatus.OK);

    }

    @RequestMapping(value = "/refresh")
    public void refetch() {
        System.out.println("refresh");
    }
}
