
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
import java.util.Iterator;

public class MenuEmpresa extends Menu {
    private Scanner input = new Scanner(System.in);
    private GestaoContribuintes contribuintes;
    
    public MenuEmpresa(GestaoContribuintes contribuintes){
        this.contribuintes=contribuintes;
    }
    
    
    public void menuEmpresa(Empresa e){
        int selection;
        do{
            System.out.println("*********************");
            System.out.println(
                "Seleccione uma opçao: \n" +
                "  1) Emitir Factura(s)\n" +
                "  2) Obter listagem de facturas \n" +
                "  3) Obter listagem de facturas por contribuinte num intervalo\n" +
                "  4) Obter listagem de facturas por contribuinte ordenadas por valor decrescente\n" +
                "  5) Total facturado\n" + 
                "  6) Sair\n" 
            );
         
            selection = input.nextInt();
            input.nextLine();
            
            switch (selection) {
                case 1:
                    emiteFactura(e);
                    break;
                case 2:
                    listaFacturas(e);
                    break;
                case 3:
                    listaFacturasIntervalo(e);
                    break;
                case 4:
                    listaFacturasValor(e);
                    break;    
                case 5:
                    totalFacturado(e);
                    break;                
                case 6:
                    break;
                default:
                    System.out.println("Invalid selection.");
                    break;
            }
            
        }while(selection!=6);
    }
    
    public void emiteFactura(Empresa e){
        System.out.println("Numero de Factura: ");
        String numero = input.nextLine();
        System.out.println("NIF do cliente: ");
        String nifcliente = input.nextLine();
        System.out.println("Descricao: ");
        String descricao = input.nextLine();
        System.out.println("Valor: ");
        Double valor = input.nextDouble();
        input.nextLine();
       
        boolean validada = false;
        Actividade actividade;

        
        if(e.getActividades().size() == 1){ 
            validada = true;
            actividade = e.getActividades().get(0);
        }
        else
            actividade = new Indefinida();
            
        List<Actividade> list = new ArrayList<>();
        Factura factura = new Factura(numero, e.getNif(), LocalDateTime.now(), nifcliente, descricao, actividade, valor, validada, list);
        Set<Factura> facturas = e.getFacturas();
        facturas.add(factura);
        e.setFacturas(facturas);
    }
    
    public void listaFacturasIntervalo(Empresa e){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println("Insira a data inicial(dd-mm-aaaa): ");
        String data1 = input.nextLine();         
        LocalDate date1 = LocalDate.parse(data1, formatter);
        System.out.println("Insira a data final(dd-mm-aaaa): ");
        String data2 = input.nextLine();
        LocalDate date2 = LocalDate.parse(data2, formatter);
        Set<Factura> facturas = e.listFacturasContribuinteDate(date1, date2);
        for(Factura f : facturas){
            System.out.println(f.toString());
        }
      
     
    }
                     
    public void listaFacturas(Empresa e){
        e.juntaOrdenacao("Por data", new ComparatorFactura());
        Iterator<Factura> it = e.ordenarFacturas("Por data");
        while (it.hasNext())
          System.out.println(it.next());
    }
    
    public void listaFacturasValor(Empresa e){
        e.juntaOrdenacao("Por valor", new ComparatorFacturaValor());
        Iterator<Factura> it = e.ordenarFacturas("Por valor");
        while (it.hasNext())
          System.out.println(it.next());
    }

    public void totalFacturado(Empresa e){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println("Insira a data inicial(dd-mm-aaaa): ");
        String data1 = input.nextLine();         
        LocalDate date1 = LocalDate.parse(data1, formatter);
        System.out.println("Insira a data final(dd-mm-aaaa): ");
        String data2 = input.nextLine();
        LocalDate date2 = LocalDate.parse(data2, formatter);
        System.out.println("A empresa " + e.getNome() + " facturou: " + e.totalFacturadoDate(date1, date2) + " euros\n");
        
    }    
}

