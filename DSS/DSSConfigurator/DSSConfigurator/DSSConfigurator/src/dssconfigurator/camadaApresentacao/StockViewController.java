/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaApresentacao;

import dssconfigurator.camadaLogica.Componente;
import Exceptions.ComponenteInexistenteException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

/**
 * FXML Controller class
 *
 * @author joanacruz
 */
public class StockViewController extends Controller implements Initializable {

    @FXML
    private TableView<Componente> tbStock;
    @FXML
    private TextField procuraComp;
    @FXML
    private TableColumn<Componente, String> idComponent, nameComponent, descriptionComponent, stockComponent, categoryComponent, priceComponent;
    
    private ObservableList<Componente> componentes;
    
    @FXML
    @Override
    public void launchController(){
        super.launchController();
        preencheTabela();
    } 
    
    private void preencheTabela(){
        List<Componente> comps = super.getFacade().getComponentes();
        this.componentes = FXCollections.observableArrayList(comps);
        tbStock.setItems(componentes);
    }
    
    public void backController() throws IOException{
        startController("Views/Fabrica.fxml");
    }
   
    @FXML
    private void emptyFields(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Dados Incompletos!");
        alert.setContentText("É necessário inserir um ID para procura!");
        alert.show(); 
    }
    
    @FXML
    public String searchComponente(){
        String idComponente = procuraComp.getText();
        if(idComponente.equals("")){
            emptyFields();
        }
        else{
            try{
                Componente c = super.getFacade().getComponente(idComponente);
                tbStock.getItems().stream().filter(item -> item.getId().equals(idComponente)).findAny().ifPresent(item -> {
                tbStock.getSelectionModel().select(item);
                tbStock.scrollTo(item);
                });
                return idComponente;
            }
            catch(ComponenteInexistenteException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Dados inválidos");
                alert.setContentText("O componente com o id " + idComponente + " não existe!");
                alert.show();

            }
        }

        procuraComp.clear();
        return null;
    }
    
    @FXML
    public void changeStock() throws ComponenteInexistenteException{
        String idComponente = searchComponente();
        
        if(idComponente != null){
            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("Gestão de stock");
            dialog.setContentText("Novo stock: ");
            Optional<String> result = dialog.showAndWait();
            super.getFacade().setStockComponente(idComponente,Integer.parseInt(result.get())); 
            preencheTabela();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idComponent.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        nameComponent.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        descriptionComponent.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescrição()));       
        categoryComponent.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCategoria())));       
        priceComponent.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPreco())));       
        stockComponent.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStock())));
    }    
    
}
