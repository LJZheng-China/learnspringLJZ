package org.csu.mypetstoressm.persistence;

import org.apache.ibatis.annotations.Param;
import org.csu.mypetstoressm.domain.Account;
import org.csu.mypetstoressm.domain.Cart;
import org.csu.mypetstoressm.domain.CartItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemMapper {
    List<CartItem> getCartItemListByAccount(Account account);

    void deleteCartItemListByAccount(Account account);

    void insertCartItemByAccount(@Param("account") Account account, @Param("cartItem") CartItem cartItem);
}
