/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import controllers.CLogin;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
public class CHistorique implements Initializable {

    @FXML
    private JFXComboBox<Compte> cbxCompte;
    @FXML
    private DatePicker dateRecherche;
    @FXML
    private JFXComboBox<Agence> cbxAgence;
    @FXML
    private TableView<Transaction> tbvTransaction;
    @FXML
    private TableColumn<Transaction, String> tbcTypeCompte;
    @FXML
    private TableColumn<Transaction, String> tbcCSource;
    @FXML
    private TableColumn<Transaction, String> tbcCCible;
    @FXML
    private TableColumn<Transaction, String> tbcMontant;
    @FXML
    private TableColumn<Transaction, String> tbcDate;
    @FXML
    private TableColumn<Transaction, String> tbcAgence;
    @FXML
    private TableColumn<Transaction, String> tbcGuichet;
    @FXML
    private JFXComboBox<Guichet> cbxGuichet;
    @FXML
    private JFXComboBox<Utilisateur> cbxCaissier;
    @FXML
    private JFXComboBox<Client> cbxClient;
    SecuriteService ss;
    SystemeService s;
    ObservableList<Agence> obAgences;
    ObservableList<Utilisateur> obCaissiers;
    ObservableList<Guichet> obGuichets;
    
    ObservableList<Client> obClients;
    ObservableList<Compte> obComptes;
    
    ObservableList<Transaction> obTransactions;
    int ok=0;
    int ok1=0;
    @FXML
    private JFXButton btnEtat;
    @FXML
    private JFXComboBox<Client> cbxClientBlocage;
    @FXML
    private Label lblBlocage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initData();
        tbcTypeCompte.setCellValueFactory(new PropertyValueFactory<>("type"));
        tbcMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        tbcGuichet.setCellValueFactory(new PropertyValueFactory<>("guichet"));
        tbcAgence.setCellValueFactory(new PropertyValueFactory<>("agence"));
        tbcCSource.setCellValueFactory(new PropertyValueFactory<>("compte_source"));
        tbcCCible.setCellValueFactory(new PropertyValueFactory<>("compte_cible"));
        tbcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }    
    
    private void initData(){
    
        ss=new SecuriteService();
        s=new SystemeService();
        obAgences=FXCollections.observableArrayList(ss.getAllAgences());
//        cbxAgence.getItems().addAll(obAgences);
        
        if(CLogin.getInstance().getUser().getProfil().getLibelle().compareToIgnoreCase("Client")==0)
        {
            lblBlocage.setVisible(false);
            cbxClientBlocage.setVisible(false);
            btnEtat.setVisible(false);
            
            cbxAgence.setDisable(true);
            cbxGuichet.setDisable(true);
            cbxCaissier.setDisable(true);
            
            Client cl=ss.getClientById(CLogin.getInstance().getUser().getId());
            cbxClient.getItems().add(cl);
            cbxClient.getSelectionModel().selectFirst();
            cbxClient.setDisable(true);
            
            List<Transaction>l=new ArrayList<>();
            l.addAll(s.getAllTransactions());
            
            obTransactions=FXCollections.observableArrayList(s.getAllTransactionByClient(l,cl));
            tbvTransaction.setItems(obTransactions);
            
            cbxCompte.getItems().addAll(s.getCompteByClient(cl));
            cbxCompte.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> 
                {
                    List<Transaction>lac=new ArrayList<>();
                    lac.addAll(obTransactions);
                    obTransactions.clear();
                    obTransactions=FXCollections.observableArrayList(s.getAllTransactionByCompte(lac,newValue));
                    tbvTransaction.setItems(obTransactions);                    
                }
            );
            
        
        }
        else if(CLogin.getInstance().getUser().getProfil().getLibelle().compareToIgnoreCase("Responsable")==0)
        {
            Agence a=ss.getAgenceByResponsable(CLogin.getInstance().getUser());
            cbxAgence.getItems().add(a);
            cbxAgence.getSelectionModel().selectFirst();
            
            obTransactions=FXCollections.observableArrayList(s.getAllTransactionByAgence(a));
            tbvTransaction.setItems(obTransactions);
                    
            cbxGuichet.getItems().
                addAll(ss.getAllGuichetsByAgence(a));
            cbxCaissier.getItems().
                addAll(ss.getCaissierByAgence(a));
            cbxClient.getItems().
                    addAll(ss.getAllClients());
            cbxClient.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> 
                {
                    if(ok>0)
                    {
                        cbxCompte.getItems().clear();
                    }
                    cbxCompte.getItems().
                        addAll(s.getCompteByClient(newValue));
                    ok++;
                    
                    List<Transaction>lac=new ArrayList<>();
                    lac.addAll(obTransactions);
                    obTransactions.clear();
                    obTransactions=FXCollections.observableArrayList(s.getAllTransactionByClient(lac,newValue));
                    tbvTransaction.setItems(obTransactions);   
                }
            );
            
            
            cbxAgence.setDisable(true);
            
            cbxCompte.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> 
                {
                    List<Transaction>lac=new ArrayList<>();
                    lac.addAll(obTransactions);
                    obTransactions.clear();
                    obTransactions=FXCollections.observableArrayList(s.getAllTransactionByCompte(lac,newValue));
                    tbvTransaction.setItems(obTransactions);                    
                }
            );
            
        }
        else if(CLogin.getInstance().getUser().getProfil().getLibelle().compareToIgnoreCase("Administrateur")==0)
        {
            obTransactions=FXCollections.observableArrayList(s.getAllTransactions());
            tbvTransaction.setItems(obTransactions);
            cbxAgence.getItems().addAll(ss.getAllAgences());
            cbxAgence.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> 
                {
                    if(ok1>0)
                    {
                        cbxGuichet.getItems().clear();
                        cbxCaissier.getItems().clear();
                    }
                    cbxGuichet.getItems().
                        addAll(ss.getAllGuichetsByAgence(newValue));
                    cbxCaissier.getItems().
                        addAll(ss.getCaissierByAgence(newValue));
                    ok1++;
                    
                    List<Transaction>l=new ArrayList<>();
                    l.addAll(obTransactions);
                    obTransactions.clear();
                    obTransactions=FXCollections.observableArrayList(s.getAllTransactionByAgence(newValue));
                    tbvTransaction.setItems(obTransactions);
                    
                }
            );
            
            cbxClient.getItems().
                    addAll(ss.getAllClients());
            cbxClient.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> 
                {
                    if(ok>0)
                    {
                        cbxCompte.getItems().clear();
                    }
                    cbxCompte.getItems().
                        addAll(s.getCompteByClient(newValue));
                    ok++;
                    
                    List<Transaction>lac=new ArrayList<>();
                    lac.addAll(obTransactions);
                    obTransactions.clear();
                    obTransactions=FXCollections.observableArrayList(s.getAllTransactionByClient(lac,newValue));
                    tbvTransaction.setItems(obTransactions);
                    
                }
            );
            
            cbxCompte.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> 
                {
                    List<Transaction>lac=new ArrayList<>();
                    lac.addAll(obTransactions);
                    obTransactions.clear();
                    obTransactions=FXCollections.observableArrayList(s.getAllTransactionByCompte(lac,newValue));
                    tbvTransaction.setItems(obTransactions);                    
                }
            );
        }
        
        btnEtat.setDisable(true);
        cbxClientBlocage.getItems().addAll(ss.getAllClients());
        cbxClientBlocage.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> 
                {        
                    if(!cbxClientBlocage.getSelectionModel().isEmpty())
                    {
                        btnEtat.setDisable(false);
                        if(newValue.getEtat().compareToIgnoreCase("Actif")==0)
                        btnEtat.setText("Désactiver");
                        else
                            if(newValue.getEtat().compareToIgnoreCase("Inactif")==0)
                                btnEtat.setText("Activer");
                    }   
                    
                    
                }
            );
    }
    private void initTraitementBlocage()
    {
        cbxClientBlocage.getSelectionModel().clearSelection();
        btnEtat.setDisable(true);
    }
    @FXML
    private void handleRechercher(ActionEvent event) {
    }

    @FXML
    private void handleBlocage(ActionEvent event) {
        if(!cbxClientBlocage.getSelectionModel().isEmpty())
        {
            Client c=cbxClientBlocage.getSelectionModel().getSelectedItem();
            if(c.getEtat().compareToIgnoreCase("Actif")==0)
            {
                c.setEtat("Inactif");
            }
            else
            if(c.getEtat().compareToIgnoreCase("Inactif")==0)
            {
                c.setEtat("Actif");
            }

            int id=ss.updateUtilisateur(c);

            if(id!=0)
            {
                String message=" -L'accés à cette plateform est '"+btnEtat.getText()+"' pour ce 'Client'";

                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(message);
                alert.showAndWait();
                initTraitementBlocage();
            }
        }
    }
    
}
