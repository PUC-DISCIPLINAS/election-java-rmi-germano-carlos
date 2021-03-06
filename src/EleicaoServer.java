import java.rmi.Naming;
import java.util.TreeMap;

public class EleicaoServer {
    TreeMap<String, Candidato> map = new TreeMap<String, Candidato>();

    public EleicaoServer() {
        try {
            Eleicao e = new EleicaoServant();
            e.readCSV("C:\\Users\\Carlos Germano\\Desktop\\senadores.csv");
            //Naming.rebind("rmi://localhost/EleicaoService", e);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        new EleicaoServer();
        System.out.println("Servidor de Eleicao em execu��o.");
    }

}
