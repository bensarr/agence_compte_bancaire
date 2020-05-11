/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

/**
 *
 * @author bensa
 */
public class Agence {
    private int id;
    private String libelle;
    private String adresse;
    private String tel;
    private Date datecreation;
    private Utilisateur responsable;

    public Agence() {
    }
    
    public Agence(int id, String libelle, String adresse, String tel, Utilisateur responsable) {
        this.id = id;
        this.libelle = libelle;
        this.adresse = adresse;
        this.tel = tel;
        this.datecreation = datecreation;
        this.responsable = responsable;
    }

    public Agence(String libelle, String adresse, String tel, Utilisateur responsable) {
        this.libelle = libelle;
        this.adresse = adresse;
        this.tel = tel;
        this.datecreation = datecreation;
        this.responsable = responsable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(Date datecreation) {
        this.datecreation = datecreation;
    }

    public Utilisateur getResponsable() {
        return responsable;
    }

    public void setResponsable(Utilisateur responsable) {
        this.responsable = responsable;
    }

    @Override
    public String toString() {
        return libelle;
    }
    
    
}
