package nestla.tennis.league.scheduler.cspOLD;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author alexy
 */
public class Division {
    private String divisionName;
    private int divisionID;
    private ArrayList<Team> teams;
    private Clubs clubs;
    private HashMap<Integer, Match> matches;
    private int firstMatchNum;    

    public Division() {
        teams = new ArrayList<>();
        matches = new HashMap<>();
    }

    
    public Division(String divisionName, int divisionID) {
        this.divisionName = divisionName;
        this.divisionID = divisionID;
        teams = new ArrayList<>();
        clubs = new Clubs();
        matches = new HashMap<>();
    }
 
    public String getDivisionName() {
        return divisionName;
    }

    public int getDivisionID() {
        return divisionID;
    }
    

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public Clubs getClubs() {
        return clubs;
    }

    public HashMap<Integer, Match> getMatches() {
        return matches;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
    
    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public void setClubs(Clubs clubs) {
        this.clubs = clubs;
    }

    public void setMatches(HashMap<Integer, Match> matches) {
        this.matches = matches;
    }
    
    public void addTeam(Team aTeam){
        teams.add(aTeam);
    }
    
    public void removeTeam(Team aTeam){
        teams.remove(aTeam);
    }

    public int getFirstMatchNum() {
        return firstMatchNum;
    }

    public void setFirstMatchNum(int firstMatchNum) {
        this.firstMatchNum = firstMatchNum;
    }
    
    public int getIDOfTeams(){
        return teams.size();
    }

    @Override
    public String toString() {
        return "Division: " + divisionName + ", divisionID=" + divisionID + ", teams=" + teams + ", clubs=" + clubs + ", matches=" + matches + ", firstMatchNum=" + firstMatchNum;
    }

    
    
}