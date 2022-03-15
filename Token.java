import java.util.ArrayList;

public class Token {
    
    private String valor = null;
    private ArrayList<Token> lista = null;
    private boolean evaluado = false;
    

    public Token() {
        valor = null;
        lista = null;
        evaluado = false;
    }

    /**
     * Constructor con lista
     * @param lista
     */
    public Token(ArrayList<Token> lista){
        this.lista = lista;
        evaluado = false;
    }

    /**
     * Constructor con valor
     * @param valor
     */
    public Token(String valor){
        this.valor = valor;
        evaluado = true;

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
     * Setter de valor, evaluado cambia a TRUE
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
     * Revisa el elemento al inicio de la lista
     * @return
     */
    public Token peek () {
        return lista.get(0);
    }

    /**
     * Si ha sido evaluado o no
     * @return
     */
    public boolean isEvaluado() {
        if (lista!=null) {
            if (lista.size()==1) {
                this.valor = lista.get(0).getValor();
                evaluado = true;
            }            
        }
        return evaluado;
    }
}
