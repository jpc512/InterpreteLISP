import org.junit.Test;

import junit.framework.TestCase;


public class Pruebas extends TestCase{
    EvaluadorLISP ev =new EvaluadorLISP();
    LectorLISP lector =new LectorLISP();


    @Test
    //Prueba para demostrar que enciende
    public void testOpAritmeticas(){
        Token t1 = lector.enlistar("( + 6 1 )");
        ev.eval(t1);
        assertEquals("7.0", t1.getValor());


        Token t2 = lector.enlistar("( * 6 ( / 8 4 ) )");
        ev.eval(t2);
        assertEquals("12.0", t2.getValor());

        Token t3 = lector.enlistar("( * ( + ( - 1 1 ) 2 ) ( / 8 -2 ) )");
        ev.eval(t3);
        assertEquals("-8.0", t3.getValor());

        Token t4 = lector.enlistar("( + ( - 1 ( 1 ) ) 5 )");
        ev.eval(t4);
        assertEquals("5.0", t4.getValor());
    }


    @Test
    //Prueba para demostrar que enciende
    public void testSustituir(){
        Token t1 = lector.enlistar("( + n 1 )");
        t1 = ev.sustituir("n",t1,"1");
        assertEquals("( + 1 1 )", t1.getValor());

        Token t2 = lector.enlistar("( + n * ( n * x ) / 1 )");
        t2 = ev.sustituir("n",t2,"p");
        assertEquals("( + p * ( p * x ) / 1 )", t2.getValor());
    }

    @Test
    //Prueba para demostrar que enciende
    public void testDefun(){
        Token t1 = lector.enlistar("( defun f ( n ) ( + n 1 ) )");
        ev.eval(t1);
        Token t2 = lector.enlistar("( f ( 1 ) )");
        assertEquals("2.0", ev.eval(t2));
        

        Token t3 = lector.enlistar("( defun FtoC ( temp ) ( / ( - temp 32 ) 1.8 ) )");
        ev.eval(t3);
        Token t4 = lector.enlistar("( FtoC ( 102.2 ) )");
        assertEquals("39.0", ev.eval(t4));
    }
}
