package nestla.tennis.league.scheduler.csp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import nestla.tennis.league.scheduler.league.League;
import nestla.tennis.league.scheduler.league.Match;
import nestla.tennis.league.scheduler.league.Schedule;
import nestla.tennis.league.scheduler.league.Team;

/**
 *
 * @author alexy
 */
public class SchedulerCSP extends Task<Schedule> {

	private Model model;
	private League league;
	private int negativeMatchCounter;

	public SchedulerCSP(League league) {
		this.league = league;
	}

	@Override
	protected Schedule call() throws Exception {
		negativeMatchCounter = -1;
		// 1. Create a Model
		model = new Model("Tennis SchedulerCSP Model");
		league.setUp();
		// 2. Create variables
		// Initialising IntVar array with correct size
		int[][] arrarySingleWeek = new int[league.getNumberOfCourts()][];
		IntVar[] arrayWCIntVar = new IntVar[league.getNumberOfCourts() * league.getNumberOfWeeks()];

		Schedule randomSchedule = new Schedule(league.getSchedule());
		// System.out.println(randomSchedule.toString());
		for (int i = 0; i < league.getNumberOfDivisions(); i++) {
			for (Map.Entry<Team, ArrayList<Match>> entry : league.getDivision(i).getTeamMatchesMap().entrySet()) {
				Team homeTeam = entry.getKey();
				ArrayList<Match> opponentsArrayList = entry.getValue();
				for (int j = 0; j < opponentsArrayList.size(); j++) {
					// System.out.println("Court X: "+league.getCourtX(homeTeam.getCourtID()).getNumberOfMatchesOnCourt());
					// System.out.println("Home Team Court ID: "+homeTeam.getCourtID());
					// System.out.println("Oppenents ArrayList: "+opponentsArrayList.get(j));
					randomSchedule.setMatch(league.getCourtX(homeTeam.getCourtID()).getNumberOfMatchesOnCourt(),
							homeTeam.getCourtID(), opponentsArrayList.get(j));
					league.addOneMatchToCourtX(league.getCourtX(homeTeam.getCourtID()));
				}
			}
		}
		league.resetNumberOfMatchesOnCourt();

		for (int i = 0; i < arrarySingleWeek.length; i++) {
			for (int j = 0; j < randomSchedule.getNumCourts(); j++) {
				if (i % league.getNumberOfCourts() == j) {
					Match[] tempMatch = randomSchedule.getColumnX(j);
					int[] tempIntArray = new int[tempMatch.length];
					for (int k = 0; k < tempIntArray.length; k++) {
						int tempInt;
						if (tempMatch[k].getMatchID() == -1) {
							tempInt = negativeMatchCounter;
							negativeMatchCounter--;
						} else {
							tempInt = tempMatch[k].getMatchID();
						}
						tempIntArray[k] = tempInt;
					}
					 System.out.println("Week: " + (int) Math.floor(i / league.getNumberOfCourts()) + " Court: " + j
					 + " " + Arrays.toString(tempIntArray));
					arrarySingleWeek[i] = tempIntArray;
				}
			}
		}

		// System.out.println("BREAK");

		for (int i = 0; i < arrayWCIntVar.length; i++) {
			for (int j = 0; j < arrarySingleWeek.length; j++) {
				if (i % league.getNumberOfCourts() == j) {
					// System.out.println("Week: " + (int) Math.floor(i / league.getNumberOfCourts()) + " Court: " + j
					// + " " + Arrays.toString(arrarySingleWeek[j]));
					arrayWCIntVar[i] = model.intVar(
							"Week: " + (int) Math.floor(i / league.getNumberOfCourts()) + " Court: " + j + " ",
							arrarySingleWeek[j]);
				}
			}
		}

		// Creating conflictmap with integers
		HashMap<Match, ArrayList<Match>> conflictHashMapMatch = league.getConflictMap();
		HashMap<Integer, ArrayList<Integer>> conflictHashMapInteger = new HashMap<>();

		for (Map.Entry<Match, ArrayList<Match>> entry : conflictHashMapMatch.entrySet()) {
			Match key = entry.getKey();
			ArrayList<Match> value = entry.getValue();
			ArrayList<Integer> tempArrayList = new ArrayList<>();
			for (int i = 0; i < value.size(); i++) {
				tempArrayList.add(value.get(i).getMatchID());
			}
			conflictHashMapInteger.put(key.getMatchID(), tempArrayList);
		}

		// 3. Post constraints
		// Adding matchConstraints
		// Looping through IntVar array and add constraints
		for (int i = 0; i < arrayWCIntVar.length; i++) {
			// Using temp to figure out which week are affected by current week
			int temp = i % league.getCourts().size();
			for (int j = 0; j < league.getCourts().size(); j++) {
				int tempVal = j - temp;
				if (i != (i + tempVal)) {
					// All possible values
					for (int val = 0; val < conflictHashMapInteger.size(); val++) {
						// Variable to be using in going through conflict array
						for (int conflictVal = 0; conflictVal < conflictHashMapInteger.get(val).size(); conflictVal++) {
							if (arrayWCIntVar[i].contains(val) && arrayWCIntVar[i + tempVal]
									.contains(conflictHashMapInteger.get(val).get(conflictVal))) {
								// System.out.println(arrayWCIntVar[i+tempVal]+" contains "+allConflict.get(val).get(conflictVal));
								model.ifThen(model.arithm(arrayWCIntVar[i], "=", val),
										model.arithm(arrayWCIntVar[i + tempVal], "!=",
												conflictHashMapInteger.get(val).get(conflictVal)));
								/* Uncomment lines below to see exactly what constraints are in the solver */
//								 System.out.println("if Array[" + arrayWCIntVar[i].toString() + "] equals: " + val + " then array[" + arrayWCIntVar[i + tempVal].toString() + "] cannot equal: " + conflictHashMapInteger.get(val).get(conflictVal));
								// try {
								// Thread.sleep(100);
								// } catch (InterruptedException e) {
								// e.printStackTrace();
								// }
							}
						}
					}
				}
			}
		}
		// All different constraint
		model.allDifferent(arrayWCIntVar).post();

		// 4. Solve the problem
		// Solving
		Solver solver = model.getSolver();
		System.out.println();
		System.out.println("SOLVING...");
		// Start solving
		solver.solve();
		//Update GUI
		this.updateProgress(100, 100);
		
		System.out.println("");
		solver.printStatistics();
		System.out.println("");
		System.out.println("----SOLUTION----");
		// Printing ID of constraints

		// 5. Print the solution
		for (int i = 0; i < arrayWCIntVar.length; i++) {
			int courtNum = i % (league.getCourts().size());
			if (courtNum == 0) {
				System.out.println("");
				System.out.print("Week: " + (i / league.getCourts().size()));
			}
			System.out.print(" CourtNum: " + courtNum + " Match: ");
			System.out.print(arrayWCIntVar[i].getValue() + " ");
		}

		Schedule schedule = new Schedule(league.getNumberOfWeeks(), league.getNumberOfCourts());
		ArrayList<Match> allMatches = league.getAllMatches();
		ArrayList<Match> orderedMatches = new ArrayList<>();
		for (int i = 0; i < arrayWCIntVar.length; i++) {
			for (int k = 0; k < allMatches.size(); k++) {
				// System.out.println(arrayWCIntVar[i].getValue());
				if (arrayWCIntVar[i].getValue() == allMatches.get(k).getMatchID()) {
					orderedMatches.add(allMatches.get(k));
					break;
				} else if (arrayWCIntVar[i].getValue() < 0) {
					orderedMatches.add(new Match(-1, false));
					break;
				}
			}
		}

		int counter = 0;
		for (int i = 0; i < schedule.getNumWeeks(); i++) {
			for (int j = 0; j < schedule.getNumCourts(); j++) {
				schedule.getMatchArray()[i][j] = orderedMatches.get(counter);
				counter++;
			}
		}
		
		System.out.println(schedule.toString());
		schedule.printSeperateDivision(league);
		schedule.printSeperateDivisionWords(league);
		schedule.writeToExcel(league, 1);
		return schedule;
	}
	
	@Override
	protected void updateProgress(double workDone, double max) {
		super.updateProgress(workDone, max);
	}
}