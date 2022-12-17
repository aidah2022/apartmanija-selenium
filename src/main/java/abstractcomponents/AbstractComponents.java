package abstractcomponents;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageobjects.SearchResults;
import pageobjects.WishListPage;

public class AbstractComponents {
	
	WebDriver driver;
	WebDriverWait wait;
	
	public AbstractComponents (WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
	}
	
	
	@FindBy(css="div.navbar-right a[href*='moji-apartmani']")
	private WebElement wishlist;
	
	@FindBy(css="div.navbar-right div:nth-child(2) span")
	private WebElement accomodationTypeMenu;
	
	@FindBy(css="div.navbar-right div:nth-child(2) ul a")
	private List<WebElement> accomMenuOptions;
	
	@FindBy(css="div.navbar-right div:nth-child(3) span")
	private WebElement countryMenu;
	
	@FindBy(css="div.navbar-right div:nth-child(3) ul img")
	private List<WebElement> countryOptions;
	
	@FindBy(css="div.navbar-right div:nth-child(4) button span")
	private WebElement currencyMenu;
	
	@FindBy(css="div.navbar-right div:nth-child(4) button")
	private WebElement currencyField;
	
	@FindBy(css="div.navbar-right div:nth-child(4) ul a")
	private List<WebElement> currencyOptions;
	
	@FindBy(id="search_form")
	private WebElement searchBar;
	
	@FindBy(id="dsel")
	private WebElement destinationDropdown;
	
	@FindBy(css="li[onclick*='showCityList']")
	private List<WebElement> regions;
	
	@FindBy(css="#city_list_cont li")
	private List<WebElement> cities;
	
	@FindBy(id="inp_dest")
	private WebElement destinationInput;
	
	@FindBy(css="#ui-id-1 strong")
	private List<WebElement> destinationAutosuggest;
	
	@FindBy(id="search_arrival")
	private WebElement arrivalDate;
	
	@FindBy(id="search_departure")
	private WebElement departureDate;
	
	@FindBy(xpath="(//th[@class='month'])[1]")
	private WebElement monthHeader1;
	
	@FindBy(xpath="(//th[@class='month'])[2]")
	private WebElement monthHeader2;
	
	@FindBy(xpath="//div[@class='calendar-table']")
	private List<WebElement> calendarTables;
	
	@FindBy(css=".next i")
	private WebElement nextMonth;
	
	@FindBy (css="button[class*='persons']")
	private WebElement guestsDropdown;
	
	@FindBy (css="#dd_ppl a")
	private List <WebElement> guestsOptions;
	
	@FindBy(css="button[class*='src_go'] i")
	private WebElement searchBtn;
	
	private By searchBarLocator = By.id("search_form");
	
	private By accomTypeDropdown = By.cssSelector("[class*='select_type']");
	
	
	public WishListPage openWishlist() throws InterruptedException
	{
		wishlist.click();
		//Thread.sleep(1000);
		return new WishListPage(driver);
	}
	
	public void openAccomTypePage(String accomType)
	{
		accomodationTypeMenu.click();
		for (WebElement type : accomMenuOptions)
		{
			if (type.getText().contains(accomType))
			{
				type.click();
				break;
			}
		}
	}
	
	public void selectCountryPage (String countryURL) throws InterruptedException
	{
		countryMenu.click();
		WebElement option = countryOptions.stream().filter(country->country.getAttribute("src").contains(countryURL)).findFirst().orElse(null);
		option.click();
		//Thread.sleep(2000);
	}
	
	public void selectCurrency (String currency)
	{
		currencyMenu.click();
		WebElement currencyOp = currencyOptions.stream().filter(option->option.getText().contains(currency)).findFirst().orElse(null);
		currencyOp.click();
	}
	
	public String getCurrencySelected()
	{
		String currency = currencyField.getText();
		return currency;
	}
	
	public boolean searchBarPresent()
	{
		boolean elementPresent = driver.findElements(searchBarLocator).size()!=0;
		return elementPresent;
	}
	
	public boolean accomodationDropdownPresent()
	{
		boolean elementPresent = driver.findElements(accomTypeDropdown).size()!=0;
		return elementPresent;
	}
	
	public void selectRegionAndCity(String region, String city)
	{
		destinationDropdown.click();
		WebElement selectedRegion = regions.stream().filter(selRegion->selRegion.getText().equalsIgnoreCase(region)).findFirst().orElse(null);
		selectedRegion.click();
		waitForVisibilityOfList(cities);
		WebElement selectedCity = cities.stream().filter(selCity->selCity.getText().equalsIgnoreCase(city)).findFirst().orElse(null);
		selectedCity.click();
	}
	
	public void inputDestination(String destination)
	{
		destinationInput.click();
		destinationInput.sendKeys(destination);
		WebElement selectedDestination = destinationAutosuggest.stream().filter(dest->dest.getText().equalsIgnoreCase(destination)).findFirst().orElse(null);
		
		selectedDestination.click();		
	}
	
	public void inputDestination (String destination, String destinationToSelect)
	{
		destinationInput.click();
		destinationInput.sendKeys(destination);
		WebElement selectedDestination = destinationAutosuggest.stream().filter(dest->dest.getText().equalsIgnoreCase(destinationToSelect)).findFirst().orElse(null);
		selectedDestination.click();
	}
	
	public void openCalendar()
	{
		arrivalDate.click();
	}
	
	public void selectDate(String date, String monthYear)
	{
		WebElement calendar = null;
		
		if (!monthHeader1.getText().equalsIgnoreCase(monthYear))
		{
			if(!monthHeader2.getText().equalsIgnoreCase(monthYear))
				while(!monthHeader2.getText().equalsIgnoreCase(monthYear))
					nextMonth.click();
					calendar = calendarTables.get(1);
		} else {
			calendar = calendarTables.get(0);
		}
		
		List<WebElement> dates = calendar.findElements(By.tagName("td"));
		
		for (WebElement selectedDate : dates)
		{
			if (selectedDate.getText().equalsIgnoreCase(date))
			{
				selectedDate.click();
				break;
			}
		}
	}
	
	public String checkArrivalDateValue()
	{
		return arrivalDate.getAttribute("value");
	}
	
	public void openGuestsDropdown()
	{
		guestsDropdown.click();
	}
	
	public void enterGuestNumber (String guests)
	{
		WebElement guestNumber = guestsOptions.stream().filter(guestsOp->guestsOp.getText().equals(guests)).findFirst().orElse(null);
		guestNumber.click();		
	}
	
	public SearchResults search() {
		searchBtn.click();
		return new SearchResults(driver);
	}
	
	public void waitForVisibilityOfList (List<WebElement> list)
	{
		wait.until(ExpectedConditions.visibilityOfAllElements(list));
	}
	
	public void switchToWindow(String window)
	{
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> it = windows.iterator();
		String parent = it.next();
		String child = it.next();
		
		while(it.hasNext())
		{
			child = it.next();
		}
		
			
		if (window.equalsIgnoreCase("parent"))
		{
			driver.switchTo().window(parent);
		}
		if (window.equalsIgnoreCase("child"))
		{		
			driver.switchTo().window(child);
		}
	}
	
	public void waitForPage(String pageUrl)
	{
		wait.until(ExpectedConditions.urlContains(pageUrl));
	}
	
	public void navigateBack()
	{
		driver.navigate().back();
	}
	
	public String getCurrentPageURL() throws InterruptedException
	{
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		switchToWindow("child");
		String currentURL =  driver.getCurrentUrl();

		return currentURL;
	}
	
	public void waitForVisibilityOfElement (WebElement element)
	{
		wait.until(ExpectedConditions.visibilityOf(element));
	}
}
