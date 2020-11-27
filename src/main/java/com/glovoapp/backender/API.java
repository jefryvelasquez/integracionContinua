package com.glovoapp.backender;

import com.glovoapp.backender.domain.Courier;
import com.glovoapp.backender.dto.OrderVM;
import com.glovoapp.backender.service.courier.CourierService;
import com.glovoapp.backender.service.order.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ComponentScan("com.glovoapp.backender")
@EnableAutoConfiguration
public class API {

    private final String welcomeMessage;
    private final CourierService courierService;
    private final OrderService orderService;

    API(@Value("${backender.welcome_message}") String welcomeMessage,
        CourierService courierService,
        OrderService orderService) {
        this.welcomeMessage = welcomeMessage;
        this.courierService = courierService;
        this.orderService = orderService;
    }

    public static void main(String[] args) {
        SpringApplication.run(API.class);
    }

    @GetMapping("/")
    @ResponseBody
    public String root() {
        return welcomeMessage;
    }

    @GetMapping("/orders")
    @ResponseBody
    List<OrderVM> orders() {
        return orderService.findAll();
    }

    @GetMapping("/orders/{courierId}")
    @ResponseBody
    List<OrderVM> ordersByCourier(@PathVariable String courierId) {
        Courier courier = courierService.findBy(courierId);
        return orderService.findBy(courier);
    }
}
