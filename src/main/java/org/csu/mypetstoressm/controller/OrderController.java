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
import java.util.Date;
import java.util.List;
import java.util.Map;

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
//        HttpSession session = context.getRequest().getSession();
//        AccountActionBean accountBean = (AccountActionBean) session.getAttribute("/actions/Account.action");
//        orderList = orderService.getOrdersByUsername(accountBean.getAccount().getUsername());

        Account account = (Account) model.getAttribute("account");
        if(account != null) {
            List<Order> orderList = orderService.getOrdersByUsername(account.getUsername());
            model.addAttribute("orderList",orderList);
        }
        return LIST_ORDERS;
    }

    @GetMapping("/newOrderForm")
    public String newOrderForm(Model model, HttpSession session) {
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

        Account account = (Account)session.getAttribute("account");
        Cart cart = (Cart)model.getAttribute("cart");

        if (account == null) {
            model.addAttribute("message", "You must sign on before attempting to check out.  Please sign on and try checking out again.");
            return "account/signon";
        } else if(cart != null) {
            //Order order = new Order();
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
        //order = (Order) model.getAttribute("order");
        //System.out.println("order: " + order);
        String shippingAddressRequired = request.getParameter("shippingAddressRequired");

        System.out.println(order);
        if (shippingAddressRequired != null) {
            return SHIPPING;
        } else {
            return CONFIRM_ORDER;
        }
    }

    @PostMapping("/shippingAddress")
    public String updateAddress(@ModelAttribute(value = "order") Order order, Model model){
//        Order order = (Order) model.getAttribute("order");
//        if(order!=null) {
//            order.setShipToFirstName((String) params.get("shipToFirstName"));
//            order.setShipToLastName((String) params.get("shipToLastName"));
//            order.setShipAddress1((String) params.get("shipAddress1"));
//            order.setShipAddress2((String) params.get("shipAddress2"));
//            order.setShipCity((String) params.get("shipCity"));
//            order.setShipState((String) params.get("shipState"));
//            order.setShipZip((String) params.get("shipZip"));
//            order.setShipCountry((String) params.get("shipCountry"));
//        }
        System.out.println(order);

        model.addAttribute("order", order);
        return CONFIRM_ORDER;
    }

    @GetMapping("/viewOrder")
    public String viewOrder(Model model) {
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

        System.out.println(order);
        model.addAttribute("lineItem", order.getLineItems());
        String msg = "";

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
//        return ERROR;
    }
}
