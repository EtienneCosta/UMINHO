/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaApresentacao;

import com.sun.deploy.Environment;
import dssconfigurator.camadaLogica.ConfiguraFacil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author joanacruz
 */
public class DSSConfigurator extends Application {
    
    private ConfiguraFacil facade = new ConfiguraFacil();
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/Mainview.fxml"));                    
        Parent lista = fxmlLoader.load();
        stage.setTitle("ConfiguraFácil");
        MainviewController mainController = fxmlLoader.getController();
        mainController.setFacade(this.facade);
        mainController.setScene(new Scene(lista));
        mainController.setStage(stage);            
        mainController.launchController();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
