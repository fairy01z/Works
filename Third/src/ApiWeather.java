import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.GZIPInputStream;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class ApiWeather {
	static Collection weather=new ArrayList();
	static int weatherid;
	static Statement stmt;
	static ResultSet count;
	static ResultSet isEmpty;
	public Collection getWeather() {
		return weather;
	}
	public void setid(int id) {
		weatherid=id;
	}
	public  String getString(int id){
   	 String hefengUrl="https://devapi.qweather.com/v7/weather/3d?location="+id+"&key=c686a179dfc442c08831c835057d948f";
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
	public  void json(String s) {
		 JSONObject json=JSONObject.parseObject(s);		
		 String daily=json.getString("daily");
    	 JSONArray j=JSONArray.parseArray(daily);
    	 int id=0;
    	 for(int i=0;i<3;i++) {
    		id++;
    	 Weather w=new Weather();
    	 JSONObject jsonObject=j.getJSONObject(i);
    	 String fxdate=jsonObject.getString("fxDate");
    	 int tempMin=jsonObject.getIntValue("tempMin");
    	 int tempMax=jsonObject.getIntValue("tempMax");
    	 String textDay=jsonObject.getString("textDay");
    	 w.setID(id);//暂时集合中天气的id,与表中的天气id无关
    	 w.setFxdate(fxdate);
    	 w.setTempMax(tempMax);
    	 w.setTempMin(tempMin);
    	 w.setTextDay(textDay);
    	 weather.add(w);
    	 }
	}
	public  void toStore(Collection weather,int cityid,Connection connect) throws SQLException{     
		      Object[] obj=weather.toArray();
		      stmt = connect.createStatement();
		      isEmpty=stmt.executeQuery("select count(*) from stores where cityID="+cityid);
		      int x=0;
		      if(isEmpty.next()) x=isEmpty.getInt(1);
		      ResultSet row;
		      if(x!=0) { row=stmt.executeQuery("select weatherID from stores where cityID="+cityid+" order by weatherID asc");
		               int[] arr=new int[x];
		               int i=-1;
		               while(row.next()) {
		            	  i++;
		            	  arr[i]=row.getInt("weatherID");		            	  
		               }
		               stmt.executeUpdate("delete from stores where weatherID between "+arr[0]+" and "+arr[arr.length-1]);
		               stmt.executeUpdate("delete from weather where weatherID between "+arr[0]+" and "+arr[arr.length-1]);
		      }
				for(int i=0;i<obj.length;i++) {
					weatherid++;
				Weather w=(Weather)obj[i];
		      String sql="insert into weather(weatherid,fxDate,tempMax,tempMin,textDay) values("+weatherid+","+"'"+w.getfxDate()+"'"+","+w.getTempMax()+","+w.getTempMin()+","+"'"+w.getTextDay()+"'"+")";	 		      
		      String sql1="insert into stores(cityID,weatherID) values("+cityid+","+weatherid+")";
		      int affect=stmt.executeUpdate(sql);
		      int affect1=stmt.executeUpdate(sql1);
		      if(affect>0 && affect1>0) {
		    	  System.out.println("插入/覆盖成功 ");
		      }
				}		    
		}
		  }

