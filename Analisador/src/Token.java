public class Token {

    private String lexema;
    private String token;
    private int linha;

    public Token(String lexema, String token, int linha) {
        this.lexema = lexema;
        this.token = token;
        this.linha = linha;
    }

    public String getLexema() {
        return lexema;
    }

    public String getToken() {
        return token;
    }

    public int getLinha() {
        return linha;
    }

    @Override
    public String toString() {
        return "Token{" +
                "Lexema='" + lexema + '\'' +
                ", Token='" + token + '\'' +
                ", Linha=" + linha +
                '}';
    }
}
