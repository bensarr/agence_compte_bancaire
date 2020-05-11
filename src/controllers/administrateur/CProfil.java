/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.administrateur;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Profil;
import services.SecuriteService;

/**
 * FXML Controller class
 *
 * @author bensa
 */
public class CProfil implements Initializable {

    @FXML
    private JFXTextField txtLibelle;
    @FXML
    private TableView<Profil> tbvProfil;
    @FXML
    private TableColumn<Profil, String> tbcId;
    @FXML
    private TableColumn<Profil, String> tbcLibelle;
    ObservableList<Profil> obProfils;
    SecuriteService ss;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ss=new SecuriteService();
        tbcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcLibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        
        initdata();
    }    
    public void initdata()
    {
        obProfils=FXCollections.observableArrayList(ss.getAllProfilsWoClient());
        
        tbvProfil.setItems(obProfils);
    }
    @FXML
    private void handleAjouter(ActionEvent event) {
        Profil p=new Profil(txtLibelle.getText());
        int id=ss.addProfil(p);
        if(id!=0){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
          alert.setContentText("Profil "+p.toString()+" Enregisté avec succees");
           alert.showAndWait();
           p.setId(id);
           obProfils.add(p);
        }
    }
    
}
