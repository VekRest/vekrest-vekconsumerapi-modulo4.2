# 🧬 Projeto VekRest - VekConsumer - Módulo 4

Consumer VekRest: consumer kafka via Spring Boot com Docker e Maven. **Módulo 4 / Consumer 2**

> ATENÇÃO: VÁ ATÉ OS REPOSITÓRIOS DAS PARTES DO MÓDULO 4 E SIGA AS INSTRUÇÕES DE EXECUÇÃO DO README DE CADA APLICAÇÃO PARA RODAR A APLICAÇÃO COMPLETA!

## 🧩 PARTES DO MÓDULO 4
| Aplicação      | Descrição                                                                 | Link                                                                                                |
|----------------|---------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------|
| VekProducer    | Producer (este projeto) - Producer Kafka                                  | [Repositório VekProducer Módulo 4](https://github.com/VekRest/vekrest-vekproducer-modulo4)          |
| VekConsumer    | Consumer - Consumer Kafka simples                                         | [Repositório VekConsumer Módulo 4](https://github.com/VekRest/vekrest-vekconsumer-modulo4.2)                                                                                    
| VekConsumerAPI | Consumer REST - Consumer Kafka com comunicação com a API REST VekSecurity | Este Repositório 

> Este projeto depende das outras duas aplicações (VekProducer e VekConsumer) para funcionar corretamente.
> Faça o build no docker das outras aplicações ou utilize as imagens do DockerHub para rodar os containers necessários.
> Por último, suba os containers do projeto VekProducer para completar o ambiente.

---

# 1.✨ Imagem Docker (DockerHub)

> A imagem desta aplicação é atualizada a cada nova tag ou pull request na [branch main](https://github.com/VekRest/vekrest-vekconsumerapi-modulo4.2/tree/main)

> Link da imagem no DockerHub: [vek03/vekrest-vekconsumerapi:latest](https://hub.docker.com/r/vek03/vekrest-vekconsumerapi)

> Utilize 3 containers Kafka para alta disponibilidade (kafka1, kafka2 e kafka3), um para cada Broker. Cada Broker possui 5 partições e 2 réplicas.

---

## 1.1 🧩 Containers necessários para rodar a aplicação:

| Container      | Imagem                               | Link                                                                                                                                           | 
|----------------|--------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| MongoDB        | `mongo:latest`                       | https://hub.docker.com/_/mongo                                                                                                                 |
| OpenSearch     | `opensearchproject/opensearch:2.4.0` | https://hub.docker.com/layers/opensearchproject/opensearch/2.4.0/images/sha256-c8681472b70d46e7de61fe770d288a972f84b3f122f3c74ca06ea525264b6fd5|
| Graylog        | `graylog/graylog:5.1.5`              | https://hub.docker.com/layers/graylog/graylog/5.1.5/images/sha256-3b6967572e88731eacfa661e6d7ca41da3e259bc5eb041e58fb10e4deb823dcb             |
| Zookeeper      | `confluentinc/cp-zookeeper:7.5.0`    | https://hub.docker.com/layers/confluentinc/cp-zookeeper/7.5.0/images/sha256-d18e7b3a81326dd278a5f2121b29a7f009582e0b0f5552165eb5efc83533a52b   |
| Kafka          | `confluentinc/cp-kafka:7.5.0`        | https://hub.docker.com/layers/confluentinc/cp-kafka/7.5.0/images/sha256-69022c46b7f4166ecf21689ab4c20d030b0a62f2d744c20633abfc7c0040fa80       |

---

## 1.2 ⚙ Variáveis de ambiente necessárias para rodar o container:

| Variável        | Descrição                        | Exemplo                                    |
|-----------------|----------------------------------|--------------------------------------------|
| `KAFKA_BROKERS` | Endereço do broker Kafka         | `kafka1:19092, kafka2:19093, kafka3:19094` |
| `GRAYLOG_HOST`  | Endereço do Graylog              | `graylog`                                  |
| `GRAYLOG_PORT`  | Porta do Graylog                 | `12201`                                    |

---

## 1.3 🐳 Como rodar o container

1️⃣ Para baixar a imagem do Docker Hub:
```bash
docker pull vek03/vekrest-vekconsumerapi:latest
```

2️⃣ Para rodar o container localmente:
```bash
docker run -d \
  --name vekconsumerapi \
    -e KAFKA_BROKERS=kafka1:19092, kafka2:19093, kafka3:19094 \
    -e GRAYLOG_HOST=graylog \
    -e GRAYLOG_PORT=12201 \
  vek03/vekrest-vekconsumerapi:latest
```

3️⃣ Alternativamente, você pode adicionar o serviço no seu docker-compose.yml local, descomentando ou adicionando o seguinte trecho:
```bash
services:
  vekconsumerapi:
    image: vek03/vekrest-vekconsumerapi:latest
    hostname: vekconsumerapi
    container_name: vekconsumerapi
    environment:
      KAFKA_BROKERS: kafka1:19092, kafka2:19093, kafka3:19094
      GRAYLOG_HOST: graylog
      GRAYLOG_PORT: 12201
    depends_on:
      mongodb:
        condition: service_healthy
      opensearch:
        condition: service_healthy
      graylog:
        condition: service_started
      zookeeper:
        condition: service_healthy
      kafka1:
        condition: service_healthy
      kafka2:
        condition: service_healthy
      kafka3:
        condition: service_healthy
```

4️⃣ Depois de adicionar o serviço em docker-compose.yml, suba os containers:
```bash
docker-compose up -d
```

---

## 📦 Instalação e Configuração do Ambiente

### 1️⃣ Clone o projeto na sua máquina e baixe as dependências:
```bash
# Clonar
git clone https://github.com/VekRest/vekrest-vekconsumerapi-modulo4.2.git

# Acesse a pasta do projeto
cd vekrest-vekconsumerapi-modulo4.2
````

### 2️⃣ Suba os containers necessários e Rode o projeto na sua IDE de preferência (ou via comando Maven)
```bash
# Suba os containers necessários (MongoDB, Redis, OpenSearch, Graylog)
docker-compose up -d

# Agora abra o projeto na sua IDE (IntelliJ, Eclipse, VSCode, etc) e rode a aplicação Spring Boot
# Ou, se preferir, rode via terminal com properties-local:
mvn spring-boot:run -pl spring -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=local"
```

### 3️⃣ (Opcional) Alternativamente, se quiser rodar via container localmente:
```bash
# Dentro da pasta do projeto:
mvn clean package -DskipTests

# Agora faça deploy no Docker local:
docker build -t vekrest/vekconsumerapi:latest .

# Descomente as últimas linhas do docker-compose.yml (relacionadas ao vekconsumerapi) e rode:
docker-compose up -d
```

> Ou execute o script .bat (executar_tudo.bat) na pasta .commands para automatizar o processo.

### 4️⃣ (Opcional) Caso deseje, pode rodar o SonarQube localmente

```bash
# Após configurar o pom.xml com as informações do Sonar em Properties:
mvn clean install sonar:sonar -Dsonar.token={TOKEN_SONAR}
```

---

## 📨 Como testar o Producer Kafka

> Com a aplicação rodando, você pode enviar mensagens para o tópico Kafka "client.updated"

### Exemplos de mensagens para enviar ao tópico Kafka

#### Exemplo de mensagem JSON
```json
{
  "name": "Vek",
  "birth": "2023-01-01",
  "address": {
    "cep": "03759040",
    "state": "SP"
  }
}
```

#### Exemplo de mensagem hexadecimal
```bash
# key
6b6579

# value
7b226e616d65223a2256656b222c226269727468223a22323032332d30312d3031222c2261646472657373223a7b22636570223a223033373539303430222c227374617465223a225350227d7d
```

### Endpoint REST para enviar mensagens via HTTP POST
> Depende do container VekProducer estar rodando corretamente
```bash
POST http://localhost:8083/vekrest/vekproducer/v1/client
```

---

## 🧩 Tecnologias Utilizadas

- **Spring Boot** → Framework Back-End
- **Java** → Linguagem de programação
- **Maven** → Build
- **Docker** → Containers e virtualização
- **Docker Hub** → Repositório de imagens Docker
- **Kafka** → Mensageria
- **Zookeeper** → Gerenciamento do Kafka
- **MongoDB** → Banco de Dados NoSQL
- **OpenSearch e Graylog** → Logs da Aplicação
- **SonarQube** → Qualidade
- **Github Actions** → CI/CD automatizado
- **.bat** → Scripts para automatizar processos no Windows

---

## ✅ Qualidade (SonarQube)

> Este projeto tem qualidade analisada pelo SonarQube Cloud. Verifique nos badges!

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-dark.svg)](https://sonarcloud.io/summary/new_code?id=vekconsumerapi)

---

## 📦 Esteira CI/CD Automatizada com Github Actions

> A esteira CI/CD deste projeto é automatizada via Github Actions. A cada tag criada a esteira é disparada.

### Para executar a Esteira pelo trigger:
```bash
# Exemplo: Cria a tag
git tag <version>

# Envia a tag para o repositório remoto
git push origin <version>
```

[![VekConsumerAPI CI/CD Workflow](https://github.com/VekRest/vekrest-vekconsumerapi-modulo4.2/actions/workflows/main.yml/badge.svg)](https://github.com/VekRest/vekrest-vekconsumerapi-modulo4.2/actions/workflows/main.yml)

---

## Postman Collection

> Link para download da coleção Postman utilizada nos testes da API: [Postman Collection VekRest](https://www.postman.com/aviation-pilot-88658184/workspace/my-workspace/folder/33703402-dad9baf5-9c1b-4010-a4c7-7ace385191fd?action=share&source=copy-link&creator=33703402&ctx=documentation)

---