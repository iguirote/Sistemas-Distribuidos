package Modulo2;

import Modulo2.controller.FranquiaController;
import Modulo2.service.FranquiaService;
import Modulo2.view.FranquiaView;

public class Main {
    public static void main(String[] args) {
        FranquiaView view = new FranquiaView();
        FranquiaService service = new FranquiaService();
        FranquiaController controller = new FranquiaController(view, service);

        controller.calcularFaturamentos();
    }
}
