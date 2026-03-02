# DevCalc API

## Objetivo

Demonstrar a implementação de pipelines CI/CD com GitHub Actions, incluindo workflows reutilizáveis e integração com actions do Marketplace.

## Tecnologias

- Java 11
- Javalin 5.6.3
- Maven
- JUnit 5
- GitHub Actions
- Checkstyle

## Estrutura do Projeto

```
devcalc-api/
├── .github/
│   └── workflows/
│       ├── hello.yml          # Pipeline simples de demonstração
│       ├── ci.yml             # Pipeline completo de CI/CD
│       └── lint-and-test.yml  # Workflow reutilizável para lint e testes
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── devcalc/
│   │               ├── App.java                    # Aplicação principal
│   │               └── service/
│   │                   └── CalculatorService.java  # Lógica de negócio
│   └── test/
│       └── java/
│           └── com/
│               └── devcalc/
│                   └── service/
│                       └── CalculatorServiceTest.java  # Testes unitários
├── pom.xml               # Configuração Maven
├── .gitignore           # Arquivos ignorados pelo Git
├── actions-explicacao.md # Documentação sobre workflows vs actions
└── README.md            # Este arquivo
```

## Endpoints da API

- `GET /` - Informações sobre a API
- `GET /add?a=10&b=5` - Soma dois números
- `GET /subtract?a=10&b=5` - Subtrai dois números
- `GET /multiply?a=10&b=5` - Multiplica dois números
- `GET /divide?a=10&b=5` - Divide dois números

Todos os endpoints retornam JSON com os valores de entrada e resultado.

## Executando Localmente

**Pré-requisitos:** Java 11 e Maven 3.6

```bash
# Compilar e testar
mvn clean install

# Executar aplicação
java -jar target/devcalc-api.jar
```

Aplicação disponível em: `http://localhost:7000`

## Workflows CI/CD

### hello.yml
Pipeline simples de demonstração que exibe mensagens quando executado.

### ci.yml - Pipeline Principal

**Gatilhos:**
- Push na main (apenas alterações em `src/**`, `pom.xml` ou workflows)
- Pull requests para main
- Execução manual com inputs `run_tests` e `run_lint`

**Jobs:**
1. **lint-and-test** - Chama workflow reutilizável (testes + checkstyle)
2. **build** - Compila o projeto
3. **package** - Gera JAR e salva como artefato
4. **deploy** - Simula deploy

**Dependências:** build e lint-and-test rodam em paralelo, depois package, e por último deploy.

### lint-and-test.yml - Workflow Reutilizável

Workflow chamado por `ci.yml` usando `workflow_call`.

**Jobs:**
- **test** - Testes unitários com JUnit 5
- **lint** - Análise de código com Maven Checkstyle Plugin

**Actions do Marketplace utilizadas:**
- `actions/checkout@v4` - Checkout do código
- `actions/setup-java@v4` - Configuração do Java 11
- `actions/upload-artifact@v4` - Upload de relatórios e JAR

## Comandos Maven

```bash
mvn clean install    # Compilar e testar
mvn test            # Executar testes
mvn package         # Gerar JAR
```

## Monitoramento e Depuração

### Status Badge
Badge no topo mostra o status da última execução (verde = sucesso, vermelho = falha).

### Diferenças entre Push Automático e Execução Manual

**Push Automático:**
- Dispara quando há commit em `src/**` ou `pom.xml`
- Todos os jobs executam automaticamente
- Validação completa do código

**Execução Manual (workflow_dispatch):**
- Disparado pelo botão "Run workflow" na aba Actions
- Permite escolher executar só testes, só lint, ambos ou nenhum
- Útil para validações rápidas e específicas

**Diferença prática:** Execuções manuais dão controle sobre o que rodar, enquanto push automático garante validação completa sempre.

## Depuração de Pipeline - Registro de Erro Provocado

**Erro provocado:** Alteração do comando `mvn clean compile` para `mvn clean compileeee` no job build.

**Identificação:**
1. Aba Actions mostrou workflow com status vermelho
2. Clique no workflow → job "build" com erro
3. Logs do step "Compilar projeto" exibiram: `Error: Unknown lifecycle phase "compileeee"`

**Resolução:** Corrigido o comando no arquivo `ci.yml` e feito novo commit. Pipeline voltou ao status verde.

**Ferramentas usadas:** Interface Actions (visualização de workflows), navegação por jobs/steps, e logs detalhados.
