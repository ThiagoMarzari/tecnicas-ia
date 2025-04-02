## O que são algoritmos genéricos?

Algoritmos genéricos são algoritmos que são desenhados de forma a serem aplicáveis a uma ampla gama de problemas, sem depender de uma 
implementação específica para cada situação.

Algoritmos genéticos (AGs) são uma classe de algoritmos de otimização inspirados no processo de evolução natural. Eles são usados para resolver problemas complexos onde as soluções não podem ser facilmente encontradas por métodos tradicionais.

Porém ele tem algumas desvantagens como o alto custo computacional, especialmente quando o espaço de busca é muito grande ou as funções fitness (Explicado logo abaixo) são caras de avaliar.


### Como ele funciona?

Os algoritmos genéticos simulam o processo evolutivo natural com as seguintes etapas principais:

População Inicial: Uma população aleatória de soluções (indivíduos) é criada. Cada indivíduo é uma solução potencial para o problema em questão, representado por um cromossomo (geralmente uma string de bits, números ou genes).

Seleção: O algoritmo avalia a qualidade das soluções (fitness). Indivíduos com maior fitness têm maior chance de "reproduzir" e gerar novas soluções.

Cruzamento (Crossover): O cruzamento combina duas soluções para gerar uma nova solução, misturando as características de ambos os pais.

Mutação: Ocorre uma pequena mutação nos descendentes, o que introduz diversidade na população e evita que a solução fique presa em um ótimo local, sem explorar outras possibilidades e acabar ficando estagnada.

Substituição: A cada geração ocorre uma troca, os melhores indivíduos da geração anterior são substituídos pelos novos indivíduos.

Critério de Parada: O algoritmo pode ser interrompido de diversas formas, por exemplo: O processo continua até que uma solução suficientemente boa seja encontrada ou até que um número pré-determinado de gerações seja alcançado. 

#### Exemplo básico


```
Geração 1: Melhor solução = 24
Geração 2: Melhor solução = 28
Geração 3: Melhor solução = 31
...
Geração 50: Melhor solução = 31

Melhor solução final: ?

```
