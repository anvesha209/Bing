import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class TestApi 
{
	public static void testingByApi(String path) throws IOException, JSONException
	{
		File file2 = new File("Translated by API.txt");
	    file2.createNewFile();
	    PrintWriter writer2 = new PrintWriter(file2);
	    writer2.print("");
	    writer2.close();
	    	      
	    String[] strng = new String[10];
	    Arrays.fill(strng, "");
	    BufferedReader br = null;
	    String sCurrentLine;
	    int i=0;
	    File fileToRead = new File(System.getProperty("user.home"));
	    fileToRead = new File(fileToRead, path);
	    br = new BufferedReader(new FileReader(fileToRead));
	    while ((sCurrentLine = br.readLine()) != null) 
	    {
	    	if(i!=0 && i!=1)
	    	{
	    		strng[i-2] = sCurrentLine;
	        }
	        i++;
	    }
	    br.close();
	    int totalline = i-2;
	      
		URL url1 = new URL("https://datamarket.accesscontrol.windows.net/v2/OAuth2-13");
		HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String id=URLEncoder.encode("shivam_14", "UTF-8");
		String pass=URLEncoder.encode("mfEFOhwx6kjDKyUZj6VR5rKvuJPvNZ3toSIcuqFqWFE=", "UTF-8");	
				
		String urlParameters = "grant_type=client_credentials&client_id="+id+"&client_secret="+pass+"&scope=http://api.microsofttranslator.com";
		conn.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) 
		{
			response.append(inputLine);
		}
		in.close();
		String ssss=new String(response);
		
		JSONObject obj=new JSONObject(ssss);
		String access_token=(String) obj.get("access_token");
		
		File file_code = new File("/home/vikashkumar/Documents/Vikash Kumar/Projects/BingTesting - 21 June 2016/Code.csv");
	    BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(file_code)));
	    String line_code = null;
	    HashMap map=new HashMap();
	    while( (line_code = br1.readLine())!= null )
	    {
	    	String[]ss = line_code.split(",");
	        map.put(ss[0], ss[1]);
	    }
	    br1.close();
	    
	    for( i=0;i<totalline;i++)
	  	{
	    	String[] part = strng[i].split(",");
	  		String from=(String) map.get(part[0]);
	  		String to=(String) map.get(part[1]);
	  		String text=part[2];
	  		
	  		String uri = "http://api.microsofttranslator.com/v2/Http.svc/Translate?text=" + URLEncoder.encode(text, "UTF-8") + "&from=" + from + "&to=" + to;
	  		String authToken = "Bearer" + " " + access_token;

	  		URL urll=new URL(uri);	
	  		HttpURLConnection conn1 = (HttpURLConnection) urll.openConnection();
	  		conn1.setRequestMethod("GET");
	  		conn1.setRequestProperty("Authorization",authToken);
		
	  		BufferedReader in1 = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
	  		String inputLine1;
	  		StringBuffer response1 = new StringBuffer();

	  		while ((inputLine1 = in1.readLine()) != null)
	  		{
	  			response1.append(inputLine1);
	  		}
	  		in1.close();
		
	  		String sss=response1.toString();
	  		sss = sss.substring(sss.indexOf(">") + 1);
	  		sss = sss.substring(0, sss.indexOf("<"));		
		
	  		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/home/vikashkumar/Documents/Vikash Kumar/Projects/BingTesting - 21 June 2016/Translated by API.txt", true)));
        
	  		out.println(sss);
	  		out.close();
		}
	}
}