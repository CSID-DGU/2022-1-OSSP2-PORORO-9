import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Schedule {
	private Plan schedule[] = new Plan[48]; //24시간 30분 단위로
	//public ArrayList<Plan> schedule = new ArrayList<>();
	
	public Schedule(Calendar date) { 
		
		int i;
		
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		
		for (i=0; i<16; i++) { //오전 8시까지 잠자기
			this.schedule[i].setStartTime(date);
			date.add(Calendar.MINUTE, 30*(i+1));
			this.schedule[i].setEndTime(date);
			this.schedule[i].setActivityCode(0); //잠자기
		}
		
		//activity Code 입력
		//같은 날짜에 같은 일정 나오게 --> 체크
		//activity code 구현한 뒤 생성자로 변경하기
		
		int a=0, b=0; //a는 짧은 일정(일단은 최대가 11이라고 가정), b는 긴 일정
		//short은 30분 long은 1시간으로 계산 --> 추후 30분 단위로 변경 가능
		
		int sa=1, la=0; //short는 1 이상
		double r;
		for (int x=0;x<28;x++) { //short 최소 1회를 빼고 28개
			r = Math.random();
			if(r<0.3) { //30퍼센트의 확률로 short Act 발생
				sa++;
			}
			else { //나머지의 경우 long Act
				la++;
				x++; //30분 2번 사용
			}
		}
		if(sa>11) { //short가 존재하는 short activity 보다 많이 발생할 경우
			sa=11;
			la=(29-sa)/2;
		} //11로 값 조정
		if(sa%2==0) { //short act가 짝수번 발생할 경우
			sa++;
			la=(29-sa)/2;
		} //홀수번으로 값 조정
				
		int SAC[] = {1000,1001,1002,1003,1004,1005,1006,1007,1008,1009,1010};
		int LAC[] = {2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,
				2011,2012,2013};
		
		for(i=16; i<41; i++) {
			int whichOne=0;
			double n = Math.random();
			if(n<0.3) { //30퍼센트의 확률로 short Act 발생
				whichOne=0;
			}
			else { //나머지의 경우 long Act
				whichOne=1;
			}
			
			if (a!=sa && b!=la) {
				if (whichOne==0) { //a(short) 증가
					this.schedule[i].setStartTime(date);
					date.add(Calendar.MINUTE, 30*(i+1));
					this.schedule[i].setEndTime(date);
					//SAC 중에 랜덤으로 하나 뽑아서 set, 한번 나온 코드는 다시 못 나오게
					//ac 코드 완성되면 생성자로 합치기
					a++;
				}
				else { //b (long) 증가
					this.schedule[i].setStartTime(date);
					date.add(Calendar.MINUTE, 30*(i+1));
					this.schedule[i].setEndTime(date);
					i++;
					this.schedule[i].setStartTime(date);
					date.add(Calendar.MINUTE, 30*(i+1));
					this.schedule[i].setEndTime(date);
					b++;
				}
			}
			else if (a==sa) { //a=shortAct 하루 중 정해진 짧은 일정을 전부 다 했을 떄
				this.schedule[i].setStartTime(date);
				date.add(Calendar.MINUTE, 30*(i+1));
				this.schedule[i].setEndTime(date);
				i++;
				this.schedule[i].setStartTime(date);
				date.add(Calendar.MINUTE, 30*(i+1));
				this.schedule[i].setEndTime(date);
				b++;
			} //긴 일정만 실행
			else { //b==longAct 하루 중 정해진 긴 일정을 전부 다 했을 떄
				this.schedule[i].setStartTime(date);
				date.add(Calendar.MINUTE, 30*(i+1));
				this.schedule[i].setEndTime(date);
				a++;
			} //짧은 일정 실행
		}
		
		for (i=41; i<48; i++) { //10시 30분부터 잠자기
			this.schedule[i].setStartTime(date);
			date.add(Calendar.MINUTE, 30*(i+1));
			this.schedule[i].setEndTime(date);
			this.schedule[i].setActivityCode(0);
		}
	}
	
	public int currentPlan(Calendar time) { //해당시간에 하는 일 반환
		int i;
		boolean timeCheck1=false;
		boolean timeCheck2=false;
		
		for(i=0; timeCheck1==false || timeCheck2==false; i++) {
			timeCheck1 = time.after(this.schedule[i].getStartTime());
			timeCheck2 = time.before(this.schedule[i].getEndTime());
		}
		
		return this.schedule[i].getActivityCode();
	}
}