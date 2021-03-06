public class Voto {

    public String hashEleit;
    public String numeroCand;
    boolean vote;
    boolean computado;

    public Voto(String hashEleit, String numeroCand, boolean vote, boolean computado) {
        this.hashEleit = hashEleit;
        this.numeroCand = numeroCand;
        this.vote = vote;
        this.computado = computado;
    }
}
