package com.xprodcda.spring.xprodcda.domain;

import java.util.ArrayList;
import java.util.List;
import com.xprodcda.spring.xprodcda.domain.Produit;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="PRODUITAPPRO")
public class ProduitAppro extends Produit{
	
	@Column(name="PRIXACHATUHT")
	private double prixAchatUHT;
	@Column(name="REFEXTPROD")
	private String refExterneProd;
	@Column(name="PRIXAPPUHT")
	private double prixAppUHT;
	
	@ManyToMany( fetch = FetchType.LAZY, mappedBy="produitAppro")
	private List<Fournisseur> fournisseurs = new ArrayList<>();
	
	@OneToMany(mappedBy="id", fetch = FetchType.LAZY)	//id de la LigneCmde
	private List<LigneCmde> ligneCmde = new ArrayList<>(); 
	
	public ProduitAppro(Long id, String refInterne, String designation, String descriptif, double prixUHT) {
		super(id, refInterne, designation, descriptif, prixUHT);
	}

	public ProduitAppro() {
		super();
		
	}

	public ProduitAppro(Long id, String refInterne, String designation, String descriptif, double prixUHT,
			double prixAchatUHT) {
		super(id, refInterne, designation, descriptif, prixUHT);
		this.prixAchatUHT = prixAchatUHT;
	}
	public ProduitAppro(Long id, String refInterne, String designation, String descriptif, double prixUHT,
			double prixAchatUHT, String refExterneProd, double prixAppUHT) {
		super(id, refInterne, designation, descriptif, prixUHT);
		this.prixAchatUHT = prixAchatUHT;
		this.refExterneProd = refExterneProd;
		this.prixAppUHT = prixAppUHT;
	}

	public ProduitAppro(Long id, String refInterne, String designation, String descriptif, double prixUHT,
			double prixAchatUHT, String refExterneProd, double prixAppUHT, List<Fournisseur> fournisseurs) {
		super(id, refInterne, designation, descriptif, prixUHT);
		this.prixAchatUHT = prixAchatUHT;
		this.refExterneProd = refExterneProd;
		this.prixAppUHT = prixAppUHT;
		this.fournisseurs = fournisseurs;
	}

	public String getRefExterneProd() {
		return refExterneProd;
	}

	public void setRefExterneProd(String refExterneProd) {
		this.refExterneProd = refExterneProd;
	}

	public double getPrixAppUHT() {
		return prixAppUHT;
	}

	public void setPrixAppUHT(double prixAppUHT) {
		this.prixAppUHT = prixAppUHT;
	}

	public List<Fournisseur> getFournisseurs() {
		return fournisseurs;
	}

	public void setFournisseurs(List<Fournisseur> fournisseurs) {
		this.fournisseurs = fournisseurs;
	}

	public double getPrixAchatUHT() {
		return prixAchatUHT;
	}

	public void setPrixAchatUHT(double prixAchatUHT) {
		this.prixAchatUHT = prixAchatUHT;
	}
	

}
