package cl.emora.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TestCards {
    
    public static void main(String[] args) {
        TestCards test = new TestCards();
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese cartas en grupos de 5");
        List<String> manos = new ArrayList<String>();
        while (!sc.hasNext(Pattern.compile("0"))) {
            String linea = sc.nextLine(); 
            if (!linea.contains("0")) {
                manos.add(linea);
            }
        }
        for (String manoIn : manos) {
            test.ordenaMano2(test.parseMano(manoIn));
        }
    }

    private void ordenaMano(int[] mano) {
        for (int i = 0; i < mano.length - 1; i++) {
            for (int j = 0; j < mano.length - (i + 1); j++) {
                if (mano[j] > mano[j + 1]) {
                    int cartaAux = mano[j];
                    mano[j] = mano[j + 1];
                    mano[j + 1] = cartaAux;
                }
            }
        }
        imprimirMano(mano);
    }

    private void ordenaMano2(int[] mano) {
        for (int i = 0, j = 0; i < mano.length - 1;) {
            if (mano[j] > mano[j + 1]) {
                int cartaAux = mano[j];
                mano[j] = mano[j + 1];
                mano[j + 1] = cartaAux;
            }
            j = ++j < mano.length - (i + 1) ? j : ++i;
        }
        imprimirMano(mano);
    }

    private void imprimirMano(int[] mano) {
        for (int i = 0; i < mano.length; i++) {
            String carta;
            String spaceOrEndLine = i < (mano.length - 1) ? " " : "\n";
            switch (mano[i]) {
            case 1:
                carta = "A";
                break;
            case 11:
                carta = "J";
                break;
            case 12:
                carta = "Q";
                break;
            case 13:
                carta = "K";
                break;
            default:
                carta = Integer.toString(mano[i]);
                break;
            }
            System.out.print(carta + spaceOrEndLine);
        }
    }

    private int[] parseMano(String manoStr) {
        String[] strArr = manoStr.split(" ");
        int[] mano = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            mano[i] = Integer.parseInt(strArr[i].toUpperCase().replaceAll("K", "13").replaceAll("Q", "12").replaceAll("J", "11").replaceAll("A", "1"));
        }
        return mano;
    }
}
