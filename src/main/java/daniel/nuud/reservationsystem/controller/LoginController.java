package daniel.nuud.reservationsystem.controller;

import daniel.nuud.reservationsystem.dto.AuthRequest;
import daniel.nuud.reservationsystem.util.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(
            @RequestParam(name = "loginRequired", required = false) final Boolean loginRequired,
            @RequestParam(name = "loginError", required = false) final Boolean loginError,
            final Model model) {

        model.addAttribute("authentication", new AuthRequest());
        if (loginRequired == Boolean.TRUE) {
            model.addAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("authentication.login.required"));
        }
        if (loginError == Boolean.TRUE) {
            model.addAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("authentication.login.error"));
        }
        return "authentication/login";
    }

}

