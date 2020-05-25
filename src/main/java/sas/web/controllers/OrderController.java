package sas.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sas.service.services.OrderService;
import sas.web.models.OrderViewModel;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final ModelMapper mapper;

    public OrderController(OrderService orderService, ModelMapper mapper) {
        this.orderService = orderService;
        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/my-orders")
    ModelAndView getUserOrders(ModelAndView modelAndView, HttpSession session) {
        List<OrderViewModel> orders = this.orderService.getUserOrders(session).stream().map(order -> this.mapper.map(order, OrderViewModel.class)).sorted((o1, o2) -> {
            if (o1.getCreatedOn().isAfter(o2.getCreatedOn())) {
                return -1;
            } else if (o1.getCreatedOn().isBefore(o2.getCreatedOn())) {
                return 1;
            }
            return 0;
        }).collect(Collectors.toList());
        modelAndView.addObject("orders", orders);
        modelAndView.setViewName("/order/my-orders");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/create-order")
    String createOrderForUser(HttpSession session) {
        this.orderService.createOrder(session);
        return "redirect:/my-orders";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/order-products")
    ModelAndView getOrderProductsConfirm(@RequestParam(name = "orderId") String orderId, ModelAndView modelAndView) {
        OrderViewModel order = this.mapper.map(this.orderService.getOrderById(orderId), OrderViewModel.class);
        modelAndView.addObject("products", order.getProducts());
        modelAndView.setViewName("/order/order-products");
        return modelAndView;
    }
}
