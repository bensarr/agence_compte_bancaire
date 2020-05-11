/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import services.SecuriteService;

/**
 *
 * @author user
 */
public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String login;
    private String password;
    private String etat; 
    private String avoiragence;
    private Agence agence;
     
    //Propriete de navigation
    private Profil profil;
     

    public Utilisateur() {
    }

    public Utilisateur(String login, String password) {
        this.login = login;
        this.password = password;
    }
    
    public Utilisateur(int id, String nom, String prenom, String login, String password, String etat, Profil profil) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.password = password;
        this.etat = etat;
        this.profil = profil;
    }

    public Utilisateur(String nom, String prenom, String login, String password, Profil profil) {
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.password = password;
        this.profil = profil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public String getAvoiragence() {
        return avoiragence;
    }

    public void setAvoiragence(String avoiragence) {
        this.avoiragence = avoiragence;
    }

    public Agence getAgence() {
        SecuriteService ss=new SecuriteService();
        if(this.profil.getLibelle().compareToIgnoreCase("Caissier")==0)
        return ss.getAgenceByCaissier(this);
        else
            if(this.profil.getLibelle().compareToIgnoreCase("Responsable")==0)
                return ss.getAgenceByResponsable(this);
        return null;
    }
    
    @Override
    public String toString() {
        return nom + " " + prenom;
    }

     
}
