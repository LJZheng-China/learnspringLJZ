package org.csu.mypetstoressm.service;

import org.csu.mypetstoressm.domain.Category;
import org.csu.mypetstoressm.domain.Item;
import org.csu.mypetstoressm.domain.Product;
import org.csu.mypetstoressm.persistence.CategoryMapper;
import org.csu.mypetstoressm.persistence.ItemMapper;
import org.csu.mypetstoressm.persistence.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CatalogService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ItemMapper itemMapper;

    public List<Category> getCategoryList() {
        return categoryMapper.getCategoryList();
    }

    public Category getCategory(String categoryId){
        return categoryMapper.getCategory(categoryId);
    }

    public Product getProduct(String productId) {
        return productMapper.getProduct(productId);
    }

    public List<Product> getProductListByCategory(String categoryId) {
        return productMapper.getProductListByCategory(categoryId);
    }

    public List<Product> searchProductList(String keyword) {
        return productMapper.searchProductList("%" + keyword.toLowerCase() + "%");
    }

    public List<Item> getItemListByProduct(String productId) {
        List<Item> itemList = itemMapper.getItemListByProduct(productId);
        List<Item> itemList1 = new ArrayList<>();
        for (Item item : itemList) {
            if (item.getStatus().equals("P")) {
                itemList1.add(item);
            }
        }
        return itemList1;
    }

    public Item getItem(String itemId) {
        return itemMapper.getItem(itemId);
    }

    public boolean isItemInStock(String itemId) {
        return itemMapper.getInventoryQuantity(itemId) > 0;
    }
}
