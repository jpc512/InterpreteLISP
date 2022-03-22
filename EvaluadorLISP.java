import java.util.ArrayList;
import java.util.HashMap;

public class EvaluadorLISP {
    
    private HashMap<String, Token> funciones;   //Diccionario de funciones definidas por el usuario

    public EvaluadorLISP(){
        funciones = new HashMap<>();
    }

    public String eval(Token t) {   //Ejecuta una lista de tokens
        Token tokenEval = t;
        if (!t.isEvaluado()) {
            String operador = eval(t.pull());
            String op1,op2,r;       //Operandos para operaciones aritmaticas
            Token name,vars,exp,params;    //Tokens para definir funciones
            switch (operador) {
                case "+":
                    op1 = eval(t.pull());
                    op2 = eval(t.pull());                    
                    r = Double.toString(Double.valueOf(op1) + Double.valueOf(op2));
                    tokenEval = new Token(r);
                    break;

                case "-":
                    op1 = eval(t.pull());
                    op2 = eval(t.pull());                    
                    r = Double.toString(Double.valueOf(op1) - Double.valueOf(op2));
                    tokenEval = new Token(r);
                    break;
                
                case "*":
                    op1 = eval(t.pull());
                    op2 = eval(t.pull());                    
                    r = Double.toString(Double.valueOf(op1) * Double.valueOf(op2));
                    tokenEval = new Token(r);
                    break;
            
                case "/":
                    op1 = eval(t.pull());
                    op2 = eval(t.pull());                    
                    r = Double.toString(Double.valueOf(op1) / Double.valueOf(op2));
                    tokenEval = new Token(r);
                    break;

                case "defun":
                    name = t.pull();                
                    vars = t.pull();                
                    exp = t.pull();                  
                    defun(name, vars, exp);
                    break;

                case "cond":
                    tokenEval = new Token(cond(t.getLista()));
                    break;

                case "atom":
                    String isAtom = t.poll().isEvaluado() ? "t": "nil";
                    tokenEval = new Token(isAtom);
                    break;
                
                case "list":
                    String isList = !t.poll().isEvaluado() ? "t": "nil";
                    tokenEval = new Token(isList);
                    break;

                case "equal":
                    op1 = eval(t.poll());
                    op2 = eval(t.poll());
                    r = (op1 == op2) ? "t": "nil";
                    tokenEval = new Token(r);
                    break;

                default:
                    params = t.poll();
                    tokenEval = new Token(

                        evalfun(operador, params)
                        );
                    break;        
            }
        }

        return tokenEval.getValor();
    }

    private Token sustituir(Token exp, HashMap <String, String> vals){
        Token newExp = new Token(new ArrayList<Token>());       //Token con nueva expresion
        //Se inicia el reemplazo
        for (Token t : exp.getLista()) {
            Token t2 = new Token(t.getValor());
            if (t.isEvaluado()) {
                String atom = t.getValor();
                if (vals.containsKey(atom)) {
                    t2 = new Token(vals.get(atom));
                }
            } else {
               t2 = new Token(sustituir(t, vals));
            }
            newExp.add(t2);
        }
        return newExp;
    }

    private HashMap<String,String> defVars (Token vars, Token params) {
        HashMap <String, String> defVars = new HashMap<>();     //Mapeo de parametros
        for (int i = 0; i < vars.size(); i++) {
            defVars.put(
                eval(vars.getLista().get(i)),
                eval(params.getLista().get(i))
            );
        }
        return defVars;
    }

    private void defun(Token name, Token vars, Token exp) { //Definir funcion
        String key = eval(name);                
        Token value = new Token(new ArrayList<Token>());   // value es el token que contiene la expresion
        value.add(vars);                      //  variables de la funcion
        value.add(exp);                       //  instrucciones de la funcion
        funciones.put(key, value)  ;        
    }

    private String evalfun(String callFun, Token params) { //Evaluar funcion

        Token fun = new Token(funciones.get(callFun));        
        Token vars = fun.poll();         // variables a sustituir
        Token exp = fun.poll();          // expresion sin sustituir                
        HashMap<String,String> vals = defVars(vars, params);
        Token newExp = sustituir(                 // expresion con variables sustituidas
            exp, 
            vals);                
        return eval(newExp);
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
        Token condition = test;
        Double a = 0.0; Double b = 0.0;
        if (!test.isEvaluado()) {
            condition = test.poll();
            a = Double.valueOf(test.poll().getValor()); 
            b = Double.valueOf(test.poll().getValor());
        }
        switch (condition.getValor()) {
            case "=":
                if (Math.abs(a - b) < 0.0001){
                    return eval(action);
                 } else { 
                    return "nil";
                }

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
