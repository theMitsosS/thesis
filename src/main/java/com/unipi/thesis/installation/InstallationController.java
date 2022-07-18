package com.unipi.thesis.installation;

import com.unipi.thesis.model.Installation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class InstallationController {


    @GetMapping("installations")
    public ResponseEntity<List<Installation>> getAllInstallations(){
        List<Installation> allInstallations = new ArrayList<>();
        allInstallations.add(new Installation("Installation 1","Apollonos 8",15,new Point(10,5)));
        allInstallations.add(new Installation("Installation 2","Perikleous 9",10,new Point(10,15)));
        return new ResponseEntity<>(allInstallations, HttpStatus.OK);
    }
}
