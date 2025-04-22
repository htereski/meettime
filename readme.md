# 📡 Integração HubSpot API - MeetTime

Este projeto foi desenvolvido em Java com Spring Boot e realiza integração com a API do HubSpot, permitindo a criação de contatos através de uma chamada autenticada.

---

## ⚙️ Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring AOP
- Bucket4j
- Lombok
- Log4j2
- Maven
- Ngrok (para desenvolvimento local com webhooks)

---

## 🧾 Obtendo `client_id` e `client_secret`

1. Acesse [HubSpot Developer Portal](https://developers.hubspot.com/)
2. Vá em **Apps > Create app**
3. Preencha o nome e outras informações básicas
4. Na aba **Auth**, selecione `OAuth 2.0`
5. Adicione os escopos necessários
6. Após salvar, você verá o `client_id` e o `client_secret`

---

## 🔐 Permissões (Escopos) do HubSpot

Para que sua aplicação tenha acesso à criação e leitura de contatos e possa receber webhooks, ela precisa dos seguintes **escopos (scopes)**:

- `crm.objects.contacts.read`
- `crm.objects.contacts.write`

> ✅ Configure esses escopos no [painel de apps do HubSpot](https://developers.hubspot.com/apps) ao criar sua aplicação.

---


## 🛠️ Configuração das Variáveis de Ambiente

Crie um arquivo `application.properties` com base no `application.example.properties` e adicione as seguintes variáveis:

`client_id`=seu_client_id

`client_secret`=seu_client_secret

`scope`=crm.objects.contacts.read crm.objects.contacts.write

`auth_url`=https://app.hubspot.com/oauth/authorize

`redirect_uri`=http://localhost:8080/oauth/hubspot/callback

`token_url`=https://api.hubapi.com/oauth/v1/token

`hubspot.endpoint.create.contact`=https://api.hubapi.com/crm/v3/objects/contacts

`hubspot.api.rate.limit`=100

Substitua os valores de `client_id` e `client_secret`

## 🌐 Executando o Ngrok para Webhooks
Para testes locais com webhooks do HubSpot, é necessário expor sua aplicação local com o Ngrok:

1. Crie uma conta no site do Ngrok, e siga o passo a passo para configurar na sua máquina.

2. Abra o arquivo `ngrok.exe` na raiz do projeto pelo explorador de arquivos do Windows

3. Com o arquivo aberto, execute:

``
ngrok http 8080
``

4. Copie a URL HTTPS gerada (ex: https://cadc-2804-14c-87e2-83df-3591-b6a7-6750-df77.ngrok-free.app)

5. No painel do seu app no HubSpot, vá em Webhooks e adicione essa URL com o endpoint para os eventos que deseja receber `/hubspot/contact/created`

6. Exemplo de URL completa para o webhook:

``
https://cadc-2804-14c-87e2-83df-3591-b6a7-6750-df77.ngrok-free.app/hubspot/contact/created
``

7. Nesse endpoint chegará o webhook
---

## 🔁 Executando o projeto

1. Rode o projeto

2. Abra o navegador e acesse o endpoint `http:localhost:8080/oauth/hubspot/redirect` e siga o fluxo

3. Após isso, faça uma chamada no Postman com o verbo `POST` para o endpoint `http:localhost:8080/contacts/create` com o seguinte `body`:  
```
{
    "email": "email@teste.com",
    "firstname": "Jane",
    "lastname": "Doe",
    "phone": "(555) 555-5555",
    "company": "HubSpot",
    "website": "hubspot.com",
    "lifecyclestage": "marketingqualifiedlead"
}
```
## 🧠 Decisões Técnicas e Justificativas

✅ Linguagem e Framework
- Java 21 foi escolhido por ser a versão LTS mais recente, oferecendo estabilidade para ambientes de produção e acesso às novas funcionalidades da linguagem, como padrões de correspondência mais avançados, melhorias em desempenho e recursos modernos para programação funcional e orientação a objetos.

- Spring Boot foi selecionado por sua robustez, produtividade e ecossistema consolidado para o desenvolvimento de APIs RESTful. A escolha também se justifica pela modularização limpa, e afinidade do desenvolver com o desenvolvimento.

⚙️ Rate Limiting com Bucket4j
- O Bucket4j foi escolhido pela sua simplicidade e eficiência no controle de requisições, evitando sobrecarga no consumo da API do HubSpot, que possui limites por segundo e por dia.

- Implementado via AOP para facilitar a reutilização do controle sem poluir os endpoints.

🪝 Webhooks e Ngrok
- A utilização do Ngrok permite expor a aplicação localmente para que o HubSpot consiga enviar eventos via webhook durante o desenvolvimento e testes.

- Essa estratégia evita a necessidade de deploy em ambiente externo apenas para testes.

📦 Outras Bibliotecas
- Lombok: Reduz boilerplate de código, como getters, setters e construtores.

- Log4j2: Para geração de logs com nível de detalhamento controlável e melhor desempenho.


## 🚀 Melhorias Futuras

1. Persistência de Tokens e Contatos
- Atualmente os tokens são armazenados apenas em memória.
- Implementar persistência em banco de dados garantiria suporte à reinicialização do serviço.

2. Refresh automático do Access Token
- O sistema pode ser estendido para atualizar automaticamente o access_token usando o refresh_token, garantindo que requisições nunca falhem por token expirado.

3. Documentação com Swagger
- Expor os endpoints REST com Swagger ajudaria a desenvolver e testar com mais facilidade.