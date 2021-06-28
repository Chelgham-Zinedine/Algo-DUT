/*
 * M415 2020-2021: additional algorithmics
 */
package cityplan;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuildingPlanTest {
    private static BuildingPlan[] bpCorrect;
    private static String[][] myCorrectPlan = {
            {
                    "#"
            },
            {
                    "##"
            },
            {
                    "########"
            },
            {
                    "#",
                    "#"
            },
            {
                    "#",
                    "#",
                    "#",
                    "#"
            },
            {
                    ".#",
                    "##"
            },

            {
                    "#.",
                    "##"
            },
            {
                    "#..###...",
                    "#########",
                    "...######"
            },
            {
                    "######.",
                    ".....#.",
                    "#######"
            },
            {
                    "######.#######",
                    "#####..#######",
                    "#####.########",
                    "#####........#",
                    "############.#",
                    "#............#",
                    "##############"
            },
            {
                    "###",
                    "#.#",
                    "#.#",
            },
            {
                    "#####.##",
                    "########",
                    "########",
                    "######..",
                    "#####...",
                    "######..",
                    "..#....."
            }
    };
    private static int[] myCorrectEmpty = {0, 0, 0, 0, 0, 1, 1, 8, 7, 25, 2, 15};

    private static BuildingPlan[] bpNoEdge;
    private static String[][] myNoEdge = {
            {
            },
            {
                    ".#"
            },
            {
                    "##."
            },
            {
                    "#",
                    "."
            },
            {
                    ".",
                    "#",
                    "#",
                    "#"
            },
            {
                    "..",
                    "##"
            },

            {
                    "#.",
                    "#."
            },
            {
                    "#..###...",
                    "#########",
                    "........."
            },
            {
                    "######.",
                    ".....#.",
                    "######."
            },
    };
    private static BuildingPlan[] bpNotConnected;
    private static String[][] myNotConnected = {
            {
                    ".#",
                    "#."
            },

            {
                    "#.#",
                    ".##"
            },
            {
                    "#..###...",
                    "####.####",
                    "....#.###"
            },
            {
                    "#####..",
                    ".....#.",
                    "#####.#"
            }
    };
    private static BuildingPlan[] bpWithHole;
    private static String[][] myWithHole = {
            {
                    "#..###...",
                    "####.####",
                    "...######"
            },
            {
                    "######.",
                    "#....#.",
                    "#######"
            },
            {
                    "######.#######",
                    "#####..#######",
                    "##.##.########",
                    "#####....#...#",
                    "############.#",
                    "#............#",
                    "##############"
            },
            {
                    "###",
                    "#.#",
                    "###",
            },
            {
                    "#####.##",
                    "########",
                    "########",
                    "######..",
                    "##.##...",
                    "######..",
                    "..#....."
            }
    };

    @BeforeAll
    static void initAll() {
        bpCorrect = new BuildingPlan[myCorrectPlan.length];
        for (int i = 0; i < myCorrectPlan.length; i++) {
            bpCorrect[i] = new BuildingPlan(myCorrectPlan[i]);
            System.out.println("Correct building plan " + i);
            bpCorrect[i].display();
        }

        bpNoEdge = new BuildingPlan[myNoEdge.length];
        for (int i = 0; i < myNoEdge.length; i++) {
            bpNoEdge[i] = new BuildingPlan(myNoEdge[i]);
            System.out.println("Building plan with no cell in some edges " + i);
            bpNoEdge[i].display();
        }

        bpNotConnected = new BuildingPlan[myNotConnected.length];
        for (int i = 0; i < myNotConnected.length; i++) {
            bpNotConnected[i] = new BuildingPlan(myNotConnected[i]);
            System.out.println("Building plan with some cells not connected " + i);
            bpNotConnected[i].display();
        }

        bpWithHole = new BuildingPlan[myWithHole.length];
        for (int i = 0; i < myWithHole.length; i++) {
            bpWithHole[i] = new BuildingPlan(myWithHole[i]);
            System.out.println("Building plan with some holes " + i);
            bpWithHole[i].display();
        }
    }

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    void getNbEmpty() {
        for (int i = 0; i < bpCorrect.length; i++) {
            assertEquals(bpCorrect[i].getNbEmpty(), myCorrectEmpty[i]);
        }
    }

    @Test
    void checkPlan() {
        for (int i = 0; i < bpCorrect.length; i++) {
            assertTrue(bpCorrect[i].checkPlan());
        }
    }

    @Test
    void checkPlanEdges() {
        List<String> expectedMessagesEdges = Arrays.asList("invalid building: no cell on the(.*)");
        for (int i = 0; i < bpNoEdge.length; i++) {
            BuildingPlan bp = bpNoEdge[i];
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                bp.checkPlan();
            });
            assertLinesMatch(expectedMessagesEdges, Arrays.asList(exception.getMessage()));
        }
    }

    @Test
    void checkPlanConnected() {
        List<String> expectedMessagesNotConnected = Arrays.asList("invalid building: occupied cells are not connected(.*)");
        for (int i = 0; i < bpNotConnected.length; i++) {
            BuildingPlan bp = bpNotConnected[i];
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                bp.checkPlan();
            });
            assertLinesMatch(expectedMessagesNotConnected, Arrays.asList(exception.getMessage()));
        }
    }

    @Test
    void checkPlanHoles() {
        for (int i = 0; i < bpWithHole.length; i++) {
            BuildingPlan bp = bpWithHole[i];
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                bp.checkPlan();
            });
            assertEquals("invalid building: there exists a hole in the plan", exception.getMessage());
        }
    }
}