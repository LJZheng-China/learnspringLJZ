package org.csu.mypetstoressm.service;

import org.csu.mypetstoressm.domain.Item;
import org.csu.mypetstoressm.domain.LineItem;
import org.csu.mypetstoressm.domain.Order;
import org.csu.mypetstoressm.domain.Sequence;
import org.csu.mypetstoressm.persistence.ItemMapper;
import org.csu.mypetstoressm.persistence.LineItemMapper;
import org.csu.mypetstoressm.persistence.OrderMapper;
import org.csu.mypetstoressm.persistence.SequenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SequenceMapper sequenceMapper;
    @Autowired
    private LineItemMapper lineItemMapper;

    /*
    声明式事务处理
     */
    @Transactional
    public void insertOrder(Order order) {
        order.setOrderId(this.getNextId("ordernum"));

        int i;
        LineItem lineItem;
        for(i = 0; i < order.getLineItems().size(); ++i) {
            lineItem = (LineItem)order.getLineItems().get(i);
            String itemId = lineItem.getItemId();
            Integer increment = new Integer(lineItem.getQuantity());
            Map<String, Object> param = new HashMap(2);
            param.put("itemId", itemId);
            param.put("increment", increment);
            itemMapper.updateInventoryQuantity(param);
        }

        orderMapper.insertOrder(order);
        orderMapper.insertOrderStatus(order);

        for(i = 0; i < order.getLineItems().size(); ++i) {
            lineItem = (LineItem)order.getLineItems().get(i);
            lineItem.setOrderId(order.getOrderId());
            lineItemMapper.insertLineItem(lineItem);
        }

    }

    @Transactional
    public Order getOrder(int orderId) {
        Order order = orderMapper.getOrder(orderId);
        order.setLineItems(lineItemMapper.getLineItemsByOrderId(orderId));

        for(int i = 0; i < order.getLineItems().size(); ++i) {
            LineItem lineItem = (LineItem)order.getLineItems().get(i);
            Item item = itemMapper.getItem(lineItem.getItemId());
            item.setQuantity(itemMapper.getInventoryQuantity(lineItem.getItemId()));
            lineItem.setItem(item);
        }

        return order;
    }

    public List<Order> getOrdersByUsername(String username) {
        return orderMapper.getOrdersByUsername(username);
    }

    public int getNextId(String name) {
        Sequence sequence = new Sequence(name, -1);
        sequence = this.sequenceMapper.getSequence(sequence);
        if (sequence == null) {
            throw new RuntimeException("Error: A null sequence was returned from the database (could not get next " + name + " sequence).");
        } else {
            Sequence parameterObject = new Sequence(name, sequence.getNextId() + 1);
            sequenceMapper.updateSequence(parameterObject);
            return sequence.getNextId();
        }
    }
}
