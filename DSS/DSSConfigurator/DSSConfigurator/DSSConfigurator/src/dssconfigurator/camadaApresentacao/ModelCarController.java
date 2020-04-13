/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaApresentacao;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author joanacruz
 */
public class ModelCarController extends Controller implements Initializable {
    
    @FXML
    private ImageView model1, model2, model3, model4, model5, model6, model7, model8;
    @FXML
    private Circle circle1, circle2, circle3, circle4, circle5, circle6, circle7, circle8;
    
    private String idModelo;
        
    @Override
    public void launchController(){
        super.launchController();
    }
    
    public void allCirclesInvisble(){
        circle1.setVisible(false);
        circle2.setVisible(false);
        circle3.setVisible(false);
        circle4.setVisible(false);
        circle5.setVisible(false);
        circle6.setVisible(false);
        circle7.setVisible(false);
        circle8.setVisible(false);
    }
    
    public void click(String idModelo, Circle circle){
        this.idModelo = idModelo;
        allCirclesInvisble();
        circle.setVisible(true);
    }    
    
    public void nextController() throws IOException{
        if(idModelo == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro!");
            alert.setContentText("Tem que seleccionar um modelo de carro!");
            alert.show();
        }
        else{            
            super.getFacade().setIdModeloConfiguracao(idModelo);
            super.getFacade().setIdFuncionarioConfiguracao(super.getFuncionario());
            startController("Views/ConfiguracaoBase.fxml");
        }
    }
    
    public void backController() throws IOException{
        startController("Views/Mainview.fxml");       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.model1.setOnMouseClicked((e)->{click("1", circle1);});
        this.model2.setOnMouseClicked((e)->{click("2", circle2);});
        this.model3.setOnMouseClicked((e)->{click("3", circle3);});
        this.model4.setOnMouseClicked((e)->{click("4", circle4);});
        this.model5.setOnMouseClicked((e)->{click("5", circle5);});
        this.model6.setOnMouseClicked((e)->{click("6", circle6);});
        this.model7.setOnMouseClicked((e)->{click("7", circle7);});
        this.model8.setOnMouseClicked((e)->{click("8", circle8);});


    }    
    
}
