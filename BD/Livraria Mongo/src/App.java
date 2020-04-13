import Data.*;

class App {
    public static void main(String[] args){
        Dados d = new Dados();
        d.init();
        d.getData();
        d.export2mongo();
    }
}
