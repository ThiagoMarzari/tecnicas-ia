package labirinto;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import busca.Heuristica;
import busca.BuscaLargura;
import busca.BuscaProfundidade;
import busca.BuscaIterativo;
import busca.BuscaRecursiva;
import busca.Estado;
import busca.MostraStatusConsole;
import busca.Nodo;
import javax.swing.JOptionPane;

public class Labirinto implements Estado, Heuristica {
    
    @Override
    public String getDescricao() {
        return "O jogo do labirinto é uma matriz NxN, com duas entradas (E1 e E2) e uma saída.\n"
                + "Cada entrada utilizará um método de busca diferente para encontrar\n"
                + "o caminho até a saída, permitindo comparar as soluções.";
    }

    final char matriz[][]; // preferir "immutable objects"
    int linhaAtual, colunaAtual; // posição atual (pode ser de qualquer entrada)
    int linhaSaida, colunaSaida;
    final String op; // operacao que gerou o estado
    
    // Posições das entradas (adicionadas para o novo requisito)
    int linhaEntrada1, colunaEntrada1;
    int linhaEntrada2, colunaEntrada2;
    boolean usandoEntrada1; // flag para saber qual entrada está sendo usada

    //atenção.... matrizes precisam ser clonadas ao gerarmos novos estados
    char [][]clonar(char origem[][]) {
        char destino[][] = new char[origem.length][origem.length];
        for (int i = 0; i < origem.length; i++) {
            for (int j = 0; j < origem.length; j++) {
                destino[i][j] = origem[i][j];
            }
        }
        return destino;
    }
    
    /**
     * construtor para o estado gerado na evolução/resolução do problema, recebe cada valor de atributo
     */
    public Labirinto(char m[][], int linhaAtual, int colunaAtual, 
                    int linhaSaida, int colunaSaida, 
                    int linhaEntrada1, int colunaEntrada1,
                    int linhaEntrada2, int colunaEntrada2,
                    boolean usandoEntrada1, String o) {
        this.matriz = m; //ter certeza que m foi clonado antes de entrar no construtor
        this.linhaAtual = linhaAtual;
        this.colunaAtual = colunaAtual;
        this.linhaSaida = linhaSaida;
        this.colunaSaida = colunaSaida;
        this.linhaEntrada1 = linhaEntrada1;
        this.colunaEntrada1 = colunaEntrada1;
        this.linhaEntrada2 = linhaEntrada2;
        this.colunaEntrada2 = colunaEntrada2;
        this.usandoEntrada1 = usandoEntrada1;
        this.op = o;
    }
    
    /**
     * construtor para o estado INICIAL
     */
    public Labirinto(int dimensao, String o, int porcentagemObstaculos) {
        this.matriz = new char[dimensao][dimensao];
        this.op = o;
        
        int quantidadeObstaculos = (dimensao*dimensao)* porcentagemObstaculos/100;
        System.out.println("Quantidade de obstáculos: " + quantidadeObstaculos);
        
        Random gerador = new Random();

        // Sorteia duas entradas diferentes
        int entrada1 = gerador.nextInt(dimensao * dimensao);
        int entrada2;
        do {
            entrada2 = gerador.nextInt(dimensao * dimensao);
        } while (entrada1 == entrada2);
        
        
        // Sorteia uma saída diferente das entradas
        int saida;
        do {
            saida = gerador.nextInt(dimensao * dimensao);
        } while (saida == entrada1 || saida == entrada2);
        

        int contaPosicoes = 0;
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                if (contaPosicoes == entrada1) {
                    this.matriz[i][j] = '1'; // E1
                    this.linhaEntrada1 = i;
                    this.colunaEntrada1 = j;
                    this.linhaAtual = i;
                    this.colunaAtual = j;
                    this.usandoEntrada1 = true;
                } else if (contaPosicoes == entrada2) {
                    this.matriz[i][j] = '2'; //E2
                    this.linhaEntrada2 = i;
                    this.colunaEntrada2 = j;
                } else if (contaPosicoes == saida) {
                    this.matriz[i][j] = 'S';
                    this.linhaSaida = i;
                    this.colunaSaida = j;
                } else if (quantidadeObstaculos > 0 && gerador.nextInt(3) == 1) {
                    quantidadeObstaculos--;
                    this.matriz[i][j] = '@';
                } else {
                    this.matriz[i][j] = 'O';
                }
                contaPosicoes++;
            }
        }
    }

    /**
     * verifica se o estado e meta
     */
    @Override
    public boolean ehMeta() {
        return this.linhaAtual == this.linhaSaida && this.colunaAtual == this.colunaSaida;
    }

    /**
     * @return Custo do movimento (sempre 1 neste caso)
     */
    @Override
    public int custo() {
        return 1;
    }

    /**
     * Heurística: Distância de Manhattan até a saída
     */
    @Override 
    public int h() {
        return Math.abs(linhaAtual - linhaSaida) + Math.abs(colunaAtual - colunaSaida);
    }

    /**
     * gera uma lista de sucessores do nodo.
     */
    @Override
    public List<Estado> sucessores() {
        List<Estado> visitados = new LinkedList<Estado>(); // a lista de sucessores

        paraCima(visitados);
        paraBaixo(visitados);
        paraEsquerda(visitados);
        paraDireita(visitados);
        
        return visitados;
    }

    private void paraCima(List<Estado> visitados) {
        if (this.linhaAtual == 0 || this.matriz[this.linhaAtual - 1][this.colunaAtual] == '@') return; 

        char mTemp[][];
        mTemp = clonar(this.matriz);
        int linhaTemp = this.linhaAtual - 1;
        int colunaTemp = this.colunaAtual;
        
        mTemp[this.linhaAtual][this.colunaAtual] = 'O';
        mTemp[linhaTemp][colunaTemp] = this.usandoEntrada1 ? '1' : '2';
     
        Labirinto novo = new Labirinto(mTemp, linhaTemp, colunaTemp, 
                                     this.linhaSaida, this.colunaSaida,
                                     this.linhaEntrada1, this.colunaEntrada1,
                                     this.linhaEntrada2, this.colunaEntrada2,
                                     this.usandoEntrada1, "Movendo para cima");
        if (!visitados.contains(novo)) visitados.add(novo);
    }

    private void paraBaixo(List<Estado> visitados) {
        if (this.linhaAtual == this.matriz.length-1 || this.matriz[this.linhaAtual + 1][this.colunaAtual] == '@') return;

        char mTemp[][];
        mTemp = clonar(this.matriz);
        int linhaTemp = this.linhaAtual + 1;
        int colunaTemp = this.colunaAtual;
        
        mTemp[this.linhaAtual][this.colunaAtual] = 'O';
        mTemp[linhaTemp][colunaTemp] = this.usandoEntrada1 ? '1' : '2';
               
        Labirinto novo = new Labirinto(mTemp, linhaTemp, colunaTemp, 
                                     this.linhaSaida, this.colunaSaida,
                                     this.linhaEntrada1, this.colunaEntrada1,
                                     this.linhaEntrada2, this.colunaEntrada2,
                                     this.usandoEntrada1, "Movendo para baixo");
        if (!visitados.contains(novo)) visitados.add(novo);
    }

    private void paraEsquerda(List<Estado> visitados) {
        if (this.colunaAtual == 0 || this.matriz[this.linhaAtual][this.colunaAtual - 1] == '@') return;

        char mTemp[][];
        mTemp = clonar(this.matriz);
        int linhaTemp = this.linhaAtual;
        int colunaTemp = this.colunaAtual - 1;
        
        mTemp[this.linhaAtual][this.colunaAtual] = 'O';
        mTemp[linhaTemp][colunaTemp] = this.usandoEntrada1 ? '1' : '2';
     
        Labirinto novo = new Labirinto(mTemp, linhaTemp, colunaTemp, 
                                     this.linhaSaida, this.colunaSaida,
                                     this.linhaEntrada1, this.colunaEntrada1,
                                     this.linhaEntrada2, this.colunaEntrada2,
                                     this.usandoEntrada1, "Movendo para esquerda");
        if (!visitados.contains(novo)) visitados.add(novo);
    }

    private void paraDireita(List<Estado> visitados) {
        if (this.colunaAtual == this.matriz.length-1 || this.matriz[this.linhaAtual][this.colunaAtual + 1] == '@') return;
        
        char mTemp[][];
        mTemp = clonar(this.matriz);
        int linhaTemp = this.linhaAtual;
        int colunaTemp = this.colunaAtual + 1;
        
        mTemp[this.linhaAtual][this.colunaAtual] = 'O';
        mTemp[linhaTemp][colunaTemp] = this.usandoEntrada1 ? '1' : '2';
               
        Labirinto novo = new Labirinto(mTemp, linhaTemp, colunaTemp, 
                                     this.linhaSaida, this.colunaSaida,
                                     this.linhaEntrada1, this.colunaEntrada1,
                                     this.linhaEntrada2, this.colunaEntrada2,
                                     this.usandoEntrada1, "Movendo para direita");
        if (!visitados.contains(novo)) visitados.add(novo);
    }

    /**
     * verifica se um estado e igual a outro (usado para poda)
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Labirinto) {
            Labirinto e = (Labirinto) o;
            for (int i = 0; i < e.matriz.length; i++) {
                for (int j = 0; j < e.matriz.length; j++) {
                    if (e.matriz[i][j] != this.matriz[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * retorna o hashCode desse estado (usado para poda, conjunto de fechados)
     */
    @Override
    public int hashCode() {
        String estado = "";
        
        for (int i = 0; i < this.matriz.length; i++) {
            for (int j = 0; j < this.matriz.length; j++) {
                estado = estado + this.matriz[i][j];
            }
        }
        return estado.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                // Mostrar E1 e E2 em vez de 1 e 2
                if (matriz[i][j] == '1') {
                    resultado.append("E1");
                } else if (matriz[i][j] == '2') {
                    resultado.append("E2");
                } else {
                    resultado.append(this.matriz[i][j]);
                }
                resultado.append("\t");
            }
            resultado.append("\n");
        }
        resultado.append("Posição Atual: " + this.linhaAtual + "," + this.colunaAtual +"\n");
        resultado.append("Entrada 1 (E1): " + this.linhaEntrada1 + "," + this.colunaEntrada1 +"\n");
        resultado.append("Entrada 2 (E2): " + this.linhaEntrada2 + "," + this.colunaEntrada2 +"\n");
        resultado.append("Posição Saida (S): " + this.linhaSaida + "," + this.colunaSaida +"\n");
        resultado.append("\n"+ op + "\n------------------\n\n");
        return resultado.toString();
    }

    public static void main(String[] a) {
    try {
        int dimensao = Integer.parseInt(JOptionPane.showInputDialog(null,"Entre com a dimensão do Labirinto:"));
        int porcentagemObstaculos = Integer.parseInt(JOptionPane.showInputDialog(null,"Porcentagem de obstáculos:"));

        int metodoEntrada1 = escolherMetodoBusca("1");
        int metodoEntrada2 = escolherMetodoBusca("2");
        
        // Labirinto base, apenas para configuracao 
        Labirinto labirintoInicial = new Labirinto(dimensao, "estado inicial", porcentagemObstaculos);
        
        //Tive que criar outro estado pois estava sobreescrevendo o estado e1
            
        //E1
        Labirinto estadoE1 = new Labirinto(
            labirintoInicial.clonar(labirintoInicial.matriz),
            labirintoInicial.linhaEntrada1, labirintoInicial.colunaEntrada1,
            labirintoInicial.linhaSaida, labirintoInicial.colunaSaida,
            labirintoInicial.linhaEntrada1, labirintoInicial.colunaEntrada1,
            labirintoInicial.linhaEntrada2, labirintoInicial.colunaEntrada2,
            true, "Estado para E1");
        
        //E2
        Labirinto estadoE2 = new Labirinto(
            labirintoInicial.clonar(labirintoInicial.matriz),
            labirintoInicial.linhaEntrada2, labirintoInicial.colunaEntrada2,
            labirintoInicial.linhaSaida, labirintoInicial.colunaSaida,
            labirintoInicial.linhaEntrada1, labirintoInicial.colunaEntrada1,
            labirintoInicial.linhaEntrada2, labirintoInicial.colunaEntrada2,
            false, "Estado para E2");

        System.out.println("\n=== RESOLVENDO PARA ENTRADA E1 ===");
        Nodo n1 = executarBusca(estadoE1, metodoEntrada1);
        
        System.out.println("\n=== RESOLVENDO PARA ENTRADA E2 ===");
        Nodo n2 = executarBusca(estadoE2, metodoEntrada2);
        
        System.out.println("\n=== RESULTADOS COMPARATIVOS ===");
        mostrarResultado("Entrada E1", n1);
        mostrarResultado("Entrada E2", n2);
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        e.printStackTrace();
    }
    System.exit(0);
}
    
    private static int escolherMetodoBusca(String numeroEntrada){
        int metodo = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Método para Entrada "+numeroEntrada+ ":\n1 - Profundidade\n2 - Largura\n3 - Recursiva\n4 - Iterativa"));
        
        return metodo;
    }
    
    private static Nodo executarBusca(Labirinto estado, int metodo) {
        switch (metodo) {
            case 1: 
                System.out.println("Usando busca em PROFUNDIDADE");
                return new BuscaProfundidade(new MostraStatusConsole()).busca(estado);
            case 2: 
                System.out.println("Usando busca em LARGURA");
                return new BuscaLargura(new MostraStatusConsole()).busca(estado);
            case 3:
                System.out.println("Usando busca Recursiva");
                return new BuscaRecursiva(new MostraStatusConsole()).busca(estado);
            case 4:
            System.out.println("Usando busca Iterativa");
            return new BuscaIterativo(new MostraStatusConsole()).busca(estado);
            default: 
                JOptionPane.showMessageDialog(null, "Método não implementado");
                return null;
        }
    }
    
    private static void mostrarResultado(String entrada, Nodo n) {
        System.out.println("\n--- " + entrada + " ---");
        if (n == null) {
            System.out.println("Sem solução encontrada!");
        } else {
            System.out.println("Solução encontrada em " + n.getProfundidade() + " passos:");
            System.out.println(n.montaCaminho());
        }
    }
}