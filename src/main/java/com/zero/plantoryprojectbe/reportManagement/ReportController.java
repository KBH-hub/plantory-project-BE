package com.zero.plantoryprojectbe.reportManagement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReportController {

    @RequestMapping("/admin")
    public String admin(){
        return "admin/reportManagement";
    }
}
