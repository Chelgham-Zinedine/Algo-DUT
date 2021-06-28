/*
 * M415 2020-2021: additional algorithmics
 */
package cityplan;

/*
 Programming assignment : City Plan

 Name: Michel Syska

 NetID: syska

 Date: Monday March 4

 Precept: P01

 Compilation: javac *.java
 Execution: java cityplan/CityPlanMain

 Dependencies: BuildingProject; BuildingPlan; ResidentialBuilding; UtilityBuilding, CityPlan

 Description: Given a set of building projects, your task is to decide which of the available projects to build and
 where, in order to maximize residential capacity and availability of utilities to residents.

 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * City Plan: Google Hash Code 2018, see
 * https://codingcompetitions.withgoogle.com/hashcode/archive
 *
 * @author msyska
 * @version 0.1
 */
public class CityPlanMain {

    public static void main(String[] args) throws FileNotFoundException {
        // read data from file
        Scanner s = new Scanner(new File("cityplan-skel/25x30.in"));
     /*   CityPlan cp = new CityPlan(s);

        // see what first project is
        cp.getBp()[0].display();
        //cp.getBp()[1].display();

        // place projects one by one with handmade coordinates
        cp.placeAt(0, new Point(0, 0));
        cp.placeAt(1, new Point(0, 3));


        // see what happens when place is not empty
        try {
            cp.placeAt(1, new Point(0, 3));
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        // check if place at (21,21) is empty for project number 18
        System.out.println((cp.isEmptyPlaceAt(18, new Point(21, 21))));

        cp.placeAt(2, new Point(0, 8));
        cp.placeAt(3, new Point(0, 17));
        cp.placeAt(4, new Point(0, 23));
        cp.placeAt(5, new Point(4, 0));
        cp.placeAt(6, new Point(8, 0));
        cp.placeAt(7, new Point(13, 0));
        cp.placeAt(8, new Point(17, 0));
        cp.placeAt(9, new Point(17, 4));
        cp.placeAt(10, new Point(21, 3));
        cp.placeAt(11, new Point(11, 7));
        cp.placeAt(12, new Point(4, 4));
        cp.placeAt(13, new Point(6, 9));
        cp.placeAt(14, new Point(11, 11));
        cp.placeAt(15, new Point(20, 7));
        cp.placeAt(16, new Point(11, 16));
        cp.placeAt(17, new Point(18, 16));
        cp.placeAt(18, new Point(21, 21));
        cp.placeAt(19, new Point(13, 24));
        cp.placeAt(20, new Point(6, 19));
        cp.placeAt(21, new Point(6, 27));

        // display result of placement
        cp.display();


        // check again if place at (21,21) is empty for project number 18
        System.out.println((cp.isEmptyPlaceAt(18, new Point(21, 21))));

        // compute distances between projects
        System.out.println(cp.getPlacement()[5] + " - " + cp.getPlacement()[19]);
        System.out.println("Distance between 5 and j: " + cp.distance(5, 19));
        System.out.println("Distance between 5 and 5: " + cp.distance(5, 5));
        System.out.println("Distance between j and j: " + cp.distance(19, 19));
        System.out.println("Distance between j and k: " + cp.distance(19, 20));
        System.out.println("Distance between 6 and 9: " + cp.distance(6, 9));
        System.out.println("Distance between c and g: " + cp.distance(12, 16));

        for (int i = 0; i < cp.getBp().length; i++) {
            for (int j = 0; j < cp.getBp().length; j++) {
                System.out.printf(" %3d ", cp.distance(i, j));
            }
            System.out.println();
        }
    */
        // naive left to right placement
        //Scanner s2 = new Scanner(new File("input/25x30.in"));
        CityPlan cp2 = new CityPlan(s);
        cp2.solve();
        System.out.println("dist" + cp2.distance(0,1));
    }
}

