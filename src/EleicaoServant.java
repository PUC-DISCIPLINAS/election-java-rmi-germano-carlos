import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;

public class EleicaoServant extends java.rmi.server.UnicastRemoteObject implements Eleicao {

    TreeMap<String, Candidato> map = new TreeMap<String, Candidato>();

    public EleicaoServant() throws java.rmi.RemoteException {
        super();
    }

    @Override
    public void votar(String eleitor, String candidato) throws RemoteException {

    }

    @Override
    public void adicionarCandidato(String key, Candidato value) throws RemoteException {
        this.map.put(key, value);
    }

    @Override
    public void readCSV(String pathToCsv) throws RemoteException {

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(";");

                if("NR_CANDIDATO".equals(data[0]) && "NM_URNA_CANDIDATO".equals(data[1]) && "SG_PARTIDO".equals(data[2]))
                    continue;

                // Gera o hash MD5 baseado no nome do eleitor
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(data[0].getBytes());
                byte[] digest = md.digest();
                String md5String = DatatypeConverter.printHexBinary(digest).toUpperCase();

                //Adiciona o Candidato no TreeMap
                this.adicionarCandidato(md5String, new Candidato(md5String,data[1],data[2]));
            }
            csvReader.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException: " + e.getMessage());
        }
    }
}
