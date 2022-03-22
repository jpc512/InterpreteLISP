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
        assertEquals("7.0", vars.getValor());


        Token exp = lector.enlistar("( * 6 ( / 8 4 ) )");
        ev.eval(exp);
        assertEquals("12.0", exp.getValor());

        Token t3 = lector.enlistar("( * ( + ( - 1 1 ) 2 ) ( / 8 -2 ) )");
        ev.eval(t3);
        assertEquals("-8.0", t3.getValor());

        Token t4 = lector.enlistar("( + ( - 1 ( 1 ) ) 5 )");
        ev.eval(t4);
        assertEquals("5.0", t4.getValor());
    }


    @Test
    //Prueba para demostrar que enciende
    public void testCond(){
        Token vars = lector.enlistar("( cond ( t si ) )");
        ev.eval(vars);
        assertEquals("si", vars.getValor());
    }

    @Test
    //Prueba para demostrar que enciende
    public void testPredicados(){
        Token vars = lector.enlistar("( atom p )");
        ev.eval(vars);
        assertEquals("t", vars.getValor());

        Token t2 = lector.enlistar("( list ( 1 2 3 ( ) ) )");
        ev.eval(t2);
        assertEquals("t", t2.getValor());

        Token t3 = lector.enlistar("( equal 4.0 ( / 8 2 ) )");
        ev.eval(t3);
        assertEquals("t", t3.getValor());
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
