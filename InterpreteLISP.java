

public class InterpreteLISP {
    public static void main(String[] args) {
        
        VistaLISP vist =new VistaLISP();

        vist.desplegarvista();


        // Token t4 = lector.enlistar("( + ( - 1 ( 1 ) ) 5 )");
        // ev.eval(t4);



        // Token vars = lector.enlistar("( + n 1 )");
        // Token exp = ev.sustituir("n",vars,"1");
        // System.out.println(vars.getValor());
        // System.out.println(exp.getValor());
        // System.out.println(ev.eval(exp));

        // Token exp = lector.enlistar("( + n * ( n * x ) / 1 )");
        // exp = ev.sustituir("n",exp,"p");
        // System.out.println("( + p * ( p * x ) / 1 )"+ exp.getValor());

        // Token vars = lector.enlistar("( defun f ( n ) ( + 1 n ) )");
        // ev.eval(vars);
        // Token exp = lector.enlistar("( f ( 1 ) )");
        // System.out.println("2.0" + ev.eval(exp));

        // Token t3 = lector.enlistar("( defun FtoC ( temp ) ( / ( - temp 32 ) 1.8 ) )");
        // ev.eval(t3);
        // Token t4 = lector.enlistar("( FtoC ( 1 ) )");
        // System.out.println(ev.eval(t4));
    }
}
