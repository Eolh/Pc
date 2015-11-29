package model;

public class setSeatInfo {

	private String id;
	private String password;
	private int seat;

	public setSeatInfo() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}
	public String getinfo(){
		String msg = getId()+" ,"+getPassword()+"," +getSeat();
		return msg;
	}
	
}
