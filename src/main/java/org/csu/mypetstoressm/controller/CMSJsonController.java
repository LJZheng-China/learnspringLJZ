package org.csu.mypetstoressm.controller;

import org.csu.mypetstoressm.domain.Account;
import org.csu.mypetstoressm.domain.Category;
import org.csu.mypetstoressm.service.BackendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/CMSJson")
public class CMSJsonController {

    @Autowired
    BackendService backendService;

    @RequestMapping("/accountlist")
    public List<Account> getAccountList() {
        return backendService.getAccountList();
    }

    @RequestMapping("/category")
    public List<Category> getCategoryList() {
        return backendService.getCategoryList();
    }
}
