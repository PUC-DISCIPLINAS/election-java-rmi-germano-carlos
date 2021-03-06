public interface Eleicao extends java.rmi.Remote {

    public void votar(String eleitor, String candidato) throws java.rmi.RemoteException;

    public void adicionarCandidato(String key, Candidato value) throws java.rmi.RemoteException;

    public void readCSV(String pathToCsv) throws java.rmi.RemoteException;

}
