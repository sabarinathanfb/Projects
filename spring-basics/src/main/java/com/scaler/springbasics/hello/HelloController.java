package com.scaler.springbasics.hello;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/world")
    public String helloworld(){

        return "Hello World!";
    }

    @GetMapping("/you")
    public String helloyou(@RequestParam String name){

        return "Hi " +name + "!";
    }


}
