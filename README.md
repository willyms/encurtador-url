Encurtador de URLs
===========================
---
#### Descrição do desafio
O seu serviço irá receber inicialmente como parâmetro uma URL que deverá ser encurtada seguindo as seguintes regras:

1. Mínimo de 5 e máximo de 10 caracteres.

2. Apenas letras e números.

A url retornada deverá ser salva no banco de dados e possui prazo de validade (poderá escolher quanto tempo) e ao receber uma url encurtada, deverá fazer o redirecionamento para a url salva no banco.

#### Exemplo ao encurtar
- O seu sistema recebe uma chamada para encurtar a url `backendbrasil.com.br` e retorna o seguinte json

``` 
{ 
  newUrl: "http://localhost:8081/abc123ab";
} 
```

#### Exemplo ao redirecionar
- Ao receber uma chamada para `http://localhost:8081/abc123ab` você irá retorna um redirecionamento para a url salva no banco (`backendbrasil.com.br`), caso não seja encontrada, retornar HTTP 404
---
###Tecnologias utilizadas:
- [Docker](https://docs.docker.com/get-started/)
- Java
- JPA
- Hibernate
- [Lombok](https://projectlombok.org/)
- [ModelMapper](http://modelmapper.org/getting-started/)
- Postgres
- [Junit 5](https://junit.org/junit5/docs/current/user-guide/)
- H2

###Pré requisito
Para executar este aplicativo, você precisa instalar duas ferramentas: Docker e Docker Compose.

Instruções de como instalar o Docker no [Ubuntu](https://docs.docker.com/engine/install/ubuntu/), [Windows](https://docs.docker.com/docker-for-windows/install/), [Mac](https://docs.docker.com/docker-for-mac/install/).

Docker Compose já está incluído nos pacotes de instalação para Windows e Mac, portanto, apenas usuários do Ubuntu precisam seguir estas [instruções](https://docs.docker.com/compose/install/).

###Executando
Pode ser executado um único comando no terminal:
~~~
$ docker-compose up -d
~~~
Se você quiser pará-lo, execute o seguinte comando:
~~~
$ docker-compose down
~~~

A lista completa de endpoints REST disponíveis pode ser encontrada na IU Swagger, que pode ser chamada usando o link: http://localhost:8080/api/swagger-ui.html

Este aplicativo também é colocado no contêiner do Docker e as suas definições podem ser encontrada no arquivo `./Dockerfile`.

PS: IU Swagger, não fazer o redirecionamento externo do link da página vinculado, para redireciona para o link da página digite na barra de endereço do navegador. Ex. `http://localhost:8080/urls/xdqzgQ5N9s`

![image01](https://github.com/willyms/Encurtador-de-URL/blob/master/ui-swagger.png)
---
@Willyms