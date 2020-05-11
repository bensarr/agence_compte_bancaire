/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.administrateur;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Agence;
import models.Utilisateur;
import services.SecuriteService;

/**
 * FXML Controller class
 *
 * @author bensa
 */
public class CAgence implements Initializable {

    @FXML
    private JFXTextField txtLibelle;
    @FXML
    private JFXTextField txtAdresse;
    @FXML
    private JFXTextField txtTelephone;
    @FXML
    private JFXComboBox<Utilisateur> cbxResponsable;
    @FXML
    private TableView<Agence> tbvAgence;
    @FXML
    private TableColumn<Agence,String> tbcId;
    @FXML
    private TableColumn<Agence,String> tbcLibelle;
    @FXML
    private TableColumn<Agence,String> tbcAdresse;
    @FXML
    private TableColumn<Agence,String> tbcTelephone;
    @FXML
    private TableColumn<Agence,String> tbcDatedeCreation;
    @FXML
    private TableColumn<Agence,String> tbcResponsable;
    @FXML
    private JFXComboBox<Agence> cbxAgence;
    
    ObservableList<Agence> obAgences;
    ObservableList<Utilisateur> obResponsables;
    
    SecuriteService ss;
    @FXML
    private JFXComboBox<Utilisateur> cbxResponsableAgence;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ss=new SecuriteService();
        tbcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcLibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        tbcAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        tbcTelephone.setCellValueFactory(new PropertyValueFactory<>("tel"));
        tbcDatedeCreation.setCellValueFactory(new PropertyValueFactory<>("datecreation"));
        tbcResponsable.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        
        initData();
                
    }  
    
    private void initData()
    {
        initLabel();
        obAgences=FXCollections.observableArrayList(ss.getAllAgences());
        obResponsables=FXCollections.observableArrayList(ss.getAllUtilisateurByProfil("Responsable"));
        
        cbxAgence.getItems().addAll(obAgences);
        cbxResponsable.getItems().addAll(obResponsables);
        cbxResponsableAgence.getItems().addAll(obResponsables);
        
        tbvAgence.setItems(obAgences);
        
        
        cbxResponsableAgence.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if(newValue.getAvoiragence().compareTo("Oui")==0)
            {
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Ce Responsable est déjà affecté à une Agence.\n");
                alert.showAndWait();
            }
        });
        cbxResponsable.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if(!cbxResponsable.getSelectionModel().isEmpty())
                if(newValue.getAvoiragence().compareTo("Oui")==0)
                {
                    Alert alert=new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Ce Responsable est déjà affecté à une Agence.\n");
                    alert.showAndWait();
                }
        });
        cbxAgence.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if(!cbxAgence.getSelectionModel().isEmpty())
                if(newValue.getResponsable()!=null)
                {
                    Alert alert=new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Cet Agence posséde déjà un Responsable.\n");
                    alert.showAndWait();
                }
        });
    }
    private void initLabel()
    {
        txtLibelle.setText("");
        txtAdresse.setText("");
        txtTelephone.setText("");
        cbxResponsableAgence.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleAnnuler(ActionEvent event) {
        initLabel();
    }

    @FXML
    private void handleAjouter(ActionEvent event) {
        Utilisateur responsable=null;
        if(cbxResponsableAgence.getValue()!=null)
        {
            responsable=cbxResponsableAgence.getSelectionModel().getSelectedItem();
            responsable.setAvoiragence("Oui");
            ss.updateUtilisateur(responsable);
        }
        Agence a=new Agence(txtLibelle.getText(), txtAdresse.getText(), txtTelephone.getText(), responsable);
        int id=ss.addAgence(a);
        if(id!=0){
           Alert alert=new Alert(Alert.AlertType.INFORMATION);
           alert.setContentText("Agence Enregisté avec succees");
           alert.showAndWait();
           a.setId(id);
           obAgences.add(a);
           initLabel();
        }
    }

    @FXML
    private void handleAffecter(ActionEvent event) {
        Utilisateur responsable=cbxResponsable.getSelectionModel().getSelectedItem();
        responsable.setAvoiragence("Oui");
        ss.updateUtilisateur(responsable);
        Agence a=cbxAgence.getSelectionModel().getSelectedItem();
        if(a.getResponsable()!=null)
        {
            a.getResponsable().setAvoiragence("Non");
            ss.updateUtilisateur(a.getResponsable());
        }
        a.setResponsable(responsable);
        int id=ss.updateAgence(a);
        
        if(id!=0){
           Alert alert=new Alert(Alert.AlertType.INFORMATION);
           alert.setContentText("Responsable "+responsable.toString()+" Affecté à l'Agence "+a.toString());
           alert.showAndWait();
           a.setId(id);
           cbxAgence.getSelectionModel().clearSelection();
           cbxResponsable.getSelectionModel().clearSelection();
        }
    }
    
}
