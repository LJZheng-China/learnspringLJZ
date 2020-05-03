package org.csu.mypetstoressm;

import org.csu.mypetstoressm.domain.Account;
import org.csu.mypetstoressm.domain.Cart;
import org.csu.mypetstoressm.domain.Item;
import org.csu.mypetstoressm.service.CartService;
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

    @Autowired
    CartService cartService;


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

        System.out.println("getItemListByProduct: " + catalogService.getItemListByProduct("K9-BD-01"));

        System.out.println("getItem: " + catalogService.getItem("EST-6"));

        System.out.println("isItemInStock: " + catalogService.isItemInStock("EST-6"));

    }

    @Test
    void testGCart() {
        Account account = new Account();
        account.setUsername("kk");
        Cart cart = cartService.getCart(account);
        System.out.println(cart);

        Item item = catalogService.getItem("EST-20");

        cart.addItem(item, true);
        cart.setQuantityByItemId("EST-20", 3);

        item = catalogService.getItem("EST-21");
        cart.addItem(item, true);
        cart.setQuantityByItemId("EST-21", 2);

        cartService.setCart(account, cart);
    }




}
