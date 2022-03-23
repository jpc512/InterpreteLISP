import java.util.ArrayList;
import java.util.HashMap;

public class EvaluadorLISP {
    
    private HashMap<String, Token> funciones;   //Diccionario de funciones definidas por el usuario
    private HashMap<String, String> variables;  //Diccionario de variables definidas por el usuario

    /**
     * Constructor de EvaluadorLISP
     */
    public EvaluadorLISP(){
        funciones = new HashMap<>();
        variables = new HashMap<>();
    }

    /**
     * Evalua los tokens y les da su valor final.
     * Efectua algunas operaciones basicas.
     * @param token
     * @return String atom
     */
    public String eval(Token token) {   //Ejecuta una lista de tokens
        Token tokenEval = token;
        if (!token.isEvaluado()) {
            String operador = eval(token.poll());
            String op1,op2,r;       //Operadores para operaciones aritmaticas
            Token name,vars,exp,params;    //Tokens para definir funciones
            switch (operador) {
                case "+":                   //Suma
                    op1 = eval(token.poll());
                    op2 = eval(token.poll());                    

                    r = Double.toString(Double.valueOf(op1) + Double.valueOf(op2));
                    tokenEval = new Token(r);
                    break;

                case "-":                   //Resta
                    op1 = eval(token.poll());
                    op2 = eval(token.poll());                    

                    r = Double.toString(Double.valueOf(op1) - Double.valueOf(op2));
                    tokenEval = new Token(r);
                    break;
                

                case "*":                   //Multiplicacion
                    op1 = eval(token.poll());
                    op2 = eval(token.poll());                    

                    r = Double.toString(Double.valueOf(op1) * Double.valueOf(op2));
                    tokenEval = new Token(r);
                    break;
            
                case "/":                   //Division
                    op1 = eval(token.poll());
                    op2 = eval(token.poll());                    

                    r = Double.toString(Double.valueOf(op1) / Double.valueOf(op2));
                    tokenEval = new Token(r);
                    break;
                
                case "setq":                //Definir variables
                    setQuery(token.getList());
                    tokenEval = new Token("( )");
                    break;

                case "defun":               //Definif funcion
                    name = token.poll();                
                    vars = token.poll();                
                    exp = token.poll();                  

                    defun(name, vars, exp);
                    break;

                case "quote":               // Dejar expresion sin evaluar
                    tokenEval = new Token(token.getAtom());
                    break;

                case "cond":                //Evalua condiciones
                    tokenEval = new Token(cond(token.getList()));
                    break;

                case "atom":                //Verifica si el token es un atomo
                    String isAtom = token.poll().isEvaluado() ? "t": "nil";
                    tokenEval = new Token(isAtom);
                    break;
                
                case "list":                //Verifica si el token es una lista
                    String isList = !token.poll().isEvaluado() ? "t": "nil";
                    tokenEval = new Token(isList);
                    break;

                case "equal":               //Verifica si dos tokens son iguales
                    op1 = eval(token.poll());
                    op2 = eval(token.poll());
                    r = (op1 == op2) ? "t": "nil";
                    tokenEval = new Token(r);
                    break;

                default:

                    //Busca si el termino es una variable o una funcion registrada
                    if (this.funciones.containsKey(operador)) {
                        params = token.poll();
                        tokenEval = new Token(
                            evalfun(operador, params)
                            );
                    } else {
                        tokenEval = new Token(
                            this.variables.get(operador)
                            );
                    }
                break;        
            }
        }
        return tokenEval.getAtom();
    }

    /**
     * Agrega las variables definidas al diccionario de variables
     * @param g_vars
     */
    private void setQuery (ArrayList<Token> g_vars) {
        for (int i = 0; i < g_vars.size();) {
            Token key = g_vars.get(i++);
            Token value = g_vars.get(i++);
            this.variables.put(key.getAtom(), eval(value));
        }
    }

    /**
     * Agrega la funcion al diccionario de funciones
     * @param name
     * @param vars
     * @param exp
     */
    private void defun(Token name, Token vars, Token exp) { //Definir funcion
        String key = eval(name);                
        Token value = new Token(new ArrayList<Token>());   // value es el token que contiene la expresion
        value.add(vars);                      //  variables de la funcion
        value.add(exp);                       //  instrucciones de la funcion
        funciones.put(key, value);        
    }

    /**
     * Evalua una funcion llamada por el usuario
     * @param callFun
     * @param params
     * @return
     */
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

    /**
     * Define las variables usadas en funciones
     * @param vars
     * @param params
     * @return defVars
     */
    private HashMap<String,String> defVars (Token vars, Token params) {
        HashMap <String, String> defVars = new HashMap<>();     //Mapeo de parametros
        for (int i = 0; i < vars.size(); i++) {
            defVars.put(
                eval(vars.getList().get(i)),
                eval(params.getList().get(i))
            );
        }
        return defVars;
    }

    /**
     * Sustituye las variables de una funcion por los valores dados por el usuario
     * @param exp
     * @param vals
     * @return newExp
     */
    private Token sustituir(Token exp, HashMap <String, String> vals){
        Token newExp = new Token(new ArrayList<Token>());       //Token con nueva expresion
        //Se inicia el reemplazo
        for (Token t : exp.getList()) {
            Token t2 = new Token(t.getAtom());
            if (t.isEvaluado()) {
                String atom = t.getAtom();
                if (vals.containsKey(atom)) {
                    t2 = new Token(vals.get(atom));
                }
            } else {
               t2 = new Token(sustituir(t, vals).getList());
            }
            newExp.add(t2);
        }
        return newExp;
    }

    /**
     * Evalua una serie de condiciones. Si se cumple una, se evalua su expresion 
     * asociada y se retorna su valor.
     * Si no se cumple, pasa a la siguiente. 
     * Si ninguna se cumple, retorna nill.
     * @param expresions
     * @return value o nill
     */
    private String cond (ArrayList<Token> expresions) {
        String value;   //Valor final de la expresion
        for (Token test : expresions) {
            if (test.size() == 1) {return eval(test.poll());} //Si no hay condicion, evalua la expresion
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

    /**
     * Evalua una condicion. Si se cumple ejecuta una accion o retorna nill.
     * @param test
     * @param action
     * @return expresion evaluada o nill
     */
    private String condTest (Token test, Token action) {
        Token condition = test;
        Double a = 0.0; Double b = 0.0;
        if (!test.isEvaluado()) { //Determina si la condicion requiere valores
            condition = test.poll();
            a = Double.valueOf(test.poll().getAtom()); 
            b = Double.valueOf(test.poll().getAtom());
        }
        switch (condition.getAtom()) {
            case "=":       //Igualdad
                if (Math.abs(a - b) < 0.0001){
                    return eval(action);
                 } else { 
                    return "nil";
                }

            case "/=":      //Diferencia
                return (a != b) ? eval(action) : "nil";

            case ">=":      //Mayor o igual
                return (a >= b) ? eval(action) : "nil";

            case ">":       //Mayor
                return (a > b) ? eval(action) : "nil";

            case "<=":      //Menor o igual
                return (a <= b) ? eval(action) : "nil";

            case "<":       //Menor
                return (a < b) ? eval(action) : "nil";

            case "t":       //everdadero, efectua la accion
                return eval(action);

            default:
                return "nil";
        }
    }

}
