## Serviço de transação de pagamentos da Pismo

Crie uma conta agora mesmo e comece fazer transações ilimitadas totalmente gratuítas (fakes).
Somos uma empresa que nasceu com o objetivo de modernizar os meios de pagamentos, 
hoje somos uma das maiores empresas de transações do mundo.

Teste agora mesmo nossas soluções.

### Installation
- Install OpenJDK 11 [Installing OpenJDK](https://openjdk.java.net/install/)
- Maven [Installing Apache Maven](https://maven.apache.org/install.html)
- Git [Installing Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)

Baixe o repositório do github
```
❯ git clone git@github.com:robsonkades/payments-transaction-pismo.git
```

Entre na pasta do repositório
```
❯ cd payments-transaction-pismo
```

Execute o commando para iniciar a aplicação
```
❯ mvn spring-boot:run
```

Após a inicialização da sua aplicação será disponibilizado as apis para criação de conta e transação.
Para consultar as apis disponíveis basta acessar o swagger (http://localhost:8080/v2/api-docs) contendo a documentação de como executar, 
ou você pode acessar o swagger ui para testar atráves do endereço http://localhost:8080/swagger-ui/index.htm



## Executando com docker

### Installation
- Install OpenJDK 11 [Installing OpenJDK](https://openjdk.java.net/install/)
- Maven [Installing Apache Maven](https://maven.apache.org/install.html)
- Git [Installing Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
- Docker [Installing Docker](https://docs.docker.com/engine/install/)

Baixe o repositório do github
```
❯ git clone git@github.com:robsonkades/payments-transaction-pismo.git
```

Entre na pasta do repositório
```
❯ cd payments-transaction-pismo
```

Execute o commando para compilar a aplicação
```
❯  mvn clean package
```

Criando imagem do docker
```
❯  docker build  -t pismo:1.0.0 .
```

Executando o container
```
❯  docker run --name pismo -p 8080:8080  -d pismo:1.0.0
```

