package nestla.tennis.league.scheduler.cspOLD;

/**
 *
 * @author alexy
 */
public class CourtBlock {
	private int courtBlockNum;
	private Club club;

	public CourtBlock(int courtBlockNum, Club club) {
		this.courtBlockNum = courtBlockNum;
		this.club = club;
	}

	public int getCourtBlockNum() {
		return courtBlockNum;
	}

	public void setCourtBlockNum(int courtBlockNum) {
		this.courtBlockNum = courtBlockNum;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	@Override
	public String toString() {
		return "CourtBlock: " + courtBlockNum + " Club: " + club.getClubName();
	}

}
