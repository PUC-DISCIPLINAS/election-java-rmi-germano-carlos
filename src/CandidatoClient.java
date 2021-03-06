import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CandidatoClient {
    public static void main(String[] args) {
        String servidor = "rmi://localhost/";
        String nome = "EleicaoService";
        try {
            // lookup method to find reference of remote object
            Eleicao access = (Eleicao) Naming.lookup("rmi://localhost:1900/EleicaoServer");

            System.out.println("Teste " + access.testMethod());

        } catch (RemoteException e) {
            System.out.println("Erro na invoca��o remota.");
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.out.println("Objeto remoto " + nome + " n�o est� dispon�vel.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
