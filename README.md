# redes-ep2-typerace
Repositório para o EP2 de Redes de Computadores, EACH-USP - 2021/2
<br><br>

## Integrantes
* Maraiza Adami Pereira - 6836201
* Matheus Pecoraro de Carvalho Santos - 11917271
* Sungwon Yoon - 9822261
* Vinicius Almeida - 10816815
<br>


## Pré-requisitos
* JDK 11 ou maior (testado com a JDK11 OpenJDK)
* Gradle (incluso no repositório, não é necessário instalá-lo)
<br>

## Rodando
Para rodar o servidor
```sh
./gradlew server:run
```

Para rodar um cliente
```sh
./gradlew client:run --console=plain
```
<br>

## Instruções do jogo

* Regras do Jogo

  •	Envie uma palavra por vez <br>
  •	Não há ordem específica para enviar palavras<br>
  •	Palavras podem ser maiúsculas ou minúsculas<br>
  •	Palavras erradas não tiram ponto<br>
  
o	Comandos:<br>

  o	Digite "Start " para começar<br>
  o	Digite "Sair" para sair antes do jogo iniciar<br>
  o	Digite "exit" para sair a qualquer hora<br>
