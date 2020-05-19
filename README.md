# Projeto de teste (LojaVirtual)

Esse é um pequeno projeto que implementa um API RESTful com CRUD e tratamento de exceções uilizando SpringBoot

# Criação do projeto

Criar um novo projeto em: https://start.spring.io/

![Tela do SpringBoot](https://github.com/rodcezar/lojavirtual/blob/master/src/main/resources/public/images/springboot.png)

Escolhendo as seguintes dependências:

* Spring Web  - Traz um conjunto de bibliotecas para criar aplicativos da web, incluindo RESTful, usando o Spring MVC. Usa o um Tomcat incorporado como o contêiner incorporado padrão.

* JPA: Conjunto de bibliotecas Spring Data JPA e Hibernate

* DevTools: Utilitários para para reinicialização automática do servidor, etc

* H2: Banco de dados rápido, leve e simplesde usar

# Inicialização da base de dados

O banco de dados é inicializado usando scripts SQL. Tudo será definido em dois scripts SQL: schema.sql e data.sql, ambos localizados na pasta 'src / main / resources'.

* schema.sql:

```
CREATE TABLE IF NOT EXISTS `produto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `amount` int NOT null,
  
  PRIMARY KEY(`id`),
  UNIQUE(`name`)
) engine=InnoDB default charset=utf8;
```

* data.sql

```
INSERT INTO produto(name, amount) values ('Torradeira', 10);
INSERT INTO produto(name, amount) values ('Maquina de lavar', 10);
INSERT INTO produto(name, amount) values ('Martelo', 10);
INSERT INTO produto(name, amount) values ('Cafeteira', 10);
INSERT INTO produto(name, amount) values ('Celta 2006', 10);
```

# Configuração do banco de dados

Vamos configurar o H2 para persistir os dados no disco. Essas configurações ficam em:

* 'src/main/resources/application.yml':

```
spring:
  h2:
    console:
      enabled: true
      path: /h2
  datasource:
    url: jdbc:h2:file:./db/crud
    driverClassName: org.h2.Driver
    username: sa
    password: 
    continueOnError: true
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: none
```

* Estamos definindo 'path' como '/ h2'. Dessa forma, poderemos acessar o console H2 pressionando 'http: // localhost: 8080 / h2' depois de iniciar o aplicativo;

![Tela do H2](https://github.com/rodcezar/lojavirtual/blob/master/src/main/resources/public/images/h2.png)

* Definindo 'url' como 'jdbc: h2: file: ./db / crud', estamos dizendo ao H2 que persistiremos a base no disco, e não na memória. O arquivo do banco de dados será armazenado em 'db / crud';
* Importante: estamos usando o JPA e inicializando nosso banco de dados via scripts SQL, portanto, não queremos que o Hibernate gere o DDL, é por isso que estamos definindo 'ddl-auto' como 'none'.
* Os testes unitários usarão um banco de dados na memória (não irá persistir no disco) -> 'src / test / resources / application.yml':

```
spring:
  datasource:
    driver-class-name: org.h2.Driver
    url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
    username: sa
    password: sa
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: create-drop

```

Para inicializar nosso banco de dados de teste, definimos um arquivo chamado import.sql na pasta 'src / test / resources':

```

INSERT INTO produto(name, amount) values ('Torradeira1', 20);
INSERT INTO produto(name, amount) values ('Maquina de lavar1', 10);
INSERT INTO produto(name, amount) values ('Martelo1', 20);
INSERT INTO produto(name, amount) values ('Cafeteira1', 20);
INSERT INTO produto(name, amount) values ('Celta 20061', 20);

```

Após a criação do projeto e inicialização do banco de dados, foi desenvolvida a api RESTful que implementa o CRUD para gerenciar produtos. Segue uma visão do projeto no Eclipse. 

![Árvore do projetot](https://github.com/rodcezar/lojavirtual/blob/master/src/main/resources/public/images/project.png)

Para testar suas funcionalidades, basta baixar ou clonar o projeto no github.

Agora, vamos às funcionalidades:

* GET /api/produtos: retorna uma lista de produtos;
* GET /produtos/name/name?{string}: retorna uma lista de produtos que comecem com a substring;
* POST /api/produto: Cria um produto a partir de um JSON no bodyRequest;
* PUT /api/produto/{id}: atualiza um produto com o ID fornecido em um JSON no bodyRequest;
* DELETE /api/produto/{id}: exclui um produto com o ID fornecido.

Vamos usar [curl](https://curl.haxx.se/) para testar:

Primeiramente, inicializar o servidor:


```
C:\SpringBoot\lojavirtual>mvn spring-boot:run   
```
*Ambiente windows


# Testando GET API/PRODUTOS 

```
curl -v "http://localhost:8080/api/produtos"
```

* Resultado no prompt de comando

![resultado no prompt de comando](https://github.com/rodcezar/lojavirtual/blob/master/src/main/resources/public/images/curl_teste_01.png)

* Resultado em um client HTML/Angular (ainda incompleto), que faz parte do projeto:

![CLient Angular](https://github.com/rodcezar/lojavirtual/blob/master/src/main/resources/public/images/client_angular.png)

Algumas outras URL's para teste:

```
curl -v "http://localhost:8080/api/produtos/name?name=Ma"
```

Testando o POST /api/produto:

```

curl -v -H "Content-Type: application/json" -X POST "http://localhost:8080/api/produto" -d '{"name":"NintendoSwitch", "amount":1}'

```

etc... 
