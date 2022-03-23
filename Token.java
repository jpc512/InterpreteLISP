import java.util.ArrayList;

public class Token {
    
    private String atom;        //Valores cualquiera
    private ArrayList<Token> list; //Lista de atomos
    private boolean evaluado;   //Si es o no una expresion evaluada
    
    /**
     * Constructor del token
     */
    public Token() {
        this.atom = null;
        this.list = null;
        this.evaluado = false;
    }

    /**
     * Constructor con token
     * @param token
     */
    public Token (Token token) {
        this.atom = new String(token.getAtom());
        this.list = new ArrayList<>(token.getList());
        this.evaluado = Boolean.valueOf(token.isEvaluado());
    }

    /**
     * Constructor con lista
     * @param list
     */
    public Token(ArrayList<Token> list){
        this.list = list;
        evaluado = false;
    }

    /**
     * Constructor con atom
     * @param atom
     */
    public Token(String atom){
        this.atom = atom;
        this.list = null;
        evaluado = true;
    }

    /**
     * Getter de atom
     * @return atom unicamente si evaluado = true
     */
    public String getAtom() {
        if(evaluado){
            return new String(this.atom);
        }
        else{
            String ev = "(";
            for (Token t : list) {
                ev += " "+t.getAtom();
            }
            ev += " )";
            return ev;
        }        
    }

    /**
     * Getter de list, para evaluar.
     * @return list
     */
    public ArrayList<Token> getList() {
        return new ArrayList<>(this.list);
    }

    /**
     * Agrega otro token a la lista actual
     * @param t
     */
    public void add(Token t){
        list.add(t);
    }


    /**
     * Revisa el elemento al inicio de la lista
     * @return token
     */
    public Token peek () {
        return list.get(0);
    }

    /**
     * Remueve el elemento al inicio de la lista
     * @return token
     */

    public Token poll () {
        return list.remove(0);
    }

    /**
     * Obtiene el tamaño de la lista
     * @return count
     */
    public int size () {
        return list.size();
    }

    /**
     * Si ha sido evaluado o no
     * @return
     */
    public boolean isEvaluado() {
        return this.evaluado;
    }

}
