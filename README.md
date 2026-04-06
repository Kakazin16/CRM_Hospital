# 🏥 Hospital CRM

Sistema desktop desenvolvido em **Java** com **JavaFX** para gerenciamento de pacientes, médicos, leads e agendamentos.

---

## 📌 Sobre o projeto

Este projeto simula um **CRM hospitalar**, permitindo:

* Cadastro e gerenciamento de pacientes
* Cadastro e gerenciamento de médicos
* Controle de leads (potenciais pacientes)
* Agendamento de consultas
* Validação de regras de negócio (ex: data no passado, duplicidade, etc.)

O objetivo do projeto é aplicar conceitos de:

* Programação Orientada a Objetos (POO)
* Arquitetura em camadas (Controller, Service, DAO)
* Integração com banco de dados (JDBC)
* Interface gráfica com JavaFX

---

## 🧱 Arquitetura

O projeto segue uma separação em camadas:

* **Controller** → comunicação com a interface
* **Service** → regras de negócio
* **DAO** → acesso ao banco de dados
* **Model** → entidades do sistema
* **View (JavaFX)** → interface gráfica

---

## 🛠️ Tecnologias utilizadas

* Java 17+
* JavaFX
* JDBC
* Oracle Database
* Dotenv (para variáveis de ambiente)

---

## ⚙️ Configuração do ambiente

### 1. Clonar o repositório

```bash
git clone <URL_DO_REPOSITORIO>
cd hospital-crm
```

---

### 2. Criar arquivo `.env`

Na raiz do projeto, crie um arquivo chamado `.env` com o seguinte conteúdo:

```env
DB_URL=jdbc:oracle:thin:@SEU_HOST:PORTA:SID
DB_USER=SEU_USUARIO
DB_PASS=SUA_SENHA
```

📌 Exemplo:

```env
DB_URL=jdbc:oracle:thin:@localhost:1521:XE
DB_USER=system
DB_PASS=123456
```

---


### 3. Configurar banco de dados

Cole o comando abaixo no seu banco de dados

```

CREATE TABLE PACIENTE (
    ID                  NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NOME                VARCHAR2(100) NOT NULL,
    CPF                 VARCHAR2(11) UNIQUE NOT NULL,
    DATA_NASCIMENTO     DATE,
    SEXO                CHAR(1),
    PESO                NUMBER(5,2),
    ALTURA              NUMBER(3,2),
    IMC                 NUMBER(5,2),
    EMAIL               VARCHAR2(100),
    TELEFONE            VARCHAR2(20),
    POR_ONDE_CONHECEU   VARCHAR2(100),
    ATIVO               CHAR(1) DEFAULT 'S'
);

CREATE TABLE MEDICO (
    ID                      NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NOME                    VARCHAR2(100) NOT NULL,
    CRM                     VARCHAR2(20) UNIQUE NOT NULL,
    TELEFONE                VARCHAR2(20),
    EMAIL                   VARCHAR2(100),
    PROCEDIMENTOS_ATENDIDOS VARCHAR2(500),
    DATA_NASCIMENTO         DATE,
    CPF                     VARCHAR2(11) UNIQUE,
    ATIVO                   CHAR(1) DEFAULT 'S'
);

CREATE TABLE LEAD (
    ID          NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NOME        VARCHAR2(100) NOT NULL,
    TELEFONE    VARCHAR2(20),
    EMAIL       VARCHAR2(100),
    INTERESSE   VARCHAR2(200),
    ORIGEM      VARCHAR2(100),
    STATUS      VARCHAR2(20) DEFAULT 'FRIO',
    DATA_CADASTRO DATE DEFAULT SYSDATE
);

CREATE TABLE AGENDAMENTO (
    ID              NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    PACIENTE_ID     NUMBER NOT NULL,
    MEDICO_ID       NUMBER NOT NULL,
    DATA_HORA       TIMESTAMP NOT NULL,
    STATUS          VARCHAR2(20) DEFAULT 'AGENDADO',
    CONSTRAINT fk_agend_paciente FOREIGN KEY (PACIENTE_ID) REFERENCES PACIENTE(ID),
    CONSTRAINT fk_agend_medico   FOREIGN KEY (MEDICO_ID)   REFERENCES MEDICO(ID)
);

```

---

### 4. Configure o JavaFX

1. Adicionar o JavaFX como Library
   
Vai em:

File > Project Structure

Entra em:

Libraries

Clica no + → Java

Seleciona a pasta do JavaFX que já está no projeto (lib/javafx/lib)

Confirma tudo com OK

3. Configurar VM Options (ESSENCIAL)
   
Vai em:

Run > Edit Configurations

Na sua aplicação (classe Main), adiciona em VM options:

--module-path "CAMINHO/DA/PASTA/lib/javafx/lib" --add-modules javafx.controls,javafx.fxml


### 5. Executar o projeto

Execute a classe:

```java
Main.java
```

A interface será aberta com abas para cada módulo do sistema.

---

## 🧪 Testes

A classe `Main` contém uma área de testes no console que valida:

* Cadastro de paciente
* Validação de CPF duplicado
* Cadastro de médico
* Cadastro de lead
* Validação de agendamento no passado

---

## 📷 Funcionalidades

* Cadastro de pacientes com cálculo automático de IMC
* Edição e inativação de registros
* Listagem de dados
* Controle de status de agendamentos
* Validação de regras de negócio

---


## 📄 Licença

Este projeto é apenas para fins educacionais.
