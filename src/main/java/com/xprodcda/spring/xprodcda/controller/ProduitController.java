package com.xprodcda.spring.xprodcda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.xprodcda.spring.xprodcda.dao.ProduitDao;
import com.xprodcda.spring.xprodcda.domain.Produit;

@RestController
@RequestMapping
@CrossOrigin("*")
public class ProduitController {

	@Autowired
	ProduitDao produitsDao;
	
	@GetMapping("/produits")
	public  List<Produit> getAllProduits(@Validated @RequestBody(required=false) Produit produits){
		return produitsDao.getProduits();
		
	}
	@PostMapping("/produits")
	public Produit createProduit(@Validated @RequestBody(required=false) Produit produit) {
		return produitsDao.saveProduit(produit);
	}
	@GetMapping("/produits/{idProduit}")
	public ResponseEntity findProduitById(@PathVariable(name="idProduit") Long idProduit) {
		if(idProduit == null) {
			return ResponseEntity.badRequest().body("Cannot retrieve produit with null ID");
		}
		Produit produit = produitsDao.getProduitById(idProduit);
		
		if(produit==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(produit);
		
	}
	@PutMapping("/produits/{id}")
	 public ResponseEntity<Produit> updateProduit(@Validated @PathVariable(name="id") Long idProduit, @RequestBody(required=false) Produit produit) {
        if(produit==null) {
            return ResponseEntity.notFound().build();
        }
        produit.setId(idProduit);
        produitsDao.updateProduit(produit);
        return ResponseEntity.ok().body(produit);
    }
	
	@DeleteMapping("/produits/{id}")
	public ResponseEntity<Produit> deleteProduits(@Validated @PathVariable(name="id") Long idProduit){
		Produit produit = produitsDao.getProduitById(idProduit);
		if(produit==null) {
			return ResponseEntity.notFound().build();
		}
		produitsDao.deleteProduit(produit);
		
		return ResponseEntity.ok().body(produit);
	}
}

