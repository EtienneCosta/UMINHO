/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dssconfigurator.camadaLogica;

import Exceptions.UtilizadorExistenteException;
import Exceptions.UtilizadorInexistenteException;
import Exceptions.PasswordInvalidaException;
import Exceptions.EncomendaInexistenteException;
import Exceptions.ComponenteInexistenteException;
import dssconfigurator.camadaDados.ComponenteDAO;
import dssconfigurator.camadaDados.EncomendaDAO;
import dssconfigurator.camadaDados.ModeloDAO;
import dssconfigurator.camadaDados.PacoteDAO;
import dssconfigurator.camadaDados.UtilizadorDAO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 *
 * @author Joana Marta Cruz
 */
public class ConfiguraFacil {
    
    private Configuracao estado;
    private final ComponenteDAO componenteDAO;
    private final EncomendaDAO encomendaDAO;
    private final ModeloDAO modeloDAO;
    private final UtilizadorDAO utilizadorDAO;
    private final PacoteDAO pacoteDAO;
    
    public ConfiguraFacil(){
        this.estado = new Configuracao();
        this.componenteDAO = new ComponenteDAO();
        this.encomendaDAO = new EncomendaDAO();
        this.modeloDAO = new ModeloDAO();
        this.utilizadorDAO = new UtilizadorDAO();
        this.pacoteDAO = new PacoteDAO();
    }
   
        
    /*************************************************************
     *           LOGIN
     *
     ************************************************************/
    
    public Utilizador login(String username, String password) throws UtilizadorInexistenteException, PasswordInvalidaException{
        List<Utilizador> utilizadores = this.utilizadorDAO.list();
        for(Utilizador u : utilizadores){
            if(u.getUsername().equals(username) && u.getPassword().equals(password)){
                return u;
            }
            else if(u.getUsername().equals(username)) throw new PasswordInvalidaException("A password é inválida");
        }
        throw new UtilizadorInexistenteException("O utilizador com o seguinte" + username + " não existe!");
    }
    

    
    /*************************************************************
     *           FUNCIONÁRIOS
     *
     ************************************************************/
    
        
    public boolean funcionarioExistente(String username){
        return this.utilizadorDAO.containsKey(username);
    }
    
    public List<Funcionario> getFuncionarios(){
        List<Funcionario> res = new ArrayList<>();
        
        this.utilizadorDAO.list().stream().filter((u) -> (u instanceof Funcionario)).forEachOrdered((u) -> {
            res.add((Funcionario)u);
        });
        
        return res;
    }
    
    public Funcionario getFuncionario(String username) throws UtilizadorInexistenteException{
        if (!funcionarioExistente(username)) {
            throw new UtilizadorInexistenteException("O utilizador com o seguinte" + username + " não existe!");
        }        
        Funcionario f = (Funcionario)this.utilizadorDAO.get(username);
        return f;
    }
    
    public void registaFuncionario(Funcionario f) throws UtilizadorExistenteException{
        if(funcionarioExistente(f.getUsername())){
            throw new UtilizadorExistenteException("O utilizador com o seguinte" + f.getUsername() + " já existe!");
        }
        this.utilizadorDAO.put(f.getUsername(), f.clone());
    }
    
    public void removeFuncionario(String username)/* throws UtilizadorInexistenteException*/{
        /*if (!this.utilizadores.containsKey(username)) {
            throw new UtilizadorInexistenteException("O utilizador com o seguinte" + username + " não existe!");
        }*/
        this.utilizadorDAO.remove(username);
    }
    
    public void atualizaFuncionario(String username, String adress, String contact){
        this.utilizadorDAO.updateInfo(username, adress, contact);
    }
    
   
     /*************************************************************
     *           ENCOMENDAS
     *
     ************************************************************/
    public boolean encomendaExistente(String id){
        return this.encomendaDAO.containsKey(id);
    }
        
    public List<Encomenda> getEncomendas(){
        return this.encomendaDAO.list();
    }
    
    public Encomenda getEncomenda(String id) throws EncomendaInexistenteException, Exception{
        if (!encomendaExistente(id)) {
            throw new EncomendaInexistenteException("A encomenda com o seguinte" + id + " não existe!");
        }        
        Encomenda e = this.encomendaDAO.get(id);
        return e.clone();
    }
      
    public void registaEncomenda(Encomenda e){
        this.encomendaDAO.put(e.clone());
    }
    
    /*************************************************************
     *           COMPONENTES
     *
     ************************************************************/

    public boolean componenteExistente(String id){
        return this.componenteDAO.containsKey(id);
    }
        
    public List<Componente> getComponentes(){
        return  this.componenteDAO.list();      
    }
    
    public Componente getComponente(String id) throws ComponenteInexistenteException{
        if (!componenteExistente(id)) {
            throw new ComponenteInexistenteException("O componente com o seguinte" + id + " não existe!");
        }        
        Componente c = this.componenteDAO.get(id);
        return c.clone();
    }
    
    //Atualiza o stock do componente
    public void setStockComponente(String idComponente, int stock){ 
        this.componenteDAO.updateStock(idComponente, stock);
    }   
        
    public String getNomeComponente(String idComponente){
        return this.componenteDAO.getNome(idComponente);
    }
    
    public String getIdNomeComponente(String idComponente){
        return "Componente " + idComponente + " - " + this.componenteDAO.getNome(idComponente);
    }
    
    public String getIdNomePrecoComponente(String idComponente){
        return idComponente + " - " + this.componenteDAO.getNome(idComponente) + " - " + componenteDAO.getPreco(idComponente) + " €";
    }    
        
    public List<Componente> getObjectsComponentes(List<String> ids){     
        List<Componente> res = new ArrayList<>();
        ids.forEach((s) -> {
            res.add(componenteDAO.get(s));
        });
        return res;
    }
 
        
    /*************************************************************
     *           PACOTES
     *
     ************************************************************/
    
    public List<String> getComponentesPacote(String idPacote){
        return pacoteDAO.getComponentes(idPacote);
    }
    
    public Pacote getPacote(String idPacote){
        return this.pacoteDAO.get(idPacote);
    }
          
    public double getPrecoPacote(String idPacote){
        double desconto = this.pacoteDAO.getDesconto(idPacote);
        List<String> componentesPacote = getComponentesPacote(idPacote);
        double preco = 0;
        
        preco = componentesPacote.stream().map((idComponente) -> this.componenteDAO.getPreco(idComponente)).reduce(preco, (accumulator, _item) -> accumulator + _item);
        
        return preco - preco*desconto;
    }
          
       
    public String getnomePrecoPacote(String idPacote){
        Pacote p = this.pacoteDAO.get(idPacote);
        return p.getNome() + " - " + String.valueOf(getPrecoPacote(idPacote)) + " €";
    }
    
    public String getIdNomePrecoPacote(String idPacote){
        Pacote p = pacoteDAO.get(idPacote);
        return p.getId() + " - " + getnomePrecoPacote(idPacote);
    }

        
     /*************************************************************
     *           MODELOS
     *
     ************************************************************/
    
    public Modelo getModelo(String idModelo){       
        return this.modeloDAO.get(idModelo);
    }
    
    //retorna os componentes que estao disponiveis para cada categoria dado um modelo
    public List<String> getComponentesDisponiveisCategoria(String idModelo, Categoria c){
        return this.modeloDAO.get(idModelo).getComponentesDisponiveisCategoria(c);
    }
        
    //retorna os pacotes disponiveis dado um modelo
    public List<String> getPacotesDisponiveis(String idModelo){
        return this.modeloDAO.get(idModelo).getPacotesDisponiveis();
    }
    
    /*************************************************************
    *           CONFIGURAÇÃO
    *
    ************************************************************/
    
    public Configuracao getConfiguracao(){
        return this.estado;
    }
    //retorna a lista de componentes base da cofiguração
    public List<String> getComponentesBaseConfiguracao(){
        List<String> res = new ArrayList<>();
        List<String> componentesBase = this.estado.getComponentesBase();
        
        componentesBase.forEach((c) -> {
            res.add(c);
        });
        
        return res;
    }
    
    //retorna a lista de componente opcionais da configuração
    public List<String> getComponentesOpcionaisConfiguracao(){
        List<String> res = new ArrayList<>();
        List<String> componentesOpcionais = this.estado.getComponentesOpcionais();
        
        componentesOpcionais.forEach((c) -> {
            res.add(c);
        });
        
        return res;
    }
            
    //retorna os componentes dos pacotes da configuração
    public List<String> getComponentesPacoteConfiguracao(){
        List<String> idsPacotes = this.estado.getPacotes();
        List<String> res = new ArrayList<>();
        
        idsPacotes.forEach((id) -> {
            res.addAll(getComponentesPacote(id));
        });
        
        return res;
    }
    
    //retorna todos os componentes que integram a configuração atual, sejam base, opcionais ou de pacotes
    public List<String> getComponentesConfiguracao(){
        List<String> componentes = new ArrayList<>();
        componentes.addAll(getComponentesBaseConfiguracao());
        componentes.addAll(getComponentesOpcionaisConfiguracao());
        componentes.addAll(getComponentesPacoteConfiguracao());
        
        return componentes;
    }
    
    public void setConfiguracao(Configuracao c){
        this.estado = c;
    }
    
    public void setIdModeloConfiguracao(String idModelo){
        this.estado.setIdModelo(idModelo);
    }
    
    public void setIdFuncionarioConfiguracao(String idFunc){
        this.estado.setIdFuncionario(idFunc);
    }
    
    //regista os componentes base
    public void setComponentesBaseConfiguracao(List<String> componentesBase){
        this.estado.setComponentesBase(componentesBase);
    }   
        
    public void setComponentesOpcionaisConfiguracao(List<String> componentesOpcionais){
        this.estado.setComponentesOpcionais(componentesOpcionais);
    }
    
    public void setPacotesConfiguracao(List<String> pacotes){
        this.estado.setPacotes(pacotes);
    }
    
    //adiciona componente opcional à configuração
    public void adicionaComponenteOpcionalConfiguracao(String idComponente){
        this.estado.adicionaComponenteOpcional(idComponente);
    }
    
    //remove componente opcional da configuração
    public void removeComponenteOpcionalConfiguracao(String idComponente){
        this.estado.removeComponenteOpcional(idComponente);
    }
    
    //adiciona pacote à configuração
    public void adicionaPacoteConfiguracao(String idPacote){
        this.estado.adicionaPacote(idPacote);
    }
    
    //remove pacote da configuração
    public void removePacoteConfiguracao(String idPacote){
        this.estado.removePacote(idPacote);
    }
    
        
    //remove a lista de componentes incompativeis da configuracao atual
    public void removeListaComponentes(List<String> listaIncompatibilidades){
        List<String> listaCompsOpcionaisConfAtual = this.estado.getComponentesOpcionais();
        
        listaIncompatibilidades.forEach((c) -> {
            listaCompsOpcionaisConfAtual.remove(c);
        });
        
        this.estado.setComponentesOpcionais(listaCompsOpcionaisConfAtual);
    }
    
    
    //adiciona a lista de componentes dependentes à configuracao atual
    public void adicionaListaDependencias(List<String> listaDependencias){
        List<String> listaCompsOpcionaisConfAtual = this.estado.getComponentesOpcionais();
        
        listaDependencias.forEach((c) -> {
            listaCompsOpcionaisConfAtual.add(c);
        });
        
        this.estado.setComponentesOpcionais(listaCompsOpcionaisConfAtual);
    }
    
    /*************************************************************
    *           VERIFICAÇÕES DE INCOMPATIBILIDADES E DEPENDÊNCIAS
    *
    ************************************************************/ 
    
    //retorna a lista dos incompativeis entre um componente e os que integram a configuracao atual
    public List<String> verificaIncompatibilidadesComponente(String idComponente){
        List<String> listaIncompativeis = this.componenteDAO.get(idComponente).getIncompativeis();
        List<String> listaCompsOpcionaisConfAtual = this.estado.getComponentesOpcionais();
        List<String> res = new ArrayList<>();
        
        listaIncompativeis.stream().filter((incompativel) -> (listaCompsOpcionaisConfAtual.contains(incompativel))).forEachOrdered((incompativel) -> {
            res.add(incompativel);
        });

        
        return res;
    }
    
    //retorna a lista dos incompativeis entre os componentes de um pacote e os que integram a configuracao atual
    public List<String> verificaIncompatibilidadesPacote(String idPacote){
        List<String> listaComponentesPacote = pacoteDAO.getComponentes(idPacote);
        Set<Componente> res = new HashSet<>();
        
        listaComponentesPacote.forEach((s) -> {
            res.addAll(getObjectsComponentes(verificaIncompatibilidadesComponente(s)));
        });
        
        return (res.stream().map(Componente::getId).collect(Collectors.toList()));
    }
    
    //retorna a lista dos componentes dependentes de um componente, removendo os que já integram a configuracao atual
    public List<String> verificaDependenciasComponente(String idComponente){
        List<String> compsOpcionaisConfAtual = this.estado.getComponentesOpcionais();
        List<String> componentesDependentes = this.componenteDAO.get(idComponente).getDependentes();
        List<String> res = new ArrayList<>();
        
        for(String dependente : componentesDependentes){
            if(!compsOpcionaisConfAtual.contains(dependente)) res.add(dependente);
            
        }
               
        return res;
    }
    
    //retorna a lista dos componentes dependentes dos componentes de um pacote, removendo os que já integram a configuracao atual
    public List<String> verificaDependenciasPacote(String idPacote){
        List<String> listaComponentesPacote = pacoteDAO.getComponentes(idPacote);
        Set<Componente> res = new HashSet<>();
        
        for(String s : listaComponentesPacote){
            res.addAll(getObjectsComponentes(verificaDependenciasComponente(s)));
        }    
        
        return (res.stream().map(Componente::getId).collect(Collectors.toList()));
    }
    
    //retorna a lista dos que dependem de um dado componente
    public List<String> verificaDependentesComponente(String idComponente){
        List<String> compsOpcionaisConfAtual = this.estado.getComponentesOpcionais();
        List<String> res = new ArrayList<>();
        
        for(String s : compsOpcionaisConfAtual){
            List<String> componentesDependentes = this.componenteDAO.get(s).getDependentes();
            if(componentesDependentes.contains(idComponente)) res.add(s);
        } 
        
        return res;
    }
    
    //retorna a lista dos que dependem dos componentes de um dado pacote
    public List<String> verificaDependentesPacote(String idPacote){
        List<String> listaComponentesPacote = pacoteDAO.getComponentes(idPacote);
        Set<Componente> res = new HashSet<>();
        
        for(String s : listaComponentesPacote){
            res.addAll(getObjectsComponentes(verificaDependentesComponente(s)));
        }    
        
        return (res.stream().map(Componente::getId).collect(Collectors.toList()));
    }

    
    /*************************************************************
    *           VERIFICAÇÕES EXTRA PARA CORRER TUDO BEM E NÃO HAVER REPETIÇÕES
    *
    ************************************************************/ 
    //Verifica se o componente que integra a configuraçao atual pertence é de um dos pacotes
    public boolean verificaSeComponentePertencePacote(String idComponente){
        List<String> pacotes = this.estado.getPacotes();
        List<String> res = new ArrayList<>();

        for(String p : pacotes) {
            List<String> ids = pacoteDAO.getComponentes(p);
            res.addAll(ids);
        }
        
        return res.contains(idComponente);
    }
    
    //Verifica se algum dos componentes do pacote que quero adicionar já esta nos componentes opcionais da conf atual. Se estiver retorna-me essa lista para depois serem removidos dos componentes opcionais
    public List<String> verificaSeComponenteIntegraConfAtual(String idPacote){
        List<String> compsPacote = pacoteDAO.getComponentes(idPacote);
        List<String> compsConfAtual = this.estado.getComponentesOpcionais();
        List<String> res = new ArrayList<>();
        
        compsPacote.stream().filter((c) -> (compsConfAtual.contains(c))).forEachOrdered((c) -> {
            res.add(c);
        });
            
        return res;
    }  
    
    //Verifica se o pacote em si ja integra a configuracao atual
    public boolean verificaSePacoteJaFoiAdicionado(String idPacote){
        List<String> pacotesConfAtual = this.estado.getPacotes();
        
        return pacotesConfAtual.contains(idPacote);
    }
    
    //Verifica se o componente já esta na configuração atual tanto em componenntes opcionais como pode estar num pacote
    public boolean verificaSeComponenteJaFoiAdicionado(String idComponente){
        List<String> pacotes = this.estado.getPacotes();
        List<String> componentesPacotes = new ArrayList<>();
        List<String> compsConfAtual = this.estado.getComponentesOpcionais();
        
        for(String p : pacotes){
            List<String> componentesP = pacoteDAO.getComponentes(p);
            componentesPacotes.addAll(componentesP);
        }
   
        return componentesPacotes.contains(idComponente) || compsConfAtual.contains(idComponente);
        
    }
    
    /*************************************************************
    *            PREÇOS
    *
    ************************************************************/ 
    //retorna preço do modelo do carro
    public double getPrecoModelo(){
        double soma = 0;
        String idModelo = this.estado.getIdModelo();
        
        soma = this.modeloDAO.getPreco(idModelo);
        
        return soma;
    }
    
    //retorna preço dos componentes base da configuracao atual
    public double getPrecoComponentesBase(){
        List<String> componentesBase = this.estado.getComponentesBase();
        double soma = 0;
        
        for(String idComponente : componentesBase){
            soma += this.componenteDAO.getPreco(idComponente);
        }
        
        return soma;
    }

    //retorna preço do modelo e dos componentes base da configuracao atual
    public double getPrecoModeloAndComponentesBase(){
        double soma = 0;
        
        soma = getPrecoComponentesBase() + getPrecoModelo();
        
        return soma;
    }

    //retorna preço dos componentes opcionais da configuracao atual
    public double getPrecoComponentesOpcionais(){
        List<String> componentesOpcionais = this.estado.getComponentesOpcionais();
        double soma = 0;
        
        soma = componentesOpcionais.stream().map((idComponente) -> this.componenteDAO.getPreco(idComponente)).reduce(soma, (accumulator, _item) -> accumulator + _item);        
        
        return soma;
    }

    //retorna preço dos pacotes da configuracao atual
    public double getPrecoPacotes(){
        List<String> pacotes = this.estado.getPacotes();
        double soma = 0;
        
        soma = pacotes.stream().map((idPacote) -> getPrecoPacote(idPacote)).reduce(soma, (accumulator, _item) -> accumulator + _item);
        return soma;
    }

    //retorna preço total da configuracao atual
    public double getPrecoConfiguracaoAtual(){
        double soma = 0;
        
        soma = getPrecoModeloAndComponentesBase() + getPrecoComponentesOpcionais() + getPrecoPacotes();
        
        return soma;
    }
    
    
    /*************************************************************
    *            OUTROS
    *
    ************************************************************/ 
    
    public int getNextIdEncomenda(){
        return this.encomendaDAO.size() + 1;       
    }
    
    //Verifica se alguma combinação de componentes opcionais da configuração atual dá origem a um pacote
    public List<String> verificaDesconto(String idModelo){
        List<String> opcionais = this.estado.getComponentesOpcionais();
        List<String> pacotesDisponiveis = this.modeloDAO.get(idModelo).getPacotesDisponiveis();
        List<String> res = new ArrayList<>();
                
        for(String idPacote : pacotesDisponiveis){
            List<String> componentesPacote = this.pacoteDAO.getComponentes(idPacote);
            if(opcionais.containsAll(componentesPacote)) {
                res.add(idPacote);
                opcionais.removeAll(componentesPacote);
                this.estado.adicionaPacote(idPacote);
            }
        }
        
        setComponentesOpcionaisConfiguracao(opcionais);
        
        return res;
    }
    
    
    
    public void configuracaoOtima(String idModelo, double orcamento){
        setComponentesOpcionaisConfiguracao(new ArrayList<>());
        List<String> todosDisponiveis = this.modeloDAO.get(idModelo).getAllOptionalComponentes();
        Set<String> componentesOrdenadosPreco = new TreeSet<>();
        componentesOrdenadosPreco.addAll(todosDisponiveis);
        double precoAtual = 0;
        
        for(String idComponente : componentesOrdenadosPreco){
            precoAtual += this.componenteDAO.getPreco(idComponente) + this.componenteDAO.get(idComponente).getPrecoDependentes();
            List<String> dependentes = this.componenteDAO.get(idComponente).getDependentes();
            if(precoAtual < orcamento){
                List<String> incompativeis = verificaIncompatibilidadesComponente(idComponente);
                boolean exist = verificaSeComponenteJaFoiAdicionado(idComponente);

                if(incompativeis.isEmpty() && exist == false){
                    adicionaComponenteOpcionalConfiguracao(idComponente);
                    for(String dependente : dependentes){
                        boolean existe = verificaSeComponenteJaFoiAdicionado(dependente);
                        if(existe == false) adicionaComponenteOpcionalConfiguracao(dependente);
                    }                   
                }
            }
            else{break;}           
        }
                 
    }

}
