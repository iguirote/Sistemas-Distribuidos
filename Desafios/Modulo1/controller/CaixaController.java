package Modulo1.controller;

import Modulo1.service.CaixaService;
import Modulo1.view.CaixaView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CaixaController {

    private static final int NUMERO_CAIXAS = 5;

    private final CaixaService caixaService;
    private final CaixaView caixaView;

    public CaixaController(CaixaService caixaService, CaixaView caixaView) {
        this.caixaService = caixaService;
        this.caixaView = caixaView;
    }

    public void iniciar() {
        // Pool simples com 5 threads, uma para cada caixa físico
        ExecutorService pool = Executors.newFixedThreadPool(NUMERO_CAIXAS);
        List<Future<?>> futuros = new ArrayList<>();

        for (int i = 0; i < NUMERO_CAIXAS; i++) {
            final int numeroCaixa = i + 1;
            futuros.add(pool.submit(() -> {
                caixaService.venderFicha();
                caixaView.exibirMensagem("Caixa " + numeroCaixa + " vendida com sucesso!");
            }));
        }

        // Espera todos os caixas terminarem antes de olhar o saldo final
        for (Future<?> futuro : futuros) {
            try {
                futuro.get();
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
                caixaView.exibirMensagem("Erro em um dos caixas: " + e.getMessage());
            }
        }
        pool.shutdown();

        double saldo = caixaService.verSaldo();
        caixaView.exibirSaldo(saldo);

        if (saldo == 50000.00) {
            caixaView.exibirMensagem("✓ Saldo correto!");
        } else {
            caixaView.exibirMensagem("✗ Saldo incorreto!");
        }
    }
}
