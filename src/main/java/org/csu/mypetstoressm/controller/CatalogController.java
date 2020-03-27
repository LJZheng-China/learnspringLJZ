package org.csu.mypetstoressm.controller;

import org.csu.mypetstoressm.domain.Category;
import org.csu.mypetstoressm.domain.Item;
import org.csu.mypetstoressm.domain.Product;
import org.csu.mypetstoressm.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    private static final String MAIN = "catalog/main";
    private static final String VIEW_CATEGORY = "catalog/category";
    private static final String VIEW_PRODUCT = "catalog/product";
    private static final String VIEW_ITEM = "catalog/item";
    private static final String SEARCH_PRODUCTS = "catalog/searchProducts";

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/viewMain")
    public String viewMain(){
        return MAIN;
    }

    @GetMapping("/viewCategory")
    public String viewCategory(String categoryId, Model model) {
        if(categoryId != null) {
            Category category = catalogService.getCategory(categoryId);
            List<Product> productList = catalogService.getProductListByCategory(categoryId);
            model.addAttribute("category", category);
            model.addAttribute("productList", productList);
            return VIEW_CATEGORY;
        } else {
            return MAIN;
        }
    }

    public String viewProduct(String productId, Model model) {
        if (productId != null) {
            List<Item> itemList = catalogService.getItemListByProduct(productId);
            Product product = catalogService.getProduct(productId);
        }
        return VIEW_PRODUCT;
    }
}
