import java.net.Socket;

/**
 * Tarefa que fica OUVINDO um socket e imprime tudo o que chegar.
 * Implementa Runnable: é o "o que a thread vai fazer" (método run).
 * Usada pelos dois lados: o cliente ouve o servidor, e o servidor ouve cada cliente.
 */
public class ThreadRecebedora implements Runnable {
    private Socket socket; // a conexão que esta thread vai ouvir

    public ThreadRecebedora(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // Laço infinito: fica esperando mensagens até a conexão acabar.
        while (true) {
            // Bloqueia aqui até chegar algo (ou a conexão cair).
            String mensagem = Comunicador.recebeMensagem(socket);

            // null = conexão encerrada -> sai do laço e a thread termina.
            if (mensagem == null) {
                System.out.println("Conexão encerrada com: " + socket.getInetAddress());
                break;
            }

            // Mostra quem mandou (IP) e o texto.
            System.out.println("[" + socket.getInetAddress() + "]: " + mensagem);
        }
    }
}
