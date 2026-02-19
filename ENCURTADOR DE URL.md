ENCURTADOR DE URL

BACKEND:
API REST:
- Encurtar URL - POST 
  Request: path:url/encurtar ,body: {url: "urlparaserencurtada.com"}
  Response: status: 200 (201 não por buscar links que ja foram gerados) ,body: {urlEncurtado:"enc/hash"}
  Logica: hash na Base62 pois gera hashs curtos e atende a nossa necessidade
   - Banco relacional com Redis, se não existir no Redis, pega no relacional, grava no Redis e devolve o link (talvez jogar a confirmação da gravação em um menssageiro para ser gravado por um outro serviço para que ser der falha não comprometa a requisição ?)

- Redirecionar URL - GET - 
  Request: urlEncurtada.com/hash
  Response: 302
  Logica: deve estar preparado para inclusão de métricas, com fila RabbitMQ/Kafka

FRAMEWORK:
Spring: boot;

Banco:
Nunca mexi com NoSql mas acho que é a melhor alternativa, procuraria algo do DynamoDB ou Redis, subindo em um podman

Pergunta: Terá métrica ?

Ai eu começaria a desenvolver as apis pq eu não tenho experiencia com system design, mas certeza que eu mexendo eu ia ver algo que eu precisava ter definido

----------------------------------------------------------

Fase 1 (MVP):
Spring Boot + H2 Database (banco em memória para facilitar).
Entenda a lógica de conversão: ID 1 vira a, ID 2 vira b... ID 62 vira 10. (Pesquise "Base62 conversion Java").
Faça funcionar o POST e o GET.

Fase 2 (Persistência Real):
Suba um PostgreSQL no Podman.
Conecte o Spring nele.

Fase 3 (Performance - Onde entra o System Design):
Suba um Redis no Podman.
Adicione o cache.


----------------------------------------------------------------
### 1. Fase 1: O MVP (Lógica Core)

* **Objetivo:** Fazer o algoritmo funcionar sem infraestrutura pesada.
* **Tech:** Java 17 + Spring Boot + H2 (Banco em memória).
* **Desafio de Design:** Implementar o **Algoritmo Base62**.
* **Entregável:** Uma API que encurta (`POST`) e redireciona (`GET`), mas perde os dados se reiniciar.

### 2. Fase 2: Persistência (A "Verdade")

* **Objetivo:** Garantir que os links durem para sempre.
* **Tech:** PostgreSQL (via Docker/Podman).
* **Desafio de Design:** Modelagem de dados relacional e uso de **Sequences/Auto-increment** para alimentar o Base62.
* **Entregável:** O sistema mantém os links mesmo após reiniciar o servidor.

### 3. Fase 3: Performance (Cache-Aside)

* **Objetivo:** Reduzir a latência de leitura (Redirecionamento).
* **Tech:** Redis (via Docker/Podman).
* **Desafio de Design:** Implementar o padrão **Cache-Aside** (Ler Cache  Se não achar, Ler Banco  Salvar Cache).
* **Entregável:** O redirecionamento acontece em milissegundos sem bater no banco SQL repetidamente.

### 4. Fase 4: Assincronismo (Escalabilidade)

* **Objetivo:** Contar cliques (métricas) sem travar o usuário.
* **Tech:** RabbitMQ (via Docker/Podman).
* **Desafio de Design:** **Desacoplamento**. A API dispara um evento ("Fire-and-Forget") para a fila e redireciona imediatamente. Um *Worker* processa a contagem depois.
* **Entregável:** Sistema resiliente; se o serviço de métricas cair, o encurtador continua funcionando.