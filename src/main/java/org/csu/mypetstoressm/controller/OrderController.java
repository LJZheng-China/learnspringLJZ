package org.csu.mypetstoressm.controller;

import org.csu.mypetstoressm.domain.Order;
import org.csu.mypetstoressm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionScope
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private Order order;

    private static final String CONFIRM_ORDER = "order/confirm_order";
    private static final String LIST_ORDERS = "order/list_orders";
    private static final String NEW_ORDER = "order/new_order_form";
    private static final String SHIPPING = "order/shipping_form";
    private static final String VIEW_ORDER = "order/view_order";

    @GetMapping("/listOrders")
    public String listOrders(Model model) {
//        HttpSession session = context.getRequest().getSession();
//        AccountActionBean accountBean = (AccountActionBean) session.getAttribute("/actions/Account.action");
//        orderList = orderService.getOrdersByUsername(accountBean.getAccount().getUsername());
        return LIST_ORDERS;
    }

    @PostMapping("/newOrderForm")
    public String newOrderForm(HttpServletRequest request, Model model) {
//        HttpSession session = context.getRequest().getSession();
//        AccountActionBean accountBean = (AccountActionBean) session.getAttribute("/actions/Account.action");
//        CartActionBean cartBean = (CartActionBean) session.getAttribute("/actions/Cart.action");

//        clear();
//        if (accountBean == null || !accountBean.isAuthenticated()) {
//            setMessage("You must sign on before attempting to check out.  Please sign on and try checking out again.");
//            return new ForwardResolution(AccountActionBean.class);
//        } else if (cartBean != null) {
//            order.initOrder(accountBean.getAccount(), cartBean.getCart());
//            return new ForwardResolution(NEW_ORDER);
//        } else {
//            setMessage("An order could not be created because a cart could not be found.");
//            return new ForwardResolution(ERROR);
//        }
        
        return "";
    }

    public String newOrder() {
//        HttpSession session = context.getRequest().getSession();
//
//        if (shippingAddressRequired) {
//            shippingAddressRequired = false;
//            return new ForwardResolution(SHIPPING);
//        } else if (!isConfirmed()) {
//            return new ForwardResolution(CONFIRM_ORDER);
//        } else if (getOrder() != null) {
//
//            orderService.insertOrder(order);
//
//            CartActionBean cartBean = (CartActionBean) session.getAttribute("/actions/Cart.action");
//            cartBean.clear();
//
//            setMessage("Thank you, your order has been submitted.");
//
//            return new ForwardResolution(VIEW_ORDER);
//        } else {
//            setMessage("An error occurred processing your order (order was null).");
//            return new ForwardResolution(ERROR);
//        }
        return "";
    }

    public String viewOrder() {
//        HttpSession session = context.getRequest().getSession();
//
//        AccountActionBean accountBean = (AccountActionBean) session.getAttribute("accountBean");
//
//        order = orderService.getOrder(order.getOrderId());
//
//        if (accountBean.getAccount().getUsername().equals(order.getUsername())) {
//            return new ForwardResolution(VIEW_ORDER);
//        } else {
//            order = null;
//            setMessage("You may only view your own orders.");
//            return new ForwardResolution(ERROR);
//        }
        return "";
    }
}
