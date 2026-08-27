Threads

|

+->Fisicas

|

+->Lógicas/Virtuais (Java)



Threads --> Mini **<i>~~processos~~</i>** 

&#x09;	|

&#x09;	+-> Na programação : 

&#x09;		- Rotinas;

&#x09;		- Métodos;



No contexto de **LISTAS :** 

&#x20;- Armazenar : inserir, popular, ....;

&#x20;- Exibir : filtrar;

&#x20;- Buscar/Pesquisar : Atualizar e reescrever.



Soluções para threads :



A) Thread comunicadas (cada thread com seu nome);

// Usável academicamente, mas ruim em grande escala

B) Lista de thread (uma lista com vários threads);

// Mais recomendado, mas se quiser saber o numero da thread precisa achar

\*Só instancia, precisa passar outra "stratando"

C) Pool de thread (Aglomera threads, já instanciando e startando, mais prático)



public class ExemploPool {

&#x20;   public static void main(String\[] args) {

&#x20;       // Criamos um pool de threads com capacidade para N tarefas ao mesmo tempo

&#x20;       int N = 5;

&#x09;int tamanhoLista = 20;

&#x20;       ExecutorService pool = Executors.newFixedThreadPool(N);

// N Sendo o numero de thread com listas criadas

&#x20;    for (int i = 1; i <= N; i++) {

&#x20;           final int idTarefa = i;

&#x20;           pool.execute(() -> {

&#x20;               // Cada thread cria sua própria lista (sem memória compartilhada)

&#x20;               List<Integer> lista = new ArrayList<>();

&#x20;               Random random = new Random();



&#x20;               // 1. Popular de forma aleatória

&#x20;               for (int j = 0; j < N; j++) {

&#x20;                   lista.add(random.nextInt(100));

&#x20;               }



&#x20;               // 2. Exibir lista original

&#x20;               System.out.println("Tarefa " + idTarefa + " (Original): " + lista);



&#x20;               // 3. Ordenar

&#x20;               Collections.sort(lista);



&#x20;               // 4. Exibir lista ordenada

&#x20;               System.out.println("Tarefa " + idTarefa + " (Ordenada): " + lista);

&#x20;           });

&#x20;       }

// o for realiza a rotina toda até i for igual a n



<=============> Desafio <=============>



Arquivo LOG -> Erros.log

&#x20;|

&#x20;+-> Precisa conter 4 tipos de erro sendo eles :

&#x20;0) Conexão com o banco

&#x20;1) Montagem de volume

&#x20;2) Execução de migração

&#x20;3) Erro no buffer de memória

A linha tenha a seguinte estrutura :

data, hora, código de erro, usuário logado





