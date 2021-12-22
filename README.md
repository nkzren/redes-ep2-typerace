# redes-ep2-typerace
Repositório para o EP2 de Redes de Computadores, EACH-USP - 2021/2

# Integrantes
* Gabriel Braga Lagrotaria de Oliveira - 11796600
* Rodrigo Fernandes Gomes Vieira - 11796548
* Matheus Aranha Lopes - 11221219
* Rafael Afonso Stettiner - 4444444

## Pré-requisitos
* JDK 11 ou maior (testado com a JDK11 OpenJDK)
* Gradle (incluso no repositório, não é necessário instalá-lo)

### Rodando
Para rodar o servidor (se preferir, substitua 8081 por outra porta de sua preferência)
```sh
./gradlew server:run --args="8081" --console=plain
```

Para rodar um cliente
```sh
./gradlew client:run --args="8081" --console=plain
```

Ao rodar o servidor, ele esperará clientes se conectarem, e que algum cliente mande um comando para iniciar;
Ao rodar os clientes, eles serão informados da quantidade de jogadores (sempre que um novo entrar) e terão a possibilidade de iniciar a partida, digitando "iniciar" e teclando Enter;
O cliente pode digitar "sair" a qualquer momento para encerrar sua conexão com o servidor;
Ao iniciar a partida, o servidor envia a lista de palavras, e os clientes recebem; o cliente que finalizar o envio de todas as palavras corretamente ganha a partida;
Os critérios de desempate em caso de número de acertos iguais são: quem tiver menor número de erros fica na frente, e caso eles sejam iguais também, quem tiver mandado a última palavra primeiro fica na frente.
Quando a partida finalizar, todos os clientes recebem a classificação, com a quantidade de acertos, erros e a duração da partida;

