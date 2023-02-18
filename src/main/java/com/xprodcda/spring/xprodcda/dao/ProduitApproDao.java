package com.xprodcda.spring.xprodcda.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xprodcda.spring.xprodcda.domain.ProduitAppro;
import com.xprodcda.spring.xprodcda.repository.IProduitApproRepository;


@Service
public class ProduitApproDao {
	@Autowired
	IProduitApproRepository produitApproRepository;
	
	//Liste de produits
		public List<ProduitAppro> getProduitsAppro(){
			return produitApproRepository.findAll();
			
		}
		//Save 
		public ProduitAppro saveProduitAppro(ProduitAppro produit) {
			return produitApproRepository.save(produit); 
		}
		
		//get a Produit
		public ProduitAppro getProduitApproById(Long idProd) {
			return produitApproRepository.findById(idProd).get();
		}
		
		//Delete a produit
		public void deleteProduitAppro(ProduitAppro produit) {
			produitApproRepository.delete(produit);
		}
}
