package org.csu.mypetstoressm.service;

import org.csu.mypetstoressm.domain.Account;
import org.csu.mypetstoressm.domain.Cart;
import org.csu.mypetstoressm.domain.CartItem;
import org.csu.mypetstoressm.persistence.CartItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class CartService {

    @Autowired
    CartItemMapper cartItemMapper;

    public Cart getCart(Account account) {
        Cart cart = new Cart();

        if (account != null) {
            CartItem cartItem;
            Iterator<CartItem> cartItemIterator;
            cartItemIterator = cartItemMapper.getCartItemListByAccount(account).iterator();// cartItemDAO.getCartItemListByAccount(account).iterator();
            while (cartItemIterator.hasNext()) {
                cartItem = cartItemIterator.next();
                cart.addItem(cartItem.getItem(), cartItem.isInStock());
            }
        }

        return cart;
    }

    public void setCart(Account account, Cart cart) {
        cartItemMapper.deleteCartItemListByAccount(account);

        Iterator<CartItem> cartItemIterator = cart.getCartItems();
        while (cartItemIterator.hasNext()) {
            CartItem cartItem = cartItemIterator.next();
            cartItemMapper.insertCartItemByAccount(account, cartItem);
        }

    }
}
