from cromossomo import Cromossomo
from util import Util
mutante = Cromossomo('alexone', 'simone')
print('Antes da mutação..', mutante)
posicao_gene_mutante = random.randrange(len(mutante.palavra))
novo_gene = Util.letras[random.randrange(Util.tamanho)]
mutante.palavra[posicao_gene_mutante] = str(novo_gene)
mutante.calcular_aptidao('simone')
print('Mutacao:', mutante)