package com.project.code.Controller;

import com.project.code.Model.*;
import com.project.code.Service.OrderService;
import com.project.code.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Map<String, String> addStore(@RequestBody Store store) {
        Store savedStore = storeRepository.save(store);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Mağaza başarıyla oluşturuldu. ID: " + savedStore.getId());
        return response;
    }

    @GetMapping("validate/{storeId}")
    public boolean validateStore(@PathVariable Long storeId) {
        return storeRepository.existsById(storeId);
    }

    @PostMapping("/placeOrder")
    public Map<String, String> placeOrder(@RequestBody PlaceOrderRequestDTO placeOrderRequest) {
        Map<String, String> response = new HashMap<>();
        try {
            orderService.saveOrder(placeOrderRequest);
            response.put("message", "Sipariş başarıyla verildi");
        } catch (Exception e) {
            response.put("Error", e.getMessage());
        }
        return response;
    }
}