/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.AffectationCaissierDao;
import dao.AffectationDao;
import dao.AgenceDao;
import dao.ClientDao;
import dao.DaoMysql;
import dao.GuichetDao;
import dao.ISGBD;
import dao.ProfilDao;
import dao.UtilisateurDao;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import models.Affectation;
import models.AffectationCaissier;
import models.Agence;
import models.Client;
import models.Guichet;
import models.Profil;
import models.Utilisateur;

/**
 *
 * @author bensa
 */
public class SecuriteService {

    ISGBD sgbd=new DaoMysql();
        AgenceDao daoag=new AgenceDao(sgbd);
        AffectationCaissierDao daoaffC=new AffectationCaissierDao(sgbd);
        GuichetDao daog=new GuichetDao(sgbd);
        AffectationDao daoaf=new AffectationDao(sgbd);
        ClientDao daoc=new ClientDao(sgbd);
        UtilisateurDao daou=new UtilisateurDao(sgbd);
        ProfilDao daop=new ProfilDao(sgbd);
    public SecuriteService() {
    }
    /*
    *Agence
    */
    public int addAgence(Agence a)
    {
        return daoag.create(a);
    }
    public int updateAgence(Agence a)
    {
        return daoag.update(a);
    }
    public List<Agence> getAllAgences()
    {
        return daoag.findAll();
    }
    public Agence getAgenceByResponsable(Utilisateur r)
    {
        return daoag.findByResponsable(r);
    }
    public Agence getAgenceById(int id)
    {
        return daoag.findById(id);
    }
    /*
    *Affectation caissier a Agence
    */
    public int affecterCaissierToAgence(AffectationCaissier a)
    {
        return daoaffC.create(a);
    }
    public Agence getAgenceByCaissier(Utilisateur c)
    {
        if(daoaffC.findByCaissier(c)!=null)
            return daoaffC.findByCaissier(c).getAgence();
        return null;
    }
    public List<Utilisateur> getCaissierByAgence(Agence a)
    {
        List<Utilisateur> l=new ArrayList();
        daoaffC.findAllByAgence(a).forEach(aff->{
            l.add(aff.getCaissier());
        });
        return l;
    }
    /*
    *Guichet
    */
    public int addGuichet(Guichet g)
    {
        return daog.create(g);
    }
    public List<Guichet> getAllGuichets()
    {
        return daog.findAll();
    }
    public List<Guichet> getAllGuichetsByAgence(Agence a)
    {
        return daog.findAllByAgence(a);
    }
    public Guichet getGuichetByID(int id)
    {
        return daog.findById(id);
    }
    /*
    *Affectation
    */
    public int addAffectation(Affectation af)
    {
        return daoaf.create(af);
    }
    public int updateAffectation(Affectation af)
    {
        return daoaf.update(af);
    }
    public List<Affectation> getAllAffectations()
    {
        return daoaf.findAll();
    }
    public Affectation getAffectationByID(int id)
    {
        return daoaf.findById(id);
    }
    public List<Affectation> getAffectationsByAgenece(Agence a)
    {
        List<Affectation> l=getAllAffectations();
        List<Affectation> ll=new ArrayList<>();
        for (Affectation affectation : l) {
            if(affectation.getGuichet().getAgence().getId()==a.getId())
                ll.add(affectation);
        }
        return ll;
    }
    public List<Affectation> getAffectationByCaissier(Utilisateur c)
    {List<Affectation> l=getAllAffectations();
        List<Affectation> as=new ArrayList<>();
        for (Affectation affectation : l) {
            if(affectation.getCaissier().getId()==c.getId())
                as.add(affectation);
        }
        return as;
    }
    public boolean ifCaissierWorks(Utilisateur c)
    {
        if(getAllAffectationsByOneDate(getAffectationByCaissier(c),LocalDate.now()).isEmpty())
            return false;
        return true;
    }
    
    public List<Affectation> getAffectationByGuichet(Guichet g)
    {List<Affectation> l=getAllAffectations();
        List<Affectation> as=new ArrayList<>();
        for (Affectation affectation : l) {
            if(affectation.getGuichet().getId()==g.getId())
                as.add(affectation);
        }
        return as;
    }
    public List<Affectation> getAllAffectationsByOneDate(List<Affectation> l,LocalDate date)
    {
        List<Affectation> ls=new ArrayList<>();
        for (Affectation a : l) {
            List<LocalDate> dates=new ArrayList<>();
            getAllWordkingDates(dates, a.getDatedebut(), a.getDatefin());
            if(dates.contains(date)) 
                ls.add(a);
        }
        return ls;
    }
    public int AnalyseAffectation(Guichet g,Utilisateur u,LocalDate dd,LocalDate df)
    {
        if(dd.isBefore(df)==false)
        {
            return 1;
        }
        else
        {
            List<LocalDate> lw=new ArrayList<>();
            List<LocalDate> lpw=new ArrayList<>();
            List<Affectation> uAffectation=getAffectationByCaissier(u);
            uAffectation.forEach((a) -> {
                getAllWordkingDates(lw,a.getDatedebut(),a.getDatefin());
            });
            List<Affectation> gAffectation=getAffectationByCaissier(u);
            gAffectation.forEach((a) -> {
                getAllWordkingDates(lw,a.getDatedebut(),a.getDatefin());
            });
            getAllWordkingDates(lpw,dd,df);
            for (LocalDate d : lpw) {
                if(lw.contains(d)) {
                   return 2;
                }
            }
                
        }
        return 0;
    }
    public List<LocalDate> getAllWordkingDates(List<LocalDate> l,LocalDate dd,LocalDate df)
    {
        for (LocalDate date = dd; date.isBefore(df.plusDays(1)); date = date.plusDays(1))
        {
            l.add(date);
        }
        return l;
    }
    
    /*
    *Profils
    */
    public int addProfil(Profil p)
    {
        return daop.create(p);
    }
    public Profil getProfilById(int id)
    {
        return daop.findById(id);
    }
    public List<Profil> getAllProfils()
    {
        return daop.findAll();
    }
    public List<Profil> getAllProfilsWoClient()
    {
        List<Profil> l=daop.findAll();
        List<Profil> lp=new ArrayList<>();
        l.forEach((p) -> {
            if(p.getLibelle().compareTo("Client")!=0 && p.getLibelle().compareTo("Administrateur")!=0)
            lp.add(p);
        });
        return lp;
    }
    /*
    *Clients
    */
    public int addClient(Client c)
    {
        return daoc.create(c);
    }
    public List<Client> getAllClients()
    {
        
        return daoc.findAll();
        
    }
    public Client getClientById(int id)
    {
        return daoc.findById(id);
        
    }
    public Client getClientByNci(String nci)
    {
        return daoc.findByNCI(nci);
    }
    
    /*
    *Utilisateur
    *Responsable
    *Caissier
    */
    public int addUtilisateur(Utilisateur u)
    {
        return daou.create(u);
    }
    public List<Utilisateur> getAllUtilisateurs()
    {
        return daou.findAll();
    }
    public List<Utilisateur> getAllUtilisateurByProfil(String profil)
    {
        List<Utilisateur> l=daou.findAll();
        List<Utilisateur> lp=new ArrayList<>();
        l.forEach((user) -> {
            if(user.getProfil().getLibelle().compareToIgnoreCase(profil)==0)
            lp.add(user);
        });
        return lp;
    }
    public List<Utilisateur> listeCaissierAffGuichet()
    {
        List<Utilisateur> lc=new ArrayList<>();
        getAllAffectations().
            forEach(aff->{
                lc.add(aff.getCaissier());
            });
        return lc;
    }
    public int updateUtilisateur(Utilisateur resp)
    {
        return daou.update(resp);
    }
    public Utilisateur getUtilisateurByID(int id)
    {
        return daou.findById(id);
    }
    public Utilisateur seConnecter(Utilisateur user)
    {
        return daou.findByLoginPassword(user);
    }
}
