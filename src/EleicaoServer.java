import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.TreeMap;

public class EleicaoServer {
    TreeMap<String, Candidato> map = new TreeMap<String, Candidato>();

    public EleicaoServer() {
        System.setProperty("java.rmi.server.hostname", "localhost");
        System.setProperty("java.rmi.server.useLocalHostname","true");
        //System.setProperty("java.security.policy","rmi.policy");
        System.setSecurityManager(null);

        try {
            Eleicao election = new EleicaoServant();
            election.readCSV("C:\\Users\\Carlos Germano\\Desktop\\senadores.csv");

            LocateRegistry.createRegistry(1900);
            Naming.rebind("rmi://localhost:1900/EleicaoServer",election);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        new EleicaoServer();
        System.out.println("Servidor de Eleicao em execu��o.");
    }

}
