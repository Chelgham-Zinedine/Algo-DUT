/*
 * M415 2020-2021: additional algorithmics
 */

package cityplan;
import java.util.Arrays;
import java.util.Scanner;


/**
 * City Plan: Google Hash Code 2018, see
 * https://codingcompetitions.withgoogle.com/hashcode/archive
 * BuildingProject : either residential or utility type
 *
 * @author msyska
 * @version 0.1
 */
public class BuildingProject {

    private BuildingPlan plan;
    private int h; // the integer number of rows of the building project (1 ≤ h ≤ min(H, 50))
    private int w; // the integer number of columns of the building project (1 ≤ w ≤ min(W, 50))

    public BuildingProject(Scanner s, int H, int W, int h, int w) {
        try {
            if (h < 1 || h > 50 || h > H) throw new IllegalArgumentException("precondition 1 ≤ h ≤ min(H, 50) failed");
            this.h = h;
            if (w < 1 || w > 50 || w > W) throw new IllegalArgumentException("precondition 1 ≤ w ≤ min(W, 50) failed");
            this.w = w;
            char[][] plan = new char[h][w];
            s.nextLine();
            for (int i = 0; i < h; i++) {
                String row = s.nextLine();
                plan[i] = row.toCharArray();
            }
            this.plan = new BuildingPlan(plan);
        } catch (java.util.InputMismatchException e) {
            System.out.println("bad file format: " + e);
        } catch (java.lang.IllegalArgumentException e) {
            System.out.println("bad file input - " + e);
        }
        plan.checkPlan(); // e
    }

    public BuildingProject() {
    }

    public BuildingPlan getPlan() {
        return plan;
    }
    public int getH() {
        return h;
    }

    public int getW() {
        return w;
    }

    public void display(){
        plan.display();
    }
}