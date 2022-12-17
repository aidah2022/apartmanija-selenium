package testcomponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import pageobjects.LandingPage;

public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingPage;
	public String browserType;
	String url;
	
	public WebDriver initializeDriver() throws IOException
	{
		Properties prop = new Properties();
		String propFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\resources\\GlobalVariables.properties";
		FileInputStream file = new FileInputStream(propFilePath);
		prop.load(file);
		
		browserType = System.getProperty("browser") !=null ? System.getProperty("browser") : prop.getProperty("browser");
		url = prop.getProperty("url");
		
		if (browserType.contains("chrome"))
		{
			ChromeOptions options = new ChromeOptions();

			if (browserType.contains("headless"))
			{
				options.addArguments("headless", "no-sandbox", "disable-gpu", "disable-dev-shm-usage", "window-size=1440,900");
			}
			
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);
		} else
		{
			if (browserType.equalsIgnoreCase("edge"))
			{
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				driver.manage().window().setSize(new Dimension(1440, 900));
			}
		}
				
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
	public static List<HashMap<String,String>> getJsonData(String jsonFilePath) throws IOException
	{
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){});
		return data;
	}
	
	public String getScreenshotPath (String testcaseName, WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String filePath = System.getProperty("user.dir")+"//reports//" + testcaseName +  ".png";
		File destFile = new File(filePath);
		FileUtils.copyFile(source, destFile);
		
		return filePath;
	}
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException
	{
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo(url);
		return landingPage;
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown()
	{
		driver.quit();
	}
}
