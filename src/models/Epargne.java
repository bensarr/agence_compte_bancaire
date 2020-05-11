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
public class Epargne extends Compte{
    private double taux_interet;
    private LocalDate date_fin_blocage;
    private int idE;

    public Epargne(int id, String numero, double solde, String etat, Client client, Agence agence,double taux_interet, LocalDate date_fin_blocage, int idE) {
        super(id, numero, solde, etat, client, agence);
        this.taux_interet = taux_interet;
        this.date_fin_blocage = date_fin_blocage;
        this.idE = idE;
    }
    
    public Epargne(int id, String numero, double solde, String etat, Client client, Agence agence, double taux_interet, LocalDate date_fin_blocage) {
        super(id, numero, solde, etat, client, agence);
        this.taux_interet = taux_interet;
        this.date_fin_blocage = date_fin_blocage;
    }

    public Epargne( int idE,double taux_interet, LocalDate date_fin_blocage) {
        this.taux_interet = taux_interet;
        this.date_fin_blocage = date_fin_blocage;
        this.idE = idE;
    }
    

    public Epargne(String numero, double solde, String etat, Client client, Agence agence,double taux_interet, LocalDate date_fin_blocage) {
        super(numero, solde, etat, client, agence);
        this.taux_interet = taux_interet;
        this.date_fin_blocage = date_fin_blocage;
    }

    public double getTaux_interet() {
        return taux_interet;
    }

    public void setTaux_interet(double taux_interet) {
        this.taux_interet = taux_interet;
    }

    public LocalDate getDate_fin_blocage() {
        return date_fin_blocage;
    }

    public void setDate_fin_blocage(LocalDate date_fin_blocage) {
        this.date_fin_blocage = date_fin_blocage;
    }

    @Override
    public String toString() {
        return "Epargne{"+super.toString() + "taux_interet=" + taux_interet + ", date_fin_blocage=" + date_fin_blocage + '}';
    }

    public int getIdE() {
        return idE;
    }

    public void setIdE(int idE) {
        this.idE = idE;
    }
    
    
}
