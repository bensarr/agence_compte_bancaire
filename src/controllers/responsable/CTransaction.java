/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.responsable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controllers.CDashboard;
import controllers.CLogin;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import models.Compte;
import models.Guichet;
import models.Transaction;
import models.Utilisateur;
import services.SystemeService;

/**
 * FXML Controller class
 *
 * @author bensa
 */
public class CTransaction implements Initializable {
    private static Utilisateur userConnect;
    @FXML
    private Label lblTitle;
    @FXML
    private JFXTextField txtNCompte;
    @FXML
    private JFXTextField txtMontant;
    @FXML
    private JFXTextField txtNCompteBenef;
    @FXML
    private Label lblClient;
    @FXML
    private Label lblBenef;
    @FXML
    private Label lblTransaction;
    SystemeService s;
    String title;
    int a,b,c;
    @FXML
    private JFXButton btnCl;
    @FXML
    private JFXButton btnDest;
    @FXML
    private Separator vM2;
    @FXML
    private JFXButton btnAff;
    Compte cCl,cBnf;
    double val;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        userConnect = CLogin.getInstance().getUser();
        
        s=new SystemeService();
        String tmp=lblTitle.getText();
        title=CDashboard.getInstance().getViewName();
        lblTitle.setText(tmp+" "+title);
        a=0;b=0;c=0;
        initData();
    }    
    private void initData()
    {
        if(title.compareToIgnoreCase("Virement")==0)
        {
            
        }
        else
        {
            btnDest.setVisible(false);
            txtNCompteBenef.setVisible(false);
            lblBenef.setVisible(false);
            vM2.setVisible(false);
        }
    }
    private void initForm()
    {
        if(title.compareToIgnoreCase("Virement")==0)
        {
            txtNCompteBenef.setText("");
            lblBenef.setText("");
            b=0;
        }
        txtNCompte.setText("");
        lblClient.setText("");
        
        txtMontant.setText("");
        lblTransaction.setText("");
        a=0;
        c=0;
        
    }
    public void activation(boolean bl)
    {
        if(title.compareToIgnoreCase("Virement")==0)
        {
            txtNCompteBenef.setDisable(bl);
            btnDest.setDisable(bl);
            lblBenef.setDisable(bl);
        }
        txtNCompte.setDisable(bl);
        btnCl.setDisable(bl);
        lblClient.setDisable(bl);
        
        txtMontant.setDisable(bl);
        btnAff.setDisable(bl);
    }
    public String chargerInfo(Compte c)
    {
        return "\t"+c.getNumero().toUpperCase()+"\n"
                + "\t"+c.getNom().toUpperCase()+"\n"
                + "\t"+c.getPrenom().toUpperCase()+"\n"
                + "\t"+c.getTypeCompte().toUpperCase()+"\n";
    }
    private int showInfo(Compte c,Label l,int test)
    {
        if(c!=null)
        {
            l.setText(chargerInfo(c));
            test=1;
        }
        else
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ce N°compte n'existe pas.");
            alert.showAndWait();
            test=0;
        }
        return test;
    }

    @FXML
    private void handleCharger(ActionEvent event) {
        a=showInfo(s.getCompteByNumero(txtNCompte.getText()),lblClient,a);
        txtNCompte.setDisable(true);
    }

    @FXML
    private void handleChargerDest(ActionEvent event) {
        b=showInfo(s.getCompteByNumero(txtNCompteBenef.getText()),lblBenef,b);
        txtNCompteBenef.setDisable(true);
    }

    @FXML
    private void handleAfficherTransaction(ActionEvent event) {
        if(title.compareToIgnoreCase("Virement")==0)
        {
            if(a==1 && b==1)
            {
                if(txtMontant.getText().isEmpty())
                {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Vueiller Saisir le Montant de ce virement SVP!!!");
                    alert.showAndWait();
                }else
                {
                    cBnf=s.getCompteByNumero(txtNCompteBenef.getText());
                    cCl=s.getCompteByNumero(txtNCompte.getText());
                    val=Double.parseDouble(txtMontant.getText());
                    if(cCl.getId()!=cBnf.getId())
                    {
                    lblTransaction.setText("***********************************************\n"+
                    title+":\nDe:"+lblClient.getText()+
                    "***********************************************\n"
                    +title+":\nVers:"+cBnf.getNumero()+
                    "\n***********************************************\n"
                            +"Montant"+":\t"+val+" FCFA\n"
                                    + "***********************************************");                
                    c=1;
                    activation(true);
                    }
                    else
                    {
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Virement ne peut être effectué sur le même Compte!");
                        alert.showAndWait();
                    }
                }
            }
            else
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Vueiller Charger tous les numéros de comptes concernés par ce virement!");
                alert.showAndWait();
            }
        }
        else
        {
            if(a==1)
            {
                if(txtMontant.getText().isEmpty())
                {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Vueiller Saisir le Montant de ce "+title+" SVP!!!");
                    alert.showAndWait();
                }else
                {
                    cCl=s.getCompteByNumero(txtNCompte.getText());
                    val=Double.parseDouble(txtMontant.getText());
                    lblTransaction.setText("***********************************************\n"+
                    title+":\n"+lblClient.getText()+
                    "***********************************************\n"
                            +"Montant"+":\t"+val+" FCFA\n"
                                    + "***********************************************");
                    c=1;
                    activation(true);
                }
            }
            else
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Vueiller Charger le numéro de compte client concernés par ce "+title+"!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleEnregistrerTransaction(ActionEvent event) {
        if(c==1)
        {
            String message="Opération Impossible !!!\n";
            switch(title)
            {
                case "Virement":
                    faireTransactionVirement(cCl,cBnf,message);
                    break;
                default:
                    if(title.compareToIgnoreCase("Retrait")==0)
                    {
                        faireTransactionRetrait(cCl,message);
                    }
                    else
                    {
                        faireTransactionDepot(cCl,message);
                    }
                    break;
            }
            initForm();
            activation(false);
        }
        else  
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Vueiller 'Afficher' les informations avant d'Enregistrer'");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleAnnulerTransaction(ActionEvent event) {
        initForm();
        activation(false);
    }
    public void faireTransactionVirement(Compte cCl,Compte cBnf,String message)
    {
        if(cCl!=null && cCl.getTypeCompte().compareToIgnoreCase("Courant")==0)
        {
            double x=cCl.getSolde()-val;
            if(x>=0)
            {
                cCl.setSolde(x);
                s.updateCompte(cCl);
                if(cBnf!=null)
                {
                    cBnf.setSolde(cBnf.getSolde()+val);
                    s.updateCompte(cBnf);
                }
                Utilisateur u=userConnect;
                Guichet g=null;
                if(u.getProfil().getLibelle().compareToIgnoreCase("Caissier")==0)
                    g=s.getGuichetByCaissierByOneDate(u,LocalDate.now());                               

                Transaction trans=new Transaction(title, val, cCl, cBnf, g);
                s.addTransaction(trans);
                message="- '"+title+"' Effectué avec Success";
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(message);
                alert.showAndWait();
            }
            else
            {
                
            }
        }
        else
        {
            message+=" -Impossible de Débiter le compte est un Compte Epargne !!!";
            
        }        
        
        
    }
    public void faireTransactionRetrait(Compte cCl,String message)
    {
        if(cCl!=null && cCl.getTypeCompte().compareToIgnoreCase("Courant")==0)
        {
            double x=cCl.getSolde()-val;
            if(x>=0)
            {
                cCl.setSolde(x);
                s.updateCompte(cCl);
                
                Utilisateur u=userConnect;
                Guichet g=null;
                if(u.getProfil().getLibelle().compareToIgnoreCase("Caissier")==0)
                    g=s.getGuichetByCaissierByOneDate(u,LocalDate.now());                               

                Transaction trans=new Transaction(title, val, cCl, null, g);
                s.addTransaction(trans);
                message="- '"+title+"' Effectué avec Success";
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(message);
                alert.showAndWait();
            }
            else
            {
                message+=" -Solde Demandeur Insuffisant !!!";
            }
        }
        else
        {
            message+=" -Impossible de Débiter le compte est un Compte Epargne !!!";
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText(message);
            alert.showAndWait();
        }        
        
        
    }
    public void faireTransactionDepot(Compte cCl,String message)
    {
        if(cCl!=null && cCl.getTypeCompte().compareToIgnoreCase("Courant")==0)
        {
            double x=cCl.getSolde()+val;
            
            cCl.setSolde(x);
            s.updateCompte(cCl);

            Utilisateur u=userConnect;
            Guichet g=null;
            if(u.getProfil().getLibelle().compareToIgnoreCase("Caissier")==0)
                g=s.getGuichetByCaissierByOneDate(u,LocalDate.now());                               

            Transaction trans=new Transaction(title, val, cCl, null, g);
            s.addTransaction(trans);
            message="- '"+title+"' Effectué avec Success";
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(message);
            alert.showAndWait();
            
        }
        else
        {
            message+=" -Impossible de Débiter le compte est un Compte Epargne !!!";
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText(message);
            alert.showAndWait();
        }        
        
        
    }
    
}
