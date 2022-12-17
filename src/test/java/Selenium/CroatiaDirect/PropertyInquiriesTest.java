package Selenium.CroatiaDirect;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageobjects.PropertyPage;
import pageobjects.SearchResults;
import testcomponents.BaseTest;

public class PropertyInquiriesTest extends BaseTest {
	
	@Test(retryAnalyzer= testcomponents.Retry.class, enabled=true)
	public void sendInquiryUsingUnitInquiryButton() throws InterruptedException
	{
		landingPage.inputDestination("Fažana");
		landingPage.selectDate("6", "ruj 2023");
		landingPage.selectDate("10", "ruj 2023");
		landingPage.enterGuestNumber("4");
		landingPage.selectAccomodationType("Apartmani");
		SearchResults searchResults = landingPage.search();
		PropertyPage propertyPage = searchResults.selectProperty("Apartmani Nada");
		Assert.assertTrue(propertyPage.verifyPropertyPageOpened("Apartmani Nada"));
		propertyPage.selectUnitForInquiry("A4+2");
		propertyPage.enterFirstName("Marko");
		propertyPage.enterLastName("Marić");
		propertyPage.enterEmail("marko_m@gmail.com");
		propertyPage.enterPhoneNum("123456789");
		propertyPage.enterMobileNum("0614455667");
		Assert.assertTrue(propertyPage.verifyUnitSelected("14045"));
		propertyPage.selectArrivalDate("6", "ruj 2023");
		propertyPage.selectDepartureDate("10", "ruj 2023");
		propertyPage.selectNumAdults("2");
		propertyPage.selectChildrenUnder12("2");
		propertyPage.enterInquiryText("This is a test");
		Assert.assertTrue(propertyPage.sendInquiry());
	}
	
	@Test(retryAnalyzer= testcomponents.Retry.class, enabled=true)
	public void sendInquiryWithoutFirstName() throws InterruptedException
	{
		landingPage.inputDestination("Fažana");
		landingPage.selectDate("6", "ruj 2023");
		landingPage.selectDate("10", "ruj 2023");
		landingPage.enterGuestNumber("4");
		landingPage.selectAccomodationType("Apartmani");
		SearchResults searchResults = landingPage.search();
		PropertyPage propertyPage = searchResults.selectProperty("Apartmani Nada");
		Assert.assertTrue(propertyPage.verifyPropertyPageOpened("Apartmani Nada"));
		propertyPage.selectUnitForInquiry("A4+2");
		propertyPage.enterLastName("Marić");
		propertyPage.enterEmail("marko_m@gmail.com");
		propertyPage.enterPhoneNum("123456789");
		propertyPage.enterMobileNum("064455667");
		Assert.assertTrue(propertyPage.verifyUnitSelected("14045"));
		propertyPage.selectArrivalDate("6", "ruj 2023");
		propertyPage.selectDepartureDate("10", "ruj 2023");
		propertyPage.selectNumAdults("2");
		propertyPage.selectChildrenUnder12("2");
		propertyPage.enterInquiryText("This is a test");
		propertyPage.clickSendInquiryBtn();
		Assert.assertEquals(propertyPage.getFirstNameErrorMsg(), "Obavezno polje");;
	}
	
	@Test(retryAnalyzer= testcomponents.Retry.class, enabled = true)
	public void sendInquiryWithoutLastName() throws InterruptedException
	{
		landingPage.inputDestination("Fažana");
		landingPage.selectDate("6", "ruj 2023");
		landingPage.selectDate("10", "ruj 2023");
		landingPage.enterGuestNumber("4");
		landingPage.selectAccomodationType("Apartmani");
		SearchResults searchResults = landingPage.search();
		PropertyPage propertyPage = searchResults.selectProperty("Apartmani Nada");
		Assert.assertTrue(propertyPage.verifyPropertyPageOpened("Apartmani Nada"));
		propertyPage.selectUnitForInquiry("A4+2");
		propertyPage.enterFirstName("Marko");
		propertyPage.enterEmail("marko_m@gmail.com");
		propertyPage.enterPhoneNum("123456789");
		propertyPage.enterMobileNum("0614455667");
		Assert.assertTrue(propertyPage.verifyUnitSelected("14045"));
		propertyPage.selectArrivalDate("6", "ruj 2023");
		propertyPage.selectDepartureDate("10", "ruj 2023");
		propertyPage.selectNumAdults("2");
		propertyPage.selectChildrenUnder12("2");
		propertyPage.enterInquiryText("This is a test");
		propertyPage.clickSendInquiryBtn();
		Assert.assertEquals(propertyPage.getLastNameErrorMsg(), "Obavezno polje");
	}
	
	@Test(retryAnalyzer= testcomponents.Retry.class, enabled = true)
	public void sendInquiryWithoutEmail() throws InterruptedException
	{
		landingPage.inputDestination("Fažana");
		landingPage.selectDate("6", "ruj 2023");
		landingPage.selectDate("10", "ruj 2023");
		landingPage.enterGuestNumber("4");
		landingPage.selectAccomodationType("Apartmani");
		SearchResults searchResults = landingPage.search();
		PropertyPage propertyPage = searchResults.selectProperty("Apartmani Nada");
		Assert.assertTrue(propertyPage.verifyPropertyPageOpened("Apartmani Nada"));
		propertyPage.selectUnitForInquiry("A4+2");
		propertyPage.enterFirstName("Marko");
		propertyPage.enterLastName("Marić");
		propertyPage.enterPhoneNum("123456789");
		propertyPage.enterMobileNum("0614455667");
		Assert.assertTrue(propertyPage.verifyUnitSelected("14045"));
		propertyPage.selectArrivalDate("6", "ruj 2023");
		propertyPage.selectDepartureDate("10", "ruj 2023");
		propertyPage.selectNumAdults("2");
		propertyPage.selectChildrenUnder12("2");
		propertyPage.enterInquiryText("This is a test");
		propertyPage.clickSendInquiryBtn();
		Assert.assertEquals(propertyPage.getEmailErrorMsg(), "Obavezno polje");
	}
	
	@Test(retryAnalyzer= testcomponents.Retry.class, enabled = true)
	public void sendInquiryWithoutSelectingUnit() throws InterruptedException
	{
		landingPage.inputDestination("Fažana");
		landingPage.selectDate("6", "ruj 2023");
		landingPage.selectDate("10", "ruj 2023");
		landingPage.enterGuestNumber("4");
		landingPage.selectAccomodationType("Apartmani");
		SearchResults searchResults = landingPage.search();
		PropertyPage propertyPage = searchResults.selectProperty("Apartmani Nada");
		Assert.assertTrue(propertyPage.verifyPropertyPageOpened("Apartmani Nada"));
		propertyPage.enterFirstName("Marko");
		propertyPage.enterLastName("Maric");
		propertyPage.enterEmail("marko_m@gmail.com");
		propertyPage.enterPhoneNum("123456789");
		propertyPage.enterMobileNum("0614455667");		
		propertyPage.openArrivalCalendar();
		Assert.assertEquals(propertyPage.getSelectUnitErrorMsg(), "Odaberite smještajnu jedinicu");
	}
	
	@Test(retryAnalyzer= testcomponents.Retry.class, enabled = true)
	public void sendInquiryWithoutArrivalDate() throws InterruptedException
	{
		landingPage.inputDestination("Fažana");
		landingPage.selectDate("6", "ruj 2023");
		landingPage.selectDate("10", "ruj 2023");
		landingPage.enterGuestNumber("4");
		landingPage.selectAccomodationType("Apartmani");
		SearchResults searchResults = landingPage.search();
		PropertyPage propertyPage = searchResults.selectProperty("Apartmani Nada");
		Assert.assertTrue(propertyPage.verifyPropertyPageOpened("Apartmani Nada"));
		propertyPage.selectUnitForInquiry("A4+2");
		propertyPage.enterFirstName("Marko");
		propertyPage.enterLastName("Maric");
		propertyPage.enterEmail("marko_m@gmail.com");
		propertyPage.enterPhoneNum("123456789");
		propertyPage.enterMobileNum("0614455667");
		Assert.assertTrue(propertyPage.verifyUnitSelected("14045"));
		Assert.assertTrue(propertyPage.verifyArrivalCalendarOpened());
		propertyPage.selectNumAdults("2");
		propertyPage.selectChildrenUnder12("2");
		propertyPage.enterInquiryText("This is a test");
		propertyPage.clickSendInquiryBtn();
		Assert.assertEquals(propertyPage.getArrivalDateErrorMsg(), "Obavezno polje");
		Assert.assertEquals(propertyPage.getDepartureDateErrorMsg(), "Obavezno polje");
	}
	
	@Test(retryAnalyzer= testcomponents.Retry.class, enabled = true)
	public void sendInquiryWithoutDepartureDate() throws InterruptedException
	{
		landingPage.inputDestination("Fažana");
		landingPage.selectDate("6", "ruj 2023");
		landingPage.selectDate("10", "ruj 2023");
		landingPage.enterGuestNumber("4");
		landingPage.selectAccomodationType("Apartmani");
		SearchResults searchResults = landingPage.search();
		PropertyPage propertyPage = searchResults.selectProperty("Apartmani Nada");
		Assert.assertTrue(propertyPage.verifyPropertyPageOpened("Apartmani Nada"));
		propertyPage.selectUnitForInquiry("A4+2");
		propertyPage.enterFirstName("Marko");
		propertyPage.enterLastName("Maric");
		propertyPage.enterEmail("marko_m@gmail.com");
		propertyPage.enterPhoneNum("123456789");
		propertyPage.enterMobileNum("0614455667");
		Assert.assertTrue(propertyPage.verifyUnitSelected("14045"));
		propertyPage.selectArrivalDate("6", "ruj 2023");
		propertyPage.closeCalendar();
		propertyPage.selectNumAdults("2");
		propertyPage.selectChildrenUnder12("2");
		propertyPage.enterInquiryText("This is a test");
		propertyPage.clickSendInquiryBtn();
		Assert.assertEquals(propertyPage.getDepartureDateErrorMsg(), "Obavezno polje");
	}
	
	@Test(retryAnalyzer= testcomponents.Retry.class, enabled = true)
	public void sendInquiryWithoutNumberOfAdults() throws InterruptedException
	{
		landingPage.inputDestination("Fažana");
		landingPage.selectDate("6", "ruj 2023");
		landingPage.selectDate("10", "ruj 2023");
		landingPage.enterGuestNumber("4");
		landingPage.selectAccomodationType("Apartmani");
		SearchResults searchResults = landingPage.search();
		PropertyPage propertyPage = searchResults.selectProperty("Apartmani Nada");
		Assert.assertTrue(propertyPage.verifyPropertyPageOpened("Apartmani Nada"));
		propertyPage.selectUnitForInquiry("A4+2");
		propertyPage.enterFirstName("Marko");
		propertyPage.enterLastName("Maric");
		propertyPage.enterEmail("marko_m@gmail.com");
		propertyPage.enterPhoneNum("123456789");
		propertyPage.enterMobileNum("0614455667");
		Assert.assertTrue(propertyPage.verifyUnitSelected("14045"));
		propertyPage.selectArrivalDate("6", "ruj 2023");
		propertyPage.selectDepartureDate("10", "ruj 2023");
		propertyPage.selectChildrenUnder12("2");
		propertyPage.enterInquiryText("This is a test");
		propertyPage.clickSendInquiryBtn();
		Assert.assertEquals(propertyPage.getAdultsNumErrorMsg(), "Obavezno polje");
	}
	
	@Test(retryAnalyzer= testcomponents.Retry.class, enabled = true)
	public void sendInquiryWithoutInquiryTextFieldEntry() throws InterruptedException
	{
		landingPage.inputDestination("Fažana");
		landingPage.selectDate("6", "ruj 2023");
		landingPage.selectDate("10", "ruj 2023");
		landingPage.enterGuestNumber("4");
		landingPage.selectAccomodationType("Apartmani");
		SearchResults searchResults = landingPage.search();
		PropertyPage propertyPage = searchResults.selectProperty("Apartmani Nada");
		Assert.assertTrue(propertyPage.verifyPropertyPageOpened("Apartmani Nada"));
		propertyPage.selectUnitForInquiry("A4+2");
		propertyPage.enterFirstName("Marko");
		propertyPage.enterLastName("Maric");
		propertyPage.enterEmail("marko_m@gmail.com");
		propertyPage.enterPhoneNum("123456789");
		propertyPage.enterMobileNum("0614455667");
		Assert.assertTrue(propertyPage.verifyUnitSelected("14045"));
		propertyPage.selectArrivalDate("6", "ruj 2023");
		propertyPage.selectDepartureDate("10", "ruj 2023");
		propertyPage.selectNumAdults("2");
		propertyPage.selectChildrenUnder12("2");
		propertyPage.clickSendInquiryBtn();
		Assert.assertEquals(propertyPage.getInquiryTextErrorMsg(), "Obavezno polje");
	}
	
	@Test(retryAnalyzer= testcomponents.Retry.class, enabled = true)
	public void sendInquiryUsingForm() throws InterruptedException
	{
		landingPage.inputDestination("Fažana");
		landingPage.selectDate("6", "ruj 2023");
		landingPage.selectDate("10", "ruj 2023");
		landingPage.enterGuestNumber("4");
		landingPage.selectAccomodationType("Apartmani");
		SearchResults searchResults = landingPage.search();
		PropertyPage propertyPage = searchResults.selectProperty("Apartmani Nada");
		Assert.assertTrue(propertyPage.verifyPropertyPageOpened("Apartmani Nada"));
		propertyPage.enterFirstName("Marko");
		propertyPage.enterLastName("Maric");
		propertyPage.enterEmail("marko_m@gmail.com");
		propertyPage.enterPhoneNum("123456789");
		propertyPage.enterMobileNum("0614455667");
		propertyPage.selectUnitFromDropdown("A4+2|14045");
		Assert.assertTrue(propertyPage.verifyUnitSelected("14045"));
		propertyPage.selectArrivalDate("6", "ruj 2023");
		propertyPage.selectDepartureDate("10", "ruj 2023");
		propertyPage.selectNumAdults("2");
		propertyPage.selectChildrenUnder12("2");
		propertyPage.enterInquiryText("This is a test");
		Assert.assertTrue(propertyPage.sendInquiry());
	}
	
}
