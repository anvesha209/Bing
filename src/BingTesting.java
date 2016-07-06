import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

class BingTesting
{
	static String path = null;
    public static String bingTest() throws Exception
    {
        WebDriver driver = new FirefoxDriver();
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        Component parent = null;
		int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) 
        {
            File selectedFile = fileChooser.getSelectedFile();
            path=selectedFile.getAbsolutePath();
        }
		path = path.substring(18,path.length());
        
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
        
        File file = new File("Translated by UI.txt");
        file.createNewFile();
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.close();
        
        File file1 = new File("Translated by Translation Jar.txt");
        file1.createNewFile();
        PrintWriter writer1 = new PrintWriter(file1);
        writer1.print("");
        writer1.close();
        
        int totalline = i-2;
        
        for(i=0 ; i < totalline ; i++)
        {
        	driver.get("https://www.bing.com/translator");
            
            String[] part = strng[i].split(",");
        	
            //BY UI
        	driver.findElement(By.id("srcText")).sendKeys(part[2]);
        	Thread.sleep(1000);
        	
           	List<WebElement> button=driver.findElements(By.className("LS_Header"));
			List<WebElement> language=driver.findElements(By.className("LanguageList"));
			List<WebElement> selectsource=language.get(0).findElements(By.className("LS_Item"));
			List<WebElement> selectdestination=language.get(1).findElements(By.className("LS_Item"));
	
			button.get(0).click();
			for(int j=0; j<54; j++)
			{
				String str=selectsource.get(j).getText();
				if(str.equals(part[0]))
				{
					selectsource.get(j).click();
					break;
				}
			}
	
			button.get(1).click();
			for(int j=0; j<53; j++)
			{
				String str1=selectdestination.get(j).getText();
				if(str1.equals(part[1]))
				{
					selectdestination.get(j).click();
					break;
				}
			}
			
			driver.findElement(By.id("TranslateButton")).click();

        	Thread.sleep(1000);
            String data1 = driver.findElement(By.id("destText")).getText();
            
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append(data1+"\n");
			bw.close();
			
			//BY TRANSLATION JAR
			Translate.setClientId("e8061be2-57bf-47ab-b78a-f4763a8a6285");
			Translate.setClientSecret("O+kxgHLN72c/TqRxuSXMhodYqbvCr2mtdlGasu5+q/M");
			
			String translatedText = Translate.execute( part[2], Language.valueOf(part[0].toUpperCase()), Language.valueOf(part[1].toUpperCase()));
			
			FileWriter fw1 = new FileWriter(file1, true);
			BufferedWriter bw1 = new BufferedWriter(fw1);
			bw1.append(translatedText+"\n");
			bw1.close();
        }
        driver.close();
        return path;
    }
}



