import java.io.StringReader;
import java.io.IOException;

public class Leitor {
    private StringReader codigo;

    public Leitor(String input) {
        this.codigo = new StringReader(input);
    }

    public int lerCaracter() throws IOException {
        return codigo.read();
    }
}
