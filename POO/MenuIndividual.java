

/**
 * Write a description of class MenuIndividual here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.TreeSet;


public class MenuIndividual extends Menu {
    private Scanner input = new Scanner(System.in);
    private GestaoContribuintes contribuintes;
       
    public MenuIndividual(GestaoContribuintes contribuintes){
        this.contribuintes=contribuintes;
    }
    
    public void menuIndividual(Individual i){
        int selection;
        Set <Factura> facturas = new TreeSet<>(new ComparatorFactura());
        facturas = contribuintes.criaFactura(i);
        i.setFacturas(facturas);
        List<Actividade> actividades = i.geraActividades();
        i.setActividades(actividades);
        
        do{ 
            System.out.println("*********************");
            System.out.println(
                "Seleccione uma opçao: \n" +
                "  1) Verificar despesas \n" +
                "  2) Validar Facturas\n" +
                "  3) Corrigir Actividade\n" +
                "  4) Verificar deduçoes\n" +
                "  5) Sair\n" 
            );
         
            selection = input.nextInt();
            input.nextLine();
            switch (selection) {
                case 1:
                    this.listaDespesas(i);
                    break;
                case 2:
                    this.validaFacturas(i, contribuintes);
                    break;
                case 3:
                    this.corrigeActividade(i, contribuintes);
                    break;
                case 4:
                    this.listaDeducoes(i);
                    break;                    
                case 5:
                    break;
                default:
                    System.out.println("Invalid selection.");
                    break;
            }
        
        }while(selection!=5);
    }
    
    public void listaDespesas(Individual i){
        System.out.println("Facturas: \n");
        System.out.println(i.getFacturas().toString());
    }
    
    public void validaFacturas(Individual i, GestaoContribuintes c){
        List<Actividade> actividades = i.getActividades();
        Set<Factura> facturas = i.getFacturas();
        
        for(Factura f : facturas){
            if(f.getActividade() instanceof Indefinida){
                try{
                    Empresa e = (Empresa)c.getContribuinte(f.getNifEmitente());
                    Actividade a = this.escolheActividade(e, f);
                    f.setActividade(a);
                    f.setValidada(true);
                    actividades = i.addFacturaAct(f);
                    i.setActividades(actividades);
                }
                catch (ContribuinteInexistenteException ex) {          
                    System.out.println("Contribuinte " + ex.getMessage() + " não existe!!!");
                }
            }         
        }
    }
    
    public Actividade escolheActividade(Empresa e, Factura f){
        List<Actividade> actividades = e.getActividades();
        int i = 1;
        System.out.println("Escolha uma actividade associada à factura " + f.getNumero()+"\n");
        for(Actividade a : actividades){
            System.out.print(i + " - ");
            System.out.println(a.getDescricao());
            i++;
        }
        Actividade a;
        int escolha = input.nextInt();
        if(escolha > 0 && escolha <= actividades.size()){
            a = actividades.get(escolha-1);
            return a;}
        else{
            a = new Indefinida();
            return a;
        }

    }
    
    public void corrigeActividade(Individual i, GestaoContribuintes c){
        Set<Factura> facturas = i.getFacturasActs(c);
        System.out.println("Insira o número da factura que quer corrigir:");
        String numero = input.nextLine();
        
        try{
            Factura f = i.getFactura(numero);
            Actividade anterior = f.getActividade();
            List<Actividade> registo = f.getRegisto();
            List<Actividade> actividades = i.getActividades();
            actividades = i.removeFacturaAct(f);
            registo.add(anterior);
            f.setRegisto(registo);
            try{
                Actividade actividade = escolheActividade((Empresa)c.getContribuinte(f.getNifEmitente()), f);
                f.setActividade(actividade);
                i.setActividades(i.addFacturaAct(f));
                this.validaFacturas(i,c);
            }
            catch (ContribuinteInexistenteException ex) {          
                System.out.println("Contribuinte " + ex.getMessage() + " não existe!!!");
            }
        }
        catch(FacturaInexistenteException ex){
            System.out.println("Factura " + ex.getMessage() + " não existe!!!");
        }

    }
    
    public void listaDeducoes(Individual i){
        List<Actividade> actividades = i.getActividades();
        double deducao;
        double deducao_total = 0.0;
        double beneficio = 1;
        if(i instanceof FamiliaNumerosa){ 
            FamiliaNumerosa f = (FamiliaNumerosa) i;
            beneficio = f.reducaoImposto();
        }
        for(Actividade a: actividades){
            deducao = a.getAcumulado();
            
            if(deducao>0.0)System.out.println(a.getDescricao() + " : " + deducao*beneficio + "euros"); 
            deducao_total += deducao;
        }
        System.out.println("Valor total de deduçoes: " + deducao_total + " euros\n"); 
    }
}
