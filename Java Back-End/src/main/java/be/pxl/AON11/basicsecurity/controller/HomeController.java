package be.pxl.AON11.basicsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Ebert Joris on 24/03/2018.
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/assessment")
    @ResponseBody
    public String index() {
        return "index";
    }

}
