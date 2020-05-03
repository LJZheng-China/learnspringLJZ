package org.csu.mypetstoressm.controller;

import org.csu.mypetstoressm.domain.Account;
import org.csu.mypetstoressm.domain.Product;
import org.csu.mypetstoressm.service.AccountService;
import org.csu.mypetstoressm.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/account/")
@SessionAttributes({"account", "myList", "authenticated", "cart"})
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CatalogService catalogService;

    private static final List<String> LANGUAGE_LIST;
    private static final List<String> CATEGORY_LIST;

    static {
        List<String> langList = new ArrayList<>();
        langList.add("ENGLISH");
        langList.add("CHINESE");
        LANGUAGE_LIST = Collections.unmodifiableList(langList);

        List<String> catList = new ArrayList<>();
        catList.add("FISH");
        catList.add("DOGS");
        catList.add("REPTILES");
        catList.add("CATS");
        catList.add("BIRDS");

        CATEGORY_LIST = Collections.unmodifiableList(catList);
    }
    
    private static final String SINGON = "account/signon";
    private static final String EDIT_ACCOUNT = "account/edit_account";

    @GetMapping("signonForm")
    public String signonForm() {
        return SINGON;
    }

    @PostMapping("signon")
    public String signon(String username, String password, String verification, Model model, HttpSession session) {
        String verifyCode = (String) session.getAttribute("verifyCode");

        //equalsIgnoreCase: 不考虑大小写
        if (verifyCode==null || !verifyCode.equalsIgnoreCase(verification)) {
            if (verification == null || verification == "") {
                model.addAttribute("msg", "请输入验证码!");
            } else {
                model.addAttribute("msg", "验证码输入错误!");
            }
            return SINGON;
        }

        Account account = accountService.getAccount(username, password);

        if (account == null) {
            String msg = "Invalid username or password.  Signon failed.";
            model.addAttribute("msg", msg);
            return SINGON;
        } else {
            account.setPassword(null);
            List<Product> myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
            model.addAttribute("account", account);
            model.addAttribute("myList", myList);
            model.addAttribute("authenticated", true);
            return "catalog/main";
        }
    }

    @GetMapping("signoff")
    public String signoff(Model model, HttpSession session) {
        model.addAttribute("account", null);
        model.addAttribute("myList", null);
        model.addAttribute("authenticated", false);
        session.setAttribute("account", null);
        session.setAttribute("cart", null);
        System.out.println("signoff"+ session.getAttribute("account"));
        return "catalog/main";
    }

    @GetMapping("editAccountForm")
    public String editAccountForm(@SessionAttribute("account") Account account , Model model) {
        model.addAttribute("account", account);
        model.addAttribute("LANGUAGE_LIST", LANGUAGE_LIST);
        model.addAttribute("CATEGORY_LIST", CATEGORY_LIST);
        return EDIT_ACCOUNT;
    }

    @PostMapping("editAccount")
    public String editAccount(Account account, String repeatedPassword, Model model) {
        if (account.getPassword() == null || account.getPassword().length() == 0 || repeatedPassword == null || repeatedPassword.length() == 0) {
            String msg = "密码不能为空";
            model.addAttribute("msg", msg);
            return EDIT_ACCOUNT;
        } else if (!account.getPassword().equals(repeatedPassword)) {
            String msg = "两次密码不一致";
            model.addAttribute("msg", msg);
            return EDIT_ACCOUNT;
        } else {
            accountService.updateAccount(account);
            account = accountService.getAccount(account.getUsername());
            List<Product> myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
            boolean authenticated = true;
            model.addAttribute("account", account);
            model.addAttribute("myList", myList);
            model.addAttribute("authenticated", authenticated);
            return "redirect:/catalog/index";
        }
    }

    @GetMapping("newAccountForm")
    public String newAccountForm(Model model){
        model.addAttribute("newAccount",new Account());
        model.addAttribute("LANGUAGE_LIST", LANGUAGE_LIST);
        model.addAttribute("CATEGORY_LIST", CATEGORY_LIST);
        return "account/new_account";
    }

    @PostMapping("newAccount")
    public String newAccount(@ModelAttribute(value = "newAccount") Account account, Model model){
        if(account.getUsername()==null||account.getUsername().trim().length()==0||
                account.getPassword()==null||account.getPassword().trim().length()==0){
            String msg = "用户名或密码不能为空";
            model.addAttribute("msg",msg);
            return "account/new_account";
        }
        else {
            accountService.insertAccount(account);
//            model.addAttribute("account", account);
            return "catalog/main";
        }
    }
}
