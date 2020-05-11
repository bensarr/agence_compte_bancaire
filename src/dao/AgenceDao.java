/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Agence;
import models.Utilisateur;

/**
 *
 * @author bensa
 */
public class AgenceDao implements IDao<Agence>{

    private final String SQL_SELECT_ALL="select * from agence";
    //private final String SQL_SELECT_LOGIN_PASSWORD="select * from agence where login=? and password=?";
    private final String SQL_SELECT_ONE="select * from agence where id=?";
    private final String SQL_SELECT_AgenceByResponsable="select * from agence where responsable_id=?";
    private final String SQL_INSERT="INSERT INTO `agence` (`libelle`, `adresse`,`tel`, `responsable_id`) VALUES (?,?,?,?)";
    private final String SQL_UPDATE="UPDATE `agence` SET `libelle` = ?, `adresse` = ?,`tel` = ?, `datecreation` = ?, `responsable_id` = ? WHERE `agence`.`id` = ?";
    private ISGBD  sgbd;
    private UtilisateurDao  daoResponsable;

    public AgenceDao(ISGBD sgbd) {
        this.sgbd = sgbd;
        daoResponsable=new UtilisateurDao(sgbd);
    }
    
    
    @Override
    public int create(Agence objet) {
        int result=0;
        sgbd.initPS(SQL_INSERT);
        try {
            //Date d =objet.getDatecreation();
            sgbd.getPstm().setString(1,objet.getLibelle());
            sgbd.getPstm().setString(2,objet.getAdresse());
            sgbd.getPstm().setString(3,objet.getTel());
            if(objet.getResponsable()!=null)
                sgbd.getPstm().setInt(4,objet.getResponsable().getId());
            else
                sgbd.getPstm().setInt(4,0);
            sgbd.executeMaj();
            ResultSet rs=sgbd.getPstm().getGeneratedKeys();
            if(rs.first()){
                result=rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AgenceDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              sgbd.CloseConnection();
        }
      
        return result;
    }

    @Override
    public int update(Agence objet) {
    int result=0;
        sgbd.initPS(SQL_UPDATE);
        try {
            Date d =objet.getDatecreation();
            sgbd.getPstm().setString(1,objet.getLibelle());
            sgbd.getPstm().setString(2,objet.getAdresse());
            sgbd.getPstm().setString(3,objet.getTel());
            sgbd.getPstm().setDate(4,new java.sql.Date(d.getTime()));
            sgbd.getPstm().setInt(5,objet.getResponsable().getId());
            sgbd.getPstm().setInt(6,objet.getId());
            result=sgbd.executeMaj();
        } catch (SQLException ex) {
            Logger.getLogger(AgenceDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              sgbd.CloseConnection();
        }
      
        return result;
    }
    

    @Override
    public List<Agence> findAll() {
        sgbd.initPS(SQL_SELECT_ALL);
        List<Agence> result=null;
        try {
            //Mapping  objet(Profil) avec Table Profil
             
              ResultSet rs=sgbd.executeSelect();
              result=new ArrayList<>();
              while(rs.next()){
                  Agence a=new Agence();
                  a.setId(rs.getInt("id"));
                  a.setLibelle(rs.getString("libelle"));
                  a.setAdresse(rs.getString("adresse"));
                  a.setTel(rs.getString("tel"));
                  a.setDatecreation(rs.getDate("datecreation"));
                  int id_responsable=rs.getInt("responsable_id");
                   Utilisateur r=daoResponsable.findById(id_responsable);
                   a.setResponsable(r);
                  result.add(a);
              }
        } catch (SQLException ex) {
            Logger.getLogger(AgenceDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return result;
    }

    @Override
    public Agence findById(int id) {
        Agence agence = null;
        sgbd.initPS(SQL_SELECT_ONE);
        try {
            sgbd.getPstm().setInt(1, id);
            ResultSet rs = sgbd.executeSelect();
            if(rs.first()){
                int idAg = rs.getInt("id");
                String libelle = rs.getString("libelle");
                String adresse = rs.getString("adresse");
                String tel = rs.getString("tel");
                //Date datecreation = rs.getDate("datecreation");
                int idResponsable=rs.getInt("responsable_id");
                Utilisateur resp=daoResponsable.findById(idResponsable);
                agence = new Agence(idAg,libelle,adresse,tel,resp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AgenceDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return agence;
    }
    
    public Agence findByResponsable(Utilisateur u) {
        Agence agence = null;
        sgbd.initPS(SQL_SELECT_AgenceByResponsable);
        try {
            sgbd.getPstm().setInt(1, u.getId());
            ResultSet rs = sgbd.executeSelect();
            if(rs.first()){
                int idAg = rs.getInt("id");
                String libelle = rs.getString("libelle");
                String adresse = rs.getString("adresse");
                String tel = rs.getString("tel");
                //Date datecreation = rs.getDate("datecreation");
                int idResponsable=rs.getInt("responsable_id");
                Utilisateur resp=daoResponsable.findById(idResponsable);
                agence = new Agence(idAg,libelle,adresse,tel,resp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AgenceDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return agence;
    }
    
}
