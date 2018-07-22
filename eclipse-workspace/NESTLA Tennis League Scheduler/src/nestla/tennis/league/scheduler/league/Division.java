/* Class holding details of a single division */

package nestla.tennis.league.scheduler.league;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Division implements Serializable {

	private static final long serialVersionUID = 1L;

	private String divisionName;
	private int divisionID;
	private ArrayList<Team> teams;
	private ArrayList<Match> allMatches;
	private HashMap<Team, ArrayList<Match>> teamMatchesMap;
	private HashMap<Match, ArrayList<Match>> conflictMap;

	public Division(String divisionName, int divisionID) {
		this.divisionName = divisionName;
		this.divisionID = divisionID;
		this.teams = new ArrayList<>();
		this.allMatches = new ArrayList<>();
		this.teamMatchesMap = new HashMap<>();
		this.conflictMap = new HashMap<>();
	}

	/* Getters & Setter */

	public void addTeam(Team team) {
		teams.add(team);
	}

	public ArrayList<Team> getTeams() {
		return teams;
	}

	public int getNumberOfTeams() {
		return teams.size();
	}

	public HashMap<Team, ArrayList<Match>> getTeamMatchesMap() {
		return teamMatchesMap;
	}

	public HashMap<Match, ArrayList<Match>> getConflictMap() {
		return conflictMap;
	}

	public int getDivisionID() {
		return divisionID;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setID(int ID) {
		divisionID = ID;
	}

	public void setName(String text) {
		divisionName = text;
	}

	public ArrayList<Match> getAllMatches() {
		return allMatches;
	}

	public int getMinNumberOfWeeksToComplete() {
		int output = 0;
		if (teams.size() % 2 == 0) {
			// Even
			output = (teams.size() - 1) * 2;
		} else {
			// Odd
			output = teams.size() * 2;
		}
		return output;
	}

	/* Calculating matches and conflict details for scheduler */
	public int calculateMatches(int matchIDCounter) {
		int matchNum = matchIDCounter;
		for (int i = 0; i < teams.size(); i++) {
			Team homeTeam = teams.get(i);
			for (int j = 0; j < teams.size(); j++) {
				if (i != j) {
					Team awayTeam = teams.get(j);
					Match match = new Match(matchNum, homeTeam, awayTeam, this, false);
					setMatchToTeam(homeTeam, match);
					matchNum++;
				}
			}
		}
		this.calculateConflicts();
		return matchNum;
	}

	public void setMatchToTeam(Team aTeam, Match aMatch) {
		ArrayList<Match> tempMatchArrayList = new ArrayList<Match>();
		if (teamMatchesMap.containsKey(aTeam)) {
			tempMatchArrayList = teamMatchesMap.get(aTeam);
		}
		tempMatchArrayList.add(aMatch);
		// Also add match to an allMatchList for ease of use
		allMatches.add(aMatch);
		teamMatchesMap.put(aTeam, tempMatchArrayList);
	}

	public void calculateConflicts() {
		for (int i = 0; i < allMatches.size(); i++) {
			Match currentMatch = allMatches.get(i);
			for (int j = 0; j < allMatches.size(); j++) {
				if (i != j) {
					Match otherMatch = allMatches.get(j);
					if (otherMatch.containsTeamX(currentMatch.getHomeTeam())
							|| otherMatch.containsTeamX(currentMatch.getAwayTeam())) {
						if (conflictMap.containsKey(currentMatch)) {
							ArrayList<Match> tempArrayList = conflictMap.get(currentMatch);
							tempArrayList.add(otherMatch);
							conflictMap.put(currentMatch, tempArrayList);
						} else {
							ArrayList<Match> tempArrayList = new ArrayList<>();
							tempArrayList.add(otherMatch);
							conflictMap.put(currentMatch, tempArrayList);
						}
					}
				}
			}
		}
	}

	/* toString() */
	@Override
	public String toString() {
		return "{divisionName=" + divisionName + ", divisionID=" + divisionID + "}";
	}

	/* Reseting division back to before divisions.setUp() was called */
	public void totalReset() {
		this.teams = new ArrayList<>();
		this.allMatches = new ArrayList<>();
		this.teamMatchesMap = new HashMap<>();
		this.conflictMap = new HashMap<>();
	}

}
