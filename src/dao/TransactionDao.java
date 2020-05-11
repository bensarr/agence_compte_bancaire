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
import models.Compte;
import models.Guichet;
import models.Transaction;

/**
 *
 * @author bensa
 */
public class TransactionDao implements IDao<Transaction>{
    private final String SQL_SELECT_ALL="select * from transaction";
    //private final String SQL_SELECT_LOGIN_PASSWORD="select * from transaction where login=? and password=?";
    private final String SQL_SELECT_ONE="select * from transaction where id=?";
    private final String SQL_INSERT="INSERT INTO `transaction` (`type`, `montant`, `compte_source`, `compte_cible`, `guichet_id`) VALUES (?,?,?,?,?)";
    private final String SQL_UPDATE="UPDATE `transaction` SET `type` = ?, `montant` = ?, `date` = ?, `compte_source` = ?, `compte_cible` = ? WHERE `transaction`.`id` = ?";
    private ISGBD  sgbd;
    private CompteDao  daoCompte_Source,daoCompte_Cible;
    private GuichetDao  daoGuichet;

    public TransactionDao(ISGBD sgbd) {
        this.sgbd = sgbd;
        this.daoCompte_Source = new CompteDao(sgbd);
        this.daoCompte_Cible = new CompteDao(sgbd);
        this.daoGuichet=new GuichetDao(sgbd);
    }

    @Override
    public int create(Transaction objet) {
        int result=0;
        sgbd.initPS(SQL_INSERT);
        try {
            //Date d =objet.getDatecreation();
            sgbd.getPstm().setString(1,objet.getType());
            sgbd.getPstm().setDouble(2,objet.getMontant());
            sgbd.getPstm().setInt(3,objet.getCompte_source().getId());
            if(objet.getCompte_cible()!=null)
                sgbd.getPstm().setInt(4,objet.getCompte_cible().getId());
            else
                sgbd.getPstm().setInt(4,0);
                
            //sgbd.getPstm().setDate(4,new java.sql.Date(d.getTime()));
            if(objet.getGuichet()!=null)
                sgbd.getPstm().setInt(5,objet.getGuichet().getId());
            else
                sgbd.getPstm().setInt(5,0);
            sgbd.executeMaj();
            ResultSet rs=sgbd.getPstm().getGeneratedKeys();
            if(rs.first()){
                result=rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              sgbd.CloseConnection();
        }
      
        return result;
    }

    @Override
    public int update(Transaction objet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Transaction> findAll() {
        sgbd.initPS(SQL_SELECT_ALL);
        List<Transaction> result=null;
        try {
            //Mapping  objet(Profil) avec Table Profil
             
              ResultSet rs=sgbd.executeSelect();
              result=new ArrayList<>();
              while(rs.next()){
                  Transaction t=new Transaction();
                  t.setId(rs.getInt("id"));
                  t.setType(rs.getString("type"));
                  t.setMontant(rs.getDouble("montant"));
                  t.setDate(rs.getDate("date").toLocalDate());
                  int id_Compte_Source=rs.getInt("compte_source");
                  Compte source=daoCompte_Source.findById(id_Compte_Source);
                  t.setCompte_source(source);
                  int id_Compte_Cible=rs.getInt("compte_cible");
                  Compte cible=daoCompte_Cible.findById(id_Compte_Cible);
                  t.setCompte_cible(cible);
                  int id_Guichet=rs.getInt("guichet_id");
                  Guichet guichet=daoGuichet.findById(id_Guichet);
                  t.setGuichet(guichet);
                  result.add(t);
              }
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return result;
    }

    @Override
    public Transaction findById(int id) {
        Transaction t = null;
        sgbd.initPS(SQL_SELECT_ONE);
        try {
            sgbd.getPstm().setInt(1, id);
            ResultSet rs = sgbd.executeSelect();
            if(rs.first()){
                t=new Transaction();
                  t.setId(rs.getInt("id"));
                  t.setType(rs.getString("type"));
                  t.setMontant(rs.getDouble("montant"));
                  t.setDate(rs.getDate("date").toLocalDate());
                  int id_Compte_Source=rs.getInt("compte_source");
                  Compte source=daoCompte_Source.findById(id_Compte_Source);
                  t.setCompte_source(source);
                  int id_Compte_Cible=rs.getInt("compte_cible");
                  Compte cible=daoCompte_Cible.findById(id_Compte_Cible);
                  t.setCompte_cible(cible);
                  int id_Guichet=rs.getInt("guichet_id");
                  Guichet guichet=daoGuichet.findById(id_Guichet);
                  t.setGuichet(guichet);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return t;
    }
    
}
