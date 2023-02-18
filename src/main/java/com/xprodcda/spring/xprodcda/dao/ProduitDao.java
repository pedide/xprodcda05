package com.xprodcda.spring.xprodcda.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xprodcda.spring.xprodcda.domain.Produit;
import com.xprodcda.spring.xprodcda.repository.IProduitRepository;

@Service
public class ProduitDao {
	@Autowired
	IProduitRepository produitRepository;
	
	//Liste de produits
	public List<Produit> getProduits(){
		return produitRepository.findAll();
		
	}
	//Save 
	public Produit saveProduit(Produit produit) {
		return produitRepository.save(produit); 
	}
	
	//get a Produit
	public Produit getProduitById(Long idProd) {
		return produitRepository.findById(idProd).get();
	}
	
	//Delete a produit
	public void deleteProduit(Produit produit) {
		produitRepository.delete(produit);
	}
	//Update 
		public Produit updateProduit(Produit produit) {
			return produitRepository.save(produit);
		}
}
