import java.util.HashMap;

public class EvaluadorLisp {
    
    Token tokenList;    //Lista de tokens
    HashMap<String, Token> funciones;   //Diccionario de funciones definidas por el usuario

    public EvaluadorLisp () {
        tokenList = new Token();
        funciones = new HashMap<>();
    }

    public Token eval() {   //Ejecuta una lista de tokens
        //To do
        return null;
    }

    public void defun(Token funcion) { //Definir funcion
        Token name = funcion.poll(); //Nombre de la funcion
        funciones.put(name.getValor(), funcion); //Agrega la funcion al diccionario de funciones
    }

    public Token evalfun(Token funCall) { //Evaluar funcion
        Token func = funciones.get(funCall.getValor());
        //To do
        return null;
    }

}
