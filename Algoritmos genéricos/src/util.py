class Util:
    letras = "abcdefghijklmnopqrstuvxwyz"
    tamanho = len(letras)

    @staticmethod
    def gerar_palavra(tamanho_palavra):
        palavra = ''

        for i in range(tamanho_palavra):
            palavra += Util.letras[ posicao_sorteada ]

        return palavra


print(Util.gerar_palavra(10))
