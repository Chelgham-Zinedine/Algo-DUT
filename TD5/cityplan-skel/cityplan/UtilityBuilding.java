/*
 * M415 2020-2021: additional algorithmics
 */
package cityplan;

import java.util.Scanner;

/**
 * City Plan: Google Hash Code 2018, see
 * https://codingcompetitions.withgoogle.com/hashcode/archive
 * UtilityBuilding : projects of buildings which provide services to district residents. A utility building project is
 * described by:
 * - its building plan
 * - its type – an integer number (serviceValue) indicating the type of service provided
 *
 * @author msyska
 * @version 0.1
 */
public class UtilityBuilding extends BuildingProject {
    private int serviceValue;

    public UtilityBuilding(Scanner s, int H, int W, int h, int w, int value) {
        super(s, H, W, h, w);
        this.serviceValue = value;
    }

    public void display() {
        System.out.println("Utility building");
        super.display();
    }
}
