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
import models.Profil;
import models.Utilisateur;

/**
 *
 * @author user
 */
public class UtilisateurDao implements IDao <Utilisateur> {

    private final String SQL_SELECT_ALL="select * from utilisateur";
    private final String SQL_SELECT_LOGIN_PASSWORD="select * from utilisateur where login=? and password=?";
    private final String SQL_SELECT_ONE="select * from utilisateur where id=?";
    private final String SQL_INSERT="INSERT INTO `utilisateur` (`nom`, `prenom`, `login`, `password`, `etat`, `avoiragence`, `profil_id`) VALUES (?,?,?,?,?,?,?)";
    private final String SQL_UPDATE="UPDATE `utilisateur` SET `nom` = ?, `prenom` = ?,`password` = ?, `etat` = ?,`avoiragence` = ?, `profil_id` = ? WHERE `utilisateur`.`id` = ?";
        private ISGBD  sgbd;
        private ProfilDao  daoProfil;
    public UtilisateurDao(ISGBD sgbd) {
        this.sgbd=sgbd;
        daoProfil=new ProfilDao(sgbd);
    }
   
    
    @Override
    public int create(Utilisateur objet) {
        int result=0;
        
        sgbd.initPS(SQL_INSERT);
        try {
            sgbd.getPstm().setString(1,objet.getNom());
            sgbd.getPstm().setString(2,objet.getPrenom());
            sgbd.getPstm().setString(3,objet.getLogin());
            sgbd.getPstm().setString(4,objet.getPassword());
            sgbd.getPstm().setString(5,"Inactif");
            if(objet.getProfil().getLibelle().compareTo("Responsable")==0||objet.getProfil().getLibelle().compareTo("Caissier")==0)
            {
                objet.setAvoiragence("Non");
                sgbd.getPstm().setString(6,objet.getAvoiragence());                
            }else
            {
                objet.setAvoiragence("");
                sgbd.getPstm().setString(6,objet.getAvoiragence());  
            }
            sgbd.getPstm().setInt(7,objet.getProfil().getId());
            sgbd.executeMaj();
            ResultSet rs=sgbd.getPstm().getGeneratedKeys();
            if(rs.first()){
                result=rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateurDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              sgbd.CloseConnection();
        }
      
        return result;
    }

    @Override
    public int update(Utilisateur objet) {
        int result=0;
        sgbd.initPS(SQL_UPDATE);
        try {
            sgbd.getPstm().setString(1,objet.getNom());
            sgbd.getPstm().setString(2,objet.getPrenom());
            sgbd.getPstm().setString(3,objet.getPassword());
            sgbd.getPstm().setString(4,objet.getEtat());
            if(objet.getProfil().getLibelle().compareTo("Responsable")==0||objet.getProfil().getLibelle().compareTo("Caissier")==0)
            {
                objet.setAvoiragence("Non");
                sgbd.getPstm().setString(5,objet.getAvoiragence());
            }
            else
            {
                objet.setAvoiragence("");
                sgbd.getPstm().setString(5,objet.getAvoiragence());  
            }
            sgbd.getPstm().setInt(6,objet.getProfil().getId());
            sgbd.getPstm().setInt(7,objet.getId());
            result=sgbd.executeMaj();
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateurDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              sgbd.CloseConnection();
        }
      
        return result;
    }

    @Override
    public List<Utilisateur> findAll() {
        sgbd.initPS(SQL_SELECT_ALL);
           List<Utilisateur> result=null;
        try {
            //Mapping  objet(Profil) avec Table Profil
             
              ResultSet rs=sgbd.executeSelect();
              result=new ArrayList<>();
              while(rs.next()){
                Utilisateur u=new Utilisateur();
                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setLogin(rs.getString("login"));
                u.setPassword(rs.getString("password"));
                u.setEtat(rs.getString("etat"));
                int id_profil=rs.getInt("profil_id");
                Profil p=daoProfil.findById(id_profil);
                u.setProfil(p);
                if(p.getLibelle().compareTo("Responsable")==0||p.getLibelle().compareTo("Caissier")==0)
                {
                  u.setAvoiragence(rs.getString("avoiragence"));               
                }
                result.add(u);
              }
            

        } catch (SQLException ex) {
            Logger.getLogger(UtilisateurDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return result;
    }

    @Override
    public Utilisateur findById(int id) {
        Utilisateur utilisateur = null;
        sgbd.initPS(SQL_SELECT_ONE);
        try {
            sgbd.getPstm().setInt(1, id);
            ResultSet rs = sgbd.executeSelect();
            if(rs.first()){
                int idU = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String etat= rs.getString("etat");
                int idProfil=rs.getInt("profil_id");
                Profil profil=daoProfil.findById(idProfil);
                utilisateur = new Utilisateur(idU,nom,prenom,login,password,etat,profil);
                if(profil.getLibelle().compareTo("Responsable")==0||profil.getLibelle().compareTo("Caissier")==0)
                {
                  utilisateur.setAvoiragence(rs.getString("avoiragence"));               
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateurDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return utilisateur;
    }
    
    public Utilisateur findByLoginPassword(Utilisateur user){
        sgbd.initPS(SQL_SELECT_LOGIN_PASSWORD);
           Utilisateur u=null;
        try {
            //Mapping  objet(Profil) avec Table Profil
             sgbd.getPstm().setString(1, user.getLogin());
             sgbd.getPstm().setString(2, user.getPassword());
              ResultSet rs=sgbd.executeSelect();
              if(rs.next()){
                 u=new Utilisateur();
                  u.setId(rs.getInt("id"));
                  u.setNom(rs.getString("nom"));
                  u.setPrenom(rs.getString("prenom"));
                  u.setLogin(rs.getString("login"));
                  u.setPassword(rs.getString("password"));
                  u.setEtat(rs.getString("etat"));
                  int id_profil=rs.getInt("profil_id");
                  Profil p=daoProfil.findById(id_profil);
                  if(p.getLibelle().compareTo("Responsable")==0||p.getLibelle().compareTo("Caissier")==0)
                  {
                    u.setAvoiragence(rs.getString("avoiragence"));               
                  }
                   u.setProfil(p);
              }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateurDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return u;
    }
    
}
