/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDate;

/**
 *
 * @author bensa
 */
public class Affectation {
    private int id;
    private LocalDate datedebut;
    private LocalDate datefin;
    private Utilisateur caissier;
    private Guichet guichet;
    private Agence agence;

    public Affectation() {
    }

    public Affectation(int id, LocalDate datedebut, LocalDate datefin, Utilisateur caissier, Guichet guichet) {
        this.id = id;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.caissier = caissier;
        this.guichet = guichet;
    }

    public Affectation(LocalDate datedebut, LocalDate datefin, Utilisateur caissier, Guichet guichet) {
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.caissier = caissier;
        this.guichet = guichet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(LocalDate datedebut) {
        this.datedebut = datedebut;
    }

    public LocalDate getDatefin() {
        return datefin;
    }

    public void setDatefin(LocalDate datefin) {
        this.datefin = datefin;
    }

    public Utilisateur getCaissier() {
        return caissier;
    }

    public void setCaissier(Utilisateur caissier) {
        this.caissier = caissier;
    }

    public Guichet getGuichet() {
        return guichet;
    }

    public void setGuichet(Guichet guichet) {
        this.guichet = guichet;
    }

    @Override
    public String toString() {
        return "Affectation{" + "id=" + id + ", datedebut=" + datedebut + ", datefin=" + datefin + ", caissier=" + caissier + ", guichet=" + guichet + '}';
    }

    public Agence getAgence() {
        return guichet.getAgence();
    }

    public void setAgence(Agence agence) {
        this.agence = guichet.getAgence();
    }
    
    
}
