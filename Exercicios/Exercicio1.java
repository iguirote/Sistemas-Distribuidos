import java.util.Arrays;
import java.util.Random;

public class Exercicio1 {

    static class SomaTarefa implements Runnable {
        
        private int[] subLista;
        private int soma;

        public SomaTarefa(int[] subLista) {
            this.subLista = subLista;
            this.soma = 0;
        }

        public int getSoma() {
            return soma;
        }

        @Override
        public void run() {
            for (int num : subLista) {
                soma += num;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // criar vetor de 100 números aleatórios aqui
        
        int[] listaOriginal= new int[100];
        Random random = new Random();
        for (int i = 0; i < listaOriginal.length; i++) {
            listaOriginal[i] = random.nextInt(100);
        }

        // dividir em 4 Partes e criar as tarefas e threads aqui
        int Partes = listaOriginal.length/4;
        SomaTarefa[] tarefas = new SomaTarefa[4];
        Thread[] threads = new Thread[4];
        int[][] sublistas = new int[4][];

        for (int i = 0; i < 4; i++) {
            int inicio = i * Partes;
            int fim = inicio + Partes;
            sublistas[i] = Arrays.copyOfRange(listaOriginal, inicio, fim);
            tarefas[i] = new SomaTarefa(sublistas[i]);
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

        int somaTotal = 0;
        for (int i = 0; i < 4; i++) {
            somaTotal += tarefas[i].getSoma();
            System.out.println("Sub lista " + (i + 1) + " : " + Arrays.toString(sublistas[i]) + " Soma : " + tarefas[i].getSoma());
        }
        System.out.println("Soma total : " + somaTotal);

    }
}