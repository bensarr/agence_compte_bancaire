/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author bensa
 */
public class AffectationCaissier {
    private int id;
    private Utilisateur caissier;
    private Agence agence;

    public AffectationCaissier() {
    }

    public AffectationCaissier(int id, Utilisateur caissier, Agence agence) {
        this.id = id;
        this.caissier = caissier;
        this.agence = agence;
    }

    public AffectationCaissier(Utilisateur caissier, Agence agence) {
        this.caissier = caissier;
        this.agence = agence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getCaissier() {
        return caissier;
    }

    public void setCaissier(Utilisateur caissier) {
        this.caissier = caissier;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    @Override
    public String toString() {
        return "AffectationCaissier{" + "id=" + id + ", caissier=" + caissier + ", agence=" + agence + '}';
    }

    
}
