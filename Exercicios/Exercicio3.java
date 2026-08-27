import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Exercicio3 {

    static class LerNome implements Runnable {
        
        private List<String> nomes;

        public LerNome(List<String> nomes) {
            this.nomes = nomes;
        }

        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new FileReader("nomes.txt"))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    nomes.add(linha);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
                    
    static class LerNumb implements Runnable {
        
        private List<Integer> numeros;

        public LerNumb(List<Integer> numeros) {
            this.numeros = numeros;
        }

        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new FileReader("numeros.txt"))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    numeros.add(Integer.parseInt(linha.trim()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class ExibirNome implements Runnable {
        private final List<String> nomes;

        public ExibirNome(List<String> nomes) {
            this.nomes = nomes;
        }

        @Override
        public void run() {
            System.out.println("Nomes :");
            for (String nome : nomes) {
                System.out.println(nome);
            }
        }
    }

    static class ExibirNumb implements Runnable {
        private List<Integer> numeros;

        public ExibirNumb(List<Integer> numeros) {
            this.numeros = numeros;
        }

        @Override
        public void run() {
            System.out.println("Números :");
            for (Integer numero : numeros) {
                System.out.println(numero);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        List<String> nomes = new ArrayList<>();
        List<Integer> numeros = new ArrayList<>();


        Thread[] threadsLer = new Thread[2];
        threadsLer[0] = new Thread(new LerNome(nomes));
        threadsLer[1] = new Thread(new LerNumb(numeros));
        
        Thread[] threadsExibir = new Thread[2];
        threadsExibir[0] = new Thread(new ExibirNome(nomes));
        threadsExibir[1] = new Thread(new ExibirNumb(numeros));

        // Inicar threads de ler
        for (Thread t : threadsLer) {
            t.start();
        }


        try {
            for (Thread t : threadsLer) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            }

        // Inicar threads de exibir

        for (Thread t : threadsExibir) {
            t.start();
        }

        try {
            for (Thread t : threadsExibir) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            }

            
    }
}