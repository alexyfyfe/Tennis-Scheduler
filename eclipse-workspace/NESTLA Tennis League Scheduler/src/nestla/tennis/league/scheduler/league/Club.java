package nestla.tennis.league.scheduler.league;

import java.io.Serializable;
import java.util.ArrayList;

public class Club implements Serializable {

	private static final long serialVersionUID = 1L;

	private String clubName;
	private int clubID;
	private int numberOfCourts;
	private int numberOfCourtsWithLights;
	private int numberOfTeams;
	private ArrayList<Team> teams;
	private ArrayList<Court> courts;

	// The number of courts added to court array with lights
	private int lightCourtsCounter;

	public Club(String clubName, int clubID, int numberOfCourts, int numberOfCourtsWithLights) {
		this.clubName = clubName;
		this.clubID = clubID;
		this.numberOfCourts = numberOfCourts;
		this.numberOfCourtsWithLights = numberOfCourtsWithLights;
		numberOfTeams = 0;
		this.teams = new ArrayList<>();
		this.courts = new ArrayList<>();
		lightCourtsCounter = 0;
	}

	/* Getters & Setter */
	public String getClubName() {
		return clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	public int getClubID() {
		return clubID;
	}

	public ArrayList<Court> getCourts() {
		return courts;
	}

	public int getNumberOfCourts() {
		return numberOfCourts;
	}

	public void setNumberOfCourts(int numberOfCourts) {
		this.numberOfCourts = numberOfCourts;
	}

	public int getNumberOfCourtsWithLights() {
		return numberOfCourtsWithLights;
	}

	public void setNumberOfCourtsWithLights(int numberOfCourtsWithLights) {
		this.numberOfCourtsWithLights = numberOfCourtsWithLights;
	}

	public ArrayList<Team> getTeams() {
		return teams;
	}

	public int getNumberOfTeams() {
		return numberOfTeams;
	}

	/* Calculating number of courts for scheduler to create based on number of teams in club 
	and number of courts, then setting teams to particular courts for scheduler. */
	public int setCourts(int courtNumberCounter) {
		// Clubs cannot have more the 2XnumberOfCourts teams
		Boolean lights = false;
		int numberOfCourtsToCreate = 0;
		if (numberOfCourts > teams.size()) {
			numberOfCourtsToCreate = teams.size();
		} else {
			numberOfCourtsToCreate = numberOfCourts;
		}
		for (int i = 0; i < teams.size(); i++) {
			if (i < numberOfCourtsToCreate) {
				if (lightCourtsCounter < numberOfCourtsWithLights) {
					lights = true;
					lightCourtsCounter++;
				} else {
					lights = false;
				}
				courts.add(new Court(courtNumberCounter, (clubName + ": " + (i + 1)), lights));
				courts.get(i).addTeam(teams.get(i));
				teams.get(i).setCourtID(courtNumberCounter);
				courtNumberCounter++;
			} else {
				courts.get(i - numberOfCourtsToCreate).addTeam(teams.get(i));
				teams.get(i).setCourtID(courts.get(i - numberOfCourtsToCreate).getCourtNum());
			}
		}
		return courtNumberCounter;
	}

	/* Add and remove teams */
	public void addTeam(Team team) {
		numberOfTeams++;
		teams.add(team);
		// System.out.println("Number of Teams: "+numberOfTeams);
	}

	public void removeTeamX(int teamID) {
		for (int i = 0; i < teams.size(); i++) {
			if (teams.get(i).getTeamID() == teamID) {
				teams.remove(i);
			}
		}
	}

	public void removeAllTeams() {
		teams.clear();
		numberOfTeams = 0;
	}

	public void removeAllCourts() {
		courts.clear();
	}

	/* toString() */
	@Override
	public String toString() {
		return "{clubName=" + clubName + ", clubID=" + clubID + ", numberOfCourts=" + numberOfCourts + " withLights="
				+ numberOfCourtsWithLights + "}";
	}

}
