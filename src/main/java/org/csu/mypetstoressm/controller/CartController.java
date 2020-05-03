package org.csu.mypetstoressm.controller;

import org.csu.mypetstoressm.domain.Account;
import org.csu.mypetstoressm.domain.Cart;
import org.csu.mypetstoressm.domain.CartItem;
import org.csu.mypetstoressm.domain.Item;
import org.csu.mypetstoressm.service.CartService;
import org.csu.mypetstoressm.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;

@Controller
@SessionAttributes({"cart", "account"})
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private CartService cartService;

    @GetMapping("/viewCart")
    public String viewCart(Model model, HttpSession session){
        Cart cart = (Cart) session.getAttribute("cart");
        Account account = (Account) session.getAttribute("account");
        if (account != null) {
            cart = cartService.getCart(account);
        } else if (cart == null) {
            cart = new Cart();
        }

        model.addAttribute("cart", cart);
        session.setAttribute("cart", cart);


        System.out.println("viewCart: "+model.getAttribute("cart"));
        System.out.println("viewCart: "+model.getAttribute("account"));
        return "cart/cart";
    }

    @GetMapping("/addItemToCart")
    public String addItemToCart(String workingItemId, Model model, HttpSession session){
        Cart cart = (Cart) session.getAttribute("cart");
        Account account = (Account) session.getAttribute("account");

        if (cart == null) {
            cart = new Cart();
        }

        if(cart.containsItemId(workingItemId)){
            cart.incrementQuantityByItemId(workingItemId);
        } else {
            boolean isInStock = catalogService.isItemInStock(workingItemId);
            Item item = catalogService.getItem(workingItemId);
            cart.addItem(item,isInStock);
        }

        if (account != null) {
            cartService.setCart(account, cart);
        }

        model.addAttribute("cart",cart);
        return "cart/cart";
    }

    @GetMapping("/removeItemFromCart")
    public String removeItemFromCart(String workingItemId, Model model, HttpSession session){
        Cart cart = (Cart) session.getAttribute("cart");
        Account account = (Account) session.getAttribute("account");
        Item item = cart.removeItemById(workingItemId);

        model.addAttribute("cart",cart);
        System.out.println("removeItemFromCart" + model.getAttribute("cart"));

        if (item == null) {
            model.addAttribute("message", "Attempted to remove null CartItem from Cart.");
//            return "common/error";
        } else {
            if (account != null) {
                cartService.setCart(account, cart);
            }

            session.setAttribute("cart",cart);
            model.addAttribute("cart", cart);

            return "cart/cart";
        }
        return "cart/cart";
    }

    @PostMapping("/updateCartQuantities")
    public String updateCartQuantities(HttpServletRequest request, Model model, HttpSession session){
        Cart cart = (Cart) session.getAttribute("cart");
        Account account = (Account) session.getAttribute("account");

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

        if (account != null) {
            cartService.setCart(account, cart);
        }
        session.setAttribute("cart", cart);
        model.addAttribute("cart",cart);

        System.out.println("updateCartQuantities" + model.getAttribute("cart"));
        return "cart/cart";
    }

    @GetMapping("/checkOut")
    public String checkOut(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("cart", cart);
        return "cart/checkout";
    }
}
