package nestla.tennis.league.scheduler.ga;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.PoissonGenerator;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.PopulationData;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.uncommons.watchmaker.framework.termination.TargetFitness;
import javafx.concurrent.Task;
import nestla.tennis.league.scheduler.league.League;
import nestla.tennis.league.scheduler.league.Schedule;

public class SchedulerGA extends Task<Schedule> {

	private League league;
	private double initialFitness;
	private boolean initialFitnessSet;

	public SchedulerGA(League league) {
		this.league = league;
		initialFitnessSet = false;
	}

	@Override
	protected Schedule call() throws Exception {
		System.out.println("RUNNING");
		// 0. Set up league
		league.setUp();

		// Run hard constraint engine first
		Schedule result = this.contraintSolve();
		// Run soft constraint engine with result from hard constraint engine
		initialFitnessSet = false;
		System.out.println("\n RESULT");
		System.out.println(result.toString());
		result.printSeperateDivision(league);
		result.printSeperateDivisionWords(league);
		result.writeToExcel(league, 1);
		return result;
	}

	public Schedule contraintSolve() {
		// 1. Firstly create a CadidateFactory to produce an initial population of
		// solutions
		ScheduleFactory factory = new ScheduleFactory(league);

		// 5. RNG for EvolutionEngine
		Random rng = new MersenneTwisterRNG();

		// 2. Create a pipeline that applies evolutionary operators.
		List<EvolutionaryOperator<Schedule>> operators = new LinkedList<EvolutionaryOperator<Schedule>>();
		// Add crossover function to pipeline (1 being the number of crossovers)
		operators.add(new ScheduleCrossover(1));
		// Add mutation function to pipeline
		// Poisson Generator for a random ID of mutations with mean of 2
		operators.add(new ScheduleMutation(new PoissonGenerator(2, rng)));
		EvolutionaryOperator<Schedule> pipeline = new EvolutionPipeline<Schedule>(operators);

		// 3. Create a FitnessEvaluator to measure fitness of candidates
		FitnessEvaluator<Schedule> fitnessEvaluator = new ScheduleEvaluator(league);

		// 4. Create a SelectionStrategy to select promising candidates (Mainly based on
		// fitness)
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
				if (initialFitnessSet == false) {
					initialFitness = data.getMeanFitness();
					initialFitnessSet = true;
				}
				updateProgress((initialFitness - data.getBestCandidateFitness()), initialFitness);
				// cell.setCellValue(data.getGenerationID());
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
		GenerationCount generationLimit = new GenerationCount(30000);
		
		Schedule result = engine.evolve(400, 50, new TargetFitness(0, fitnessEvaluator.isNatural()), generationLimit);
		while(engine.getSatisfiedTerminationConditions().get(0).equals(generationLimit)) {
			result = engine.evolve(400, 50, new TargetFitness(0, fitnessEvaluator.isNatural()), generationLimit);
		}
		return result;
	}

	@Override
	public boolean cancel(boolean arg0) {
		return super.cancel(arg0);
	}

	@Override
	protected void updateProgress(double workDone, double max) {
		super.updateProgress(workDone, max);
	}

}
