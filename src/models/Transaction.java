/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author bensa
 */
public class Transaction {
    private int id;
    private String type;
    private double montant;
    private LocalDate date;
    private Compte compte_source;
    private Compte compte_cible;
    private Guichet guichet;
    private Agence agence;

    public Transaction() {
    }
    
    public Transaction(int id, String type, double montant, LocalDate date, Compte compte_source, Compte compte_cible,Guichet guichet) {
        this.id = id;
        this.type = type;
        this.montant = montant;
        this.date = date;
        this.compte_source = compte_source;
        this.compte_cible = compte_cible;
        this.guichet = guichet;
    }

    public Transaction(String type, double montant, Compte compte_source, Compte compte_cible,Guichet guichet) {
        this.type = type;
        this.montant = montant;
        this.compte_source = compte_source;
        this.compte_cible = compte_cible;
        this.guichet = guichet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Compte getCompte_source() {
        return compte_source;
    }

    public void setCompte_source(Compte compte_source) {
        this.compte_source = compte_source;
    }

    public Compte getCompte_cible() {
        return compte_cible;
    }

    public void setCompte_cible(Compte compte_cible) {
        this.compte_cible = compte_cible;
    }

    public Guichet getGuichet() {
        return guichet;
    }

    public void setGuichet(Guichet guichet) {
        this.guichet = guichet;
    }

    @Override
    public String toString() {
        return "Transaction{" + "id=" + id + ", type=" + type + ", montant=" + montant + ", date=" + date + ", compte_source=" + compte_source + ", compte_cible=" + compte_cible + ", guichet=" + guichet + '}';
    }

    public Agence getAgence() {
        if(guichet!=null)
            return guichet.getAgence();
        else
            return null;
    }
    
    
}
