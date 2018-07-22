package nestla.tennis.league.scheduler.ga;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import nestla.tennis.league.scheduler.league.Court;
import nestla.tennis.league.scheduler.league.League;
import nestla.tennis.league.scheduler.league.Match;
import nestla.tennis.league.scheduler.league.Schedule;

public class ScheduleEvaluator implements FitnessEvaluator<Schedule> {
	
	private League league;
	private HashMap<Match, ArrayList<Match>> allConflictMap;

	public ScheduleEvaluator(League league) {
		this.league = league;
		this.allConflictMap = league.getConflictMap();
	}

	@Override
	public double getFitness(Schedule candidate, List<? extends Schedule> population) {
		double fitness = 0;
		int tempVal = 0;
		int numberOfWeeksInSchedule = candidate.getNumWeeks();
		/* CHECKING WEEK BY WEEK FOR CONFLICTS */
		for (int weekNum = 0; weekNum < numberOfWeeksInSchedule; weekNum++) {
			Match[] currentWeek = candidate.getWeekX(weekNum);
			for (int currentWeekValIndex = 0; currentWeekValIndex < currentWeek.length; currentWeekValIndex++) {
				Match currentMatch = currentWeek[currentWeekValIndex];
				if (currentMatch.getMatchID() != -1) {
					//If out of league range add one to fitness
					if(currentMatch.getDivision().getMinNumberOfWeeksToComplete()<numberOfWeeksInSchedule) {
						if(weekNum<2||weekNum>numberOfWeeksInSchedule-3) {
							fitness++;
						}
					}
					//If out of lights range increase fitness
					if (league.getCourtX(currentMatch.getHomeTeam().getCourtID()).hasLights() == false) {
						double numberOfWeeksFitnessScore = Math.floor(currentMatch.getDivision().getMinNumberOfWeeksToComplete() / 4);
						tempVal = (int) (numberOfWeeksFitnessScore - weekNum);
						if (tempVal > 0) {
							fitness = fitness + tempVal;
							tempVal = 0;
						} else {
							tempVal = 0;
						}
						tempVal = (int) (weekNum - (candidate.getNumWeeks() - 1) + numberOfWeeksFitnessScore);
						if (tempVal > 0) {
							fitness = fitness + tempVal;
							tempVal = 0;
						} else {
							tempVal = 0;
						}
					}
					ArrayList<Match> conflictArrayCurrentVal = allConflictMap.get(currentMatch);
					for (int currentWeekNextValIndex = currentWeekValIndex+ 1; currentWeekNextValIndex < currentWeek.length; currentWeekNextValIndex++) {
						Match nextMatch = currentWeek[currentWeekNextValIndex];
						if (nextMatch.getMatchID() != -1) {
							for (int conflictArrayIndex = 0; conflictArrayIndex < conflictArrayCurrentVal.size(); conflictArrayIndex++) {
								if (conflictArrayCurrentVal.get(conflictArrayIndex).getMatchID() == nextMatch.getMatchID()) {
									fitness++;
								}
							}
						}
					}
				}
			}
		}
		return fitness;
	}

	@Override
	public boolean isNatural() {
		return false;
	}

}
