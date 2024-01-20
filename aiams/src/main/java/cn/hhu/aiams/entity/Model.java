package cn.hhu.aiams.entity;

public class Model {
	private Integer id;
	private String modelname;
	private String algname;
	private String sampleset;
	private String state;
	private String createtime;
	private String trainstarttime;
	private String trainendtime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getModelname() {
		return modelname;
	}
	public void setModelname(String modelname) {
		this.modelname = modelname;
	}
	public String getAlgname() {
		return algname;
	}
	public void setAlgname(String algname) {
		this.algname = algname;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSampleset() {
		return sampleset;
	}
	public void setSampleset(String sampleset) {
		this.sampleset = sampleset;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getTrainendtime() {
		return trainendtime;
	}
	public void setTrainendtime(String trainendtime) {
		this.trainendtime = trainendtime;
	}
	public String getTrainstarttime() {
		return trainstarttime;
	}
	public void setTrainstarttime(String trainstarttime) {
		this.trainstarttime = trainstarttime;
	}
	
}
