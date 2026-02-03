package com.project.code.Repo;


import com.kursaddcinar.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    // E-posta ile müşteri bulma
    Customer findByEmail(String email);

    // ID ile müşteri bulma (Optional dönmesi best practice'dir ama yönergeye göre Customer dönüyoruz)
    Customer findById(long id);
}