package nestla.tennis.league.scheduler.ga;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import nestla.tennis.league.scheduler.league.Schedule;

public class ExperimentMainGA {

	public static Cell cell;

	public static void main(String[] args) {

//		// Scheduling Setup
//		// Create over arching divisions object
//		League divisions = new League();
//		
//		 // Create individual divisions (Name, DivNum)
//		divisions.addNewDivision("Division 1", 0);
//		divisions.addNewDivision("Division 2", 1);
//		divisions.addNewDivision("Division 3", 2);
//
//		  // Create clubs (Name, ClubNum, NumOfCourts, NumOfCourtsWithLights)
//		divisions.addNewClub("Club 1", 0, 1, 1);
//		divisions.addNewClub("Club 2", 1, 1, 1);
//		divisions.addNewClub("Club 3", 2, 1, 1);
//		divisions.addNewClub("Club 4", 3, 1, 1);
//		divisions.addNewClub("Club 5", 4, 1, 1);
//		divisions.addNewClub("Club 6", 5, 1, 1);
//		divisions.addNewClub("Club 7", 6, 1, 1);
//		divisions.addNewClub("Club 8", 7, 1, 1);
//
//		divisions.addNewClub("Club 9", 8, 1, 1);
//		divisions.addNewClub("Club 10", 9, 1, 1);
//		divisions.addNewClub("Club 11", 10, 1, 1);
//		divisions.addNewClub("Club 12", 11, 1, 1);
//		divisions.addNewClub("Club 13", 12, 1, 1);
//		divisions.addNewClub("Club 14", 13, 1, 1);
//		divisions.addNewClub("Club 15", 14, 1, 1);
//		divisions.addNewClub("Club 16", 15, 1, 1);
//		
//		divisions.addNewClub("Club 17", 16, 1, 1);
//		divisions.addNewClub("Club 18", 17, 1, 1);
//		divisions.addNewClub("Club 19", 18, 1, 1);
//		divisions.addNewClub("Club 20", 19, 1, 1);
//		divisions.addNewClub("Club 21", 20, 1, 1);
//		divisions.addNewClub("Club 22", 21, 1, 1);
//		divisions.addNewClub("Club 23", 22, 1, 1);
//		divisions.addNewClub("Club 24", 23, 1, 1);
//		
//		  // Create Teams (Name, TeamNum, ClubNum, DivNum)
//		  //Division 1 Teams
//		divisions.addNewTeam("Team 1", 0, 0, 0);
//		divisions.addNewTeam("Team 2", 1, 0, 0);
//		divisions.addNewTeam("Team 3", 2, 2, 0);
//		divisions.addNewTeam("Team 4", 3, 2, 0);
//		divisions.addNewTeam("Team 5", 4, 4, 0);
//		divisions.addNewTeam("Team 6", 5, 4, 0);
//		divisions.addNewTeam("Team 7", 6, 6, 0);
//		divisions.addNewTeam("Team 8", 7, 6, 0);
//		 //Division 2 Teams
//		divisions.addNewTeam("Team 9", 8, 8, 1);
//		divisions.addNewTeam("Team 11", 9, 8, 1);
//		divisions.addNewTeam("Team 11", 10, 10, 1);
//		divisions.addNewTeam("Team 12", 11, 10, 1);
//		divisions.addNewTeam("Team 13", 12, 12, 1);
//		divisions.addNewTeam("Team 14", 13, 12, 1);
//		divisions.addNewTeam("Team 15", 14, 14, 1);
//		divisions.addNewTeam("Team 16", 15, 14, 1);
//		//Division 3 Teams
//		divisions.addNewTeam("Team 17", 16, 16, 2);
//		divisions.addNewTeam("Team 18", 17, 16, 2);
//		divisions.addNewTeam("Team 19", 18, 18, 2);
//		divisions.addNewTeam("Team 20", 19, 18, 2);
//		divisions.addNewTeam("Team 21", 20, 20, 2);
//		divisions.addNewTeam("Team 22", 21, 20, 2);
//		divisions.addNewTeam("Team 23", 22, 22, 2);
//		divisions.addNewTeam("Team 24", 23, 22, 2);
		
		
		 /* ACTUAL PROBLEM 2017 Men*/
		 League league = new League();
		 // Add League (Name, DivNum)
		 league.addNewDivision("Division 1", 0);
		 league.addNewDivision("Division 2", 1);
		 league.addNewDivision("Division 3", 2);
		 league.addNewDivision("Division 4", 3);
		 league.addNewDivision("Division 5", 4);
		
		 // Add Clubs (Name, ClubNum, NumberOfCourts, Lights)
		 league.addNewClub("Aboyne", 0, 2, 2);
		 league.addNewClub("Albury", 1, 3, 3);
		 league.addNewClub("Banchory", 2, 2, 2);
		 league.addNewClub("Cults", 3, 3, 3);
		 league.addNewClub("David Llyod", 4, 1, 1);
		 league.addNewClub("Ellon", 5, 1, 1);
		 league.addNewClub("Insch", 6, 1, 1);
		 league.addNewClub("Kings College", 7, 2, 2);
		 league.addNewClub("Longside", 8, 1, 1);
		 league.addNewClub("Portlethen", 9, 1, 1);
		 league.addNewClub("Rubislaw", 10, 2, 2);
		 league.addNewClub("Stonehaven", 11, 3, 3);
		 league.addNewClub("Udny", 12, 1, 1);
		 league.addNewClub("Westburn", 13, 2, 2);
		 league.addNewClub("Westhill", 14, 1, 1);
		
		 // Teams (Name, TeamNum, ClubNum, DivNum)
		 // Div 1 Teams
		 league.addNewTeam("David Lloyd A", 0, 4, 0);
		 league.addNewTeam("David Lloyd B", 1, 4, 0);
		 league.addNewTeam("Rubislaw A", 2, 10, 0);
		 league.addNewTeam("Rubislaw B", 3, 10, 0);
		 league.addNewTeam("Cults A", 4, 3, 0);
		 league.addNewTeam("Stonehaven A", 5, 11, 0);
		 league.addNewTeam("Stonehaven B", 6, 11, 0);
		 // Div 2 Teams
		 league.addNewTeam("Cults B", 7, 3, 1);
		 league.addNewTeam("Cults C", 8, 3, 1);
		 league.addNewTeam("Kings A", 9, 7, 1);
		 league.addNewTeam("Westhill A", 9, 14, 1);
		 league.addNewTeam("Longside A", 11, 8, 1);
		 league.addNewTeam("Banchory A", 12, 2, 1);
		 league.addNewTeam("Banchory B", 13, 2, 1);
		 // Div 3 Teams
		 league.addNewTeam("Stonehaven C", 14, 11, 2);
		 league.addNewTeam("Stonehaven D", 15, 11, 2);
		 league.addNewTeam("Rubislaw C", 16, 10, 2);
		 league.addNewTeam("Rubislaw D", 17, 10, 2);
		 league.addNewTeam("Cults D", 18, 3, 2);
		 league.addNewTeam("Ellon A", 19, 5, 2);
		 league.addNewTeam("Aboyne A", 20, 0, 2);
		 // Div 4 Teams
		 league.addNewTeam("Udny A", 21, 12, 3);
		 league.addNewTeam("Albury A", 22, 1, 3);
		 league.addNewTeam("Insch A", 23, 6, 3);
		 league.addNewTeam("Ellon B", 24, 5, 3);
		 league.addNewTeam("Cults E", 25, 3, 3);
		 league.addNewTeam("Kings B", 26, 7, 3);
		 league.addNewTeam("Tennis Aberdeen A", 27, 13, 3);
		 // Div 5 Teams
		 league.addNewTeam("Cults F", 28, 3, 4);
		 league.addNewTeam("Udny B", 29, 12, 4);
		 league.addNewTeam("Albury B", 30, 1, 4);
		 league.addNewTeam("Portlethen A", 31, 9, 4);
		 league.addNewTeam("Banchory C", 32, 2, 4);
		 league.addNewTeam("Stonehaven E", 33, 11, 4);
		 league.addNewTeam("Aboyne B", 34, 0, 4);
		 league.addNewTeam("Tennis Aberdeen B", 35, 13, 4);

		league.setUp();

		/* TESTING LOOP FOR DIFFERENT MUTATIONS, CROSSOVERS & FITNESS EVALUATORS */
		boolean success = false;
		String userHomeFolder = System.getProperty("user.home");
		// Create a directory; all non-existent ancestor directories are automatically created
		File folderName = new File(userHomeFolder + "/Desktop/Tennis-League-Experiments");
		if (!folderName.exists()) {
			success = (folderName).mkdirs();
			if (!success) {
				// Directory creation failed
				System.out.println("Directory creation failed");
			}
		}
		String FILE_NAME = folderName.toString() + "/REPORT.xlsx";
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet;
		sheet = workbook.createSheet("REPORT");
		int rowNum = 0;
		Row row = sheet.createRow(rowNum++);
		int colNum = 0;
		cell = row.createCell(colNum++);
		cell.setCellValue((String) "--- REPORT ---");

		int numberOfTest = 10;
		for (int i = 0; i < numberOfTest; i++) {
			// GA STARTS
			colNum = 0;
			row = sheet.createRow(rowNum++);
			cell = row.createCell(colNum++);
			cell.setCellValue((String) "Test: " + i);
			cell = row.createCell(colNum++);

			// 1. Firstly create a CadidateFactory to produce an initial population of solutions
			ScheduleFactory factory = new ScheduleFactory(league);

			// 5. RNG for EvolutionEngine
			Random rng = new MersenneTwisterRNG();

			// 2. Create a pipeline that applies evolutionary operators.
			List<EvolutionaryOperator<Schedule>> operators = new LinkedList<EvolutionaryOperator<Schedule>>();
			// Design own crossover
			operators.add(new ScheduleCrossover(1));
			/* Every Candidate mutates once */
			// double mutationProbability = 1;
			// operators.add(new ScheduleMutationConstraints(mutationProbability));
			/* Poisson Generator for a random ID of mutations with mean of 2 */
			operators.add(new ScheduleMutation(new PoissonGenerator(2, rng)));
			EvolutionaryOperator<Schedule> pipeline = new EvolutionPipeline<Schedule>(operators);

			// 3. Create a FitnessEvaluator to measure fitness of candidates
			FitnessEvaluator<Schedule> fitnessEvaluator = new ScheduleEvaluator(league);

			// 4. Create a SelectionStrategy to select promising candidates (Mainly based on fitness)
//			ContinuousUniformGenerator cug = new ContinuousUniformGenerator(0.05,1,rng);
//			SelectionStrategy<Object> selection = new TruncationSelection(cug);
//			SelectionStrategy<Object> selection = new RankSelection();
//			Probability chance = new Probability(0.75);
//			SelectionStrategy<Object> selection = new TournamentSelection(chance);
			SelectionStrategy<Object> selection = new RouletteWheelSelection();
			// 6. Create the EvolutionEngine and add all components needed
			EvolutionEngine<Schedule> engine = new GenerationalEvolutionEngine<Schedule>(factory, pipeline,
					fitnessEvaluator, selection, rng);

			// Print out whilst evolving
			engine.addEvolutionObserver(new EvolutionObserver<Schedule>() {
				public void populationUpdate(PopulationData<? extends Schedule> data) {
					System.out.println();
					System.out.printf("Generation %d: %s\n " + data.getMeanFitness(), data.getGenerationNumber(),
							data.getBestCandidate());
					cell.setCellValue(data.getGenerationNumber());
					// SLOW DOWN GENERATIONS
					// try {
					// Thread.sleep(1000);
					// } catch (InterruptedException e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
				}
			});

			// 7. Run the machine
			//Setting the generation count limit in case stuck in local optimum
			GenerationCount generationLimit = new GenerationCount(20000);
	
			Schedule result = engine.evolve(400, 0, new TargetFitness(0, fitnessEvaluator.isNatural()), generationLimit);
//			while(engine.getSatisfiedTerminationConditions().get(0).equals(generationLimit)) {
//				result = engine.evolve(10000, 100, new TargetFitness(0, fitnessEvaluator.isNatural()), generationLimit);
//			}
			System.out.println("\n RESULT");
			System.out.println(result.toString());
			result.printSeperateDivision(league);
			result.printSeperateDivisionWords(league);
			result.writeToExcel(league, i);
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
