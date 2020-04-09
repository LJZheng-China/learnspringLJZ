package org.csu.mypetstoressm.domain;

import java.math.BigDecimal;

public class CartItem {
    private Item item;
    private int quantity;
    private boolean inStock;
    private BigDecimal total;

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
        calculateTotal();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateTotal();
    }

    public void incrementQuantity() {
        quantity++;
        calculateTotal();
    }

    private void calculateTotal() {
        if (item != null && item.getListPrice() != null) {
            total = item.getListPrice().multiply(new BigDecimal(quantity));
        } else {
            total = null;
        }
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "item=" + item +
                ", quantity=" + quantity +
                ", inStock=" + inStock +
                ", total=" + total +
                '}';
    }
}
