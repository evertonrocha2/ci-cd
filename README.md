# DevCalc API

API REST desenvolvida em Java para operações matemáticas simples, criada para demonstrar práticas de CI/CD com GitHub Actions.

## Objetivo do Projeto

Este projeto tem como objetivo:
- Disponibilizar uma API REST simples com operações matemáticas básicas
- Demonstrar a implementação de pipelines de CI/CD com GitHub Actions
- Aplicar práticas de desenvolvimento com testes automatizados
- Implementar integração e entrega contínua

## Tecnologias Utilizadas

- **Java 11** - Linguagem de programação
- **Javalin 5.6.3** - Framework web minimalista para Java
- **Maven** - Ferramenta de build e gerenciamento de dependências
- **JUnit 5** - Framework de testes unitários
- **GitHub Actions** - Plataforma de CI/CD

## Estrutura do Projeto

```
devcalc-api/
├── .github/
│   └── workflows/
│       ├── hello.yml          # Pipeline simples de demonstração
│       └── ci.yml             # Pipeline completo de CI/CD
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
├── evidencias/            # Capturas de tela do processo
├── pom.xml               # Configuração Maven
├── .gitignore           # Arquivos ignorados pelo Git
└── README.md            # Este arquivo
```

## Endpoints da API

A API expõe os seguintes endpoints:

### GET /
Retorna informações sobre a API e endpoints disponíveis.

**Resposta:**
```json
{
  "message": "DevCalc API está funcionando!",
  "info": "Use os endpoints: /add, /subtract, /multiply, /divide com parâmetros a e b"
}
```

### GET /add
Soma dois números.

**Parâmetros:**
- `a` (obrigatório): primeiro operando
- `b` (obrigatório): segundo operando

**Exemplo:**
```
GET /add?a=10&b=5
```

**Resposta:**
```json
{
  "a": 10.0,
  "b": 5.0,
  "result": 15.0
}
```

### GET /subtract
Subtrai dois números.

**Exemplo:**
```
GET /subtract?a=10&b=5
```

**Resposta:**
```json
{
  "a": 10.0,
  "b": 5.0,
  "result": 5.0
}
```

### GET /multiply
Multiplica dois números.

**Exemplo:**
```
GET /multiply?a=10&b=5
```

**Resposta:**
```json
{
  "a": 10.0,
  "b": 5.0,
  "result": 50.0
}
```

### GET /divide
Divide dois números.

**Exemplo:**
```
GET /divide?a=10&b=5
```

**Resposta:**
```json
{
  "a": 10.0,
  "b": 5.0,
  "result": 2.0
}
```

**Erro (divisão por zero):**
```json
{
  "error": "Divisão por zero não é permitida"
}
```

## Executando Localmente

### Pré-requisitos

- Java 11 ou superior
- Maven 3.6 ou superior

### Clone o repositório

```bash
git clone https://github.com/SEU_USUARIO/devcalc-api.git
cd devcalc-api
```

### Compile o projeto

```bash
mvn clean install
```

### Execute os testes

```bash
mvn test
```

### Execute a aplicação

```bash
mvn exec:java -Dexec.mainClass="com.devcalc.App"
```

Ou execute o JAR gerado:

```bash
java -jar target/devcalc-api.jar
```

A aplicação estará disponível em: `http://localhost:7000`

### Testando os endpoints

```bash
# Teste de adição
curl "http://localhost:7000/add?a=10&b=5"

# Teste de subtração
curl "http://localhost:7000/subtract?a=10&b=5"

# Teste de multiplicação
curl "http://localhost:7000/multiply?a=10&b=5"

# Teste de divisão
curl "http://localhost:7000/divide?a=10&b=5"
```

## Workflows CI/CD

### hello.yml - Pipeline Inicial

Pipeline simples que demonstra o funcionamento básico do GitHub Actions.

**Gatilhos:**
- Push na branch `main`
- Pull requests para `main`

**Ações:**
- Exibe mensagem "Pipeline iniciado com sucesso"
- Mostra informações do evento

### ci.yml - Pipeline Completo (TP1 + TP2)

Pipeline completo de Integração e Entrega Contínua com melhorias do TP2.

**Gatilhos:**
- Push na branch `main` (apenas em alterações em `src/**`, `pom.xml` ou workflows)
- Pull requests para `main` (apenas em alterações em `src/**` ou `pom.xml`)
- Execução manual via `workflow_dispatch` com parâmetros customizáveis

**Parâmetros de Execução Manual:**
- `environment`: Escolha do ambiente (development, staging, production)
- `run_tests`: Booleano para executar ou não os testes automatizados
- `run_lint`: Booleano para executar ou não a análise de código

**Jobs:**

1. **checkout** - Realiza checkout do código e disponibiliza como artefato
2. **lint-and-test** - **[NOVO TP2]** Chama workflow reutilizável para lint e testes
3. **code-quality** - **[NOVO TP2]** Análise de código com Action do Marketplace (Checkstyle)
4. **build** - Configura Java 11 e compila o projeto com Maven
5. **package** - Gera o JAR executável da aplicação
6. **deploy** - Simula o deploy da aplicação

**Dependências:**
- `lint-and-test` e `code-quality` dependem de `checkout`
- `build` depende de `checkout`
- `package` depende de `build` e `lint-and-test`
- `deploy` depende de `build`, `lint-and-test` e `package`

### lint-and-test.yml - Workflow Reutilizável (TP2)

**[NOVIDADE TP2]** Workflow modular e reutilizável para análise de código e testes.

**Tipo:** Workflow Reutilizável (`workflow_call`)

**Parâmetros de Entrada:**
- `java-version`: Versão do Java (padrão: '11')
- `run-lint`: Executar análise de código (padrão: true)
- `run-tests`: Executar testes automatizados (padrão: true)

**Jobs:**

1. **lint** - Executa Checkstyle e gera relatórios de qualidade de código
2. **test** - Executa testes automatizados e gera relatórios de cobertura

**Vantagens:**
- Reutilizável em múltiplos workflows
- Parametrizável para diferentes cenários
- Modularização do pipeline principal
- Facilita manutenção e evolução

### test-error.yml - Pipeline de Teste de Erro (TP2)

**[NOVIDADE TP2]** Workflow criado para demonstrar depuração de erros.

**Propósito:** Educacional - mostra como identificar e corrigir falhas no pipeline

**Conteúdo:**
- Comando intencionalmente inválido para provocar erro
- Demonstra uso da aba Actions para debugging

## Instruções para Evidências

Para documentar o processo, capture as seguintes telas:

### 1. Estruturação inicial
- Comandos `git init`, `git remote add`, `git add`, `git commit`, `git push`
- Repositório criado no GitHub

### 2. Desenvolvimento
- Código do `CalculatorService.java`
- Código do `App.java`
- Código do `CalculatorServiceTest.java`
- Execução dos testes: `mvn test` (com resultados)

### 3. Workflows
- Arquivo `.github/workflows/hello.yml`
- Execução do workflow `hello.yml` na aba Actions
- Arquivo `.github/workflows/ci.yml`
- Execução completa do workflow `ci.yml`
- Grafo de dependências dos jobs
- Logs de cada job individual

### 4. Testes de gatilhos
- Alteração em `README.md` (pipeline NÃO executado)
- Alteração em arquivo dentro de `src/` (pipeline executado)

### 5. Pull Request e execução manual
- Pull request aberto com CI rodando
- Execução manual via workflow_dispatch

Organize todas as capturas na pasta `evidencias/` com nomes descritivos.

## Comandos Úteis

```bash
# Compilar o projeto
mvn clean compile

# Executar testes
mvn test

# Compilar e executar testes
mvn clean install

# Gerar JAR
mvn package

# Limpar build anterior
mvn clean

# Ver dependências
mvn dependency:tree

# Executar aplicação
java -jar target/devcalc-api.jar
```

## Conceitos Aplicados

- **Integração Contínua (CI):** Automação de build e testes a cada commit
- **Entrega Contínua (CD):** Preparação automática para deploy
- **Testes Automatizados:** Validação da qualidade do código
- **Versionamento:** Controle de versões com Git
- **Pipeline as Code:** Workflows definidos em YAML
- **Artefatos:** Preservação de builds e relatórios
- **Jobs e Dependencies:** Orquestração de tarefas paralelas e sequenciais
- **Actions do Marketplace:** Reutilização de componentes da comunidade
- **Workflows Reutilizáveis:** Modularização e reuso de pipelines
- **Análise de Código:** Verificação de qualidade com Checkstyle

## Depuração de Erros no Pipeline

Durante o desenvolvimento do TP2, provocamos intencionalmente um erro no pipeline para exercitar o processo de depuração. 

### Como Identificar Problemas

1. **Aba Actions do GitHub:**
   - Acesse a aba "Actions" no repositório
   - Identifique execuções com falha vs sucesso
   - Clique na execução com falha para ver detalhes

2. **Navegação pelos Logs:**
   - Cada job aparece no painel lateral esquerdo
   - Jobs com erro aparecem em vermelho
   - Clique no job com problema
   - Expanda os steps para ver onde ocorreu a falha
   - O log mostra a mensagem de erro específica

3. **Ferramentas Utilizadas:**
   - **Interface web do GitHub Actions:** Visualização hierárquica dos jobs e steps
   - **Logs detalhados:** Cada step mostra stdout/stderr completo
   - **Timestamps:** Identificação de quanto tempo cada step levou
   - **Anotações:** Erros são destacados automaticamente

### Erro Provocado e Resolução

**Erro Intencional:**
Criamos um workflow `test-error.yml` com um comando inválido:
```yaml
- name: Comando inválido que vai falhar
  run: comando_invalido_inexistente
```

**Sintomas:**
- Pipeline falhou com status code 127 (command not found)
- Mensagem de erro: "comando_invalido_inexistente: command not found"
- Steps subsequentes não foram executados

**Como Identificamos:**
1. Aba Actions mostrou erro na execução
2. Clicamos no workflow "Test Error Pipeline"
3. Expandimos o step "Comando inválido que vai falhar"
4. Log mostrou claramente: `/bin/sh: 1: comando_invalido_inexistente: not found`
5. Exit code 127 indicou comando não encontrado

**Correção:**
Removemos o comando inválido e substituímos por um comando válido ou removemos o workflow de teste após documentar o processo.

## Comparação: Execução Automática vs Manual

### Execução por Push (Automática)

Quando fazemos `git push` para a branch `main`, o pipeline é acionado automaticamente se os arquivos modificados estiverem nos paths configurados (`src/**`, `pom.xml`, etc.).

**Características:**
- **Gatilho:** `on: push`
- **Contexto:** `github.event_name = 'push'`
- **Parâmetros:** Não aceita parâmetros personalizados
- **Execução:** Roda todos os jobs configurados
- **Uso típico:** CI contínua durante desenvolvimento

### Execução Manual (workflow_dispatch)

Quando clicamos em "Run workflow" na aba Actions, podemos escolher parâmetros antes da execução.

**Características:**
- **Gatilho:** `on: workflow_dispatch`
- **Contexto:** `github.event_name = 'workflow_dispatch'`
- **Parâmetros:** Aceita inputs customizados (`run_tests`, `run_lint`, `environment`)
- **Execução:** Pode executar seletivamente jobs com base nos parâmetros
- **Uso típico:** Testes específicos, deploys controlados, debugging

### Observações Práticas

Após realizar execuções de ambos os tipos, observamos:

1. **Flexibilidade:** A execução manual permite testar apenas componentes específicos (ex: só lint, ou só testes)
2. **Velocidade:** Execuções manuais seletivas são mais rápidas quando não precisamos de todo o pipeline
3. **Controle:** workflow_dispatch é ideal para ambientes controlados (staging, production)
4. **Filtros na Aba Actions:** É possível filtrar execuções por tipo de gatilho (Event)
5. **Histórico:** Execuções manuais aparecem marcadas com o ícone de "play" manual
6. **Parâmetros Visíveis:** Na interface, é possível ver quais parâmetros foram passados em execuções manuais

**Exemplo de Uso:**
- **Push automático:** Desenvolvedor faz commit → Pipeline valida tudo automaticamente
- **Execução manual:** Antes de merge, rodar só os testes sem rebuild completo para economizar tempo

## Novidades do TP2

### Melhorias Implementadas

#### 1. Action do GitHub Marketplace
Integrada a action `jwgmeligmeyling/checkstyle-github-action@v1.2` para análise automática de código Java com Checkstyle. Esta action cria anotações visuais diretamente no código quando problemas de estilo são detectados.

#### 2. Documentação Conceitual
Criado o arquivo `actions-explicacao.md` que explica em detalhes:
- Diferença entre workflows e actions
- Estrutura interna de uma action (`action.yml`)
- Como inputs e outputs funcionam
- Exemplos práticos com as actions utilizadas no projeto

#### 3. Workflow Reutilizável
Criado `lint-and-test.yml` como workflow reutilizável que:
- Pode ser chamado por outros workflows
- Aceita parâmetros para customização
- Modulariza a lógica de testes e lint
- Facilita manutenção e reuso

#### 4. Execução Manual Parametrizável
O workflow `ci.yml` agora aceita execução manual com parâmetros:
- `run_tests`: Controla se os testes serão executados
- `run_lint`: Controla se a análise de código será executada
- `environment`: Escolha do ambiente de deploy

Jobs usam expressões condicionais (`if:`) para respeitar esses parâmetros.

#### 5. Prática de Debugging
Criado workflow `test-error.yml` com erro intencional para:
- Praticar navegação nos logs do GitHub Actions
- Identificar causas de falhas
- Documentar processo de correção

#### 6. Monitoramento e Análise
Implementado sistema de monitoramento do pipeline com documentação detalhada comparando diferentes modos de execução.

### Arquivos Adicionados no TP2

```
.
├── .github/workflows/
│   ├── lint-and-test.yml     # Workflow reutilizável
│   └── test-error.yml         # Workflow para teste de erro
├── actions-explicacao.md      # Documentação conceitual
├── checkstyle.xml            # Configuração do Checkstyle
└── README.md                 # Atualizado com todas as novidades
```

### Arquivos Modificados no TP2

- `pom.xml` - Adicionado plugin maven-checkstyle-plugin
- `.github/workflows/ci.yml` - Refatorado para usar workflow reutilizável e parâmetros
- `README.md` - Documentação completa das melhorias

## Contribuindo

1. Faça fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## Licença

Este projeto foi desenvolvido para fins educacionais.

## Autor

Desenvolvido como parte da disciplina de CI/CD.

---

**Nota:** Este é um projeto educacional para demonstração de práticas de CI/CD com GitHub Actions.
