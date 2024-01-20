package cn.hhu.aiams.entity;

public class Algorithm {
	private Integer id;
	private String name;
	private String type;
	private String state;
	private String callby;
	private String date;
	private String modloadtime;
	private String timeperpic;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCallby() {
		return callby;
	}
	public void setCallby(String callby) {
		this.callby = callby;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getModloadtime() {
		return modloadtime;
	}
	public void setModloadtime(String modloadtime) {
		this.modloadtime = modloadtime;
	}
	public String getTimeperpic() {
		return timeperpic;
	}
	public void setTimeperpic(String timeperpic) {
		this.timeperpic = timeperpic;
	}
	
}
