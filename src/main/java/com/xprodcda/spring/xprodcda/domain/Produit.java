package com.xprodcda.spring.xprodcda.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
@Entity
@Table(name="PRODUIT")
@Inheritance(strategy=InheritanceType.JOINED)
public class Produit{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="IDPRODUIT")
	private Long id;   //id pour la DB
	
	@Column(name="REFINTERNE")
	private String refInterne; 
	@Column(name="DESIGNATION")
	private String designation;
	@Column(name="DESCRIPTION")
	private String descriptif;
	@Column(name="PRIXUHT")
	private double prixUHT;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRefInterne() {
		return refInterne;
	}
	public void setRefInterne(String refInterne) {
		this.refInterne = refInterne;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDescriptif() {
		return descriptif;
	}
	public void setDescriptif(String descriptif) {
		this.descriptif = descriptif;
	}
	public double getPrixUHT() {
		return prixUHT;
	}
	public void setPrixUHT(double prixUHT) {
		this.prixUHT = prixUHT;
	}
	
	public Produit(Long id, String refInterne, String designation, String descriptif, double prixUHT) {
		super();
		this.id = id;
		this.refInterne = refInterne;
		this.designation = designation;
		this.descriptif = descriptif;
		this.prixUHT = prixUHT;
	}
	public Produit() {
		super();
	}
	
	
	

}
