/* Class holding details of all divisions, clubs & teams */

package nestla.tennis.league.scheduler.league;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;

public class League implements Serializable {

	// default serialVersion id
	private static final long serialVersionUID = 1L;

	private ArrayList<Division> divisions;
	private ArrayList<Club> clubs;
	private ArrayList<Match> allMatches;
	private HashMap<Match, ArrayList<Match>> allConflictMap;
	/* A counter to create courts */
	private int courtNumberCounter;
	private int matchNumberCounter;
	private int numberOfWeeks;
	
	/* Schedule */
	private Schedule schedule;
	private boolean divisionsChanged;

	public League() {
		this.divisions = new ArrayList<>();
		this.clubs = new ArrayList<>();
		this.allMatches = new ArrayList<>();
		this.allConflictMap = new HashMap<>();
		this.courtNumberCounter = 0;
		this.matchNumberCounter = 0;
		this.numberOfWeeks = 0;
		divisionsChanged = false;
	}

	/* Add new Division, Club & Team to divisions */
	public void addNewDivision(String divisionName, int divisionID) {
		divisions.add(new Division(divisionName, divisionID));
		schedule = new Schedule(0,0);
		divisionsChanged = true;
	}

	public void addNewClub(String clubName, int clubID, int numberOfCourts, int numberOfCourtsWithLights) {
		clubs.add(new Club(clubName, clubID, numberOfCourts, numberOfCourtsWithLights));
		divisionsChanged = true;
	}

	public void addNewTeam(String teamName, int teamID, int clubID, int divisionID) {
		for (int i = 0; i < clubs.size(); i++) {
			if (clubs.get(i).getClubID() == clubID) {
				clubs.get(i).addTeam(new Team(teamName, teamID, clubID, divisionID));
			}
		}
		divisionsChanged = true;
	}
	
	/* Getters & Setters */
	
	public ArrayList<Match> getAllMatches() {
		for(int i=0;i<divisions.size();i++) {
			allMatches.addAll(divisions.get(i).getAllMatches());
		}
		return allMatches;
	}
	
	public int getNumberOfTeams() {
		int numberOfTeams = 0;
		for (int i = 0; i < clubs.size(); i++) {
			numberOfTeams += clubs.get(i).getTeams().size();
		}
		return numberOfTeams;
	}

	public ArrayList<Team> getTeams() {
		ArrayList<Team> teams = new ArrayList<>();
		for (int i = 0; i < clubs.size(); i++) {
			teams.addAll(clubs.get(i).getTeams());
		}
		return teams;
	}
	
	public int getNumberOfCourts() {
		return courtNumberCounter;
	}

	public int getNumberOfWeeks() {
		for (int i = 0; i < divisions.size(); i++) {
			//Check if number of teams is even or odd
			if(divisions.get(i).getNumberOfTeams()%2==0) {
				//Even
				if (numberOfWeeks < ((divisions.get(i).getNumberOfTeams() - 1) * 2)) {
					numberOfWeeks = (divisions.get(i).getNumberOfTeams() - 1) * 2;
				}
			}else {
				//Odd
				if (numberOfWeeks < (divisions.get(i).getNumberOfTeams() * 2)) {
					numberOfWeeks = divisions.get(i).getNumberOfTeams() * 2;
				}
			}
			
		}
		return numberOfWeeks;
	}

	public ArrayList<Division> getDivisions() {
		return divisions;
	}

	public int getNumberOfDivisions() {
		return divisions.size();
	}

	public Division getDivision(int i) {
		return divisions.get(i);
	}

	public Division getDivisionAtDivNum(int divNum) {
		for (int i = 0; i < divisions.size(); i++) {
			if (divisions.get(i).getDivisionID() == divNum) {
				return divisions.get(i);
			}
		}
		return null;
	}

	public ArrayList<Club> getClubs() {
		return clubs;
	}

	public int getIDOfClubs() {
		return clubs.size();
	}

	public Court getCourtX(int courtID) {
		Court output = new Court(-1,"No Court", false);
		for (int i = 0; i < clubs.size(); i++) {
			for (int j = 0; j < clubs.get(i).getCourts().size(); j++) {
				if (clubs.get(i).getCourts().get(j).getCourtNum() == courtID) {
					output = clubs.get(i).getCourts().get(j);
				}
			}
		}
		return output;
	}

	public void addOneMatchToCourtX(Court courtX) {
		for (int i = 0; i < clubs.size(); i++) {
			for (int j = 0; j < clubs.get(i).getCourts().size(); j++) {
				if (clubs.get(i).getCourts().get(j).getCourtNum() == courtX.getCourtNum()) {
					clubs.get(i).getCourts().get(j).addOneToNumberOfMatchesOnCourt();
				}
			}
		}
	}

	public HashMap<Match, ArrayList<Match>> getConflictMap() {
		return allConflictMap;
	}
	
	public void setDivisions(ObservableList<Division> allDivisions) {
		divisions.removeAll(divisions);
		System.out.println(allDivisions);
		divisions.addAll(allDivisions);
		divisionsChanged = true;
	}

	public int getNumberOfTeamsInDivisionX(int divNum) {
		int output = 0;
		for (int i = 0; i < clubs.size(); i++) {
			for (int j = 0; j < clubs.get(i).getTeams().size(); j++) {
				if (clubs.get(i).getTeams().get(j).getDivisionID() == divNum) {
					output++;
				}
			}
		}
		return output;
	}



	/* DELETE DIVISION GIVEN FROM DIVISION ARRAYLIST */
	public void deleteDivision(ObservableList<Division> divisionsSelected) {
		Division divisionToDelete = divisionsSelected.get(0);
		for (int i = 0; i < divisions.size(); i++) {
			if (divisions.get(i).getDivisionID() == divisionToDelete.getDivisionID()) {
				for (int k = 0; k < clubs.size(); k++) {
					for (int j = 0; j < clubs.get(k).getTeams().size(); j++) {
						if (clubs.get(k).getTeams().get(j).getDivisionID() == divisionToDelete.getDivisionID()) {
							clubs.get(k).getTeams().remove(j);
							j--;
						}
					}
				}
				divisions.remove(i);
			}
		}
		divisionsChanged = true;
	}

	public void setClubs(ObservableList<Club> allClubs) {
		clubs.removeAll(clubs);
		System.out.println(allClubs);
		clubs.addAll(allClubs);
		divisionsChanged = true;
	}

	/* DELETE CLUB GIVEN FROM CLUB ARRAYLIST */
	public void deleteClub(ObservableList<Club> clubsSelected) {
		Club clubToDelete = clubsSelected.get(0);
		for (int k = 0; k < clubs.size(); k++) {
			if(clubs.get(k).getClubID()==clubToDelete.getClubID()) {
				for (int j = 0; j < clubs.get(k).getTeams().size(); j++) {
					if (clubs.get(k).getTeams().get(j).getDivisionID() == clubToDelete.getClubID()) {
						clubs.get(k).getTeams().remove(j);
						j--;
					}
				}
				clubs.remove(k);
			}
		}
		divisionsChanged = true;
	}
	
	
	/* Get Highest ID to make sure new divisions/clubs/teams have unique IDIDs */
	public int getHighestDivisionID() {
		int highest = -1;
		for (int i = 0; i < divisions.size(); i++) {
			if (divisions.get(i).getDivisionID() > highest) {
				highest = divisions.get(i).getDivisionID();
			}
		}
		return highest;
	}

	public int getHighestClubID() {
		int highest = -1;
		for (int i = 0; i < clubs.size(); i++) {
			if (clubs.get(i).getClubID() > highest) {
				highest = clubs.get(i).getClubID();
			}
		}
		return highest;
	}

	public int getHighestTeamID() {
		int highest = -1;
		for (int i = 0; i < clubs.size(); i++) {
			for(int j=0;j<clubs.get(i).getTeams().size();j++) {
				if(highest<clubs.get(i).getTeams().get(j).getTeamID()) {
					highest = clubs.get(i).getTeams().get(j).getTeamID();
				}
			}
		}
		return highest;
	}

	public void setTeams(ObservableList<Team> allTeams) {
		for(int i=0;i<clubs.size();i++){
			clubs.get(i).removeAllTeams();
		}
		for(int i=0;i<allTeams.size();i++) {
			Team curTeam = allTeams.get(i);
			//teamName,teamID,clubID,divisionID
			this.addNewTeam(curTeam.getTeamName(), curTeam.getTeamID(), curTeam.getClubID(), curTeam.getDivisionID());
		}
		divisionsChanged = true;
	}

	/* DELETE TEAM */
	public void deleteTeam(ObservableList<Team> teamsSelected) {
		Team teamToDelete = teamsSelected.get(0);
		for (int k = 0; k < clubs.size(); k++) {
			if(clubs.get(k).getClubID()==teamToDelete.getClubID()) {
				clubs.get(k).removeTeamX(teamToDelete.getTeamID());
			}
		}
		divisionsChanged = true;
	}

	//Setting club name and division name in teams
	public void divisionUpdateTeamClubNameDivisionName() {
		for(int i=0;i<clubs.size();i++) {
			for(int j=0;j<clubs.get(i).getTeams().size();j++) {
				clubs.get(i).getTeams().get(j).setClubName(this.getClubX(clubs.get(i).getTeams().get(j).getClubID()).getClubName());
				clubs.get(i).getTeams().get(j).setDivisionName(this.getDivisionX(clubs.get(i).getTeams().get(j).getDivisionID()).getDivisionName());
			}
		}
		divisionsChanged = true;
	}

	public Division getDivisionX(int divisionID) {
		Division output=null;
		for(int i=0;i<divisions.size();i++) {
			if(divisions.get(i).getDivisionID()==divisionID) {
				output = divisions.get(i);
			}
		}
		return output;
	}

	public Club getClubX(int clubID) {
		Club output=null;
		for(int i=0;i<clubs.size();i++) {
			if(clubs.get(i).getClubID()==clubID) {
				output = clubs.get(i);
			}
		}
		return output;
	}

	public ArrayList<Court> getCourts() {
		ArrayList<Court> output = new ArrayList<>();
		for(int i=0;i<clubs.size();i++) {
			output.addAll(clubs.get(i).getCourts());
		}
		return output;
	}
	
	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public void setScheduleWithMatchArray(Match[][] matchArray) {
		schedule = new Schedule(matchArray);
	}
	
	/* Include all method to set up all individual divisions */
	public void setUp() {
		if(divisionsChanged == true) {
			this.totalReset();
			this.setUpCourts();
			this.setTeamsToDivisions();
			this.calculateMatchesAndConflicts();
			this.createAllConflictMap();
			this.createSchedule();
		}
		divisionsChanged = false;
	}

	private void setUpCourts() {
		for (int i = 0; i < clubs.size(); i++) {
			courtNumberCounter = clubs.get(i).setCourts(courtNumberCounter);
		}
	}

	private void setTeamsToDivisions() {
		for (int i = 0; i < clubs.size(); i++) {
			for (int j = 0; j < clubs.get(i).getTeams().size(); j++) {
				this.getDivisionAtDivNum(clubs.get(i).getTeams().get(j).getDivisionID()).addTeam(clubs.get(i).getTeams().get(j));
			}
		}
	}

	private void calculateMatchesAndConflicts() {
		for (int i = 0; i < divisions.size(); i++) {
			matchNumberCounter = divisions.get(i).calculateMatches(matchNumberCounter);
		}
	}

	private void createAllConflictMap() {
		for (int i = 0; i < divisions.size(); i++) {
			allConflictMap.putAll(divisions.get(i).getConflictMap());
		}
	}
	
	private void createSchedule() {
		schedule = new Schedule(this.getNumberOfWeeks(), courtNumberCounter);
		for(int i=0;i<clubs.size();i++) {
			for(int j=0;j<clubs.get(i).getCourts().size();j++) {
				clubs.get(i).getCourts().get(j).setNumberOfTotalSlots(this.getNumberOfWeeks());
			}
		}
		for(int i=0;i<divisions.size();i++) {
			for(Map.Entry<Team, ArrayList<Match>> entry : divisions.get(i).getTeamMatchesMap().entrySet()) {
			    Team key = entry.getKey();
			    ArrayList<Match> value = entry.getValue();
			    for(int j=0;j<value.size();j++) {
			    	this.getCourtX(key.getCourtID()).takeOneFromAvailableSlots();
			    }
			}
		}
		System.out.println("Create Schedule "+schedule.toString());
	}

	

	/* Resets */
	public void resetNumberOfMatchesOnCourt() {
		for (int i = 0; i < clubs.size(); i++) {
			for (int j = 0; j < clubs.get(i).getCourts().size(); j++) {
				clubs.get(i).getCourts().get(j).setNumberOfMatchesOnCourt(0);
			}
		}
	}
	
	/* Reseting divisions to before divisions.setUp() was called */
	public void totalReset() {
		this.allConflictMap = new HashMap<>();
		this.courtNumberCounter = 0;
		this.matchNumberCounter = 0;
		for (int i = 0; i < divisions.size(); i++) {
			divisions.get(i).totalReset();
		}
		for(int i=0;i<clubs.size();i++) {
			clubs.get(i).removeAllCourts();
		}
		schedule.reset();
	}

	@Override
	public String toString() {
		String output = "";
		output += "League (" + this.getNumberOfDivisions() + ") " + divisions.toString() + "\n";
		output += "Clubs (" + this.getIDOfClubs() + ") " + clubs.toString() + "\n";
		output += "Teams (" + this.getNumberOfTeams() + ") " + this.getTeams().toString() + "\n";
		return output;
	}
	
}
