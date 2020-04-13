/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaApresentacao;

import dssconfigurator.camadaLogica.Categoria;
import dssconfigurator.camadaLogica.Componente;
import Exceptions.ComponenteInexistenteException;
import dssconfigurator.camadaLogica.Pacote;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

/**
 * FXML Controller class
 *
 * @author joanacruz
 */
public class ConfiguracaoExtraController extends Controller implements Initializable {

    @FXML
    private ComboBox pacotes, jantesPneus, interior, exterior, direccao, iluminacao, coresTeto, climatizacao;

    @FXML
    private ListView confAtual, pacotesConfAtual;

    @FXML
    private Label precoAtual;

    private String idModelo;
    private ObservableList<Componente> componentesObs;
    private ObservableList<Pacote> pacotesObs;

    @Override
    public void launchController() {
        super.launchController();
        this.idModelo = super.getFacade().getConfiguracao().getIdModelo();
        setComponentesComboBoxes();
        this.componentesObs = convertToObservableComponente(super.getFacade().getObjectsComponentes(super.getFacade().getComponentesBaseConfiguracao()));
        this.pacotesObs = convertToObservablePacote(new ArrayList<>());
        this.confAtual.setItems(componentesObs);
        this.pacotesConfAtual.setItems(pacotesObs);
        this.precoAtual.setText(String.valueOf(super.getFacade().getPrecoModeloAndComponentesBase()));
    }

    /**
     * ***********************************************************
     * GETTERS DOS COMPONENTES DISPONIVEIS
     *
     ***********************************************************
     */
    private List<String> getPacotes() {
        return super.getFacade().getPacotesDisponiveis(this.idModelo);
    }

    private List<String> getJantesPneus() {
        return super.getFacade().getComponentesDisponiveisCategoria(this.idModelo, Categoria.PNEUS);
    }

    private List<String> getInteriores() {
        return super.getFacade().getComponentesDisponiveisCategoria(this.idModelo, Categoria.INTERIOR);
    }

    private List<String> getExteriores() {
        return super.getFacade().getComponentesDisponiveisCategoria(this.idModelo, Categoria.EXTERIOR);
    }

    private List<String> getDireccoes() {
        return super.getFacade().getComponentesDisponiveisCategoria(this.idModelo, Categoria.DIREÇÃO);
    }

    private List<String> getIluminacoes() {
        return super.getFacade().getComponentesDisponiveisCategoria(this.idModelo, Categoria.FAROIS);
    }

    private List<String> getCoresTeto() {
        return super.getFacade().getComponentesDisponiveisCategoria(this.idModelo, Categoria.CORTETOINTERIOR);
    }

    private List<String> getClimatizacoes() {
        return super.getFacade().getComponentesDisponiveisCategoria(this.idModelo, Categoria.CLIMATIZAÇÃO);
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

    private ObservableList<Componente> convertToObservableComponente(List<Componente> components) {
        return FXCollections.observableArrayList(components);
    }

    private ObservableList<Pacote> convertToObservablePacote(List<Pacote> pacotes) {
        return FXCollections.observableArrayList(pacotes);
    }

    private List<String> convertToIdNamePriceComponente(List<String> ids) {
        List<String> res = new ArrayList<>();
        for (String s : ids) {
            res.add(super.getFacade().getIdNomePrecoComponente(s));
        }
        return res;
    }

    private List<String> convertToIdNamePricePacote(List<String> ids) {
        List<String> res = new ArrayList<>();
        for (String s : ids) {
            res.add(super.getFacade().getIdNomePrecoPacote(s));
        }
        return res;
    }

    private List<String> convertToName(List<String> ids) {
        List<String> res = new ArrayList<>();
        for (String s : ids) {
            res.add(super.getFacade().getNomeComponente(s));
        }
        return res;
    }

    private String getSelected(ComboBox box) {
        return (String) box.getItems().get(box.getSelectionModel().getSelectedIndex());
    }

    private int getSelectedIndice(ComboBox box) {
        return box.getSelectionModel().getSelectedIndex();
    }

    private void clearComboBox(ComboBox box) {
        box.getSelectionModel().select(-1);
    }

    private void lancaAviso(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Erro!");
        alert.setContentText("Tem que escolher algum componente da categoria " + s);
        alert.show();
    }

    private void lancaAvisoComponentePresente() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Erro!");
        alert.setContentText("Esse componente já foi adicionado!");
        alert.show();
    }

    private String getSelectedId(String[] array) {
        return array[0];
    }

    private void setComponentesComboBoxes() {
        this.pacotes.setItems(convertToObservableString(convertToIdNamePricePacote(getPacotes())));
        this.jantesPneus.setItems(convertToObservableString(convertToIdNamePriceComponente(getJantesPneus())));
        this.interior.setItems(convertToObservableString(convertToIdNamePriceComponente(getInteriores())));
        this.exterior.setItems(convertToObservableString(convertToIdNamePriceComponente(getExteriores())));
        this.direccao.setItems(convertToObservableString(convertToIdNamePriceComponente(getDireccoes())));
        this.iluminacao.setItems(convertToObservableString(convertToIdNamePriceComponente(getIluminacoes())));
        this.coresTeto.setItems(convertToObservableString(convertToIdNamePriceComponente(getCoresTeto())));
        this.climatizacao.setItems(convertToObservableString(convertToIdNamePriceComponente(getClimatizacoes())));
    }

    /**
     * ***********************************************************
     * MÉTODOS RELACIONADOS COM ADIÇÃO E REMOÇÃO DE COMPONENTES E PACOTES
     *
     ***********************************************************
     */
    private void removeListComponent(List<String> components) {
        super.getFacade().removeListaComponentes(components);
        componentesObs.removeAll(super.getFacade().getObjectsComponentes(components));
    }

    private void addListComponent(List<String> components) {
        super.getFacade().adicionaListaDependencias(components);
        componentesObs.addAll(super.getFacade().getObjectsComponentes(components));
    }

    private void addComponente(String idComponente) throws ComponenteInexistenteException {
        super.getFacade().adicionaComponenteOpcionalConfiguracao(idComponente);
        componentesObs.add(super.getFacade().getComponente(idComponente));
        this.precoAtual.setText(String.valueOf(super.getFacade().getPrecoConfiguracaoAtual()));
    }

    private void removeComponente(Componente c) {
        if (c.getCategoria() == Categoria.MOTOR || c.getCategoria() == Categoria.TRANSMISSÃO
                || c.getCategoria() == Categoria.COR || c.getCategoria() == Categoria.ESTOFO || c.getCategoria() == Categoria.CORTEJADILHO || c.getCategoria() == Categoria.JANTES) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro!");
            alert.setContentText("Não pode remover os componentes base da configuração! Caso os queira alterar terá que reiniciar");
            alert.show();
        } else {
            super.getFacade().removeComponenteOpcionalConfiguracao(c.getId());
            componentesObs.remove(c);
            this.precoAtual.setText(String.valueOf(super.getFacade().getPrecoConfiguracaoAtual()));
        }
    }

    private void removePacote(String idPacote) {
        super.getFacade().removePacoteConfiguracao(idPacote);
        componentesObs.removeAll(super.getFacade().getObjectsComponentes(super.getFacade().getComponentesPacote(idPacote)));
        pacotesObs.remove(super.getFacade().getPacote(idPacote));
        this.precoAtual.setText(String.valueOf(super.getFacade().getPrecoConfiguracaoAtual()));
    }

    private void addPacote(String idPacote) {
        super.getFacade().adicionaPacoteConfiguracao(idPacote);
        componentesObs.addAll(super.getFacade().getObjectsComponentes(super.getFacade().getComponentesPacote(idPacote)));
        pacotesObs.add(super.getFacade().getPacote(idPacote));
        this.precoAtual.setText(String.valueOf(super.getFacade().getPrecoConfiguracaoAtual()));
    }

    /**
     * ***********************************************************
     * INCOMPATIBILIDADES E DEPENDÊCIAS
     *
     ***********************************************************
     */
    private void justIncompatibilidades(String idComponente, List<String> listaIncompatibilidades, String inc) throws ComponenteInexistenteException {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Tem a certeza que pretende adicionar o componente "
                + super.getFacade().getNomeComponente(idComponente) + "? Devido a incompatibilidades, caso o adicione vai ter que remover os seguintes componentes" + inc, ButtonType.OK, ButtonType.CANCEL);
        alert.setHeaderText("Aviso de incompatibilidades");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            removeListComponent(listaIncompatibilidades);
            addComponente(idComponente);
        }
    }

    private void justDependencias(String idComponente, List<String> listaDependencias, String dep) throws ComponenteInexistenteException {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Tem a certeza que pretende adicionar o componente "
                + super.getFacade().getNomeComponente(idComponente) + "? Devido a dependências, caso o adicione vai ter que adicionar os seguintes componentes" + dep, ButtonType.OK, ButtonType.CANCEL);
        alert.setHeaderText("Aviso de dependências");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            addListComponent(listaDependencias);
            addComponente(idComponente);
        }
    }

    private void incompatibilidadesAndDependencias(String idComponente, List<String> listaDependencias, List<String> listaIncompatibilidades, String dep, String inc) throws ComponenteInexistenteException {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Tem a certeza que pretende adicionar o componente "
                + super.getFacade().getNomeComponente(idComponente) + "? Devido a dependências, caso o adicione vai ter que adicionar os seguintes componentes" + dep
                + " E devido a incompatibilidades, caso o adicione vai ter que remover os seguintes componentes" + inc, ButtonType.OK, ButtonType.CANCEL);
        alert.setHeaderText("Aviso de dependências e incompatibilidades");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            removeListComponent(listaIncompatibilidades);
            addListComponent(listaDependencias);
            addComponente(idComponente);
        }
    }

    private void justIncompatibilidadesPacote(String idPacote, List<String> listaIncompatibilidades, String inc, List<String> jaExistentes) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Tem a certeza que pretende adicionar o pacote "
                + super.getFacade().getNomeComponente(idPacote) + "? Devido a incompatibilidades, caso o adicione vai ter que remover os seguintes componentes" + inc, ButtonType.OK, ButtonType.CANCEL);
        alert.setHeaderText("Aviso de incompatibilidades");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            if (jaExistentes.isEmpty()) {
                removeListComponent(listaIncompatibilidades);
                addPacote(idPacote);
            } else {
                removeListComponent(listaIncompatibilidades);
                removeListComponent(jaExistentes);
                addPacote(idPacote);
            }
        }
    }

    private void justDependenciasPacote(String idPacote, List<String> listaDependencias, String dep, List<String> jaExistentes) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Tem a certeza que pretende adicionar o pacote "
                + super.getFacade().getNomeComponente(idPacote) + "? Devido a dependências, caso o adicione vai ter que adicionar os seguintes componentes" + dep, ButtonType.OK, ButtonType.CANCEL);
        alert.setHeaderText("Aviso de dependências");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            if (jaExistentes.isEmpty()) {
                addListComponent(listaDependencias);
                addPacote(idPacote);
            } else {
                addListComponent(listaDependencias);
                removeListComponent(jaExistentes);
                addPacote(idPacote);
            }
        }
    }

    private void incompatibilidadesAndDependenciasPacote(String idPacote, List<String> listaDependencias, List<String> listaIncompatibilidades, String dep, String inc, List<String> jaExistentes) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Tem a certeza que pretende adicionar o pacote "
                + super.getFacade().getNomeComponente(idPacote) + "? Devido a dependências, caso o adicione vai ter que adicionar os seguintes componentes" + dep
                + " E devido a incompatibilidades, caso o adicione vai ter que remover os seguintes componentes" + inc, ButtonType.OK, ButtonType.CANCEL);
        alert.setHeaderText("Aviso de dependências e incompatibilidades");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            if (jaExistentes.isEmpty()) {
                removeListComponent(listaIncompatibilidades);
                addListComponent(listaDependencias);
                addPacote(idPacote);
            } else {
                removeListComponent(listaIncompatibilidades);
                removeListComponent(jaExistentes);
                addListComponent(listaDependencias);
                addPacote(idPacote);
            }
        }
    }

    private void verificaIncompatibilidadesEDependenciasComponente(String idComponente) throws ComponenteInexistenteException {
        List<String> listaIncompatibilidades = super.getFacade().verificaIncompatibilidadesComponente(idComponente);
        String inc = convertToName(listaIncompatibilidades).toString();
        List<String> listaDependencias = super.getFacade().verificaDependenciasComponente(idComponente);
        System.out.println(listaDependencias.size());
        String dep = convertToName(listaDependencias).toString();
        if (listaIncompatibilidades.isEmpty() && listaDependencias.isEmpty()) {
            addComponente(idComponente);
        } else if (!listaIncompatibilidades.isEmpty() && listaDependencias.isEmpty()) {
            justIncompatibilidades(idComponente, listaIncompatibilidades, inc);
        } else if (listaIncompatibilidades.isEmpty() && !listaDependencias.isEmpty()) {
            justDependencias(idComponente, listaDependencias, dep);
        } else {
            incompatibilidadesAndDependencias(idComponente, listaDependencias, listaIncompatibilidades, dep, inc);
        }
    }

    private void verificaIncompatibilidadesEDependenciasPacote(String idPacote) throws ComponenteInexistenteException {
        List<String> listaIncompatibilidades = super.getFacade().verificaIncompatibilidadesPacote(idPacote);
        String inc = convertToName(listaIncompatibilidades).toString();
        List<String> listaDependencias = super.getFacade().verificaDependenciasPacote(idPacote);
        String dep = convertToName(listaDependencias).toString();
        List<String> jaExistentes = super.getFacade().verificaSeComponenteIntegraConfAtual(idPacote);
        if (listaIncompatibilidades.isEmpty() && listaDependencias.isEmpty()) {
            if (jaExistentes.isEmpty()) {
                addPacote(idPacote);
            } else {
                removeListComponent(jaExistentes);
                addPacote(idPacote);
            }

        } else if (!listaIncompatibilidades.isEmpty() && listaDependencias.isEmpty()) {
            justIncompatibilidadesPacote(idPacote, listaIncompatibilidades, inc, jaExistentes);
        } else if (listaIncompatibilidades.isEmpty() && !listaDependencias.isEmpty()) {
            justDependenciasPacote(idPacote, listaDependencias, dep, jaExistentes);
        } else {
            incompatibilidadesAndDependenciasPacote(idPacote, listaDependencias, listaIncompatibilidades, dep, inc, jaExistentes);
        }

    }

    /**
     * ***********************************************************
     * ADIÇÃO DE COMPONENTES E PACOTES
     *
     ***********************************************************
     */
    public void addPacote() throws ComponenteInexistenteException {
        if (getSelectedIndice(this.pacotes) == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro!");
            alert.setContentText("Tem que escolher algum pacote");
            alert.show();
        } else {
            String idPacote = getSelectedId(getSelected(this.pacotes).split(" "));
            boolean exist = super.getFacade().verificaSePacoteJaFoiAdicionado(idPacote);
            if (exist == true) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erro!");
                alert.setContentText("Esse pacote já foi adicionado!");
                alert.show();
            } else {
                verificaIncompatibilidadesEDependenciasPacote(idPacote);
            }
        }
        clearComboBox(this.pacotes);
    }

    public void addComponenteOpcionalJantesPneus() throws ComponenteInexistenteException {
        if (getSelectedIndice(this.jantesPneus) == -1) {
            lancaAviso("Jantes e Pneus");
        } else {
            String idComponente = getSelectedId(getSelected(this.jantesPneus).split(" "));
            boolean exist = super.getFacade().verificaSeComponenteJaFoiAdicionado(idComponente);
            if (exist == true) {
                lancaAvisoComponentePresente();
            } else {
                verificaIncompatibilidadesEDependenciasComponente(idComponente);
            }
        }
        clearComboBox(this.jantesPneus);
    }

    public void addComponenteOpcionalInterior() throws ComponenteInexistenteException {
        if (getSelectedIndice(this.interior) == -1) {
            lancaAviso("Interior");
        } else {
            String idComponente = getSelectedId(getSelected(this.interior).split(" "));
            boolean exist = super.getFacade().verificaSeComponenteJaFoiAdicionado(idComponente);
            if (exist == true) {
                lancaAvisoComponentePresente();
            } else {
                verificaIncompatibilidadesEDependenciasComponente(idComponente);
            }
        }

        clearComboBox(this.interior);
    }

    public void addComponenteOpcionalExterior() throws ComponenteInexistenteException {
        if (getSelectedIndice(this.exterior) == -1) {
            lancaAviso("Exterior");
        } else {
            String idComponente = getSelectedId(getSelected(this.exterior).split(" "));
            boolean exist = super.getFacade().verificaSeComponenteJaFoiAdicionado(idComponente);
            if (exist == true) {
                lancaAvisoComponentePresente();
            } else {
                verificaIncompatibilidadesEDependenciasComponente(idComponente);
            }
        }
        clearComboBox(this.exterior);
    }

    public void addComponenteOpcionalDireccao() throws ComponenteInexistenteException {
        if (getSelectedIndice(this.direccao) == -1) {
            lancaAviso("Direcção");
        } else {
            String idComponente = getSelectedId(getSelected(this.direccao).split(" "));
            boolean exist = super.getFacade().verificaSeComponenteJaFoiAdicionado(idComponente);
            if (exist == true) {
                lancaAvisoComponentePresente();
            } else {
                verificaIncompatibilidadesEDependenciasComponente(idComponente);
            }
        }
        clearComboBox(this.direccao);
    }

    public void addComponenteOpcionalIluminacao() throws ComponenteInexistenteException {
        if (getSelectedIndice(this.iluminacao) == -1) {
            lancaAviso("Iluminação");
        } else {
            String idComponente = getSelectedId(getSelected(this.iluminacao).split(" "));
            boolean exist = super.getFacade().verificaSeComponenteJaFoiAdicionado(idComponente);
            if (exist == true) {
                lancaAvisoComponentePresente();
            } else {
                verificaIncompatibilidadesEDependenciasComponente(idComponente);
            }
        }
        clearComboBox(this.iluminacao);
    }

    public void addComponenteOpcionalcoresTeto() throws ComponenteInexistenteException {
        if (getSelectedIndice(this.coresTeto) == -1) {
            lancaAviso("Cores Teto");
        } else {
            String idComponente = getSelectedId(getSelected(this.coresTeto).split(" "));
            boolean exist = super.getFacade().verificaSeComponenteJaFoiAdicionado(idComponente);
            if (exist == true) {
                lancaAvisoComponentePresente();
            } else {
                verificaIncompatibilidadesEDependenciasComponente(idComponente);
            }
        }
        clearComboBox(this.coresTeto);
    }

    public void addComponenteOpcionalClimatizacao() throws ComponenteInexistenteException {
        if (getSelectedIndice(this.climatizacao) == -1) {
            lancaAviso("Climatização");
        } else {
            String idComponente = getSelectedId(getSelected(this.climatizacao).split(" "));
            boolean exist = super.getFacade().verificaSeComponenteJaFoiAdicionado(idComponente);
            if (exist == true) {
                lancaAvisoComponentePresente();
            } else {
                verificaIncompatibilidadesEDependenciasComponente(idComponente);
            }
        }
        clearComboBox(this.climatizacao);
    }

    /**
     * ***********************************************************
     * REMOÇÃO DE COMPONENTES E PACOTES
     *
     ***********************************************************
     */
    //o utilizador é que remove o pacote
    public void removeComponenteOpcional() throws ComponenteInexistenteException {
        Componente c = (Componente) this.confAtual.getSelectionModel().getSelectedItem();
        boolean existPacote = super.getFacade().verificaSeComponentePertencePacote(c.getId());
        if (existPacote == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro!");
            alert.setContentText("Esse componente faz parte de um pacote, caso o queira remover tem que remover o pacote por completo!");
            alert.show();
        } else {
            List<String> listaDependentes = super.getFacade().verificaDependentesComponente(c.getId());
            String dep = convertToName(listaDependentes).toString();
            if (listaDependentes.isEmpty()) {
                removeComponente(c);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Tem a certeza que pretende remover o componente "
                        + c.getNome() + "? Devido a dependências, caso o remova serão removidos os seguintes componentes" + dep, ButtonType.OK, ButtonType.CANCEL);
                alert.setHeaderText("Aviso de dependências");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    removeListComponent(listaDependentes);
                    removeComponente(c);
                }
            }
        }
    }

    public void removePacote() throws ComponenteInexistenteException {
        Pacote p = (Pacote) this.pacotesConfAtual.getSelectionModel().getSelectedItem();
        List<String> listaDependentes = super.getFacade().verificaDependentesPacote(p.getId());
        String dep = convertToName(listaDependentes).toString();
        if (listaDependentes.isEmpty()) {
            removePacote(p.getId());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Tem a certeza que pretende remover o pacote "
                    + p.getNome() + "? Devido a dependências, caso o remova serão removidos os seguintes componentes" + dep, ButtonType.OK, ButtonType.CANCEL);
            alert.setHeaderText("Aviso de dependências");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                removeListComponent(listaDependentes);
                removePacote(p.getId());
            }
        }
    }

    public void infoComponentesPacote() {
        String idPacote = getSelected(this.pacotes);
        List<String> compsPacotes = super.getFacade().getComponentesPacote(idPacote);
        List<String> nomeCompsPacotes = convertToName(compsPacotes);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Componentes que integram o pacote");
        alert.setContentText("Os componentes que integram o pacote são" + nomeCompsPacotes.toString());
        alert.show();
    }

    public void configuracaoOtima() throws ComponenteInexistenteException {

        List<String> componentes = super.getFacade().getComponentesOpcionaisConfiguracao();
        componentesObs.removeAll(componentes);
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Orçamento para configuração ótima");
        dialog.setContentText("Orçamento disponibilizado: ");
        Optional<String> result = dialog.showAndWait();
        super.getFacade().configuracaoOtima(idModelo, Double.parseDouble(result.get()));
        List<Componente> componentesAtual = super.getFacade().getObjectsComponentes(super.getFacade().getComponentesOpcionaisConfiguracao());
        componentesObs.addAll(componentesAtual);
        this.precoAtual.setText(String.valueOf(super.getFacade().getPrecoConfiguracaoAtual()));

    }

    /**
     * ***********************************************************
     * MUDANÇA DE CONTROLLERS
     *
     ***********************************************************
     */
    public void nextController() throws IOException {
        startController("Views/Encomenda.fxml");
    }

    public void backController() throws IOException {
        super.getFacade().setComponentesOpcionaisConfiguracao(new ArrayList<>());
        startController("Views/ConfiguracaoBase.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.confAtual.setCellFactory(param -> new ListCell<Componente>() {
            @Override
            protected void updateItem(Componente item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getNome() == null) {
                    setText(null);
                } else {
                    setText(item.getNome());
                }
            }
        });

        this.pacotesConfAtual.setCellFactory(param -> new ListCell<Pacote>() {
            @Override
            protected void updateItem(Pacote item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getNome() == null) {
                    setText(null);
                } else {
                    setText(item.getNome());
                }
            }
        });

    }

}
