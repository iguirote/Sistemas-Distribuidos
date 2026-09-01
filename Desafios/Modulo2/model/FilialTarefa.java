package Modulo2.model;

import java.util.concurrent.Callable;

// Tarefa executada por uma thread do pool: recebe SÓ a sua Filial
// (nenhuma variável global) e devolve o faturamento local via Future.
public class FilialTarefa implements Callable<Double> {

    private Filial filial;

    public FilialTarefa(Filial filial) {
        this.filial = filial;
    }

    @Override
    public Double call() {
        double faturamentoLocal = 0.0;
        for (Double valor : filial.getVendas()) {
            faturamentoLocal += valor;
        }
        return faturamentoLocal;
    }
}
