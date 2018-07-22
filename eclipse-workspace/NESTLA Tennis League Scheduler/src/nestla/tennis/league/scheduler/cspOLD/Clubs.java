package nestla.tennis.league.scheduler.cspOLD;

import java.util.ArrayList;

/**
 *
 * @author alexy
 */
public class Clubs {
    
    private ArrayList<Club> clubs;

    public Clubs() {
        clubs = new ArrayList<>();
    }
    
    public void addClub(Club aClub){
        clubs.add(aClub);
    }
    
    public ArrayList<Club> getClubs(){
        return clubs;
    }

    void printClubNames() {
        String temp = "";
        for (int i = 0; i < clubs.size(); i++) {
            temp += clubs.get(i).getClubName() + " ";
        }
        System.out.println(temp);
    }

    int getSize() {
        return clubs.size();
    }

    Club getClubAtIndex(int clubNum) {
        return clubs.get(clubNum);
    }
    
}
