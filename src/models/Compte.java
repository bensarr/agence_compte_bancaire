/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDate;
import java.util.Date;
import services.SecuriteService;
import services.SystemeService;

/**
 *
 * @author bensa
 */
public class Compte {
    private int id;
    private String numero;
    private double solde;
    private LocalDate datecreation;
    private String etat;
    private Client client;
    private Agence agence;
    
    
    SystemeService ss;
    SecuriteService sec;

    public Compte() {
    }
    
    public Compte(int id, String numero, double solde, String etat, Client client, Agence agence) {
        this.id = id;
        this.numero = numero;
        this.solde = solde;
        this.etat = etat;
        this.client = client;
        this.agence = agence;
    }

    public Compte(String numero, double solde, String etat, Client client, Agence agence) {
        this.numero = numero;
        this.solde = solde;
        this.etat = etat;
        this.client = client;
        this.agence = agence;
    }

    public Compte(int id, String numero, double solde, LocalDate datecreation, String etat, Client client, Agence agence) {
        this.id = id;
        this.numero = numero;
        this.solde = solde;
        this.datecreation = datecreation;
        this.etat = etat;
        this.client = client;
        this.agence = agence;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public LocalDate getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(LocalDate datecreation) {
        this.datecreation = datecreation;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    @Override
    public String toString() {
        return numero;
    }
    public String getNci() {
        if(client!=null)
        return client.getNci();
        else
            return null;
    }
    public String getNom() {
        if(client!=null)
        return client.getNom();
        else
            return null;
    }
    public String getPrenom() {
        if(client!=null)
        return client.getPrenom();
        else
            return null;
    }

    public String getEmail() {
        if(client!=null)
        return client.getEmail();
        else
            return null;
    }

    public String getTel() {
        if(client!=null)
        return client.getTel();
        else
            return null;
    }
    public LocalDate getDate_fin_blocage() {
        ss=new SystemeService();
        if(ss.getCompteEpargneByID(id)!=null)
            return ss.getCompteEpargneByID(id).getDate_fin_blocage();
        else
            return null;
    }
    public String getTypeCompte() {
        ss=new SystemeService();
        if(ss.getCompteCourantByID(id)!=null)
            return "Courant";
        else
            return "Epargne";
    }
    
    
       
}
