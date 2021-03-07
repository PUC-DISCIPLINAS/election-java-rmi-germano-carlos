import Utils.Encript;
import sun.nio.ch.SelectorImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.TreeMap;

public class EleicaoServer {
    TreeMap<String, Candidato> map = new TreeMap<String, Candidato>();

    public EleicaoServer() {
        System.setProperty("java.rmi.server.hostname", "localhost");
        System.setProperty("java.rmi.server.useLocalHostname","true");
        System.setProperty("java.security.policy","rmi.policy");
        System.setSecurityManager(null);

        try {
            Eleicao election = new EleicaoServant();

            LocateRegistry.createRegistry(1900);
            Naming.rebind("rmi://localhost:1900/EleicaoServer",election);
            System.err.println("Servidor de Eleicao está sendo executado");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        new EleicaoServer();
    }

}
