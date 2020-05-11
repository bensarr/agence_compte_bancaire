/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Agence;
import models.Guichet;
import models.Profil;
import models.Utilisateur;

/**
 *
 * @author bensa
 */
public class GuichetDao implements IDao<Guichet>{
    private final String SQL_SELECT_ALL="select * from guichet";
    private final String SQL_SELECT_LOGIN_PASSWORD="select * from guichet where login=? and password=?";
    private final String SQL_SELECT_ONE="select * from guichet where id=?";
    private final String SQL_SELECT_ByAgence="select * from guichet where agence_id=?";
    private final String SQL_INSERT="INSERT INTO `guichet` (`numero`, `etat`, `agence_id`) VALUES (?,?,?)";
    private final String SQL_UPDATE="UPDATE `guichet` SET `numero` = ?, `etat` = ?, `agence_id` = ? WHERE `guichet`.`id` = ?";
    private ISGBD  sgbd;
    private AgenceDao  daoAgence;

    public GuichetDao(ISGBD sgbd) {
        this.sgbd = sgbd;
        daoAgence=new AgenceDao(sgbd);
    }

    @Override
    public int create(Guichet objet) {
        int result=0;
        sgbd.initPS(SQL_INSERT);
        try {
            sgbd.getPstm().setString(1,objet.getNumero());
            sgbd.getPstm().setString(2,objet.getEtat());
            sgbd.getPstm().setInt(3,objet.getAgence().getId());
            sgbd.executeMaj();
            ResultSet rs=sgbd.getPstm().getGeneratedKeys();
            if(rs.first()){
                result=rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(GuichetDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              sgbd.CloseConnection();
        }
      
        return result;
    }

    @Override
    public int update(Guichet objet) {
        int result=0;
        sgbd.initPS(SQL_UPDATE);
        try {
            sgbd.getPstm().setString(1,objet.getNumero());
            sgbd.getPstm().setString(2,objet.getEtat());
            sgbd.getPstm().setInt(3,objet.getAgence().getId());
            sgbd.getPstm().setInt(4,objet.getId());
            result=sgbd.executeMaj();
        } catch (SQLException ex) {
            Logger.getLogger(GuichetDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              sgbd.CloseConnection();
        }
      
        return result;
    }

    @Override
    public List<Guichet> findAll() {
        sgbd.initPS(SQL_SELECT_ALL);
           List<Guichet> result=null;
        try {
            //Mapping  objet(Profil) avec Table Profil
             
              ResultSet rs=sgbd.executeSelect();
              result=new ArrayList<>();
              while(rs.next()){
                  Guichet g=new Guichet();
                  g.setId(rs.getInt("id"));
                  g.setNumero(rs.getString("numero"));
                  g.setEtat(rs.getString("etat"));
                  int id_agence=rs.getInt("agence_id");
                  Agence a=daoAgence.findById(id_agence);
                  g.setAgence(a);
                  result.add(g);
              }
        } catch (SQLException ex) {
            Logger.getLogger(GuichetDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return result;
    }

    @Override
    public Guichet findById(int id) {
        Guichet guichet = null;
        sgbd.initPS(SQL_SELECT_ONE);
        try {
            sgbd.getPstm().setInt(1, id);
            ResultSet rs = sgbd.executeSelect();
            if(rs.first()){
                int idG = rs.getInt("id");
                String numero = rs.getString("numero");
                String etat = rs.getString("etat");
                int idAgence=rs.getInt("agence_id");
                Agence agence=daoAgence.findById(idAgence);
                guichet = new Guichet(idG,numero,etat,agence);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateurDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return guichet;
    }
    
    public List<Guichet> findAllByAgence(Agence agence) {
        sgbd.initPS(SQL_SELECT_ByAgence);
           List<Guichet> result=null;
        try {
            //Mapping  objet(Profil) avec Table Profil
              sgbd.getPstm().setInt(1, agence.getId());
              ResultSet rs=sgbd.executeSelect();
              result=new ArrayList<>();
              while(rs.next()){
                  Guichet g=new Guichet();
                  g.setId(rs.getInt("id"));
                  g.setNumero(rs.getString("numero"));
                  g.setEtat(rs.getString("etat"));
                  int id_agence=rs.getInt("agence_id");
                  Agence a=daoAgence.findById(id_agence);
                  g.setAgence(a);
                  result.add(g);
              }
        } catch (SQLException ex) {
            Logger.getLogger(GuichetDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return result;
    }
    
}
