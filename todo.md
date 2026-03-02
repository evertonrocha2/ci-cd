Introdução
Neste segundo Teste de Performance, você e sua equipe continuarão evoluindo o projeto DevCalc, a API Java com operações matemáticas básicas, agora com foco em aprofundar o uso do GitHub Actions. Depois de estruturar a base da aplicação e criar o pipeline inicial no TP1, é hora de tornar esse pipeline mais robusto, modularizado e profissional.

Este trabalho irá testar sua habilidade em utilizar actions externas, criar workflows reutilizáveis, configurar parâmetros de execução, depurar falhas e monitorar a saúde da integração contínua. Ao final, o pipeline da DevCalc estará mais próximo do que se espera em um projeto real de engenharia de software com entrega contínua.

1 Integração de uma Action do Marketplace

Agora que o pipeline principal já executa testes e build da aplicação, você deve aprimorá-lo com o uso de uma action externa do GitHub Actions Marketplace. Essa action deve automatizar uma verificação complementar no projeto, como análise de segurança, revisão de código ou linting.

A escolha da action será feita com o apoio do professor, que indicará uma opção compatível com a stack Java utilizada. Uma vez escolhida, adicione essa action como um novo job dentro do workflow ci.yml. Certifique-se de referenciar corretamente o repositório da action, sua versão (usando, por exemplo, @v4) e os parâmetros necessários.

Finalize essa etapa verificando, na aba “Actions” do GitHub, se o novo job foi executado com sucesso após um push na branch main, e registre a evidência.

2 Explicando a Diferença entre Workflows e Actions

É muito comum confundir os conceitos de workflow e action em GitHub Actions, especialmente quando estamos apenas utilizando arquivos prontos. Por isso, você deve demonstrar domínio desses conceitos criando um documento chamado actions-explicacao.md, dentro do repositório do projeto.

Nesse documento, escreva de forma clara e objetiva: qual é a diferença entre workflows e actions; como uma action é estruturada internamente; e qual o papel do arquivo action.yml para definir entradas, saídas e comandos executados. Escolha uma das actions que você já utilizou no projeto e explique como ela é chamada pelo workflow (por meio do uses:) e como os parâmetros são passados.

3 Separação do Pipeline com Workflows Reutilizáveis

À medida que os pipelines crescem, torna-se importante modularizar as etapas para evitar repetições e facilitar a manutenção. Nesta atividade, você deve criar um novo arquivo de workflow reutilizável chamado lint-and-test.yml, que conterá os jobs responsáveis por verificar o estilo do código (lint) e executar os testes automatizados da aplicação.

Esse novo workflow deve ser preparado para ser reutilizado a partir de outros workflows, utilizando a instrução workflow_call. Em seguida, refatore o arquivo ci.yml principal para que ele invoque o novo workflow, delegando a execução dos testes e do linting.

Essa prática aproxima seu pipeline de um ambiente corporativo, onde reutilização e organização são essenciais.

4 Execução Manual com Parâmetros

Seu pipeline agora será ainda mais flexível. Você deve configurar o arquivo ci.yml para que ele possa ser executado manualmente a partir da interface do GitHub, usando o gatilho workflow_dispatch.

Além disso, adicione dois parâmetros booleanos: run_tests e run_lint. Esses parâmetros permitirão que o usuário escolha, ao executar o workflow manualmente, se deseja rodar os testes ou o linting. Cada job no workflow deverá usar uma expressão if: para verificar se deve ser executado com base nesses inputs.

Essa abordagem simula pipelines mais inteligentes, capazes de se adaptar ao tipo de verificação que o desenvolvedor deseja realizar.

5 Provocando e Corrigindo um Erro no Pipeline

Para exercitar a depuração de workflows, você deve agora provocar intencionalmente uma falha no pipeline. Para isso, altere temporariamente algum comando de um step para um nome inválido ou gere um erro proposital (como run: exit 1).

Depois de fazer o push com o erro, vá até a aba Actions do GitHub, navegue pelos logs e localize a causa da falha. Corrija o erro, e registre no final do seu README.md um breve comentário explicando como você identificou o problema, quais ferramentas da interface do GitHub foram utilizadas e como o problema foi resolvido.

Essa etapa ajudará a desenvolver a habilidade de diagnosticar e corrigir problemas reais em pipelines de CI/CD.

6 Monitoramento e Status do Pipeline

Por fim, você deve configurar um status badge no topo do arquivo README.md do projeto. Esse badge deve exibir o status do workflow principal, indicando se a última execução foi bem-sucedida ou não.

Realize pelo menos duas execuções distintas do pipeline: uma por push automático e outra usando o botão manual (Run workflow). Acesse a aba Actions, explore os filtros de execução, compare os gatilhos usados e escreva, ao final do README.md, um pequeno parágrafo com suas observações sobre a diferença entre esses dois modos de execução.
Essa etapa conclui o TP2, consolidando sua autonomia para monitorar, personalizar e diagnosticar workflows reais no GitHub Actions.