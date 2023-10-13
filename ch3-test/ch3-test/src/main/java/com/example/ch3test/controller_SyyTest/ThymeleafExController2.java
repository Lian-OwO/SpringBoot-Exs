package com.example.ch3test.controller_SyyTest;

import com.example.shop.dto_SyyTest.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/thymeleaf2")
public class ThymeleafExController2 {


    @GetMapping(value = "/ex08")
    public String thymeleafExample07(){
        return "thymeleafEx_SyyTest/thymeleafEx07";
    }

}