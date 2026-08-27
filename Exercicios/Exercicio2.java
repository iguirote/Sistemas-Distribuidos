import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Exercicio2 {

    static class LimpaTarefa implements Runnable {
        
        private List<String> subLista;
        private List<String> listaLimpa;

        public LimpaTarefa(List<String> subLista) {
            this.subLista = subLista;
            this.listaLimpa = new ArrayList<>();
        }

        public List<String> getListaLimpa() {
            return listaLimpa;
        }
        

        @Override
        public void run() {
            for (String nome : subLista) {
                String nomeLimpo = nome.trim().toUpperCase();
                listaLimpa.add(nomeLimpo);
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Lê o .csv de nomes 
        List<String> nomes = new ArrayList<>();
        System.out.println("Lendo nomes do arquivo...");
        try (BufferedReader br = new BufferedReader(new FileReader("nomes.csv"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                nomes.add(linha);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // dividir em 2 Partes e criar as tarefas e threads aqui
        int partes = nomes.size() / 2;
        LimpaTarefa[] tarefas = new LimpaTarefa[2];
        Thread[] threads = new Thread[2];
        List<String>[] subListas = new List[2];

        for (int i = 0; i < 2; i++) {
            int inicio = i * partes;
            int fim = (i == 1) ? nomes.size() : inicio + partes;
            subListas[i] = new ArrayList<>(nomes.subList(inicio, fim));
            tarefas[i] = new LimpaTarefa(subListas[i]);
            threads[i] = new Thread(tarefas[i]);
        }

        // iniciar as threads aqui
        for (Thread t : threads) {
            t.start();
        }

        // join() nas threads aqui

        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            }
            
            
        // exibir resultados e soma total aqui

        List<String> listaFinal = new ArrayList<>();
        listaFinal.addAll(tarefas[0].getListaLimpa());
        listaFinal.addAll(tarefas[1].getListaLimpa());
        for (int i=0; i < 2; i ++) {
            System.out.println("Sublista " + (i+1) + " : " + subListas[i]);
        }
            System.out.println("Lista final : " + listaFinal);

    }
}