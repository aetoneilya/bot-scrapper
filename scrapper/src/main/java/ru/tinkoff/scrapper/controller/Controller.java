package ru.tinkoff.scrapper.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping("/hello")
    public String helloWorld(){
        return "Hello World!";
    }

    @RequestMapping("/hello/{id}")
    public String helloWorldId(@PathVariable String id){
        return "Hello World!" + id;
    }
}
