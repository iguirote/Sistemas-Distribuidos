# Sistemas Distribuídos — Threads em Java

> Anotações, exercícios e desafios desenvolvidos ao longo das aulas de Sistemas Distribuídos.  
> Foco em concorrência, paralelismo e comunicação entre processos usando Java.
>
> Readme feito com ia, porem utilizando como base exclusivamente meus arquivos e minhas anotações de aula.

---

## Índice

- [Conceitos Fundamentais](#conceitos-fundamentais)
- [Aula 1 — Comunicação em SD](#aula-1--comunicação-em-sd)
- [Aula 2 — Threads e Seção Crítica](#aula-2--threads-e-seção-crítica)
- [Aula 4 — Soluções com Threads](#aula-4--soluções-com-threads)
- [Aula 7 — Sockets](#aula-7--sockets)
- [Aula 9 — Streams e Serialização](#aula-9--streams-e-serialização)

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

## Aula 7 — Sockets

### O que é um Socket ?

Socket é um meio **lógico** para duas máquinas se conectarem e trocarem dados.

- Sockets **necessitam de threads**, pois enquanto uma thread espera uma leitura (bloqueante), outra precisa continuar trabalhando.
- O padrão de envio de dados via sockets é o **JSON**.
- **Serialização** é converter um objeto em uma sequência de bytes para que ele possa ser enviado.
- Para mandar algo via socket, esse dado precisa poder ser **serializado**.

### Server x Client

```
Server
  ├── ServerSocket
  └── Socket (representa o cliente)
        ├── Escritor ┐
        └── Leitor   ┴→ de sockets

Client
  ├── Socket
  ├── Escritor ┐
  └── Leitor   ┴→ de sockets
```

Fluxo da comunicação:

```
Cliente  →  Servidor  →  Cliente(s)
```

### Atividade

| | Descrição |
|-|-----------|
| **Cliente** | `JFrame` com campo de input para o nome, botão de enviar e campo de email que exibe a resposta do servidor |
| **Servidor** | `JFrame` com um `JTextArea` mostrando a lista de pessoas no formato `nome - email` |

> **Regra:** toda vez que o servidor receber um usuário novo, a lista deve ser **ordenada pelo nome**.

---

## Aula 9 — Streams e Serialização

### Passo a passo da conexão

| | Cliente | Servidor |
|-|---------|----------|
| **1** | `Socket` | `ServerSocket` (endereço IP + porta de serviço) |
| **2** | Output | `Socket` (representa o cliente) |
| **3** | Input | `OutputStream` |
| **4** | — | `InputStream` |

```
Servidor  ←——————→  Cliente
IP Servidor          IP Cliente
Porta Servidor       Porta Cliente
```

### O que passa no Socket ?

```
Bytes   → preferido, pois qualquer tecnologia consegue ler
String  → DataOutputStream / DataInputStream
Objeto  → ObjectOutputStream / ObjectInputStream (exige SERIALIZAÇÃO)
```

| Tipo | Classes de escrita/leitura | Observação |
|------|----------------------------|------------|
| **Bytes** | `OutputStream` / `InputStream` | Mais compatível entre tecnologias |
| **String** | `DataOutputStream` / `DataInputStream` | Simples para textos |
| **Objeto** | `ObjectOutputStream` / `ObjectInputStream` | A classe precisa implementar `Serializable` |

> Exemplo prático com `String` (`writeUTF` / `readUTF`): veja o [Chat Cliente/Servidor](#chat-clienteservidor-aula-9) na seção de Exercícios.

---

## Exercícios

### Chat Cliente/Servidor (Aula 9)

Chat simples via console usando `Socket`, `ServerSocket` e threads. O cliente e o servidor trocam mensagens de texto (`String`) usando `DataOutputStream` / `DataInputStream`.

#### Estrutura

```
Servidor
  ├── ServerSocket (porta 1234)
  ├── Thread enviadora  → lê o console e envia para TODOS os clientes (broadcast)
  └── Para cada cliente:
        └── ThreadRecebedora → ouve aquele cliente

Cliente
  ├── Socket (127.0.0.1:1234)
  ├── ThreadRecebedora → ouve o servidor
  └── ThreadEnviadora  → lê o teclado e envia ao servidor
```

#### Classes

| Classe | Papel |
|--------|-------|
| `Servidor` | Abre o `ServerSocket`, aceita clientes com `accept()` e guarda os sockets numa lista |
| `Cliente` | Conecta ao servidor e dispara as duas threads (receber e enviar) |
| `Comunicador` | Classe utilitária com `enviaMensagem` e `recebeMensagem` (métodos `static`) |
| `ThreadRecebedora` | `Runnable` que fica ouvindo um socket e imprime o que chega. Usada pelos dois lados |
| `ThreadEnviadora` | `Runnable` que lê o teclado e envia pelo socket. Usada só pelo cliente |

#### Comunicador

```java
public class Comunicador {

    public static String recebeMensagem(Socket s) {
        try {
            return new DataInputStream(s.getInputStream()).readUTF(); // bloqueante
        } catch (Exception e) {
            return null; // null = conexão encerrada
        }
    }

    public static void enviaMensagem(Socket s, String mensagem) {
        try {
            new DataOutputStream(s.getOutputStream()).writeUTF(mensagem);
        } catch (Exception e) { }
    }
}
```

#### Pontos importantes

- **`accept()` é bloqueante**: fica parado até um cliente se conectar. Por isso cada cliente ganha sua própria `ThreadRecebedora`, e o laço volta logo para o `accept()`.
- **Duas threads no cliente**: ler o teclado e ler o socket são operações bloqueantes. Com uma thread só, uma travaria a outra.
- **`recebeMensagem` retorna `null` quando a conexão cai**, e é isso que faz a `ThreadRecebedora` sair do laço.
- **`writeUTF()`** envia o tamanho do texto junto, para o `readUTF()` do outro lado saber onde a mensagem termina.
- A porta (`1234`) precisa ser a mesma no servidor e no cliente. `127.0.0.1` significa "esta mesma máquina".

#### Como executar

```bash
javac *.java
java Servidor      # em um terminal
java Cliente       # em outro terminal (pode abrir vários)
```

---

