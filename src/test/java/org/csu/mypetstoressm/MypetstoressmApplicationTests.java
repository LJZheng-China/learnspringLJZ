package org.csu.mypetstoressm;

import org.csu.mypetstoressm.service.CatalogService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("org.csu.mypetstoressm.persistence")
class MypetstoressmApplicationTests {

    @Autowired
    CatalogService catalogService;

    @Test
    void contextLoads() {
    }

    @Test
    void testCatalog() {
        System.out.println("getCategory: " + catalogService.getCategory("BIRDS"));

        System.out.println("getCategoryList: " + catalogService.getCategoryList());

        System.out.println("getProduct: " + catalogService.getProduct("FL-DLH-02"));

        System.out.println("getProductListByCategory: " + catalogService.getProductListByCategory("FISH"));

        System.out.println("searchProductList: " + catalogService.searchProductList("e"));

    }


}
