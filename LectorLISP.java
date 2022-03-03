import java.util.ArrayList;
import java.util.Stack;
public class LectorLISP {
    
    public static void main(String[] args) {

        // Token t1 = new Token(new ArrayList<Token>(), null);
        // t1.add(new Token("a", t1));
        // t1.add(new Token("b", t1));
        // Token t2 = new Token(new ArrayList<Token>(), t1);
        // t1.add(t2);
        // t2.add(new Token("c", t2));
        // t2.add(new Token("d", t2));
        // t1.add(new Token("c", t2));
        // Token t3 = new Token("o", t2);
        // t2.add(t3);
        // System.out.println(t3.getAnterior().getValor());



        String codigo = "( format t \"32 33 34\" a b ( ( k ) ( b a ) n ) a )";
        Token tokList = enlistar(codigo);
        System.out.println(tokList.getValor());
    }

    public LectorLISP(){}

    /**
     * Toma un string y lo separa en átomos, tomando los elementos entre
     * comillas "" como átomos.
     * @param codigo
     * @return Lista de átomos
     */
    private static ArrayList<String> separar(String codigo) {

        String[] arr = codigo.split(" ");
        ArrayList<String> l = new ArrayList<String>();
        for (int i = 0; i < arr.length; i++) {
            if(arr[i].startsWith("\"")){ //Determina si el elemento se indica como una cadena de strings
                int j = i;
                String str =  "";
                while(!arr[j].endsWith("\"")){
                    if (str == "") {
                        str += arr[j];
                    } else {
                        str += " "+arr[j];
                    }
                    j++;
                }
                str += " "+arr[j];
                l.add(str);
                i = j;
            }else{
                l.add(arr[i]);
            }
        }  

        return l;
    }

    /**
     * Toma una lista de strings y los distribuye en la estructura de tokens
     * @param l lista de strings
     * @return token de lista
     */
    public static Token enlistar(String codigo){
        ArrayList<String> l = separar(codigo);

        Token tokenList = new Token();
        Stack<Token> stack =new Stack<>(); //Stack que controla en que Token se trabaja

        for (int i = 0; i < l.size(); i++) {
            if(l.get(i).equals("(")){       //encuentra que se abre una nueva lista
                stack.push(new Token(new ArrayList<Token>()));  
                //abre nuevo token de lista para agregar los siguientes. Agrega a la pila el token abierto.
            }
            else if(l.get(i).equals(")")){ //encuentra que se cierra lista
                Token closedTkn = stack.pop(); //se saca el token de la pila
                if (stack.empty()) {
                    tokenList = closedTkn;
                } else {
                    stack.peek().add(closedTkn); //agrega el token recién cerrado al token anterior
                }
            }
            else{
                Token t = new Token(l.get(i)); //crea un token de string
                stack.peek().add(t);              //agrega el nuevo token al token abierto
            }
        }
        return tokenList;
    }


}
