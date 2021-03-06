public interface Eleicao extends java.rmi.Remote {

    void votar(String eleitor, String candidato) throws java.rmi.RemoteException;

    void adicionarCandidato(String key, Candidato value) throws java.rmi.RemoteException;

    void readCSV(String pathToCsv) throws java.rmi.RemoteException;

    String testMethod() throws java.rmi.RemoteException;

}
