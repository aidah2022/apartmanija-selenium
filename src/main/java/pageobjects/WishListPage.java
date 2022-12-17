package pageobjects;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abstractcomponents.AbstractComponents;

public class WishListPage extends AbstractComponents {
	
	WebDriver driver;
	
	public WishListPage (WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="div.ads_results_ttl i.fa-th")
	private WebElement numOfProperties;
	
	@FindBy(css=".ads_cont.__top")
	private WebElement propertiesDiv;
	
	@FindBy(css="div.ads_cont.__top div.prop_wrap")
	private List<WebElement> propertyFields;
	
	@FindBy(css="div.search_noresults_msg")
	private WebElement noPropertiesMsg;
	
	@FindBy(css="a.__removeAllFromWishlist")
	private WebElement removeAllBtn;
	
	private By propertyName = By.cssSelector("p.prop_name");
	
	private By removeProperty = By.cssSelector("div.prop_footer_wsl span");
	
	public boolean verifyPropertyWishlisted(String propName) throws InterruptedException
	{
		//Thread.sleep(1000);
		boolean onList = false;
		try
		{
			Actions a = new Actions (driver);
			a.moveToElement(propertiesDiv).build().perform();
				
			for (WebElement property : propertyFields)
			{
				String name = property.findElement(propertyName).getText();
				if(name.equalsIgnoreCase(propName))
				{
					onList = true;
					break;
				}
			}
		} 
		catch (NoSuchElementException e)
		{
			
		}
		
		return onList;
	}
	
	public List<String> getWishlistedProperties()
	{
		List<String> properties = new ArrayList<String>();
		for(int i=0; i<properties.size(); i++)
		{
			properties.add(propertyFields.get(i).findElement(propertyName).getText());
		}
		
		return properties;
	}
	
	public void removeProperty(String propName)
	{		
		for (WebElement property : propertyFields)
		{
			if(property.findElement(propertyName).getText().equalsIgnoreCase(propName))
			{
				property.findElement(removeProperty).click();
				break;
			}
		}
	}
	
	public void removeAllProperties()
	{		
		removeAllBtn.click();
	}
	
	public boolean verifyAllPropertiesRemoved()
	{
		return noPropertiesMsg.getText().equalsIgnoreCase("Lista odabranih smještaja je prazna");
	}

}
