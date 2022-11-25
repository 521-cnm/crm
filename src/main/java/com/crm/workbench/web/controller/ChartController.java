package com.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChartController {
    @RequestMapping("/workbench/chart/transaction/index")
    public String index(){
        return "workbench/chart/transaction/index";
    }
}
