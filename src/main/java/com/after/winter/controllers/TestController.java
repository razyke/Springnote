package com.after.winter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

  @RequestMapping(value = "/get", method = RequestMethod.GET)
  @ResponseBody
  public String printHello(ModelMap model) {
    return "hello";
  }

}
