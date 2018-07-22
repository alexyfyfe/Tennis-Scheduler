package nestla.tennis.league.scheduler.cspOLD;

import java.util.ArrayList;

/**
 *
 * @author alexy
 */
public class Club {
    private String clubName;
    private boolean lights;
    private int numCourts;
    private double numCourtsBlocksInUse;
    private ArrayList<Team> teams;

    public Club(String clubName, boolean lights, int numCourts) {
        this.clubName = clubName;
        this.lights = lights;
        this.numCourts = numCourts;
        numCourtsBlocksInUse = 0;
        teams = new ArrayList<>();
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public boolean isLights() {
        return lights;
    }

    public void setLights(boolean lights) {
        this.lights = lights;
    }

    public int getNumCourts() {
        return numCourts;
    }

    public void setNumCourts(int numCourts) {
        this.numCourts = numCourts;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    int getNumTeams() {
        return teams.size();
    }
    
    public double getNumCourtsBlocksInUse() {
		return numCourtsBlocksInUse;
	}
    
    public void addToNumCourtsBlocksInUse() {
    	numCourtsBlocksInUse++;
    }

	public void setNumCourtsBlocksInUse(int num) {
		this.numCourtsBlocksInUse = num;
	}

	Team getTeamAtIndex(int clubTeamNum) {
        return teams.get(clubTeamNum);
    }

    void addTeam(Team aTeam) {
       teams.add(aTeam);
    }


}
