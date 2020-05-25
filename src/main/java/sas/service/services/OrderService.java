package sas.service.services;

import sas.service.models.OrderServiceModel;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface OrderService {

    void createOrder(HttpSession session);

    List<OrderServiceModel> getUserOrders(HttpSession session);

    OrderServiceModel getOrderById(String orderId);
}
