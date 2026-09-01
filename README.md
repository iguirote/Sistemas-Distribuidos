# Sistemas Distribuídos — Threads em Java

> Anotações, exercícios e desafios desenvolvidos ao longo das aulas de Sistemas Distribuídos.  
> Foco em concorrência, paralelismo e comunicação entre processos usando Java.

---

## Índice

- [Conceitos Fundamentais](#conceitos-fundamentais)
- [Aula 1 — Comunicação em SD](#aula-1--comunicação-em-sd)
- [Aula 2 — Threads e Seção Crítica](#aula-2--threads-e-seção-crítica)
- [Aula 4 — Soluções com Threads](#aula-4--soluções-com-threads)
- [Exercícios](#exercícios)
- [Desafios ](#desafio--pool-de-threads)

---

## Conceitos Fundamentais

**Sistemas Distribuídos** são sistemas que dividem o trabalho entre múltiplos processos ou máquinas para compartilhar recursos e aumentar a eficiência. A ideia central é : *dividir para conquistar*.

A comunicação entre esses processos é feita através de troca de dados, respeitando modelos como o **TCP/IP**.

**Threads** são mini processos que executam tarefas de forma independente dentro de um mesmo programa. Em Java, elas envolvem rotinas, métodos e instruções que podem rodar de forma concorrente ou paralela.

```
Processamento Concorrente  →  tarefas se alternam no mesmo núcleo
Processamento Paralelo     →  tarefas rodam ao mesmo tempo em núcleos diferentes
```

---

## Aula 1 — Comunicação em SD

### Tipos de comunicação

| Tipo | Descrição |
|------|-----------|
| **Unicast** | Um para um |
| **Multicast** | Um para um grupo |
| **Broadcast** | Um para todos |

A comunicação pode ser **bloqueante** : quem escreve (writer/sender) espera quem lê (reader/receiver) estar pronto.

### Modelo TCP/IP

```
Aplicação
    ↓
Transporte
    ↓
Interface
    ↓
Rede
```

Cada máquina é identificada por um **endereço IP**, que pode ser de servidor, cliente ou grupo. A comunicação acontece através de **sockets**.

---

## Aula 2 — Threads e Seção Crítica

### O que é Seção Crítica ?

Seção crítica é quando duas ou mais threads precisam acessar o mesmo recurso ao mesmo tempo. Isso pode causar inconsistências nos dados se não for controlado.

### Tipos de thread quanto ao acesso à memória

```
Com Seção Crítica (Memória Compartilhada)
    ├── Sincronismo por tempo → Sistema Operacional
    ├── Semáforos
    └── Lock

Sem Seção Crítica (Memória Isolada)
    └── Cada thread trabalha com seus próprios dados
```

### Thread vs Runnable

| | `Thread` (classe) | `Runnable` (interface) |
|-|-------------------|------------------------|
| **Memória** | Sem compartilhamento | Com compartilhamento |
| **Uso** | Academicamente simples | Mais recomendado |
| **Como usar** | Herdar a classe | Implementar a interface |

```java
// Herdando Thread (sem memória compartilhada)
class MinhaThread extends Thread {
    public void run() { ... }
}
MinhaThread t1 = new MinhaThread();
t1.start();

// Implementando Runnable (com memória compartilhada)
class MinhaTarefa implements Runnable {
    public void run() { ... }
}
Thread t1 = new Thread(new MinhaTarefa());
t1.start();
```

---

## Aula 4 — Soluções com Threads

### Threads Físicas vs Lógicas

```
Threads
  ├── Físicas      → núcleos reais do processador
  └── Lógicas/Virtuais → criadas pelo Java, gerenciadas pela JVM
```

### Operações comuns com listas em threads

- **Armazenar** : inserir, popular
- **Exibir** : filtrar, listar
- **Buscar** : pesquisar, atualizar, reescrever

### As 3 formas de usar threads em Java

**A) Threads nomeadas** — cada thread com seu próprio nome
```java
MinhaThread t1 = new MinhaThread();
MinhaThread t2 = new MinhaThread();
t1.start();
t2.start();
```
> Usável academicamente, mas ruim em grande escala.

---

**B) Lista de threads** — array gerenciando múltiplas threads
```java
Thread[] threads = new Thread[4];
for (int i = 0; i < 4; i++) {
    threads[i] = new Thread(new MinhaTarefa());
    threads[i].start();
}
for (Thread t : threads) {
    t.join(); // espera todas terminarem
}
```
> Mais recomendado que threads nomeadas. Precisa de `join()` para coordenar.

---

**C) Pool de threads** — gerenciador automático de threads
```java
ExecutorService pool = Executors.newFixedThreadPool(N);
pool.execute(() -> {
    // tarefa aqui
});
pool.shutdown();
```
> Mais prático. O pool já instancia e gerencia as threads automaticamente.

### Métodos essenciais

| Método | O que faz |
|--------|-----------|
| `start()` | Inicia a execução da thread de forma concorrente |
| `join()` | Faz a thread principal esperar essa thread terminar |
| `run()` | Código que a thread executa |
| `shutdown()` | Encerra o pool após todas as tarefas terminarem |

---

---

## Referências

- [Documentação oficial Java — Thread](https://docs.oracle.com/en/java/docs/api/java.base/java/lang/Thread.html)
- [Documentação oficial Java — ExecutorService](https://docs.oracle.com/en/java/docs/api/java.base/java/util/concurrent/ExecutorService.html)
- [Arquivo de log usado no desafio](https://raw.githubusercontent.com/alexandrezamberlan/sistemasDistribuidos/refs/heads/master/00-exercicios_trabalhos/erro.log)
