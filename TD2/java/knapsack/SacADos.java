/*
 * TLFIDM13 2019-2020: algorithmic
 */
package knapsack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Un Magasin et un Sac à remplir
 * <p>
 * Magasin : ArrayList
 * <p>
 * Sac : ArrayList
 *
 * @author MS
 * @version 03 01 2017
 */
public class SacADos {
    private List<Article> magasin; // les articles du magasin

    /**
     * @param magasin : list of articles
     */
    private SacADos(Article[] magasin) {
        this.magasin = new ArrayList<>();
        Collections.addAll(this.magasin, magasin);
    }

    public SacADos(File filePathArticles) throws FileNotFoundException {
        magasin = new ArrayList<>();
        Scanner sLine = new Scanner(filePathArticles);
        while (sLine.hasNext()) {
            int p = sLine.nextInt();
            int v = sLine.nextInt();
            String s = sLine.findInLine("[^ ].*");
            magasin.add(new Article(p, v, s));
        }
        sLine.close();
    }

    /* renvoie le butin de plus grande valeur de poids maximum poidsMax */
    private List<Article> voler(int poidsMax) {

        return volerAux(poidsMax, magasin.size() - 1);
    }

    /**
     * renvoie le butin de plus grande valeur de poids maximum poidsMax, pris
     * dans les objets d'indice <= j complexité dans le pire des cas :
     * Theta(2^j)
     */
    private List<Article> volerAux(int poidsMax, int j) {/*
        if (j >= 0) {
            if (magasin[j].getPoids() > poidsMax) {
                return volerAux(poidsMax, j - 1);
            }
            else
                return Math.max(volerAux(poidsMax - magasin[j].getPoids(), j - 1) + magasin[j].getValeur(), volerAux(poidsMax, j - 1) + 0);
        }
        return 0;*/
    }


    private void afficheSolution(List<Article> sac) {
        int p = 0;
        int v = 0;
        for (Article article : sac) {

            System.out.println(">> " + article.getNom() + " de valeur " + article.getValeur() + " et de poids "
                    + article.getPoids());
            p += article.getPoids();
            v += article.getValeur();
        }
        System.out.println("Sac de " + sac.size() + " articles de valeur totale " + v + " et de poids total " + p);
    }

    private int valeur(List<Article> sac) {
        // int v = 0;
        // for (Article article : sac) {
        // v += article.getValeur();
        // }
        // return v;
        return sac.stream().mapToInt(Article::getValeur).sum();
    }

    public static void main(String[] args) {
        List<Article> sac;

        System.out.println("Magasin bio");

        Article[] bio = {new Article(100, 5000, "collier en or"), new Article(1000, 1, "spaghetti"),
                new Article(100, 5, "amandes décortiquées biologiques"), new Article(1, 50, "saffran"),
                new Article(1000, 1, "flageolets en conserve"), new Article(100, 10, "faux filet"),
                new Article(10, 2, "purée biologique"), new Article(300, 10, "merguez")};

        SacADos m1 = new SacADos(bio);
        sac = m1.voler(311);
        System.out.println("Sac de poids max 130: " + m1.valeur(sac));
        /*
        m1.afficheSolution(sac);
        System.out.println();
        sac.clear();

        SacADos m2 = new SacADos(bio);
        sac = m2.voler(311);
        System.out.println("Sac de poids max 311: " + m2.valeur(sac));
        m2.afficheSolution(sac);
        System.out.println();
        sac.clear();

        SacADos m3 = new SacADos(bio);
        sac = m3.voler(1000);
        System.out.println("Sac de poids max 1000: " + m3.valeur(sac));
        m3.afficheSolution(sac);
        System.out.println();
        sac.clear();

        System.out.println("Sac1 de 64 objets");
        File filePathSac1 = new File("sac1.txt");*/
    }

}
