package com.project.code.Controller;

import com.project.code.Model.*;
import com.project.code.Service.ServiceClass;
import com.project.code.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ServiceClass serviceClass;

    @PutMapping
    public Map<String, String> updateInventory(@RequestBody CombinedRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            if (!serviceClass.ValidateProductId(request.getProduct().getId())) {
                response.put("message", "Ürün mevcut değil");
                return response;
            }

            Inventory existingInventory = inventoryRepository.findByProductIdandStoreId(
                    request.getProduct().getId(), request.getInventory().getStore().getId());

            if (existingInventory != null) {
                existingInventory.setStockLevel(request.getInventory().getStockLevel());
                inventoryRepository.save(existingInventory);
                response.put("message", "Ürün başarıyla güncellendi");
            } else {
                response.put("message", "Veri mevcut değil");
            }
        } catch (DataIntegrityViolationException e) {
            response.put("message", "Veri bütünlüğü hatası");
        }
        return response;
    }

    @PostMapping
    public Map<String, String> saveInventory(@RequestBody Inventory inventory) {
        Map<String, String> response = new HashMap<>();
        try {
            if (!serviceClass.validateInventory(inventory)) {
                response.put("message", "Veri zaten mevcut");
            } else {
                inventoryRepository.save(inventory);
                response.put("message", "Veri başarıyla kaydedildi");
            }
        } catch (Exception e) {
            response.put("message", "Hata oluştu");
        }
        return response;
    }

    @GetMapping("/{storeid}")
    public Map<String, Object> getAllProducts(@PathVariable Long storeid) {
        Map<String, Object> response = new HashMap<>();
        response.put("products", productRepository.findProductsByStoreId(storeid));
        return response;
    }

    @GetMapping("filter/{category}/{name}/{storeid}")
    public Map<String, Object> getProductName(@PathVariable String category, @PathVariable String name, @PathVariable Long storeid) {
        Map<String, Object> response = new HashMap<>();
        List<Product> products;

        if (category.equals("null")) {
            products = productRepository.findByNameLike(storeid, name);
        } else if (name.equals("null")) {
            products = productRepository.findByCategoryAndStoreId(storeid, category);
        } else {
            products = productRepository.findByNameAndCategory(storeid, name, category);
        }
        response.put("product", products);
        return response;
    }

    @GetMapping("search/{name}/{storeId}")
    public Map<String, Object> searchProduct(@PathVariable String name, @PathVariable Long storeId) {
        Map<String, Object> response = new HashMap<>();
        response.put("product", productRepository.findByNameLike(storeId, name));
        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String, String> removeProduct(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        if (!serviceClass.ValidateProductId(id)) {
            response.put("message", "Veritabanında ürün mevcut değil");
        } else {
            inventoryRepository.deleteByProductId(id);
            response.put("message", "Ürün başarıyla silindi");
        }
        return response;
    }

    @GetMapping("validate/{quantity}/{storeId}/{productId}")
    public boolean validateQuantity(@PathVariable Integer quantity, @PathVariable Long storeId, @PathVariable Long productId) {
        Inventory inv = inventoryRepository.findByProductIdandStoreId(productId, storeId);
        return inv != null && inv.getStockLevel() >= quantity;
    }
}