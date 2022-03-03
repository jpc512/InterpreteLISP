import java.util.ArrayList;

public class Token {
    
    String valor = null;
    ArrayList<Token> lista = null;
    boolean evaluado = false;
    Token tokAnterior = null;

    /**
     * Constructor con lista
     * @param lista
     */
    public Token(ArrayList<Token> lista, Token anterior){
        this.lista = lista;
        evaluado = false;
        tokAnterior = anterior;
    }

    /**
     * Constructor con valor
     * @param valor
     */
    public Token(String valor,  Token anterior){
        this.valor = valor;
        evaluado = true;
        tokAnterior = anterior;

    }

    /**
     * Getter de valor
     * @return valor unicamente si evaluado = true
     */
    public String getValor() {
        if(evaluado){
            return valor;
        }
        else{
            String ev = "(";
            for (Token t : lista) {
                ev += " "+t.getValor();
            }
            ev += " )";
            return ev;
        }        
    }

    /**
     * Setter de valor
     * @param valor
     */
    public void setValor(String valor) {
        this.valor = valor;
        evaluado = true;
    }

    /**
     * Getter de list, para evaluar.
     * @return
     */
    public ArrayList<Token> getLista() {
        return lista;
    }

    /**
     * Agrega otro token a la lista actual
     * @param t
     */
    public void add(Token t){
        lista.add(t);
    }

    /**
     * Devuelve el token donde se encuentra almacenado
     * @return
     */
    public Token getAnterior() {
        return tokAnterior;
    }
}
