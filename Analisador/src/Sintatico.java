import java.util.List;

public class Sintatico {
    private List<Token> tokens;
    private int posicaoAtual;

    public Sintatico(List<Token> tokens) {
        this.tokens = tokens;
        this.posicaoAtual = 0;
    }

    public void analisar() {
        System.out.println("Iniciando análise sintática...");
        while (!fimDosTokens()) {
            ignorarNovaLinha();

            if (analisar_imports()) {
                System.out.println("...\n...\nAnálise sintática concluída com sucesso!\n...");
            } else {
                throw new RuntimeException("Erro de sintaxe na linha " + getTokenAtual().getLinha());
            }
        }
    }

    public boolean analisar_imports() {
        int posicaoInicial = posicaoAtual;

        if (verificarToken("PALAVRA_CHAVE", "import")) {
            if (verificarToken("IDENTIFICADOR")) {
                if (verificarToken("DELIMITADOR", ";")) {
                    System.out.println("CONSTRUÇÃO DA ARVORE SINTATICA\n...");
                    System.out.println("IMPORT");
                    if (analisar_main()){
                        return true;
                    }
                }
            }
        }

        posicaoAtual = posicaoInicial;
        return false;
    }

    public boolean analisar_main() {
        int posicaoInicial = posicaoAtual;

        if (verificarToken("PALAVRA_CHAVE", "int") || verificarToken("PALAVRA_CHAVE", "void")) {
            if (verificarToken("IDENTIFICADOR")) {
                if (verificarToken("ABRE_CHAVES")) {
                    System.out.println("MAIN");
                    if (analisar_declaracao_variavel() && analisar_estrutura_if()) {
                        System.out.println("DECLARAÇÃO DE VARIAVEL");
                        System.out.println("ESTRUTURA IF");
                        if (verificarToken("FECHA_CHAVES")) {
                            System.out.println("FECHAMENTO DAS CHAVES\n...");
                            return true;
                        }
                    }
                }
            }
        }

        posicaoAtual = posicaoInicial;
        return false;
    }

    public boolean analisar_declaracao_variavel() {
        int posicaoInicial = posicaoAtual;

        if (verificarToken("PALAVRA_CHAVE", "int") || verificarToken("PALAVRA_CHAVE", "float")) {
            if (verificarToken("IDENTIFICADOR")) {
                if (verificarToken("OPERADOR_ATRIBUICAO") || verificarToken("OPERADOR_ARITMETICO")) {
                    if (verificarToken("IDENTIFICADOR") || verificarToken("NUMERO_INTEIRO") && verificarToken("DELIMITADOR", ";")) {
                        return true;
                    }
                } else if (verificarToken("DELIMITADOR", ";")) {
                    return true;
                }
            }
        }

        posicaoAtual = posicaoInicial;
        return false;
    }

    public boolean analisar_estrutura_if() {
        int posicaoInicial = posicaoAtual;

        if (verificarToken("PALAVRA_CHAVE", "if") && verificarToken("ABRE_PARENTESES")) {
            if (verificarToken("IDENTIFICADOR") || verificarToken("NUMERO_INTEIRO")) {
                if (verificarToken("OPERADOR_ARITMETICO") || verificarToken("OPERADOR_RELACIONAL") || verificarToken("OPERADOR_LOGICO")) {
                    if (verificarToken("IDENTIFICADOR") || verificarToken("NUMERO_INTEIRO")) {
                        if(verificarToken("FECHA_PARENTESES")) {
                            if(verificarToken("ABRE_CHAVES")) {
                                if (analisar_comando()) {
                                    if (verificarToken("FECHA_CHAVES")) {
                                        return true;
                                    }
                                } else {
                                    System.out.println("Estrutura IF incorreta");
                                    return false;
                                }
                            }
                        }
                    }
                } else if (verificarToken("DELIMITADOR", ";")) {

                    return true;
                }
            } else if (verificarToken("FECHA_PARENTESES")) {
                if (verificarToken("ABRE_CHAVES")) {
                    if (analisar_comando()) {
                        if (verificarToken("FECHA_CHAVES")) {
                            return true;
                        }
                    } else {
                        System.out.println("Estrutura IF incorreta");
                        return false;
                    }
                }
            }
        }

        posicaoAtual = posicaoInicial;
        return false;
    }

    public boolean analisar_comando() {
        if (verificarToken("IDENTIFICADOR")) {
            if (verificarToken("ABRE_PARENTESES")) {
                if (verificarToken("ASPAS")) {
                    if (verificarToken("IDENTIFICADOR")) {
                        if (verificarToken("IDENTIFICADOR")) {
                            if (verificarToken("ASPAS")) {
                                if (verificarToken("FECHA_PARENTESES")) {
                                    if (verificarToken("DELIMITADOR", ";")) {
                                        return true;
                                    }
                                }
                            }
                            return true;
                        } else if (verificarToken("ASPAS")) {
                            if (verificarToken("FECHA_PARENTESES")){
                                if (verificarToken("DELIMITADOR",";")) {
                                    return true;
                                }
                            }
                        }
                    } else if (verificarToken("ASPAS")) {
                        if (verificarToken("FECHA_PARENTESES")){
                            if (verificarToken("DELIMITADOR",";")) {
                                System.out.println("COMANDO RETORNOU NO LUGAR CORRETO");
                                return true;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("RETORNOU FALSO O COMANDO");
        return false;
    }

    private void ignorarNovaLinha() {
        while (!fimDosTokens() && getTokenAtual().getToken().equals("NOVA_LINHA")) {
            posicaoAtual++;
        }
    }

    public boolean verificarToken(String tipoEsperado) {
        ignorarNovaLinha();

        if (!fimDosTokens() && getTokenAtual().getToken().equals(tipoEsperado)) {
            posicaoAtual++;
            return true;
        }

        return false;
    }

    public boolean verificarToken(String tipoEsperado, String lexemaEsperado) {
        ignorarNovaLinha();

        if (!fimDosTokens() &&
                getTokenAtual().getToken().equals(tipoEsperado) &&
                getTokenAtual().getLexema().equals(lexemaEsperado)) {
            posicaoAtual++;
            return true;
        }

        return false;
    }

    private Token getTokenAtual() {
        return tokens.get(posicaoAtual);
    }

    private boolean fimDosTokens() {
        return posicaoAtual >= tokens.size();
    }
}
