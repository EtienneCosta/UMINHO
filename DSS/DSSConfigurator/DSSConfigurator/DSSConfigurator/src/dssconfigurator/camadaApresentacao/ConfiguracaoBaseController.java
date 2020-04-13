/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaApresentacao;

import dssconfigurator.camadaLogica.Categoria;
import Exceptions.ComponenteInexistenteException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author joanacruz
 */
public class ConfiguracaoBaseController extends Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ComboBox motor, transmissao, cor, estofo, tejadilho, jantes;
    @FXML
    private Label priceModel;
    @FXML
    private ImageView modeloEscolhido;

    private String idModelo;

    @Override
    public void launchController() {
        super.launchController();
        this.idModelo = super.getFacade().getConfiguracao().getIdModelo();
        setComponentesComboBoxes();
        this.priceModel.setText(String.valueOf(super.getFacade().getPrecoModelo()) + "€");
        Image image = imagemCorrespondente();
        this.modeloEscolhido.setImage(image);
    }

    private Image imagemCorrespondente() {
        String url = null;
        switch (this.idModelo) {
            case "1":
                url = "file:src/dssConfigurator/Images/serie1.jpeg";
                break;
            case "2":
                url = "file:src/dssConfigurator/Images/serie2.png";
                break;
            case "3":
                url = "file:src/dssConfigurator/Images/serie3.jpeg";
                break;
            case "4":
                url = "file:src/dssConfigurator/Images/serie4.png";
                break;
            case "5":
                url = "file:src/dssConfigurator/Images/serie5.jpeg";
                break;
            case "6":
                url = "file:src/dssConfigurator/Images/serie6.png";
                break;
            case "7":
                url = "file:src/dssConfigurator/Images/serie7.jpeg";
                break;
            case "8":
                url = "file:src/dssConfigurator/Images/serie8.png";
                break;
            default:
                break;
        }
        Image image = new Image(url);
        return image;
    }

    /**
     * ***********************************************************
     * MÉTODOS AUXILIARES
     *
     ***********************************************************
     */
    private ObservableList<String> convertToObservableString(List<String> components) {
        return FXCollections.observableArrayList(components);
    }

    private List<String> convertToIdNamePrice(List<String> ids) {
        List<String> res = new ArrayList<>();
        for (String s : ids) {
            res.add(super.getFacade().getIdNomePrecoComponente(s));
        }
        return res;
    }
    
    private String getSelectedComponente(ComboBox box) {
        return (String) box.getItems().get(box.getSelectionModel().getSelectedIndex());
    }

    private String getSelectedId(String[] array) {
        return array[0];
    }
    
    
    private void setComponentesComboBoxes() {
        this.motor.setItems(convertToObservableString(convertToIdNamePrice(getMotores())));
        this.transmissao.setItems(convertToObservableString(convertToIdNamePrice(getTransmissoes())));
        this.cor.setItems(convertToObservableString(convertToIdNamePrice(getCores())));
        this.estofo.setItems(convertToObservableString(convertToIdNamePrice(getEstofos())));
        this.tejadilho.setItems(convertToObservableString(convertToIdNamePrice(getTejadilhos())));
        this.jantes.setItems(convertToObservableString(convertToIdNamePrice(getJantes())));
    }

    private boolean validaDados() {
        boolean vazio = (this.motor.getSelectionModel().getSelectedIndex() == -1) || (this.transmissao.getSelectionModel().getSelectedIndex() == -1)
                || (this.cor.getSelectionModel().getSelectedIndex() == -1) || (this.estofo.getSelectionModel().getSelectedIndex() == -1)
                || (this.tejadilho.getSelectionModel().getSelectedIndex() == -1) || (this.jantes.getSelectionModel().getSelectedIndex() == -1);

        if (vazio) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Dados Incompletos!");
            alert.setContentText("É necessário escolher todos os componentes base da configuração!");
            alert.show();
        }

        return !vazio;
    }

    /**
     * ***********************************************************
     * GETTERS DOS COMPONENTES DISPONIVEIS
     *
     ***********************************************************
     */
    private List<String> getMotores() {
        return convertToObservableString(super.getFacade().getComponentesDisponiveisCategoria(idModelo, Categoria.MOTOR));
    }

    private List<String> getTransmissoes() {
        return convertToObservableString(super.getFacade().getComponentesDisponiveisCategoria(idModelo, Categoria.TRANSMISSÃO));
    }

    private List<String> getCores() {
        return convertToObservableString(super.getFacade().getComponentesDisponiveisCategoria(idModelo, Categoria.COR));
    }

    private List<String> getEstofos() {
        return convertToObservableString(super.getFacade().getComponentesDisponiveisCategoria(idModelo, Categoria.ESTOFO));
    }

    private List<String> getTejadilhos() {
        return convertToObservableString(super.getFacade().getComponentesDisponiveisCategoria(idModelo, Categoria.CORTEJADILHO));
    }

    private List<String> getJantes() {
        return convertToObservableString(super.getFacade().getComponentesDisponiveisCategoria(idModelo, Categoria.JANTES));
    }

    public int registaConfBase() throws ComponenteInexistenteException {
        if (this.validaDados()) {

            List<String> base = new ArrayList<>();
            base.add(getSelectedId(getSelectedComponente(this.motor).split(" ")));
            base.add(getSelectedId(getSelectedComponente(this.transmissao).split(" ")));
            base.add(getSelectedId(getSelectedComponente(this.cor).split(" ")));
            base.add(getSelectedId(getSelectedComponente(this.estofo).split(" ")));
            base.add(getSelectedId(getSelectedComponente(this.tejadilho).split(" ")));
            base.add(getSelectedId(getSelectedComponente(this.jantes).split(" ")));

            super.getFacade().setComponentesBaseConfiguracao(base);
            return 1;
        }
        return 0;
    }

    /**
     * ***********************************************************
     * MUDANÇA DE CONTROLLERS
     *
     ***********************************************************
     */
    
    public void nextController() throws IOException, ComponenteInexistenteException {
        int valid = registaConfBase();
        if (valid == 1) {
            startController("Views/ConfiguracaoExtra.fxml");
        }
    }

    public void backController() throws IOException {
        startController("Views/ModelCar.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
