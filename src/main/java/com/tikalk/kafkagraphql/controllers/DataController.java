package com.tikalk.kafkagraphql.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/data")
public class DataController {

    @GetMapping
    public ResponseEntity<String> getData(HttpServletRequest request){
        return ResponseEntity.ok("Data: " + request.getRequestURI());
    }

}
