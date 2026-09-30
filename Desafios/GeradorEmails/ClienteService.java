import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClienteService {

    public Pessoa solicitarEmail(String nome) throws Exception {
        Socket cliente = new Socket("localhost", 50000);

        ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
        saida.writeObject(nome);

        ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
        Pessoa p = (Pessoa) entrada.readObject();

        cliente.close();
        return p;
    }
}