// Chelgham G2
package knapsack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * un magasin contenant des articles et un voleur
 * <p>
 * renvoie uniquement la valeur
 */
public class Magasin {
    private Article[] magasin; // les articles du magasin

    // un constructeur par défaut pour créer un magasin (merci Helene)
    private Magasin() {
        magasin = new Article[8];
        magasin[0] = new Article(100, 5000, "collier en or");
        magasin[1] = new Article(1000, 1, "spaghetti");
        magasin[2] = new Article(100, 5, "amandes décortiquées biologiques");
        magasin[3] = new Article(1, 50, "saffran");
        magasin[4] = new Article(1000, 1, "flageolets en conserve");
        magasin[5] = new Article(100, 10, "faux filet");
        magasin[6] = new Article(10, 2, "purée biologique");
        magasin[7] = new Article(300, 10, "merguez");
    }


    public Magasin(Article[] m) {
        magasin = new Article[m.length];
        for (int i = 0; i < m.length; i++) {
            magasin[i] = new Article(m[i].getPoids(), m[i].getValeur(), m[i].getNom());
        }
    }


    private Magasin(File filePathArticles, int size) throws FileNotFoundException {
        magasin = new Article[size];
        Scanner sLine = new Scanner(filePathArticles);
        int i = 0;
        while (sLine.hasNext()) {
            int p = sLine.nextInt();
            int v = sLine.nextInt();
            String s = sLine.findInLine("[^ ].*");
            magasin[i++] = new Article(p, v, s);
        }
        sLine.close();
    }

    /* renvoie le butin de plus grande valeur de poids maximum poidsMax */
    private int voler(int poidsMax) {

        return volerAux(poidsMax, magasin.length - 1);
    }

    /*
     * renvoie le butin de plus grande valeur de poids maximum poidsMax, pris
     * dans les objets d'indice <= j
     */
    private int volerAux(int poidsMax, int j) {
        if (j >= 0) {
            if (magasin[j].getPoids() > poidsMax) {
                return volerAux(poidsMax, j - 1);
            }
            else
                return Math.max(
                                 volerAux(poidsMax - magasin[j].getPoids(), j - 1) + magasin[j].getValeur(),
                                 volerAux(poidsMax, j - 1) + 0
                                );
        }
        return 0;
    }



    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Magasin bio");
        Magasin m1 = new Magasin();
        System.out.println(m1.voler(1000));

//        System.out.println("Sac de poids max 30: " + m1.voler(30));
//        System.out.println();
//
//        Magasin m2 = new Magasin();
//        System.out.println("Sac de poids max 311: " + m2.voler(311));
//        System.out.println();
//
//        Magasin m3 = new Magasin();
//        System.out.println("Sac de poids max 1000: " + m3.voler(1000));
//        System.out.println();
//
//        System.out.println("Sac1 de 64 objets");
//        File filePathSac1 = new File("sac1.txt");
//        Magasin m4 = new Magasin(filePathSac1, 64);
//        System.out.println("Sac de poids max 4000: " + m4.voler(4000) + " ");
//        System.out.println();
//
//        System.out.println("Sac-zok de 22 objets");
//        File filePathSac = new File("sac-zok.txt");
//
//        Magasin m5 = new Magasin(filePathSac, 22);
//        System.out.println("Sac de poids max 400 (voler brute force): " + m5.voler(400));
//        System.out.println();
    }

}
