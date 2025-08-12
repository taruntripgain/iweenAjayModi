package com.iween.pageObjects;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.nio.file.Files;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import com.iween.utilities.ScreenshotUtil;




public class searchFlightPage extends BasePage {



	public searchFlightPage(WebDriver driver)
	{
		super(driver);

	}
	@FindBy(xpath="//*[contains(@class,'origin')]/input")
	WebElement departFrom;
	@FindBy(xpath="//*[contains(@class,'destination')]/input")
	WebElement arrivalTo;
	@FindBy(xpath=" css-cmx4tw-menu") 
	WebElement fromAndToSelectingDropDown;
	
	@FindBy(xpath = "//div[@id='react-select-3-listbox']//div[@class='airport-option']")
	List<WebElement> getAllDepartFrom;
	@FindBy(xpath = "//div[@id='react-select-4-listbox']//div[@class='airport-option']")
	List<WebElement> getAllArrivalTo;
	

	@FindBy(xpath="//span[text()='Adults(12y+)']/parent::div//li[text()='2']")
	WebElement adultCount;
	@FindBy(xpath="//span[text()='Children(2y-12y)']/parent::div//li[text()='5']")
	WebElement childCount;
	@FindBy(xpath="//span[text()='Infants(<2y)']/parent::div//li[text()='1']")
	WebElement infantCount;

	@FindBy(xpath="//span[@class='travellers-class_text']")
	WebElement clickOnClassPassangerDropdown;
	@FindBy(id="prefclass")
	WebElement classDropdown;
	@FindBy(xpath="//button[text()='Done']")
	WebElement doneButton;
	@FindBy(xpath="//button[text()='Search Flights']")
	WebElement searchFlight;

	@FindBy(xpath = "(//div[@class='react-datepicker__input-container'])[1]")
	WebElement datePickerInput;
 
	@FindBy(xpath = "(//div[@class='react-datepicker__current-month'])[1]")
	WebElement date;
 
	@FindBy(xpath = "//button[@aria-label='Next Month']")
	WebElement nextMonth;
 
 
	@FindBy(xpath = "(//div[@class='react-datepicker__header ']/child::div)[1]")
	WebElement MonthYear;
	
	@FindBy(xpath="//div[text()='Search Flights']")
	WebElement flightSearchPageDisplayed;
	
	
	
	
	public void departFrom(String from) {
		try {
			System.out.println(from);
			departFrom.sendKeys(from);
		
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@class='airport-focused airport-option']")).click();
		//	}
		}  catch (Exception e) {
			System.out.println("Error in departFrom(): " + e.getMessage());
		}
	}

	public void ArrivalTo(String to) {
		try {
			arrivalTo.sendKeys(to);
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@class='airport-focused airport-option']")).click();
		//	}
			
		}  catch (Exception e) {
			System.out.println("Error in ArrivalTo(): " + e.getMessage());
		}
	}

	
	public void selectDate(String day, String MonthandYear) throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Method A: Using zoom
		js.executeScript("document.body.style.zoom='80%'");
 
		datePickerInput.click();
		String Date = date.getText();
		//	String Date=driver.findElement(By.xpath("(//h2[@class='react-datepicker__current-month'])[1]")).getText();
		if(Date.contentEquals(MonthandYear))
		{
			Thread.sleep(4000);
			driver.findElement(By.xpath("(//div[@class='react-datepicker__month-container'])[1]//div[text()='"+day+"' and @aria-disabled='false']")).click();
			Thread.sleep(4000);
		}else {
			while(!Date.contentEquals(MonthandYear))
			{
				Thread.sleep(500);
				nextMonth.click();
				if(MonthYear.getText().contentEquals(MonthandYear))
				{
					driver.findElement(By.xpath("(//div[@class='react-datepicker__month-container'])[1]//div[text()='"+day+"' and @aria-disabled='false']")).click();
					break;
				}
 
			}
		}
	}
	
	public void addAdult(String adult) {
	    try {
	        driver.findElement(By.xpath("//*[contains(@class,'adult')][text()='" + adult + "']")).click();
	    } catch (Exception e) {
	        System.out.println("Error in addAdult(): " + e.getMessage());
	    }
	}

	public void addChild(String child) {
	    try {
	        driver.findElement(By.xpath("//*[contains(@class,'child')][text()='" + child + "']")).click();
	    } catch (Exception e) {
	        System.out.println("Error in addChild(): " + e.getMessage());
	    }
	}

	public void infantCount(String infant) {
	    try {
	        driver.findElement(By.xpath("//*[contains(@class,'infant')][text()='" + infant + "']")).click();
	    } catch (Exception e) {
	        System.out.println("Error in infantCount(): " + e.getMessage());
	    }
	}

	
	
	public void selectTravelClass(String travelClass) {
	    try {
	        classDropdown.click();
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

	        Select select = new Select(classDropdown);
	        select.selectByVisibleText(travelClass);
	    } catch (Exception e) {
	        System.out.println("Error in selectTravelClass(): " + e.getMessage());
	    }
	}

	public void clickOnSearchFlight() {
		try {
			searchFlight.click();
		} catch (Exception e) {
			System.out.println("Error in clickOnSearchFlight(): " + e.getMessage());
		}
	}
	
	

	
	public void searchFightsOnHomePage(String from, String to, String day, String MonthandYear, String adult, String child, String infant) {
	    try {
	    	Thread.sleep(1000);
	    	enterFromLocation(from);
	        Thread.sleep(1000);
	        enterToLocation(to);
	        Thread.sleep(1000);
	        selectDate(day, MonthandYear);
	        Thread.sleep(1000);
	        
	        clickOnClassPassangerDropdown.click();
	        Thread.sleep(1000);
	        addAdult(adult);
	        Thread.sleep(1000);
	        addChild(child);
	        Thread.sleep(1000);
	        infantCount(infant);
	        Thread.sleep(1000);
	        doneButton.click();
	       
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt(); // Best practice to reset the interruption status
	        System.out.println("Interrupted while searching flights on home page: " + e.getMessage());
	    } catch (Exception e) {
	        System.out.println("Error in searchFightsOnHomePage(): " + e.getMessage());
	    }
	}

	public void verifyFlightSearchPageIsDisplayed(ExtentTest test)
	{
		if(flightSearchPageDisplayed.isDisplayed())
		{
			 System.out.println("Search page is getting displayed successfully.");
			  test.log(Status.PASS, "Search page is getting displayed successfully.");
        } else {
            System.out.println("Failed to load the Search Page: Element found but not visible.");
            test.log(Status.FAIL, "Failed to load the Search Page: Element not visible.");
           ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Search page element not visible", "SearchPageNotVisible");
         //   test.log(Status.INFO, "Home page element not visible ,HomePageNotVisible");
            Assert.fail();
		}
	}
	
	
	//Method to select City.
		public void location(String location) throws TimeoutException {
			try {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

				// Wait for dropdown container to appear
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//div[@role='listbox']")));

				// Wait until options are loaded
				wait.until(driver -> driver.findElements(By.xpath("//span[@class='airport-option_country-code']")).size() > 0);

				List<WebElement> initialOptions = driver.findElements(By.xpath("//span[@class='airport-option_country-code']"));
				int bestScore = Integer.MAX_VALUE;
				String bestMatchText = null;

				String input = location.trim().toLowerCase();

				for (int i = 0; i < initialOptions.size(); i++) {
					try {
						WebElement option = driver.findElements(By.xpath("//span[@class='airport-option_country-code']")).get(i);
						String suggestion = option.getText().trim().toLowerCase();
						int score = levenshteinDistance(input, suggestion);

						if (score < bestScore) {
							bestScore = score;
							bestMatchText = option.getText().trim();
						}
					} catch (StaleElementReferenceException e) {
						System.out.println("Stale element at index " + i + ", skipping.");
					}
				}

				if (bestMatchText != null) {
					// Retry clicking best match up to 3 times
					int attempts = 0;
					boolean clicked = false;
					while (attempts < 3 && !clicked) {
						try {
							WebElement bestMatch = wait.until(ExpectedConditions.elementToBeClickable(
									By.xpath("//span[@class='airport-option_country-code'][text()='" + bestMatchText + "']")));
							bestMatch.click();
							System.out.println("Selected best match: " + bestMatchText);
							clicked = true;
						} catch (StaleElementReferenceException e) {
							System.out.println("Stale element on click attempt " + (attempts + 1) + ", retrying...");
						}
						attempts++;
					}

					if (!clicked) {
						System.out.println("Failed to click the best match after retries.");
					}

				} else {
					System.out.println("No suitable match found for input: " + location);
				}

			} catch (NoSuchElementException e) {
				System.out.println("Input or dropdown not found: " + e.getMessage());
			} catch (Exception e) {
				System.out.println("Unexpected error while selecting city or hotel: " + e.getMessage());
			}
		}
		
		//Method to enter From Location
		public void enterFromLocation(String from) {
			try {
				departFrom.clear();
				departFrom.sendKeys(from);
				location(from);
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void enterToLocation(String to) {
			try {
				arrivalTo.clear();
				arrivalTo.sendKeys(to);
				location(to);
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public int levenshteinDistance(String a, String b) {
			int[][] dp = new int[a.length() + 1][b.length() + 1];

			for (int i = 0; i <= a.length(); i++) {
				for (int j = 0; j <= b.length(); j++) {
					if (i == 0) {
						dp[i][j] = j;
					} else if (j == 0) {
						dp[i][j] = i;
					} else {
						int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
						dp[i][j] = Math.min(
								Math.min(dp[i - 1][j] + 1,     // deletion
										dp[i][j - 1] + 1),    // insertion
								dp[i - 1][j - 1] + cost); // substitution
					}
				}
			}
			return dp[a.length()][b.length()];
		}

		public static void logFullPageSourceToReport(WebDriver driver, ExtentTest test) {
	        try {
	            // Get raw page source
	            String rawHtml = driver.getPageSource();

	            // Parse and pretty format using Jsoup (optional)
	            Document doc = Jsoup.parse(rawHtml);
	            String prettyHtml = doc.outerHtml();

	            // Escape HTML entities to show code properly inside the report
	            String escapedHtml = prettyHtml
	                .replace("&", "&amp;")
	                .replace("<", "&lt;")
	                .replace(">", "&gt;")
	                .replace("\"", "&quot;")
	                .replace("'", "&#39;");

	            // Log inside report wrapped in a <pre> block for formatting
	            String htmlBlock = "<pre style='white-space: pre-wrap; max-height:400px; overflow:auto; border:1px solid #ccc; padding:10px;'>" 
	                + escapedHtml + "</pre>";

	            test.log(Status.INFO, "Full Page Source HTML:\n" + htmlBlock);

	        } catch (Exception e) {
	            test.log(Status.FAIL, "Error logging full page source: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

}
