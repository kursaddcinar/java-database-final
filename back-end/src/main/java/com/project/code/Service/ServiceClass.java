package com.project.code.Service;

import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.repository.InventoryRepository;
import com.project.code.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceClass {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Belirli bir ürün ve mağaza kombinasyonu için envanter kaydı var mı kontrol eder.
     * Kayıt varsa false (yeni kayıt eklenemez), yoksa true döner.
     */
    public boolean validateInventory(Inventory inventory) {
        Inventory existing = inventoryRepository.findByProductIdandStoreId(
            inventory.getProduct().getId(), 
            inventory.getStore().getId()
        );
        return existing == null;
    }

    /**
     * Ürün ismine göre mükerrer kayıt kontrolü yapar.
     * Aynı isimde ürün varsa false, yoksa true döner.
     */
    public boolean validateProduct(Product product) {
        Product existing = productRepository.findByName(product.getName());
        return existing == null;
    }

    /**
     * Ürün ID'sine göre varlık doğrulaması yapar.
     */
    public boolean ValidateProductId(long id) {
        Product product = productRepository.findByid(id);
        return product != null;
    }

    /**
     * Ürün ve mağaza ikilisine ait mevcut envanter nesnesini döndürür.
     */
    public Inventory getInventoryId(Inventory inventory) {
        return inventoryRepository.findByProductIdandStoreId(
            inventory.getProduct().getId(), 
            inventory.getStore().getId()
        );
    }
}