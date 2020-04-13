/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaApresentacao;

import dssconfigurator.camadaLogica.ConfiguraFacil;
import dssconfigurator.camadaLogica.Funcionario;
import dssconfigurator.camadaLogica.FuncionarioFabrica;
import dssconfigurator.camadaLogica.FuncionarioStand;
import Exceptions.UtilizadorExistenteException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joanacruz
 */
public class NewFuncionarioController extends Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField Username, Name, Adress, Contact;
    
    @FXML
    private ComboBox Department;
            
    @FXML
    private boolean validData() {
        boolean vazio = this.Username.getText().equals("") || this.Name.getText().equals("") ||
                (this.Department.getSelectionModel().getSelectedIndex() == -1) || this.Adress.getText().equals("") ||
                this.Contact.getText().equals("");
        
        if (vazio){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Dados Incompletos!");
            alert.setContentText("É necessário inserir todos os dados");
            alert.show();
        }
        
        return !vazio;
    }
    
    @FXML
    public void insertFuncionario() throws IOException {

        if (this.validData()) {

            String username = Username.getText();
            String name = Name.getText();
            String department = Department.getValue().toString();
            String adress = Adress.getText();
            String contact = Contact.getText();
            clean();
                        
            try{
                Funcionario f = null;
                if(department.equals("Fabrica")){
                    f = new FuncionarioFabrica(username, "12", name, adress, contact);
                }
                else if(department.equals("Stand")){
                    f = new FuncionarioStand(username, "12", name, adress, contact);
                }
                super.getFacade().registaFuncionario(f);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Funcionário inserido com sucesso!");
                alert.setContentText("O funcionário foi inserido");
                alert.show();
                backController();
            
            }
            catch(UtilizadorExistenteException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Funcionário já existente!");
                alert.setContentText("É necessário inserir outro username!!");
                alert.show();
            }
        }
    }
    
    public void clean(){
        Username.clear();
        Name.clear();
        Adress.clear();
        Contact.clear();
    }
    
    public void backController() throws IOException{
        startController("Views/AdminView.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.Department.getItems().addAll("Fabrica", "Stand");  
    }    
    
}
