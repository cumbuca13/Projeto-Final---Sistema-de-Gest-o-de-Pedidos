# 🧾 Sistema de Gestão de Pedidos (Console)

Projeto final desenvolvido em **Java**, com foco em **POO**, **concorrência**, **tratamento de exceções** e **boas práticas de arquitetura (SOLID e Object Calisthenics)**.

---

## 🚀 Como compilar e executar (linha de comando)

1. Vá para a pasta `src`:

   ```bash
   cd src
   ```

2. Compile o projeto:

   ```bash
   javac app/Main.java app/service/*.java app/model/*.java app/model/enums/*.java app/repository/*.java app/exception/*.java
   ```

3. Execute:

   ```bash
   java app.Main
   ```

💡 **Dica:** Se preferir, crie um *package* e use uma IDE como **IntelliJ** ou **Eclipse** para organizar automaticamente a estrutura.

---

## 🧠 O que o sistema faz

* **Cadastro de Clientes**

  * Atributos: `ID`, `nome`, `email`
  * Inclui validações de dados.

* **Cadastro de Produtos**

  * Atributos: `ID`, `nome`, `preço`, `categoria`
  * Inclui validações de dados.

* **Criação de Pedidos**

  * Cada pedido contém **itens (produto + quantidade)**.

* **Processamento Assíncrono de Pedidos**

  * Pedidos entram em uma **fila**.
  * Uma **thread** os consome, marcando o pedido como:

    * `PROCESSANDO` → aguarda um tempo → `FINALIZADO`.

* **Listagens disponíveis:**

  * Clientes
  * Produtos
  * Pedidos (com status atual)

---

## 🧩 Conceitos de POO aplicados

* **Classes e objetos:**
  `Client`, `Product`, `Order`, `OrderItem`.

* **Encapsulamento:**
  Atributos `private`, métodos públicos bem definidos.

* **Herança e Polimorfismo:**
  Estrutura flexível para extensões (ex.: `DigitalProduct extends Product`).

* **Interfaces e classes abstratas:**
  Repositórios com responsabilidade única (possível evoluir para `Repository<T>`).

---

## 🧱 Princípios SOLID

* **S — Single Responsibility:**
  Cada classe tem uma única responsabilidade (modelo, repositório, processamento, menu).

* **O — Open/Closed:**
  Aberto para extensão (novos status ou tipos de produto) sem alterar código existente.

* **L — Liskov Substitution:**
  Substituição garantida em subclasses de `Product`.

* **I — Interface Segregation:**
  Serviços expõem apenas o necessário.

* **D — Dependency Inversion:**
  `MenuService` recebe dependências (repositórios, `OrderProcessor`) via construtor.

---

## ⚙️ Object Calisthenics (mínimo de 3 regras aplicadas)

1. **Classes pequenas:**
   Cada classe tem uma única responsabilidade.

2. **Métodos curtos:**
   Métodos do `MenuService` realizam apenas uma ação.

3. **Sem getters/setters triviais:**
   Somente o necessário é exposto.
   Exemplo: `getItems()` retorna uma coleção imutável (`Collections.unmodifiableList`).

---

## 🚨 Tratamento de Exceções

* `InvalidDataException` → erros de validação.
* `EntityNotFoundException` → entidades não encontradas.
* `InterruptedException` → controle de interrupções no processamento assíncrono.

---

## ⚔️ Concorrência

* **Fila de pedidos:** `LinkedBlockingQueue<Order>` (thread-safe).
* **Processamento:** `ExecutorService` com *consumer thread*.
* **Repositórios:** baseados em `ConcurrentHashMap` para segurança entre threads.
* **Controle de estado:** `Order.setStatus(...)` é `synchronized` para garantir coerência.

---

## 📚 Estrutura de pacotes

```
app/
├── Main.java
├── service/
│   └── MenuService.java
├── model/
│   ├── Client.java
│   ├── Product.java
│   ├── Order.java
│   ├── OrderItem.java
│   └── enums/
│       └── OrderStatus.java
├── repository/
│   ├── ClientRepository.java
│   ├── ProductRepository.java
│   └── OrderRepository.java
└── exception/
    ├── InvalidDataException.java
    └── EntityNotFoundException.java
```

---

## 👥 Autores

* **Matheus Grochovski**
* **Luan Ribeiro**

📍 Projeto desenvolvido para fins de aprendizado e prática dos conceitos de **Programação Orientada a Objetos** em Java.

---
