# Instruções de Configuração no GitHub

Este guia descreve como configurar o repositório no GitHub e executar os workflows do TP2.

## 📦 Passos Iniciais

### 1. Criar Repositório no GitHub

1. Acesse https://github.com
2. Clique em "New repository" ou use o botão "+"
3. Configure o repositório:
   - **Nome:** `devcalc-api` (ou outro nome de sua preferência)
   - **Visibilidade:** Public ou Private
   - **Não inicialize** com README, .gitignore ou licença (já existem localmente)
4. Clique em "Create repository"

### 2. Conectar Repositório Local ao GitHub

No terminal, execute os seguintes comandos na pasta do projeto:

```bash
# Inicializar repositório Git (se ainda não foi feito)
git init

# Adicionar remote do GitHub (substitua SEU_USUARIO e SEU_REPOSITORIO)
git remote add origin https://github.com/SEU_USUARIO/SEU_REPOSITORIO.git

# Adicionar arquivos ao staging
git add .

# Fazer commit inicial
git commit -m "Initial commit: DevCalc API com pipelines CI/CD"

# Enviar para o GitHub
git push -u origin main
```

Se você usar `master` como branch padrão, ajuste os workflows para usar `master` ao invés de `main`.

### 3. Verificar Workflows na Aba Actions

1. Acesse seu repositório no GitHub
2. Clique na aba "Actions"
3. Você verá os workflows:
   - **Hello Pipeline** - já executado pelo push
   - **CI/CD Pipeline** - já executado pelo push

## 🔧 Usando as Funcionalidades do TP2

### Execução Automática (Push)

Toda vez que você fizer push de alterações nos seguintes arquivos, o pipeline será executado automaticamente:
- Arquivos em `src/**`
- `pom.xml`
- Arquivos em `.github/workflows/**`

**Teste:**
1. Faça uma alteração simples em `src/main/java/com/devcalc/App.java`
2. Commit e push
3. Veja a execução automática na aba Actions

### Execução Manual com Inputs

1. Na aba "Actions", clique em "CI/CD Pipeline" no menu lateral esquerdo
2. Clique no botão "Run workflow" (canto superior direito)
3. Selecione a branch `main`
4. Configure os inputs:
   - ✅ **Executar testes?** - marque para rodar testes
   - ✅ **Executar análise de código (lint)?** - marque para rodar Checkstyle
5. Clique em "Run workflow"

**Experimente diferentes combinações:**
- Apenas testes (desmarque lint)
- Apenas lint (desmarque testes)
- Ambos (ambos marcados)
- Nenhum (ambos desmarcados - útil para testar apenas build e deploy)

### Testando Pull Requests

1. Crie uma nova branch:
   ```bash
   git checkout -b feature/nova-funcionalidade
   ```

2. Faça alterações em arquivos de código

3. Commit e push da branch:
   ```bash
   git add .
   git commit -m "Adiciona nova funcionalidade"
   git push -u origin feature/nova-funcionalidade
   ```

4. No GitHub, crie um Pull Request para `main`

5. O workflow será executado automaticamente no contexto do PR

6. Checkstyle comentará no PR se encontrar problemas de estilo

## 🐛 Provocando e Corrigindo Erro

### Como Provocar o Erro

1. Edite `.github/workflows/ci.yml`
2. No job `build`, altere a linha:
   ```yaml
   - name: Compilar projeto
     run: mvn clean compile
   ```
   Para:
   ```yaml
   - name: Compilar projeto
     run: mvn clean compileeee
   ```

3. Commit e push:
   ```bash
   git add .github/workflows/ci.yml
   git commit -m "Provoca erro intencional no pipeline"
   git push
   ```

### Como Identificar o Erro

1. Vá para a aba "Actions"
2. Veja que o último workflow tem um ❌ vermelho
3. Clique no workflow com falha
4. Clique no job "build" que está marcado como falha
5. Expanda o step "Compilar projeto"
6. Leia os logs de erro:
   ```
   [ERROR] Unknown lifecycle phase "compileeee". You must specify a valid lifecycle phase...
   ```

### Como Corrigir

1. Corrija o arquivo `.github/workflows/ci.yml` voltando ao comando correto:
   ```yaml
   - name: Compilar projeto
     run: mvn clean compile
   ```

2. Commit e push da correção:
   ```bash
   git add .github/workflows/ci.yml
   git commit -m "Corrige comando Maven no pipeline"
   git push
   ```

3. Verifique que o workflow agora tem ✅ verde

## 📊 Status Badge

O badge no README.md é gerado automaticamente pelo GitHub usando a URL:

```markdown
![CI/CD Pipeline](https://github.com/SEU_USUARIO/SEU_REPOSITORIO/actions/workflows/ci.yml/badge.svg)
```

**Importante:** Substitua `SEU_USUARIO` e `SEU_REPOSITORIO` pelos valores corretos!

## 📈 Monitorando Execuções

### Filtrar por Tipo de Evento

Na aba Actions, use os filtros:
- **Event:** Filtre por `push`, `workflow_dispatch`, `pull_request`
- **Status:** Filtre por `success`, `failure`, `cancelled`
- **Branch:** Filtre por branch específica
- **Actor:** Filtre por quem executou

### Comparar Execuções

1. Selecione uma execução por **push**
2. Observe que foi triggered automaticamente
3. Todos os jobs executam (test e lint sempre rodam)

4. Selecione uma execução por **workflow_dispatch**
5. Observe que foi triggered manualmente por um usuário
6. Você pode ver os inputs selecionados
7. Jobs condicionais podem ter sido pulados

## ✅ Validação Final

Antes de submeter o TP2, verifique:

- [ ] Repositório criado no GitHub
- [ ] Código-fonte commitado e enviado
- [ ] Workflow `hello.yml` executando com sucesso
- [ ] Workflow `ci.yml` executando com sucesso
- [ ] Workflow reutilizável `lint-and-test.yml` criado e funcionando
- [ ] Action do Marketplace (Checkstyle) integrada e executando
- [ ] Execução manual testada com diferentes inputs
- [ ] Erro provocado, identificado e corrigido
- [ ] Status badge aparecendo no README.md
- [ ] Documento `actions-explicacao.md` criado
- [ ] Evidências capturadas e organizadas

## 🎓 Critérios de Avaliação Atendidos

Este projeto demonstra:

✅ **Incorporação de action externa do GitHub Actions Marketplace**
- Action `nikitasavinov/checkstyle-action@0.6.0` referenciada corretamente
- Versão especificada com `@0.6.0`
- Parâmetros de configuração inseridos (`github_token`, `reporter`, `checkstyle_config`, etc.)

✅ **Compreensão conceitual de workflows vs actions**
- Documento `actions-explicacao.md` explica estrutura do `action.yml`
- Papéis de inputs/outputs claramente descritos
- Uso do comando `uses:` explicado com exemplo prático

✅ **Workflow modularizado corretamente estruturado**
- Arquivo `lint-and-test.yml` criado com `workflow_call`
- Separação clara de responsabilidades
- Arquivo `ci.yml` invoca o workflow reutilizável com `uses:`

✅ **Configuração de workflows manuais**
- `workflow_dispatch` configurado em `ci.yml`
- Inputs booleanos `run_tests` e `run_lint` definidos
- Expressões condicionais `if:` usadas para controlar execução de jobs

✅ **Domínio na leitura e interpretação de logs**
- Erro provocado intencionalmente (comando Maven inválido ou `exit 1`)
- Processo de identificação documentado no README.md
- Ferramentas da interface Actions utilizadas (navegação jobs → steps → logs)
- Problema corrigido com sucesso

✅ **Monitoramento via status badges**
- Badge configurado no README.md
- Compreensão das diferenças entre push automático e workflow_dispatch documentada
- Observações sobre filtros e tipos de gatilhos incluídas
