import java.util.ArrayList;

public class Token {
    
    private String atom;
    private ArrayList<Token> lista;
    private boolean evaluado;
    

    public Token() {
        this.atom = null;
        this.lista = null;
        this.evaluado = false;
    }

    public Token (Token token) {
        this.atom = new String(token.getValor());
        this.lista = new ArrayList<>(token.getLista());
        this.evaluado = Boolean.valueOf(token.isEvaluado());
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
     * Constructor con atom
     * @param atom
     */
    public Token(String atom){
        this.atom = atom;
        this.lista = null;
        evaluado = true;
    }

    /**
     * Getter de atom
     * @return atom unicamente si evaluado = true
     */
    public String getValor() {
        if(evaluado){
            return new String(this.atom);
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
     * Getter de list, para evaluar.
     * @return
     */
    public ArrayList<Token> getLista() {
        return new ArrayList<>(this.lista);
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
     * Remueve el elemento al inicio de la lista
     * @return
     */
    public Token poll () {
        return lista.remove(0);
    }

    public int size () {
        return lista.size();
    }

    /**
     * Si ha sido evaluado o no
     * @return
     */
    public boolean isEvaluado() {
        // if (lista!=null) {
        //     if (lista.size() > 0) {
        //         this.atom = lista.get(0).getValor();
        //         evaluado = true;
        //     }            
        // }
        return evaluado;
    }

}
