public interface Eleicao extends java.rmi.Remote {

    boolean votar(String eleitor, String candidato) throws java.rmi.RemoteException;

    void adicionarCandidato(String key, Candidato value) throws java.rmi.RemoteException;

    void readCSV(String pathToCsv) throws java.rmi.RemoteException;

    void atualizaCSV(String pathtoCsv) throws java.rmi.RemoteException;

    String testMethod() throws Exception;

}
