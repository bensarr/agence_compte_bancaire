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
import models.AffectationCaissier;
import models.Agence;
import models.Utilisateur;

/**
 *
 * @author bensa
 */
public class AffectationCaissierDao implements IDao<AffectationCaissier>{
    
    private final String SQL_SELECT_ALL="select * from affectationagence";
    //private final String SQL_SELECT_LOGIN_PASSWORD="select * from affectation where login=? and password=?";
    private final String SQL_SELECT_ONE="select * from affectationagence where id=?";
    private final String SQL_SELECT_ByAgence="select * from affectationagence where agence_id=?";
    private final String SQL_SELECT_ByCaissier="select * from affectationagence where caissier_id=?";
    private final String SQL_INSERT="INSERT INTO `affectationagence` (`caissier_id`, `agence_id`) VALUES (?,?)";
    private final String SQL_UPDATE="UPDATE `affectationagence` SET `caissier_id` = ?, `agence_id` = ? WHERE `affectationagence`.`id` = ?";
    private ISGBD  sgbd;
    private UtilisateurDao  daoCaissier;
    private AgenceDao  daoAgence;

    public AffectationCaissierDao(ISGBD sgbd) {
        this.sgbd = sgbd;
        this.daoCaissier = new UtilisateurDao(sgbd);
        this.daoAgence = new AgenceDao(sgbd);
    }
    
    

    @Override
    public int create(AffectationCaissier objet) {
        int result=0;
        sgbd.initPS(SQL_INSERT);
        try {
            sgbd.getPstm().setInt(1,objet.getCaissier().getId());
            sgbd.getPstm().setInt(2,objet.getAgence().getId());
            sgbd.executeMaj();
            ResultSet rs=sgbd.getPstm().getGeneratedKeys();
            if(rs.first()){
                result=rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AffectationCaissierDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              sgbd.CloseConnection();
        }
      
        return result;
    }

    @Override
    public int update(AffectationCaissier objet) {

        int result=0;
        sgbd.initPS(SQL_UPDATE);
        try {
            sgbd.getPstm().setInt(1,objet.getCaissier().getId());
            sgbd.getPstm().setInt(2,objet.getAgence().getId());
            sgbd.getPstm().setInt(3,objet.getId());
            result=sgbd.executeMaj();
        } catch (SQLException ex) {
            Logger.getLogger(AffectationCaissierDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              sgbd.CloseConnection();
        }
      
        return result;
    }

    @Override
    public List<AffectationCaissier> findAll() {

        sgbd.initPS(SQL_SELECT_ALL);
        List<AffectationCaissier> result=null;
        try {
            //Mapping  objet(Profil) avec Table Profil
             
              ResultSet rs=sgbd.executeSelect();
              result=new ArrayList<>();
              while(rs.next()){
                  AffectationCaissier a=new AffectationCaissier();
                  a.setId(rs.getInt("id"));
                  int id_caissier=rs.getInt("caissier_id");
                  Utilisateur c=daoCaissier.findById(id_caissier);
                  int id_agence=rs.getInt("agence_id");
                  Agence ag=daoAgence.findById(id_agence);
                  a.setCaissier(c);
                  a.setAgence(ag);
                  result.add(a);
              }
        } catch (SQLException ex) {
            Logger.getLogger(AffectationCaissierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return result;
    }

    @Override
    public AffectationCaissier findById(int id) {

        AffectationCaissier affectation = null;
        sgbd.initPS(SQL_SELECT_ONE);
        try {
            sgbd.getPstm().setInt(1, id);
            ResultSet rs = sgbd.executeSelect();
            if(rs.first()){
                int idAf = rs.getInt("id");
                int idCaissier=rs.getInt("caissier_id");
                int idAgence=rs.getInt("agence_id");
                Utilisateur caissier=daoCaissier.findById(idCaissier);
                Agence agence=daoAgence.findById(idAgence);
                affectation = new AffectationCaissier(idAf,caissier,agence);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AffectationCaissierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return affectation;
    }
    public AffectationCaissier findByCaissier(Utilisateur u) {

        AffectationCaissier affectation = null;
        sgbd.initPS(SQL_SELECT_ByCaissier);
        try {
            sgbd.getPstm().setInt(1, u.getId());
            ResultSet rs = sgbd.executeSelect();
            if(rs.first()){
                int idAf = rs.getInt("id");
                int idCaissier=rs.getInt("caissier_id");
                int idAgence=rs.getInt("agence_id");
                Utilisateur caissier=daoCaissier.findById(idCaissier);
                Agence agence=daoAgence.findById(idAgence);
                affectation = new AffectationCaissier(idAf,caissier,agence);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AffectationCaissierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return affectation;
    }
    public List<AffectationCaissier> findAllByAgence(Agence agence) {

        sgbd.initPS(SQL_SELECT_ByAgence);
        List<AffectationCaissier> result=null;
        try {
            //Mapping  objet(Profil) avec Table Profil
              sgbd.getPstm().setInt(1, agence.getId());
              ResultSet rs=sgbd.executeSelect();
              result=new ArrayList<>();
              while(rs.next()){
                  AffectationCaissier a=new AffectationCaissier();
                  a.setId(rs.getInt("id"));
                  int id_caissier=rs.getInt("caissier_id");
                  Utilisateur c=daoCaissier.findById(id_caissier);
                  int id_agence=rs.getInt("agence_id");
                  Agence ag=daoAgence.findById(id_agence);
                  a.setCaissier(c);
                  a.setAgence(ag);
                  result.add(a);
              }
        } catch (SQLException ex) {
            Logger.getLogger(AffectationCaissierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return result;
    }
    
}
