package pageobjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import abstractcomponents.AbstractComponents;

public class PropertyPage extends AbstractComponents {
	
	WebDriver driver;
	
	public PropertyPage (WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="h1")
	private WebElement propertyNameHeader;
	
	@FindBy(css="a.property_add_to_wsl")
	private WebElement saveToWishListBtn;
	
	@FindBy(css="div.property_unit_cont")
	private List<WebElement> propertyUnits;
	
	@FindBy(xpath="(//*[@name='fname'])[1]")
	private WebElement firstNameField;
	
	@FindBy(id="cform_fname_errorloc")
	private WebElement firstNameErrorMsg;
	
	@FindBy(xpath="(//*[@name='lname'])[1]")
	private WebElement lastNameField;
	
	@FindBy(id="cform_lname_errorloc")
	private WebElement lastNameErrorMsg;
	
	@FindBy(xpath="(//*[@name='email'])[1]")
	private WebElement emailField;
	
	@FindBy(id="cform_email_errorloc")
	private WebElement emailErrorMsg;
	
	@FindBy(xpath="(//*[@name='phone'])[1]")
	private WebElement phoneNumField;
	
	@FindBy(xpath="(//*[@name='mobile'])[1]")
	private WebElement mobileNumField;
	
	@FindBy(xpath="(//*[@name='acm_unit'])[1]")
	private WebElement selectUnitDropdown;
	
	@FindBy(id="cform_acm_unit_errorloc")
	private WebElement selectUnitErrorMsg;
	
	@FindBy(id="arrival")
	private WebElement arrivalDate;
	
	@FindBy(id="cform_arrival_errorloc")
	private WebElement arrivalDateErrorMsg;
	
	@FindBy(xpath="(//th[@class='month'])[1]")
	private WebElement arrivalMonthHeader;
	
	@FindBy(id="departure")
	private WebElement departureDate;
	
	@FindBy(id="cform_departure_errorloc")
	private WebElement departureDateErrorMsg;
	
	@FindBy(xpath="(//th[@class='month'])[3]")
	private WebElement departureMonthHeader;
	
	@FindBy(xpath="(//div[@class='calendar-table'])[9]")
	private WebElement arrivalCalendar;
	
	@FindBy(xpath="(//div[@class='calendar-table'])[13]")
	private WebElement departureCalendar;
	
	@FindBy(xpath="(//*[@name='adults'])[1]")
	private WebElement adultsDropdown;
	
	@FindBy(id="cform_adults_errorloc")
	private WebElement adultsNumErrorMsg;
	
	@FindBy(xpath="(//*[@name='child_12'])[1]")
	private WebElement childrenUnder6Dropdown;
	
	@FindBy(xpath="(//*[@name='child_18'])[1]")
	private WebElement childrenUnder12Dropdown;
	
	@FindBy(xpath="(//*[@name='inquiry'])[1]")
	private WebElement inquiryTextField;
	
	@FindBy(id="cform_inquiry_errorloc")
	private WebElement inquiryTextErrorMsg;
	
	@FindBy(css="button.property_unit_book_btn")
	private WebElement bookBtn;
	
		
	private By unitType = By.cssSelector("div.property_unit_det_info div span");
	
	private By sendInquiryBtn = By.cssSelector("a.property_unit_book_btn");
	
	private By nextMonth = By.cssSelector(".next i");
	
	public boolean verifyPropertyPageOpened(String propertyName)
	{
		boolean pageOpened = false;

		if (propertyNameHeader.getText().contains(propertyName))
		{
			pageOpened = true;
		}
		
		return pageOpened;
	}
	
	public void addOrRemoveWishList()
	{
	    saveToWishListBtn.click();
	}
	
	public void selectUnitForInquiry (String unit)
	{
		for (WebElement propertyUnit : propertyUnits)
		{
			if (propertyUnit.findElement(unitType).getText().equalsIgnoreCase(unit))
			{
				propertyUnit.findElement(sendInquiryBtn).click();
				break;
			}
		}
	}
	
	public void enterFirstName (String firstName)
	{
		firstNameField.sendKeys(firstName);
	}
	
	public String getFirstNameErrorMsg()
	{
		return firstNameErrorMsg.getText();
	}
	
	public void enterLastName (String lastName)
	{
		lastNameField.sendKeys(lastName);
	}
	
	public String getLastNameErrorMsg()
	{
		return lastNameErrorMsg.getText();
	}
	
	public void enterEmail (String email)
	{
		emailField.sendKeys(email);
	}
	
	public String getEmailErrorMsg()
	{
		return emailErrorMsg.getText();
	}
	
	public void enterPhoneNum (String phone)
	{
		phoneNumField.sendKeys(phone);
	}
	
	public void enterMobileNum (String mobile)
	{
		mobileNumField.sendKeys(mobile);
	}
	
	public void selectUnitFromDropdown(String unit)
	{
		Select unitDropdown = new Select(selectUnitDropdown);
		unitDropdown.selectByValue(unit);
	}
	
	public String getSelectUnitErrorMsg()
	{
		return selectUnitErrorMsg.getText();
	}
	
	public boolean verifyUnitSelected(String ID) throws InterruptedException
	{
		//Thread.sleep(1000);
		boolean correctUnit = false;
		if (selectUnitDropdown.getAttribute("value").contains(ID))
		{
			correctUnit = true;
		}
		
		return correctUnit;
	}
	
	public void openArrivalCalendar() throws InterruptedException
	{
		//Thread.sleep(1000);
		arrivalDate.click();
	}
	
	public void selectArrivalDate(String date, String monthYear) throws InterruptedException
	{
		openArrivalCalendar();
		while(!arrivalMonthHeader.getText().equalsIgnoreCase(monthYear)) {
			arrivalCalendar.findElement(nextMonth).click();
		}
		
		
		List<WebElement> dates = arrivalCalendar.findElements(By.cssSelector("td.available"));
		
		for (WebElement selectedDate : dates)
		{
			if (selectedDate.getText().equalsIgnoreCase(date))
			{
				selectedDate.click();
				break;
			}
		}
	}
	
	public void closeCalendar()
	{
		phoneNumField.click();
	}
	
	public String getArrivalDateErrorMsg()
	{
		return arrivalDateErrorMsg.getText();
	}
	
	public boolean verifyArrivalCalendarOpened() throws InterruptedException
	{
		//Thread.sleep(1000);
		departureDate.click();
		boolean arrivalCalOpen = arrivalCalendar.isDisplayed();
		closeCalendar();
		return arrivalCalOpen;
	}
	
	public void selectDepartureDate(String date, String monthYear)
	{
		
		while(!departureMonthHeader.getText().equalsIgnoreCase(monthYear)) {
			departureCalendar.findElement(nextMonth).click();
		}
		
		
		List<WebElement> dates = departureCalendar.findElements(By.cssSelector("td.available"));
		
		for (WebElement selectedDate : dates)
		{
			if (selectedDate.getText().equalsIgnoreCase(date))
			{
				selectedDate.click();
				break;
			}
		}
	}
	
	public String getDepartureDateErrorMsg()
	{
		return departureDateErrorMsg.getText();
	}
	
	public void selectNumAdults(String adults)
	{
		adultsDropdown.click();
		Select adultsDD = new Select(adultsDropdown);
		adultsDD.selectByValue(adults);
	}
	
	public String getAdultsNumErrorMsg()
	{
		return adultsNumErrorMsg.getText();
	}
	
	public void selectChildrenUnder6(String children_6)
	{
		Select unitDropdown = new Select(childrenUnder6Dropdown);
		unitDropdown.selectByValue(children_6);
	}
	
	public void selectChildrenUnder12(String children_12)
	{
		Select unitDropdown = new Select(childrenUnder12Dropdown);
		unitDropdown.selectByValue(children_12);
	}
	
	public void enterInquiryText(String inquiryText)
	{
		inquiryTextField.sendKeys(inquiryText);
	}
	
	public String getInquiryTextErrorMsg()
	{
		return inquiryTextErrorMsg.getText();
	}
	
	public boolean sendInquiry()
	{
		//The booking button won't be clicked because the page being tested is up and running.
		//This will allow for testing the Send Inquiries form without actually sending inquiries.
		
		boolean bookingEnabled = false;
		
		if (bookBtn.isEnabled())
		{
			bookingEnabled = true;
		}
		
		return bookingEnabled;
	}
	
	public void clickSendInquiryBtn()
	{
		//This method is called only when validating error messages
		
		Actions a = new Actions (driver);
		a.moveToElement(bookBtn).click().build().perform();
	}
	

}
