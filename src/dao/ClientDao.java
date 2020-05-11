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
import models.Client;
import models.Profil;
import models.Utilisateur;


/**
 *
 * @author bensa
 */
public class ClientDao implements IDao<Client>{
    private final String SQL_SELECT_ALL="SELECT c.*,u.* FROM client c JOIN utilisateur u on u.id=c.id";
    //private final String SQL_SELECT_LOGIN_PASSWORD="select * from client where login=? and password=?";
    private final String SQL_SELECT_ONE="SELECT c.*,u.* FROM client c JOIN utilisateur u on u.id=c.id WHERE c.id=?";
    private final String SQL_SELECT_BY_NCI="SELECT c.*,u.* FROM client c JOIN utilisateur u on u.id=c.id WHERE c.nci=?";
    private final String SQL_INSERT="INSERT INTO `client` (`id`, `nci`, `adresse`,`email`, `tel`) VALUES (?,?,?,?,?)";
    private final String SQL_UPDATE="UPDATE `client` SET `nci` = ?, `adresse` = ?,`email` = ?, `tel` = ? WHERE `client`.`id` = ?";
    private ISGBD  sgbd;
    private UtilisateurDao  daoUtilisateur;
    private ProfilDao  daoProfil;

    public ClientDao(ISGBD sgbd) {
        this.sgbd = sgbd;
        daoUtilisateur=new UtilisateurDao(sgbd);
        daoProfil=new ProfilDao(sgbd);
    }

    @Override
    public int create(Client objet) {
        int idU =daoUtilisateur.create(objet);
        int dern_id=0;
        sgbd.initPS(SQL_INSERT);
        try {
            sgbd.getPstm().setInt(1,idU);
            sgbd.getPstm().setString(2,objet.getNci());
            sgbd.getPstm().setString(3,objet.getAdresse());
            sgbd.getPstm().setString(4,objet.getEmail());
            sgbd.getPstm().setString(5,objet.getTel());
            sgbd.executeMaj();
            ResultSet rs=sgbd.getPstm().getGeneratedKeys();
            if(rs.next()){
                dern_id = rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              sgbd.CloseConnection();
        }
      
        return dern_id;
    }

    @Override
    public int update(Client objet) {
        daoUtilisateur.update(objet);
        int ok=0;
        sgbd.initPS(SQL_UPDATE);
        try {
            sgbd.getPstm().setString(1, objet.getNci());
            sgbd.getPstm().setString(2, objet.getAdresse());
            sgbd.getPstm().setString(3, objet.getEmail());
            sgbd.getPstm().setString(4, objet.getTel());
            sgbd.getPstm().setInt(5, objet.getId());
            if(sgbd.executeMaj()==1) ok=1;
        } catch (SQLException ex) {
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return ok;
    }

    @Override
    public List<Client> findAll() {
        sgbd.initPS(SQL_SELECT_ALL);
        List<Client> resultats=null;
        try {
            ResultSet rs = sgbd.executeSelect();
            resultats=new ArrayList<>();
            while(rs.next()){
                int id = rs.getInt("id");
                String nci = rs.getString("nci");
                String email = rs.getString("email");
                String adresse = rs.getString("adresse");
                String tel = rs.getString("tel");
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
                Client client = new Client(id,u.getNom(), u.getPrenom(), u.getLogin(), u.getPassword(), u.getEtat(),
                        u.getProfil(),nci, adresse, email, tel,id);
                resultats.add(client);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return resultats;
    }

    @Override
    public Client findById(int id) {
        Client client = null;
        sgbd.initPS(SQL_SELECT_ONE);
        try {
            sgbd.getPstm().setInt(1, id);
            ResultSet rs = sgbd.executeSelect();
            if(rs.first()){
                int idU = rs.getInt("id");
                String nci = rs.getString("nci");
                String email = rs.getString("email");
                String adresse = rs.getString("adresse");
                String tel = rs.getString("tel");
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
                client = new Client(id,u.getNom(), u.getPrenom(), u.getLogin(), u.getPassword(), u.getEtat(),
                        u.getProfil(),nci, adresse, email, tel,id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return client;
    }
    
    public Client findByNCI(String NCI) {
        Client client = null;
        sgbd.initPS(SQL_SELECT_BY_NCI);
        try {
            sgbd.getPstm().setString(1, NCI);
            ResultSet rs = sgbd.executeSelect();
            if(rs.first()){
                int idU = rs.getInt("id");
                String nci = rs.getString("nci");
                String email = rs.getString("email");
                String adresse = rs.getString("adresse");
                String tel = rs.getString("tel");
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
                client = new Client(idU,u.getNom(), u.getPrenom(), u.getLogin(), u.getPassword(), u.getEtat(),
                        u.getProfil(),nci, adresse, email, tel,idU);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        sgbd.CloseConnection();
        return client;
    }
    
}
