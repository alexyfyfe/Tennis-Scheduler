package nestla.tennis.league.scheduler.ga;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import nestla.tennis.league.scheduler.league.League;
import nestla.tennis.league.scheduler.league.Match;
import nestla.tennis.league.scheduler.league.Schedule;
import nestla.tennis.league.scheduler.league.Team;

public class ScheduleFactory extends AbstractCandidateFactory<Schedule> {
	private League league;

	public ScheduleFactory(League league) {
		this.league = league;
	}

	/* Generate Random Candidate Schedules */
	public Schedule generateRandomCandidate(Random rng) {
		
		// Create schedule object with parameters from setup league, this includes
		// the size of the schedule and any matches that are fixed.
		Schedule randomSchedule = new Schedule(league.getSchedule());

		// Iterate through all league
		for (int i = 0; i < league.getNumberOfDivisions(); i++) {
			// For every team to matches hashmap in all league
			for (Map.Entry<Team, ArrayList<Match>> entry : league.getDivision(i).getTeamMatchesMap().entrySet()) {
				Team homeTeam = entry.getKey();
				// Get the list of matches that the home-team has to play.
				ArrayList<Match> opponentsArrayList = entry.getValue();
				// Iterate through the matches list and set the match to the schedule on the
				// correct court, being the home team plays on their clubs courts.
				for (int j = 0; j < opponentsArrayList.size(); j++) {
					randomSchedule.setMatch(league.getCourtX(homeTeam.getCourtID()).getNumberOfMatchesOnCourt(),
							homeTeam.getCourtID(), opponentsArrayList.get(j));
					// This adds one to a counter on each court to stop matches overwriting each
					// other on the scheduler array.
					league.addOneMatchToCourtX(league.getCourtX(homeTeam.getCourtID()));
				}
			}
		}

		// Reset the counter of matches on courts to allow for the next time this method
		// is called to start with no matches on courts.
		league.resetNumberOfMatchesOnCourt();

		// Randomise the values in a schedule only allow to randomise per column.
		randomSchedule.shuffleAllColumns();

		return randomSchedule;
	}
}
