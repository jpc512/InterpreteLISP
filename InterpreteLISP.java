

public class InterpreteLISP {
    public static void main(String[] args) {
        
        VistaLISP vist =new VistaLISP();

        vist.desplegarvista();

        /**
         * CODIGO PARA PROBAR: (usar espacios entre cada término)
         * 
         * Conversión de Fahrenheit a Centígrados:
         *      ( defun FtoC ( temp ) ( / ( - temp 32 ) 1.8 ) )
         *      FtoC ( 100 )
         *
         * Serie de Fibonacci:
         *      ( defun fibonacci ( n ) ( cond  ( ( = n 0 ) 1 ) ( ( = n 1 ) 1 ) 
         *         ( t ( + ( fibonacci ( - n 2 ) ) ( fibonacci ( - n 1 ) ) ) ) ) )
         * 
         * Factorial:
         *      ( defun f! ( n ) ( cond ( ( = n 0 ) 1 ) ( t ( * n ( f! ( - n 1 ) ) ) ) ) )
         * 
         * */

    }
}
