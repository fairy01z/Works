
public class City {
	private int cityid;
	private String name;
	private double lat;
	private double lon;
	
	public City() {

	}
	public int getcityid() {
		return cityid;
	}
	public void setId(int id) {
		cityid=id;
	}
	public String getName() {
		return name;
	}
	public void setName(String n) {
		name=n;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double d) {
		lat=d;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double d) {
		lon=d;
	}
	public void print() {
		String details= "id :"+cityid;
		       details+="\nname :"+name;
		       details+="\nlat :"+lat;
		       details+="\nlon :"+lon;
		System.out.println(details);
	}
}
