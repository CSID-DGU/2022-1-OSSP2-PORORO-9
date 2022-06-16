public class password {
	
	int cond0, cond1, cond2, cond3, cond4;
	// 0 : 비밀번호는 최소 8자리 이상
	// 1 : 적어도 한개 이상의 대문자
	// 2 : 적어도 한개 이상의 소문자
	// 3 : 적어도 한개 이상의 특수문자
	// 4 : 적어도 한개 이상의 숫자
	
	private String userPassword;
	private int error;
	
	public password(String password) {
		this.userPassword = password;
		error = 0;
	}
	
	public int getError() {
		return error;
	}
	
	public void setError(int e) {
		error = e;
	}
	
	public String getUserPW() {
		return userPassword;
	}
	
	public void checkCond(int conditionCase) {
		int i = 0;
		int length = userPassword.length();
		
		if (conditionCase == 0) {
			if (length >= 8)
				return;
			else
				setError(1);
				return;
		}
		else if (conditionCase == 1) {
			for (i = 0; i < length; i++) {
				if (Character.isUpperCase(userPassword.charAt(i)) == true)
					return;
			}
			if (i == length)
				setError(1);
				return;
		}
		else if (conditionCase == 2) {
			for (i = 0; i < length; i++) {
				if (Character.isLowerCase(userPassword.charAt(i)) == true)
					return;
			}
			if (i == length)
				setError(1);
				return;
		}
		else if (conditionCase == 3) {
			for (i = 0; i < length; i++) {
				if (String.valueOf(userPassword.charAt(i)).matches("[^a-zA-Z0-9\\s]"))
					return;
			}
			if (i==length)
				setError(1);
				return;
		}
		else {
			for (i = 0; i < length; i++) {
				if (Character.isDigit(userPassword.charAt(i)) == true)
					return;
			}
			if (i == length)
				setError(1);
				return;
		}
	}
	
	public int check() {
		checkCond(0);
		checkCond(1);
		checkCond(2);
		checkCond(3);
		checkCond(4);
		
		return getError();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		password User = new password("Jang0505!");

		if (User.check() == 0)
			System.out.println("Valid");
		else
			System.out.println("Non-valid, please change");
	}

}
