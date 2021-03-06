import Utils.Encript;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.TreeMap;

public class EleicaoServant extends UnicastRemoteObject implements Eleicao {

    TreeMap<String, Candidato> map = new TreeMap<String, Candidato>();
    Cache<Object> cache;

    public EleicaoServant() throws java.rmi.RemoteException {
        super();
        this.cache = new Cache<>(30);
    }

    @Override
    public boolean votar(String eleitor, String candidato) throws RemoteException {
        try {
            // Valida se o voto já esta no Cache
            Voto x = (Voto) this.cache.Get(eleitor);

            if(Objects.isNull(x))
                this.cache.Add(eleitor, new Voto(eleitor,candidato,true,false ));

            // Busca novamento pelo Cache
            Voto voto = (Voto) this.cache.Get(eleitor);
            if(voto.computado)
                return true;

            String id = Encript.StringToMD5(candidato,"upper");
            this.map.get(id).setVotos(this.map.get(id).getVotos()+1);
            this.cache.Add(eleitor, new Voto(eleitor,candidato,true,true ));

            // Atualizar o CSV

            return true;
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return false;
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
                this.adicionarCandidato(md5String, new Candidato(md5String,data[1],data[2],0));
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
    public String testMethod() throws Exception {
        String dados = "";
        String vote = this.map.get(Encript.StringToMD5("544","upper")).getVotos().toString();
        String numero = this.map.get(Encript.StringToMD5("544","upper")).getId();
        String id = this.map.get(Encript.StringToMD5("544","upper")).getNome();
        String partido = this.map.get(Encript.StringToMD5("544","upper")).getPartido();
        Voto cacheObj = (Voto) this.cache.Get(Encript.StringToMD5("Carlos","upper"));
        boolean votoComputador = cacheObj.computado;

        return "Votos: " + vote +
                "\\n Numero: " + numero +
                " \\nid: " + id +
                " \\npartido: " + partido +
                " \\nvotoComputador: " + votoComputador +
                " \\nEleitor: " + cacheObj.hashEleit;
    }

    @Override
    public void atualizaCSV(String pathtoCsv) throws RemoteException {

    }
}
