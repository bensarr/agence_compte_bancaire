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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Affectation;
import models.Agence;
import models.Guichet;
import models.Utilisateur;

/**
 *
 * @author bensa
 */
public class AffectationDao implements IDao<Affectation>{
    private final String SQL_SELECT_ALL="select * from affectation";
    //private final String SQL_SELECT_LOGIN_PASSWORD="select * from affectation where login=? and password=?";
    private final String SQL_SELECT_ONE="select * from affectation where id=?";
    private final String SQL_INSERT="INSERT INTO `affectation` (`datedebut`, `datefin`,`caissier_id`, `guichet_id`) VALUES (?,?,?,?)";
    private final String SQL_UPDATE="UPDATE `affectation` SET `datedebut` = ?, `datefin` = ?, `caissier_id` = ?, `guichet_id` = ? WHERE `affectation`.`id` = ?";
    private ISGBD  sgbd;
    private UtilisateurDao  daoCaissier;
    private GuichetDao  daoGuichet;

    public AffectationDao(ISGBD sgbd) {
        this.sgbd = sgbd;
        daoCaissier=new UtilisateurDao(sgbd);
        daoGuichet= new GuichetDao(sgbd);
    }

    @Override
    public int create(Affectation objet) {
        int result=0;
        sgbd.initPS(SQL_INSERT);
        try {
            LocalDate dd = objet.getDatedebut();
            LocalDate df = objet.getDatefin();
            sgbd.getPstm().setDate(1,java.sql.Date.valueOf(dd));
            sgbd.getPstm().setDate(2,java.sql.Date.valueOf(df));
            sgbd.getPstm().setInt(3,objet.getCaissier().getId());
            sgbd.getPstm().setInt(4,objet.getGuichet().getId());
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
    public int update(Affectation objet) {
        int result=0;
        sgbd.initPS(SQL_UPDATE);
        try {
            LocalDate dd = objet.getDatedebut();
            LocalDate df = objet.getDatefin();
            sgbd.getPstm().setDate(1,java.sql.Date.valueOf(dd));
            sgbd.getPstm().setDate(2,java.sql.Date.valueOf(df));
            sgbd.getPstm().setInt(3,objet.getCaissier().getId());
            sgbd.getPstm().setInt(4,objet.getGuichet().getId());
            sgbd.getPstm().setInt(5,objet.getId());
            result=sgbd.executeMaj();
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateurDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              sgbd.CloseConnection();
        }
      
        return result;
    }

    @Override
    public List<Affectation> findAll() {
        sgbd.initPS(SQL_SELECT_ALL);
        List<Affectation> result=null;
        try {
            //Mapping  objet(Profil) avec Table Profil
             
              ResultSet rs=sgbd.executeSelect();
              result=new ArrayList<>();
              while(rs.next()){
                  Affectation a=new Affectation();
                  a.setId(rs.getInt("id"));
                  a.setDatedebut(rs.getDate("datedebut").toLocalDate());
                  a.setDatefin(rs.getDate("datefin").toLocalDate());
                  int id_caissier=rs.getInt("caissier_id");
                  Utilisateur c=daoCaissier.findById(id_caissier);
                  int id_guichet=rs.getInt("guichet_id");
                  Guichet g=daoGuichet.findById(id_guichet);
                  a.setCaissier(c);
                  a.setGuichet(g);
                  result.add(a);
              }
        } catch (SQLException ex) {
            Logger.getLogger(ProfilDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return result;
    }

    @Override
    public Affectation findById(int id) {
        Affectation affectation = null;
        sgbd.initPS(SQL_SELECT_ONE);
        try {
            sgbd.getPstm().setInt(1, id);
            ResultSet rs = sgbd.executeSelect();
            if(rs.first()){
                int idAf = rs.getInt("id");
                LocalDate datedebut = rs.getDate("datedebut").toLocalDate();
                LocalDate datefin = rs.getDate("datefin").toLocalDate();
                int idCaissier=rs.getInt("caissier_id");
                int idGuichet=rs.getInt("guichet_id");
                Utilisateur caissier=daoCaissier.findById(idCaissier);
                Guichet guichet=daoGuichet.findById(idGuichet);
                affectation = new Affectation(idAf,datedebut,datefin,caissier,guichet);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateurDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return affectation;
    }
    
}
