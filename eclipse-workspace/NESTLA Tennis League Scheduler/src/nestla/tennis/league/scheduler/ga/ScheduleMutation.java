package nestla.tennis.league.scheduler.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import nestla.tennis.league.scheduler.league.Schedule;

public class ScheduleMutation implements EvolutionaryOperator<Schedule> {

	private NumberGenerator<Integer> mutationCountVariable;

	public ScheduleMutation(NumberGenerator<Integer> mutationCount) {
		this.mutationCountVariable = mutationCount;
	}

	@Override
	public List<Schedule> apply(List<Schedule> selectedCandidates, Random rng) {
		List<Schedule> mutatedCandidates = new ArrayList<Schedule>(selectedCandidates.size());
		//Pass schedule one by one to mutate.
		for (Schedule schedule : selectedCandidates) {
			mutatedCandidates.add(mutate(schedule, rng));
		}
		return mutatedCandidates;
	}

	//Mutation switching matches, can only swap matches in same column
	private Schedule mutate(Schedule schedule, Random rng) {
		int mutationCount = Math.abs(mutationCountVariable.nextValue());
		while (mutationCount > 0) {
			int randomWeekNum1 = rng.nextInt(schedule.getNumWeeks());
			int randomWeekNum2 = rng.nextInt(schedule.getNumWeeks());
			int randomCourtNum = rng.nextInt(schedule.getNumCourts());
			/* If matched is fixed do not move it */
			if (schedule.getMatchArray()[randomWeekNum1][randomCourtNum].isFixed()
					|| schedule.getMatchArray()[randomWeekNum2][randomCourtNum].isFixed()) {
			} else {
				schedule.switchValues(randomWeekNum1, randomWeekNum2, randomCourtNum);
				mutationCount--;
			}
		}
		return schedule;
	}
}
