package nestla.tennis.league.scheduler.league;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class Schedule implements Serializable {

	private static final long serialVersionUID = 1L;

	// private Match[][] oldMatchArray;
	private Match[][] matchArray;
	private int numCourts;
	private int numWeeks;

	public Schedule(int numWeeks, int numCourts) {
		this.numWeeks = numWeeks;
		this.numCourts = numCourts;
		matchArray = new Match[numWeeks][numCourts];
		// Set all values in array to -1 as they have not been assigned a match
		this.reset();
	}

	// Copy all parents values
	public Schedule(Schedule parent) {
		this.numWeeks = parent.getNumWeeks();
		this.numCourts = parent.getNumCourts();
		matchArray = new Match[numWeeks][numCourts];
		for (int j = 0; j < numCourts; j++) {
			for (int i = 0; i < numWeeks; i++) {
				matchArray[i][j] = parent.getMatchArray()[i][j];
			}
		}
	}

	// Creates a schedule from a 1D array
	public Schedule(int numWeeks, int numCourts, Match[] array) {
		this.numWeeks = numWeeks;
		this.numCourts = numCourts;
		matchArray = new Match[numWeeks][numCourts];

		int arrayCounter = 0;
		for (int j = 0; j < numCourts; j++) {
			for (int i = 0; i < numWeeks; i++) {
				matchArray[i][j] = array[arrayCounter];
				arrayCounter++;
			}
		}
	}

	public Schedule(Match[][] matchArray) {
		this.numWeeks = matchArray.length;
		this.numCourts = matchArray[0].length;
		this.matchArray = matchArray;
	}

	public Match[][] getMatchArray() {
		return matchArray;
	}

	public int getNumCourts() {
		return numCourts;
	}

	public int getNumWeeks() {
		return numWeeks;
	}

	public void setMatch(int weekNum, int courtNum, Match match) {
		while (matchArray[weekNum][courtNum].isFixed() || matchArray[weekNum][courtNum].getMatchID() != -1) {
			weekNum++;
		}
		matchArray[weekNum][courtNum] = match;
	}

	// Method to shuffle the values in each column around.
	public void shuffleAllColumns() {
		// Creating arrays of columns to be shuffled.
		Match[] tempColumn = new Match[numWeeks];
		for (int j = 0; j < numCourts; j++) {
			for (int i = 0; i < numWeeks; i++) {
				tempColumn[i] = matchArray[i][j];
			}
			shuffleArray(tempColumn);
			// Setting the shuffle arrays back into the schedule
			for (int k = 0; k < numWeeks; k++) {
				matchArray[k][j] = tempColumn[k];
			}
		}
	}

	// shuffleArray and swap methods from here:
	// http://www.vogella.com/tutorials/JavaAlgorithmsShuffle/article.html
	public static void shuffleArray(Match[] column) {
		int n = column.length;
		Random random = new Random();
		random.nextInt();
		for (int i = 0; i < n; i++) {
			// Adding check if value is fixed
			if (column[i].isFixed() == false) {
				int change = i + random.nextInt(n - i);
				if (column[change].isFixed() == false) {
					swap(column, i, change);
				}
			}
		}
	}

	private static void swap(Match[] a, int i, int change) {
		Match helper = a[i];
		a[i] = a[change];
		a[change] = helper;
	}

	public void shuffleARandomWeek(Random rng) {
		Boolean weekSwap = true;
		int randomWeekNum1 = rng.nextInt(numWeeks);
		int randomWeekNum2 = rng.nextInt(numWeeks);
		for (int i = 0; i < numCourts; i++) {
			if (this.getMatchArray()[randomWeekNum1][i].getMatchID() == this.getMatchArray()[randomWeekNum2][i]
					.getMatchID()) {
				// Week swap still true
			} else if (this.getMatchArray()[randomWeekNum1][i].isFixed()
					&& this.getMatchArray()[randomWeekNum2][i].isFixed()) {
				// Week swap still true
			} else if (this.getMatchArray()[randomWeekNum1][i].isFixed() == false
					&& this.getMatchArray()[randomWeekNum2][i].isFixed() == false) {
				// Week swap still true
			} else {
				// Week swap false
				weekSwap = false;
			}
		}
		if (weekSwap == true) {
			for (int i = 0; i < numCourts; i++) {
				this.switchValues(randomWeekNum1, randomWeekNum2, i);
			}
		}
	}

	public Match[] convertTo1D() {
		ArrayList<Match> tempList = new ArrayList<>();
		for (int j = 0; j < numCourts; j++) {
			for (int i = 0; i < numWeeks; i++) {
				tempList.add(matchArray[i][j]);
			}
		}
		Match[] tempArray = new Match[tempList.size()];
		for (int i = 0; i < tempList.size(); i++) {
			tempArray[i] = tempList.get(i);
		}
		return tempArray;
	}

	public Match[] getWeekX(int weekNum) {
		Match[] weekX = matchArray[weekNum];
		return weekX;
	}

	public Match[] getColumnX(int colNum) {
		Match[] output = new Match[numWeeks];
		for (int i = 0; i < numWeeks; i++) {
			output[i] = matchArray[i][colNum];
		}
		return output;
	}

	public void switchValues(int randomWeekNum1, int randomWeekNum2, int randomCourtNum) {
		Match temp = matchArray[randomWeekNum1][randomCourtNum];
		matchArray[randomWeekNum1][randomCourtNum] = matchArray[randomWeekNum2][randomCourtNum];
		matchArray[randomWeekNum2][randomCourtNum] = temp;
	}

	/* PRINTING OUT RESULTS */

	public void printSeperateDivision(League league) {
		for (int d = 0; d < league.getNumberOfDivisions(); d++) {
			System.out.println(league.getDivision(d).getDivisionName());
			for (int i = 0; i < matchArray.length; i++) {
				System.out.print("Week: " + i + " -- ");
				for (int j = 0; j < matchArray[i].length; j++) {
					if (matchArray[i][j].getMatchID() > -1) {
						if (league.getDivision(d).getDivisionID() == matchArray[i][j].getDivision()
								.getDivisionID()) {
							System.out.print(matchArray[i][j].getMatchID() + "  ");
						}
					}
				}
				System.out.println();
			}
		}
	}

	public void printSeperateDivisionWords(League league) {
		for (int d = 0; d < league.getNumberOfDivisions(); d++) {
			System.out.println(league.getDivision(d).getDivisionName());
			for (int i = 0; i < matchArray.length; i++) {
				System.out.print("Week: " + i + " || ");
				for (int j = 0; j < matchArray[i].length; j++) {
					if (matchArray[i][j].getMatchID() > -1) {
						if (league.getDivision(d).getDivisionID() == matchArray[i][j].getDivision()
								.getDivisionID()) {
							System.out.print(matchArray[i][j].getMatchDetails() + " || ");
						}
					}
				}
				System.out.println();
			}
		}
	}

	// Create an excel file with solution
	public void writeToExcel(League league, int ID) {
		boolean success = false;
		String userHomeFolder = System.getProperty("user.home");
		// Create a directory; all non-existent ancestor directories are
		// automatically created
		//Folder Name Below
		File folderName = new File(userHomeFolder + "/Desktop/Tennis-Scheduler");
		if (!folderName.exists()) {
			success = (folderName).mkdirs();
			if (!success) {
				// Directory creation failed
				System.out.println("Directory creation failed");
			}
		}
		
		//File Name Below
		String FILE_NAME = folderName.toString()+"/Tennis-Scheduler-Output.xlsx";
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet;
		CellStyle matchCellStyle;
		CellStyle weekCellStyle;
		// Create a tab with information for all division matches
		sheet = workbook.createSheet("All League");
		int rowNum = 0;
		// Match cell style
		matchCellStyle = sheet.getWorkbook().createCellStyle();
		matchCellStyle.setWrapText(true);
		matchCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		matchCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// Week cell style
		weekCellStyle = sheet.getWorkbook().createCellStyle();
		// -------
		Row row = sheet.createRow(rowNum++);
		int colNum = 0;
		Cell cell = row.createCell(colNum++);
		cell.setCellValue((String) "--- All League ---");
		rowNum++;
		row = sheet.createRow(rowNum);
		rowNum++;
		colNum = 1;
		for(int i=0;i<league.getCourts().size();i++) {
			cell = row.createCell(colNum++);
			cell.setCellValue((String) league.getCourtX(i).getCourtName());
		}
		
		for (int i = 0; i < matchArray.length; i++) {
			row = sheet.createRow(rowNum++);
			colNum = 0;
			cell = row.createCell(colNum++);
			cell.setCellValue((String) "Week: " + (i + 1));
			for (int j = 0; j < matchArray[i].length; j++) {
				sheet.autoSizeColumn(colNum);
				cell = row.createCell(colNum++);
				cell.setCellStyle(matchCellStyle);
				if (matchArray[i][j].getMatchID() > -1) {
					cell.setCellValue((String) matchArray[i][j].getMatchDetails());
				} else {
					cell.setCellValue((String) "No Match");
				}
			}
		}
		// Creating a separate tab for each division and adding match information
		for (int divNum = 0; divNum < league.getNumberOfDivisions(); divNum++) {
			sheet = workbook.createSheet(league.getDivision(divNum).getDivisionName());
			rowNum = 0;
			row = sheet.createRow(rowNum++);
			colNum = 0;
			Division curDivision = league.getDivision(divNum);
			cell = row.createCell(colNum++);
			cell.setCellValue((String) "---" + curDivision.getDivisionName() + "---");
			rowNum++;
			for (int i = 0; i < matchArray.length; i++) {
				row = sheet.createRow(rowNum++);
				colNum = 0;
				cell = row.createCell(colNum++);
				cell.setCellValue((String) "Week: " + (i + 1));
				for (int j = 0; j < matchArray[i].length; j++) {
					if (matchArray[i][j].getMatchID() > -1) {
						if (league.getDivision(divNum).getDivisionID() == matchArray[i][j].getDivision()
								.getDivisionID()) {
							sheet.autoSizeColumn(colNum);
							cell = row.createCell(colNum++);
							cell.setCellStyle(matchCellStyle);
							cell.setCellValue((String) matchArray[i][j].getMatchDetails());
						}
					}
				}
			}
		}
		try {
			FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Excel Done");
	}

	@Override
	public String toString() {
		String output = "";

		for (int i = 0; i < matchArray.length; i++) {
			output += "\n";
			for (int j = 0; j < matchArray[i].length; j++) {
				output += matchArray[i][j].getMatchID() + "  ";
			}
		}
		return output;
	}

	public void reset() {
		for (int i = 0; i < numWeeks; i++) {
			for (int j = 0; j < numCourts; j++) {
				matchArray[i][j] = new Match(-1, false);
			}
		}
	}

}
