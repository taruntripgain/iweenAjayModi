package com.iween.pageObjects;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.iween.utilities.*;

import com.iween.utilities.*;

public class DemoPage extends BasePage {

	 // Constructor of loginPage calls the BasePage constructor with driver
    public DemoPage(WebDriver driver) {
        super(driver);// calls BasePage constructor
       
    }

    @FindBy(xpath = "//input[@name='username']")
    public WebElement tripGainUserName;

    @FindBy(xpath = "//input[@name='password']")
    public WebElement tripGainPassword;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement button;

    @FindBy(xpath = "//input[@id='tg-hl-search-input']")
    private WebElement enterLocation;

    public void enterUserName(String userName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(tripGainUserName));
        tripGainUserName.clear();
        tripGainUserName.sendKeys(userName);
    }

    public void enterPasswordName(String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(tripGainPassword));
        tripGainPassword.clear();
        tripGainPassword.sendKeys(password);
    }

    public void clickButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(button));
        button.click();
    }

    public void hotelClick() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@data-testid='KeyboardArrowDownIcon'])[1]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Hotels']"))).click();
    }

    public void enterCityOrHotelName(String location) throws TimeoutException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(enterLocation));
        enterLocation.clear();
        enterLocation.sendKeys(location);
        selectCityOrHotelName(location);
    }

    public void selectCityOrHotelName(String location) throws TimeoutException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@role='listbox']/parent::div")));
        wait.until(driver -> driver.findElements(By.xpath("//div[@role='option']")).size() > 0);

        List<WebElement> options = driver.findElements(By.xpath("//div[@role='option']"));
        int bestScore = Integer.MAX_VALUE;
        String bestMatchText = null;

        String input = location.trim().toLowerCase();

        for (int i = 0; i < options.size(); i++) {
            try {
                String suggestion = options.get(i).getText().trim().toLowerCase();
                int score = levenshteinDistance(input, suggestion);

                if (score < bestScore) {
                    bestScore = score;
                    bestMatchText = options.get(i).getText().trim();
                }
            } catch (StaleElementReferenceException e) {
                System.out.println("Stale element at index " + i + ", skipping.");
            }
        }

        if (bestMatchText != null) {
            int attempts = 0;
            boolean clicked = false;
            while (attempts < 3 && !clicked) {
                try {
                    WebElement bestMatch = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//div[@role='option' and normalize-space(text())='" + bestMatchText + "']")));
                    bestMatch.click();
                    System.out.println("Selected best match: " + bestMatchText);
                    clicked = true;
                } catch (StaleElementReferenceException e) {
                    System.out.println("Stale element on click attempt " + (attempts + 1) + ", retrying...");
                }
                attempts++;
            }
            if (!clicked) {
                throw new TimeoutException("Could not select city/hotel from dropdown");
            }
        } else {
            throw new NoSuchElementException("No suitable dropdown option for " + location);
        }
    }

    public int levenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) dp[i][j] = j;
                else if (j == 0) dp[i][j] = i;
                else {
                    int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
                    dp[i][j] = Math.min(
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                            dp[i - 1][j - 1] + cost);
                }
            }
        }
        return dp[a.length()][b.length()];
    }

    ///IMP///
  //Method to check whether Home Page is displayed
    public void validateHomePageIsDisplayed(ExtentTest test) {
        try {
            Thread.sleep(3000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Search']")));
            WebElement homePage = driver.findElement(By.xpath("//span[text()='Search']"));
            
            if (homePage.isDisplayed()) {
                System.out.println("Home page is getting displayed successfully.");

                // Call utility method to capture + attach screenshot
               ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.PASS, "Home page is displayed", "HomePageDisplayed");

                test.log(Status.PASS, "Home page is getting displayed successfully.");
            } else {
                System.out.println("Failed to load the Home Page: Element found but not visible.");

               

                test.log(Status.FAIL, "Failed to load the Home Page: Element not visible.");
               ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Home page element not visible", "HomePageNotVisible");
             //   test.log(Status.INFO, "Home page element not visible ,HomePageNotVisible");
                Assert.fail();
            

            }

        } catch (Exception e) {
            System.out.println("Exception while verifying Home Page: " + e.getMessage());

          

            test.log(Status.FAIL, "Exception while verifying Home Page: " + e.getMessage());
            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Exception verifying Home Page: " + e.getMessage(), "HomePageException");
            Assert.fail();
        }
    }
//////
  //Method to Click on Check-Out  Date
  	public void clickOnReturnDate()
  	{
  		driver.findElement(By.xpath("(//input[@class='DayPickerInput input'])[2]")).click();
  	}
  	
  	@FindBy(xpath = "(//input[@class='DayPickerInput input'])[1]")
	WebElement datePickerInput;
  	
  //Method to Select Check-In Date By Passing Two Paramenters(Date and MounthYear)
  	public void selectDate(String day, String MonthandYear) throws InterruptedException
  	{
  		JavascriptExecutor js = (JavascriptExecutor) driver;
  		// Method A: Using zoom
  		js.executeScript("document.body.style.zoom='80%'");
  		
  		datePickerInput.click();
  		String Date=driver.findElement(By.xpath("(//h2[@class='react-datepicker__current-month'])[1]")).getText();
  		if(Date.contentEquals(MonthandYear))
  		{
  			Thread.sleep(4000);
  			driver.findElement(By.xpath("(//div[@class='react-datepicker__month-container'])[1]//div[text()='"+day+"' and @aria-disabled='false']")).click();
  			Thread.sleep(4000);
  		}else {
  			while(!Date.contentEquals(MonthandYear))
  			{
  				Thread.sleep(500);
  				driver.findElement(By.xpath("//button[@aria-label='Next Month']")).click();
  				if(driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]")).getText().contentEquals(MonthandYear))
  				{
  					driver.findElement(By.xpath("(//div[@class='react-datepicker__month-container'])[1]//div[text()='"+day+"' and @aria-disabled='false']")).click();
  					break;
  				}

  			}
  		}
  	}

  	//Method to Select Return Date By Passing Two Paramenters(Date and MounthYear)
  	public void selectReturnDate(String returnDate, String returnMonthAndYear) throws InterruptedException
  	{
  		clickOnReturnDate();
  		String Date=driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]")).getText();
  		if(Date.contentEquals(returnMonthAndYear))
  		{
  			driver.findElement(By.xpath("(//div[@class='react-datepicker__month-container'])[1]//div[text()='"+returnDate+"' and @aria-disabled='false']")).click();
  			Thread.sleep(4000);
  		}else {
  			while(!Date.contentEquals(returnMonthAndYear))
  			{
  				Thread.sleep(500);
  				driver.findElement(By.xpath("//button[@aria-label='Next Month']")).click();
  				if(driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]")).getText().contentEquals(returnMonthAndYear))
  				{
  					driver.findElement(By.xpath("//div[text()='"+returnDate+"']")).click();
  					break;
  				}

  			}
  		}
  	}
  	
  //Method to add room, adt and child
  	public void addRoom(int roomcount, int adultCount , int childCount, int childAge) throws InterruptedException {
  		Thread.sleep(2000);
  		try {
  			System.out.println("Expanding room selection...");
  			driver.findElement(By.xpath("//*[@data-testid='ExpandMoreIcon']")).click();
  		} catch (Exception e) {
  			System.out.println("Failed to click expand icon: " + e.getMessage());
  		}
  		JavascriptExecutor js = (JavascriptExecutor) driver;

  		for(int i = 0; i < roomcount - 1; i++) {
  			try {
  				System.out.println("Adding room: " + (i + 2));
  				driver.findElement(By.xpath("//button[text()='Add Room']")).click();
  			} catch (Exception e) {
  				System.out.println("Failed to click 'Add Room' button at index " + i + ": " + e.getMessage());
  			}
  		}

  		Thread.sleep(3000);

  		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  		try {
  			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(text(),'Room')]")));
  		} catch (Exception e) {
  			System.out.println("Room elements not visible within timeout: " + e.getMessage());
  		}

  		List<WebElement> listOfRooms = driver.findElements(By.xpath("//h6[contains(text(),'Room')]"));
  		System.out.println("Rooms found: " + listOfRooms.size());

  		for(int i = 0; i < listOfRooms.size(); i++) {
  			WebElement roomElement = listOfRooms.get(i);
  			String roomText = roomElement.getText();
  			String[] roomTextSplit1 = roomText.split(" ");
  			String finalRoomText = roomTextSplit1[1].trim();

  			System.out.println("Configuring Room " + finalRoomText);
  			System.out.println("Total rooms: " + listOfRooms.size());
  			System.out.println("Room index: " + i);
  			System.out.println("Adult count: " + adultCount);

  			// Add Adults
  			for(int j = 0; j < adultCount - 1; j++) {
  				try {
  					WebElement addAdult = driver.findElement(By.xpath("(//h6[text()='" + finalRoomText + "']/parent::div/parent::div//p/parent::div)[1]//*[contains(normalize-space(@class), 'tg-hl-adult-plus')]"));
  					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addAdult);
  					Thread.sleep(2000);
  					addAdult.click();
  					System.out.println("Adult added to Room " + finalRoomText);
  				} catch (Exception e) {
  					System.out.println("Failed to add adult in Room " + finalRoomText + " at iteration " + j + ": " + e.getMessage());
  				}
  			}

  			// Add Children
  			for (int k = 1; k < childCount + 1; k++) {
  				try {
  					Thread.sleep(3000);
  					driver.findElement(By.xpath("(//h6[text()='" + finalRoomText + "']/parent::div/parent::div//p/parent::div)[2]//*[contains(normalize-space(@class), 'tg-hl-child-plus')]")).click();
  					System.out.println("Child " + k + " added to Room " + finalRoomText);
  				} catch (Exception e) {
  					System.out.println("Failed to add child in Room " + finalRoomText + " at iteration " + k + ": " + e.getMessage());
  				}

  				try {
  					driver.findElement(By.xpath("((//h6[text()='" + finalRoomText + "']/parent::div/parent::div//p/parent::div)[2]/parent::div//div[@aria-haspopup='listbox'])[" + k + "]")).click();
  					Thread.sleep(2000);
  					List<WebElement> childAgeList = driver.findElements(By.xpath("//ul//li"));
  					childAgeList.get(childAge).click();
  					System.out.println("Child " + k + " age set in Room " + finalRoomText);
  				} catch (Exception e) {
  					System.out.println("Failed to set child age in Room " + finalRoomText + " for child " + k + ": " + e.getMessage());
  				}
  			}

  			// Click Done Button after final room
  			if (i == listOfRooms.size() - 1) {
  				try {
  					WebElement doneButton = driver.findElement(By.xpath("//button[text()='Done']"));
  					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", doneButton);
  					Thread.sleep(500);
  					doneButton.click();
  					System.out.println("Clicked 'Done' after completing Room " + finalRoomText);
  				} catch (Exception e) {
  					System.out.println("Failed to click 'Done' button: " + e.getMessage());
  				}
  			}
  		}
  	}
 // Method to click on search button
 	public void clickOnSearch() {
 		try {
 			System.out.println("Attempting to click on the 'Search' button...");
 			driver.findElement(By.xpath("//button[text()='Search']")).click();
 			System.out.println("'Search' button clicked successfully.");
 		} catch (Exception e) {
 			System.out.println("Failed to click on the 'Search' button: " + e.getMessage());
 		}
 	}
 	//Method to get the hotels name text for validating result screen is displayed or not 

 	public void validateResultScreen(ExtentTest test) {
 	    try {
 	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
 	        WebElement hotelGrid = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[@title]")));

 	        // ✅ If hotel grid is displayed, proceed
 	        if (!hotelGrid.isDisplayed()) {
 	            try {
 	                WebElement hotelNotFound = driver.findElement(
 	                    By.xpath("//*[@data-testid='HotelIcon']/parent::div//h5")
 	                );

 	                if (hotelNotFound.isDisplayed()) {
 	                    String message = hotelNotFound.getText();
 	                    test.log(Status.FAIL, "No hotels found. Message: " + message);
 	                    ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "No hotels found", "NoHotelsFound");
 	                    Assert.fail("No hotels found.");
 	                }

 	            } catch (NoSuchElementException e) {
 	                test.log(Status.PASS, "Hotels are found. 'No hotels' message not present.");
 	            } catch (Exception e) {
 	                test.log(Status.FAIL, "Unexpected exception while checking 'no hotels' message: " + e.getMessage());
 	                ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Exception", "HotelNotFoundCheckFailed");
 	                Assert.fail("Exception while checking hotel result: " + e.getMessage());
 	            }

 	            // ✅ Collect hotel names
 	            List<WebElement> hotelNames = driver.findElements(By.xpath("//*[contains(@class,'tg-hl-name')]"));
 	            if (hotelNames.size() > 0) {
 	                List<String> hotelList = new ArrayList<>();
 	                for (WebElement hotel : hotelNames) {
 	                    String hotelName = hotel.getAttribute("title");
 	                    hotelList.add(hotelName);
 	                    test.log(Status.INFO, "Hotel Found: " + hotelName);
 	                }
 	                test.log(Status.PASS, "Total hotels displayed: " + hotelList.size());
 	            } else {
 	                test.log(Status.FAIL, "Hotel grid displayed but no hotel names found.");
 	                ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "No hotel names found", "HotelListEmpty");
 	                Assert.fail("Hotel grid displayed but no hotel names found.");
 	            }

 	        } else {
 	            test.log(Status.FAIL, "Hotel grid not displayed.");
 	            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Hotel grid missing", "HotelGridMissing");
 	            Assert.fail("Hotel grid not displayed.");
 	        }

 	    } catch (Exception e) {
 	        test.log(Status.FAIL, "Exception during result screen validation: " + e.getMessage());
 	        ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Validation Exception", "ResultScreenValidationException");
 	        Assert.fail("Exception during hotel result validation.");
 	    }
 	}
 		
}
