import javax.swing.JOptionPane;

public class ClienteController {
    private ClienteView view;
    private ClienteService service;

    public ClienteController() {
        view = new ClienteView();
        service = new ClienteService();

        view.setBotaoListener(e -> enviarDados());
        view.setVisible(true);
    }

    private void enviarDados() {
        String nome = view.getNomeInput().trim();

        if (!nome.contains(" ")) {
            view.exibirMensagem("Digite nome e sobrenome!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Pessoa p = service.solicitarEmail(nome);

            if (p == null) {
                view.setEmailOutput("Email já existe!");
                view.exibirMensagem("Seu nome já está na lista.", "Atenção", JOptionPane.WARNING_MESSAGE);
            } else {
                view.setEmailOutput(p.getEmail());
                view.exibirMensagem("Email: " + p.getEmail(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            view.exibirMensagem("Erro ao conectar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new ClienteController();
    }
}