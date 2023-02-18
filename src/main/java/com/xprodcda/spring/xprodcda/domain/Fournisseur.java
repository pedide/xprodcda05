package com.xprodcda.spring.xprodcda.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name="FOURNISSEUR")
public class Fournisseur implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="IDFOUR")
	private Long id;
	
	@Column(name="NOM")
	private String nom;
	@Column(name="RS")
	private String rs;
	@Column(name="RUE")
	private String rue;
	@Column(name="CP")
	private String cp;
	@Column(name="VILLE")
	private String ville;
	@Column(name="PAYS")
	private String pays;
	@Column(name="TEL")
	private String tel;
	@Column(name="EMAIL")
	private String email;
	@Column(name="REFEXTPROD")
	private String refExterneProd;
	@Column(name="PRIXAPPUHT")
	private double prixAppUHT;
	
	@OneToMany(mappedBy = "idCmd",fetch=FetchType.LAZY)  //id de commande
	private List<Commande> commande = new ArrayList<>();
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name="approvisionner",
	joinColumns=@JoinColumn(name="IDFOUR"),
	inverseJoinColumns = @JoinColumn(name="IDPRODUIT")
	)
	private List<ProduitAppro> produitAppro;
	
	public Fournisseur() {
		super();
	}
	
	

	public Fournisseur(Long id, String nom, String rs, String rue, String cp, String ville, String pays, String tel,
			String email) {
		super();
		this.id = id;
		this.nom = nom;
		this.rs = rs;
		this.rue = rue;
		this.cp = cp;
		this.ville = ville;
		this.pays = pays;
		this.tel = tel;
		this.email = email;
	}
	

	public Fournisseur(Long id, String nom, String rs, String rue, String cp, String ville, String pays, String tel,
			String email, String refExterneProd, double prixAppUHT, List<Commande> commande,
			List<ProduitAppro> produitAppro) {
		super();
		this.id = id;
		this.nom = nom;
		this.rs = rs;
		this.rue = rue;
		this.cp = cp;
		this.ville = ville;
		this.pays = pays;
		this.tel = tel;
		this.email = email;
		this.refExterneProd = refExterneProd;
		this.prixAppUHT = prixAppUHT;
		this.commande = commande;
		this.produitAppro = produitAppro;
	}

	public Fournisseur(Long id, String nom, String rs, String rue, String cp, String ville, String pays, String tel,
			String email, String refExterneProd, double prixAppUHT) {
		super();
		this.id = id;
		this.nom = nom;
		this.rs = rs;
		this.rue = rue;
		this.cp = cp;
		this.ville = ville;
		this.pays = pays;
		this.tel = tel;
		this.email = email;
		this.refExterneProd = refExterneProd;
		this.prixAppUHT = prixAppUHT;
	}

	public String getRefExterneProd() {
		return refExterneProd;
	}

	public List<ProduitAppro> getProduitAppro() {
		return produitAppro;
	}

	public void setProduitAppro(List<ProduitAppro> produitAppro) {
		this.produitAppro = produitAppro;
	}

	public void setCommande(List<Commande> commande) {
		this.commande = commande;
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

	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getRs() {
		return rs;
	}
	public void setRs(String rs) {
		this.rs = rs;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
    
}
