package org.csu.mypetstoressm.controller;

import org.csu.mypetstoressm.domain.Cart;
import org.csu.mypetstoressm.domain.CartItem;
import org.csu.mypetstoressm.domain.Item;
import org.csu.mypetstoressm.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

@Controller
@SessionAttributes({"cart", "account"})
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private Cart cart;

    @GetMapping("/viewCart")
    public String viewCart(Model model){
        if(model.getAttribute("cart") == null) {
            model.addAttribute("cart",cart);
        }
        System.out.println("viewCart: "+model.getAttribute("cart"));
        System.out.println("viewCart: "+model.getAttribute("account"));
        return "cart/cart";
    }

    @GetMapping("/addItemToCart")
    public String addItemToCart(String workingItemId, Model model){
        if(cart.containsItemId(workingItemId)){
            cart.incrementQuantityByItemId(workingItemId);
        }else{
            boolean isInStock = catalogService.isItemInStock(workingItemId);
            Item item = catalogService.getItem(workingItemId);
            cart.addItem(item,isInStock);
        }
        model.addAttribute("cart",cart);
        return "cart/cart";
    }

    @GetMapping("/removeItemFromCart")
    public String removeItemFromCart(String workingItemId, Model model){
        Item item = cart.removeItemById(workingItemId);
        model.addAttribute("cart",cart);
        System.out.println("removeItemFromCart" + model.getAttribute("cart"));
        if(item == null){
            model.addAttribute("message", "Attempted to remove null CartItem from Cart.");
            return "common/error";
        }else{
            return "cart/cart";
        }
    }

    @PostMapping("/updateCartQuantities")
    public String updateCartQuantities(HttpServletRequest request, Model model){
        Iterator<CartItem> cartItems = cart.getAllCartItems();
        while (cartItems.hasNext()){
            CartItem cartItem = cartItems.next();
            String itemId = cartItem.getItem().getItemId();
            try{
                int quantity = Integer.parseInt(request.getParameter(itemId));
                cart.setQuantityByItemId(itemId,quantity);
                if(quantity < 1){
                    cartItems.remove();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        model.addAttribute("cart",cart);

        System.out.println("updateCartQuantities" + model.getAttribute("cart"));
        return "cart/cart";
    }

    @GetMapping("/checkOut")
    public String checkOut(Model model) {
        model.addAttribute("cart",cart);
        return "cart/checkout";
    }
}