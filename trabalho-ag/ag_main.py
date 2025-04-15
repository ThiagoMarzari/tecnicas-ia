import os
from cromossomo import Cromossomo

#Thiago Marzari & Gabriel Pinheiro

estado_final = [1, 2, 3, 4, 5, 6, 7, 8, 9]

tamanho_populacao = int(input('Tamanho da população: '))
quantidade_geracoes = int(input('Quantidade de gerações: '))
taxa_selecao = int(input('Taxa de seleção [25 a 75]: '))
taxa_reproducao = 100 - taxa_selecao
frequencia_mutacao = int(input('Frequência de mutação [ex: 5]: '))

populacao = list()
nova_populacao = list()

# Inicialização
populacao = Cromossomo.gerar_populacao(tamanho_populacao, len(estado_final))
populacao.sort(key=lambda cromossomo: cromossomo.aptidao, reverse=True)
Cromossomo.exibir_populacao(populacao, 0)

# Geracoes
for i in range(1, quantidade_geracoes + 1):
    Cromossomo.selecionar(populacao, nova_populacao, taxa_selecao)
    Cromossomo.reproduzir(populacao, nova_populacao, taxa_reproducao, estado_final)

     #mutacao quebra a estagnacao ou máximo locais
    if i % frequencia_mutacao == 0:
        Cromossomo.mutar(nova_populacao)

    populacao.clear()
    populacao.extend(nova_populacao)
    nova_populacao.clear()
    populacao.sort(key=lambda cromossomo: cromossomo.aptidao, reverse=True) 
    Cromossomo.exibir_populacao(populacao, i)

    if populacao[0].aptidao == 0:
        print(f'\nSolução encontrada na geração {i}: {populacao[0].rota}')
        break
