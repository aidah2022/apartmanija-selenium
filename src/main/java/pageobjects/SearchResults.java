package pageobjects;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abstractcomponents.AbstractComponents;

public class SearchResults extends AbstractComponents {
	
	WebDriver driver;
	
	public SearchResults(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="city_ttl")
	private WebElement cityTitle;
	
	@FindBy(css="div.ads_cont div.no_results_msg")
	private WebElement noResultsMsg;
	
	@FindBy(css=".ads_results_ttl .pull-left strong")
	private WebElement numberOfResultsFound;
	
	@FindBy(xpath="//div[@class='ads_cont __top']/div[contains(@class, 'prop_wrap')]")
	private List<WebElement> searchResultsFields;
	
	@FindBy(css=".search_filter_sort")
	private WebElement sortByBtn;
	
	@FindBy(css="[data-filter='sort'] a")
	private List<WebElement> sortOptions;
	
	@FindBy(xpath="//a[@class='ff1']/span[@class='clearfix']/following-sibling::span")
	private List<WebElement> reviewSpans;
	
	@FindBy(xpath="//a[@class='ff1']/span[2]/i[5]")
	private List<WebElement> numberOfReviews;
	
	@FindBy (css=".prop_footer_prc span:nth-child(2)")
	private List<WebElement> prices;
	
	private By propertyName = By.cssSelector("p.prop_name");
		
	
	public String getSearchHeader()
	{
		String searchHeader = cityTitle.getText();
		return searchHeader;
	}
	
	public String getNoResultsMsg()
	{
		return noResultsMsg.getText();
	}
	
	public int getResultsCount() 
	{
		return searchResultsFields.size();
	}
	
	public boolean verifyNumberOfResults()
	{
		String resultCount = String.valueOf(getResultsCount());
		return resultCount.equalsIgnoreCase(numberOfResultsFound.getText());
	}
	
	public PropertyPage selectProperty(String propName)
	{
		for (int i = 0; i<searchResultsFields.size(); i++)
		{
			if(searchResultsFields.get(i).findElement(propertyName).getText().equalsIgnoreCase(propName))
			{
				searchResultsFields.get(i).findElement(By.tagName("a")).click();
				break;
			}
		}
		
		switchToWindow("child");
		
		return new PropertyPage(driver);		
	}
	
	public void sortBy(String sortOption)
	{
		sortByBtn.click();
		WebElement sortByOption = sortOptions.stream().filter(option->option.getText().contains(sortOption)).findFirst().orElse(null);
		sortByOption.click();
	}
	
	public boolean checkIfSortedBy(String sortOption)
	{
		boolean sorted = false;
		int accomPrice;
		int nextAccomPrice = 0;
		int starCount;
		List<Integer> starsReview = new ArrayList<Integer>();
		
		if(sortOption.contains("niže"))
		{	
			accomPrice = Integer.valueOf(prices.get(0).getText().split(" ")[1]);
			for (int i = 1; i<prices.size(); i++)
			{
				nextAccomPrice = Integer.valueOf(prices.get(i).getText().split(" ")[1]);
				if (accomPrice<=nextAccomPrice)
				{
					sorted = true;
					accomPrice = nextAccomPrice;
				
				}
				else
				{
					sorted = false;
				}
			}
		}
			
		if(sortOption.contains("više"))
		{	
			accomPrice = Integer.valueOf(prices.get(0).getText().split(" ")[1]);
			for (int i = 1; i<prices.size(); i++)
			{
				nextAccomPrice = Integer.valueOf(prices.get(i).getText().split(" ")[1]);
				if (accomPrice>=nextAccomPrice)
				{
					sorted = true;
					accomPrice = nextAccomPrice;
				}
				else
				{
					sorted = false;
				}
			}
		}
		
		if(sortOption.contains("Ocjeni"))
		{
			for (WebElement reviewSpan : reviewSpans)
			{
				List<WebElement> stars = reviewSpan.findElements(By.tagName("i"));
				starCount = 0;
				for (WebElement star : stars)
				{
					if (!star.getAttribute("class").contains("fa-star-o"))
					{
						starCount++;
					}
				}
				
				starsReview.add(starCount);
			}
			
			int accomStars = starsReview.get(0);
			for (int i = 1; i<starsReview.size(); i++)
			{
				int nextAccomStars = starsReview.get(i);
				if (accomStars >= nextAccomStars)
				{
					sorted = true;
					accomStars = nextAccomStars;
				}
				else
				{
					sorted = false;
				}
			}
		}
		
		if (sortOption.contains("Broju"))
		{
			String accomReviewNum = reviewSpans.get(0).getText().split(" ")[0];
					
			for (int i = 1; i<reviewSpans.size(); i++)
			{
				String nextAccomReviewNum = reviewSpans.get(i).getText().split(" ")[0];
				
				if (Integer.valueOf(accomReviewNum) >= Integer.valueOf(nextAccomReviewNum))
				{
					sorted = true;
					accomReviewNum = nextAccomReviewNum;
				}
				else
				{
					sorted = false;
				}
			}
		}
		
		return sorted;		
	}
	
	public boolean verifyPriceCurrency (String currencySymbol)
	{
		boolean currencySelected = false;
		
		for (WebElement price : prices)
		{
			if (price.getText().contains(currencySymbol))
			{
				currencySelected = true;
			}
			else
			{
				currencySelected = false;
			}
		}
	
		return currencySelected;
	}
}