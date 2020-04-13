/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaApresentacao;

import dssconfigurator.camadaLogica.ConfiguraFacil;
import dssconfigurator.camadaLogica.Configuracao;
import dssconfigurator.camadaLogica.Encomenda;
import Exceptions.EncomendaInexistenteException;
import dssconfigurator.camadaLogica.Funcionario;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joanacruz
 */
public class ProductionViewController extends Controller implements Initializable {

    @FXML
    private TableView<Encomenda> listEncomendas;
    
    @FXML
    private TableColumn<Encomenda, String> idEncomenda, dateEncomenda, estadoEncomenda, nomeCliente, contactoCliente, detalhesEncomenda;
    
    @FXML
    private TextField idProcura;
    
    private List<Encomenda> encomendas;

    @FXML
    @Override
    public void launchController(){
        super.launchController();
        preencheTabela(super.getFacade());
    }
    
    @FXML
    public void preencheTabela(ConfiguraFacil facade){
        this.encomendas = facade.getEncomendas();
        ObservableList<Encomenda> encs = FXCollections.observableArrayList(encomendas);
        listEncomendas.setItems(encs); 
    }
    
    @FXML
    public void emptyFields(){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Dados Incompletos!");
            alert.setContentText("É necessário inserir um ID para procura!");
            alert.show(); 
    }  
    
    @FXML
    public String searchEncomenda() throws Exception{
        String idEncomenda = idProcura.getText();
        if(idEncomenda.equals("")){
            emptyFields();
        }
        else{
            try{
                Encomenda e = super.getFacade().getEncomenda(idEncomenda);
                listEncomendas.getItems().stream().filter(item -> item.getId().equals(idEncomenda)).findAny().ifPresent(item -> {
                listEncomendas.getSelectionModel().select(item);
                listEncomendas.scrollTo(item);
                });
                return idEncomenda;
            }
            catch(EncomendaInexistenteException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Dados inválidos");
                alert.setContentText("A Encomenda com o id " + idEncomenda + " não existe!");
                alert.show();
            }
        }
     
        idProcura.clear();
        return null;
    }
    
    public void seeDetailsEnc() throws EncomendaInexistenteException, IOException, Exception{
        String idEncomenda = searchEncomenda();
        super.setEncomenda(idEncomenda);
        startController("Views/DetalhesEncomenda.fxml");   
    }
    
    @FXML
    public void backController() throws IOException{
        startController("Views/Fabrica.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        idEncomenda.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        dateEncomenda.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getData().format(formatter)));
        estadoEncomenda.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstado()));
        nomeCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeCliente()));
        contactoCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContactoCliente()));
        detalhesEncomenda.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDetalhes()));
    }  
    
}
