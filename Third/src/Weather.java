
public class Weather {
	private int id;
	private String fxdate;
	private int tempMax;
	private int tempMin;
	private String textDay;
	
	public Weather() {
		
	}
	public int getId() {
		return id;
	}
	public String getfxDate() {
		return fxdate;
	}
	public String getTextDay() {
		return textDay;
	}
	public int getTempMax() {
		return tempMax;
	}
	public int getTempMin() {
		return tempMin;
	}
	public void setID(int Id) {
		id=Id;
	}
	public void setFxdate(String data) {
		fxdate=data;
	}
	public void setTextDay(String s) {
		textDay=s;
	}
	public void setTempMax(int t) {
		tempMax=t;
	}
	public void setTempMin(int t) {
		tempMin=t;
	}
	public void print() {
		String details="date :"+fxdate;
		       details+="\ntempMax :"+tempMax;
		       details+="\ntempMin :"+tempMin;
		       details+="\ntextDay :"+textDay;		       
		       System.out.println(details);
	}
}
