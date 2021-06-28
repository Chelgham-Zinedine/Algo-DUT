/*
 * IUT INFO 2019-2020: additional algorithmic
 */
package knapsack;

import java.util.Comparator;

/**
 * classe pour les articles d'un magasin
 */
public class Article implements Comparable<Article> {

    private int poids;
    private int valeur;
    private String nom;

    Article(int p, int v, String n) {
        this.poids = p;
        this.valeur = v;
        this.nom = n;
    }

    int getPoids() {
        return this.poids;
    }

    int getValeur() {
        return this.valeur;
    }

    String getNom() {
        return this.nom;
    }

    public String toString() {
        return " nom : " + this.nom + "poids : " + this.poids + " valeur :"
                + this.valeur;
    }

    @Override
    public int compareTo(Article article) {
        return Integer.compare(this.poids, article.poids);
    }

    public static Comparator<Article> ArticleValeurComparator = new Comparator<>() {
        public int compare(Article arg0, Article arg1) {
            return Integer.compare(arg1.valeur, arg0.valeur);
            // plus grand au plus petit
        }
    };

    public static Comparator<Article> ArticleValeurPoidsComparator = (arg0, arg1) -> {
        return Double.compare((double) arg1.valeur / (double) arg1.poids,
                (double) arg0.valeur / (double) arg0.poids);
        // plus grand au plus petit
    };

}