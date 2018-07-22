package nestla.tennis.league.scheduler.cspOLD;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.criteria.Criterion;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 *
 * @author alexy
 */
public class Scheduler {

	// OLD Variables (Some Still in use don't delete)
	private Divisions divisions;
	private Clubs clubs;
	private double courtsPerMatch;
	private HashMap<Integer, Team> teamIDMap;
	private HashMap<Division, HashMap<Integer, Match>> divisionMatchesMap;
	private HashMap<Integer, Match> matchesMap;
	private HashMap<Division, ArrayList<CourtBlock>> divisionCourtsMap;
	private HashMap<Match, CourtBlock> matchToCourtMap;
	private ArrayList<ArrayList<Integer>> allConflict;
	private HashMap<Integer, ArrayList<Integer>> courtMatchesArrayList;
	private HashMap<Integer, int[]> courtMatchesArray;

	// NEW AND IMPROVED VARIABLES
	private ArrayList<Match> allMatches;
	private ArrayList<CourtBlock> allCourtBlocks;
	private HashMap<CourtBlock, ArrayList<Match>> courtBlockToMatches;
	private HashMap<Integer, int[]> courtBlockToMatchesArray;
	private HashMap<Integer, ArrayList<Integer>> duplicatesHashMap;
	int minIDOfWeeks;
	private int negativeMatchVal;
	int lastNegativeMatchValBeforePadding;
	int maxIDOfTeamsInDivisions;

	// ChocoSolver variables
	private Model model;
	private IntVar[] arrayWCIntVar;
	int IDOfWeeksIter;
	private int totalTimeLimit;

	// Excel Output variables
	private static String FILE_NAME;

	public Scheduler(Divisions divisions, Clubs clubs, double courtsPerMatch) {
		this.divisions = divisions;
		this.clubs = clubs;
		this.courtsPerMatch = courtsPerMatch;
		teamIDMap = new HashMap<>();
		divisionMatchesMap = new HashMap<>();
		matchesMap = new HashMap<>();
		allMatches = new ArrayList<>();
		allCourtBlocks = new ArrayList<>();
		divisionCourtsMap = new HashMap<>();
		matchToCourtMap = new HashMap<>();
		allConflict = new ArrayList<>();
		courtMatchesArrayList = new HashMap<>();
		courtMatchesArray = new HashMap<>();
		negativeMatchVal = -1;
		lastNegativeMatchValBeforePadding = -1;
		courtBlockToMatches = new HashMap<>();
		courtBlockToMatchesArray = new HashMap<>();
		duplicatesHashMap  =  new HashMap<>();
		minIDOfWeeks = 0;
	}
	
	//Adding teams to correct divisions based on information put into teams when created.
	void setTeamsToDivisions() {
		for (int clubNum = 0; clubNum < clubs.getSize(); clubNum++) {
			Club club = clubs.getClubAtIndex(clubNum);
			for (int clubTeamNum = 0; clubTeamNum < club.getNumTeams(); clubTeamNum++) {
				Team team = club.getTeamAtIndex(clubTeamNum);
				divisions.getDivisionByID(team.getDivisionID()).addTeam(team);
			}
		}
		maxIDOfTeamsInDivisions = divisions.getMaxIDOfTeamsInDivision();
	}

	//Setting every team to have a unique ID (ID)
	void setTeamIDs() {
		int index = 0;
		for (int clubNum = 0; clubNum < clubs.getSize(); clubNum++) {
			Club club = clubs.getClubAtIndex(clubNum);
			for (int clubTeamNum = 0; clubTeamNum < club.getNumTeams(); clubTeamNum++) {
				teamIDMap.put(index, club.getTeamAtIndex(clubTeamNum));
				index++;
			}
		}
	}

	//Printing out the teams ID
	void printTeamIDs() {
		System.out.println("");
		System.out.println("PRINTING TEAM IDS");
		for (int i = 0; i < teamIDMap.size(); i++) {
			System.out.println("Team ID: " + i + " Team: " + teamIDMap.get(i).getTeamName());
		}
	}

	//Creating matches in divisions so that every team in a certain division have to play every other team home and away.
	void createMatches() {
		int IDOfMatch = 0;
		for (int i = 0; i < divisions.getSize(); i++) {
			matchesMap = new HashMap<>();
			// System.out.println();
			Division curDivision = divisions.getDivisionAtIndex(i);
			int lowestIDOfMatchInDiv = IDOfMatch;
			for (int homeTeamNum = 0; homeTeamNum < teamIDMap.size(); homeTeamNum++) {
				for (int awayTeamNum = 0; awayTeamNum < teamIDMap.size(); awayTeamNum++) {
					if (homeTeamNum != awayTeamNum) {
						Team homeTeam = teamIDMap.get(homeTeamNum);
						Team awayTeam = teamIDMap.get(awayTeamNum);
						//System.out.println("home: "+homeTeamNum+" div: "+homeTeam.getDivisionID()+" away: "+awayTeamNum+" div: "+awayTeam.getDivisionID());
						if (homeTeam.getDivisionID() == curDivision.getDivisionID()
								&& awayTeam.getDivisionID() == curDivision.getDivisionID()) {
							divisions.getDivisionAtIndex(i).setFirstMatchNum(lowestIDOfMatchInDiv);
							// System.out.println("Match Num: "+IDOfMatch+" home:
							// "+teamIDMap.get(homeTeamNum).getTeamName()+" away:
							// "+teamIDMap.get(awayTeamNum).getTeamName());
							allMatches.add(new Match(IDOfMatch, teamIDMap.get(homeTeamNum),
									teamIDMap.get(awayTeamNum), curDivision));
							matchesMap.put(IDOfMatch, new Match(IDOfMatch, teamIDMap.get(homeTeamNum),
									teamIDMap.get(awayTeamNum), curDivision));
							IDOfMatch++;
							// System.out.println(IDOfMatch);
						}
					}
				}
			}
			// System.out.println("Div Num:
			// "+divisions.getDivisionAtIndex(i).getDivisionID()+" sizeOfMatchesMap:
			// "+matchesMap.size());
			divisionMatchesMap.put(divisions.getDivisionAtIndex(i), matchesMap);
		}
	}

	//Printing out matches to be played
	void printAllMatches() {
		System.out.println("");
		System.out.println("PRINTING ALL MATCHES");
		for (int i = 0; i < divisionMatchesMap.size(); i++) {
			HashMap<Integer, Match> curMatchMap = divisionMatchesMap.get(divisions.getDivisionAtIndex(i));
			int lowestIDMatchInDivision = divisions.getDivisionAtIndex(i).getFirstMatchNum();
			for (int j = lowestIDMatchInDivision; j < curMatchMap.size() + lowestIDMatchInDivision; j++) {
				System.out.println("Match: " + curMatchMap.get(j));
			}
		}
	}

	//Creating availiable court blocks for the matches to be played on (Court blocks will have unique ID)
	void createAvaliableCourts() {
		int IDOfCourt = 0;
		for (int clubNum = 0; clubNum < clubs.getSize(); clubNum++) {
			Club tempClub = clubs.getClubAtIndex(clubNum);
			for (double clubCourt = 0; clubCourt < tempClub.getNumCourts(); clubCourt = clubCourt + courtsPerMatch) {
				if(clubCourt < tempClub.getNumTeams()*courtsPerMatch) {
					allCourtBlocks.add(new CourtBlock(IDOfCourt, tempClub));
					clubs.getClubAtIndex(clubNum).addToNumCourtsBlocksInUse();
					IDOfCourt++;
				}
			}
		}
	}

	//Printing out all court block information
	void printAllCourts() {
		System.out.println("");
		System.out.println("PRINTING ALL COURTS");
		for (int i = 0; i < allCourtBlocks.size(); i++) {
			System.out.println(allCourtBlocks.get(i));
		}
	}

	//Setting appropriate courts to division (courtblock will only be added to division if a team plays home at that courtblock)
	void setCourtsToDivisions() {
		for (int teamNum = 0; teamNum < teamIDMap.size(); teamNum++) {
			Team curTeam = teamIDMap.get(teamNum);
			Division curDivision = divisions.getDivisionByID(curTeam.getDivisionID());
			// System.out.println(curDivision.getDivisionName());
			if (divisionCourtsMap.containsKey(curDivision)) {
				ArrayList<CourtBlock> tempArrayList = divisionCourtsMap.get(curDivision);
				for (int courtBlockNum = 0; courtBlockNum < allCourtBlocks.size(); courtBlockNum++) {
					if (allCourtBlocks.get(courtBlockNum).getClub() == curTeam.getClub()) {
						if (!tempArrayList.contains(allCourtBlocks.get(courtBlockNum))) {
							tempArrayList.add(allCourtBlocks.get(courtBlockNum));
						}
					}
				}
				divisionCourtsMap.remove(curDivision);
				divisionCourtsMap.put(curDivision, tempArrayList);
			} else {
				ArrayList<CourtBlock> tempArrayList = new ArrayList<>();
				for (int courtBlockNum = 0; courtBlockNum < allCourtBlocks.size(); courtBlockNum++) {
					if (allCourtBlocks.get(courtBlockNum).getClub() == curTeam.getClub()) {
						if (!tempArrayList.contains(allCourtBlocks.get(courtBlockNum))) {
							tempArrayList.add(allCourtBlocks.get(courtBlockNum));
						}
					}
				}
				divisionCourtsMap.put(curDivision, tempArrayList);
			}
		}

	}

	//Printing out court blocks to division
	void printCourtsToDivsions() {
		System.out.println("");
		System.out.println("PRINTING COURTS TO DIVISIONS");
		for (int i = 0; i < divisionCourtsMap.size(); i++) {
			System.out.print(divisions.getDivisionAtIndex(i).getDivisionName());
			ArrayList<CourtBlock> tempArrayList = divisionCourtsMap.get(divisions.getDivisionAtIndex(i));
			System.out.println(" " + tempArrayList);
		}
	}

	/*BELOW WAS THE METHOD USED BEFORE DIVISIONS WERE ADDED TO ADD COURT BLOCKS TO CERTAIN DIVISIONS*/
	// Need to still decide how to set matches to court fairly
	// void setMatchesToCourts() {
	// for (int i = 0; i < allMatches.size(); i++) {
	// Match curMatch = allMatches.get(i);
	// int curMatchNum = curMatch.getMatchID();
	// Club homeTeamClub = curMatch.getHomeTeam().getClub();
	// for (int courtBlockNum = 0; courtBlockNum < allCourtBlocks.size();
	// courtBlockNum++) {
	// if (allCourtBlocks.get(courtBlockNum).getClub() == homeTeamClub) {
	// CourtBlock court = allCourtBlocks.get(courtBlockNum);
	// matchToCourtMap.put(curMatch, court);
	// }
	// }
	// }
	// }
	//
	// void printAllMatchesToCourts() {
	// System.out.println("");
	// System.out.println("PRINTING ALL COURTS TO MATCHES");
	// for (int i = 0; i < allMatches.size(); i++) {
	// Match curMatch = allMatches.get(i);
	// CourtBlock curCourtBlock = matchToCourtMap.get(curMatch);
	// System.out.println(curMatch.toString() + " || " + curCourtBlock.toString());
	// }
	// }

	//Adding appropriate matches to courts blocks 
	void createCourtToMatchesArrayList() {
		for (int i = 0; i < allMatches.size(); i++) {
			Match curMatch = allMatches.get(i);
			for (int j = 0; j < allCourtBlocks.size(); j++) {
				CourtBlock curCourtBlock = allCourtBlocks.get(j);
				if (curMatch.getHomeTeam().getClub() == curCourtBlock.getClub()) {
					if (courtBlockToMatches.containsKey(curCourtBlock)) {
						ArrayList<Match> tempArrayList = courtBlockToMatches.get(curCourtBlock);
						tempArrayList.add(curMatch);
						courtBlockToMatches.remove(curCourtBlock);
						courtBlockToMatches.put(curCourtBlock, tempArrayList);
					} else {
						ArrayList<Match> tempArrayList = new ArrayList<>();
						tempArrayList.add(curMatch);
						courtBlockToMatches.put(curCourtBlock, tempArrayList);
					}
				}
			}
		}
	}

	//Printing court blocks to matches arraylist
	void printCourtToMatchesArrayList() {
		System.out.println("");
		System.out.println("PRINTING COURT TO MATCHES ARRAYLIST");
		for (int i = 0; i < allCourtBlocks.size(); i++) {
			System.out.print(allCourtBlocks.get(i) + " || " + courtBlockToMatches.get(allCourtBlocks.get(i)));
			System.out.println("");
		}
	}

	//Converting to array due to Choco-Solver needing arrays to solve problem
	void convertCourtToMatchesArrayListToArray() {
		for (int i = 0; i < allCourtBlocks.size(); i++) {
			int[] tempArray = new int[courtBlockToMatches.get(allCourtBlocks.get(i)).size()];
			for (int j = 0; j < courtBlockToMatches.get(allCourtBlocks.get(i)).size(); j++) {
				tempArray[j] = courtBlockToMatches.get(allCourtBlocks.get(i)).get(j).getMatchID();
			}
			courtBlockToMatchesArray.put(allCourtBlocks.get(i).getCourtBlockNum(), tempArray);
		}
	}

	//Creating constraints based on matches
	/*
	 * If team A and team B are playing then any other match including
	 * either team A or team B can not be played (same week)
	 */
	void createConstraints() {
		for (int i = 0; i < allMatches.size(); i++) {
			ArrayList<Integer> conflict = new ArrayList<>();
			Team homeTeam = allMatches.get(i).getHomeTeam();
			Team awayTeam = allMatches.get(i).getAwayTeam();
			for (int j = 0; j < allMatches.size(); j++) {
				if(i!=j) {
					if (allMatches.get(j).getHomeTeam() == homeTeam || allMatches.get(j).getHomeTeam() == awayTeam
							|| allMatches.get(j).getAwayTeam() == homeTeam || allMatches.get(j).getAwayTeam() == awayTeam) {
						conflict.add(j);
					}
				}
			}
			allConflict.add(conflict);
		}
	}

	//Printing all constraints based on matches
	void printAllConstraints() {
		System.out.println("");
		System.out.println("PRINTING ALL MATCH CONSTRAINTS");
		for (int i = 0; i < allConflict.size(); i++) {
			System.out.print("Conflicts for match: " + i + " || ");
			for (int j = 0; j < allConflict.get(i).size(); j++) {
				System.out.print(allConflict.get(i).get(j) + " ");
			}
			System.out.println("");
		}
	}

	/*ID of weeks not supplied and just calculated*/
	void createChocoSolverModel() {
		// 1. Create Model
		model = new Model("Tennis SchedulerGA");

		// 2. Create variables
		System.out.println("Max ID of Teams per Division: " + maxIDOfTeamsInDivisions);
		if (maxIDOfTeamsInDivisions % 2 == 0) {
			minIDOfWeeks = (maxIDOfTeamsInDivisions - 1) * 2;
		} else {
			minIDOfWeeks = maxIDOfTeamsInDivisions * 2;
		}
		
		IDOfWeeksIter = minIDOfWeeks;
		System.out.println("ID of Weeks It: " + IDOfWeeksIter);

		initialiseChocoSolverModel();
	}

	/*ID of weeks supplied*/
	void createChocoSolverModel(int weeks) {
		// 1. Create Model
		model = new Model("Tennis SchedulerGA");

		// 2. Create variables
		// Start with min ID of weeks then + 1 every time no solution found
		// NEED TO ADD IT IN CODE SOMEWHERE LATER ^^
		IDOfWeeksIter = weeks;

		initialiseChocoSolverModel();
	}

	void initialiseChocoSolverModel() {
		int IDOfMatchesPossibleInWeeks = IDOfWeeksIter * courtBlockToMatchesArray.size();
		
		// Padding current arrays with negative values to match ID of weeks
		// This is due to choco-solver need variables to put in every week
		// and the all diff constraints means need negative values to represent
		// no match being played in slot.
		for (int j = 0; j < courtBlockToMatchesArray.size(); j++) {
			if (courtBlockToMatchesArray.get(j).length < IDOfWeeksIter) {
				int[] curArray = courtBlockToMatchesArray.get(j);
				int[] tempArray = Arrays.copyOf(curArray, IDOfWeeksIter);
				for (int i = curArray.length; i < tempArray.length; i++) {
					tempArray[i] = negativeMatchVal;
					negativeMatchVal--;
				}
				courtBlockToMatchesArray.remove(j);
				courtBlockToMatchesArray.put(j, tempArray);
			}
		}

		lastNegativeMatchValBeforePadding = negativeMatchVal;
		
		// Where values of arrays equal same add more negative values 1 for every same
		// value so multiple negative values across matches in same division
		ArrayList<Integer> doneValues = new ArrayList<>();
		ArrayList<Integer> curDoneValues = new ArrayList<>();
		for (int i = 0; i < courtBlockToMatchesArray.size(); i++) {
			for (int k = 0; k < courtBlockToMatchesArray.size(); k++) {
				int smallestLengthArrayInt = courtBlockToMatchesArray.get(i).length;
				if(courtBlockToMatchesArray.get(k).length<courtBlockToMatchesArray.get(i).length) {
					smallestLengthArrayInt = courtBlockToMatchesArray.get(k).length;
				}
				for (int j = 0; j < smallestLengthArrayInt ; j++) {
					if (i != k) {
						if (courtBlockToMatchesArray.get(i)[j] == courtBlockToMatchesArray.get(k)[j] && !doneValues.contains(courtBlockToMatchesArray.get(i)[j])) {
							// System.out.print(courtBlockToMatchesArray.get(i)[j] + " ");
							if (!duplicatesHashMap.containsKey(courtBlockToMatchesArray.get(i)[j])) {
								// System.out.println("ADDED");
								curDoneValues.add(courtBlockToMatchesArray.get(i)[j]);
								ArrayList<Integer> newTempArrayList = new ArrayList<>();
								newTempArrayList.add(negativeMatchVal);
								duplicatesHashMap.put(courtBlockToMatchesArray.get(i)[j], newTempArrayList);
								negativeMatchVal--;
							} else {
								ArrayList<Integer> tempArrayList = duplicatesHashMap.get(courtBlockToMatchesArray.get(i)[j]);
								tempArrayList.add(negativeMatchVal);
								duplicatesHashMap.remove(courtBlockToMatchesArray.get(i)[j]);
								duplicatesHashMap.put(courtBlockToMatchesArray.get(i)[j], tempArrayList);
								negativeMatchVal--;
							}
						}
					}
				}
			}
			doneValues.addAll(curDoneValues);
		}

		System.out.println(duplicatesHashMap);

		for (int i = 0; i < courtBlockToMatchesArray.size(); i++) {
			for (int j = 0; j < courtBlockToMatchesArray.get(i).length; j++) {
				int curVal = courtBlockToMatchesArray.get(i)[j];
				
				//System.out.println(curVal);
				if (duplicatesHashMap.containsKey(curVal)) {
					ArrayList<Integer> tempArrayList = duplicatesHashMap.get(curVal);
					int[] curArray = courtBlockToMatchesArray.get(i);
					int[] tempArray;
					//System.out.println(i+" Club: "+allCourtBlocks.get(i).getClub().getClubName()+" using: "+allCourtBlocks.get(i).getClub().getNumCourtsBlocksInUse()+" courtblocks");
					double maxArray = allCourtBlocks.get(i).getClub().getNumCourtsBlocksInUse()*IDOfWeeksIter;
					if((curArray.length + tempArrayList.size())>maxArray) {
						tempArray = Arrays.copyOf(curArray, (int) maxArray);
					}else {
						tempArray = Arrays.copyOf(curArray, curArray.length + tempArrayList.size());
					}
					for (int d = curArray.length; d < tempArray.length; d++) {
						tempArray[d] = tempArrayList.get(d - curArray.length);
						//System.out.println(tempArrayList.get(d-curArray.length));
					}
					courtBlockToMatchesArray.remove(i);
					courtBlockToMatchesArray.put(i, tempArray);
				}
			}
		}

		
		// Printing padded arrays to be added to the solver
		System.out.println("");
		System.out.println("PRINTING ARRAY WITH PADDING");
		for (int i = 0; i < courtBlockToMatchesArray.size(); i++) {
			System.out.print(i + " || ");
			for (int j = 0; j < courtBlockToMatchesArray.get(i).length; j++) {
				System.out.print(courtBlockToMatchesArray.get(i)[j] + " ");
			}
			System.out.println("");
		}

		// Initialising IntVar array with correct size
		arrayWCIntVar = new IntVar[IDOfMatchesPossibleInWeeks];

		// Adding potential matches to IntVar Array
		System.out.println();
//		System.out.println("PUTTING THESE VALUES INTO SOLVER");
		for (int i = 0; i < arrayWCIntVar.length; i++) {
			int j = i % allCourtBlocks.size();
			int weekNum = Math.floorDiv(i, allCourtBlocks.size());
			/*Uncomment lines below to see exactly what is going into the solver*/
//			System.out.println("i: "+i + " j: " + j + " weeknum: " + weekNum);
//			System.out.println("Week: " + weekNum + " Court: " + j+" "+Arrays.toString(courtBlockToMatchesArray.get(j)));
			arrayWCIntVar[i] = model.intVar("Week: " + weekNum + " Court: " + j, courtBlockToMatchesArray.get(j));
		}

//		System.out.println("Last Negative Value after padding before duplicates: "+lastNegativeMatchValBeforePadding);
		
		// 3. Post constraints
		// Adding matchConstraints
		// If week 0 court 0 = 0 then week 0 court 1 and 2 cannot equal any value in
		// conflict0

		// Looping through IntVar array and add constraints 
		for (int i = 0; i < arrayWCIntVar.length; i++) {
			// Using temp to figure out which week are affected by current week
			int temp = i % allCourtBlocks.size();
			for (int j = 0; j < allCourtBlocks.size(); j++) {
				int tempVal = j - temp;
				if (i != (i + tempVal)) {
					// All possible values
					for (int val = 0; val < allConflict.size(); val++) {
						// Variable to be using in going through conflict array
						for (int conflictVal = 0; conflictVal < allConflict.get(val).size(); conflictVal++) {
							if (arrayWCIntVar[i].contains(val) && arrayWCIntVar[i + tempVal].contains(allConflict.get(val).get(conflictVal))) {
								//System.out.println(arrayWCIntVar[i+tempVal]+" contains "+allConflict.get(val).get(conflictVal));
								model.ifThen(model.arithm(arrayWCIntVar[i], "=", val), model.arithm(arrayWCIntVar[i + tempVal], "!=", allConflict.get(val).get(conflictVal)));
								/*Uncomment lines below to see exactly what constraints are in the solver*/
//								System.out.println("if Array[" + arrayWCIntVar[i].toString() + "] equals: " + val + " then array[" + arrayWCIntVar[i + tempVal].toString() + "] cannot equal: " + allConflict.get(val).get(conflictVal));
//								try {
//									Thread.sleep(100);
//								} catch (InterruptedException e) {
//									e.printStackTrace();
//								}
							}
						}
					}
				}
			}
		}
		// All different contraint
		model.allDifferent(arrayWCIntVar).post();

		// Send to solving methods
		solveChocoSolverModel();
	}

	//Solve using choco solver and print an output
	void solveChocoSolverModel() {
		// 4. Solve the problem
		// Solving
		Solver solver = model.getSolver();
		System.out.println();
		System.out.println("SOLVING...");
		//Start solving
		solver.solve();
		System.out.println("");
		solver.printStatistics();
		System.out.println("");
		System.out.println("----SOLUTION----");
		// Printing ID of constraints

		// 5. Print the solution
		for (int i = 0; i < arrayWCIntVar.length; i++) {
			int courtNum = i % (allCourtBlocks.size());
			if (courtNum == 0) {
				System.out.println("");
				System.out.print("Week: " + (i / allCourtBlocks.size()));
			}
			System.out.print(" CourtNum: " + courtNum + " Match: ");
			System.out.print(arrayWCIntVar[i].getValue() + " ");
		}
	}
	
	//Checking the output to make sure all matches have been played else report error
	public void checkSolution() {
		System.out.println();
		System.out.println();
		System.out.println("---CHECKING SOLUTION---");
		boolean pass = true;
		ArrayList<Integer> checker = new ArrayList<>();
		for (int i = 0; i < arrayWCIntVar.length; i++) {
			if(arrayWCIntVar[i].getValue()>-1) {
				checker.add(arrayWCIntVar[i].getValue());
			}
		}
		Collections.sort(checker);
		System.out.println("All matches size: "+allMatches.size());
		for (int j = 0; j < allMatches.size(); j++) {
			//System.out.println(allMatches.get(checker.get(j)));
			if(!checker.contains(j)) {
				System.out.println("Missing Match ID: "+j);
				pass = false;
			}
		}
		if(pass == true) {
			System.out.println("SUCCESSFUL SOLUTION");
		}else {
			System.out.println("FAILED SOLUTION");
		}
	}

	//Print solution as ID format with divisions
	void printSolutionInTermsOfDivisions() {
		System.out.println("");
		for (int j = 0; j < divisions.getSize(); j++) {
			Division curDivision = divisions.getDivisionAtIndex(j);
			ArrayList<CourtBlock> curCourtBlockArrayList = divisionCourtsMap.get(curDivision);
			ArrayList<Integer> courtBlockID = new ArrayList<>();
			for (int k = 0; k < curCourtBlockArrayList.size(); k++) {
				courtBlockID.add(divisionCourtsMap.get(curDivision).get(k).getCourtBlockNum());
			}
			System.out.println("");
			System.out.println("---" + curDivision.getDivisionName() + "---");
			for (int i = 0; i < arrayWCIntVar.length; i++) {
				int courtNum = i % (allCourtBlocks.size());
				if (courtNum == 0) {
					System.out.println("");
					System.out.print("Week: " + (i / allCourtBlocks.size()));
				}
				if (courtBlockID.contains(courtNum)) {
					System.out.print(" CourtNum: " + courtNum + " Match: ");
					if (arrayWCIntVar[i].getValue() >= 0) {
						if (allMatches.get(arrayWCIntVar[i].getValue()).getDivision().getDivisionID() == curDivision
								.getDivisionID()) {
							System.out.print(arrayWCIntVar[i].getValue() + " ");
						} else {
							System.out.print("- ");
						}
					} else {
						System.out.print("- ");
					}
				}
			}
			System.out.println("");
		}
	}

	//Print solution as written word format with divisions
	void printSolutionInTermsOfDivisionsTextVersion() {
		System.out.println("");
		for (int j = 0; j < divisions.getSize(); j++) {
			Division curDivision = divisions.getDivisionAtIndex(j);
			ArrayList<CourtBlock> curCourtBlockArrayList = divisionCourtsMap.get(curDivision);
			ArrayList<Integer> courtBlockID = new ArrayList<>();
			for (int k = 0; k < curCourtBlockArrayList.size(); k++) {
				courtBlockID.add(divisionCourtsMap.get(curDivision).get(k).getCourtBlockNum());
			}
			System.out.println("");
			System.out.println("---" + curDivision.getDivisionName() + "---");
			for (int i = 0; i < arrayWCIntVar.length; i++) {
				int courtNum = i % (allCourtBlocks.size());
				if (courtNum == 0) {
					System.out.println("");
					System.out.print("Week: " + (i / allCourtBlocks.size()) + " ");
				}
				if (courtBlockID.contains(courtNum)) {
					if (arrayWCIntVar[i].getValue() >= 0) {
						if (allMatches.get(arrayWCIntVar[i].getValue()).getDivision().getDivisionID() == curDivision
								.getDivisionID()) {
							System.out.print(allMatches.get(arrayWCIntVar[i].getValue()).getMatchDetails() + " ");
						} else {
						}
					} else {
					}
				}
			}
			System.out.println("");
		}
	}

	//Create an excel file with solution
	void writeToExcel() {

		String userHomeFolder = System.getProperty("user.home");
		FILE_NAME = userHomeFolder + "/Desktop/eclipseoutput/NESTLA Tennis League.xlsx";
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet;
		CellStyle matchCellStyle;
		CellStyle weekCellStyle;

		for (int j = 0; j < divisions.getSize(); j++) {
			// Setting up Excel style
			sheet = workbook.createSheet(divisions.getDivisionAtIndex(j).getDivisionName());
			int rowNum = 0;
			// Match cell style
			matchCellStyle = sheet.getWorkbook().createCellStyle();
			matchCellStyle.setWrapText(true);
			matchCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			matchCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

			// Week cell style
			weekCellStyle = sheet.getWorkbook().createCellStyle();

			// -------
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			Division curDivision = divisions.getDivisionAtIndex(j);
			ArrayList<CourtBlock> curCourtBlockArrayList = divisionCourtsMap.get(curDivision);
			ArrayList<Integer> courtBlockID = new ArrayList<>();
			for (int k = 0; k < curCourtBlockArrayList.size(); k++) {
				courtBlockID.add(divisionCourtsMap.get(curDivision).get(k).getCourtBlockNum());
			}
			Cell cell = row.createCell(colNum++);
			cell.setCellValue((String) "---" + curDivision.getDivisionName() + "---");
			rowNum++;
			for (int i = 0; i < arrayWCIntVar.length; i++) {
				int courtNum = i % (allCourtBlocks.size());
				if (courtNum == 0) {
					row = sheet.createRow(rowNum++);
					colNum = 0;
					cell = row.createCell(colNum++);
					cell.setCellValue((String) "Week: " + (i / allCourtBlocks.size()));
				}
				if (courtBlockID.contains(courtNum)) {
					if (arrayWCIntVar[i].getValue() >= 0) {
						if (allMatches.get(arrayWCIntVar[i].getValue()).getDivision().getDivisionID() == curDivision
								.getDivisionID()) {
							// System.out.println(colNum);
							sheet.autoSizeColumn(colNum);
							cell = row.createCell(colNum++);
							cell.setCellStyle(matchCellStyle);
							cell.setCellValue((String) allMatches.get(arrayWCIntVar[i].getValue()).getMatchDetails());
						} else {
						}
					} else {
					}
				}
			}
			System.out.println("");
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Excel Done");

	}

	
}