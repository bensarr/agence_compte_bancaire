/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Agence;
import models.Client;
import models.Compte;
import models.Epargne;

/**
 *
 * @author bensa
 */
public class EpargneDao implements IDao<Epargne>{
    private final String SQL_SELECT_ALL="SELECT e.*,cpt.* FROM epargne e JOIN compte cpt on cpt.id=e.id";
    //private final String SQL_SELECT_LOGIN_PASSWORD="select * from client where login=? and password=?";
    private final String SQL_SELECT_ONE="SELECT e.*,cpt.* FROM epargne e JOIN compte cpt on cpt.id=e.id WHERE e.id=?";
    private final String SQL_INSERT="INSERT INTO `epargne` (`id`, `taux_interet`, `date_fin_blocage`) VALUES (?, ?, ?)";
    private final String SQL_UPDATE="UPDATE `epargne` SET `taux_interet` = ?,`date_fin_blocage` = ? WHERE `epargne`.`id` = ?";
    private ISGBD  sgbd;
    private CompteDao  daoCompte;
    private ClientDao  daoClient;
    private AgenceDao  daoAgence;

    public EpargneDao(ISGBD sgbd) {
        this.sgbd = sgbd;
        this.daoCompte = new CompteDao(sgbd);
        this.daoClient=new ClientDao(sgbd);
        this.daoAgence=new AgenceDao(sgbd);
    }
    

    @Override
    public int create(Epargne objet) {
        int idCmpt =daoCompte.create(objet);
        int dern_id=0;
        sgbd.initPS(SQL_INSERT);
        try {
            LocalDate df = objet.getDate_fin_blocage();
            sgbd.getPstm().setInt(1,idCmpt);
            sgbd.getPstm().setDouble(2,objet.getTaux_interet());
            sgbd.getPstm().setDate(3,java.sql.Date.valueOf(df));
            sgbd.executeMaj();
            ResultSet rs=sgbd.getPstm().getGeneratedKeys();
            if(rs.next()){
                dern_id = rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EpargneDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              sgbd.CloseConnection();
        }
      
        return dern_id;
    }

    @Override
    public int update(Epargne objet) {
        daoCompte.update(objet);
        int ok=0;
        sgbd.initPS(SQL_UPDATE);
        try {
            LocalDate df = objet.getDate_fin_blocage();
            sgbd.getPstm().setDouble(1,objet.getTaux_interet());
            sgbd.getPstm().setDate(2,java.sql.Date.valueOf(df));
            sgbd.getPstm().setInt(3, objet.getId());
            if(sgbd.executeMaj()==1) ok=1;
        } catch (SQLException ex) {
            Logger.getLogger(EpargneDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return ok;
    }

    @Override
    public List<Epargne> findAll() {
        sgbd.initPS(SQL_SELECT_ALL);
        List<Epargne> resultats=null;
        try {
            ResultSet rs = sgbd.executeSelect();
            resultats=new ArrayList<>();
            while(rs.next()){
                int id = rs.getInt("id");
                double taux_interet = rs.getDouble("taux_interet");
                LocalDate date_fin_blocage = rs.getDate("date_fin_blocage").toLocalDate();
                
                Compte c=new Compte();
                  c.setId(rs.getInt("id"));
                  c.setNumero(rs.getString("numero"));
                  c.setSolde(rs.getDouble("solde"));
                  c.setDatecreation(rs.getDate("datecreation").toLocalDate());
                  c.setEtat(rs.getString("etat"));
                  int id_client=rs.getInt("client_id");
                  Client cl=daoClient.findById(id_client);
                  c.setClient(cl);
                  int id_agence=rs.getInt("agence_id");
                  Agence a=daoAgence.findById(id_agence);
                  c.setAgence(a);
                
                Epargne epargne = new Epargne(c.getId(),  c.getNumero(), c.getSolde(), c.getEtat(), cl, a,taux_interet,date_fin_blocage,id);
                resultats.add(epargne);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EpargneDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return resultats;
    }

    @Override
    public Epargne findById(int id) {
        Epargne epargne = null;
        sgbd.initPS(SQL_SELECT_ONE);
        try {
            sgbd.getPstm().setInt(1, id);
            ResultSet rs = sgbd.executeSelect();
            if(rs.first()){
                int idc = rs.getInt("id");
                double taux_interet = rs.getDouble("taux_interet");
                LocalDate date_fin_blocage = rs.getDate("date_fin_blocage").toLocalDate();
                
                Compte c=new Compte();
                  c.setId(rs.getInt("id"));
                  c.setNumero(rs.getString("numero"));
                  c.setSolde(rs.getDouble("solde"));
                  c.setDatecreation(rs.getDate("datecreation").toLocalDate());
                  c.setEtat(rs.getString("etat"));
                  int id_client=rs.getInt("client_id");
                  Client cl=daoClient.findById(id_client);
                  c.setClient(cl);
                  int id_agence=rs.getInt("agence_id");
                  Agence a=daoAgence.findById(id_agence);
                  c.setAgence(a);
                
                epargne = new Epargne(c.getId(),  c.getNumero(), c.getSolde(), c.getEtat(), cl, a,taux_interet,date_fin_blocage,id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EpargneDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return epargne;
    }
    
}
