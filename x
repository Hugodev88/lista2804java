import java.io.*;
import java.util.*;

class Item {
    String jogo;
    String categoria;
    double avaliacao;

    public Item(String jogo, String categoria, double avaliacao) {
        this.jogo = jogo;
        this.categoria = categoria;
        this.avaliacao = avaliacao;
    }
}

public class OrdenadorJogos {

    private static Item[] jogos;

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
        List<Item> jogosList = new ArrayList<>();

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

                jogosList.add(new Item(jogo, categoria, avaliacao));
            }

            jogos = jogosList.toArray(new Item[0]);
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

        // Ordenar os jogos por categoria
        Arrays.sort(jogos, Comparator.comparing(item -> item.categoria));

        for (Item jogo : jogos) {
            System.out.println(jogo.jogo + ", " + jogo.categoria + ", " + jogo.avaliacao);
        }

        salvarEmArquivo("JogosOrdenadosporCategoria.csv");

        System.out.println("Jogos ordenados por categoria e salvos no arquivo 'src/JogosOrdenadosporCategoria.csv'.");
    }

    private static void ordenarPorAvaliacao() {
        if (jogos == null) {
            System.out.println("Por favor, leia o arquivo primeiro (Opção 1).");
            return;
        }

        // Ordenar os jogos por avaliação decrescente
        Arrays.sort(jogos, Comparator.comparing((Item item) -> -item.avaliacao));

        for (Item jogo : jogos) {
            System.out.println(jogo.jogo + ", " + jogo.categoria + ", " + jogo.avaliacao);
        }

        salvarEmArquivo("JogosOrdenadosporAvaliacao.csv");

        System.out.println("Jogos ordenados por avaliação e salvos no arquivo 'src/JogosOrdenadosporAvaliacao.csv'.");
    }

    private static void salvarEmArquivo(String nomeArquivo) {
        try {
            String diretorioAtual = System.getProperty("user.dir");
            String caminhoArquivo = diretorioAtual + File.separator + "src" + File.separator + nomeArquivo;
            BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo));

            for (Item jogo : jogos) {
                writer.write(String.format("%s,%s,%.1f\n", jogo.jogo, jogo.categoria, jogo.avaliacao));
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar no arquivo: " + e.getMessage());
        }
    }

}
