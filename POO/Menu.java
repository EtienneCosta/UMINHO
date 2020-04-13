import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import static java.util.Arrays.asList;
import java.io.*;

public class Menu {
    private Scanner input = new Scanner(System.in);
    private GestaoContribuintes contribuintes = new GestaoContribuintes();

    public GestaoContribuintes getContribuintes(){
         return this.contribuintes;
    }


    public void inicial() {
        do{
            System.out.println("*********************");
            System.out.println(
                "Seleccione uma opçao: \n" +
                "  1) Login\n" +
                "  2) Registo\n" +
                "  3) Carrega Estado\n" +
                "  4) Salva Estado\n" +
                "  5) Sair\n" 
            );

            int selection = input.nextInt();
            input.nextLine();
            switch (selection) {
                case 1:
                    this.login();
                    break;
                case 2:
                    this.registarContribuinte();
                    break;
                case 3:
                    this.contribuintes = carrega();
                    break;
                case 4:
                    this.guarda(contribuintes);
                    break;
                case 5:
                    this.exit();
                    break;
                default:
                    System.out.println("Invalid selection.");
                    break;
            }
        }while(true);
    }

    private void exit() {
        System.out.println("A Sair..");
        System.exit(1);
    }


    private void login() {


        System.out.println("Numero de contribuinte: ");
        String nif = input.nextLine();
        System.out.println("Password: ");
        String password = input.nextLine();
        if(contribuintes.login(nif,password)){
            try{
                Contribuinte c = contribuintes.getContribuinte(nif);
                if (c instanceof Individual){
                    
                    MenuIndividual mi = new MenuIndividual(this.contribuintes);
                    c.setFacturas(contribuintes.criaFactura(c));
                    Individual i = (Individual) c;
                    i.setActividades(i.geraActividades());
                    mi.menuIndividual(i);
                }
                if (c instanceof Empresa){
                    MenuEmpresa me = new MenuEmpresa(this.contribuintes);
                    me.menuEmpresa((Empresa) c);
                }
            }
            catch (ContribuinteInexistenteException ex) {
               System.out.println("Contribuinte " + ex.getMessage() + " não existe!!!");
            }
        }
        else if(nif.equals("admin") && password.equals("admin")){
                MenuAdministrador ma = new MenuAdministrador(this.contribuintes);
                ma.menuAdministrador();
        }
        else System.out.println("Failed\n");

    }

    private void registarContribuinte() {
        System.out.println("*********************");
        System.out.println(
            "Seleccione uma opçao: \n" +
            "  1) Individual\n" +
            "  2) Empresa\n" +
            "  3) Voltar Atras\n");

        int selection = input.nextInt();
        input.nextLine();
        switch (selection) {
            case 1:
                this.registarIndividual();
                break;
            case 2:
                this.registarEmpresa();
                break;
            case 3:
                this.inicial();
                break;
        }
    }

    private void registarIndividual() {
        System.out.println("Numero de contribuinte: ");
        String nif = input.nextLine();
        System.out.println("E-mail: ");
        String email = input.nextLine();
        System.out.println("Nome: ");
        String nome = input.nextLine();
        System.out.println("Morada: ");
        String morada = input.nextLine();
        System.out.println("Numero de elementos do agregado familiar: ");
        int num_agregado = input.nextInt();
        input.nextLine();
        int num_dependentes;
        if(num_agregado > 1){
            System.out.println("Desses " + num_agregado +" elementos, quantos sao dependentes?");
            num_dependentes = input.nextInt();
            input.nextLine();
        }
        else num_dependentes = 0;


        List<String> nums = new ArrayList<>();

        for(int i  = 1; i < num_agregado; i++){
            System.out.println("Insira o NIF do " + i +"º elemento: ");
            nums.add(input.nextLine());
        }

        List<Actividade> actividades = new ArrayList<>();
        System.out.println(
            "Escalao: \n" +
            "  1) Contribuinte regular\n" +
            "  2) Contribuinte Isento\n" +
            "  3) Pensionista\n");
        int selection = input.nextInt();
        double coeficiente = 0;
        input.nextLine();
        switch (selection) {
            case 1:
                coeficiente = 1;
                break;
            case 2:
                coeficiente = 1.05;
                break;
            case 3:
                coeficiente = 1.10;
                break;
        }

        //String password = Long.toHexString(Double.doubleToLongBits(Math.random()));
        String password = "1";

        Set<Factura> facturas = new TreeSet<>(new ComparatorFactura());
        Contribuinte c;
        
        if(num_dependentes>4) c = new FamiliaNumerosa(nif, email, nome, morada, password, num_agregado,
                                                    nums, coeficiente, actividades, facturas, num_dependentes);
        else c = new Individual(nif, email, nome, morada, password, num_dependentes,
                         nums, coeficiente, actividades, facturas);

        Individual i = (Individual) c;


        try{
            contribuintes.registaContribuinte(c);
            System.out.println("Registado com sucesso...");
            System.out.println("A sua password e: "+password+"\n");
            System.out.println(i.toString());
        }
        catch(ContribuinteExistenteException ex){
            System.out.println("Contribuinte " + ex.getMessage() + " já existe!!!");
        }
    }

    private void registarEmpresa() {
        System.out.println("Numero de contribuinte: ");
        String nif = input.nextLine();
        System.out.println("E-mail: ");
        String email = input.nextLine();
        System.out.println("Nome: ");
        String nome = input.nextLine();
        System.out.println("Morada: ");
        String morada = input.nextLine();
        System.out.println("Indique o seu concelho: ");
        String concelho = input.nextLine();

        List<Actividade> actividades = new ArrayList<>();


        System.out.println(
            "Adicionar Actividade(s): \n" +
            "  1) Geral\n" +
            "  2) Saude\n" +
            "  3) Educacao\n" +
            "  4) Restauraçao\n" +
            "  5) Transportes\n" +
            "  6) Habitacao\n" +
            "  --------------\n" +
            "  7) Concluir\n");

        Actividade a;
        boolean concluido = false;
        while(concluido == false ){
            int selection = input.nextInt();
            input.nextLine();
            switch (selection) {
                case 1:
                    a = new Geral();
                    actividades.add(a);
                    System.out.println("Actividade 'Geral' Adicionada!\n");
                    break;
                case 2:
                    a = new Saude();
                    actividades.add(a);
                    System.out.println("Actividade 'Saude' Adicionada!\n");
                    break;
                case 3:
                    a = new Educacao();
                    actividades.add(a);
                    System.out.println("Actividade 'Educacao' Adicionada!\n");
                    break;
                case 4:
                    a = new Restauracao();
                    actividades.add(a);
                    System.out.println("Actividade 'Restauracao' Adicionada!\n");
                    break;
                case 5:
                    a = new Transportes();
                    actividades.add(a);
                    System.out.println("Actividade 'Transportes' Adicionada!\n");
                    break;
                case 6:
                    a = new Habitacao();
                    actividades.add(a);
                    System.out.println("Actividade '"+a.getDescricao()+"' Adicionada!\n");
                    break;
                case 7:

                    if(actividades.size() == 0){
                        System.out.println("Tem que inserir pelo menos uma actividade\n");
                        break;}
                    else{
                        concluido = true;
                        System.out.println("Processo concluido!\n");
                        break;
                    }
                }
            }
        double factor = 1;
        List<String> concelhos = asList("Odemira", "Aljustrel", "Almodovar","Alvito","Barrancos","Beja","Castro Verde","Cuba","Ferreira do Alentejo", "Mértola","Moura",
                                "Ourique","Serpa","Vidigueira","Carrazeda de Ansiaes","Freixo de Espada à Cinta","Torre de Moncorvo","Alfândega da Fé","Bragança",
                                "Macedo de Cavaleiros","Miranda do Douro","Mirandela","Mogadouro", "Vila Flor","Vimioso","Vinhais","Belmonte","Covilhã","Fundão",
                                "Castelo Branco","Idanha-a-Nova","Oleiros","Penamacor","Proença-a-Nova","Vila Velha de Ródão","Sertã","Vila de Rei","Aguiar da Beira",
                                "Almeida","Celorico da Beira","Figueira de Castelo Rodrigo", "Fornos de Algodres","Gouveia","Guarda","Manteigas","Mêda","Pinhel",
                                "Sabugal","Seia","Trancoso","Alter do Chão","Arronches","Avis","Campo Maior","Castelo de Vide","Crato","Elvas","Fronteira","Gavião",
                                "Marvão","Monforte","Nisa","Ponte de Sor","Portalegre","Sousel");

        Set<Factura> facturas = new TreeSet<>(new ComparatorFactura());
        String password = "1";
        //String password = Long.toHexString(Double.doubleToLongBits(Math.random()));
        Contribuinte e;

        if(concelhos.contains(concelho)){
          e = new EmpresaInterior(nif, email, nome, morada, password, actividades, factor, facturas);
        }
        else e = new Empresa(nif, email, nome, morada, password, actividades, factor, facturas);


        try{
            contribuintes.registaContribuinte(e);
            System.out.println("Registado com sucesso...");
            System.out.println("A sua password e: "+password+"\n");
            System.out.println(e.toString());
        }
        catch(ContribuinteExistenteException ex){
            System.out.println("Contribuinte " + ex.getMessage() + " já existe!!!");
        }
        
    }


    public void guarda(GestaoContribuintes cont){
        try{
            cont.guardaEstado("contribuintes.obj");
        }

        catch(FileNotFoundException e) {System.out.println("Ficheiro não encontrado");}
        catch(IOException e) {System.out.println("Erro a aceder ficheiro");}
    }

    public GestaoContribuintes carrega(){
        GestaoContribuintes res = null;
        try{
             res = GestaoContribuintes.carregaEstado("contribuintes.obj");
        }
        catch(FileNotFoundException e) {System.out.println("Ficheiro não encontrado");}
        catch(IOException e) {System.out.println("Erro a aceder ficheiro");}
        catch(ClassNotFoundException e) {System.out.println("Classe não encontrada");}
        finally{
            return res;
        }
    }
}
