import java.util.ArrayList;

public class ServidorController {
    private ServidorView view;
    private ServidorService service;

    public ServidorController() {
        view = new ServidorView();
        view.setVisible(true);

        service = new ServidorService(this);
        service.start();
    }

    public void atualizarListaNaView(ArrayList<Pessoa> lista) {
        String texto = "";
        for (Pessoa p : lista) {
            texto += p + "\n";
        }
        view.atualizarTexto(texto);
    }

    public static void main(String[] args) {
        new ServidorController();
    }
}