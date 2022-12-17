package Selenium.CroatiaDirect;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.SearchResults;
import testcomponents.BaseTest;

public class SortingSearchResultsTest extends BaseTest{
	
	@Test(enabled=true, dataProvider="resultSortData", retryAnalyzer=testcomponents.Retry.class)
	public void checkResultsAreSorted(String destination, String sortBy)
	{
		landingPage.inputDestination(destination);
		landingPage.selectDate("15", "kol 2023");
		landingPage.selectDate("19", "kol 2023");
		landingPage.enterGuestNumber("2");
		landingPage.selectAccomodationType("Apartmani");
		SearchResults searchResults = landingPage.search();
		searchResults.sortBy(sortBy);
		Assert.assertTrue(searchResults.checkIfSortedBy(sortBy));
	}
	
	@DataProvider
	public String[][] resultSortData()
	{
		String[][] sortData = {{"Zagreb", "niže"}, {"Zagreb", "više"}, {"Zagreb", "Ocjeni"}, {"Zagreb", "Broju"},
				{"Pula", "niže"}, {"Pula", "više"}, {"Pula", "Ocjeni"}, {"Pula", "Broju"}};
		return sortData;
	}

}
