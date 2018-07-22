package nestla.tennis.league.scheduler.cspOLD;

/**
 *
 * @author alexy
 */
public class Match {

    private int matchID;
    private Team homeTeam;
    private Team awayTeam;
    private Division division;

    public Match(int matchID, Team homeTeam, Team awayTeam, Division division) {
        this.matchID = matchID;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.division = division;
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

    void printMatch() {
        System.out.println("Match ID: " + matchID + " || HomeTeam: " + homeTeam.getTeamName() + " vs AwayTeam: " + awayTeam.getTeamName());
    }

    @Override
    public String toString() {
        return "MatchID: " + matchID + " || HomeTeam: " + homeTeam.getTeamName() + " vs AwayTeam: " + awayTeam.getTeamName();
    }

	public String getMatchDetails() {
		return ("HomeTeam: " + homeTeam.getTeamName() + " vs AwayTeam: " + awayTeam.getTeamName());
	}

}
