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
import models.Profil;

/**
 *
 * @author user
 */
public class ProfilDao implements IDao<Profil> {
     
    private final String SQL_SELECT_ALL="select * from profil";
    private final String SQL_SELECT_ONE="select * from profil where id=?";
    private final String SQL_INSERT="INSERT INTO profil (libelle ) VALUES (?)";
    private final String SQL_UPDATE="UPDATE `profil` SET `libelle` = ? WHERE `profil`.`id` = ?";

    private ISGBD  sgbd;
    
    public ProfilDao(ISGBD sgbd) {
        this.sgbd=sgbd;
    }
    
    @Override
    public int create(Profil objet) {
           sgbd.initPS(SQL_INSERT);
           int id=0;
        try {
            //Mapping  objet(Profil) avec Table Profil
            sgbd.getPstm().setString(1,objet.getLibelle());
            sgbd.executeMaj(); 
             
            ResultSet rs=sgbd.getPstm().getGeneratedKeys();
            if(rs.next()) id= rs.getInt(1);
             
        } catch (SQLException ex) {
            Logger.getLogger(ProfilDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return id;
    }

    @Override
    public int update(Profil objet) {
        sgbd.initPS(SQL_UPDATE);
           int id=0;
        try {
            //Mapping  objet(Profil) avec Table Profil
              sgbd.getPstm().setString(1,objet.getLibelle());
              sgbd.getPstm().setInt(2,objet.getId());
             id=sgbd.executeMaj(); 

        } catch (SQLException ex) {
            Logger.getLogger(ProfilDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return id;
    }

    @Override
    public List<Profil> findAll() {
       sgbd.initPS(SQL_SELECT_ALL);
           List<Profil> result=null;
        try {
            //Mapping  objet(Profil) avec Table Profil
             
              ResultSet rs=sgbd.executeSelect();
              result=new ArrayList<>();
              while(rs.next()){
                  Profil p=new Profil();
                  p.setId(rs.getInt("id"));
                  p.setLibelle(rs.getString("libelle"));
                  result.add(p);
              }
            

        } catch (SQLException ex) {
            Logger.getLogger(ProfilDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return result;
    }

    @Override
    public Profil findById(int id) {
        sgbd.initPS(SQL_SELECT_ONE);
           Profil result=null;
        try {
            //Mapping  objet(Profil) avec Table Profil
              sgbd.getPstm().setInt(1,id);
              ResultSet rs=sgbd.executeSelect();
              if(rs.first()){
                  result=new Profil();
                  result.setId(rs.getInt("id"));
                  result.setLibelle(rs.getString("libelle"));
              }
            

        } catch (SQLException ex) {
            Logger.getLogger(ProfilDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return result;
    }
    
}
