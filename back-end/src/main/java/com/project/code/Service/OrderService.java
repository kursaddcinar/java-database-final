package com.project.code.Service;

import com.project.code.Model.*;
import com.project.code.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    /**
     * Müşterinin siparişini işler; müşteri kaydı, stok güncelleme ve sipariş detaylarını kaydeder.
     */
    public void saveOrder(PlaceOrderRequestDTO placeOrderRequest) {
        // 1. Müşteriyi Al veya Oluştur
        Customer customer = customerRepository.findByEmail(placeOrderRequest.getCustomerEmail());
        if (customer == null) {
            customer = new Customer();
            customer.setName(placeOrderRequest.getCustomerName());
            customer.setEmail(placeOrderRequest.getCustomerEmail());
            customer.setPhone(placeOrderRequest.getCustomerPhone());
            customer = customerRepository.save(customer);
        }

        // 2. Mağazayı Al
        Store store = storeRepository.findByid(placeOrderRequest.getStoreId());
        if (store == null) {
            throw new RuntimeException("Mağaza bulunamadı! ID: " + placeOrderRequest.getStoreId());
        }

        // 3. Sipariş Detaylarını Oluştur ve Kaydet
        OrderDetails orderDetails = new OrderDetails(
            customer, 
            store, 
            placeOrderRequest.getTotalPrice(), 
            LocalDateTime.now()
        );
        orderDetails = orderDetailsRepository.save(orderDetails);

        // 4. Sipariş Kalemlerini İşle ve Stok Güncelle
        for (PurchaseProductDTO item : placeOrderRequest.getPurchaseProduct()) {
            // Envanteri bul ve stok düşür
            Inventory inventory = inventoryRepository.findByProductIdandStoreId(item.getId(), placeOrderRequest.getStoreId());
            if (inventory != null) {
                inventory.setStockLevel(inventory.getStockLevel() - item.getQuantity());
                inventoryRepository.save(inventory);
            }

            // Ürünü al ve Sipariş Kalemini (OrderItem) oluştur
            Product product = productRepository.findByid(item.getId());
            OrderItem orderItem = new OrderItem(
                orderDetails, 
                product, 
                item.getQuantity(), 
                item.getPrice()
            );
            orderItemRepository.save(orderItem);
        }
    }
}