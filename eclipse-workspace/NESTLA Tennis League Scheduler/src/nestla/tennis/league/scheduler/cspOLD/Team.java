package nestla.tennis.league.scheduler.cspOLD;

/**
 *
 * @author alexy
 */
public class Team {
    private String teamName;
    private Club club;
    private int divisionID;

    public Team(String teamName, Club aClub, int aDivNum) {
        this.teamName = teamName;
        this.club = aClub;
        this.divisionID = aDivNum;
        club.addTeam(this);
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
    
    public void printTeam(){
        System.out.println("Team Name: "+teamName+" part of club: "+club.getClubName()+" in division: "+divisionID);
	}

}
