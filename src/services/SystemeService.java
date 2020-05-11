/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.AgenceDao;
import dao.CompteDao;
import dao.CourantDao;
import dao.DaoMysql;
import dao.EpargneDao;
import dao.ISGBD;
import dao.TransactionDao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import models.Affectation;
import models.Agence;
import models.Client;
import models.Compte;
import models.Courant;
import models.Epargne;
import models.Guichet;
import models.Transaction;
import models.Utilisateur;

/**
 *
 * @author bensa
 */
public class SystemeService {
    ISGBD sgbd=new DaoMysql();
        CompteDao daoCompte=new CompteDao(sgbd);
        CourantDao daoCourant=new CourantDao(sgbd);
        EpargneDao daoEpargne=new EpargneDao(sgbd);
        TransactionDao daoTransaction=new TransactionDao(sgbd);
        SecuriteService ss=new SecuriteService();
    public SystemeService() {}
    /*
    *Compte
    */
    public int updateCompte(Compte c)
    {
        return daoCompte.update(c);
    }
    public int addCompteCourant(Courant c)
    {
        return daoCourant.create(c);
    }
    public int addCompteEpargne(Epargne e)
    {
        return daoEpargne.create(e);
    }
    public List<Compte> getAllComptes()
    {
        return daoCompte.findAll();
    }
    public Compte getCompteByID(int id)
    {
        return daoCompte.findById(id);
    }
    
    public Courant getCompteCourantByID(int id)
    {
        return daoCourant.findById(id);
    }
    public Epargne getCompteEpargneByID(int id)
    {
        return daoEpargne.findById(id);
    }
    public List<Compte> getCompteByClient(Client cl)
    {
        List<Compte> lc = new ArrayList<>();
        getAllComptes().forEach((t) -> {
            if(t.getClient().getId()==cl.getId())
            {
                lc.add(t);
            }
        });
        return lc;
    }
    public Compte getCompteByNumero(String num)
    {
        return daoCompte.findByNumero(num);
    }
    /*
    *Guichet
    */
    public Guichet getGuichetByCaissierByOneDate(Utilisateur u,LocalDate local)
    {           
        Guichet g=null;
        List<Affectation> l=ss.getAllAffectations();
        List<Affectation> la=ss.getAllAffectationsByOneDate(l, local);
        for (Affectation a : la) {
            if(a.getCaissier().getId()==u.getId())
                g=a.getGuichet();
        }   
        return g;
    }
    public Utilisateur getCaissierByGuichetByOneDate(Guichet g,LocalDate local)
    {           
        Utilisateur u=null;
        List<Affectation> l=ss.getAllAffectations();
        List<Affectation> la=ss.getAllAffectationsByOneDate(l, local);
        for (Affectation a : la) {
            if(a.getGuichet().getId()==g.getId())
                u=a.getCaissier();
        }   
        return u;
    }
    /*
    *Transaction
    */
    public int addTransaction(Transaction t)
    {
        return daoTransaction.create(t);
    }
    public List<Transaction> getAllTransactions()
    {
        return daoTransaction.findAll();
    }
    public Transaction getTransactionById(int id)
    {
        return daoTransaction.findById(id);
    }
    public List<Transaction> getAllTransactionByAgence(Agence ag)
    {
        List<Transaction> l=new ArrayList<>();
        List<Transaction> lt=getAllTransactions();
        for (Transaction t : lt) {
            if(t.getGuichet()!=null)
                if(t.getGuichet().getAgence().getId()==ag.getId())
                    l.add(t);
        }   
        return l;
    }
    public List<Transaction> getAllTransactionByGuichet(List<Transaction> l,Guichet g)
    {
        List<Transaction> lt=new ArrayList<>();
        for (Transaction t : l) {
            if(t.getGuichet()!=null)
                if(t.getGuichet().getId()==g.getId())
                    lt.add(t);
        }   
        return lt;
    }
    public List<Transaction> getAllTransactionByCompte(List<Transaction> l,Compte c)
    {
        List<Transaction> lt=new ArrayList<>();
        for (Transaction t : l) {
            if(t.getCompte_source()!=null)
            {
                if(t.getCompte_source().getId()==c.getId())
                    lt.add(t);
            }
            if(t.getCompte_cible()!=null)
            {
                if(t.getCompte_cible().getId()==c.getId())
                    lt.add(t);
            }
        }   
        return lt;
    }
    public List<Transaction> getAllTransactionByClient(List<Transaction> l,Client cl)
    {
        List<Transaction> lt=new ArrayList<>();
        for (Transaction t : l) {
            if(t.getCompte_source()!=null)
            {
                if(t.getCompte_source().getClient().getId()==cl.getId())
                    lt.add(t);
            }
            if(t.getCompte_cible()!=null)
            {
                if(t.getCompte_cible().getClient().getId()==cl.getId())
                    lt.add(t);
            }        
        }
        return lt;
    }
    public List<Transaction> getAllTransactionByDate(List<Transaction> l,LocalDate local)
    {
        List<Transaction> lt=new ArrayList<>();
        for (Transaction t : l) {
            if(t.getGuichet()!=null)
                if(t.getDate().equals(local))
                    lt.add(t);
        }   
        return lt;
    }
    
}
