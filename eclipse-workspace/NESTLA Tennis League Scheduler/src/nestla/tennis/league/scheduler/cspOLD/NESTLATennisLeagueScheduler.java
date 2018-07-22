package nestla.tennis.league.scheduler.cspOLD;

/**
 *
 * @author alexy
 */
public class NESTLATennisLeagueScheduler {
//
//	/**
//	 * @param args
//	 *            the command line arguments
//	 */
//	public static void main(String[] args) {
//
//		// Grouping objects
//		League divisions = new League();
//		Clubs clubs = new Clubs();
//
////		 // ----VERSION 1----
////		 // Simplest form 4 teams, 4 clubs, 1 division
////		 // Division
////		 Division division1 = new Division("Division 1", 1);
////		 // Add League
////		 divisions.addDivision(division1);
////		 // Clubs
////		 Club cults = new Club("Cults", true, 6);
////		 Club aboyne = new Club("Aboyne", true, 4);
////		 Club banchory = new Club("Banchory", true, 2);
////		 Club stonehaven = new Club("Stonehaven", true, 2);
////		 // Add Clubs
////		 clubs.addClub(cults);
////		 clubs.addClub(aboyne);
////		 clubs.addClub(banchory);
////		 clubs.addClub(stonehaven);
////		 // Teams
////		 Team cultsA = new Team("Cults A", cults, 1);
////		 Team aboyneA = new Team("Aboyne A", aboyne, 1);
////		 Team banchoryA = new Team("Banchory A", banchory, 1);
////		 Team stonehavenA = new Team("Stonehaven A", stonehaven, 1);
//
//		// // ----VERSION 1.1----
//		// // Simplest form of 6 teams, 6 clubs, 1 division
//		// // Division
//		// Division division1 = new Division("Division 1", 1);
//		// // Add League
//		// divisions.addDivision(division1);
//		// // Clubs
//		// Club cults = new Club("Cults", true, 2);
//		// Club aboyne = new Club("Aboyne", true, 2);
//		// Club banchory = new Club("Banchory", true, 2);
//		// Club stonehaven = new Club("Stonehaven", true, 2);
//		// Club udny = new Club("Udny", true, 2);
//		// Club westhill = new Club("Westhill", true, 2);
//		// // Add Clubs
//		// clubs.addClub(cults);
//		// clubs.addClub(aboyne);
//		// clubs.addClub(banchory);
//		// clubs.addClub(stonehaven);
//		// clubs.addClub(udny);
//		// clubs.addClub(westhill);
//		// // Teams
//		// Team cultsA = new Team("Cults A", cults, 1);
//		// Team aboyneA = new Team("Aboyne A", aboyne, 1);
//		// Team banchoryA = new Team("Banchory A", banchory, 1);
//		// Team stonehavenA = new Team("Stonehaven A", stonehaven, 1);
//		// Team undyA = new Team("Udny A", udny, 1);
//		// Team westhillA = new Team("Westhill A", westhill, 1);
//
//		// // ----VERSION 2----
//		// // 4 teams, 3 clubs, 1 division
//		// // Division
//		// Division division1 = new Division("Division 1", 1);
//		// // Add League
//		// divisions.addDivision(division1);
//		// // Clubs
//		// Club cults = new Club("Cults", true, 4);
//		// Club aboyne = new Club("Aboyne", true, 2);
//		// Club banchory = new Club("Banchory", true, 2);
//		// // Add Clubs
//		// clubs.addClub(cults);
//		// clubs.addClub(aboyne);
//		// clubs.addClub(banchory);
//		// // Teams
//		// Team cultsA = new Team("Cults A", cults, 1);
//		// Team aboyneA = new Team("Aboyne A", aboyne, 1);
//		// Team banchoryA = new Team("Banchory A", banchory, 1);
//		// Team cultsB = new Team("Cults B", cults, 1);
//
////		 // ----VERSION 3----
////		 // 6 teams, 5 clubs, 1 division
////		 // Division
////		 Division division1 = new Division("Division 1", 1);
////		 // Add League
////		 divisions.addDivision(division1);
////		 // Clubs
////		 Club cults = new Club("Cults", true, 4);
////		 Club aboyne = new Club("Aboyne", true, 2);
////		 Club banchory = new Club("Banchory", true, 2);
////		 Club ellon = new Club("Ellon", true, 2);
////		 Club udny = new Club("Udny", true, 2);
////		 // Add Clubs
////		 clubs.addClub(cults);
////		 clubs.addClub(aboyne);
////		 clubs.addClub(banchory);
////		 clubs.addClub(ellon);
////		 clubs.addClub(udny);
////		 // Teams
////		 Team cultsA = new Team("Cults A", cults, 1);
////		 Team aboyneA = new Team("Aboyne A", aboyne, 1);
////		 Team ellonA = new Team("Ellon A", ellon, 1);
////		 Team banchoryA = new Team("Banchory A", banchory, 1);
////		 Team cultsB = new Team("Cults B", cults, 1);
////		 Team udnyA = new Team("Udny A", udny, 1);
//
////		 //----VERSION 3.1----
////		 // 6 teams, 4 clubs, 1 division
////		 // Division
////		 Division division1 = new Division("Division 1", 1);
////		 // Add League
////		 divisions.addDivision(division1);
////		 // Clubs
////		 Club cults = new Club("Cults", true, 6);
////		 Club aboyne = new Club("Aboyne", true, 2);
////		 Club banchory = new Club("Banchory", true, 2);
////		 Club stonehaven = new Club("Stonehaven", true, 2);
////		 // Add Clubs
////		 clubs.addClub(cults);
////		 clubs.addClub(aboyne);
////		 clubs.addClub(banchory);
////		 clubs.addClub(stonehaven);
////		 // Teams
////		 Team cultsA = new Team("Cults A", cults, 1);
////		 Team cultsB = new Team("Cults B", cults, 1);
////		 Team cultsC = new Team("Cults C", cults, 1);
////		 Team aboyneA = new Team("Aboyne A", aboyne, 1);
////		 Team banchoryA = new Team("Banchory A", banchory, 1);
////		 Team stonehavenB = new Team("Stonehaven B", stonehaven, 1);
//
////		// ----VERSION 4----
////		// 8 teams, 6 clubs, 2 division
////		// Division
////		Division division1 = new Division("Division 1", 1);
////		Division division2 = new Division("Division 2", 2);
////		// Add League
////		divisions.addDivision(division1);
////		divisions.addDivision(division2);
////		// Clubs
////		Club cults = new Club("Cults", true, 6);
////		Club aboyne = new Club("Aboyne", true, 2);
////		Club banchory = new Club("Banchory", true, 2);
////		Club insch = new Club("Isnch", true, 2);
////		Club kemnay = new Club("Kemnay", true, 2);
////		Club stonehaven = new Club("Stonehaven", true, 2);
////		// Add Clubs
////		clubs.addClub(cults);
////		clubs.addClub(aboyne);
////		clubs.addClub(banchory);
////		clubs.addClub(kemnay);
////		clubs.addClub(insch);
////		clubs.addClub(stonehaven);
////		// Teams
////		Team cultsA = new Team("Cults A", cults, 1);
////		Team aboyneA = new Team("Aboyne A", aboyne, 1);
////		Team banchoryA = new Team("Banchory A", banchory, 1);
////		Team cultsB = new Team("Cults B", cults, 1);
////		Team stonehavenA = new Team("Stonehaven A", stonehaven, 2);
////		Team inchA = new Team("Inch A", insch, 2);
////		Team kemnayA = new Team("Kemnay A", kemnay, 2);
////		Team cultsC = new Team("Cults C", cults, 2);
//		
////		// ----VERSION 4.1----
////		// 8 teams, 4 clubs, 2 division
////		// Division
////		Division division1 = new Division("Division 1", 1);
////		Division division2 = new Division("Division 2", 2);
////		// Add League
////		divisions.addDivision(division1);
////		divisions.addDivision(division2);
////		// Clubs
////		Club cults = new Club("Cults", true, 6);
////		Club aboyne = new Club("Aboyne", true, 2);
////		Club banchory = new Club("Banchory", true, 4);
////		Club stonehaven = new Club("Stonehaven", true, 4);
////		// Add Clubs
////		clubs.addClub(cults);
////		clubs.addClub(aboyne);
////		clubs.addClub(banchory);
////		clubs.addClub(stonehaven);
////		// Teams
////		Team cultsA = new Team("Cults A", cults, 1);
////		Team aboyneA = new Team("Aboyne A", aboyne, 1);
////		Team banchoryA = new Team("Banchory A", banchory, 1);
////		Team cultsB = new Team("Cults B", cults, 1);
////		Team stonehavenA = new Team("Stonehaven A", stonehaven, 2);
////		Team banchoryB = new Team("Banchory B", banchory, 2);
////		Team stonehavenB = new Team("Stonehaven B", stonehaven, 2);
////		Team cultsC = new Team("Cults C", cults, 2);
//
////		// ----VERSION 4.2----
////		// 12 teams, 8 clubs, 2 division (EVERYONE WITH 2 COURTS EACH)
////		// Division
////		Division division1 = new Division("Division 1", 1);
////		Division division2 = new Division("Division 2", 2);
////		// Add League
////		divisions.addDivision(division1);
////		divisions.addDivision(division2);
////		// Clubs
////		Club cults = new Club("Cults", true, 6);
////		Club aboyne = new Club("Aboyne", true, 2);
////		Club banchory = new Club("Banchory", true, 4);
////		Club stonehaven = new Club("Stonehaven", true, 4);
////		
////		Club udny = new Club("Udny", true, 2);
////		Club kemnay = new Club("Kemnay", true, 2);
////		Club insch = new Club("Insch", true, 2);
////		Club aberdeen = new Club("Aberdeen", true, 2);
////		// Add Clubs
////		clubs.addClub(cults);
////		clubs.addClub(aboyne);
////		clubs.addClub(banchory);
////		clubs.addClub(stonehaven);
////		
////		clubs.addClub(udny);
////		clubs.addClub(kemnay);
////		clubs.addClub(insch);
////		clubs.addClub(aberdeen);
////		// Teams
////		Team cultsA = new Team("Cults A", cults, 1);
////		Team aboyneA = new Team("Aboyne A", aboyne, 1);
////		Team banchoryA = new Team("Banchory A", banchory, 1);
////		Team cultsB = new Team("Cults B", cults, 1);
////		Team udnyA = new Team("Udny A", udny, 1);
////		Team kemnayA = new Team("Kemnay B", kemnay, 1);
////		
////		Team stonehavenA = new Team("Stonehaven A", stonehaven, 2);
////		Team banchoryB = new Team("Banchory B", banchory, 2);
////		Team stonehavenB = new Team("Stonehaven B", stonehaven, 2);
////		Team cultsC = new Team("Cults C", cults, 2);
////		Team inschA = new Team("Insch B", insch, 2);
////		Team aberdeenA = new Team("Aberdeen C", aberdeen, 2);
//		
//		//----VERSION 4.3----
//		// 12 teams, 12 clubs, 2 division (EVERYONE WITH 2 COURTS EACH)
//		// Division
//		Division division1 = new Division("Division 1", 1);
//		Division division2 = new Division("Division 2", 2);
//		// Add League
//		divisions.addDivision(division1);
//		divisions.addDivision(division2);
//		// Clubs
//		Club c1 = new Club("c1", true, 2);
//		Club c2 = new Club("c2", true, 2);
//		Club c3 = new Club("c3", true, 2);
//		Club c4 = new Club("c4", true, 2);
//		Club c5 = new Club("c5", true, 2);
//		Club c6 = new Club("c6", true, 2);
//		
//		Club c7 = new Club("c7", true, 2);
//		Club c8 = new Club("c8", true, 2);
//		Club c9 = new Club("c9", true, 2);
//		Club c10 = new Club("c10", true, 2);
//		Club c11 = new Club("c11", true, 2);
//		Club c12 = new Club("c12", true, 2);
//		// Add Clubs
//		clubs.addClub(c1);
//		clubs.addClub(c2);
//		clubs.addClub(c3);
//		clubs.addClub(c4);
//		clubs.addClub(c5);
//		clubs.addClub(c6);
//		
//		clubs.addClub(c7);
//		clubs.addClub(c8);
//		clubs.addClub(c9);
//		clubs.addClub(c10);
//		clubs.addClub(c11);
//		clubs.addClub(c12);
//		
//		// Teams
//		Team t1 = new Team("Team 1", c1, 1);
//		Team t2 = new Team("Team 2", c2, 1);
//		Team t3 = new Team("Team 3", c3, 1);
//		Team t4 = new Team("Team 4", c4, 1);
//		Team t5 = new Team("Team 5", c5, 1);
//		Team t6 = new Team("Team 6", c6, 1);
//		
//		Team t7 = new Team("Team 7", c7, 2);
//		Team t8 = new Team("Team 8", c8, 2);
//		Team t9 = new Team("Team 9", c9, 2);
//		Team t10 = new Team("Team 10", c10, 2);
//		Team t11 = new Team("Team 11", c11, 2);
//		Team t12 = new Team("Team 12", c12, 2);
//
//		// // ----VERSION 5----
//		// // 12 teams, 9 clubs, 3 division
//		// // Division
//		// Division division1 = new Division("Division 1", 1);
//		// Division division2 = new Division("Division 2", 2);
//		// Division division3 = new Division("Division 3", 3);
//		// // Add League
//		// divisions.addDivision(division1);
//		// divisions.addDivision(division2);
//		// divisions.addDivision(division3);
//		// // Clubs
//		// Club cults = new Club("Cults", true, 6);
//		// Club aboyne = new Club("Aboyne", true, 2);
//		// Club banchory = new Club("Banchory", true, 2);
//		// Club insch = new Club("Isnch", true, 2);
//		// Club kemnay = new Club("Kemnay", true, 2);
//		// Club stonehaven = new Club("Stonehaven", true, 2);
//		// Club ellon = new Club("Ellon", true, 4);
//		// Club kings = new Club("Kings", true, 2);
//		// Club westburn = new Club("Westburn", true, 2);
//		// // Add Clubs
//		// clubs.addClub(cults);
//		// clubs.addClub(aboyne);
//		// clubs.addClub(banchory);
//		// clubs.addClub(kemnay);
//		// clubs.addClub(insch);
//		// clubs.addClub(stonehaven);
//		// clubs.addClub(ellon);
//		// clubs.addClub(kings);
//		// clubs.addClub(westburn);
//		// // Teams
//		// Team cultsA = new Team("Cults A", cults, 1);
//		// Team aboyneA = new Team("Aboyne A", aboyne, 1);
//		// Team banchoryA = new Team("Banchory A", banchory, 1);
//		// Team cultsB = new Team("Cults B", cults, 1);
//		// Team stonehavenA = new Team("Stonehaven A", stonehaven, 2);
//		// Team inchA = new Team("Inch A", insch, 2);
//		// Team kemnayA = new Team("Kemnay A", kemnay, 2);
//		// Team cultsC = new Team("Cults C", cults, 2);
//		// Team ellonA = new Team("Ellon A", ellon, 3);
//		// Team ellonB = new Team("Ellon B", ellon, 3);
//		// Team kingsA = new Team("kings A", kings, 3);
//		// Team westburnA = new Team("Westburn A", westburn, 3);
//
////		 // ----VERSION 5.1----
////		 // 6 teams per Div, 12 clubs, 3 division (All team has 2 courts)
////		 // Division
////		 Division division1 = new Division("Division 1", 1);
////		 Division division2 = new Division("Division 2", 2);
////		 Division division3 = new Division("Division 3", 3);
////		 // Add League
////		 divisions.addDivision(division1);
////		 divisions.addDivision(division2);
////		 divisions.addDivision(division3);
////		 // Clubs
////		 Club cults = new Club("Cults", true, 6);
////		 Club aboyne = new Club("Aboyne", true, 4);
////		 Club banchory = new Club("Banchory", true, 2);
////		 Club insch = new Club("Insch", true, 4);
////		 Club kemnay = new Club("Kemnay", true, 2);
////		 Club stonehaven = new Club("Stonehaven", true, 4);
////		 Club davidlloyd = new Club("Davidlloyd", true, 2);
////		 Club ellon = new Club("Ellon", true, 4);
////		 Club kings = new Club("Kings", true, 2);
////		 Club westburn = new Club("Westburn", true, 4);
////		 Club westhill = new Club("Westhill", true, 2);
////		 // Add Clubs
////		 clubs.addClub(cults);
////		 clubs.addClub(aboyne);
////		 clubs.addClub(banchory);
////		 clubs.addClub(kemnay);
////		 clubs.addClub(insch);
////		 clubs.addClub(stonehaven);
////		 clubs.addClub(davidlloyd);
////		 clubs.addClub(ellon);
////		 clubs.addClub(kings);
////		 clubs.addClub(westburn);
////		 clubs.addClub(westhill);
////		 // Teams
////		 Team cultsA = new Team("Cults A", cults, 1);
////		 Team aboyneA = new Team("Aboyne A", aboyne, 1);
////		 Team banchoryA = new Team("Banchory A", banchory, 1);
////		 Team cultsB = new Team("Cults B", cults, 1);
////		 Team stonehavenA = new Team("Stonehaven A", stonehaven, 1);
////		 Team inschA = new Team("Inch A", insch, 1);
////		 Team kemnayA = new Team("Kemnay A", kemnay, 2);
////		 Team cultsC = new Team("Cults C", cults, 2);
////		 Team aboyneB = new Team("Aboyne B", aboyne, 2);
////		 Team kingsA = new Team("Kings A", kings, 2);
////		 Team ellonA = new Team("Ellon A", ellon, 2);
////		 Team westburnA = new Team("Westburn A", westburn, 2);
////		 Team stonehavenB = new Team("Stonehaven B", stonehaven, 3);
////		 Team inschB = new Team("Insch B", insch, 3);
////		 Team ellonB = new Team("Ellon B", ellon, 3);
////		 Team westhillA = new Team("Westhill A", westhill, 3);
////		 Team davidlloydA = new Team("David Lloyd A", davidlloyd, 3);
////		 Team westburnB = new Team("Westburn B", westburn, 3);
//
////		 // ----FINAL VERSION----
////		 // NESTLA 2017 Men's League
////		 //224 Matches in season
////		 // Division
////		 Division division1 = new Division("Division 1", 1);
////		 Division division2 = new Division("Division 2", 2);
////		 Division division3 = new Division("Division 3", 3);
////		 Division division4 = new Division("Division 4", 4);
////		 Division division5 = new Division("Division 5", 5);
////		 // Add League
////		 divisions.addDivision(division1);
////		 divisions.addDivision(division2);
////		 divisions.addDivision(division3);
////		 divisions.addDivision(division4);
////		 divisions.addDivision(division5);
////		 // Clubs
////		 Club aboyne = new Club("Aboyne", true, 4);
////		 Club albury = new Club("Albury", false, 5);
////		 Club banchory = new Club("Banchory", true, 3);
////		 Club cults = new Club("Cults", true, 6);
////		 Club davidlloyd = new Club("David Llyod", true, 2);
////		 Club ellon = new Club("Ellon", true, 2);
////		 Club insch = new Club("Insch", true, 2);
////		 Club kings = new Club("Kings College", true, 3);
////		 Club longside = new Club("Longside", true, 2);
////		 Club portlethen = new Club("Portlethen", true, 2);
////		 Club rubislaw = new Club("Rubislaw", true, 3);
////		 Club stonehaven = new Club("Stonehaven", true, 5);
////		 Club udny = new Club("Udny", true, 2);
////		 Club westburn = new Club("Westburn", true, 4);
////		 Club westhill = new Club("Westhill", true, 2);
////		 // Add Clubs
////		 clubs.addClub(aboyne);
////		 clubs.addClub(albury);
////		 clubs.addClub(banchory);
////		 clubs.addClub(cults);
////		 clubs.addClub(davidlloyd);
////		 clubs.addClub(ellon);
////		 clubs.addClub(insch);
////		 clubs.addClub(kings);
////		 clubs.addClub(longside);
////		 clubs.addClub(portlethen);
////		 clubs.addClub(rubislaw);
////		 clubs.addClub(stonehaven);
////		 clubs.addClub(udny);
////		 clubs.addClub(westburn);
////		 clubs.addClub(westhill);
////		 // Teams
////		 //Div 1 Teams
////		 Team davidlloydA = new Team("David Lloyd A", davidlloyd, 1);
////		 Team davidlloydB = new Team("David Lloyd B", davidlloyd, 1);
////		 Team rubislawA = new Team("Rubislaw A", rubislaw, 1);
////		 Team rubislawB = new Team("Rubislaw B", rubislaw, 1);
////		 Team cultsA = new Team("Cults A", cults, 1);
////		 Team stonehavenA = new Team("Stonehaven A", stonehaven, 1);
////		 Team stonehavenB = new Team("Stonehaven B", stonehaven, 1);
////		 //Div 2 Teams
////		 Team cultsB = new Team("Cults B", cults, 2);
////		 Team cultsC = new Team("Cults C", cults, 2);
////		 Team kingsA = new Team("Kings A", kings, 2);
////		 Team westhillA = new Team("Westhill A", westhill, 2);
////		 Team longsideA = new Team("Longside A", longside, 2);
////		 Team banchoryA = new Team("Banchory A", banchory, 2);
////		 Team banchoryB = new Team("Banchory B", banchory, 2);
////		 //Div 3 Teams
////		 Team stonehavenC = new Team("Stonehaven C", stonehaven, 3);
////		 Team stonehavenD = new Team("Stonehaven D", stonehaven, 3);
////		 Team rubislawC = new Team("Rubislaw C", rubislaw, 3);
////		 Team rubislawD = new Team("Rubislaw D", rubislaw, 3);
////		 Team cultsD = new Team("Cults D", cults, 3);
////		 Team ellonA = new Team("Ellon A", ellon, 3);
////		 Team aboyneA = new Team("Aboyne A", aboyne, 3);
////		 //Div 4 Teams
////		 Team udnyA = new Team("Udny A", udny, 4);
////		 Team alburyA = new Team("Albury A", albury, 4);
////		 Team inschA = new Team("Insch A", insch, 4);
////		 Team ellonB = new Team("Ellon B", ellon, 4);
////		 Team cultsE = new Team("Cults E", cults, 4);
////		 Team kingsB = new Team("Kings B", kings, 4);
////		 Team tennisAberdeenA = new Team("Tennis Aberdeen A", westburn, 4);
////		 //Div 5 Teams
////		 Team cultsF = new Team("Cults F", cults, 5);
////		 Team udnyB = new Team("Udny B", udny, 5);
////		 Team alburyB = new Team("Albury B", albury, 5);
////		 Team portlethenA = new Team("Portlethen A", portlethen, 5);
////		 Team banchoryC = new Team("Banchory C", banchory, 5);
////		 Team stonehavenE = new Team("Stonehaven E", stonehaven, 5);
////		 Team aboyneB = new Team("Aboyne B", aboyne, 5);
////		 Team tennisAberdeenB = new Team("Tennis Aberdeen B", westburn, 5);
//
//		// Creating scheduler
//		Scheduler scheduler = new Scheduler(divisions, clubs, 2);
//
//		scheduler.setTeamsToDivisions();
//		// Running scheduler
//		scheduler.setTeamIDs();
//		scheduler.printTeamIDs();
//		scheduler.createMatches();
//		scheduler.printAllMatches();
//		scheduler.createAvaliableCourts();
//		scheduler.printAllCourts();
//		scheduler.setCourtsToDivisions();
//		scheduler.printCourtsToDivsions();
//		// DEPRICATED METHODS
//		//// scheduler.setMatchesToCourts();
//		//// scheduler.printAllMatchesToCourts();
//		scheduler.createCourtToMatchesArrayList();
//		scheduler.printCourtToMatchesArrayList();
//		scheduler.convertCourtToMatchesArrayListToArray();
//		scheduler.createConstraints();
//		 scheduler.printAllConstraints();
//		scheduler.createChocoSolverModel();
//		scheduler.printSolutionInTermsOfDivisions();
//		scheduler.printSolutionInTermsOfDivisionsTextVersion();
//		scheduler.writeToExcel();
//
//		// PRINTS IF SOLUTION IS CORRECT!
//		scheduler.checkSolution();
//
//	}

}
