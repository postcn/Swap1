package scheduleGenerator;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Day is used to store jobs for a given day.
 *
 * @author schneimd.
 *         Created Oct 15, 2012.
 */
public class Day implements Serializable{
	
	private String dayOfWeek;
	private ArrayList<String> jobs = new ArrayList<String>();
	
	/**
	 * Construct a day with a name and jobs.
	 * 
	 * @param name 
	 *
	 * @param jobs
	 */
	//SMELL - SWAP 1 TEAM 04 - Speculative Generality - Jobs are strings everywhere, this means that having them be objects here
	//is speculative generality. (They are treated as objects elsewhere when they are strings also).
	//Fixing this would be very easy, and makes the code more understandable instead of casting everywhere. Jobs are always represented
	//as strings currently so it makes no sense to pass them as objects.
	public Day(String name, ArrayList<Object> jobs)
	{
		this.dayOfWeek = name;
		for(Object i:jobs)
		{
			this.jobs.add((String)i);
		}
	}
	
	/**
	 * Add one jobName.
	 *
	 * @param jobName
	 */
	public void addJob(String jobName) {
		this.jobs.add(jobName);
	}
	
	/**
	 * Set jobs to new jobs.
	 *
	 * @param jobNames
	 */
	public void setJobs(ArrayList<String> jobNames) {
		this.jobs = jobNames;
	}
	
	/**
	 * return current jobs.
	 *
	 * @return jobs
	 */
	public ArrayList<String> getJobs() {
		return this.jobs;
	}
	
	/**
	 * Gives the name of this day.
	 *
	 * @return day of week
	 */
	public String getNameOfDay() {
		return this.dayOfWeek;
	}
}
