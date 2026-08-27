import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class DesafioPool {

    // Classe que guarda o resumo parcial de cada trabalhador


    static class Resultado {
        int erroBanco;
        int erroVolume;
        int erroMigracao;
        int erroBuffer;
        
        public Resultado(int erroBanco, int erroVolume, int erroMigracao, int erroBuffer){
            this.erroBanco = erroBanco;
            this.erroVolume = erroVolume;
            this.erroMigracao = erroMigracao;
            this.erroBuffer = erroBuffer;
        }
    }

    // Tarefa que cada trabalhador do pool vai executar
    static class Trabalhador implements Runnable {
        private List<String> parte;

        private BlockingQueue<Resultado> filaResultados;


        public Trabalhador(List<String> parte, BlockingQueue<Resultado> filaResultados) {
            this.parte = parte;
            this.filaResultados = filaResultados;
        }

        @Override
        public void run() {

            // Contadores locais deste trabalhador
            int erroBanco = 0;
            int erroVolume = 0;
            int erroMigracao = 0;
            int erroBuffer = 0;
            
            for (String linha : parte) {

                // Separa os dados da linha
                // data, hora, código de erro, usuário
                String[] dados = linha.split(",");

                // O código do erro está na posição 2
                int codigoErro = Integer.parseInt(dados[2]);

                // Conta o tipo de erro
                switch (codigoErro) {

                    case 0:
                        erroBanco++;
                        break;

                    case 1:
                        erroVolume++;
                        break;

                    case 2:
                        erroMigracao++;
                        break;

                    case 3:
                        erroBuffer++;
                        break;
                }
            }

            // Cria o resumo deste trabalhador
            Resultado resultado = new Resultado(
                    erroBanco,
                    erroVolume,
                    erroMigracao,
                    erroBuffer
            );

            try {
                filaResultados.put(resultado);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        int trabalhadorThread = 4;
        ExecutorService pool = Executors.newFixedThreadPool(trabalhadorThread);
        BlockingQueue<Resultado> filaResultados = new LinkedBlockingQueue<>();

        Path arquivo = Path.of("Erros.log");
        List<String> linhas = Files.readAllLines(arquivo);
        System.out.println("Total de linhas : " + linhas.size());

        int tamanhoParte = linhas.size() / trabalhadorThread;

        for (int i = 0; i < trabalhadorThread; i++) {
            int inicio = i * tamanhoParte;
            int fim = (i == trabalhadorThread - 1) ? linhas.size() : inicio + tamanhoParte;
            List<String> parte = new ArrayList<>(linhas.subList(inicio, fim));

            // enviar cada parte pro pool aqui
            pool.execute(
                    new Trabalhador(parte, filaResultados)
            );         
        }

        // encerrar o pool aqui
        pool.shutdown();
        
        // coletar os 4 resultados da fila e somar aqui
        int totalBanco = 0;
        int totalVolume = 0;
        int totalMigracao = 0;
        int totalBuffer = 0;

        // Coordenador recebe os 4 resultados
        for (int i = 0; i < trabalhadorThread; i++) {

            // Espera um resultado chegar na fila
            Resultado resultado = filaResultados.take();

            // Soma os resultados
            totalBanco += resultado.erroBanco;
            totalVolume += resultado.erroVolume;
            totalMigracao += resultado.erroMigracao;
            totalBuffer += resultado.erroBuffer;
        }

        // Mostra o resultado final
        System.out.println();
        System.out.println("========== RESULTADO FINAL ==========");

        System.out.println(
                "Conexão com o banco: " + totalBanco
        );

        System.out.println(
                "Montagem de volume: " + totalVolume
        );

        System.out.println(
                "Execução de migração: " + totalMigracao
        );

        System.out.println(
                "Erro no buffer de memória: " + totalBuffer
        );
        }
    }
}