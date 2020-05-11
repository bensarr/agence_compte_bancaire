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
public class Guichet {
    private int id;
    private String numero;
    private String etat;
    private Agence agence;

    public Guichet() {
    }

    public Guichet(int id, String numero, String etat, Agence agence) {
        this.id = id;
        this.numero = numero;
        this.etat = etat;
        this.agence = agence;
    }

    public Guichet(String numero, String etat, Agence agence) {
        this.numero = numero;
        this.etat = etat;
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

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
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
    
}
