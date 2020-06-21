package org.csu.mypetstoressm.persistence;

import org.csu.mypetstoressm.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ItemMapper {
    void updateInventoryQuantity(Map<String, Object> param);

    int getInventoryQuantity(String itemId);

    List<Item> getItemListByProduct(String productId);

    Item getItem(String itemId);

    List<Item> getItemList();

    void putDownItem(String itemId);

    void putUpItem(String itemId);
}
