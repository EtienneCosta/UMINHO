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
import Exceptions.UtilizadorInexistenteException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

/**
 *
 * @author joanacruz
 */
public class FuncionarioController extends Controller implements Initializable {
    
    @FXML
    private TableView<Funcionario> tbDataFuncionarios;
    
    @FXML
    private TableColumn<Funcionario, String> nameFuncionario, usernameFuncionario, departmentFuncionario, moradaFuncionario, contactoFuncionario;
    
    @FXML
    private TextField usernameFunc;
    
    private List<Funcionario> funcionarios;
   
    
    @FXML
    @Override
    public void launchController(){
        super.launchController();
        this.funcionarios = super.getFacade().getFuncionarios();
        ObservableList<Funcionario> funcs = FXCollections.observableArrayList(funcionarios);
        tbDataFuncionarios.setItems(funcs); 
    }
     
    @FXML
    private void emptyFields(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Dados Incompletos!");
        alert.setContentText("É necessário inserir um username!");
        alert.show(); 
    } 
    
    @FXML
    public void addFuncionario() throws IOException{
        startController("Views/NewFuncionario.fxml");
    }
   
    @FXML
    public String searchFuncionario(){
        String username = usernameFunc.getText();
        if(username.equals("")){
            emptyFields();
        }
        else{
            try{
                Funcionario f = super.getFacade().getFuncionario(username);
                tbDataFuncionarios.getItems().stream().filter(item -> item.getUsername().equals(username)).findAny().ifPresent(item -> {
                tbDataFuncionarios.getSelectionModel().select(item);
                tbDataFuncionarios.scrollTo(item);
                });
                return username;
            }catch(UtilizadorInexistenteException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Dados inválidos");
                alert.setContentText("O funcionário com o username " + username + " não existe!");
                alert.show();
            }
        }
     
        usernameFunc.clear();
        return null;
    }
    
    @FXML
    public void updateInfo() throws IOException{
        String username = searchFuncionario();
        
        if(!username.equals("")){
            super.setFuncionario(username);

            super.startController("Views/EditFuncionario.fxml");
        }
    }
    
    
    @FXML
    public void removeFuncionario(){
        String username = searchFuncionario();
        if(!username.equals("")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tem a certeza que pretende remover o funcionário "
                    + "com o seguinte username " + username + "?" ,ButtonType.OK, ButtonType.CANCEL);
            alert.setHeaderText("Confirmação de remoção");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                tbDataFuncionarios.getItems().remove(
                tbDataFuncionarios.getSelectionModel().getSelectedItem());
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setHeaderText("Funcionário removido com sucesso!");
                alert1.setContentText("O funcionário foi removido");
                alert1.show();
                super.getFacade().removeFuncionario(username);
            }
        }
    }
    
    @FXML
    public void backController() throws IOException{
        startController("Views/Mainview.fxml");
    }
        
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usernameFuncionario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        nameFuncionario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        departmentFuncionario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartamento()));
        contactoFuncionario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContacto()));
        moradaFuncionario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMorada()));
        
    }
    
}
