package security.com.securityjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class OAuth2Controller {

    @GetMapping("/oauth2")
    @ResponseBody
    public String oauth2Login() {
        return "OAuth2 Login successful";
    }
}
