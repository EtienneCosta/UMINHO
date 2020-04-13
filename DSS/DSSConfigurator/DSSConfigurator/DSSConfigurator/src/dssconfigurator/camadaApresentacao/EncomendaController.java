/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaApresentacao;

import dssconfigurator.camadaLogica.Componente;
import dssconfigurator.camadaLogica.Configuracao;
import dssconfigurator.camadaLogica.Encomenda;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author joanacruz
 */
public class EncomendaController extends Controller implements Initializable {

    @FXML
    private ImageView modeloEscolhido;
    @FXML
    private Label precoTotal, precoModelo, precoCompsBase, precoCompsOpcs, precoPacotes;
    @FXML
    private ListView componentesConfig;
    @FXML
    private TextField nameClient, contactClient;

    private String idModelo;

    @Override
    public void launchController() {
        super.launchController();
        this.idModelo = super.getFacade().getConfiguracao().getIdModelo();
        Image image = imagemCorrespondente();
        this.modeloEscolhido.setImage(image);
        componentesConfig.setItems(convertToObservableComponente(super.getFacade().getObjectsComponentes(super.getFacade().getComponentesConfiguracao())));
        precoTotal.setText(String.valueOf(super.getFacade().getPrecoConfiguracaoAtual()));
        precoModelo.setText(String.valueOf(super.getFacade().getPrecoModelo()));
        precoCompsBase.setText(String.valueOf(super.getFacade().getPrecoComponentesBase()));
        precoCompsOpcs.setText(String.valueOf(super.getFacade().getPrecoComponentesOpcionais()));
        precoPacotes.setText(String.valueOf(super.getFacade().getPrecoPacotes()));
    }

    public boolean validDataClient() {
        boolean vazio = this.nameClient.getText().equals("")
                || this.contactClient.getText().equals("");
        if (vazio) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Dados Incompletos!");
            alert.setContentText("É necessário inserir os dados do cliente para prosseguir com a encomenda");
            alert.show();
        }
        return !vazio;
    }

    public ObservableList<Componente> convertToObservableComponente(List<Componente> components) {
        return FXCollections.observableArrayList(components);
    }

    public Image imagemCorrespondente() {
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

    public void registaEncomenda() throws IOException {
        if (validDataClient()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tem a certeza que pretende prosseguir com a encomenda?", ButtonType.OK, ButtonType.CANCEL);
            alert.setHeaderText("Confirmação de encomenda");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                int idEncomenda = super.getFacade().getNextIdEncomenda();
                double valor = super.getFacade().getPrecoConfiguracaoAtual();
                Configuracao c = super.getFacade().getConfiguracao();
                System.out.println(super.getFacade().getConfiguracao().getIdFuncionario());
                String nomeCliente = nameClient.getText();
                String contactoCliente = contactClient.getText();
                Encomenda e = new Encomenda(String.valueOf(idEncomenda), LocalDate.now(), valor, c, "Pendente", nomeCliente, contactoCliente);
                super.getFacade().registaEncomenda(e);
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "A encomenda foi registada, e irá ser brevemente tratada", ButtonType.OK);
                alert1.setHeaderText("Encomenda registada com sucesso!");
                alert1.showAndWait();
                if (alert1.getResult() == ButtonType.OK) {
                    backControllerModelCar();
                }
            }
        }
    }

    public void backController() throws IOException {
        super.getFacade().setComponentesOpcionaisConfiguracao(new ArrayList<>());
        super.getFacade().setPacotesConfiguracao(new ArrayList<>());

        startController("Views/ConfiguracaoExtra.fxml");
        
    }

    public void backControllerModelCar() throws IOException {
        super.getFacade().setConfiguracao(new Configuracao());

        startController("Views/ModelCar.fxml");
        
    }
    
    public void verificaDesconto(){
        List<String> pacotes = super.getFacade().verificaDesconto(this.idModelo);
        List<String> res = new ArrayList<>();
        for(String p : pacotes){
            res.add(super.getFacade().getPacote(p).getNome());
        }
        String pac = res.toString();
        if(pacotes.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Sem desconto!");
            alert.setContentText("Não teve direito a desconto pois nenhuma combinação de componentes dá origem a um pacote disponível");
            alert.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Direito a desconto!");
            alert.setContentText("Teve direito a desconto, pois certos componentes deram origem a este/s pacote/s " + pac);
            alert.show();
            precoTotal.setText(String.valueOf(super.getFacade().getPrecoConfiguracaoAtual()));
            precoCompsOpcs.setText(String.valueOf(super.getFacade().getPrecoComponentesOpcionais()));
            precoPacotes.setText(String.valueOf(super.getFacade().getPrecoPacotes()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.componentesConfig.setCellFactory(param -> new ListCell<Componente>() {
            @Override
            protected void updateItem(Componente item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getNome() == null) {
                    setText(null);
                } else {
                    setText(item.getNameAndPrice());
                }
            }
        });
    }

}
