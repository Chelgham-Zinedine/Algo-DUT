/*
 * M415 2020-2021: additional algorithmics
 */
package cityplan;

import java.util.Scanner;

/**
 * City Plan: Google Hash Code 2018, see
 * https://codingcompetitions.withgoogle.com/hashcode/archive
 * ResidentialBuilding : projects of buildings in which residents live. A residential building project is described
 * by:
 * - its building plan
 * - its capacity – the number of residents living in the building (residentialCapacity)
 *
 * @author msyska
 * @version 0.1
 */
public class ResidentialBuilding extends BuildingProject {
    private int residentialCapacity;

    public ResidentialBuilding(Scanner s, int H, int W, int h, int w, int value) {
        super(s, H, W, h, w);
        this.residentialCapacity = value;
    }

    public void display() {
        System.out.println("Residential building");
        super.display();
    }
}
