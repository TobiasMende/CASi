/*  CASi is a Context Awareness Simulation software
    Copyright (C) 2012  Moritz Bürger, Marvin Frick, Tobias Mende

    This program is free software. It is licensed under the
    GNU Lesser General Public License with one clarification.
    See the LICENSE.txt file in this projects root folder or
    <http://www.gnu.org/licenses/lgpl.html> for more details.   
 */
package de.uniluebeck.imis.casi.utils.pathfinding;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;

import de.uniluebeck.imis.casi.CASi;

/**
 * This test checks the GraphPathSolver which allows to find the shortest path
 * in any graph with an adjacency matrix.
 * 
 * @author Tobias Mende
 * 
 */
public class GraphPathSolverTest {
	private static final Logger log = Logger
			.getLogger(GraphPathSolverTest.class.getName());

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		CASi.setupLogging();
	}

	@Test
	public void testCompute() {
		double[][] adjacencyMatrix = {
				{ 0, 2, -1, -1, -1, -1, -1, -1, -1, 100 },
				{ 0, 0, 10, -1, -1, -1, -1, -1, -1, 80 },
				{ 0, 2, 0, 10, -1, -1, -1, -1, -1, 70 },
				{ 0, 0, 10, 0, 5, -1, -1, -1, -1, 60 },
				{ 0, 2, -1, 5, 0, 10, -1, -1, -1, 50 },
				{ 0, 100, -1, -1, 10, 0, -1, -1, 1, 30 },
				{ 0, 2, -1, -1, -1, -1, 0, -1, -1, 20 },
				{ -1, -1, -1, -1, -1, -1, -1, 0, -1, -1 },
				{ 0, 2, -1, -1, -1, -1, -1, -1, 0, 20 },
				{ 0, 0, -1, -1, -1, -1, -1, -1, -1, 0 } };
		GraphPathSolver solver = new GraphPathSolver(9, adjacencyMatrix);
		List<Integer> path = solver.compute(0);
		List<Integer> expected = new ArrayList<Integer>();
		expected.add(0);
		expected.add(1);
		expected.add(2);
		expected.add(3);
		expected.add(4);
		expected.add(5);
		expected.add(8);
		expected.add(9);
		assertEquals(expected, path);
		log.info(path.toString());

		path = solver.compute(7);
		assertNull(path);
	}

	@Test
	public void testComputeWithMultipleDestinations() {
		double[][] adjacencyMatrix = {
				{ 0, 2, -1, -1, -1, -1, -1, -1, -1, 100 },
				{ 0, 0, 10, -1, -1, -1, -1, -1, -1, 80 },
				{ 0, 2, 0, 10, -1, -1, -1, -1, -1, 70 },
				{ 0, 0, 10, 0, 5, -1, -1, -1, -1, 60 },
				{ 0, 2, -1, 5, 0, 10, -1, -1, -1, 50 },
				{ 0, 100, -1, -1, 10, 0, -1, -1, 1, 30 },
				{ 0, 2, -1, -1, -1, -1, 0, -1, -1, 20 },
				{ -1, -1, -1, -1, -1, -1, -1, 0, -1, -1 },
				{ 0, 2, -1, -1, -1, -1, -1, -1, 0, 20 },
				{ 0, 0, -1, -1, -1, -1, -1, -1, -1, 0 } };
		Set<Integer> destinations = new HashSet<Integer>();
		destinations.add(7);
		destinations.add(9);
		GraphPathSolver solver = new GraphPathSolver(destinations,
				adjacencyMatrix);
		List<Integer> path = solver.compute(0);
		List<Integer> expected = new ArrayList<Integer>();
		expected.add(0);
		expected.add(1);
		expected.add(2);
		expected.add(3);
		expected.add(4);
		expected.add(5);
		expected.add(8);
		expected.add(9);
		assertEquals(expected, path);
		log.info(path.toString());
		// Path from a destination.
		path = solver.compute(7);
		expected.clear();
		expected.add(7);
		assertEquals(expected, path);
	}

	@Test
	public void testComputePerformance() {
		double[][] adjacencyMatrix = new double[1000][1000];

		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = 0; j < adjacencyMatrix.length; j++) {
				if (j == i) {
					adjacencyMatrix[i][j] = 0;
				} else if (i > j) {
					adjacencyMatrix[i][j] = j / i;
				} else {
					adjacencyMatrix[i][j] = i ^ 2;
				}
			}
		}

		GraphPathSolver solver = new GraphPathSolver(900, adjacencyMatrix);
		List<Integer> path = solver.compute(0);
		assertNotNull(path);
		log.info(path.toString());
	}

}
