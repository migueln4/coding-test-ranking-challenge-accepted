package com.idealista.main;

import com.idealista.main.persistence.InMemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Main {

    @Autowired
    private InMemoryPersistence imp;

    @PostConstruct
    public void init() { imp.init(); }
    
    public static void main(String [] args){
        SpringApplication.run(Main.class, args);
    }

}