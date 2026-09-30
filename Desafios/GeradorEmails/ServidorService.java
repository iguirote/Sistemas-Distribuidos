import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class ServidorService extends Thread {
    private ServidorController controller;
    private ArrayList<Pessoa> lista;

    public ServidorService(ServidorController controller) {
        this.controller = controller;
        this.lista = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            ServerSocket servidor = new ServerSocket(50000);
            System.out.println("Servidor ouvindo a porta 50000");

            while (true) {
                Socket cliente = servidor.accept();

                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                String nome = (String) entrada.readObject();

                String[] partes = nome.split(" ");
                String email = (partes[0] + "." + partes[partes.length - 1] + "@ufn.edu.br").toLowerCase();
                Pessoa p = new Pessoa(nome.toUpperCase(), email);

                ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());

                if (lista.contains(p)) {
                    saida.writeObject(null);
                } else {
                    lista.add(p);
                    Collections.sort(lista);
                    controller.atualizarListaNaView(lista);
                    saida.writeObject(p);
                }

                cliente.close();
            }
        } catch (Exception e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        }
    }
}