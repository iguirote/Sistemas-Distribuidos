import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Lado SERVIDOR: espera clientes se conectarem, ouve todos eles ao mesmo tempo
 * e permite enviar uma mensagem digitada no console para todos (broadcast).
 */
public class Servidor {
    private ServerSocket servidor;                          // "porta de entrada" que aceita conexões
    private ArrayList<Socket> clientes = new ArrayList<>(); // todos os clientes conectados

    public Servidor() throws Exception {
        // Abre a porta 1234 e passa a escutar. Se a porta já estiver em uso, dá erro aqui.
        servidor = new ServerSocket(1234);
        System.out.println("Servidor escutando na porta 1234...");

        iniciaThreadEnviadora(); // thread paralela: lê o teclado do servidor
        aguardaClientes();       // laço principal: aceita novos clientes
    }

    // Uma única thread lê o console do servidor e envia para TODOS os clientes.
    private void iniciaThreadEnviadora() {
        new Thread(() -> {  // lambda: forma curta de criar um Runnable
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String mensagem = scanner.nextLine(); // espera o operador digitar
                // Percorre a lista e manda a mesma mensagem para cada cliente.
                for (Socket cliente : clientes) {
                    Comunicador.enviaMensagem(cliente, "Servidor diz: " + mensagem);
                }
            }
        }).start();
    }

    private void aguardaClientes() throws Exception {
        while (true) {
            // accept() BLOQUEIA até alguém se conectar; devolve o socket daquele cliente.
            Socket cliente = servidor.accept();
            System.out.println("Nova conexão recebida de: " + cliente.getInetAddress());

            // Guarda o cliente para poder enviar mensagens a ele depois.
            clientes.add(cliente);

            // Cria uma thread só para ouvir ESTE cliente. Assim o laço volta
            // imediatamente para o accept() e o próximo cliente não precisa esperar.
            new Thread(new ThreadRecebedora(cliente)).start();
        }
    }

    public static void main(String[] args) throws Exception {
        new Servidor();
    }
}
