    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import java.awt.Event;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Window;
import models.Utilisateur;
import services.SecuriteService;

/**
 *
 * @author bensa
 */
public class CDashboard implements Initializable {
    
    private static Utilisateur userConnect;
    @FXML
    private AnchorPane anchorContent;
    @FXML
    private Label lblProfil;
    @FXML
    private Label lblAgence;
    @FXML
    private Label lblNom;
    @FXML
    private Label lblPrenom;
    @FXML
    private Pane panelClient;
    @FXML
    private Pane panelAdmin;
    @FXML
    private Pane panelCaissier;
    @FXML
    private Pane panelResponsable;
    @FXML
    private JFXButton btnDeconnexion;
    
    private static CDashboard instance;
    private String viewName;
    
    SecuriteService securite;
    
    private final Map<String,Pane> mapPanel=new HashMap<>();
    @FXML
    private Label lblWelcome;
    

    public CDashboard() {
        this.securite = new SecuriteService();
    }

    public static CDashboard getInstance() {
        return instance;
    }

    public String getViewName() {
        return viewName;
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userConnect = CLogin.getInstance().getUser();
        initData();
        verfProfil(userConnect.getProfil().getLibelle());
        instance=this;
    }  
    
    private void initData()
    {
        addMapPanels();
        if(userConnect.getProfil().getLibelle().compareTo("Administrateur")==0||userConnect.getProfil().getLibelle().compareTo("Client")==0)
        {
            lblAgence.setText("");
        }else{
            if(userConnect.getProfil().getLibelle().compareTo("Responsable")==0)
                lblAgence.setText(securite.getAgenceByResponsable(userConnect).getLibelle());
            else
                lblAgence.setText(securite.getAgenceByCaissier(userConnect).getLibelle());
        }
        lblProfil.setText(userConnect.getProfil().toString());
        lblNom.setText(userConnect.getNom());
        lblPrenom.setText(userConnect.getPrenom());
    }
    private void addMapPanels(){
        mapPanel.put("Client",panelClient);
        mapPanel.put("Caissier",panelCaissier);
        mapPanel.put("Responsable",panelResponsable);
        mapPanel.put("Administrateur",panelAdmin);
    }
    
    private void verfProfil(String profil){
        boolean bol = userConnect.getProfil().getLibelle().compareToIgnoreCase(profil)==0;
        panelClient.setDisable(true);
        panelCaissier.setDisable(true);
        panelResponsable.setDisable(true);
        panelAdmin.setDisable(true);
        if(bol){
            mapPanel.get(profil).setDisable(false);
            lblWelcome.setText("Bienvenu Dans Votre Agence Bancaire !!!");
            if(profil.compareToIgnoreCase("Caissier")==0 && securite.ifCaissierWorks(userConnect)==false)
            {
                mapPanel.get(profil).setDisable(true);
                lblWelcome.setText("Vous n'avez pas été planifié pour Aujourd'hui !!!");                
            }
        }else {
        panelClient.setDisable(false);
        panelCaissier.setDisable(false);
        panelResponsable.setDisable(false);
        panelAdmin.setDisable(false);
        }
    }

    @FXML
    private void handleDeconnexion(MouseEvent event) throws IOException {
        CLogin.getUtils().changeView(lblNom, "VLogin");
    }

    @FXML
    private void handleDepot(MouseEvent event) throws IOException {
        viewName="Dépot";
        CLogin.getUtils().showView(anchorContent,"responsable/vTransaction");
    }

    @FXML
    private void handleRetrait(MouseEvent event) throws IOException {
        viewName="Retrait";
        CLogin.getUtils().showView(anchorContent,"responsable/vTransaction");
    }

    @FXML
    private void handleVirement(MouseEvent event) throws IOException {
        viewName="Virement";
        CLogin.getUtils().showView(anchorContent,"responsable/vTransaction");
    }

    @FXML
    private void handleCompteBanque(MouseEvent event) throws IOException {
        CLogin.getUtils().showView(anchorContent,"responsable/vCompte");
    }


    @FXML
    private void handleAgenceSecurite(MouseEvent event) throws IOException {
        CLogin.getUtils().showView(anchorContent,"administrateur/vAgence");
    }

    @FXML
    private void handleUtilisateurSecurite(MouseEvent event) throws IOException {
        CLogin.getUtils().showView(anchorContent,"administrateur/vUtilisateurs");
    }

    @FXML
    private void handleGuichetSecurite(MouseEvent event) throws IOException {
        CLogin.getUtils().showView(anchorContent,"administrateur/vGuichet");
    }

    @FXML
    private void handleProfilSecurite(MouseEvent event) throws IOException {
        CLogin.getUtils().showView(anchorContent,"administrateur/vProfil");
    }

    @FXML
    private void handleHistorique(MouseEvent event) throws IOException {
        CLogin.getUtils().showView(anchorContent,"client/vHistorique");
    }

    
}
