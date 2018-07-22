package nestla.tennis.league.scheduler.csp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.uncommons.maths.number.AdjustableNumberGenerator;
import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.ContinuousUniformGenerator;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.PoissonGenerator;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.PopulationData;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RankSelection;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
import org.uncommons.watchmaker.framework.selection.TruncationSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.uncommons.watchmaker.framework.termination.TargetFitness;

import nestla.tennis.league.scheduler.league.League;
import nestla.tennis.league.scheduler.league.Match;
import nestla.tennis.league.scheduler.league.Schedule;
import nestla.tennis.league.scheduler.league.Team;

public class ExperimentMainCSP {

	public static Cell cell;

	public static void main(String[] args) {

		// Scheduling Setup
		// Create over arching divisions object
		League league = new League();
		
		 // Create individual divisions (Name, DivNum)
		league.addNewDivision("Division 1", 0);
		league.addNewDivision("Division 2", 1);
		league.addNewDivision("Division 3", 2);
		league.addNewDivision("Division 4", 3);
		league.addNewDivision("Division 5", 4);
		
		  // Create clubs (Name, ClubNum, NumOfCourts, NumOfCourtsWithLights)
		league.addNewClub("Club 1", 0, 1, 1);
		league.addNewClub("Club 2", 1, 1, 1);
		league.addNewClub("Club 3", 2, 1, 1);
		league.addNewClub("Club 4", 3, 1, 1);
		league.addNewClub("Club 5", 4, 1, 1);
		league.addNewClub("Club 6", 5, 1, 1);
		league.addNewClub("Club 7", 6, 1, 1);
		league.addNewClub("Club 8", 7, 1, 1);

		league.addNewClub("Club 9", 8, 1, 1);
		league.addNewClub("Club 10", 9, 1, 1);
		league.addNewClub("Club 11", 10, 1, 1);
		league.addNewClub("Club 12", 11, 1, 1);
		league.addNewClub("Club 13", 12, 1, 1);
		league.addNewClub("Club 14", 13, 1, 1);
		league.addNewClub("Club 15", 14, 1, 1);
		league.addNewClub("Club 16", 15, 1, 1);
		
		league.addNewClub("Club 17", 16, 1, 1);
		league.addNewClub("Club 18", 17, 1, 1);
		league.addNewClub("Club 19", 18, 1, 1);
		league.addNewClub("Club 20", 19, 1, 1);
		league.addNewClub("Club 21", 20, 1, 1);
		league.addNewClub("Club 22", 21, 1, 1);
		league.addNewClub("Club 23", 22, 1, 1);
		league.addNewClub("Club 24", 23, 1, 1);
		
		league.addNewClub("Club 25", 24, 1, 1);
		league.addNewClub("Club 26", 25, 1, 1);
		league.addNewClub("Club 27", 26, 1, 1);
		league.addNewClub("Club 28", 27, 1, 1);
		
		league.addNewClub("Club 29", 28, 1, 1);
		league.addNewClub("Club 30", 29, 1, 1);
		league.addNewClub("Club 31", 30, 1, 1);
		league.addNewClub("Club 32", 31, 1, 1);

		
		  // Create Teams (Name, TeamNum, ClubNum, DivNum)
		  //Division 1 Teams
		league.addNewTeam("Team 1", 0, 0, 0);
		league.addNewTeam("Team 2", 1, 0, 0);
		league.addNewTeam("Team 3", 2, 2, 0);
		league.addNewTeam("Team 4", 3, 2, 0);
		league.addNewTeam("Team 5", 4, 4, 0);
		league.addNewTeam("Team 6", 5, 4, 0);
//		divisions.addNewTeam("Team 7", 6, 6, 0);
//		divisions.addNewTeam("Team 8", 7, 7, 0);
		 //Division 2 Teams
		league.addNewTeam("Team 9", 8, 8, 1);
		league.addNewTeam("Team 10", 9, 8, 1);
		league.addNewTeam("Team 11", 10, 10, 1);
		league.addNewTeam("Team 12", 11, 10, 1);
		league.addNewTeam("Team 13", 12, 12, 1);
		league.addNewTeam("Team 14", 13, 12, 1);
//		divisions.addNewTeam("Team 15", 14, 14, 1);
//		divisions.addNewTeam("Team 16", 15, 15, 1);
		//Division 3 Teams
		league.addNewTeam("Team 17", 16, 16, 2);
		league.addNewTeam("Team 18", 17, 16, 2);
		league.addNewTeam("Team 19", 18, 18, 2);
		league.addNewTeam("Team 20", 19, 18, 2);
		league.addNewTeam("Team 21", 20, 20, 2);
		league.addNewTeam("Team 22", 21, 20, 2);
//		divisions.addNewTeam("Team 23", 22, 22, 2);
//		divisions.addNewTeam("Team 24", 23, 23, 2);
		
		league.addNewTeam("Team 25", 24, 24, 3);
		league.addNewTeam("Team 26", 25, 24, 3);
		league.addNewTeam("Team 27", 26, 26, 3);
		league.addNewTeam("Team 28", 27, 26, 3);
		league.addNewTeam("Team 29", 28, 28, 3);
		league.addNewTeam("Team 30", 29, 28, 3);
//		divisions.addNewTeam("Team 31", 30, 30, 3);
//		divisions.addNewTeam("Team 32", 31, 31, 3);
		
		league.setUp();

		int negativeMatchCounter = -1;
		// 1. Create a Model
		Model model = new Model("Tennis SchedulerCSP Model");
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
					// System.out.println("Court X: "+divisions.getCourtX(homeTeam.getCourtID()).getNumberOfMatchesOnCourt());
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
					// System.out.println("Week: " + (int) Math.floor(i / divisions.getNumberOfCourts()) + " Court: " + j
					// + " " + Arrays.toString(tempIntArray));
					arrarySingleWeek[i] = tempIntArray;
				}
			}
		}

		// System.out.println("BREAK");

		for (int i = 0; i < arrayWCIntVar.length; i++) {
			for (int j = 0; j < arrarySingleWeek.length; j++) {
				if (i % league.getNumberOfCourts() == j) {
					// System.out.println("Week: " + (int) Math.floor(i / divisions.getNumberOfCourts()) + " Court: " + j
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
								 System.out.println("if Array[" + arrayWCIntVar[i].toString() + "] equals: " + val + " then array[" + arrayWCIntVar[i + tempVal].toString() + "] cannot equal: " + conflictHashMapInteger.get(val).get(conflictVal));
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
	

	}
}
