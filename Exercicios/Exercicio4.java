import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Exercicio4 {

                    
    static class LerNumbs implements Runnable {
        
        private List<Integer> numeros;
        private String arquivo;

        public LerNumbs(List<Integer> numeros, String arquivo) {
            this.numeros = numeros;
            this.arquivo = arquivo;
        }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                numeros.add(Integer.parseInt(linha.trim()));
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo: " + arquivo, e);
        }
    }
}

    static class ExibirNumbs implements Runnable {
        private List<Integer> numeros;

        public ExibirNumbs(List<Integer> numeros) {
            this.numeros = numeros;
        }

        @Override
        public void run() {
            synchronized (numeros) {
            for (Integer numero : numeros) {
                System.out.println(numero);
            }
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {

        List<Integer> numeros = Collections.synchronizedList(new ArrayList<>());
        
        Thread[] threadsLer = new Thread[2];
        threadsLer[0] = new Thread(new LerNumeros(numeros, "numeros1.txt"));
        threadsLer[1] = new Thread(new LerNumeros(numeros, "numeros2.txt"));
        
        for (Thread t : threadsLer) {
            t.start();
        }

        try {
            for (Thread t : threadsLer) {
                t.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrompida ao aguardar leitura dos arquivos.", e);
        }

        Thread exibirThread = new Thread(new ExibirNumbs(numeros));
        exibirThread.start();

        try {
            exibirThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrompida ao exibir os números.", e);
        }       
    }
}