package com.project.code.Repo;

import com.kursaddcinar.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    /**
     * ID'ye göre mağaza bulur. 
     * JpaRepository içindeki default findById'den farklı olarak doğrudan Store döner.
     */
    Store findByid(Long id);

    /**
     * Mağaza adında geçen kelimeye göre büyük/küçük harf duyarsız arama yapar.
     */
    @Query("SELECT s FROM Store s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :pname, '%'))")
    List<Store> findBySubName(@Param("pname") String pname);
}