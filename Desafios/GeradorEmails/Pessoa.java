import java.io.Serializable;

public class Pessoa implements Serializable, Comparable<Pessoa> {
    private String nome;
    private String email;

    public Pessoa(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return nome + " - " + email;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Pessoa && email.equals(((Pessoa) obj).email);
    }

    @Override
    public int compareTo(Pessoa outra) {
        return nome.compareToIgnoreCase(outra.nome);
    }
}