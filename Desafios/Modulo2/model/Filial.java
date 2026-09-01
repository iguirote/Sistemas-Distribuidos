package Modulo2.model;

import java.util.List;

// "Model": apenas guarda os dados de UMA filial (nome + vendas locais).
// Não estende Thread e não tem nenhum vínculo com as outras filiais:
// cada objeto Filial é isolado, sem estado compartilhado.
public class Filial {

    private String nome;
    private List<Double> vendas;

    public Filial(String nome, List<Double> vendas) {
        this.nome = nome;
        this.vendas = vendas;
    }

    public String getNome() {
        return nome;
    }

    public List<Double> getVendas() {
        return vendas;
    }
}
