/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.Utilisateur;
import services.SecuriteService;
import utilitaire.UtilitaireFX;

/**
 * FXML Controller class
 *
 * @author bensa
 */
public class CLogin implements Initializable {

    @FXML
    private Label lblError;
    @FXML
    private JFXTextField txtLogin;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnAnnuler;
    SecuriteService securite;
    private static CLogin instance;
    private Utilisateur u;
    private static UtilitaireFX utils = new UtilitaireFX();

    public static UtilitaireFX getUtils() {
        return utils;
    }
    
    public static CLogin getInstance() {
        return instance;
    }
    public Utilisateur getUser() {
        return u;
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         securite=new SecuriteService();
            instance=this;
    }    

    @FXML
    private void handleConnexion(ActionEvent event) throws IOException {
        if(txtLogin.getText().isEmpty() || txtPassword.getText().isEmpty()){
            lblError.setText("Login et Mot de passe Obligatoire");
            lblError.setVisible(true);
        }else{
             u=securite.seConnecter(new Utilisateur(txtLogin.getText(), txtPassword.getText()));
         
             if(u==null){
                 lblError.setText("Login ou Mot de passe Incorrect");
                  lblError.setVisible(true);
             }else{
                  if(u.getEtat().compareToIgnoreCase("Actif")==0){
                         CLogin.getUtils().changeView(lblError, "VDashboard");
                }else{
                       lblError.setText("Utilisateur bloqué");
                     lblError.setVisible(true);
                }
             }
        }
    }

    @FXML
    private void handleAnnuler(ActionEvent event) {
        utils.closeButton(btnAnnuler);
    }
    
}
