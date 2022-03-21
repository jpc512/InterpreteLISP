import java.util.ArrayList;
import java.util.HashMap;

public class EvaluadorLISP {
    
    Token tokenList;    //Lista de tokens
    HashMap<String, Token> funciones;   //Diccionario de funciones definidas por el usuario

    public EvaluadorLISP(){
        funciones = new HashMap<>();
    }

    public String eval(Token t) {   //Ejecuta una lista de tokens
        if (!t.isEvaluado()) {
            String operador = eval(t.pull());
            String op1,op2,r;       //Operandos para operaciones aritmaticas
            Token name,vars,exp,params;    //Tokens para definir funciones
            switch (operador) {
                case "+":
                    op1 = eval(t.pull());
                    op2 = eval(t.pull());                    
                    r = Double.toString(Double.valueOf(op1) + Double.valueOf(op2));
                    t.setValor(r);
                    break;

                case "-":
                    op1 = eval(t.pull());
                    op2 = eval(t.pull());                    
                    r = Double.toString(Double.valueOf(op1) - Double.valueOf(op2));
                    t.setValor(r);
                    break;
                
                case "*":
                    op1 = eval(t.pull());
                    op2 = eval(t.pull());                    
                    r = Double.toString(Double.valueOf(op1) * Double.valueOf(op2));
                    t.setValor(r);
                    break;
            
                case "/":
                    op1 = eval(t.pull());
                    op2 = eval(t.pull());                    
                    r = Double.toString(Double.valueOf(op1) / Double.valueOf(op2));
                    t.setValor(r);
                    break;

                case "defun":
                    name = t.pull();                
                    vars = t.pull();                
                    exp = t.pull();                  
                    defun(name, vars, exp);
                    break;

                case "cond":
                    t.setValor(cond(t.getLista()));
                    break;

                case "atom":
                    String isAtom = t.pull().isEvaluado() ? "t": "nil";
                    t.setValor(isAtom);
                    break;
                
                case "list":
                    String isList = !t.pull().isEvaluado() ? "t": "nil";
                    t.setValor(isList);
                    break;

                case "equal":
                    op1 = eval(t.pull());
                    op2 = eval(t.pull());
                    r = (op1.equals(op2)) ? "t": "nil";
                    t.setValor(r);
                    break;

                default:
                    params = t.pull();
                    t.setValor(
                        evalfun(operador, params)
                        );
                    break;        
            }
        }

        return t.getValor();
    }

    private Token sustituir(ArrayList<Token> vars, ArrayList<Token> exp, ArrayList<Token> params){
        Token newExp = new Token(new ArrayList<Token>());       //Token con nueva expresion
        HashMap <String, String> defVars = new HashMap<>();     //Mapeo de parametros
        for (int i = 0; i < vars.size(); i++) {
            defVars.put(
                vars.get(i).getValor(),
                params.get(i).getValor()
            );
        }
        //Se inicia el reemplazo
        for (Token t : exp) {
            if (t.isEvaluado()) {
                String val = t.getValor();
                if (defVars.containsKey(val)) {
                    t.setValor(defVars.get(val));
                } else {
                    t.setValor(val);
                }
            } else {
               t = sustituir(vars, t.getLista(), params);
            }
            newExp.add(t);
        }
        return newExp;
    }

    private void defun(Token name, Token vars, Token exp) { //Definir funcion
        String key = eval(name);                
        Token value = new Token(new ArrayList<Token>());   // value es el token que contiene la expresion
        value.add(vars);                      //  variables de la funcion
        value.add(exp);                       //  instrucciones de la funcion
        funciones.put(key, value)  ;        
    }

    private String evalfun(String callFun, Token params) { //Evaluar funcion
        Token fun = funciones.get(callFun);        
        Token vars = fun.pull();         // variables a sustituir
        Token exp = fun.pull();          // expresion sin sustituir                
        exp = sustituir(                 // expresion con variables sustituidas
            vars.getLista(), 
            exp.getLista(), 
            params.getLista());                
        return eval(exp);
    }

    private String cond (ArrayList<Token> expresions) {
        String value;
        for (Token test : expresions) {
            value = condTest(
                test.pull(),    
                test.pull()     
                );
            if (value != "nil") {
                return value;
            }
        }
        return "nil";
    }

    private String condTest (Token test, Token action) {
        Token condition = test.pull();
        Double a = 0.0; Double b = 0.0;
        if (test.size() != 0) {
            a = Double.valueOf(test.pull().getValor()); 
            b = Double.valueOf(test.pull().getValor());
        }
        switch (condition.getValor()) {
            case "=":
                return (a == b) ? eval(action) : "nil";

            case "/=":
                return (a != b) ? eval(action) : "nil";

            case ">=":
                return (a >= b) ? eval(action) : "nil";

            case ">":
                return (a > b) ? eval(action) : "nil";

            case "<=":
                return (a <= b) ? eval(action) : "nil";

            case "<":
                return (a < b) ? eval(action) : "nil";

            case "t":
                return eval(action);

            default:
                return "";
        }
    }

}
