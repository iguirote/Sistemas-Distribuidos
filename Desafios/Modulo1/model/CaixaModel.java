package Modulo1.model;

// "Model": guarda o saldo_central COMPARTILHADO por todas as threads.
// Os métodos são synchronized -> só uma thread por vez consegue ler/alterar
// o saldo, isso evita a condição de corrida (race condition).
public class CaixaModel {

    private double saldoCentral = 0.0;

    public synchronized void adicionarVenda(double valor) {
        saldoCentral += valor;
    }

    public synchronized double getSaldoCentral() {
        return saldoCentral;
    }
}
