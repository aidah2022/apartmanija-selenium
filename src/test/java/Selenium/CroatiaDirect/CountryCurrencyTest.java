package Selenium.CroatiaDirect;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.SearchResults;
import testcomponents.BaseTest;

public class CountryCurrencyTest extends BaseTest{
	
	@Test(enabled = true, dataProvider="getCountryURL")
	public void selectCountryOnLandingPageAndVerifyCurrency(HashMap<String, String> input) throws InterruptedException
	{
		landingPage.selectCountryPage(input.get("countryLink"));
		Assert.assertEquals(landingPage.getCurrentPageURL(), input.get("url"));
		Assert.assertTrue(landingPage.getCurrencySelected().contains(input.get("currency")));
	}
	
	@Test(enabled = true, dataProvider="getCurrencyData")
	public void selectCurrencyBeforeSearch(HashMap<String, String> input)
	{
		landingPage.selectCurrency(input.get("currency"));
		landingPage.inputDestination("Umag");
		landingPage.selectDate("6", "ožu 2023");
		landingPage.selectDate("12", "ožu 2023");
		landingPage.enterGuestNumber("3");
		landingPage.selectAccomodationType("Apartmani");
		SearchResults searchResults = landingPage.search();
		Assert.assertTrue(searchResults.verifyPriceCurrency(input.get("currencySymbol")));
	}
	
	@Test(enabled = true)
	public void selectCurrencyAfterEnteringSearchCriteria ()
	{
		//Selecting currency option will delete search criteria entered by user and search will not be performed.
		
		landingPage.inputDestination("Umag");
		landingPage.selectDate("6", "ožu 2023");
		landingPage.selectDate("12", "ožu 2023");
		landingPage.enterGuestNumber("3");
		landingPage.selectAccomodationType("Apartmani");
		landingPage.selectCurrency("GBP");
		SearchResults searchResults = landingPage.search();
		Assert.assertTrue(searchResults.verifyPriceCurrency("£"));
	}
	
	@Test(enabled = true, dataProvider="getCurrencyData", retryAnalyzer=testcomponents.Retry.class)
	public void convertPricesToSelectedCurrency (HashMap<String,String> input)
	{
		landingPage.selectRegionAndCity("Split i okolica", "Gradac");
		landingPage.selectDate("15", "lip 2023");
		landingPage.selectDate("20", "lip 2023");
		landingPage.enterGuestNumber("4");
		landingPage.selectAccomodationType("Apartmani");
		SearchResults searchResults = landingPage.search();
		searchResults.selectCurrency(input.get("currency"));
		Assert.assertTrue(searchResults.verifyPriceCurrency(input.get("currencySymbol")));
	}
		
	@DataProvider
	public Object[] getCountryURL() throws IOException 
	{
			List<HashMap<String, String>> data = getJsonData(System.getProperty("user.dir")+"\\src\\test\\java\\data\\CountryPageData.json");
			Object[] countryData = new Object[13];
			for (int i=1; i<data.size(); i++)
			{
				countryData[i-1]=data.get(i);
			}
			
			return countryData;
	}
	
	@DataProvider
	public Object[] getCurrencyData() throws IOException
	{
		List<HashMap<String, String>> currencyDataList = getJsonData(System.getProperty("user.dir")+ "\\src\\test\\java\\data\\CurrencyData.json");
		Object[] currencyData = new Object [13];
		for (int i=0; i<currencyData.length; i++)
		{
			currencyData[i] = currencyDataList.get(i);
		}
		return currencyData;
	}
	
}
