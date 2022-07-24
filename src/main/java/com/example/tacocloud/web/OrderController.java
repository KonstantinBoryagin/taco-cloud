package com.example.tacocloud.web;

import com.example.tacocloud.data.OrderRepository;
import com.example.tacocloud.tacos.TacoOrder;
import com.example.tacocloud.tacos.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private OrderRepository orderRepo;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepo = orderRepository;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping()
    public String processOrder(@Valid TacoOrder tacoOrder, Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {
        if(errors.hasErrors()) {
            return "orderForm";
        }
//        log.info("Order submitted: {}", tacoOrder);

        tacoOrder.setUser(user);

        orderRepo.save(tacoOrder);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
