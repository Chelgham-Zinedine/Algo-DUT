/*
 * IUT INFO 2019-2020: additional algorithmic
 */
package knapsack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
public class LitSacADos {
    private List<Article> magasin; // les articles du magasin

    /**
     * @param magasin : list of articles
     */
    private LitSacADos(Article[] magasin) {
        this.magasin = new ArrayList<>();
        Collections.addAll(this.magasin, magasin);
    }

    private LitSacADos(File filePathArticles) throws FileNotFoundException {
        magasin = new ArrayList<>();
        Scanner sLine;
        sLine = new Scanner(filePathArticles);
        while (sLine.hasNext()) {
            int p = sLine.nextInt();
            int v = sLine.nextInt();
            String s = sLine.findInLine("[^ ].*");
            magasin.add(new Article(p, v, s));
        }
        sLine.close();
    }

    private void display() {
        for (Article article : magasin) {
            System.out.printf("poids : %4d - valeur : %4d - %s\n", article.getPoids(), article.getValeur(),
                    article.getNom());
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Sac-zok de 22 objets");
        File filePathSac = new File("sac-zok.txt");
        LitSacADos m1 = new LitSacADos(filePathSac);
        m1.display();

        System.out.println("Sac de 9 objets");
        Article[] art = {new Article(250, 300, "Smart phone"), new Article(350, 450, "Notepad"),
                new Article(3420, 800, "Laptop"), new Article(120, 85, "Montre"), new Article(1250, 70, "Sac à dos"),
                new Article(80, 50, "Souris"), new Article(870, 100, "Disque externe"),
                new Article(9240, 1200, "Écran"), new Article(1800, 700, "Caméra")};

        LitSacADos m2 = new LitSacADos(art);
        m2.display();
    }
}
