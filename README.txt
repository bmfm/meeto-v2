Para correr a aplicação irá necessitar dos seguintes requisitos:
- Java 8
- Servidor com MySQL com a respectiva base de dados (o script de criação da base de dados encontra-se na pasta support, ficheiro {meetosqlscript.sql}
- Tomcat 8.0.15

Após reunidos todos os requisitos necessários, é necessário que o MySQL esteja a correr dentro da própria (localhost), ou no caso que esteja se encontre noutra máquina, que seja definido o respectivo IP e porta.
Como foi mencionado anteriormente, deve-se correr o script {meetossqlcript.sql} que contem todas as tabelas criadas bem como alguns dados.
Por fim, deve-se executar-se por esta ordem a execução de servidores da máquina para obter o comportamento necessário da meta 1:
 1 - RMIServer.java
 2 - TCPServer.java (duas vezes para um deles tomar o papel de backup)
 3 - TCPClient.java (quantas vezes necessário para criar vários clientes)

Relativamente ao IP de máquina onde os servidores e o cliente estão a correr, existe um ficheiro intitulado {property} (support/property) onde se pode alterar os endereços e as portas onde estes vão estar a correr.

No que diz respeito à segunda meta do projecto, e depois de correr os passos acima mencionados, esta poderá ser validada possuindo e executando o Tomcat e colocando o ficheiro "property" e "policy.all" na sua pasta bin. De seguida, basta abrir um browser e aceder ao seguinte link:

 www.localhost.com:8080/meeto. Substituir "localhost" pelo IP da máquina onde estiver a ser corrido o servidor.






