/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaApresentacao;

import dssconfigurator.camadaLogica.Configuracao;
import dssconfigurator.camadaLogica.Encomenda;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Joana Marta Cruz
 */
public class DetalhesEncomendaController extends Controller implements Initializable {

    
    @FXML
    private Text idModelo;
    
    @FXML
    private ComboBox componentesBase, componentesExtra, Pacotes;
   
    private Configuracao config;
   
    
    @Override
    public void launchController(){
        try {
            super.launchController();
            String idEncomenda = super.getEncomenda();
            Encomenda e = super.getFacade().getEncomenda(idEncomenda);
            this.config = e.getConfig();
            
            List<String> componentesBase = this.config.getComponentesBase();
            List<String> cBase = new ArrayList<>();
            for(String s : componentesBase) 
                cBase.add(super.getFacade().getIdNomeComponente(s));
            List<String> componentesOpcionais = this.config.getComponentesOpcionais();
            List<String> cOpcionais = new ArrayList<>();
            for(String s : componentesOpcionais) 
                cOpcionais.add(super.getFacade().getIdNomeComponente(s));

            idModelo.setText(this.config.getIdModelo());
            this.componentesBase.getItems().addAll(cBase);
            this.componentesExtra.getItems().addAll(cOpcionais);
            this.Pacotes.getItems().addAll(cBase);
        } catch (Exception ex) {
            Logger.getLogger(DetalhesEncomendaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void backController() throws IOException{
        startController("Views/ProductionView.fxml");
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
