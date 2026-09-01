package Modulo1;

import Modulo1.controller.CaixaController;
import Modulo1.model.CaixaModel;
import Modulo1.service.CaixaService;
import Modulo1.view.CaixaView;

public class Main {
    public static void main(String[] args) {
        CaixaModel caixa = new CaixaModel();
        CaixaService caixaService = new CaixaService(caixa);
        CaixaView caixaView = new CaixaView();
        CaixaController caixaController = new CaixaController(caixaService, caixaView);
        caixaController.iniciar();
    }
}
