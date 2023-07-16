# CRUD - Sistema de viagens

Este é um projeto de um CRUD (Create, Read, Update, Delete) de viagens desenvolvido em Java, utilizando orientação a objetos e alguns conceitos de Padrões de Projetos.

## Funcionalidades

O sistema oferece as seguintes funcionalidades:

- Cadastrar uma nova viagem;
- Alterar uma viagem realizada (dada opções de cidade e local, data de embarque e data de retorno);
- Excluir uma viagem realizada (caso haja);
- Imprimir as viagens realizadas (caso haja).

## Estrutura do Projeto

O projeto está organizado em pacotes `serviços, entidades, exceções e enums`. Sendo as classes principais para seu funcionamento: 

- `GerenciadorViagens`: Classe responsável pelo gerenciamento das viagens, implementando as operações do CRUD.
- `GerenciarAcoes`: Classe que organiza as escolhas de ações do sistema, chamando os métodos do gerenciador de viagens
- `ServicoIniciarDestino.java`: Classe que fornece o serviço de iniciar destinos, conforme a escolha do serviço de viagem (HURB ou Milhas)

## Dependências

O `GerenciadorViagens` possui duas dependências injetadas em seu construtor:

1. `GerenciarAcoes`: Responsável por gerenciar as ações do sistema.
2. `ServicoIniciarDestino`: Fornece o serviço de iniciar destinos.

## UML - Design do Sistema
![Class Diagram0](https://github.com/RayanArgolo03/java-crud-viagens/assets/113947677/e7281df4-2b4d-4e75-a2f7-bc1d3c9694e2)


## Aprendizados

- Neste projeto desenvolvi desde o código da aplicação até a modelagem do mesmo para a vizualização de suas relações, tendo aprendido na prática a granularizar e desacoplar um sistema, tornando-o escalável e facilmente manutenível, utilizando de forma assertiva os preceitos do paradigma orientado a objetos.




