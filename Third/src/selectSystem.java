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
//sql�ļ����Ѿ�������������ݣ�����delete�����¿�ʼ �����������б�������������
//������������ˣ����¾���begin,no throws �쳣 �� commit,��֮rollback��
//�������ݷ�ҳ��ѯ�ж����鷳��start=1; select * from (����������join�� limit(start-1)*1,start++;ÿ���������һ��ҳ�� 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		System.out.println("please input  city which you want to slect(chiness.ie.���� .): ");
		 s=sc.nextLine();
		 try {
		      Class.forName("com.mysql.cj.jdbc.Driver");     //����MYSQL JDBC��������   
		      System.out.println("Success loading Mysql Driver!");
		    }
		    catch (Exception e) {
		      System.out.print("Error loading Mysql Driver!");
		      e.printStackTrace();
		    }
		 try {
		       connect = DriverManager.getConnection(
		          "jdbc:mysql://localhost:3306/Weathersys","root","123456");//java�������д�������Լ��������
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
				      System.out.println("������Ϣ:");
				      System.out.println(json1);
				      System.out.println("δ����������Ԥ����");
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
	}//update��������Ҫдinsert��update�����ֱ����delete��insert�����£�ͬʱweatherid�ı仯�����Ա���Ƿ�ɹ�����
}
