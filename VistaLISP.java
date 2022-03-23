import java.util.Scanner;

public class VistaLISP {

    EvaluadorLISP ev =new EvaluadorLISP();
    LectorLISP lector =new LectorLISP();

    /**
     * Muestra al usuario una interfaz para evaluar expresiones LISP
     */
    public void desplegarvista() {
        System.out.println("INTERPRETE LISP\nINSTRUCIONES");
        System.out.println("Para efectuar una operacion LISP, escriba la operacion");
        System.out.println("dentro de parentesis y dejando un espacion entre cada");
        System.out.println("termino ej. ( termino1 termino2 termino3 ... termino-n ).");
        System.out.println("Para pasar una operacion o funcion como parametro de otra,");
        System.out.println("esta debe estar en una lista.");
        System.out.println("q:\t\tSalir del programa\nc:\t\tlista de instrucciones\n");

        int count = 0;
        boolean activo = true;
        while (activo) {
            Scanner scan = new Scanner(System.in);
            System.out.printf("( %d ) >> ", count);
            String command = scan.nextLine();
            if (command.startsWith("(")){
                Token t = lector.enlistar(command);
                System.out.printf("( %d ) << ", count);
                System.out.println(ev.eval(t));
                count++;
            } else {
                switch (command) {
                    case "q":
                        activo = false;
                        break;
                    case "h":
                        vistaComandos();
                        break;
                    default:
                        System.out.println("error: entrada invalida");
                        break;
                }
            }
        } 
    }

    /**
     * Despliega un menu con las instrucciones de lisp implementadas
     */
    private void vistaComandos () {
        System.out.println("Instrucciones LISP disponibles:");
        System.out.println("Operadores + - * /\nPrefijos atom list equal");
        System.out.println("defun\nsetq\ncond\nquote\n");
    }
}
