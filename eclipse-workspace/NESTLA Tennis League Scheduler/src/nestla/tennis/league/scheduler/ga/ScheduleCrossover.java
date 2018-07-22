package nestla.tennis.league.scheduler.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import nestla.tennis.league.scheduler.league.Match;
import nestla.tennis.league.scheduler.league.Schedule;

public class ScheduleCrossover extends AbstractCrossover<Schedule> {
	private int crossoverIndexDivision;

	public ScheduleCrossover() {
		this(1);
	}

	protected ScheduleCrossover(int crossoverPoints) {
		super(crossoverPoints);
	}

	@Override
	protected List<Schedule> mate(Schedule parent1, Schedule parent2, int IDOfCrossoverPoints, Random rng) {
		Match[] offspring11D = parent1.convertTo1D();
		Match[] offspring21D = parent2.convertTo1D();

		// Creating temp 1-D array to allow swap
		Match[] temp = new Match[offspring11D.length];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = new Match(-1, false);
		}

		// Cross-over index is always a value that will seperate the columns and not in
		// the middle of a week
		int crossoverIndex = -1;
		crossoverIndexDivision = parent1.getNumWeeks();
		while (crossoverIndex % crossoverIndexDivision != 0) {
			crossoverIndex = (1 + rng.nextInt(parent1.getNumCourts() * parent1.getNumWeeks()));
		}
		System.arraycopy(offspring11D, 0, temp, 0, crossoverIndex);
		System.arraycopy(offspring21D, 0, offspring11D, 0, crossoverIndex);
		System.arraycopy(temp, 0, offspring21D, 0, crossoverIndex);

		Schedule offspring1 = new Schedule(parent1.getNumWeeks(), parent1.getNumCourts(), offspring11D);
		Schedule offspring2 = new Schedule(parent2.getNumWeeks(), parent2.getNumCourts(), offspring21D);

		List<Schedule> result = new ArrayList<Schedule>(2);
		result.add(offspring1);
		result.add(offspring2);

		// Return children schedules with crossover applied
		return result;
	}
}

