
pai = 'alexandre'
mae = 'simone'

tamanho_pai = len(pai)
tamanho_mae = len(mae)

primeira_metade_pai = pai[0 : int(len(pai) / 2)] 
segunda_metade_pai = pai[int(len(pai) / 2) : tamanho_pai]

primeira_metade_mae = mae[0 : int(len(mae) / 2)]
segunda_metade_mae = mae[int(len(mae) / 2) : tamanho_mae]

filho1 = primeira_metade_pai + segunda_metade_mae
filho2 = primeira_metade_mae + segunda_metade_pai

print(filho1)
print(filho2)