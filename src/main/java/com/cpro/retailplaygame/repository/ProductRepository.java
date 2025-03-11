package com.cpro.retailplaygame.repository;

import com.cpro.retailplaygame.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// This makes it clear that this is a repository class where we interact with the database
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}




