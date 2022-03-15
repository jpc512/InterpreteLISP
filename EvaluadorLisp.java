import java.util.ArrayList;
import java.util.HashMap;

public class EvaluadorLISP {
    
    Token tokenList;    //Lista de tokens
    HashMap<String, Token> funciones = new HashMap<String, Token>();   //Diccionario de funciones definidas por el usuario

    public EvaluadorLISP(){}

    public String eval(Token t) {   //Ejecuta una lista de tokens
        if (!t.isEvaluado()) {
            String operador = eval(t.peek());
            String p1,p2,r;
            Token t1,t2;
            ArrayList<Token> lista = t.getLista();
            switch (operador) {
                case "+":
                p1 = eval(lista.get(1));
                p2 = eval(lista.get(2));                    
                r = Double.toString(Double.valueOf(p1) + Double.valueOf(p2));
                t.setValor(r);
                    break;

                case "-":
                p1 = eval(lista.get(1));
                p2 = eval(lista.get(2));                    
                r = Double.toString(Double.valueOf(p1) - Double.valueOf(p2));
                t.setValor(r);
                    break;
                
                case "*":
                p1 = eval(lista.get(1));
                p2 = eval(lista.get(2));                    
                r = Double.toString(Double.valueOf(p1) * Double.valueOf(p2));
                t.setValor(r);
                    break;
            
                case "/":
                p1 = eval(lista.get(1));
                p2 = eval(lista.get(2));                    
                r = Double.toString(Double.valueOf(p1) / Double.valueOf(p2));
                t.setValor(r);
                    break;

                case "defun":
                String key = eval(lista.get(1));                
                Token value = new Token(new ArrayList<Token>());
                t1 = lista.get(2);                  // para una funcion f(x) = x + 1
                t2 = lista.get(3);                  // key es el nombre de la funcion: f    
                value.add(t1);                      // value es el token que contiene
                value.add(t2);                      //      - la variable de la funcion: x
                funciones.put(key, value)  ;        //      - las instrucciones de la funcion: x + 1
                    break;

                default:
                Token fun = funciones.get(operador);        
                String x = eval(fun.getLista().get(0));     // nombre de la variable a sustituir
                Token arg = fun.getLista().get(1);          // expresion sin sustituir
                p1 = eval(lista.get(1));                    // valor de la variable por sustituir
                arg = sustituir(x, arg, p1);                // argumento con valores sustituidos
                r = eval(arg);
                t.setValor(r);
                    break;        
            }
        }



        return t.getValor();
    }

    public Token sustituir(String x, Token arg, String p1){
        Token arg2 = new Token(new ArrayList<Token>());
        for (Token t : arg.getLista()) {
            Token t2 = new Token();
            if (t.isEvaluado()) {
                if (t.getValor().equals(x)) {
                    t2.setValor(p1);
                } else {
                    t2.setValor(t.getValor());
                }
            } else {
               t2 = sustituir(x, t, p1);
            }
            arg2.add(t2);
        }
        return arg2;
    }

    public void defun(Token funcion) { //Definir funcion
        //Token name = funcion.pull(); //Nombre de la funcion
        //funciones.put(name.getValor(), funcion); //Agrega la funcion al diccionario de funciones
    }

    public Token evalfun(Token funCall) { //Evaluar funcion
        Token func = funciones.get(funCall.getValor());
        //To do
        return null;
    }

}
