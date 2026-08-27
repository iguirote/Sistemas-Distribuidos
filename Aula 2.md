<================> Perguntas <================>



1\. Para que serve Sistemas Distributidos ?

"Dividir pra conquistar" Serve para compartilharmos recursos



2\. Como o Sistema Distributido opera ?

Atraves de comunicação de dados



THREAD -> Mini processos

&#x20;      |

&#x20;      +-> Sistemas Distributidos

&#x20;      |

&#x20;      +-> "ENVOLVEM" rotinas ou tarefas ou instruções



<================> Tipos de thread <================>



\-> Com *Seção Critica* = Memoria Compartilhada

&#x20;      	    |

&#x20;      	    +-> Sincreonismo por tempo  = S.O;

&#x09;				= Semafaros;

&#x09;				= Lock.



\-> Sem *Seção Critica* = Memoria Compartilhada



<================> Processos <================>



+-> thread (classe) -> Sem M.C

|

+->Runnaable (interface) -> Com M.C



<================> Solução 1 <================>

&#x09;ObjetoThread



MinhaThread1 t1 = new Minhathread();

