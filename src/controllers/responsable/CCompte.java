/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.responsable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controllers.CLogin;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Agence;
import models.Client;
import models.Compte;
import models.Courant;
import models.Epargne;
import models.Guichet;
import models.Transaction;
import models.Utilisateur;
import services.SecuriteService;
import services.SystemeService;

/**
 * FXML Controller class
 *
 * @author bensa
 */
public class CCompte implements Initializable {

    @FXML
    private TableView<Compte> tbvCcompte;
    @FXML
    private TableColumn<Compte, String> tbcNcompte;
    @FXML
    private TableColumn<Compte, String> tbcNCI;
    @FXML
    private TableColumn<Compte, String> tbcNom;
    @FXML
    private TableColumn<Compte, String> tbcPrenom;
    @FXML
    private TableColumn<Compte, String> tbcSolde;
    @FXML
    private TableColumn<Compte, String> tbcEtat;
    @FXML
    private TableColumn<Compte, String> tbcTypeCompte;
    @FXML
    private TableColumn<Compte, String> tbcEmail;
    @FXML
    private TableColumn<Compte, String> tbcTelephone;
    @FXML
    private JFXTextField txtNom;
    @FXML
    private JFXPasswordField txtCPassword;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXTextField txtLogin;
    @FXML
    private JFXTextField txtPrenom;
    @FXML
    private JFXTextField txtNNCI;
    @FXML
    private JFXTextField txtAdresse;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtTelephone;
    @FXML
    private JFXComboBox<String> cbxTypeCompte;
    @FXML
    private JFXTextField txtSolde;
    @FXML
    private DatePicker dateFin;
    @FXML
    private JFXComboBox<Compte> cbxNCompteBlocage;
    @FXML
    private Label lblError;
    @FXML
    private TableColumn<Compte, String> tbcAgence;
    @FXML
    private TableColumn<Compte, String> tbcDateFin;
    @FXML
    private TableColumn<Compte, String> tbcDateCreat;
    
    SystemeService ss;
    SecuriteService sec;
    ObservableList<Compte> obComptes;
    @FXML
    private JFXButton btnEtat;
    String title;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        title="Dépot";
        initData();
        tbcNcompte.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tbcNCI.setCellValueFactory(new PropertyValueFactory<>("nci"));
        tbcNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tbcPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        tbcSolde.setCellValueFactory(new PropertyValueFactory<>("solde"));
        tbcEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        tbcTypeCompte.setCellValueFactory(new PropertyValueFactory<>("typeCompte"));
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbcTelephone.setCellValueFactory(new PropertyValueFactory<>("tel"));
        tbcAgence.setCellValueFactory(new PropertyValueFactory<>("agence"));
        tbcDateCreat.setCellValueFactory(new PropertyValueFactory<>("datecreation"));
        tbcDateFin.setCellValueFactory(new PropertyValueFactory<>("date_fin_blocage"));
    }    
    private void initData()
    {
        dateFin.setVisible(false);
        
        
        ss=new SystemeService();
        
        obComptes=FXCollections.observableArrayList(ss.getAllComptes());
        cbxNCompteBlocage.getItems().addAll(obComptes);
        
        tbvCcompte.setItems(obComptes);
        cbxTypeCompte.getItems().addAll("Courant","Epargne");
        
        cbxTypeCompte.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> 
                {
                    if(!cbxTypeCompte.getSelectionModel().isEmpty())
                        if(newValue.compareTo("Epargne")==0)
                        {
                            dateFin.setVisible(true);
                        }else
                            dateFin.setVisible(false);
                    
                }
            );
        btnEtat.setDisable(true);
        cbxNCompteBlocage.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> 
                {        
                    if(!cbxNCompteBlocage.getSelectionModel().isEmpty())
                    {
                        btnEtat.setDisable(false);
                        if(newValue.getEtat().compareToIgnoreCase("Ouvert")==0)
                        btnEtat.setText("Bloquer");
                        else
                            if(newValue.getEtat().compareToIgnoreCase("Bloqué")==0)
                                btnEtat.setText("Ouvrir");
                    }   
                    
                    
                }
            );

    }
    public void initTraitementAjout()
    {
        txtNNCI.setText("");
        txtNom.setText("");
        txtPrenom.setText("");
        txtLogin.setText("");
        txtPassword.setText("");
        txtCPassword.setText("");
        txtAdresse.setText("");
        txtEmail.setText("");
        txtTelephone.setText("");
        cbxTypeCompte.getSelectionModel().clearSelection();
    }
    private void labForm(boolean b)
    {
        txtNom.setDisable(b);
        txtPrenom.setDisable(b);
        txtLogin.setDisable(b);
        txtPassword.setDisable(b);
        txtCPassword.setDisable(b);
        txtAdresse.setDisable(b);
        txtEmail.setDisable(b);
        txtTelephone.setDisable(b);
    }
    private void initTraitementBlocage()
    {
        cbxNCompteBlocage.getSelectionModel().clearSelection();
        btnEtat.setDisable(true);
    }
    public String generatingRandomString() {
    int leftLimit = 48; // numeral '0'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 5;
    Random random = new Random();
 
    String generatedString = random.ints(leftLimit, rightLimit + 1)
      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
      .limit(targetStringLength)
      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
      .toString();
 
    return generatedString;
}
    @FXML
    private void handleEnregistrer(ActionEvent event) {
        String message="";
        Compte compte;
        int idC;
        if(txtPassword.getText().compareTo(txtCPassword.getText())==0)
        {
            sec=new SecuriteService();
            Client cl;
            if(!txtNom.getText().isEmpty() && !txtPrenom.getText().isEmpty() && !txtLogin.getText().isEmpty() && !txtPassword.getText().isEmpty() && !txtNNCI.getText().isEmpty() && !txtAdresse.getText().isEmpty() && !txtEmail.getText().isEmpty() && !txtTelephone.getText().isEmpty())
            {
                cl=new Client(txtNom.getText(), txtPrenom.getText(), txtLogin.getText(), txtPassword.getText(), sec.getProfilById(4), txtNNCI.getText(), txtAdresse.getText(), txtEmail.getText(), txtTelephone.getText());
                
                
                Agence agence=CLogin.getInstance().getUser().getAgence();
                
                if(!cbxTypeCompte.getSelectionModel().isEmpty())
                if(cbxTypeCompte.getSelectionModel().getSelectedItem().compareToIgnoreCase("Courant")==0)
                {
                    
                    if(sec.getClientByNci(cl.getNci())==null){
                        idC=sec.addClient(cl);
                        cl=sec.getClientByNci(cl.getNci());
                            message+=" -Client Enregisté avec succes !!!\n";
                    }else{
                        cl=sec.getClientByNci(txtNNCI.getText());
                    }
                    Courant courant=new Courant(generatingRandomString(), Double.parseDouble(txtSolde.getText()), "Bloqué", cl, agence, 1050.0);
                    int idCrt=ss.addCompteCourant(courant);
                    courant.setId(idCrt);
                    if(idCrt!=0)
                        message+=" -Compte 'Courant' créé avec succes !!!\n";
                    if(Double.parseDouble(txtSolde.getText())>0)
                    {
                        int idT=faireDepot(courant, Double.parseDouble(txtSolde.getText()));
                        if(idT!=0)
                            message+="- '"+title+"' Effectué avec Success";
                    }
                            
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(message);
                    alert.showAndWait();
                    initTraitementAjout();
                    obComptes.add(courant);
                    cbxNCompteBlocage.getItems().add(courant);
                    labForm(false);
                }
                else
                if(cbxTypeCompte.getSelectionModel().getSelectedItem().compareToIgnoreCase("Epargne")==0)
                {
                    if(dateFin.getValue()!=null)
                    {
                        if(sec.getClientByNci(cl.getNci())==null){
                            idC=sec.addClient(cl);
                            cl=sec.getClientByNci(cl.getNci());
                                message+=" -Client Enregisté avec succes !!!\n";
                        }else{
                            cl=sec.getClientByNci(txtNNCI.getText());
                        }
                        Epargne epargne=new Epargne(generatingRandomString(), Double.parseDouble(txtSolde.getText()), "Bloqué", cl, agence, 0.11, dateFin.getValue());
                        int idEp=ss.addCompteEpargne(epargne);
                        epargne.setId(idEp);
                        if(idEp!=0)
                            message+=" -Compte 'Epargne' créé avec succes !!!\n";
                        if(Double.parseDouble(txtSolde.getText())>0)
                        {
                            int idT=faireDepot(epargne, Double.parseDouble(txtSolde.getText()));
                            if(idT!=0)
                                message+="- '"+title+"' Effectué avec Success";
                        }
                        
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText(message);
                        alert.showAndWait();
                        initTraitementAjout();
                        obComptes.add(epargne);
                        cbxNCompteBlocage.getItems().add(epargne);
                        labForm(false);
                        
                    }else
                    {
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText(" -Vueiller saisir une date SVP");
                        alert.showAndWait();
                    }
                }
                else
                {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(" -Vueiller choisir un type de Compte SVP");
                    alert.showAndWait();
                }
                     
            }
            else
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText(" -Vueiller saisir tous les champs SVP");
                alert.showAndWait();
            }
            
            
            
        }
        else
        {
            lblError.setVisible(true);
            lblError.setText("Mots de Pass Non Conforme !!!");
        }
    }

    @FXML
    private void handleVerifier(ActionEvent event) {
        sec=new SecuriteService();
        if(!txtNNCI.getText().isEmpty())
        {
            Client cl=sec.getClientByNci(txtNNCI.getText());
            if(cl!=null)
            {
                txtNom.setText(cl.getNom());
                txtPrenom.setText(cl.getPrenom());
                txtLogin.setText(cl.getPrenom());
                txtPassword.setText(cl.getPassword());
                txtCPassword.setText(cl.getPassword());
                txtAdresse.setText(cl.getAdresse());
                txtEmail.setText(cl.getEmail());
                txtTelephone.setText(cl.getTel());
                labForm(true);
            }
            else
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Ce Numéro de CNI n'existe pas; vous \npouvez enregistrer un nouveau client");
                alert.showAndWait();
            }
        }
        else
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Le Numéro de CNI n'est pas saisit;\nVueiller le faire SVP!!!");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleBlocage(ActionEvent event) {
        if(!cbxNCompteBlocage.getSelectionModel().isEmpty())
        {
            Compte c=cbxNCompteBlocage.getSelectionModel().getSelectedItem();
            if(c.getEtat().compareToIgnoreCase("Ouvert")==0)
            {
                c.setEtat("Bloqué");
            }
            else
            if(c.getEtat().compareToIgnoreCase("Bloqué")==0)
            {
                c.setEtat("Ouvert");
            }

            int id=ss.updateCompte(c);

            if(id!=0)
            {
                String message=" -Traitement '"+btnEtat.getText()+" compte' Effectuée avec succes";

                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(message);
                alert.showAndWait();
                initTraitementBlocage();
            }
        }
    }
    public int faireDepot(Compte cCl,double val)
    {
        double x=cCl.getSolde()+val;
            
        cCl.setSolde(x);
        ss.updateCompte(cCl);                            

        Transaction trans=new Transaction("Dépot", val, cCl, null, null);
        int id=ss.addTransaction(trans);       
        trans.setId(id);
         return id;
        
    }
    
}
