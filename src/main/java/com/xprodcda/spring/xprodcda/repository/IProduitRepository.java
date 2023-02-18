package com.xprodcda.spring.xprodcda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xprodcda.spring.xprodcda.domain.Produit;

public interface IProduitRepository extends JpaRepository<Produit,Long>{

}
