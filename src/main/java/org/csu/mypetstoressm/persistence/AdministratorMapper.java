package org.csu.mypetstoressm.persistence;

import org.csu.mypetstoressm.domain.Administrator;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorMapper {
    Administrator[] getAdministratorByUsernameAndPassword(Administrator administrator);
}
