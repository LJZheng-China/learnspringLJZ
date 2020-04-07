package org.csu.mypetstoressm.controller;

import org.csu.mypetstoressm.domain.Account;
import org.csu.mypetstoressm.domain.Cart;
import org.csu.mypetstoressm.domain.Order;
import org.csu.mypetstoressm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@SessionScope
@SessionAttributes({"cart", "order", "account"})
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private Order order;

    private static final String CONFIRM_ORDER = "order/confirm_orders";
    private static final String LIST_ORDERS = "order/list_orders";
    private static final String NEW_ORDER = "order/new_order_form";
    private static final String SHIPPING = "order/shipping_form";
    private static final String VIEW_ORDER = "order/view_order";
    private static final String ERROR = "common/error";

    @GetMapping("/listOrders")
    public String listOrders(Model model) {
        Account account = (Account) model.getAttribute("account");
        if(account != null) {
            List<Order> orderList = orderService.getOrdersByUsername(account.getUsername());
            model.addAttribute("orderList",orderList);
        }
        return LIST_ORDERS;
    }

    @GetMapping("/newOrderForm")
    public String newOrderForm(Model model, HttpSession session) {
        Account account = (Account)session.getAttribute("account");
        Cart cart = (Cart)model.getAttribute("cart");

        if (account == null) {
            model.addAttribute("message", "You must sign on before attempting to check out.  Please sign on and try checking out again.");
            return "account/signon";
        } else if(cart != null) {
            order.initOrder(account, cart);
            model.addAttribute("order", order);
            return NEW_ORDER;
        }else{
            model.addAttribute("message", "An order could not be created because a cart could not be found.");
            return ERROR;
        }
    }

    @PostMapping("/newOrder")
    public String newOrder(@ModelAttribute(value = "order") Order order, HttpServletRequest request, Model model) {
        String shippingAddressRequired = request.getParameter("shippingAddressRequired");
        if (shippingAddressRequired != null) {
            return SHIPPING;
        } else {
            return CONFIRM_ORDER;
        }
    }

    @PostMapping("/shippingAddress")
    public String updateAddress(@ModelAttribute(value = "order") Order order, Model model){
        model.addAttribute("order", order);
        return CONFIRM_ORDER;
    }

    @GetMapping("/viewOrder")
    public String viewOrder(Model model) {
        model.addAttribute("lineItem", order.getLineItems());
        String msg;

        if (order != null) {
            orderService.insertOrder(order);
            model.addAttribute("cart",null);
            msg = "Thank you, your order has been submitted.";
            model.addAttribute("message",msg);
            return VIEW_ORDER;
        } else {
            msg = "An error occurred processing your order (order was null).";
            model.addAttribute("message",msg);
            return ERROR;
        }
    }
}
