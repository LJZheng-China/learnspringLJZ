package org.csu.mypetstoressm.persistence;

import org.csu.mypetstoressm.domain.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountMapper {
    Account getAccountByUsername(String username);

    Account getAccountByUsernameAndPassword(Account account);

    void insertAccount(Account account);

    void insertProfile(Account account);

    void insertSignon(Account account);

    void updateAccount(Account account);

    void updateProfile(Account account);

    void updateSignon(Account account);

    List<Account> getAccountList();
}
