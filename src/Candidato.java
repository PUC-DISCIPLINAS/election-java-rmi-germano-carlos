public class Candidato {
    private String id;
    private String nome;
    private String partido;
    private Integer votos;

    public String getNome() { return nome; }

    public String getId() { return id; }

    public String getPartido() { return partido; }

    public void setId(String id) { this.id = id; }

    public void setNome(String nome) { this.nome = nome; }

    public void setPartido(String partido) { this.partido = partido; }

    public Integer getVotos() { return votos; }

    public void setVotos(Integer votos) { this.votos = votos; }

    public Candidato(String id, String nome, String partido) {
        this.id = id;
        this.nome = nome;
        this.partido = partido;
    }
}
