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
public class Courant extends Compte{
    private double frais_tenue;
    private int idC;

    public Courant() {
    }
    

    public Courant(int idC,double frais_tenue) {
        this.frais_tenue = frais_tenue;
        this.idC = idC;
    }

    public Courant(double frais_tenue, int idC, int id, String numero, double solde, String etat, Client client, Agence agence) {
        super(id, numero, solde, etat, client, agence);
        this.frais_tenue = frais_tenue;
        this.idC = idC;
    }

    public Courant(double frais_tenue, int idC, String numero, double solde, String etat, Client client, Agence agence) {
        super(numero, solde, etat, client, agence);
        this.frais_tenue = frais_tenue;
        this.idC = idC;
    }

    public Courant(int id, String numero, double solde, String etat, Client client, Agence agence,double frais_tenue, int idC) {
        super(id, numero, solde, etat, client, agence);
        this.frais_tenue = frais_tenue;
        this.idC = idC;
    }
        
    public Courant(int id, String numero, double solde, String etat, Client client, Agence agence,double frais_tenue) {
        super(id, numero, solde, etat, client, agence);
        this.frais_tenue = frais_tenue;
    }

    public Courant(String numero, double solde, String etat, Client client, Agence agence,double frais_tenue) {
        super(numero, solde, etat, client, agence);
        this.frais_tenue = frais_tenue;
    }

    public double getFrais_tenue() {
        return frais_tenue;
    }

    public void setFrais_tenue(double frais_tenue) {
        this.frais_tenue = frais_tenue;
    }        

    @Override
    public String toString() {
        return "Courant{"+super.toString() + "frais_tenue=" + frais_tenue + '}';
    }

    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }
    
}
