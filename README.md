# unesc-final-java-project

Projeto final da disciplina de Programação Orientada a Objetos — UNESC.

Este repositório contém o projeto final em Java, desenvolvido como parte da avaliação final do curso. Ainda está em construção e será atualizado conforme o progresso das etapas de implementação.

## 🚧 Status do Projeto

🔧 Em desenvolvimento

## 📌 Objetivo

O projeto tem como objetivo desenvolver uma aplicação desktop em Java capaz de importar arquivos `.txt` com um template específico. O fluxo principal do sistema inclui:

- Importar e validar arquivos `.txt` com estrutura predefinida;
- Mostrar os dados extraídos para o usuário antes da importação;
- Permitir que o usuário aceite ou recuse a importação;
- Salvar os dados em um banco de dados PostgreSQL após confirmação;
- Exibir os dados salvos para visualização, edição e exclusão;
- Impedir a importação duplicada do mesmo arquivo.

## 💻 Tecnologias Utilizadas

- Java
- JDBC
- PostgreSQL
- IDE: Eclipse / VS Code

## 📁 Estrutura Inicial do Projeto

```
src/
├── Main.java
│
├── view/               # Interfaces gráficas
│
├── controller/         # Controladores que fazem a ponte entre a view e a lógica
│
├── service/            # Lógica de negócios (validações, processamento de dados etc.)
│
├── database/
│   ├── model/          # Classes de modelo (entidades que representam as tabelas do banco)
│   └── dao/            # Data Access Objects (operações com o banco de dados PostgreSQL)
```

## 📅 Etapas do Projeto

- ✅ Definição do escopo
- 🚧 Leitura e parser do arquivo `.txt`
- ⏳ Interface para pré-visualização e confirmação
- ⏳ Integração com PostgreSQL
- ⏳ Operações CRUD
- ⏳ Validação de arquivos duplicados
- ⏳ Testes e ajustes finais
- ⏳ Entrega e apresentação

## 📄 Licença

Este projeto é acadêmico e não possui licença comercial.

---

> Este repositório será atualizado com mais informações assim que o desenvolvimento avançar.
