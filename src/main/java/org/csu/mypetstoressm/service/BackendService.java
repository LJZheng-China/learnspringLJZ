package org.csu.mypetstoressm.service;

import org.csu.mypetstoressm.domain.Account;
import org.csu.mypetstoressm.domain.Administrator;
import org.csu.mypetstoressm.domain.Category;
import org.csu.mypetstoressm.persistence.AccountMapper;
import org.csu.mypetstoressm.persistence.AdministratorMapper;
import org.csu.mypetstoressm.persistence.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BackendService {
    @Autowired
    AdministratorMapper administratorMapper;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    CategoryMapper categoryMapper;

    public boolean login(Administrator administrator) {
        return administratorMapper.getAdministratorByUsernameAndPassword(administrator).length == 1;
    }

    public List<Account> getAccountList() {
        return accountMapper.getAccountList();
    }

    public List<Category> getCategoryList() {
        return categoryMapper.getCategoryList();
    }


}
