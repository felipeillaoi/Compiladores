import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

public class Analisador {

    //TOKENS
    private static final Pattern NUMERO_INTEIRO = Pattern.compile("^-?[0-9]+$");
    private static final Pattern NUMERO_FLOAT = Pattern.compile("^-?[0-9]+\\.[0-9]+$");
    private static final Pattern IDENTIFICADOR = Pattern.compile("^[a-z|A-Z][a-zA-Z0-9_]*$");
    private static final Pattern PALAVRAS_CHAVE = Pattern.compile("^(import|public|class|if|else|for|while|do|switch|case|break|continue|return|try|catch|finally|throw|throws|extends|implements|interface|enum|package|private|protected|final|int|String|void|float|boolean|char)$");
    private static final Pattern ABRE_PARENTESES = Pattern.compile("^[(]$");
    private static final Pattern FECHA_PARENTESES = Pattern.compile("^[)]$");
    private static final Pattern ABRE_CHAVES = Pattern.compile("^[{]$");
    private static final Pattern FECHA_CHAVES = Pattern.compile("^[}]$");
    private static final Pattern ASPAS = Pattern.compile("^[\"']$");
    private static final Pattern OPERADORES_ATRIBUICAO = Pattern.compile("^(=|\\+=|-=|\\*=|/=|%=)$");
    private static final Pattern OPERADORES_RELACIONAIS = Pattern.compile("^(==|!=|>=|<=|>|<)$");
    private static final Pattern OPERADORES_ARITMETICOS = Pattern.compile("^[+\\-*/%]$");
    private static final Pattern OPERADORES_LOGICOS = Pattern.compile("^(\\|\\||&&|!)$");
    private static final Pattern BOOLEANOS = Pattern.compile("^(true|false)$");
    private static final Pattern COMENTARIO_LINHA = Pattern.compile("^//.*$");
    private static final Pattern COMENTARIO_BLOCO = Pattern.compile("^/\\*.*?\\*/$");
    private static final Pattern STRING = Pattern.compile("^\".*?\"$");
    private static final Pattern DELIMITADORES = Pattern.compile("^[;]");
    private static final Pattern PONTO = Pattern.compile("^[.]$");

    private Leitor codigo;

    private int linhaAtual = 1;

    public Analisador(Leitor codigo) {
        this.codigo = codigo;
    }

    public Token proximoToken() throws IOException {
        StringBuilder texto = new StringBuilder();
        int valor;

        while ((valor = codigo.lerCaracter()) != -1) {
            char caractereAtual = (char) valor;

            if (caractereAtual == '\n') {
                linhaAtual++;
                return new Token("NOVA_LINHA", "NOVA_LINHA", linhaAtual);
            }

            if (Character.isWhitespace(caractereAtual)) {
                if (texto.length() > 0) {
                    return processarTexto(texto.toString());
                }
                continue;
            }

            texto.append(caractereAtual);
            String textoAtual = texto.toString();

            if (!fazParteDeTokenValido(textoAtual)) {
                if (texto.length() > 0) {
                    Token token = processarTexto(texto.substring(0, texto.length() - 1));
                    texto.setLength(0);
                    texto.append(caractereAtual);
                    return token;
                }
            }
        }

        if (texto.length() > 0) {
            return processarTexto(texto.toString());
        }

        return null;
    }


    private boolean fazParteDeTokenValido(String texto) {
        return NUMERO_INTEIRO.matcher(texto).matches() ||
                NUMERO_FLOAT.matcher(texto).matches() ||
                IDENTIFICADOR.matcher(texto).matches() ||
                PALAVRAS_CHAVE.matcher(texto).matches() ||
                STRING.matcher(texto).matches() ||
                DELIMITADORES.matcher(texto).matches() ||
                PONTO.matcher(texto).matches() ||
                ABRE_PARENTESES.matcher(texto).matches() ||
                ABRE_CHAVES.matcher(texto).matches() ||
                FECHA_CHAVES.matcher(texto).matches() ||
                FECHA_PARENTESES.matcher(texto).matches() ||
                ASPAS.matcher(texto).matches() ||
                OPERADORES_ATRIBUICAO.matcher(texto).matches() ||
                OPERADORES_RELACIONAIS.matcher(texto).matches() ||
                OPERADORES_ARITMETICOS.matcher(texto).matches() ||
                OPERADORES_LOGICOS.matcher(texto).matches() ||
                BOOLEANOS.matcher(texto).matches() ||
                COMENTARIO_LINHA.matcher(texto).matches() ||
                COMENTARIO_BLOCO.matcher(texto).matches();
    }

    private Token processarTexto(String texto) {
        if (NUMERO_INTEIRO.matcher(texto).matches()) {
            return new Token(texto, "NUMERO_INTEIRO", linhaAtual);
        }

        if (NUMERO_FLOAT.matcher(texto).matches()) {
            return new Token(texto, "NUMERO_FLOAT", linhaAtual);
        }

        if (PALAVRAS_CHAVE.matcher(texto).matches()) {
            return new Token(texto, "PALAVRA_CHAVE", linhaAtual);
        }

        if (IDENTIFICADOR.matcher(texto).matches()) {
            return new Token(texto, "IDENTIFICADOR", linhaAtual);
        }

        if (STRING.matcher(texto).matches()) {
            return new Token(texto, "STRING", linhaAtual);
        }

        if (DELIMITADORES.matcher(texto).matches()) {
            return new Token(texto, "DELIMITADOR", linhaAtual);
        }

        if (PONTO.matcher(texto).matches()) {
            return new Token(texto, "PONTO", linhaAtual);
        }

        if (ABRE_PARENTESES.matcher(texto).matches()) {
            return new Token(texto, "ABRE_PARENTESES", linhaAtual);
        }

        if (FECHA_PARENTESES.matcher(texto).matches()) {
            return new Token(texto, "FECHA_PARENTESES", linhaAtual);
        }

        if (ABRE_CHAVES.matcher(texto).matches()) {
            return new Token(texto, "ABRE_CHAVES", linhaAtual);
        }

        if (FECHA_CHAVES.matcher(texto).matches()) {
            return new Token(texto, "FECHA_CHAVES", linhaAtual);
        }

        if (ASPAS.matcher(texto).matches()) {
            return new Token(texto, "ASPAS", linhaAtual);
        }

        if (OPERADORES_ATRIBUICAO.matcher(texto).matches()) {
            return new Token(texto, "OPERADOR_ATRIBUICAO", linhaAtual);
        }

        if (OPERADORES_RELACIONAIS.matcher(texto).matches()) {
            return new Token(texto, "OPERADOR_RELACIONAL", linhaAtual);
        }

        if (OPERADORES_ARITMETICOS.matcher(texto).matches()) {
            return new Token(texto, "OPERADOR_ARITMETICO", linhaAtual);
        }

        if (OPERADORES_LOGICOS.matcher(texto).matches()) {
            return new Token(texto, "OPERADOR_LOGICO", linhaAtual);
        }

        if (BOOLEANOS.matcher(texto).matches()) {
            return new Token(texto, "BOOLEANO", linhaAtual);
        }

        if (COMENTARIO_LINHA.matcher(texto).matches()) {
            return new Token(texto, "COMENTARIO_LINHA", linhaAtual);
        }

        if (COMENTARIO_BLOCO.matcher(texto).matches()) {
            return new Token(texto, "COMENTARIO_BLOCO", linhaAtual);
        }

        return new Token(texto, "QUEBRA_LINHA", linhaAtual);
    }
}
