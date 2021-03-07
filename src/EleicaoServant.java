import Utils.Encript;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class EleicaoServant extends UnicastRemoteObject implements Eleicao {

    TreeMap<String, Candidato> map = new TreeMap<String, Candidato>();
    TreeMap<String, Integer> votosArmazenados = new TreeMap<String, Integer>();
    Cache<Object> cache;

    public EleicaoServant() throws java.rmi.RemoteException {
        super();
        this.cache = new Cache<>(30);
        this.readCSV("C:\\Users\\Carlos Germano\\Desktop\\senadores.csv");
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

            if(!localizaCandidatoB(id))
                return true;

            this.map.get(id).setVotos(this.map.get(id).getVotos()+1);
            this.cache.Add(eleitor, new Voto(eleitor,candidato,true,true ));

            // Atualizar o CSV
            if(!this.votosArmazenados.containsKey(id))
                this.votosArmazenados.put(id, 1);
            else
                this.votosArmazenados.replace(id,this.votosArmazenados.get(id) + 1);

            atualizaCSV();

            return true;
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return false;
    }
    @Override
    public String result(String numeroCandidato) throws RemoteException {

        try {
            Candidato candidato = this.localizaCandidato(Encript.StringToMD5(numeroCandidato,"upper"));

            if(Objects.isNull(candidato))
                return "Candidato não localizado, por favor insira um válido";
            else {
                return "*** Candidato encontrado *** \n" +
                        "Nome: " + candidato.getNome() + "\n"+
                        "HashId: " + candidato.getId() + "\n" +
                        "Partido: " + candidato.getPartido() + "\n"+
                        "Quantidade de Votos: " + candidato.getVotos() + "\n";
            }
        } catch (Exception e) {
            System.out.println("Exception :" + e.getMessage());
        }

        return "Ocorreu um erro ao chamar essa função. Tente novamente mais tarde !";
    }

    private void adicionarCandidato(String key, Candidato value) throws RemoteException {
        this.map.put(key, value);
    }
    private void readCSV(String pathToCsv) throws RemoteException {

        try {
            // Realiza a criação de um arquivo CSV para armezanemento de votos!
            if(!this.existsCSV())
                this.createCSV();

            // Realiza a validação do arquivo CSV para recuperação
            // e criação do Canditado com os valores respectivos de votos
            this.readCSVVotos();

            BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(";");

                if("NR_CANDIDATO".equals(data[0]) && "NM_URNA_CANDIDATO".equals(data[1]) && "SG_PARTIDO".equals(data[2]))
                    continue;

                // Gera o hash MD5 baseado no nome do eleitor
                String md5String = Encript.StringToMD5(data[0], "upper");
                int votos = 0;
                //Busca a quantidade de votos que o canditado tinha em versões anteriores do Serviço
                if(this.votosArmazenados.containsKey(md5String))
                    votos = this.votosArmazenados.get(md5String);

                // Adiciona o Candidato no TreeMap
                this.adicionarCandidato(md5String, new Candidato(md5String,data[1],data[2],votos));
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

    private Candidato localizaCandidato (String numeroC) {

        Set<String> keys = map.keySet();
        for (String k : keys) {
            if(map.get(k).getId().equals(numeroC))
                return map.get(k);
        }

        return null;
    }

    private boolean localizaCandidatoB (String numeroC) {

        Set<String> keys = map.keySet();
        for (String k : keys) {
            if(map.get(k).getId().equals(numeroC))
                return true;
        }

        return false;
    }
    private void readCSVVotos() throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader("data.csv"));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            if("HASH".equals(data[0]) && "VOTOS".equals(data[1]))
                continue;

            votosArmazenados.put(data[0],Integer.parseInt(data[1]));
        }

        csvReader.close();
    }

    private boolean existsCSV()
    {
        File tmpDir = new File("data.csv");
        return tmpDir.exists();
    }

    private void createCSV() throws IOException {
        FileWriter csvWriter = new FileWriter("data.csv");
        csvWriter.append("HASH");
        csvWriter.append(";");
        csvWriter.append("VOTOS");
        csvWriter.append("\n");

        csvWriter.flush();
        csvWriter.close();
    }

    private void atualizaCSV() throws Exception {

        if(existsCSV())
            Files.deleteIfExists(Paths.get("data.csv"));
        else
            createCSV();

        FileWriter csvWriter = new FileWriter("data.csv");
        Set<String> keys = votosArmazenados.keySet();
        for (String k : keys) {
            csvWriter.append(k);
            csvWriter.append(";");
            csvWriter.append(Integer.toString(votosArmazenados.get(k)));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();

    }
}
