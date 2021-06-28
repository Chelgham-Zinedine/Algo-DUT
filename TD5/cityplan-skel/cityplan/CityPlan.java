/*
 * M415 2020-2021: additional algorithmics
 */
//CHELGHAM
package cityplan;

/*
 Programming assignment : City Plan

 Name: Michel Syska

 NetID: syska

 Date: Monday February 25

 Precept: P01

 Compilation: javac *.java
 Execution: java cityplan/CityPlan

 Dependencies: BuildingProject; BuildingPlan; ResidentialBuilding; UtilityBuilding

 Description: Given a set of building projects, your task is to decide which of the available projects to build and
 where, in order to maximize residential capacity and availability of utilities to residents.

 */

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * City Plan: Google Hash Code 2018, see
 * https://codingcompetitions.withgoogle.com/hashcode/archive
 * <p>
 * Pre conditions: a city plan made of H rows of W columns, with a maximum walking distance of D and B building
 * projects.
 *
 * <p>
 * Post conditions: a resulting list of allocated building projects
 *
 * @author msyska
 * @version 0.1
 */
public class CityPlan {
    private final char[] symbol = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private int H; // number of rows of the city plan
    private int W; // number of columns of the city plan
    private char[][] city; // city plan of size H x W
    private int D; // maximum walking distance
    private int B; // number of building projects
    private BuildingProject[] bp; // array of building projects
    private Point[] placement; //

    /**
     * CityPlan :
     *
     * @param s: (Input) s is a Scanner over an input data file. The file format is specified in the file
     *           hashcode2018_final_task.pdf attached.
     */
    CityPlan(Scanner s) {
        try {
            this.H = s.nextInt();
            if (this.H < 1 || this.H > 1000) throw new IllegalArgumentException("precondition 1 ≤ H ≤ 1000 failed");
            this.W = s.nextInt();
            if (this.W < 1 || this.W > 1000) throw new IllegalArgumentException("precondition 1 ≤ W ≤ 1000 failed");
            this.D = s.nextInt();
            if (this.D < 1 || this.D > 20) throw new IllegalArgumentException("precondition 1 ≤ D ≤ 20 failed");
            this.B = s.nextInt();
            if (this.B < 2 || this.B > 1000) throw new IllegalArgumentException("precondition 2 ≤ B ≤ 1000 failed");

            this.city = new char[H][W]; // default char array value is 0 in Java
            this.bp = new BuildingProject[B];
            this.placement = new Point[B];

            for (int i = 0; i < B; i++) {
                // read BuildingProject
                char c = s.next().charAt(0);
                int h, w, value;
                h = s.nextInt();
                w = s.nextInt();
                value = s.nextInt();
                BuildingProject project;
                if (c == 'U') {
                    project = new UtilityBuilding(s, this.H, this.W, h, w, value);
                } else if (c == 'R') {
                    project = new ResidentialBuilding(s, this.H, this.W, h, w, value);
                } else throw new IllegalArgumentException("wrong building project type");
                // add to array
                bp[i] = project;
                //bp[i].display();
            }
            s.close();
        } catch (java.util.InputMismatchException e) {
            System.out.println("bad file format: " + e);
        } catch (java.lang.IllegalArgumentException e) {
            System.out.println("bad file input - " + e);
        }
    }

    BuildingProject[] getBp() {
        return bp;
    }

    Point[] getPlacement() {
        return placement;
    }

    /**
     * The placeAt method fill the city 2D grid with the occupied cells of the BuildingProject bp[projectNumber],
     * starting from top left corner located at point. If a cell is already used by another project, the method
     * throw an IllegalArgumentException, if the placement is successful, the placement array is updated.
     *
     * @param projectNumber: the index of project in bp[]
     * @param point:         upper left coordinates in city[][] grid
     * @throws IllegalArgumentException cell already used
     */
    void placeAt(int projectNumber, Point point) {
        // TODO
        for (int i = 0; i < bp[projectNumber].getH(); i++) {
            for (int j = 0; j < bp[projectNumber].getW(); j++) {
                if (this.city[point.x + i][point.y + j] == '#' && bp[projectNumber].getPlan().getPlan()[i][j] == '#')
                    throw new IllegalArgumentException("Occuped place");
                else {
                    this.city[point.x + i][point.y + j] = bp[projectNumber].getPlan().getPlan()[i][j];
                }
            }
        }
        placement[projectNumber] = new Point(point.x, point.y);
    }

    /**
     * The isEmptyPlaceAt method check if the BuildingProject bp[projectNumber] may be placed at point.
     *
     * @param projectNumber: the index of project in bp[]
     * @param point:         upper left coordinates in city[][] grid
     * @return true if all the matching '#'cells of project plan are corresponding to 0 in city, false otherwise
     */
    boolean isEmptyPlaceAt(int projectNumber, Point point) {
        // TODO
        for (int i = 0; i < bp[projectNumber].getH(); i++) {
            for (int j = 0; j < bp[projectNumber].getW(); j++) {
                if (point.x + i >= this.H || point.y + j >= this.W)
                    return false;
                if (this.city[point.x + i][point.y + j] != 0 && bp[projectNumber].getPlan().getPlan()[i][j] == '#')
                    return false;
            }
        }
        return true;
    }

    /**
     * attempt to place the projects from bp[]
     *
     * @return the number of projects successfully placed
     */
    int solve() {
        // TODO
        int placedProject = 0; //H25
        Point point = new Point(0, 0);
        int minH = bp[0].getH();
        for (int i = 0; i < bp.length; i++) {
            if (isEmptyPlaceAt(i, point)) {
                placeAt(i, point);
                point.y += bp[i].getW();
                placedProject++;
                display();
                System.out.println("----------------------------------------");
            } else {
                do {
                    point.x+=minH;
                    for (int y = 0; y < this.W; y++) {
                        point.y = y;
                        if (isEmptyPlaceAt(i, point)) {
                            placeAt(i, point);
                            point.y += bp[i].getW();
                            minH = bp[i].getH();
                            placedProject++;
                            break;
                        }
                    }
                }
                while (!isEmptyPlaceAt(i, point) && point.x < this.H);
                display();
                System.out.println("----------------------------------------");
            }
        }
        return placedProject;
    }

    int score(){
        int score=0;
        List<Integer> utilityIndex = getUtilityBuilding();
        List<Integer> residentialIndex = getResidentialBuilding();


        return score;
    }


    /**
     * @param p1 : source building plan
     * @param p2 : destination building plan
     * @return : Manhattan distance from source to destination
     */
    int distance(int p1, int p2) {
        // TODO
        Point bp1 = this.placement[p1];
        Point bp2 = this.placement[p2];
        return Math.abs(bp1.x - bp2.x) + Math.abs(bp1.y - bp2.y);

    }

    List<Integer> getResidentialBuilding(){
        java.util.List<Integer> residentialIndex = new ArrayList<>();
        for (int i = 0; i < bp.length; i++) {
            if (bp.getClass().getSimpleName().equals("ResidentialBuilding"))
                residentialIndex.add(i);
        }
        return residentialIndex;

    }

    List<Integer> getUtilityBuilding(){
        java.util.List<Integer> utilityIndex = new ArrayList<>();
        for (int i = 0; i < bp.length; i++) {
            if (bp.getClass().getSimpleName().equals("UtilityBuilding"))
                utilityIndex.add(i);
        }
        return utilityIndex;

    }


    /**
     * @return true if all residential projects are close enough to utility projects, false otherwise
     */

    public boolean checkDistances() {
        // TODO
        List<Integer> utilityIndex = getUtilityBuilding();
        List<Integer> residentialIndex = getResidentialBuilding();
        int counter = 0;
        for (int i = 0; i < bp.length; i++) {
            if (bp.getClass().getSimpleName().equals("UtilityBuilding"))
                utilityIndex.add(i);
            else
                residentialIndex.add(i);
        }
        for (Integer i : residentialIndex) {
            for (Integer j : utilityIndex) {
                if (distance(i.intValue(), j.intValue()) > this.D)
                    counter++;
                if (counter >= utilityIndex.size())
                    return false;
                counter = 0;
            }

        }
        return true;
    }


    /**
     * write city to stdout
     */
    void display() {
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (city[i][j] == 0)
                    System.out.print(" . ");
                else
                    System.out.print(" " + city[i][j] + " ");
            }
            System.out.println();
        }
    }
}
