package org.csu.mypetstoressm.controller;

import org.csu.mypetstoressm.domain.Administrator;
import org.csu.mypetstoressm.service.BackendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/CMS")
@SessionAttributes({"administrator"})
public class BackendController {

    public static final String CMS_LOGIN_FORM = "CMS/CMS_login_form";
    public static final String CMS_MAIN = "CMS/CMS_main";

    @Autowired
    BackendService backendService;

    @Autowired
    HttpSession session;

    @RequestMapping("/loginForm")
    public String loginForm(Model model) {
        model.addAttribute("msg", "");
        return CMS_LOGIN_FORM;
    }

    @PostMapping("/login")
    public String login(String username, String password, Model model) {
        Administrator administrator = new Administrator();
        administrator.setUsername(username);
        administrator.setPassword(password);

        if (backendService.login(administrator)) {
            session.setAttribute("administrator", administrator);
            return CMS_MAIN;
        } else {
            model.addAttribute("msg", "账号或密码错误");
            return CMS_LOGIN_FORM;
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("msg", "");
        return CMS_LOGIN_FORM;
    }

    @RequestMapping("/logout")
    public String logout() {
        session.setAttribute("administrator", null);
        return CMS_LOGIN_FORM;
    }






}
