package nestla.tennis.league.scheduler.league;

import java.io.Serializable;

/*
*
* @author alexy
*/
public class Match implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int matchID;
	private Team homeTeam;
	private Team awayTeam;
	private Division division;
	private boolean fixed;

	public Match(int matchID, Team homeTeam, Team awayTeam, Division division, boolean fixed) {
		this.matchID = matchID;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.division = division;
		this.fixed = fixed;
	}

	public Match(int i, boolean fixed) {
		this.matchID = i;
		this.fixed = fixed;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}

	public Team getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}

	public int getMatchID() {
		return matchID;
	}

	public void setMatchID(int matchID) {
		this.matchID = matchID;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	@Override
	public String toString() {
		return "Match [matchID=" + matchID + ", homeTeam=" + homeTeam + ", awayTeam=" + awayTeam+", division=" + division+ "]";
	}

	public String getMatchDetails() {
		return (homeTeam.getTeamName() + " vs " + awayTeam.getTeamName());
	}

	public boolean containsTeamX(Team aTeam) {
		boolean output = false;
		if(homeTeam == aTeam || awayTeam==aTeam) {
			output=true;
		}
		return output;
	}

}