import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.zip.GZIPInputStream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.sf.*;
import java.util.Scanner;

public class ApiCity{
	//想要存储哪个城市输入城市name（ie.beijing.shanghai,fuzhou）,时间原因没有写循环存储,因此想存几个运行几次
	//另，已经提前存好北京，上海。福州于库中。
	 static String name;	
     public static void main(String args[]) {
    	 Scanner sc=new Scanner(System.in);
    	 name=sc.nextLine();
    	 //input three city and the following code store the information of city;
    	 System.out.println(getCity(name));   	 
    	 Json(getCity(name));
    	 toStore(Json(getCity(name)));
	}
     public static String getCity(String cityName){
    	 String hefengUrl="https://geoapi.qweather.com/v2/city/lookup?location="+name+"&key=c686a179dfc442c08831c835057d948f";
    	 StringBuffer strBuf;   
    	 strBuf=new StringBuffer();
   	 try {
    		 URL url=new URL(hefengUrl);

    		 URLConnection conn=url.openConnection();
    		 GZIPInputStream gzipInputStream =new GZIPInputStream(conn.getInputStream());
    	     BufferedReader reader=new BufferedReader(new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8));
    	     String line=null;
    	     while((line=reader.readLine())!=null) {
    	    	 strBuf.append(line);
    	     }
    	     reader.close();
    	 }catch(MalformedURLException e) {
    		 e.printStackTrace();
    	 }catch(IOException e) {
    		 e.printStackTrace();
    	 }
    	 return strBuf.toString();
     }
     public static City Json(String s) {
    	 JSONObject json=JSONObject.parseObject(s);
    	 City c=new City();
    	 String location=json.getString("location");
    	 JSONArray j=JSONArray.parseArray(location);
    	 JSONObject jsonObject=j.getJSONObject(0);
    	 String name=jsonObject.getString("name");
    	 int id=jsonObject.getInteger("id");
    	 double wei=jsonObject.getDoubleValue("lat");
    	 double Jing=jsonObject.getDoubleValue("lon");
    	 c.setId(id);
    	 c.setLon(Jing);
    	 c.setLat(wei);
    	 c.setName(name);
    	 c.print(); 
    	 return c;
     }
     public static void toStore(City c) {
    	 try {
		      Class.forName("com.mysql.cj.jdbc.Driver");     //加载MYSQL JDBC驱动程序   
		      System.out.println("Success loading Mysql Driver!");
		    }
		    catch (Exception e) {
		      System.out.print("Error loading Mysql Driver!");
		      e.printStackTrace();
		    }
		    try {
		      Connection connect = DriverManager.getConnection(
		          "jdbc:mysql://localhost:3306/Weathersys","root","123456");//java这个空填写的是你自己设的密码
		           
		      System.out.println("Success connect Mysql server!");
		      Statement stmt = connect.createStatement();
		      String sql="insert into cities(cityid,cityname,cityWei,cityJing) values("+c.getcityid()+","+"'"+c.getName()+"'"+","+c.getLat()+","+c.getLon()+")";	 
		      int affect=stmt.executeUpdate(sql);
		      if(affect>0) {
		    	  System.out.println("插入成功 ");
		      }
		    }
		    catch (Exception e) {
		      System.out.print("get data error!");
		      e.printStackTrace();
		    }
		  }
 }
  
     

