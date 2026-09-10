<=====> Sockets <====>

\-aSockets é um meio lógico para 2 maquinas poderem se conectarem

\- Sockets ***NECESSITAM*** de threads

\- O padrão de envios via sockets é Json

\- Serialização é dividir partes de um projeto para poder ser enviados.

\- Para mandar as coisas vias sockets, elas precisão poder ser serializadas



<=====> Server X Client <=====>

Server

&#x20;|

&#x20;+-> ServerSpclet

&#x20;|

&#x20;+-> Socket(Reresentar o cliente)

&#x20;| 

&#x20;+-> Escritor |

&#x20;|	      | -> de sockets

&#x20;+-> Leitor   |

Client

&#x20;|

&#x20;+-> Socket

&#x20;|

&#x20;+-> Escritor |

&#x20;|	      | -> de sockets	

&#x20;+-> Leitor   |



Fluxi ckassuci -> Servidor -> Cliente(s)



<=====> Atividade <=====>

Cliente com jFrom com campo de input de nome e um botão de enviar, com o campo de email devendo exibir a resposta do servidor



Servidor jForme com lista de pessoas com jTextarea com a lista no formato : "nome - Email".

Obs : Toda vez que receber usuário, precisa ser ordenada pelo nome

