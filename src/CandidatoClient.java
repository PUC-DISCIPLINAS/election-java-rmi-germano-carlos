import Utils.Encript;

import javax.xml.bind.DatatypeConverter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;
import java.util.Scanner;

public class CandidatoClient {
    public static void main(String[] args) {
        String servidor = "rmi://localhost/";
        String nome = "EleicaoService";
        try {
            // lookup method to find reference of remote object
            Eleicao access = (Eleicao) Naming.lookup("rmi://localhost:1900/EleicaoServer");
            System.out.println("Bem vindo a URNA Eletrônica");

            // Coleta informações para realizar o processo de votação
            Scanner s = new Scanner(System.in);
            System.out.println("Insira seu nome completo por gentileza");
            String nomeEleitor = Encript.StringToMD5(s.nextLine(),"upper");

            System.out.println("Insira o candidato na qual deseja votar");
            String codigoCandidato = s.nextLine();

            System.out.println("Obrigado... Estamos computando o seu voto");
            System.out.println("Aguarde um pouco");

            //noinspection InfiniteLoopStatement
            while(true)
            {
                //Realizar chamada para inserir voto!
            }





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
