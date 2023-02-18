package com.xprodcda.spring.xprodcda.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xprodcda.spring.xprodcda.domain.Fournisseur;

import com.xprodcda.spring.xprodcda.repository.IFournisseurRepository;


@Service
public class FournisseurDao {
	@Autowired
	IFournisseurRepository fournisseurRepository;
	
	//Liste de Fournisseurs
		public List<Fournisseur> getFournisseurs(){
			return fournisseurRepository.findAll();
			
		}
		//Save 
		public Fournisseur saveFournisseur(Fournisseur fournisseur) {
			return fournisseurRepository.save(fournisseur); 
		}
		
		//get a Fournisseur
		public Fournisseur getFournisseurById(Long idFour) {
			return fournisseurRepository.findById(idFour).get();
		}
		
		//Delete a Fournisseur
		public void deleteFournisseur(Fournisseur fournisseur) {
			fournisseurRepository.delete(fournisseur);
		}
}
