import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // LEITURA DO ARQUIVO
            String codigo = new String(Files.readAllBytes(Paths.get("src/codigo.txt")));
            Leitor texto = new Leitor(codigo);
            Analisador analise = new Analisador(texto);

            imprimirCabecalho();

            List<Token> listaTokens = new ArrayList<>();
            Token token;

            // IMPRIMINDO TOKENS
            while ((token = analise.proximoToken()) != null) {
                if (!token.getToken().equals("NOVA_LINHA")) {
                    listaTokens.add(token);
                    imprimirToken(token.getLexema(), token.getToken(), token.getLinha());
                }
            }

            // ANALISE SINTATICA
            Sintatico analisadorSintatico = new Sintatico(listaTokens);
            analisadorSintatico.analisar();
            System.out.println("Análise sintática concluída com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo ou erro de exceção: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Erro durante a análise sintática: " + e.getMessage());
        }
    }

    private static void imprimirToken(String lexema, String token, int linha) {
        System.out.printf("| %-15s | %-20s | %-5d |\n", lexema, token, linha);
    }

    private static void imprimirCabecalho() {
        System.out.printf("| %-15s | %-20s | %-5s |\n", "Lexema", "Token", "Linha");
        System.out.println("|-----------------|---------------------|-------|");
    }
}
