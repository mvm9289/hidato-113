/*  Class Utiles:
    Descripcion: Proporciona operaciones utiles para el tratamiento de Objetos.
    Autor: alex.catarineu
    Revisado: 20/11/2009 09:20 */

package Utiles;

public class Utiles {

    public static int[] copiarArray(int array[]) {

        int res[] = new int[array.length];
        for (int i = 0; i < array.length; ++i)
            res[i] = array[i];

        return res;
    }

    public static int[][] copiarMatriz(int matriz[][]) {

        int res[][] = new int[matriz.length][];
        for (int i = 0; i < matriz.length; ++i)
            res[i] = copiarArray(matriz[i]);
        
        return res;
    }

    public static boolean igualArray(int a1[], int a2[]) {
        int n1 = a1.length;
        int n2 = a2.length;
        if (n1 != n2) return false;
        for (int i = 0; i < n1; ++i)
            if (a1[i] != a2[i])
                return false;
        return true;
    }
    
    public static boolean igualMatriz(int a1[][], int a2[][]) {

        int n1 = a1.length;
        int n2 = a2.length;
        if (n1 != n2) return false;
        for (int i = 0; i < n1; ++i)
            if (!igualArray(a1[i], a2[i]))
                return false;

        return true;
    }
}
