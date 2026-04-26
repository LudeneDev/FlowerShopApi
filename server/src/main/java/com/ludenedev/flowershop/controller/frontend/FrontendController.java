package com.ludenedev.flowershop.controller.frontend;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {
    @RequestMapping(value = "/{path:[^\\.]*}",produces = {MediaType.TEXT_HTML_VALUE})
    public String serveApplication(){
        return "forward:/index.html";
    }

    @RequestMapping(value = "/api", produces = {MediaType.TEXT_HTML_VALUE})
    public String apiDocs(){
        return "forward:/api.html";
    }
}
