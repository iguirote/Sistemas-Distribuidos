package Modulo2.controller;

import Modulo2.service.FranquiaService;
import Modulo2.view.FranquiaView;

import java.util.concurrent.ExecutionException;

public class FranquiaController {

    private FranquiaView view;
    private FranquiaService service;

    public FranquiaController(FranquiaView view, FranquiaService service) {
        this.view = view;
        this.service = service;
    }

    public void calcularFaturamentos() {
        try {
            double faturamentoTotal = service.calcularFaturamentoTotal();
            view.exibirFaturamentoFinal(faturamentoTotal);

        } catch (InterruptedException | ExecutionException e) {
            view.exibirMensagem("Erro ao calcular faturamento: " + e.getMessage());
        }
    }
}
