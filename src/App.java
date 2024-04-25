import java.io.*;
import java.util.*;

class Jogo {
    String jogo;
    String categoria;
    double avaliacao;

    public Jogo(String jogo, String categoria, double avaliacao) {
        this.jogo = jogo;
        this.categoria = categoria;
        this.avaliacao = avaliacao;
    }
}

public class App {

    private static Jogo[] jogos;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int opcao;
        do {
            System.out.println("[1] Ler arquivo");
            System.out.println("[2] Ordenar por categoria");
            System.out.println("[3] Ordenar por avaliação");
            System.out.println("[4] Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    lerArquivo();
                    break;
                case 2:
                    ordenarPorCategoria();
                    break;
                case 3:
                    ordenarPorAvaliacao();
                    break;
                case 4:
                    System.out.println("Programa encerrado.");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha novamente.");
            }
        } while (opcao != 4);

        scanner.close();
    }

    private static void lerArquivo() {
        List<Jogo> jogosList = new ArrayList<>();

        try {
            String diretorioAtual = System.getProperty("user.dir");
            String caminhoArquivo = diretorioAtual + File.separator + "src/JogosDesordenados.csv";
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(caminhoArquivo), "UTF-8"));

            System.out.println("Lendo arquivo:");
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                String jogo = partes[0];
                String categoria = partes[1];
                double avaliacao = Double.parseDouble(partes[2]);

                System.out.println("Jogo: " + jogo + ", Categoria: " + categoria + ", Avaliação: " + avaliacao);

                jogosList.add(new Jogo(jogo, categoria, avaliacao));
            }

            jogos = jogosList.toArray(new Jogo[0]);
            reader.close();

            System.out.println("Arquivo lido com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    private static void ordenarPorCategoria() {
        if (jogos == null) {
            System.out.println("Por favor, leia o arquivo primeiro (Opção 1).");
            return;
        }

        // selection sort
        for (int i = 0; i < jogos.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < jogos.length; j++) {
                if (jogos[j].categoria.compareTo(jogos[min].categoria) < 0) {
                    min = j;
                }
            }
            Jogo temp = jogos[min];
            jogos[min] = jogos[i];
            jogos[i] = temp;
        }

        for (Jogo jogo : jogos) {
            System.out.println(jogo.jogo + ", " + jogo.categoria + ", " + jogo.avaliacao);
        }

        salvarEmArquivo("JogosOrdenadosporCategoria.csv");
    }

    private static void ordenarPorAvaliacao() {
        if (jogos == null) {
            System.out.println("Por favor, leia o arquivo primeiro (Opção 1).");
            return;
        }

        // bubble sort
        int n = jogos.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (jogos[j].avaliacao < jogos[j + 1].avaliacao) {
                    Jogo temp = jogos[j];
                    jogos[j] = jogos[j + 1];
                    jogos[j + 1] = temp;
                }
            }
        }

        for (Jogo jogo : jogos) {
            System.out.println(jogo.jogo + ", " + jogo.categoria + ", " + jogo.avaliacao);
        }

        salvarEmArquivo("JogosOrdenadosporAvaliacao.csv");
    }

    private static void salvarEmArquivo(String nomeArquivo) {
        try {
            String diretorioAtual = System.getProperty("user.dir");
            String caminhoArquivo = diretorioAtual + File.separator + "src" + File.separator + nomeArquivo;
            PrintWriter writer = new PrintWriter(new FileWriter(caminhoArquivo));

            for (Jogo jogo : jogos) {
                writer.printf("%s,%s,%.1f%n", jogo.jogo, jogo.categoria, jogo.avaliacao);
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar no arquivo: " + e.getMessage());
        }
    }

}
