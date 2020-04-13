

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * The test class TestaContribuintes.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class TestaContribuintes{
    GestaoContribuintes contribuintes= new GestaoContribuintes();
    public static void main(String[] args){
       
    String num1 = "222";
    String num2 = "333";
    String num3 = "444";
    List<String> numsfiscais = new ArrayList<String>();
    numsfiscais.add(num1);
    numsfiscais.add(num2);
    numsfiscais.add(num3);
    
    
    Actividade cod1 = new Saude();
    Actividade cod2 = new Educacao();
    Actividade cod3 = new Transportes();
    
    List<Actividade> codsActs = new ArrayList<Actividade>();
    codsActs.add(cod1);
    codsActs.add(cod2);
    codsActs.add(cod3);
    
    
    Actividade act1 = new Saude();
    Factura f1 = new Factura("123","167",LocalDateTime.now(), "0001","fatura do joao", act1, 0.2,false,null);
    //Factura f2 = new Factura("124","167","Saude", LocalDateTime.of(1960,3,4,3,2,1), "0001","fatura do joao", act1, 0.2, true);
    Factura f2 = new Factura("124","167",LocalDateTime.now(), "0001","fatura do joao", act1, 0.2, false,null);
    TreeSet<Factura> facturas = new TreeSet<>(new ComparatorFactura());
    facturas.add(f1);
    facturas.add(f2);
    
    
    
    Contribuinte c1 = new Individual("0001","joao@email.com", "João Ferreira", "Braga", "1234", 5, numsfiscais, 2.5, codsActs, facturas);
    Contribuinte c2 = new Individual("0002","joao@email.com", "Pedro Fernandes", "Braga", "1234", 1, numsfiscais, 2.5, codsActs, facturas);
    Contribuinte c3 = new Individual("0003","joao@email.com", "Joana Cruz", "Braga", "1234", 2, numsfiscais, 2.5, codsActs, facturas);
    Contribuinte c4 = new Individual("0004","joao@email.com", "Etienne Costa", "Braga", "1234", 3, numsfiscais, 2.5, codsActs, facturas);
    Contribuinte c5 = new Individual("0005","joao@email.com", "Samuel Ferreira", "Braga", "1234", 3, numsfiscais, 2.5, codsActs, facturas);
    Contribuinte c6 = new Individual("0006","joao@email.com", "Marisa Morais", "Lisboa", "1234", 3, numsfiscais, 2.5, codsActs, facturas);
    Contribuinte c7 = new Individual("0007","joao@email.com", "Anabela Rodrigues", "Braga", "1234", 3, numsfiscais, 2.5, codsActs, facturas);
    Contribuinte c8 = new Individual("0008","joao@email.com", "Ana Cruz", "Braga", "1234", 3, numsfiscais, 2.5, codsActs, facturas); 
    Contribuinte c9 = new Individual("0009","joao@email.com", "Cátia Cruz", "Braga", "Porto", 3, numsfiscais, 2.5, codsActs, facturas);
    Contribuinte c10 = new Individual("00010","joao@email.com", "Filipe Machado", "Braga", "1234", 3, numsfiscais, 2.5, codsActs, facturas);
    Contribuinte c11 = new Individual("00011","joao@email.com", "João Marta da Cruz", "Lisboa", "1234", 3, numsfiscais, 2.5, codsActs, facturas);
    Contribuinte c12 = new Individual("00012","joao@email.com", "Rita Ferreira", "Braga", "1234", 3, numsfiscais, 2.5, codsActs, facturas);
    Contribuinte c13 = new Individual("00013","joao@email.com", "Flávia Silva", "Porto", "1234", 3, numsfiscais, 2.5, codsActs, facturas);
    Contribuinte c14 = new Individual("00014","joao@email.com", "Pedro Marinho", "Braga", "1234", 3, numsfiscais, 2.5, codsActs, facturas);
    Contribuinte c15 = new Individual("00015","joao@email.com", "António Gomes", "Braga", "1234", 3, numsfiscais, 2.5, codsActs, facturas);
    Contribuinte c16 = new Empresa("00016","joao@email.com", "Farmácia Pipa", "Braga", "1234",codsActs,2.5,facturas,"Braga");
    Contribuinte c17= new Empresa ("00017","joao@email.com","Continente","Porto","1234",codsActs,2.5,facturas,"Porto");
    Contribuinte c18 = new Empresa ("00018","joao@email.com","Zara","Lisboa","1234",codsActs,2.5,facturas,"Lisboa");
    Contribuinte c19= new Empresa ("00019","joao@email.com","Magno","Lisboa","1234",codsActs,2.5,facturas,"Lisboa");
    Contribuinte c20= new Empresa ("00020","sonae@email.com","Sonae","Lisboa","1234",codsActs,4.5,facturas,"Lisboa");
    
    
    //System.out.println(c1.toString());
    // public Empresa(String nif,String email,String nome,String morada,String password,
      //          List <Actividade> actividades, double factor,Set<Factura> f,String concelho){

    //System.out.println(c5.toString());
    GestaoContribuintes conts = new GestaoContribuintes();
    //conts.registaContribuinte(c1);
    //conts.registaContribuinte(c2);
    conts.registaContribuinte(c3);
    conts.registaContribuinte(c4);
    conts.registaContribuinte(c5);
    
    
    
    System.out.println(conts.toString());

                
    }
}
