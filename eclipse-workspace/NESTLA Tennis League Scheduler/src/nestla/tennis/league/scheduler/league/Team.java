package nestla.tennis.league.scheduler.league;

import java.io.Serializable;

public class Team implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String teamName;
	private int teamID;
	private int clubID;
	private int divisionID;
	private int courtID;
	private String divisionName;
	private String clubName;
	
	public Team(String teamName, int teamID, int clubID, int divisionID) {
		this.teamName = teamName;
		this.teamID = teamID;
		this.clubID = clubID;
		this.divisionID = divisionID;
	}

	public String getTeamName() {
		return teamName;
	}

	public int getTeamID() {
		return teamID;
	}

	public int getClubID() {
		return clubID;
	}

	public int getDivisionID() {
		return divisionID;
	}

	public int getCourtID() {
		return courtID;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getClubName() {
		return clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public void setTeamID(int teamID) {
		this.teamID = teamID;
	}

	public void setClubID(int clubID) {
		this.clubID = clubID;
	}

	public void setDivisionID(int divisionID) {
		this.divisionID = divisionID;
	}

	public void setCourtID(int courtID) {
		this.courtID = courtID;
	}

	@Override
	public String toString() {
		return "{teamName=" + teamName + ", teamID=" + teamID + ", clubID=" + clubID+ ", divisionID=" + divisionID + "}";
	}

	
	
}
