# Diferença entre Workflows e Actions no GitHub Actions

## O que é um Workflow?

Pensa num **workflow** como uma receita de bolo completa. Ele é o "passo a passo" que você escreve para dizer ao GitHub: "quando isso acontecer, faça aquilo". Por exemplo: "quando eu fizer um push no código, roda os testes, compila e faz o deploy".

É aquele processo todo que você quer automatizar. Tipo assim:
- 📥 Alguém faz push de código novo
- ✅ Roda os testes pra ver se não quebrou nada
- 🔨 Compila o projeto
- 📦 Empacota tudo num arquivo executável
- 🚀 Faz deploy pra produção

Você coloca esses workflows em arquivos YAML dentro da pasta `.github/workflows/` do seu repositório. Cada arquivo é uma receita diferente que pode ser disparada por eventos como push, pull request, ou até manualmente.

**Resumindo:**
- É um arquivo YAML na pasta `.github/workflows/`
- Define **quando** algo deve acontecer (exemplo: "quando fizer push")
- Define **o que** fazer (exemplo: "rodar testes, depois fazer build")
- Só funciona no repositório onde você criou
- É tipo um "roteiro" completo de automação

## O que é uma Action?

Agora, uma **action** é como um ingrediente pronto que você pode usar na sua receita. Sabe quando você usa fermento em pó ao invés de fazer fermento do zero? É isso! Alguém já criou uma solução que você só precisa "chamar" no seu workflow.

**Exemplo na vida real:** 

Imagina que você quer que seu código Java seja analisado automaticamente pra ver se tá seguindo boas práticas. Você poderia:
- ❌ Escrever um script complexo do zero (trabalhoso!)
- ✅ Ou simplesmente usar uma action pronta do Marketplace que já faz isso

É literalmente copiar uma linha tipo `uses: checkstyle-action@v1` e pronto! A mágica acontece.

### De onde vêm as Actions?

Actions podem vir de três lugares:
- **GitHub Marketplace** - tipo uma loja de apps, cheio de actions criadas pela comunidade (tem milhares!)
- **Seu próprio repositório** - você pode criar suas próprias actions locais pra reutilizar
- **Repositórios privados** - da sua empresa, por exemplo (quando precisa de algo mais específico)

**Resumindo:**
- É um "pedaço de código reutilizável"
- Resolve um problema específico muito bem
- Você pode usar em vários workflows diferentes (seu e de outros projetos)
- Não precisa entender como funciona por dentro (só usar)
- É tipo um plugin ou extensão

## Como uma Action é estruturada por dentro: O arquivo action.yml

Toda action tem um arquivo chamado `action.yml` que funciona como um manual de instruções. Ele diz pro GitHub como usar aquela action. Vamos ver o que tem dentro:

### 1. Informações básicas
Tipo uma carteira de identidade da action:

```yaml
name: 'Nome da Action'
description: 'O que essa action faz'
author: 'Quem criou isso'
```

### 2. Inputs (entradas)
São os parâmetros que você passa pra action funcionar. É tipo configurar um eletrodoméstico antes de usar:

```yaml
inputs:
  github_token:
    description: 'Token do GitHub para autenticação'
    required: true  # Obrigatório! Sem isso não funciona
  reporter:
    description: 'Como você quer ver os resultados'
    required: false  # Opcional
    default: 'github-pr-review'  # Se você não passar nada, usa esse valor
```

### 3. Outputs (saídas)
É o que a action "devolve" depois que termina de rodar. Tipo o resultado que você pode usar nos próximos passos:

```yaml
outputs:
  result:
    description: 'O que aconteceu aqui'
```

### 4. Runs (como executar)
Aqui define se a action roda com JavaScript, Docker, ou é só um conjunto de comandos:

```yaml
runs:
  using: 'node16'  # Vai rodar com Node.js versão 16
  main: 'dist/index.js'  # Arquivo principal pra executar
```

## Exemplo Prático: Como usamos o Checkstyle no nosso projeto

No DevCalc, a gente usa uma action chamada **checkstyle-action** que verifica se nosso código Java está seguindo boas práticas de estilo. É tipo ter um revisor automático de código!

### Como chamamos essa action no nosso workflow

```yaml
- name: Análise de código com Checkstyle
  uses: nikitasavinov/checkstyle-action@0.6.0
  with:
    github_token: ${{ secrets.GITHUB_TOKEN }}
    reporter: github-pr-review
    level: warning
    checkstyle_config: google_checks.xml
    fail_on_error: false
```

### Entendendo cada parte:

**`uses: nikitasavinov/checkstyle-action@0.6.0`**

Aqui a gente tá dizendo: "Ó GitHub, usa essa action aqui pra mim!"

- `nikitasavinov/checkstyle-action` → É tipo o "endereço" da action no GitHub (igual quando você procura um app na loja)
- `@0.6.0` → É a versão específica que queremos usar (tipo quando você escolhe qual versão do WhatsApp instalar)

**`with:` - Configurando a action**

Essa parte é onde a gente passa as "configurações" pra action. É tipo quando você configura um app antes de usar:

- `github_token` → Uma senha especial pra action poder comentar no nosso código
- `reporter` → Como queremos ver os resultados (nesse caso, como comentário no Pull Request)
- `level` → Quão crítico os avisos precisam ser (aqui tá em "warning" - só avisos)
- `checkstyle_config` → Qual "régua" de qualidade usar (Google tem um guia de estilo famoso pra Java)
- `fail_on_error` → Se deve travar tudo quando achar um problema (colocamos `false` pra não ser tão rigoroso)

## Como isso funciona na prática?

É tipo uma conversa entre o workflow e a action:

**Workflow (ci.yml):** "Opa, preciso analisar o código Java aqui!"

**Action (checkstyle-action):** "Beleza, me passa o token e as configurações"

**Workflow:** "Toma aqui: token, configuração do Google, nível warning..."

**Action:** "Show! Vou analisar... Pronto! Achei 3 problemas, tá aqui o relatório"

**Workflow:** "Valeu! Vou pro próximo passo então"

## Comparando: Workflow vs Action

| O que é? | Workflow | Action |
|---------|----------|--------|
| **Analogia** | A receita completa | Um ingrediente pronto |
| **Onde fica** | Na pasta `.github/workflows/` do seu repo | GitHub Marketplace ou outro lugar |
| **Pra que serve** | Organizar todo o processo (quando e como fazer) | Fazer uma coisa específica bem feita |
| **Reutilização** | Só funciona nesse repositório | Pode usar em qualquer projeto |
| **Quem dispara** | Eventos (push, PR, manual, etc.) | Ninguém dispara direto - só o workflow chama ela |
| **Como usar** | GitHub executa automaticamente | Você chama com `uses:` dentro de um step |

## Resumindo tudo

Pensa assim: o **workflow** é tipo um chefe de cozinha que coordena tudo. Ele decide quando cozinhar, o que fazer primeiro, o que fazer depois. Já a **action** é tipo aquele processador de alimentos que você compra pronto - você só pluga e usa quando precisa picar legumes.

Você não precisa inventar a roda (criar tudo do zero). Use actions prontas quando alguém já resolveu o problema, e use workflows pra juntar tudo e fazer o processo completo funcionar do jeito que você precisa.

É isso que torna o GitHub Actions tão poderoso: você aproveita o trabalho de milhares de desenvolvedores que já criaram actions prontas, e só monta seu workflow do jeito que faz sentido pro seu projeto!
