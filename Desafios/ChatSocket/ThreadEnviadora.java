import java.net.Socket;
import java.util.Scanner;

/**
 * Tarefa que lê o que o usuário digita no teclado e envia pelo socket.
 * Usada apenas pelo CLIENTE (o servidor tem a sua própria lógica de envio).
 */
public class ThreadEnviadora implements Runnable {
    private Socket socket; // a conexão por onde as mensagens serão enviadas

    public ThreadEnviadora(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // Scanner em System.in = leitura do teclado (console).
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pode digitar suas mensagens:");

        while (true) {
            // nextLine() bloqueia até o usuário digitar algo e apertar Enter.
            // Depois enviamos o texto para o outro lado.
            Comunicador.enviaMensagem(socket, scanner.nextLine());
        }
    }
}
