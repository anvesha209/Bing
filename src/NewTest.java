import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class NewTest 
{
	String path;
	@BeforeTest
	public void before_test() throws Exception
	{
		path = BingTesting.bingTest();
		TestApi.testingByApi(path);
	}
	
	@Test
	public void f() throws IOException 
	{
		File file_code = new File("/home/vikashkumar/Documents/Vikash Kumar/Projects/BingTesting - 21 June 2016/Translated by UI.txt");
	    FileInputStream f1=new FileInputStream(file_code);		
		File file_code1 = new File("/home/vikashkumar/Documents/Vikash Kumar/Projects/BingTesting - 21 June 2016/Translated by API.txt");
	    FileInputStream f2=new FileInputStream(file_code1);
		while((f1.read()!=-1)&&(f2.read()!=-1))
		{
			Assert.assertTrue(f1.read()==f2.read());  
		}
		f1.close();
		f2.close();
	}
}