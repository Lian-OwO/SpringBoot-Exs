package com.example.ch4test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    @GetMapping(value = "/admin/item/new")
    public String itemForm(){
        return "/item/itemForm";
    }

}