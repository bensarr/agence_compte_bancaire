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
import models.Client;
import models.Compte;
import models.Courant;

/**
 *
 * @author bensa
 */
public class CourantDao implements IDao<Courant>{
    private final String SQL_SELECT_ALL="SELECT c.*,cpt.* FROM courant c JOIN compte cpt on cpt.id=c.id";
    //private final String SQL_SELECT_LOGIN_PASSWORD="select * from client where login=? and password=?";
    private final String SQL_SELECT_ONE="SELECT c.*,cpt.* FROM courant c JOIN compte cpt on cpt.id=c.id WHERE c.id=?";
    private final String SQL_INSERT="INSERT INTO `courant` (`id`, `frais_tenue`) VALUES (?,?)";
    private final String SQL_UPDATE="UPDATE `courant` SET `frais_tenue` = ? WHERE `courant`.`id` = ?";
    private ISGBD  sgbd;
    private CompteDao  daoCompte;
    private ClientDao  daoClient;
    private AgenceDao  daoAgence;

    public CourantDao(ISGBD sgbd) {
        this.sgbd = sgbd;
        this.daoCompte = new CompteDao(sgbd);
        this.daoClient=new ClientDao(sgbd);
        this.daoAgence=new AgenceDao(sgbd);
    }
    

    @Override
    public int create(Courant objet) {
        int idCmpt =daoCompte.create(objet);
        int dern_id=0;
        sgbd.initPS(SQL_INSERT);
        try {
            sgbd.getPstm().setInt(1,idCmpt);
            sgbd.getPstm().setDouble(2,objet.getFrais_tenue());
            sgbd.executeMaj();
            ResultSet rs=sgbd.getPstm().getGeneratedKeys();
            if(rs.next()){
                dern_id = rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CourantDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              sgbd.CloseConnection();
        }
      
        return dern_id;
    }

    @Override
    public int update(Courant objet) {
        daoCompte.update(objet);
        int ok=0;
        sgbd.initPS(SQL_UPDATE);
        try {
            sgbd.getPstm().setDouble(1,objet.getFrais_tenue());
            sgbd.getPstm().setInt(2, objet.getId());
            if(sgbd.executeMaj()==1) ok=1;
        } catch (SQLException ex) {
            Logger.getLogger(CourantDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return ok;
    }

    @Override
    public List<Courant> findAll() {
        sgbd.initPS(SQL_SELECT_ALL);
        List<Courant> resultats=null;
        try {
            ResultSet rs = sgbd.executeSelect();
            resultats=new ArrayList<>();
            while(rs.next()){
                int id = rs.getInt("id");
                double frais_tenue = rs.getDouble("frais_tenue");
                
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
                
                Courant courant = new Courant(c.getId(),  c.getNumero(), c.getSolde(), c.getEtat(), cl, a,frais_tenue, id);
                
                resultats.add(courant);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourantDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return resultats;
    }

    @Override
    public Courant findById(int id) {
        Courant courant = null;
        sgbd.initPS(SQL_SELECT_ONE);
        try {
            sgbd.getPstm().setInt(1, id);
            ResultSet rs = sgbd.executeSelect();
            if(rs.first()){
                int idU = rs.getInt("id");
                double frais_tenue = rs.getDouble("frais_tenue");
                
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
                
                courant = new Courant(c.getId(),  c.getNumero(), c.getSolde(), c.getEtat(), cl, a,frais_tenue, id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return courant;
    }
    
}
