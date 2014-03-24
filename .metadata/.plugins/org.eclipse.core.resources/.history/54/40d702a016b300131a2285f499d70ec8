package scheduleGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Used to store predicted days and generate new days.
 * 
 * @author schneimd. Created Oct 18, 2012.
 */
public class Schedule extends Thread implements Serializable {

	private ArrayList<Worker> workers;
	private ArrayList<Day> days;
	private TreeMap<String, TreeMap<String, Worker>> schedule;
	private GregorianCalendar cal;
	private HashMap<Integer, ArrayList<Worker>> workerIndices;
	private boolean workerForEveryJob = true;

	/**
	 * Used to construct an initial schedule, used if one does not exist.
	 * 
	 * @param daySlots
	 * @param wrks
	 */
	public Schedule(ArrayList<Day> daySlots, ArrayList<Worker> wrks) {
		this.workers = wrks;
		this.days = daySlots;
		this.workerIndices = new HashMap<Integer, ArrayList<Worker>>();
		for (int i = 1; i <= 7; i++) {
			this.workerIndices.put(i, new ArrayList<Worker>());
		}
		this.generateIndices();

		// Key is year/month/day format and item is a hashmap with key nameOfJob
		// and item Worker
		this.schedule = new TreeMap<String, TreeMap<String, Worker>>();

		this.cal = new GregorianCalendar();

		this.calculateNextMonth();
	}

	@Override
	public void run() {
		this.calculateNextMonth();
	}

	/**
	 * returns workers in schedule.
	 * 
	 * @return workers
	 */
	public ArrayList<Worker> getWorkers() {
		return this.workers;
	}

	private void generateIndices() {
		for (int i = 0; i < this.workers.size(); i++) {
			for (Day day : this.workers.get(i).getDays()) {
				int numDay = this.numForName(day.getNameOfDay());
				this.workerIndices.get(numDay).add(this.workers.get(i));
			}
		}
	}

	/**
	 * Calculates another month of schedule based on workers availability.
	 * 
	 */
	//SMELL - SWAP 1 TEAM 04: Long Method
	//SWAP 1 Team 4 Change - Extract Method
	//SWAP 1 
	private synchronized void calculateNextMonth() {

		int initialSize = this.schedule.size();

		// If the schedule has already been generated
		if (this.schedule.size() > 0) {
			scheduleExistsAlready();
		}

		// Used to see if month changes
		int currentMonth = this.cal.get(Calendar.MONTH);

		int daysInMonth = this.days.size();
		ArrayList<Integer> numOfJobs = new ArrayList<Integer>();

		// While still in the current month generate a schedule for each day
		while (currentMonth == this.cal.get(Calendar.MONTH)) {

			for (Day day : this.days) {
				if (this.cal.get(Calendar.DAY_OF_WEEK) == this.numForName(day
						.getNameOfDay())) {

					
					String date = this.cal.get(Calendar.YEAR)
							+ "/"
							+ String.format("%02d",
									(this.cal.get(Calendar.MONTH) + 1))
							+ "/"
							+ String.format("%02d",
									this.cal.get(Calendar.DAY_OF_MONTH));
					this.schedule.put(date, assignJobsForDay(day,numOfJobs));
					break; // Breaks so it doesn't check the other days
				}
			}
			this.cal.add(Calendar.DATE, 1);
		}
		HTMLGenerator.makeTable(daysInMonth, numOfJobs);
		//SMELL - SWAP 1 TEAM 04 - Comments - The piece of codes below is unclear without this comment. method extraction may be waranted.
		// Calls itself if there aren't many days generated
		// For instance if the date it was created is the last day of the
		// month it would only makes one day of schedule.
		if (this.schedule.size() - initialSize < 2 && !this.workerForEveryJob) {
			this.calculateNextMonth();
		}

		Main.dumpConfigFile();
	}
	
	private void scheduleExistsAlready() {
		String lastDateMade = this.schedule.lastKey();
		String[] parts = lastDateMade.split("/");
		int year = Integer.parseInt(parts[0]);
		int month = Integer.parseInt(parts[1]) - 1;
		int day = Integer.parseInt(parts[2]);
		this.cal = new GregorianCalendar(year, month, day);
		int tempNum = this.cal.get(Calendar.MONTH);
		while (tempNum == this.cal.get(Calendar.MONTH)) {
			this.cal.add(Calendar.DATE, 1);
		}
	}
	
	//This method mutates numOfJobs
	private TreeMap<String, Worker> assignJobsForDay(Day day, ArrayList<Integer> numOfJobs) {
		TreeMap<String, Worker> jobsWithWorker = new TreeMap<String, Worker>();
		ArrayList<String> workersWorking = new ArrayList<String>();

		ArrayList<String> jobsInOrder = day.getJobs();

		// Used for html later

		numOfJobs.add(jobsInOrder.size());

		//

		for (String job : jobsInOrder) {

			ArrayList<Worker> workersForJob = getWorkersForJob(workersWorking,job,day);
			Worker w = getWorkerForJob(job, workersForJob, day);
			jobsWithWorker.put(job, w);
			if (w.getName().equals("Empty")) {
				break;
			}
			else {
				workersWorking.add(w.getName());
			}
		}
		return jobsWithWorker;
	}
	
	private ArrayList<Worker> getWorkersForJob(ArrayList<String> workersWorking, String job, Day day) {
		ArrayList<Worker> workersForJob = new ArrayList<Worker>();

		for (Worker worker : this.workerIndices.get(this
				.numForName(day.getNameOfDay()))) {
			Day workerDay = worker.getDayWithName(day
					.getNameOfDay());
			if (workerDay.getJobs().contains(job)
					&& !workersWorking.contains(worker
							.getName())) {
				workersForJob.add(worker);

			}
		}
		return workersForJob;
	}
	
	/*BONUS FEATURE - Each worker gets only one assignment before being repeated.
	 * We use the field in worker so that every time he gets assigned to a job he keeps track of how many
	 * jobs he has worked of that type. We choose the worker that has worked this job the least.
	 * 
	 * This was fairly easy to do since we have already refactored the code to extract methods
	 */
	private Worker getWorkerForJob(String job, ArrayList<Worker> workersForJob, Day day) {
		if (workersForJob.size() > 0) {
			Worker workerForJob = getLeastUsedWorker(workersForJob, job);
			for (Worker w : workersForJob) {
				if (w.numWorkedForJob(job) < workerForJob
						.numWorkedForJob(job)) {
					workerForJob = w;
				}
			}
			workerForJob.addWorkedJob(job);
			return workerForJob;
		} else {
			JOptionPane
					.showMessageDialog(
							new JFrame(),
							"No workers are able to work as a(n) "
									+ job + " on "
									+ day.getNameOfDay());
			this.workerForEveryJob = false;
			return new Worker("Empty",new ArrayList<Day>());
		}
	}
	
	/*
	 * BONUS FEATURE - Function implementation
	 */
	private Worker getLeastUsedWorker(ArrayList<Worker> workers, String job) {
		Worker leastUsed = workers.get(0);
		ArrayList<Worker> dups = new ArrayList<Worker>();
		for(Worker w: workers) {
			if (w.numWorkedForJob(job) < leastUsed.numWorkedForJob(job)) {
				leastUsed = w;
				dups = new ArrayList<Worker>();
				dups.add(w);
			}
			else if (w.numWorkedForJob(job) == leastUsed.numWorkedForJob(job)) {
				dups.add(w);
			}
		}
		return dups.get(new Random().nextInt(dups.size()));
	}

	private int numForName(String nameOfDay) {
		int dayNum = 0;
		if (nameOfDay.equals("Sunday")) {
			dayNum = 1;
		} else if (nameOfDay.equals("Monday")) {
			dayNum = 2;
		} else if (nameOfDay.equals("Tuesday")) {
			dayNum = 3;
		} else if (nameOfDay.equals("Wednesday")) {
			dayNum = 4;
		} else if (nameOfDay.equals("Thursday")) {
			dayNum = 5;
		} else if (nameOfDay.equals("Friday")) {
			dayNum = 6;
		} else if (nameOfDay.equals("Saturday")) {
			dayNum = 7;
		}
		return dayNum;
	}

	// /**
	// * Returns the month/day/year of next date with the name of day.
	// *
	// * @param nameOfDay
	// * @return string of year/month/day format
	// */
	// private String getNextDate(String nameOfDay) {
	// int dayNum = numForName(nameOfDay);
	// GregorianCalendar tempCal = (GregorianCalendar) this.cal.clone();
	//
	// tempCal.add(Calendar.DATE, 1);
	// while (tempCal.get(Calendar.DAY_OF_WEEK) != dayNum) {
	// tempCal.add(Calendar.DATE, 1);
	// }
	// return String.valueOf(tempCal.get(Calendar.YEAR)) + "/" +
	// String.valueOf(tempCal.get(Calendar.MONTH)) + "/"
	// + String.valueOf(tempCal.get(Calendar.DAY_OF_MONTH));
	// }

	/**
	 * Returns the schedule.
	 * 
	 * @return HashMap schedule
	 */
	public TreeMap<String, TreeMap<String, Worker>> getSchedule() {
		return this.schedule;
	}

}
