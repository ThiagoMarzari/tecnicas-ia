import random

class Cromossomo:
    def __init__(self, rota):  
        self.rota = rota
        self.aptidao = self.calcular_aptidao()

    # Representa a heurística dinâmica da solução
    def calcular_aptidao(self):
        nota = 0
        # Penalidade pela ordem incorreta
        for i in range(len(self.rota) - 1):
            if self.rota[i] > self.rota[i + 1]:
                nota += 10

        # Penalidade pela repetição
        repetidos = 0
        vistos = set() #Para armazenar as cidades já vistas e nao permitir duplicatas
        for cidade in self.rota:
            if cidade in vistos:
                repetidos += 1
            else:
                vistos.add(cidade)

        if repetidos > 0:
            nota += 20 * repetidos

        return nota
    
    def __str__(self):
        return f'{self.rota} - {self.aptidao}'
    
    def __eq__(self, other):
        return isinstance(other, Cromossomo) and self.rota == other.rota

    @staticmethod
    def gerar_populacao(tamanho_populacao, num_cidades):
        populacao = []
        for _ in range(tamanho_populacao):
            rota = list(range(1, num_cidades + 1))
            
            #Embaralhar
            random.shuffle(rota)
            
            populacao.append(Cromossomo(rota))
        return populacao

    @staticmethod
    def exibir_populacao(populacao, numero_geracao):
        print(f'\nGeração {numero_geracao}')
        for individuo in populacao:
            print(individuo)

    @staticmethod
    def selecionar(populacao, nova_populacao, taxa_selecao):
        quantidade_selecionados = int(len(populacao) * taxa_selecao / 100)

        nova_populacao.append(populacao[0])  # Elitismo

        i = 1
        while i < quantidade_selecionados:
            candidatos = random.sample(populacao, 3)
            candidatos.sort(key=lambda c: c.aptidao)

            selecionado = candidatos[0]
            if selecionado not in nova_populacao:
                nova_populacao.append(selecionado)
                i += 1

    @staticmethod
    def reproduzir(populacao, nova_populacao, taxa_reproducao, estado_final):
        # Definir a quantidade de reproduzidos
        quantidade_reproduzidos = int(len(populacao) * taxa_reproducao / 100)

        for i in range(int(quantidade_reproduzidos / 2) + 1):
            # Sorteia um pai entre os primeiros 20% da população
            cromossomo_pai = populacao[random.randrange(len(populacao))]

            while (True):
                cromossomo_mae = populacao[random.randrange(len(populacao))]
                if not cromossomo_pai.__eq__(cromossomo_mae):
                    break

            pai = cromossomo_pai.rota
            mae = cromossomo_mae.rota

            corte = len(pai) // 2

            filho1 = pai[:corte] + mae[corte:]
            filho2 = mae[:corte] + pai[corte:]

            nova_populacao.append(Cromossomo(filho1))
            nova_populacao.append(Cromossomo(filho2))

            # Podar os excedentes, caso o tamanho da nova população ultrapasse o limite
            while len(nova_populacao) > len(populacao):
                nova_populacao.pop()


    @staticmethod
    def mutar(populacao):
        quantidade_mutantes = random.randrange(1, len(populacao) // 2 + 1)  # Garante um número entre 1 e metade da população
        while quantidade_mutantes > 0:
            posicao_mutante = random.randrange(len(populacao))
            mutante = populacao[posicao_mutante]
            
            print(f"Vai mutar o cromossomo: {mutante}")

            # Mudando
            rota_mutante = mutante.rota[:]
            # Seleciona dois índices aleatórios para trocar
            # Garante que os índices sejam diferentes
            i, j = random.sample(range(len(rota_mutante)), 2)
            rota_mutante[i], rota_mutante[j] = rota_mutante[j], rota_mutante[i]
            mutante_mutado = Cromossomo(rota_mutante)

            populacao[posicao_mutante] = mutante_mutado
            quantidade_mutantes -= 1
