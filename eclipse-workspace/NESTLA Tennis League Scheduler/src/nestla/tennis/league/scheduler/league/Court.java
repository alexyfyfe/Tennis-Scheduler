package nestla.tennis.league.scheduler.league;

import java.io.Serializable;
import java.util.ArrayList;

public class Court implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int courtNum;
	private String courtName;
	private ArrayList<Team> teams;
	private int numberOfMatchesOnCourt;
	private int numberOfAvailableSlots;
	private int numberOfTotalSlots;
	private boolean lights;
	
	public Court(int courtNum, String courtName, Boolean lights) {
		this.courtNum = courtNum;
		this.courtName = courtName;
		this.lights = lights;
		this.teams = new ArrayList<>();
		numberOfMatchesOnCourt=0;
		numberOfAvailableSlots=0;
		numberOfTotalSlots=0;
	}

	/* Getters & Setter */
	public void addTeam(Team team) {
		teams.add(team);
	}
	
	public ArrayList<Team> getTeams(){
		return teams;
	}
	
	public int getCourtNum() {
		return courtNum;
	}

	public void setCourtNum(int courtNum) {
		this.courtNum = courtNum;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public boolean hasLights() {
		return lights;
	}

	public void setLights(boolean lights) {
		this.lights = lights;
	}
	
	public int getNumberOfMatchesOnCourt() {
		return numberOfMatchesOnCourt;
	}
	
	public void addOneToNumberOfMatchesOnCourt() {
		numberOfMatchesOnCourt++;
	}

	public void setNumberOfMatchesOnCourt(int num) {
		numberOfMatchesOnCourt = num;
	}

	public int getNumberOfAvailableSlots() {
		return numberOfAvailableSlots;
	}
	
	public void addOneToAvailableSlots() {
		numberOfAvailableSlots++;
	}
	
	public void takeOneFromAvailableSlots() {
		numberOfAvailableSlots--;
	}

	public int getNumberOfTotalSlots() {
		return numberOfTotalSlots;
	}

	public void setNumberOfTotalSlots(int numberOfTotalSlots) {
		this.numberOfAvailableSlots = numberOfTotalSlots;
		this.numberOfTotalSlots = numberOfTotalSlots;
	}

	public void setAvailableSlots(int i) {
		numberOfAvailableSlots = i;
	}
	
	
	
}
