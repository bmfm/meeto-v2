Para correr a aplicação irá necessitar dos seguintes requisitos:
- Java 8
- Servidor com MySQL com a respectiva base de dados (o script de criação da base de dados encontra-se na pasta support, ficheiro {meetosqlscript.sql}

Após reunidos todos os requisitos necessários, é necessário que o MySQL esteja a correr dentro da própria (localhost), ou no caso que esteja se encontre noutra máquina, que seja definido o respectivo IP e porta.
Como foi mencionado anteriormente, deve-se correr o script {meetossqlcript.sql} que contem todas as tabelas criadas bem como alguns dados.
Por fim, deve-se executar-se por esta ordem a execução de servidores da máquina:
 1 - RMIServer.java
 2 - TCPServer.java (duas vezes para um deles tomar o papel de backup)
 3 - TCPClient.java (quantas vezes necessário para criar vários clientes)

Relativamente ao IP de máquina onde os servidores e o cliente estão a correr, existe um ficheiro intitulado {property} (support/property) onde se pode alterar os endereços e as portas onde estes vão estar a correr.
