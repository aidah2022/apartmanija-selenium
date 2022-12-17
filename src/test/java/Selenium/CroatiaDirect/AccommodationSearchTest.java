package Selenium.CroatiaDirect;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.PropertyPage;
import pageobjects.SearchResults;
import pageobjects.WishListPage;
import testcomponents.BaseTest;

public class AccommodationSearchTest extends BaseTest{
	
	@Test(enabled = true, retryAnalyzer= testcomponents.Retry.class)
	public void verifySearchBarAndAccomodationTypeDropdown() throws InterruptedException
	{
		landingPage.inputDestination("Poreč");
		Assert.assertEquals(landingPage.getDefaultAccomType(), "Apartmani i sobe");
		SearchResults searchResults = landingPage.search();
		searchResults.inputDestination("Dubrovnik");
		Assert.assertFalse(searchResults.accomodationDropdownPresent());
		searchResults.search();
		PropertyPage propertyPage = searchResults.selectProperty("Apartmani Adriatic Star");
		propertyPage.inputDestination("Korčula", "Korčula (cijeli otok)");
		Assert.assertFalse(searchResults.accomodationDropdownPresent());
		propertyPage.search();
		WishListPage wishListPage = propertyPage.openWishlist();
		Assert.assertTrue(wishListPage.searchBarPresent());
		Assert.assertFalse(wishListPage.accomodationDropdownPresent());	
	}
	
	@Test(enabled = true, dataProvider="getDataDestDropdown")
	public void searchFromDestinationDropdown (HashMap<String,String> input)
	{
		landingPage.selectRegionAndCity(input.get("region"), input.get("city"));
		landingPage.selectDate(input.get("arrivalDate"), input.get("arrivalMonth"));
		landingPage.selectDate(input.get("departureDate"), input.get("departureMonth"));
		landingPage.enterGuestNumber(input.get("guests"));
		landingPage.selectAccomodationType(input.get("accomodationType"));
		SearchResults searchResults = landingPage.search();
		Assert.assertEquals(searchResults.getSearchHeader(), "Rezultati pretrage - " + input.get("accomodationType") + " " + input.get("city") + ", " + input.get("region"));
	}
	
	@Test(enabled = true, dataProvider="getDataCityInputField")
	public void searchFromCityInputField (HashMap<String,String> input)
	{
		landingPage.inputDestination(input.get("city"));
		landingPage.selectDate(input.get("arrivalDate"), input.get("arrivalMonth"));
		landingPage.selectDate(input.get("departureDate"), input.get("departureMonth"));
		landingPage.enterGuestNumber(input.get("guests"));
		landingPage.selectAccomodationType(input.get("accomodationType"));
		SearchResults searchResults = landingPage.search();
		Assert.assertEquals(searchResults.getSearchHeader(), "Rezultati pretrage - " + input.get("accomodationType") + " " + input.get("city") + ", " + input.get("region"));
		
	}
	
	@Test(enabled = true, retryAnalyzer= testcomponents.Retry.class)
	public void searchNoDestinationEntered()
	{
		landingPage.openCalendar();
		landingPage.selectDate("4", "lip 2023");
		landingPage.selectDate("9", "lip 2023");
		landingPage.enterGuestNumber("2");
		landingPage.selectAccomodationType("Apartmani");
		landingPage.search();
		Assert.assertEquals(landingPage.getDestinationErrorMsg(), "Izaberite odredište");
	}
	
	@Test(enabled = true, retryAnalyzer= testcomponents.Retry.class)
	public void searchNoDatesEntered()
	{
		landingPage.inputDestination("Split");
		landingPage.openGuestsDropdown();
		landingPage.enterGuestNumber("5");
		landingPage.selectAccomodationType("Vile");
		SearchResults searchResults = landingPage.search();
		Assert.assertEquals(searchResults.getSearchHeader(), "Rezultati pretrage - Vile Split, Split i okolica");		
	}
	
	@Test(enabled = true)
	public void searchNoGuestNumberEntered()
	{
		landingPage.selectRegionAndCity("Zadar i okolica", "Biograd na moru");
		landingPage.selectDate("10", "velj 2023");
		landingPage.selectDate("13", "velj 2023");
		landingPage.selectAccomodationType("Apartmani");
		SearchResults searchResults = landingPage.search();
		Assert.assertEquals(searchResults.getSearchHeader(), "Rezultati pretrage - Apartmani Biograd na moru, Zadar i okolica");
	}
	
	@Test(enabled = true, dataProvider="getAccomodationTypeData")
	public void searchOnAccomodationTypePages(String type)
	{
		String region = "Zagreb i središnja Hrvatska";
		String city = "Zagreb";
		
		landingPage.openAccomTypePage(type);
		landingPage.selectRegionAndCity(region, city);
		landingPage.selectDate("10", "tra 2023");
		landingPage.selectDate("13", "tra 2023");
		landingPage.openGuestsDropdown();
		landingPage.enterGuestNumber("2");
		SearchResults searchResults = landingPage.search();
		Assert.assertEquals(searchResults.getSearchHeader(), "Rezultati pretrage - " + type + " " + city + ", " + region);
		
	}
	
	@Test(enabled=true)
	public void pastDatesDisabled()
	{
		landingPage.inputDestination("Pula");
		landingPage.selectDate("01", "pro 2022");
		Assert.assertTrue(landingPage.checkArrivalDateValue().isBlank());
	}
	
	@Test(enabled = true)
	public void clickingDepartureDateBeforeArrival()
	{
		landingPage.selectRegionAndCity("Istra", "Rovinj");
		landingPage.selectDate("10", "lip 2023");
		Assert.assertTrue(landingPage.checkArrivalDateValue().equalsIgnoreCase("10.06.2023"));
		landingPage.selectDate("6", "lip 2023");
		Assert.assertTrue(landingPage.checkArrivalDateValue().equalsIgnoreCase("06.06.2023"));
	}
	
	@Test(enabled = true, dataProvider="destinationNumberOfResults", retryAnalyzer= testcomponents.Retry.class)
	public void checkNumberOfResultsFound(String destinationInput, String destinationSelect)
	{
		landingPage.inputDestination(destinationInput, destinationSelect);
		landingPage.selectDate("1", "lip 2023");
		landingPage.selectDate("6", "lip 2023");
		landingPage.enterGuestNumber("2");
		landingPage.selectAccomodationType("Apartmani");
		SearchResults searchResults = landingPage.search();
		Assert.assertTrue(searchResults.verifyNumberOfResults());
	}
	
	@Test(enabled = true, retryAnalyzer= testcomponents.Retry.class)
	public void noMatchingAccomodationMessageDisplayed()
	{
		landingPage.inputDestination("Zagreb");
		landingPage.selectDate("13", "tra 2023");
		landingPage.selectDate("17", "tra 2023");
		landingPage.enterGuestNumber("3");
		landingPage.selectAccomodationType("Robinzonski turizam");
		SearchResults searchResults = landingPage.search();
		Assert.assertTrue(searchResults.getNoResultsMsg().contains("Trenutno nema rezultata za odabrani kriterij pretraživanja!"));
	}
		
	@DataProvider
	public Object[][] getDataDestDropdown() throws IOException
	{
		List<HashMap<String,String>> searchData = getJsonData(System.getProperty("user.dir")+"\\src\\test\\java\\data\\SearchData.json");
		return new Object[][] {{searchData.get(0)}, {searchData.get(1)}, {searchData.get(2)}}; 
	}
	
	@DataProvider
	public Object[][] getDataCityInputField () throws IOException
	{
		List<HashMap<String,String>> data = getJsonData(System.getProperty("user.dir")+"\\src\\test\\java\\data\\SearchDataInputField.json");
		return new Object[][] {{data.get(0)},{data.get(1)},{data.get(2)}};
	}
	
	@DataProvider
	public String[] getAccomodationTypeData()
	{
		String[] accomodationTypes = {"Apartmani", "Vile", "Robinzonski turizam", "Seoski turizam"};
		return accomodationTypes;
	}
	
	@DataProvider
	public String[][] destinationNumberOfResults()
	{
		String[][] destinationData ={{"Hvar cijeli otok", "Hvar (cijeli otok)"},{"Dubrovnik", "Dubrovnik i okolica"}, {"Brač", "Brač Supetar"}};
		return destinationData;
	}
	
}
