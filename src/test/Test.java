/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Affectation;
import models.AffectationCaissier;
import models.Agence;
import models.Client;
import models.Compte;
import models.Courant;
import models.Epargne;
import models.Guichet;
import models.Profil;
import models.Utilisateur;
import services.SecuriteService;
import services.SystemeService;
/**
 *
 * @author bensa
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SecuriteService ss=new SecuriteService();
        SystemeService s=new SystemeService();
        //ajout Agence
//        ss.addAgence(new Agence("libelle", "adresse", "tel", ss.getUtilisateurByID(1)));
        //lister Agence
//        ss.getAllAgences().forEach((a) -> {
//            System.out.println(a.toString());
//        });


        //ajout Guichet
//        ss.addGuichet(new Guichet("G1", "INDISPONIBLE", ss.getAgenceById(1)));
        //lister Guichet
//        ss.getAllGuichets().forEach((g) -> {
//            System.out.println(g.toString());
//        });
        //afficher un Guichet
//            System.out.println(ss.getGuichetByID(1));

        //ajout Affectation
//        ss.addAffectation(new Affectation(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 31), ss.getUtilisateurByID(1), ss.getGuichetByID(1)));
        //lister Affectation
//        ss.getAllAffectations().forEach((aff) -> {
//            System.out.println(aff.toString());
//        });
        //afficher un Affectation
//            System.out.println(ss.getAffectationByID(2));


        //lister client
//        ss.getAllClients().forEach((c) -> {
//            System.out.println(c.toString());
//        });
        
        //ajout client
//        ss.addClient(new Client("nom2", "prenom2", "login2", "password2", "Inactif", new Profil("Caissier"), "nci2", "adresse2", "email2", "tel2"));
        //afficher un client
//            System.out.println(ss.getClientById(1));
//            System.out.println(ss.getAllClients().toString());

        //lister Compte
//        s.getAllComptes().forEach((c) -> {
//            System.out.println(c.toString());
//        });
        //ajout Compte
        //Courant
//        s.addCompteCourant(new Courant("numero", 0, "Bloqué", ss.getClientById(1), ss.getAgenceById(1),1050));
        //Epargne
//        System.out.println(s.addCompteEpargne(new Epargne("numero1", 0, "Bloqué", ss.getClientById(2), ss.getAgenceById(1),0.11,LocalDate.of(2020, 1, 31))));
        //afficher un Compte
//            System.out.println(s.getCompteEpargneByID(2));
            
//            System.out.println(s.getGuichetByCaissier(ss.seConnecter(new Utilisateur("test","test"))));

//            System.out.println(ss.getAllAffectationsByOneDate(ss.getAllAffectations(), LocalDate.now()));
            System.out.println(s.getAllTransactionByClient(s.getAllTransactions(),ss.getClientById(22)));
    
    }
}
