package com.unipi.thesis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping
    public ResponseEntity<Object> sampleMethod(){
        return new ResponseEntity<>("Hello world", HttpStatus.OK);
    }
}
