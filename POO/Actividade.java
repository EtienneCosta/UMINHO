import java.io.Serializable;

public abstract class Actividade implements Serializable{
    /**Variáveis de instância*/
    private String descricao;//Descrição da Actividade.
    private double maximo;//Valor máximo que pode ser deduzido.
    private double coeficiente;//Valor usado no cálculo dos montantes de dedução fiscal.
    private double acumulado;//Valor acumulado de deduçoes na actividade

    /**Construtor por omissão*/
    public Actividade(){
        this.maximo       =   0;
        this.descricao    =  "";
        this.coeficiente  = 0.0;
        this.acumulado = 0;
    }

    /**Construtor parametrizado*/
    public Actividade(double maximo, String descricao, double coeficiente){
        this.maximo      = maximo;
        this.descricao   = descricao;
        this.coeficiente = coeficiente;
        this.acumulado = 0;
    }

    /**Construtor de Cópia*/
    public Actividade(Actividade a){
        this.maximo        = a.getMaximo();
        this.descricao     = a.getDescricao();
        this.coeficiente   = a.getCoeficiente();
        this.acumulado     = a.getAcumulado();
    }

    /**Getters*/
    public double getAcumulado(){
        return this.acumulado;
    }

    public double getMaximo(){
        return this.maximo;
    }

    public String getDescricao(){
        return this.descricao;
    }

    public double getCoeficiente(){
        return this.coeficiente;
    }

    /** Setters */
    public void setAcumulado(double acumulado){
        this.acumulado = acumulado;
    }

    public void setMaximo(double maximo){
        this.maximo = maximo;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public void setCoeficiente(double coeficiente){
        this.coeficiente = coeficiente;
    }

    /**Método Clone*/
    public abstract Actividade clone();

    /**Método Equals*/
    public boolean equals(Object o){
        if(o == this) return true;
        else
          if(o == null || this.getClass() != o.getClass()) return false;
        Actividade a = (Actividade) o;
        return this.maximo == a.getMaximo() &&
               this.descricao.equals(a.getDescricao()) &&
               this.coeficiente == a.getCoeficiente();
            }

    /** Método toString */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.descricao + "\n");
        sb.append("Valor Acumulado: ");
        sb.append(this.acumulado + "\n");
        sb.append("Máximo: ");
        sb.append(this.maximo + "\n");
        sb.append("Coeficiente: ");
        sb.append(this.coeficiente);
        return sb.toString();
    }

    public void deduzFactura(double valor){
        this.acumulado += this.coeficiente * valor;
        if (this.acumulado > this.maximo) this.acumulado = this.maximo;
    }

    public double deducaoMaxima(double valor){
        double deducao = this.coeficiente * valor;
        if (deducao > this.maximo) deducao = this.maximo;
        return deducao;
    }
}
