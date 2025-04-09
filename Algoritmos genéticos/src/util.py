class Util:
    letras = "abcdefghijklmnopqrstuvxwyz"
    tamanho = len(letras)

    @staticmethod
    def gerar_palavra(tamanho_palavra):
        palavra = ''

        for i in range(tamanho_palavra):
            palavra += Util.letras[ tamanho_palavra ]

        return palavra

print(Util.gerar_palavra(10))
