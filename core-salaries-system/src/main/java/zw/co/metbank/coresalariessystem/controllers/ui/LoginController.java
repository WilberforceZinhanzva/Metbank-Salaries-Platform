package zw.co.metbank.coresalariessystem.controllers.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/v1/login")
public class LoginController {


    @GetMapping
    public ModelAndView loginPage(){
        ModelAndView mv = new ModelAndView("/login");
        return mv;
    }
}
