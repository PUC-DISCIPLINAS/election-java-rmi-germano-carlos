import Utils.Encript;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Scanner;
import static Utils.DateHelper.valida;

public class EleitorClient {
    public static void main(String[] args) {
        String servidor = "rmi://localhost:1900/";
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


            boolean computado = false;
            //noinspection InfiniteLoopStatement
            while(!computado)
            {
                Date start = new Date();
                //Realizar chamada para inserir voto!
                try {
                    computado = access.votar(nomeEleitor,codigoCandidato);
                } catch (RemoteException e) {
                    computado = valida(start);
                } catch (Exception e) {
                    computado = valida(start);
                }

            }

            System.out.println(access.testMethod());


        } catch (NotBoundException e) {
            System.out.println("Objeto remoto " + nome + " n�o est� dispon�vel.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
