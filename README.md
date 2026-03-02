# 🚀 Encurtador de URL (URL Shortener)

Um projeto escalável desenvolvido com **Spring Boot**, focado em alta performance e conceitos de System Design. O sistema transforma URLs longas em hashes curtos utilizando codificação **Base62**, garantindo links amigáveis e eficientes.

## 🛠️ Tecnologias e Ferramentas

* **Linguagem:** Java 21 (ou 17)
* **Framework:** Spring Boot 3+
* **Banco de Dados Relacional:** PostgreSQL (H2 para desenvolvimento/MVP)
* **Cache:** Redis (Estratégia Cache-Aside)
* **Mensageria:** RabbitMQ / Kafka (Para processamento de métricas assíncronas)
* **Containerização:** Docker / Podman

## 🏗️ Arquitetura e Decisões Técnicas

### 1. Algoritmo de Encurtamento (Base62)

Diferente do Base64, o **Base62** utiliza apenas caracteres alfanuméricos (`0-9`, `a-z`, `A-Z`). Isso evita caracteres especiais em URLs, tornando os links mais limpos. A lógica baseia-se na conversão do ID incremental do banco de dados para a base 62.

### 2. Estratégia de Cache (Cache-Aside)

Para garantir redirecionamentos em milissegundos, implementamos o padrão **Cache-Aside**:

1. O sistema busca o hash no **Redis**.
2. Se não encontrado (Cache Miss), busca no **PostgreSQL**.
3. Salva o resultado no Redis para futuras consultas.

### 3. Escalabilidade e Assincronismo

O redirecionamento precisa ser instantâneo. Por isso, a coleta de **métricas de acesso** (cliques, geolocalização, browser) é feita de forma assíncrona:

* A API de redirecionamento dispara um evento para uma fila (**RabbitMQ/Kafka**).
* Um serviço secundário (Worker) consome essa fila e persiste os dados, garantindo que, se o serviço de métricas falhar, o redirecionamento continue funcionando (Desacoplamento).

## 🛣️ Roadmap de Desenvolvimento

O projeto foi dividido em fases evolutivas para aplicar conceitos de System Design gradualmente:

* **Fase 1 (MVP):** Lógica core de conversão Base62 e API REST com banco em memória (H2).
* **Fase 2 (Persistência):** Integração com PostgreSQL e persistência real de dados.
* **Fase 3 (Performance):** Camada de Cache com Redis para otimizar leituras.
* **Fase 4 (Métricas):** Implementação de mensageria para processamento de estatísticas em background.

## 🔌 API Endpoints

### Encurtar URL

`POST /url/encurtar`

```json
{
  "url": "https://www.google.com/search?q=system+design+java"
}

```

* **Response (200 OK):** `{ "urlEncurtado": "http://base.url/a5B2" }`

### Redirecionar

`GET /{hash}`

* **Response (302 Found):** Redireciona para a URL original.

---

## 🚀 Como Rodar o Projeto

...
