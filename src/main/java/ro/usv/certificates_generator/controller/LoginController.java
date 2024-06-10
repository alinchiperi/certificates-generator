package ro.usv.certificates_generator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    /**
     *  Login page
     * @return login pageS
     */
    @GetMapping("/login/google")
    public String loginGoogle() {
        return "login-google";
    }

}
