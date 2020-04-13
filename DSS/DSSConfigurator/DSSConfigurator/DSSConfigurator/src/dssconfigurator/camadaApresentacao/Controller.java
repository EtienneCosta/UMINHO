/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaApresentacao;

import dssconfigurator.camadaLogica.ConfiguraFacil;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Joana Marta Cruz
 */
public abstract class Controller {
    
    private ConfiguraFacil facade;
    private Scene scene;
    private Stage stage;
    private String idFuncionario;
    private String idEncomenda;
    

    public void setFacade(ConfiguraFacil facade) {
        this.facade = facade;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public ConfiguraFacil getFacade() {
        return this.facade;
    }
    
    public Stage getStage(){
        return this.stage;
    }
    
    public String getFuncionario(){
        return this.idFuncionario;
    }
    
    public void setFuncionario(String idFuncionario){
        this.idFuncionario = idFuncionario;
    }
    
    public String getEncomenda(){
        return this.idEncomenda;
    }
    
    public void setEncomenda(String idEncomenda){
        this.idEncomenda = idEncomenda;
    }
    
    public void startController(String path) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        Parent lista = fxmlLoader.load();                   
        Controller controller = fxmlLoader.getController();
        controller.setFacade(this.facade);
        controller.setScene(new Scene(lista));
        controller.setStage(this.stage);
        controller.setFuncionario(this.idFuncionario);
        controller.setEncomenda(this.idEncomenda);
        controller.launchController();
    }
    
    public void launchController(){
        this.stage.setScene(scene);
        this.stage.centerOnScreen();
        this.stage.show();
    }
}
