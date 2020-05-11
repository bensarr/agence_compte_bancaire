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
//import models.Guichet;

/**
 *
 * @author bensa
 */
public class CompteDao implements IDao<Compte>{
    private final String SQL_SELECT_ALL="select * from compte";
    private final String SQL_SELECT_NUMERO="select * from compte where numero=?";
    private final String SQL_SELECT_ONE="select * from compte where id=?";
    private final String SQL_INSERT="INSERT INTO `compte` (`numero`,`solde`, `etat`, `client_id`, `agence_id`) VALUES (?,?,?,?,?)";
    private final String SQL_UPDATE="UPDATE `compte` SET `solde` = ?, `etat` = ? WHERE `compte`.`id` = ?";
    private ISGBD  sgbd;
    private ClientDao  daoClient;
    private AgenceDao  daoAgence;

    public CompteDao(ISGBD sgbd) {
        this.sgbd = sgbd;
        this.daoClient=new ClientDao(sgbd);
        this.daoAgence=new AgenceDao(sgbd);
    }
    
    
    
    @Override
    public int create(Compte objet) {
        int result=0;
        sgbd.initPS(SQL_INSERT);
        try {
            sgbd.getPstm().setString(1,objet.getNumero());
            sgbd.getPstm().setDouble(2,objet.getSolde());
            sgbd.getPstm().setString(3,objet.getEtat());
            sgbd.getPstm().setInt(4,objet.getClient().getId());
            sgbd.getPstm().setInt(5,objet.getAgence().getId());
            sgbd.executeMaj();
            ResultSet rs=sgbd.getPstm().getGeneratedKeys();
            if(rs.first()){
                result=rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CompteDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              sgbd.CloseConnection();
        }
      
        return result;
    }

    @Override
    public int update(Compte objet) {
        int result=0;
        sgbd.initPS(SQL_UPDATE);
        try {
            sgbd.getPstm().setDouble(1,objet.getSolde());
            sgbd.getPstm().setString(2,objet.getEtat());
            sgbd.getPstm().setInt(3,objet.getId());
            result=sgbd.executeMaj();
        } catch (SQLException ex) {
            Logger.getLogger(GuichetDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              sgbd.CloseConnection();
        }
      
        return result;
    }

    @Override
    public List<Compte> findAll() {
        sgbd.initPS(SQL_SELECT_ALL);
           List<Compte> result=null;
        try {
            //Mapping  objet(Profil) avec Table Profil
             
              ResultSet rs=sgbd.executeSelect();
              result=new ArrayList<>();
              while(rs.next()){
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
                  result.add(c);
              }
        } catch (SQLException ex) {
            Logger.getLogger(CompteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return result;
    }

    @Override
    public Compte findById(int id) {
        Compte compte = null;
        sgbd.initPS(SQL_SELECT_ONE);
        try {
            sgbd.getPstm().setInt(1, id);
            ResultSet rs = sgbd.executeSelect();
            if(rs.first()){
                int idC = rs.getInt("id");
                String numero = rs.getString("numero");
                double solde = rs.getDouble("solde");
                LocalDate datecreation = rs.getDate("datecreation").toLocalDate();
                String etat = rs.getString("etat");
                int idClient=rs.getInt("client_id");
                Client client=daoClient.findById(idClient);
                int idAgence=rs.getInt("agence_id");
                Agence agence=daoAgence.findById(idAgence);
                compte = new Compte(idC,numero,solde,datecreation,etat,client,agence);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CompteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return compte;
    }
    public Compte findByNumero(String num){
        sgbd.initPS(SQL_SELECT_NUMERO);
           Compte c=null;
        try {
             sgbd.getPstm().setString(1, num);
              ResultSet rs=sgbd.executeSelect();
              if(rs.next()){
                c=new Compte();
                int idC = rs.getInt("id");
                String numero = rs.getString("numero");
                double solde = rs.getDouble("solde");
                LocalDate datecreation = rs.getDate("datecreation").toLocalDate();
                String etat = rs.getString("etat");
                int idClient=rs.getInt("client_id");
                Client client=daoClient.findById(idClient);
                int idAgence=rs.getInt("agence_id");
                Agence agence=daoAgence.findById(idAgence);
                c = new Compte(idC,numero,solde,datecreation,etat,client,agence);
              }
        } catch (SQLException ex) {
            Logger.getLogger(CompteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return c;
    }
    
}
