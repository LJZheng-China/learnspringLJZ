package org.csu.mypetstoressm.service;

import org.csu.mypetstoressm.domain.*;
import org.csu.mypetstoressm.persistence.*;
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
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ItemMapper itemMapper;
    @Autowired
    OrderMapper orderMapper;

    public boolean login(Administrator administrator) {
        return administratorMapper.getAdministratorByUsernameAndPassword(administrator).length == 1;
    }

    public List<Account> getAccountList() {
        return accountMapper.getAccountList();
    }

    public List<Category> getCategoryList() {
        return categoryMapper.getCategoryList();
    }

    public List<Product> getProductList() {
        return productMapper.getProductList();
    }

    public List<Product> getProductListByCategoryId(String categoryId) {
        return productMapper.getProductListByCategory(categoryId);
    }

    public List<Item> getItemList() {
        return itemMapper.getItemList();
    }

    public List<Item> getItemListByProductId(String productId) {
        return itemMapper.getItemListByProduct(productId);
    }

    public List<Order> getOrderListByUsername(String username) {
        return orderMapper.getOrdersByUsername(username);
    }

    public List<Order> getOrderList() {
        return orderMapper.getOrders();
    }
}
