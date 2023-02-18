package com.xprodcda.spring.xprodcda.domain;

import java.io.Serializable;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.SequenceGenerator;

import org.springframework.data.annotation.Id;



import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name="Commande")
@Embeddable
public class Commande implements Serializable{
	@EmbeddedId
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="command_ID")
	private Long idCmd;
	@Column(name="DateCmd", nullable=false)
	private String dateCmde;
	@Column(name="FraisPort")
	private double fraisPortCmde;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idFour",nullable=false)
	private Fournisseur fournisseur;
	
	public Commande() {
		super();
	}


	public Commande(Long idCmd, String dateCmde, double fraisPortCmde) {
		super();
		this.idCmd = idCmd;
		this.dateCmde = dateCmde;
		this.fraisPortCmde = fraisPortCmde;
	}


	public Commande(Long idCmd, String dateCmde, double fraisPortCmde, Fournisseur fournisseur) {
		super();
		this.idCmd = idCmd;
		this.dateCmde = dateCmde;
		this.fraisPortCmde = fraisPortCmde;
		this.fournisseur = fournisseur;
	}


	public Fournisseur getFournisseur() {
		return fournisseur;
	}


	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}


	public Long getIdCmd() {
		return idCmd;
	}


	public void setIdCmd(Long idCmd) {
		this.idCmd = idCmd;
	}


	public String getDateCmde() {
		return dateCmde;
	}


	public void setDateCmde(String dateCmde) {
		this.dateCmde = dateCmde;
	}


	public double getFraisPortCmde() {
		return fraisPortCmde;
	}


	public void setFraisPortCmde(double fraisPortCmde) {
		this.fraisPortCmde = fraisPortCmde;
	}
	
	

}
