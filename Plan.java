import java.util.Calendar;

public class Plan {
	private Calendar startTime;
	private Calendar endTime;
	private int activityCode;
	
	public Plan(Calendar startTime, Calendar endTime, int activityCode) {
		//SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.startTime=startTime;
		this.endTime=endTime;
		this.activityCode=activityCode;
	}
	
	public Calendar getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Calendar start) {
		this.startTime=start;
	}
	
	public Calendar getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Calendar end) {
		this.endTime=end;
	}
	
	public int getActivityCode() {
		return activityCode;
	}
	
	public void setActivityCode(int code) {
		this.activityCode=code;
	}
	
//	public static void main(String[] args) {
//		Calendar date=Calendar.getInstance();
//		Plan plan = new Plan(date, date, 0);
//	}
}
