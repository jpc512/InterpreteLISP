import java.util.Scanner;

public class InterpreteLISP {
    public static void main(String[] args) {
        
        
        EvaluadorLISP ev =new EvaluadorLISP();
        LectorLISP lector =new LectorLISP();

        // Token t4 = lector.enlistar("( + ( - 1 ( 1 ) ) 5 )");
        // ev.eval(t4);



        // Token t1 = lector.enlistar("( + n 1 )");
        // Token t2 = ev.sustituir("n",t1,"1");
        // System.out.println(t1.getValor());
        // System.out.println(t2.getValor());
        // System.out.println(ev.eval(t2));

        // Token t2 = lector.enlistar("( + n * ( n * x ) / 1 )");
        // t2 = ev.sustituir("n",t2,"p");
        // System.out.println("( + p * ( p * x ) / 1 )"+ t2.getValor());

        Token t1 = lector.enlistar("( defun f ( n ) ( + 1 n ) )");
        ev.eval(t1);
        Token t2 = lector.enlistar("( f ( 1 ) )");
        System.out.println("2.0" + ev.eval(t2));

        Token t3 = lector.enlistar("( defun FtoC ( temp ) ( / ( - temp 32 ) 1.8 ) )");
        ev.eval(t3);
        Token t4 = lector.enlistar("( FtoC ( 1 ) )");
        System.out.println(ev.eval(t4));

        while (true) {
            Scanner scan = new Scanner(System.in);
            String c = scan.nextLine();
            Token t = lector.enlistar(c);
            System.out.println(ev.eval(t));
        }

    }
}
