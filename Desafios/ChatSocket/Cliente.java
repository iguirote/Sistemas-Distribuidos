import java.net.Socket;

/**
 * Lado CLIENTE: conecta no servidor e cria duas threads,
 * uma para receber e outra para enviar.
 */
public class Cliente {

    public Cliente() throws Exception {
        // Abre a conexão com o servidor.
        // "127.0.0.1" = esta mesma máquina (igual a "localhost").
        // 1234 = porta onde o servidor está escutando (precisa ser a mesma!).
        // Se o servidor não estiver rodando, aqui lança uma exceção.
        Socket socket = new Socket("127.0.0.1", 1234);
        System.out.println("Conectado ao Servidor com sucesso!");

        // Por que DUAS threads? Porque ler do teclado e ler do socket são
        // operações que ficam "travadas" esperando. Se fosse uma só, enquanto
        // esperasse o teclado, não conseguiria mostrar o que o servidor mandou
        // (e vice-versa). Com duas, as duas coisas acontecem ao mesmo tempo.
        new Thread(new ThreadRecebedora(socket)).start(); // ouve o servidor
        new Thread(new ThreadEnviadora(socket)).start();  // envia o que digitamos
    }

    public static void main(String[] args) throws Exception {
        new Cliente();
    }
}
