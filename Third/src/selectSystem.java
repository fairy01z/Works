import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.alibaba.fastjson.JSON;

public class selectSystem {
	static Connection connect;
	static Statement stmt;
	static ResultSet count;
	static int c=0;
	static String s;
//sql文件中已经存入三天的数据，可以delete后重新开始 ，三个表，城市表，天气表，关联表
//事物块来不及了，大致就是begin,no throws 异常 就 commit,反之rollback。
//少量数据分页查询有丢丢麻烦：start=1; select * from (三表关联语句join） limit(start-1)*1,start++;每天的天气分一个页中 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		System.out.println("please input  city which you want to slect(chiness.ie.北京 .): ");
		 s=sc.nextLine();
		 try {
		      Class.forName("com.mysql.cj.jdbc.Driver");     //加载MYSQL JDBC驱动程序   
		      System.out.println("Success loading Mysql Driver!");
		    }
		    catch (Exception e) {
		      System.out.print("Error loading Mysql Driver!");
		      e.printStackTrace();
		    }
		 try {
		       connect = DriverManager.getConnection(
		          "jdbc:mysql://localhost:3306/Weathersys","root","123456");//java这个空填写的是你自己设的密码
		      System.out.println("Success connect Mysql server!");
		      String sql2="select MAX(weatherid) from weather";
		      stmt = connect.createStatement();
		      count = stmt.executeQuery(sql2);
		      if(count.next()) c=count.getInt(1);
		      toUpdate();
		      toCon();
		      connect.close();
         }catch (Exception e) {
		      System.out.print("get data error!");
		      e.printStackTrace();
		    }
	}
	public static  void toCon() throws SQLException {
		      stmt = connect.createStatement();
		      ResultSet rs = stmt.executeQuery("select * from (cities join stores on cities.cityid=stores.cityID) join weather on stores.weatherID=weather.weatherid where cities.cityname ='"+s+"'");
		      int id=0;
		      while (rs.next()) {
		    	      id++;
				      Weather w =new Weather();
				      City c=new City();
				      w.setFxdate(rs.getString("fxDate"));
				      w.setTempMax(Integer.parseInt((rs.getString("tempMax"))));
				      w.setTempMin(Integer.parseInt(rs.getString("tempMin")));
				      w.setTextDay(rs.getString("textDay"));
				      w.setID(rs.getInt("weatherid"));
				      if(id==1) {
				      c.setId(Integer.parseInt(rs.getString("cityid")));
				      c.setName(rs.getString("cityname"));
				      c.setLat(rs.getDouble("cityWei"));
				      c.setLon(rs.getDouble("cityJing"));
				      String json1=JSON.toJSONString(c);
				      System.out.println("城市信息:");
				      System.out.println(json1);
				      System.out.println("未来三天天气预报：");
				      }
				      String json=JSON.toJSONString(w);
				      System.out.println(json);			      	      
		      }
		      
		    }
	public static void toUpdate() throws SQLException{
	 stmt = connect.createStatement();
	 ResultSet rs1=stmt.executeQuery("select * from cities where cityname='"+s+"';");
	 int id=0;
	 while(rs1.next()) {
		  id=rs1.getInt("cityid");
	 }
	 ApiWeather a=new ApiWeather();
	 String s1=a.getString(id);
	 a.setid(c);
	 a.json(s1);
	 a.toStore(a.getWeather(), id,connect);	 
	}//update语句更新需要写insert和update，因此直接用delete和insert来更新，同时weatherid的变化，可以辨别是否成功覆盖
}
