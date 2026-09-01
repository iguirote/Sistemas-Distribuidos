package Modulo1.service;

import Modulo1.model.CaixaModel;

public class CaixaService {

    private static final int FICHAS_POR_CAIXA = 1000;
    private static final double VALOR_FICHA = 10.0;

    private final CaixaModel caixaModel;

    public CaixaService(CaixaModel caixaModel) {
        this.caixaModel = caixaModel;
    }

    // Cada thread do pool executa isso: vende 1000 fichas e soma no saldo central.
    public void venderFicha() {
        for (int i = 0; i < FICHAS_POR_CAIXA; i++) {
            caixaModel.adicionarVenda(VALOR_FICHA);
        }
    }

    public double verSaldo() {
        return caixaModel.getSaldoCentral();
    }
}
