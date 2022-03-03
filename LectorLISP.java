import java.util.ArrayList;
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



        String codigo = "( format t \"32\" a b ( ( k ) ( b a ) n ) a )";
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
            if(arr[i].startsWith("\"")){
                int j = i;
                String str =  "";
                while(!arr[j].endsWith("\"")){
                    str += " "+arr[j];
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
        
        Token tokList = new Token(new ArrayList<Token>(), null); //token que devolverá al final
        Token tokAbierto = tokList;      //token al que se le agrega actualmente

        for (int i = 0; i < l.size(); i++) {
            if(l.get(i).equals("(")){       //encuentra que se abre una nueva lista
                tokAbierto = new Token(new ArrayList<Token>(), tokAbierto);  
                //abre nuevo token de lista para agregar los siguientes. Toma el token abierto antes como
                //parámetro de token anterior.
            }
            else if(l.get(i).equals(")")){ //encuentra que se cierra lista
                tokAbierto.getAnterior().add(tokAbierto); //agrega el token recién cerrado al token anterior
                tokAbierto = tokAbierto.getAnterior(); //cambia el token abierto al token anterior
            }
            else{
                Token t = new Token(l.get(i), tokAbierto); //crea un token de string
                tokAbierto.add(t);              //agrega el nuevo token al token abierto
            }
        }
        return tokList;
    }


}
