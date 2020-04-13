/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaApresentacao;

import dssconfigurator.camadaLogica.Administrador;
import dssconfigurator.camadaLogica.FuncionarioStand;
import Exceptions.PasswordInvalidaException;
import dssconfigurator.camadaLogica.Utilizador;
import Exceptions.UtilizadorInexistenteException;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


/**
 * FXML Controller class
 *
 * @author joanacruz
 */
public class MainviewController extends Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField username, password;
    
    
    
    public void validateLogin() throws IOException, Exception{
        String username = this.username.getText();
        String password = this.password.getText();        
        
        try{
            Utilizador u = super.getFacade().login(username, password);
            if(u instanceof Administrador){
                startController("Views/AdminView.fxml");
            }
            else if(u instanceof FuncionarioStand){
                startController("Views/ModelCar.fxml");
                super.getFacade().setIdFuncionarioConfiguracao(username);              
            }
            else {
                startController("Views/Fabrica.fxml");
            }
        }
        catch(UtilizadorInexistenteException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Username inválido!");
            alert.setContentText("Username não existente!");
            alert.show();
        }
        catch(PasswordInvalidaException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Password inválida!");
            alert.setContentText("Password Incorreta!");
            alert.show();
        }
    }
    
    public void cleanTextFields(){
        this.username.clear();
        this.password.clear();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
