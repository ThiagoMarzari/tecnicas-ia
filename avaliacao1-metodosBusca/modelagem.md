### Estados do Problema do Labirinto

**Classe:** Labirinto

**Atributos:**
- **tamanho** - Dimensão do labirinto
- **obstaculos** - Lista das posições dos obstáculos
- **saida**  - Posição (x, y) da saída
- **entrada1** - Primeira entrada no labirinto
- **entrada2** - Segunda entrada no labirinto
- **estado_atual** - Posição atual da entrada correspondente no labirinto

**Estado inicial:** entrada1 e entrada2

**Estado final:** alcançar a saída, sendo e1 & e2 = posicao da saida
---

### Regras de Transição do Problema do Labirinto

**Métodos da Classe:**
1) **mover_para_cima()** - Move para cima
2) **mover_para_baixo()** - Move para baixo
3) **mover_para_esquerda()** - Move para esquerda
4) **mover_para_direita()** - Move para direita

---

### Como tratar os visitados ou estados já produzidos

- Utilizar uma estrutura de dados como uma lista encadeada para armazenar os estados visitados e evitar loops
- Comparar os estados
- Verificar se o estado ja foi visitado antes de adicionar a proxima posicao.

---

### Mapeamento das Restrições do Problema

- Não pode atravessar obstáculos
- Não pode sair dos limites do labirinto
- Nao pode andar nas diagonais

---

### Perguntas/Métodos para Resolução do Problema por Busca:
1) **É válido?** Se a posição não for um obstáculo e estiver dentro do labirinto
2) **Foi visitado?** Se a posição já foi visitada anteriormente
3) **É a meta/objetivo?** Se a posição eh igual à saída

**Lista de estados visitados:** Ordem nivelada de exploração

### Exemplo de execucao
Caminho encontrado (5 passos):

(0,0)->(0,1)->(0,2)->(0,3)

### Características do Problema
1) Espaço de busca limitado pelo tamanho do labirinto
2) Estado final é conhecido (posição da saída)
3) Obstáculos adicionam restrições no caminho
4) Sem heurística definida (para busca cega)