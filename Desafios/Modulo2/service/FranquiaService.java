package Modulo2.service;

import Modulo2.model.Filial;
import Modulo2.model.FilialTarefa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FranquiaService {

    private static final int NUMERO_FILIAIS = 4;
    private static final int REGISTROS_POR_FILIAL = 10000;

    // Fork: gera as 4 filiais (dados isolados) e manda cada uma pro pool.
    // Join: espera os 4 Futures e soma os resultados.
    public double calcularFaturamentoTotal() throws InterruptedException, ExecutionException {
        List<Filial> filiais = gerarFiliais();

        ExecutorService pool = Executors.newFixedThreadPool(NUMERO_FILIAIS);
        List<Future<Double>> futuros = new ArrayList<>();

        for (Filial filial : filiais) {
            futuros.add(pool.submit(new FilialTarefa(filial)));
        }

        double faturamentoTotal = 0.0;
        for (Future<Double> futuro : futuros) {
            faturamentoTotal += futuro.get();
        }

        pool.shutdown();
        return faturamentoTotal;
    }

    private List<Filial> gerarFiliais() {
        List<Filial> filiais = new ArrayList<>();
        for (int i = 1; i <= NUMERO_FILIAIS; i++) {
            filiais.add(new Filial("Filial " + i, gerarVendas(REGISTROS_POR_FILIAL)));
        }
        return filiais;
    }

    private List<Double> gerarVendas(int quantidade) {
        List<Double> vendas = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            vendas.add(100.0);
        }
        return vendas;
    }
}
