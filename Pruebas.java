import org.junit.Test;

import junit.framework.TestCase;


public class Pruebas extends TestCase{
    EvaluadorLISP ev =new EvaluadorLISP();
    LectorLISP lector =new LectorLISP();


    @Test
    //Prueba para demostrar que enciende
    public void testOpAritmeticas(){
        Token vars = lector.enlistar("( + 6 1 )");
        ev.eval(vars);
        assertEquals("7.0", vars.getAtom());


        Token exp = lector.enlistar("( * 6 ( / 8 4 ) )");
        ev.eval(exp);
        assertEquals("12.0", exp.getAtom());

        Token t3 = lector.enlistar("( * ( + ( - 1 1 ) 2 ) ( / 8 -2 ) )");
        ev.eval(t3);
        assertEquals("-8.0", t3.getAtom());

        Token t4 = lector.enlistar("( + ( - 1 ( 1 ) ) 5 )");
        ev.eval(t4);
        assertEquals("5.0", t4.getAtom());
    }


    @Test
    //Prueba para demostrar que enciende
    public void testSustituir(){
        Token vars = lector.enlistar("( + n 1 )");
        vars = ev.sustituir("n",vars,"1");
        assertEquals("( + 1 1 )", vars.getAtom());

        Token exp = lector.enlistar("( + n * ( n * x ) / 1 )");
        exp = ev.sustituir("n",exp,"p");
        assertEquals("( + p * ( p * x ) / 1 )", exp.getAtom());
    }

    @Test
    //Prueba para demostrar que enciende
    public void testDefun(){
        Token vars = lector.enlistar("( defun f ( n ) ( + n 1 ) )");
        ev.eval(vars);
        Token exp = lector.enlistar("( f ( 1 ) )");
        assertEquals("2.0", ev.eval(exp));
        

        Token t3 = lector.enlistar("( defun FtoC ( temp ) ( / ( - temp 32 ) 1.8 ) )");
        ev.eval(t3);
        Token t4 = lector.enlistar("( FtoC ( 102.2 ) )");
        assertEquals("39.0", ev.eval(t4));
    }
}
