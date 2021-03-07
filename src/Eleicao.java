public interface Eleicao extends java.rmi.Remote {

    boolean votar(String eleitor, String candidato) throws java.rmi.RemoteException;
    String result(String candidato) throws Exception;

}
