from cromossomo import Cromossomo

estado_final = input("Entre com a palavra do estado final")
tamanho_populacao = int(input("Tamanho da populacao: "))
quantidade_geracoes = int(input("Geracoes: "))
taxa_selecao = int(input("Taxa de selecao [25 a 25]:"))
taxa_reproducao = 100 - taxa_selecao
taxa_mutacao = int(input("Taxa de mutacao: "))


populacao = list()

Cromossomo.gerar_populacao(populacao, tamanho_populacao, estado_final)
populacao.sort(key=lambda cromossomo: cromosso.aptidao, reverse=True)
Cromossomo.exibir_populacao(populacao, 0)





ordenar(populacao)
