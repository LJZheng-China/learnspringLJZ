package org.csu.mypetstoressm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.csu.mypetstoressm.persistence")
public class MypetstoressmApplication {

    public static void main(String[] args) {
        SpringApplication.run(MypetstoressmApplication.class, args);
    }

}
