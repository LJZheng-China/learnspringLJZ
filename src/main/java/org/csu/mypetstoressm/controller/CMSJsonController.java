package org.csu.mypetstoressm.controller;

import org.csu.mypetstoressm.domain.*;
import org.csu.mypetstoressm.service.BackendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/CMSJson")
public class CMSJsonController {

    @Autowired
    BackendService backendService;

    @RequestMapping("/accountList")
    public List<Account> getAccountList() {
        return backendService.getAccountList();
    }

    @RequestMapping("/categoryList")
    public List<Category> getCategoryList() {
        return backendService.getCategoryList();
    }

    @RequestMapping("/productList")
    public List<Product> getProductList(@RequestParam(value = "categoryId", defaultValue = "all") String categoryId) {
        if (categoryId.equals("all")) {
            return backendService.getProductList();
        } else {
            return backendService.getProductListByCategoryId(categoryId);
        }
    }

    @RequestMapping("/itemList")
    public List<Item> getItemList(@RequestParam(value = "productId", defaultValue = "all") String productId) {
        if (productId.equals("all")) {
            return backendService.getItemList();
        } else {
            return backendService.getItemListByProductId(productId);
        }
    }

    @RequestMapping("/orderList")
    public List<Order> getOrderList(@RequestParam(value = "username", required = false) String username) {
        if (username == null) {
            return backendService.getOrderList();
        } else {
            return backendService.getOrderListByUsername(username);
        }
    }

    /**
     * 下架商品 将Item的status改为 N
     * 上架status = P
     * 下架status = N
     */
    @PostMapping("/putDownItem")
    public String putDownItem(String itemId) {
        backendService.putDownItem(itemId);
        return "{'msg': 'ok'}";
    }

    @PostMapping("/putUpItem")
    public String putUpItem(String itemId) {
        backendService.putUpItem(itemId);
        return "{'msg': 'ok'}";
    }

    /**
     * 增加一个商品， 先不用实现
     * @return
     */
    public String addAItem(Item item) {

        return "ok";
    }
}
