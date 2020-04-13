
/**
 * Write a description of class MenuEmpresa here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Map;
import java.io.*;

public class MenuAdministrador extends Menu
{
    private Scanner input = new Scanner(System.in);
    private GestaoContribuintes contribuintes;
    
    public MenuAdministrador(GestaoContribuintes contribuintes){
        this.contribuintes = contribuintes;
    }
    
    
    public void menuAdministrador(){
        int selection;
        do{
            System.out.println("*********************");
            System.out.println(
                "Seleccione uma opçao: \n" +
                "  1) Listagem dos dados e das facturas de um contribuinte\n" +
                "  2) Obter os dez contribuintes que mais gastam em todo o sistema\n" +
                "  3) Obter as X empresas que mais facturam em todo o sistema e o montante de deduções fiscais das despesas dessas empresas\n" +
                "  4) Gravar Estado\n" +
                "  5) Carregar estado\n" +
                "  6) Sair\n" 
            );
         
            selection = input.nextInt();
            input.nextLine();
            
            switch (selection) {
                case 1:
                    listaContribuinte(contribuintes);
                    break;
                case 2:
                    maisGastamSistema(contribuintes);
                    break;
                case 3:
                    maisFacturamSistema(contribuintes);
                    break;
                case 4:
                    guarda(contribuintes);
                    break;
                case 5:
                    this.contribuintes = carrega();
                    System.out.println(this.contribuintes.toString());
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Invalid selection.");
                    break;
            }
            
        }while(selection!=6);
    }
    
    public void listaContribuinte(GestaoContribuintes cont){
        System.out.println("Insira um número de contribuinte: \n");
        String numero = input.nextLine();
        try{
            Contribuinte c = cont.getContribuinte(numero);
            
            System.out.println("Dados de um contribuinte: \n");
            System.out.println(c.toString());
        }
        catch (ContribuinteInexistenteException ex) {          
               System.out.println("Contribuinte " + ex.getMessage() + " não existe!");
        }
    }
    
    public void maisGastamSistema(GestaoContribuintes cont){
        Set<Contribuinte> individuais = cont.maisGastam();
        int i = 1;
        
        System.out.println(individuais.size());
        for(Contribuinte c : individuais){
            System.out.println("Contribuinte\t" + i);
            System.out.println("***********************************");
            System.out.println(c.toString());
            i++;
        }
        
    }
    
    public void maisFacturamSistema(GestaoContribuintes cont){
        System.out.println("Insira o número de empresas: ");
        int n = input.nextInt();
        input.nextLine();
        int i = 0;
        
        Set<Contribuinte> empresas = cont.maisFacturam(n);
        List<Double> resultados = cont.calculoDeducaoMaisFacturam(n);
        

        for(Contribuinte c : empresas){
            System.out.println("Empresa\t" + (i+1));
            System.out.println("***********************************");
            System.out.print(c.toString());
            System.out.println("Total valor deduzido por esta empresa: " + resultados.get(i) + "\n");
            i++;
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