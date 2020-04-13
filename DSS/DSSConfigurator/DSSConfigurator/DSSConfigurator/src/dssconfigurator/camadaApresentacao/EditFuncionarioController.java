/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaApresentacao;

import dssconfigurator.camadaLogica.Funcionario;
import Exceptions.UtilizadorInexistenteException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Joana Marta Cruz
 */
public class EditFuncionarioController extends Controller implements Initializable {
    
    @FXML
    private TextField Adress, Contact;
    @FXML
    private Label username, name;
    
    private String idFuncionario;
   
    
    @Override
    public void launchController(){
        super.launchController();
        this.idFuncionario = super.getFuncionario();
        Funcionario f;
        try {
            f = super.getFacade().getFuncionario(idFuncionario);
            username.setText(f.getUsername());
            name.setText(f.getNome());
            Adress.setText(f.getMorada());
            Contact.setText(f.getContacto());
        } catch (UtilizadorInexistenteException ex) {
        }
    }
    
    public void backController() throws IOException{
        startController("Views/AdminView.fxml");  
    }
    
    public void updateFuncionario() throws IOException{
        String adress = Adress.getText();
        String contact = Contact.getText();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tem a certeza que pretende alterar as informações do funcionário "
                + "com o seguinte username " + username.getText() + "?" ,ButtonType.OK, ButtonType.CANCEL);
        alert.setHeaderText("Confirmação de remoção");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            super.getFacade().atualizaFuncionario(this.idFuncionario, adress, contact);
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText("Sucesso!");
            alert1.setContentText("Informações do funcionário atualizadas com sucesso!");
            alert1.show();
            backController();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
