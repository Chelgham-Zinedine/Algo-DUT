/*
 * M415 2018-2019: additional algorithmics
 */
package cityplan;

import java.util.Arrays;

/**
 * City Plan: Google Hash Code 2018, see
 * https://codingcompetitions.withgoogle.com/hashcode/archive BuildingPlan :
 * common to residential or utility projects
 * <p>
 * Every building project includes a plan of the constructed building. The plan
 * of the building is represented as a rectangular grid of cells of h p rows and
 * wp columns. The cells within the grid are referenced using a pair of 0 based
 * coordinates [r , c] , denoting the row and column of the cell, respectively.
 * The cell [0,0] is the upperleft corner of the grid. Each cell in the grid is
 * either occupied (represented as '#' ) or free (represented as '.' ). Each
 * building plan meets the following conditions: - at least one cell at each
 * edge of the grid is occupied – there exists at least one occupied cell [0, c]
 * (for some value of c ), at least one occupied cell [ h p – 1, c] (for some
 * value of c) , at least one occupied cell [ r , w p – 1] (for some value of r)
 * and at least one occupied cell [r , 0] (for some value of r ). - the occupied
 * cells form one connected component – any occupied cell can be reached from
 * any other occupied cell by passing one cell at a time between neighboring
 * occupied cells. In this definition, neighboring cells mean two cells that are
 * next to each other, either in the same row or in the same column (so each
 * cell has at most 4 neighbors). - there are no holes in the building plan –
 * all unoccupied cells are reachable from the building plan border (by passing
 * one cell at a time between neighboring unoccupied cells).
 *
 * @author msyska
 * @version 0.1
 */
public class BuildingPlan {
	private char[][] plan; // '#' for occupied cells or '.' for free cells

	/**
	 *
	 * @param plan
	 */
	public BuildingPlan(char[][] plan) {
		// https://stackoverflow.com/questions/37922829/intellij-idea-overrides-method-warning-with-java-8-lambda-expressions
		this.plan = Arrays.stream(plan).map(r -> r.clone()).toArray(char[][]::new);
	}

	public BuildingPlan(String[] myPlan) {
		plan = new char[myPlan.length][];
		for (int i = 0; i < myPlan.length; i++) {
			plan[i] = myPlan[i].toCharArray();
		}
	}

	public char[][] getPlan() {
		return plan;
	}

	/**
	 * Play with some plan instances
	 */
	public static void main(String[] args) {
		String[][] myPlan = { {}, { "#..###...", "#########", "...######" }, { "######.", ".....#.", "#######" },
				{ "######.#######", "#####..#######", "#####.########", "#####........#", "############.#",
						"#............#", "##############" },
				{ "###", "#.#", "###", },
				{ "#####.##", "########", "########", "######..", "#####...", "######..", "..#....." }, { "#", "#" },
				{ ".#", "##" }, { "#" }, { ".#" }, { "##" }, { "....", ".##.", "#..#" }, { "####.", "####." },
				{ "#.###", ".#..." }, { "###.###", "###.###", "###.###" }, { "##..##", "..##..", "##..##" },
				{ ".#", "#." } };

		BuildingPlan[] bp = new BuildingPlan[myPlan.length];
		for (int i = 0; i < myPlan.length; i++) {
			bp[i] = new BuildingPlan(myPlan[i]);
			System.out.println("Building plan " + i);
			bp[i].display();
		}
		for (int i = 0; i < myPlan.length; i++) {
			try {
				System.out.println("Check building plan " + i);
				bp[i].checkPlan();
			} catch (java.lang.IllegalArgumentException e) {
				System.out.println(e);
			}
		}
	}

	/**
	 *
	 * @return nbEmpty the number of empty cells in the plan
	 */
	public int getNbEmpty() {
		int nbEmpty = 0;
		for (int i = 0; i < plan.length; i++)
			for (int j = 0; j < plan[0].length; j++)
				if (plan[i][j] == '.')
					nbEmpty++;
		return nbEmpty;
	}

	/*
	 * checkPlan : check
	 */
	public boolean checkPlan() {
		// at least one cell on each edge
		atLeastOneCellperEdge();
		// occupied cells should be connected (see case with only one occupied cell)
		// isConnected(); // was wrong
		if (!DFSConnected('#', plan.length * plan[0].length - getNbEmpty())) {
			throw new IllegalArgumentException("invalid building: occupied cells are not connected");
		}
		// no hole in the building plan
		if (!DFSNoHole('.', getNbEmpty())) {
			throw new IllegalArgumentException("invalid building: there exists a hole in the plan");
		}
		return true;
	}

	private void atLeastOneCellperEdge() {
		// at least one cell on the left edge
		boolean noCellonLeftEdge = true;
		for (int i = 0; i < plan.length; i++) {
			if (plan[i][0] == '#') {
				noCellonLeftEdge = false;
				break;
			}
		}
		if (noCellonLeftEdge)
			throw new IllegalArgumentException("invalid building: no cell on the left edge of the plan");
		// at least one cell on the right edge
		boolean noCellonRightEdge = true;
		for (int i = 0; i < plan.length; i++) {
			if (plan[i][plan[0].length - 1] == '#') {
				noCellonRightEdge = false;
				break;
			}
		}
		if (noCellonRightEdge)
			throw new IllegalArgumentException("invalid building: no cell on the right edge of the plan");
		// at least one cell on the top edge
		boolean noCellonTopEdge = true;
		for (int i = 0; i < plan[0].length; i++) {
			if (plan[0][i] == '#') {
				noCellonTopEdge = false;
				break;
			}
		}
		if (noCellonTopEdge)
			throw new IllegalArgumentException("invalid building: no cell on the top edge of the plan");
		// at least one cell on the bottom edge
		boolean noCellonBottomEdge = true;
		for (int i = 0; i < plan[0].length; i++) {
			if (plan[plan.length - 1][i] == '#') {
				noCellonBottomEdge = false;
				break;
			}
		}
		if (noCellonBottomEdge)
			throw new IllegalArgumentException("invalid building: no cell on the bottom edge of the plan");
	}


	private boolean DFSNoHole(char cell, int nbMarked) {
		// no hole in the building plan
		if (plan.length < 2 || plan[0].length < 3)
			return true;

		// make a copy of plan
		char[][] planLabel = Arrays.stream(plan).map(r -> r.clone()).toArray(char[][]::new);

		// init border : mark unoccupied cells with 'o' then start DFS from successor
		// cell
		for (int i = 0; i < planLabel[0].length; i++) { // columns
			if (planLabel[0][i] == cell) { // top edge
				planLabel[0][i] = 'o';
				DFS(1, i, planLabel, cell); // south
			}
			if (planLabel[planLabel.length - 1][i] == cell) { // bottom edge
				planLabel[planLabel.length - 1][i] = 'o';
				DFS(planLabel.length - 2, i, planLabel, cell); // north
			}
		}

		for (int i = 1; i < planLabel.length - 1; i++) { // rows
			if (planLabel[i][0] == cell) { // left edge
				planLabel[i][0] = 'o';
				DFS(i, 1, planLabel, cell); // east
			}
			if (planLabel[i][planLabel[i].length - 1] == cell) { // right edge
				planLabel[i][planLabel[i].length - 1] = 'o';
				DFS(i, planLabel[i].length - 2, planLabel, cell); // west
			}
		}

		int nbConnected = 0;
		for (int i = 0; i < planLabel.length; i++)
			for (int j = 0; j < planLabel[i].length; j++)
				if (planLabel[i][j] == 'o')
					nbConnected++;

		// displayPlan(planLabel);
		return (nbConnected == nbMarked);
	}

	private boolean DFSConnected(char cell, int nbMarked) {
		// all cells are connected
		if (plan.length < 2 || plan[0].length < 2)
			return true;

		// make a copy of plan
		char[][] planLabel = Arrays.stream(plan).map(r -> r.clone()).toArray(char[][]::new);

		for (int i = 0; i < planLabel[0].length; i++) { // columns
			if (planLabel[0][i] == cell) { // find the first occupied cell on the top edge
				planLabel[0][i] = 'o';
				DFS(1, i, planLabel, cell); // then run DFS in south direction
				DFS(0, i + 1, planLabel, cell); // and east direction
				break;
			}
		}

		int nbConnected = 0;
		for (int i = 0; i < planLabel.length; i++)
			for (int j = 0; j < planLabel[i].length; j++)
				if (planLabel[i][j] == 'o')
					nbConnected++;

		return (nbConnected == nbMarked);
	}

	/**
	 * Depth-first search (DFS)
	 *
	 * @param i         : row coordinate
	 * @param j         : column coordinate
	 * @param planLabel : labels for each cell in the grid : 'o' if already visited
	 * @param cell      : type of cell in which the DFS applies
	 */
	private void DFS(int i, int j, char[][] planLabel, char cell) {
		if (i >= 0 && i < planLabel.length && j >= 0 && j < planLabel[0].length)
			if (planLabel[i][j] == cell) {
				planLabel[i][j] = 'o';
				DFS(i, j + 1, planLabel, cell); // east
				DFS(i, j - 1, planLabel, cell); // west
				DFS(i - 1, j, planLabel, cell); // north
				DFS(i + 1, j, planLabel, cell); // south
			}
	}

	public void displayPlan(char[][] planLabel) {
		for (int i = 0; i < planLabel.length; i++) {
			String row = "";
			for (int j = 0; j < planLabel[i].length; j++) {
				row += planLabel[i][j];
			}
			System.out.println(row);
		}
	}

	public void display() {
		for (int i = 0; i < this.plan.length; i++) {
			String row = "";
			for (int j = 0; j < this.plan[i].length; j++) {
				row += this.plan[i][j];
			}
			System.out.println(row);
		}
	}
}