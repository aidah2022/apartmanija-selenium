package Selenium.CroatiaDirect;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageobjects.PropertyPage;
import pageobjects.SearchResults;
import pageobjects.WishListPage;
import testcomponents.BaseTest;
public class WishlistTest extends BaseTest {
	
	@Test(enabled = true, retryAnalyzer= testcomponents.Retry.class)
	public void addPropertyToWishList() throws InterruptedException
	{
		landingPage.inputDestination("Cres", "Cres (cijeli otok)");
		SearchResults searchResults = landingPage.search();
		PropertyPage propertyPage = searchResults.selectProperty("Apartmani Pino");
		propertyPage.addOrRemoveWishList();
		WishListPage wishListPage = propertyPage.openWishlist();
		Assert.assertTrue(wishListPage.verifyPropertyWishlisted("Apartmani Pino"));		
	}
	
	@Test(enabled = true)
	public void removePropertyFromWishListUsingProfilePage() throws InterruptedException
	{
		landingPage.inputDestination("Cres", "Cres (cijeli otok)");
		SearchResults searchResults = landingPage.search();
		PropertyPage propertyPage = searchResults.selectProperty("Apartmani Pino");
		propertyPage.addOrRemoveWishList();
		WishListPage wishListPage = propertyPage.openWishlist();
		Assert.assertTrue(wishListPage.verifyPropertyWishlisted("Apartmani Pino"));
		wishListPage.navigateBack();
		propertyPage.addOrRemoveWishList();
		propertyPage.openWishlist();
		Assert.assertFalse(wishListPage.verifyPropertyWishlisted("Apartmani Pino"));
		//Test fails because of a bug that causes the property to remain on the wish list page.
		//It can only be removed on the wishlist page, by clicking the heart icon twice.   
	}
	
	@Test(enabled = true)
	public void removePropertyFromWishList() throws InterruptedException
	{
		landingPage.inputDestination("Cres", "Cres (cijeli otok)");
		SearchResults searchResults = landingPage.search();
		PropertyPage propertyPage = searchResults.selectProperty("Apartmani Pino");
		propertyPage.addOrRemoveWishList();
		WishListPage wishListPage = propertyPage.openWishlist();
		Assert.assertTrue(wishListPage.verifyPropertyWishlisted("Apartmani Pino"));
		wishListPage.removeProperty("Apartmani Pino");
		Assert.assertFalse(wishListPage.verifyPropertyWishlisted("Apartmani Pino"));  
	}
	
	@Test(enabled = true, retryAnalyzer= testcomponents.Retry.class)
	public void removeAllPropertiesFromWishList() throws InterruptedException
	{			
			landingPage.inputDestination("Šibenik");
			SearchResults searchResults = landingPage.search();
			PropertyPage propertyPage = searchResults.selectProperty("Apartmani Stela");
			propertyPage.addOrRemoveWishList();
			propertyPage.switchToWindow("parent");
			searchResults.inputDestination("Rovinj");
			searchResults.search();
			searchResults.selectProperty("Apartmani Ana");
			propertyPage.addOrRemoveWishList();
			WishListPage wishListPage = propertyPage.openWishlist();
			Assert.assertTrue(wishListPage.verifyPropertyWishlisted("Apartmani Stela"));
			Assert.assertTrue(wishListPage.verifyPropertyWishlisted("Apartmani Ana"));
			wishListPage.removeAllProperties();
			Assert.assertTrue(wishListPage.verifyAllPropertiesRemoved());
	}
	
}
	