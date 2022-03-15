import java.util.Scanner;

public class VistaLISP {

    EvaluadorLISP ev =new EvaluadorLISP();
    LectorLISP lector =new LectorLISP();

    public void desplegarvista() {

    while (true) {
        Scanner scan = new Scanner(System.in);
        System.out.print(">> ");
        String c = scan.nextLine();
        Token t = lector.enlistar(c);
        System.out.print("<< ");
        System.out.println(ev.eval(t));
        }
    }
}
