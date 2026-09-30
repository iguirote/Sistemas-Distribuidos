import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Classe utilitária: concentra o envio e o recebimento de mensagens.
 * Todos os métodos são static, então não precisamos criar um objeto
 * (usamos direto: Comunicador.enviaMensagem(...)).
 */
public class Comunicador {

    // Lê UMA mensagem de texto que chegou pelo socket.
    public static String recebeMensagem(Socket s) {
        try {
            // getInputStream() = "canal de entrada" do socket (o que o outro lado mandou).
            // DataInputStream permite ler tipos prontos, como String, com readUTF().
            // readUTF() fica BLOQUEADO até chegar uma mensagem.
            return new DataInputStream(s.getInputStream()).readUTF();
        } catch (Exception e) {
            // Se a conexão caiu ou foi fechada, dá erro. Usamos o null como sinal
            // de "acabou a conexão" para quem chamou este método.
            return null;
        }
    }

    // Envia UMA mensagem de texto pelo socket.
    public static void enviaMensagem(Socket s, String mensagem) {
        try {
            // getOutputStream() = "canal de saída" do socket (o que vamos mandar).
            // writeUTF() escreve o texto já com o tamanho na frente, para que
            // o readUTF() do outro lado saiba onde a mensagem termina.
            new DataOutputStream(s.getOutputStream()).writeUTF(mensagem);
        } catch (Exception e) {
            // Simplificação didática: se falhar, ignoramos.
            // Num sistema real, trataríamos (ex.: remover o cliente da lista).
        }
    }
}
