Cliente :

1\. Socket

2\) Output

3\. Input



Server :

1\. ServerSocket --> endereço ip		

&#x09;	+-> porta de serviço

2\. Socket (cliente)

3\. OutputStream

4\. inputStream

&#x20;       \_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

Server |\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_| Cliente

IpServer       | IpClient

PortaServer    | PortaClient



<===========> Oq passa no Socket ? <===========> 

&#x20;

\- Bites (prefere passar bite pra poder passar pra qualquer tecnologia)

\- String

&#x20;    | 

&#x20;    +->DataOutpuStream

&#x20;    |

&#x20;    +->DataInputStream		

\- Objeto

&#x20;    | 		         \_

&#x20;    +->ObjectOutpuStream |

&#x20;    |			  |--> SERIALIZAÇÃO

&#x20;    +->ObjectInputStream\_|





&#x09;		 

