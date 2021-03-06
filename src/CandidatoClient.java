import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class CandidatoClient {
    public static void main(String[] args) {
        String servidor = "rmi://localhost/";
        String nome = "EleicaoService";
        try {
            Eleicao e = (Eleicao) Naming.lookup(servidor + nome);
            System.out.println("Objeto remoto "+ nome + " encontrado no servidor.");


        } catch (MalformedURLException e) {
            System.out.println("URL " + servidor + nome + " mal formatada.");
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
