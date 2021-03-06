import Utils.Encript;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;

public class EleicaoServant extends UnicastRemoteObject implements Eleicao {

    TreeMap<String, Candidato> map = new TreeMap<String, Candidato>();
    Cache<Object> cache;

    public EleicaoServant() throws java.rmi.RemoteException {
        super();
        this.cache = new Cache<>(30);
    }

    @Override
    public void votar(String eleitor, String candidato) throws RemoteException {

        // Valida se o voto já esta no Cache
        //var x = this.cache.Get(eleitor);


            this.cache.Add(eleitor, new Object() {
                public String hashEleit = eleitor;
                public String numeroCand = candidato;
                boolean vote = true;
                boolean computado = false;
            });






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
                String md5String = Encript.StringToMD5(data[0], "upper");

                // Adiciona o Candidato no TreeMap
                this.adicionarCandidato(md5String, new Candidato(md5String,data[1],data[2]));
            }
            csvReader.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    @Override
    public String testMethod() throws RemoteException {
        return "CARALHO";
    }
}
