/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaApresentacao;

import dssconfigurator.camadaLogica.ConfiguraFacil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Joana Marta Cruz
 */
public class FabricaController extends Controller implements Initializable {
    
    @FXML
    private Button encomendasButton, stockButton;
    
    
    public void stockView() throws IOException{
        startController("Views/StockView.fxml");
    }
        
    public void productionView() throws IOException{
        startController("Views/ProductionView.fxml");      
    }
    
    public void backController() throws IOException{
        startController("Views/Mainview.fxml");       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
