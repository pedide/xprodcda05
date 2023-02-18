package com.xprodcda.spring.xprodcda.domain;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="LigneCmde")
public class LigneCmde {
	@EmbeddedId
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="LigneCmd_ID")
	private Long id;
	@Column(name="QTE")
	private int qte;
	@Column(name="DATELIV")
	private String dateLiv;
	@Column(name="PRIXUHT")
	private double prixUHT;
	
	@ManyToOne
	@JoinColumn(name="commande_ID")
	private Commande commande;

}
