package pageobjects;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abstractcomponents.AbstractComponents;

public class LandingPage extends AbstractComponents {
	
	WebDriver driver;
	
	public LandingPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy (css="[class*='select_type']")
	private WebElement accomTypeDropdown;
	
	@FindBy (css="ul[aria-labelledby='dd_cat'] a")
	private List<WebElement> accomTypeOptions;
	
	@FindBy(css="[id*='tooltip']")
	WebElement selectDestinationError;
	
	@FindBy (css="#site_nt_txt p:nth-of-type(2) span")
	private WebElement acceptCookies;
	
	public void goTo(String url)
	{
		driver.get(url);
		try
		{
			acceptCookies.click();
		} 
		catch (Exception e)
		{
			
		}
	}
	
	public String getDefaultAccomType()
	{
		return accomTypeDropdown.getText();
	}
	
	public void openAccomodationTypeDropdown()
	{
		accomTypeDropdown.click();
	}
	
	public void selectAccomodationType (String type)
	{
		openAccomodationTypeDropdown();
		for (WebElement accomType:accomTypeOptions)
		{
			if (accomType.getText().contains(type))
			{
				accomType.click();
				break;
			}
		}
	}
	
	public String getDestinationErrorMsg()
	{
		return selectDestinationError.getText();
	}	

}
