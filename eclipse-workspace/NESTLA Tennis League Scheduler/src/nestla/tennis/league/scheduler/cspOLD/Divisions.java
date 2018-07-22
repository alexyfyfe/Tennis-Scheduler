package nestla.tennis.league.scheduler.cspOLD;

import java.util.ArrayList;

/**
 *
 * @author alexy
 */
public class Divisions {

    private ArrayList<Division> divisions;

    public Divisions() {
        divisions = new ArrayList<>();
    }

    public void addDivision(Division aDivision) {
        divisions.add(aDivision);
    }

    public ArrayList<Division> getDivisions() {
        return divisions;
    }

    void printClubNames() {
        String temp = "";
        for (int i = 0; i < divisions.size(); i++) {
            temp += divisions.get(i).getDivisionName() + " ";
        }
        System.out.println(temp);
    }

    int getSize() {
        return divisions.size();
    }

    Division getDivisionAtIndex(int index) {
        return divisions.get(index);
    }

    Division getDivisionByID(int divNum) {
        Division div = new Division();
        for (int i = 0; i < divisions.size(); i++) {
            if (divisions.get(i).getDivisionID() == divNum) {
                div = divisions.get(i);
                break;
            }
        }
        return div;
    }

    int getMaxIDOfTeamsInDivision() {
        int max = 0;
        for (int i = 0; i < divisions.size(); i++) {
//            System.out.println(divisions.get(i).getDivisionName());
//            System.out.println(divisions.get(i).getIDOfTeams());
            if (max < divisions.get(i).getIDOfTeams()) {
                max = divisions.get(i).getIDOfTeams();
            }
        }
        return max;
    }

}
