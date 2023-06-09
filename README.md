<h1 align="center">
    <img alt="ItauPersonnalite" title="#ItauPersonnalite" src="./logo_itau_personnalite.png" />
</h1>


# API Itaú Cliente Personnalité
API para cadastro de clientes Itaú Personnalité com Java e Spring Boot. Está API foi desenvolvida utilizando a linguagem Java versão 8 e o framework Spring Boot.

# Objetivo:
Construir uma API de cadastro de clientes Itaú Personnalité responsável por fazer todo o gerenciamento de dados dos clientes do banco Itaú.

# Tecnologias utilizadas: 
* Java (Versão 8); 
* Maven;
* Mockito;
* Lombook;
* JPA;
* H2 (Banco de dados em memória);
* Junit;
* Docker;
* Swagger;

# Pre-requisitos para execução do projeto sem Docker:
1. Ter o Java versão 8 instalado e com as variáveis de ambiente devidamente configuradas;
2. Ter o Maven instalado e com as variáveis de ambiente devidamente configuradas;

# Pre-requisitos para execução do projeto com Docker:
1. Ter o Docker instalado e com as variáveis de ambiente devidamente configuradas;

# Execução do projeto sem Docker:
1. Executar o comando: <b>mvn clean install</b> dentro do diretório: <b>/CadastroClienteItauPersonnalite</b>
2. Executar o comando: <b>mvn spring-boot:run</b> dentro do diretório: <b>/CadastroClienteItauPersonnalite</b>
3. Para acessar o Swagger contendo todos os Endpoints via interface web: <b>http://localhost:8080/swagger-ui.html</b>
4. Para acessar o banco de dados H2 (Banco de dados em memória): http://localhost:8080/h2
<br></br>
Obs: A senha do banco e a URL de coneção estão no arquivo: <b>application.properties (CadastroClienteItauPersonnalite/src/main/resources)</b>

# Execução do projeto com Docker:
1. Executar o comando: <b>mvn clean install</b> dentro do diretório: <b>/CadastroClienteItauPersonnalite</b>
2. Executar o comando: <b>docker build -t <Nome da imagem a ser criada (sugestão: api_itau) .></b>
3. Executar o comando: <b>docker run -p 8080:8080 <Nome da imagem a ser criada (sugestão: api_itau)></b>
4. Para acessar o Swagger contendo todos os Endpoints via interface web: <b>http://localhost:8080/swagger-ui.html</b>
5. Para acessar o banco de dados H2 (Banco de dados em memória): http://localhost:8080/h2
<br></br>
Obs: Após a execução dos passos acima a aplicação já estará sendo executada em um conteiner e você já poderá mandar requisições http através de qualquer
ferramenta de requisições http <b>(Sugestão: Postman)</b>. E também poderá acessar a documentação da Api por meio do Swagger(http://localhost:8080/swagger-ui.html)

# Exemplo requisições:
Os exemplos das requisições estão no arquivo da collection do Postman: API_Itau_Personnalite.postman_collection.json

# Observação Geral:
Essa Api foi desenvolvida utilizando um banco de dados em memória (H2).

# Conhecimento Extra
Como já dizia o velho ditato: "Conhecimento nunca é de mais!" :blush:. Resolvi instalar a aplicação como serviço no Windows e ficou show de bola! :stuck_out_tongue_winking_eye:

1. Entre no diretório: <b>\CadastroClienteItauPersonnalite</b>
2. Abra o arquivo: <b>CadastroClienteItauPersonnalite-0.0.1-SNAPSHOT.xml</b>. Alterar os valores: 
<b>value="D:\Marcel\Desenvolvimento Software\Projetos Java\Projetos\CadastroClienteItauPersonnalite"</b> <b>"D:\Marcel\Desenvolvimento Software\Projetos Java\Projetos\CadastroClienteItauPersonnalite\CadastroClienteItauPersonnalite-0.0.1-SNAPSHOT.jar"</b>
para o caminho onde projeto está salvo na sua máquina.
3. Execute o prompt de comando como administrador e digitar o seguinte comando: <b>CadastroClienteItauPersonnalite-0.0.1-SNAPSHOT.exe install service</b>. Seu novo serviço já aparecerá nos serviços do windows.
Agora é só reiniciar o computador para que o seviço seja iniciado!:smiley: e pronto você já poderá mandar requisições via Postman ou através de qualquer
ferramenta de requisições http.
<br></br>
Obs: Caso você deseje desinstalar o serviço é só executar o prompt de comando como administrador e digitar o seguinte comando: 
<b>CadastroClienteItauPersonnalite-0.0.1-SNAPSHOT.exe uninstall service</b>
