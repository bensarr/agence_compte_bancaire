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
public class Client extends Utilisateur{
    private String nci;
    private String adresse;
    private String email;
    private String tel;
    private int idC;

    public Client() {
    }

    public Client(int idC,String nci, String adresse, String email, String tel) {
        this.idC=idC;
        this.nci = nci;
        this.adresse = adresse;
        this.email = email;
        this.tel = tel;
    }

    public Client(int id, String nom, String prenom, String login, String password, String etat, Profil profil) {
        super(id, nom, prenom, login, password, etat, profil);
    }
    
    
    public Client(int id, String nom, String prenom, String login, String password, String etat, Profil profil,String nci, String adresse, String email, String tel,int idC) {
        super(id, nom, prenom, login, password, etat, profil);
        this.nci = nci;
        this.adresse = adresse;
        this.email = email;
        this.tel = tel;
        this.idC=idC;
    }

    public Client(String nom, String prenom, String login, String password, Profil profil,String nci, String adresse, String email, String tel) {
        super(nom, prenom, login, password, profil);
        this.nci = nci;
        this.adresse = adresse;
        this.email = email;
        this.tel = tel;
    }
    
    public String getNci() {
        return nci;
    }

    public void setNci(String nci) {
        this.nci = nci;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }
    
    @Override
    public String toString() {
        return nci;
    }    
}
