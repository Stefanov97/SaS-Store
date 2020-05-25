package sas.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import sas.data.models.Order;
import sas.data.models.OrderProduct;
import sas.data.models.User;
import sas.data.repositories.OrderRepository;
import sas.data.repositories.ProductRepository;
import sas.data.repositories.UserRepository;
import sas.service.models.OrderServiceModel;
import sas.service.services.OrderService;
import sas.service.services.ShoppingCartService;
import sas.web.models.ProductCartModel;
import sas.web.models.UserLoginModel;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ShoppingCartService shoppingCartService;
    private final ModelMapper mapper;


    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, ShoppingCartService shoppingCartService, ModelMapper mapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;

        this.productRepository = productRepository;
        this.shoppingCartService = shoppingCartService;
        this.mapper = mapper;
    }

    @Override
    public void createOrder(HttpSession session) {
        String username = ((UserLoginModel) session.getAttribute("user")).getUsername();
        if (username != null) {
            Optional<User> optionalUser = this.userRepository.findByUsername(username);

            List<ProductCartModel> productsFromCart = this.shoppingCartService.getProducts(session);
            List<OrderProduct> products = new ArrayList<>();
            final double[] totalPrice = {0};
            productsFromCart.forEach(p -> {
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setProduct(this.productRepository.findByModel(p.getProduct().getModel()));
                orderProduct.setQuantity(p.getQuantity());
                products.add(orderProduct);
                totalPrice[0] += p.getProduct().getPrice().doubleValue() * p.getQuantity();
            });

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Order order = new Order();
                order.setCustomer(user);
                order.setTotalPrice(BigDecimal.valueOf(totalPrice[0]));
                order.setCreatedOn(LocalDateTime.now());
                order.setProducts(products);
                order.setOrderNumber((int) this.orderRepository.count() + 1);
                this.orderRepository.saveAndFlush(order);
                session.removeAttribute("products");
            }
        }
    }

    @Override
    public List<OrderServiceModel> getUserOrders(HttpSession session) {
        String username = ((UserLoginModel) session.getAttribute("user")).getUsername();
        List<Order> orders = this.orderRepository.findAllByCustomerUsername(username);
        return orders.stream().map(order -> this.mapper.map(order, OrderServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public OrderServiceModel getOrderById(String orderId) {
        return this.mapper.map(this.orderRepository.getOne(orderId), OrderServiceModel.class);
    }
}
