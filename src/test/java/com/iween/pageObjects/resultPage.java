package com.iween.pageObjects;

import static org.testng.Assert.fail;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.iween.utilities.ScreenshotUtil;

public class resultPage extends BasePage{

	 public resultPage(WebDriver driver) {
	        super(driver);// calls BasePage constructor
	    }
	    
	 @FindBy(xpath="//*[contains(@class,'one-way-new-result-card')]")
	 WebElement resultFlightCard;
	 @FindBy(xpath="//*[@name='carrier']")
	 List<WebElement> flightNames;
	 
	 @FindBy(xpath="//*[@class='flight-company']")
		List<WebElement>flightName;
	 
	 //need to change the xpath in futhure
	 @FindBy(xpath="(//*[contains(@class,'singleValue')])[1]//span")
	 WebElement userEnteredFromLocation;
	 
	 @FindBy(xpath="(//*[contains(@class,'singleValue')])[2]//span")
	 WebElement userEnteredToLocation;
	
	 public void validateResultPageIsDisplayed(ExtentTest test)
	 {
		 if(resultFlightCard.isDisplayed())
			{
				 System.out.println("Result page is getting displayed successfully.");
				  test.log(Status.PASS, "Result page is getting displayed successfully.");
	        } else {
	            System.out.println("Failed to load the Result Page: Element found but not visible.");
	            test.log(Status.FAIL, "Failed to load the Result Page: Element not visible.");
	           ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Result page element not visible", "ResultPageNotVisible");
	         //   test.log(Status.INFO, "Home page element not visible ,HomePageNotVisible");
	            Assert.fail();
			} 
	 }
	 /*
	 public void displayAvailableFlights(ExtentTest test) {
		    try {
		        if (!flightNames.isEmpty()) {
		            JavascriptExecutor js = (JavascriptExecutor) driver;

		            for (WebElement flight : flightNames) {
		                // Scroll the element into view
		                js.executeScript("arguments[0].scrollIntoView(true);", flight);
		                Thread.sleep(2000); // Optional: wait for smooth scroll (can use WebDriverWait if needed)

		                String flightNameText = flight.getText();
		                System.out.println("Available Flights displayed from the selected route: " + flightNameText);
		                test.log(Status.INFO, "Available Flights displayed from the selected route: " + flightNameText);
		            }
		        } else {
		            System.out.println("Flights are not displaying for selected route");
		            test.log(Status.FAIL, "Flights are not displaying for selected route");
		            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Flights are not displaying for selected route", "Flights are not displaying");
		        }
		    } catch (Exception e) {
		        System.out.println("Exception occurred while displaying flights: " + e.getMessage());
		        test.log(Status.FAIL, "Exception occurred while displaying flights: " + e.getMessage());
		        ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Exception occurred while displaying flights", "Exception while fetching flights");
		    }
		}
	 */
	 public void displayAvailableFlights(ExtentTest test) {
		    try {
		        // Re-fetch the flight list to avoid stale elements
		        List<WebElement> currentFlights = driver.findElements(By.xpath("//*[@name='carrier']"));

		        if (!currentFlights.isEmpty()) {
		            JavascriptExecutor js = (JavascriptExecutor) driver;

		            for (WebElement flight : currentFlights) {
		                try {
		                    // Scroll the element into view
		                    js.executeScript("arguments[0].scrollIntoView(true);", flight);
		                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		                    wait.until(ExpectedConditions.visibilityOf(flight));

		                    String flightNameText = flight.getAttribute("id").trim();

		                    if (!flightNameText.isEmpty()) {
		                        System.out.println("Available Flight: " + flightNameText);
		                        test.log(Status.INFO, "Available Flight: " + flightNameText);
		                    } else {
		                        System.out.println("Flight element found but name is empty.");
		                        test.log(Status.WARNING, "Flight element found but name is empty.");
		                    }

		                } catch (StaleElementReferenceException se) {
		                    System.out.println("Stale element encountered while displaying flights.");
		                    test.log(Status.WARNING, "Stale element encountered for a flight entry.");
		                }
		            }

		        } else {
		            System.out.println("Flights are not displaying for selected route");
		            test.log(Status.FAIL, "Flights are not displaying for selected route");
		            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL,
		                "No Flights", "Flights list is empty or not visible.");
		        }

		    } catch (Exception e) {
		        System.out.println("Exception occurred while displaying flights: " + e.getMessage());
		        test.log(Status.FAIL, "Exception occurred while displaying flights: " + e.getMessage());
		        ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL,
		            "Error", "Exception: " + e.getMessage());
		    }
		}

	 public void selectAirLines(String airLineName, ExtentTest test) {
		    try {
		    	((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -document.body.scrollHeight);");

		    	
		    	
		        WebElement airlineCheckbox = driver.findElement(By.xpath("//*[@name='carrier' and @id='" + airLineName + "']"));

		        // Scroll into view
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", airlineCheckbox);
		        Thread.sleep(500); // Optional: wait for smooth scroll

		        // Click checkbox
		        airlineCheckbox.click();

		        // Log success
		        System.out.println("Airline selected: " + airLineName);
		        test.log(Status.PASS, "Airline selected: " + airLineName);
		    } catch (NoSuchElementException e) {
		        System.out.println("Airline with ID '" + airLineName + "' not found.");
		        test.log(Status.FAIL, "Airline with ID '" + airLineName + "' not found.");
		        ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Airline not found", "Airline: " + airLineName);
		    } catch (Exception e) {
		        System.out.println("Exception occurred while selecting airline: " + e.getMessage());
		        test.log(Status.FAIL, "Exception occurred while selecting airline: " + e.getMessage());
		        ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Error selecting airline", "Exception: " + e.getMessage());
		    }
		}

	 public List<String> selectAvailableAirlines(WebDriver driver, ExtentTest test) {
		    List<WebElement> airlineCheckboxes = driver.findElements(By.cssSelector("input[name='carrier']"));
		    List<String> selectedAirlines = new ArrayList<>();

		    if (airlineCheckboxes.isEmpty()) {
		        test.log(Status.WARNING, "No airline checkboxes found.");
		        return selectedAirlines; // returns empty list
		    }

		    for (WebElement checkbox : airlineCheckboxes) {
		        String airlineName = checkbox.getAttribute("id");

		        try {
		            if (checkbox.isDisplayed() && checkbox.isEnabled() && !checkbox.isSelected()) {
		                
		                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);
		                Thread.sleep(500); 

		                
		                checkbox.click();

		                
		                System.out.println("Airline selected: " + airlineName);
		                test.log(Status.PASS, "Airline selected: " + airlineName);
		                selectedAirlines.add(airlineName);

		                
		                Thread.sleep(1000);
		                break;
		            } else {
		                test.log(Status.INFO, "Airline already selected or not clickable: " + airlineName);
		            }
		        } catch (Exception e) {
		            System.out.println("Error selecting airline: " + airlineName + " - " + e.getMessage());
		            test.log(Status.FAIL, "Error selecting airline: " + airlineName + " - " + e.getMessage());
		            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Airline selection error", "Airline: " + airlineName);
		        Assert.fail();
		        }
		    }

		    return selectedAirlines;
		}

	 public void validateSelectedFlights(String airlineName, ExtentTest test) {
		    try {
		        boolean airlineFound = false;
		        StringBuilder matchedFlights = new StringBuilder();

		        if (flightName != null && !flightName.isEmpty()) {
		            for (WebElement flightElement : flightName) {
		                String actualFlightName = flightElement.getText();

		                // Check if the flight name contains the airline name
		                if (actualFlightName.toLowerCase().contains(airlineName.toLowerCase())) {
		                    System.out.println("Flight found for airline: " + actualFlightName);
		                    matchedFlights.append(actualFlightName).append("; ");
		                    airlineFound = true;
		                }
		            }

		            if (airlineFound) {
		                test.log(Status.PASS, "Flights found for airline: " + matchedFlights.toString());
		            } else {
		                System.out.println("No flights found for airline: " + airlineName);
		                test.log(Status.FAIL, "No flights found for airline: " + airlineName);
		                ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "No matching flights", "Airline: " + airlineName);
		                Assert.fail();
		            }

		        } else {
		            System.out.println("Flights are not displaying for selected route");
		            test.log(Status.FAIL, "Flights are not displaying for selected route");
		            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "No flights found", "Flights list is empty");
		            Assert.fail();
		        }

		    } catch (Exception e) {
		        System.out.println("Exception occurred while validating flights: " + e.getMessage());
		        test.log(Status.FAIL, "Exception occurred while validating flights: " + e.getMessage());
		        ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Error validating flights", "Exception: validating flights");
		        Assert.fail();
		    }
		}

	 public Map<String, String> userData() {
		    Map<String, String> userDetails = new HashMap<>();

		    try {
		        String userEnteredFromLocations = userEnteredFromLocation.getText();
		        String userEnteredToLocations = userEnteredToLocation.getText();
		        String userEnteredDepartDate = driver.findElement(By.xpath("//*[text()='Departure Date']/parent::div//input")).getAttribute("value");
		        String travellerClassDetails = driver.findElement(By.xpath("//*[@class='travellers-class_text']")).getText();

		        // Put common details once
		        userDetails.put("fromLocation", userEnteredFromLocations);
		        userDetails.put("toLocation", userEnteredToLocations);
		        userDetails.put("departDate", userEnteredDepartDate);
		        userDetails.put("travellerClass", travellerClassDetails);

		        WebElement oneWay = driver.findElement(By.xpath("//*[@for='OneWay']//input"));
		        if (oneWay.isSelected()) {
		            String trip = driver.findElement(By.xpath("//*[text()='One Way']")).getText();
		            userDetails.put("trip", trip);
		        } else {
		            System.out.println("One Way is not selected");
		           
		        }
		        
		    } catch (Exception e) {
		        e.printStackTrace();
		        userDetails.put("error", "Failed to fetch user data: " + e.getMessage());
		        System.out.println("Failed to fetch user data: " + e.getMessage());
		    }

		    return userDetails;
		}


//	 public void selectFlightBasedOnIndex(int index)
//	 {
//		 WebElement flightCard=driver.findElement(By.xpath("(//*[contains(@class,'one-way-new-result-card')])[1]"));
//		 String airlineName=flightCard.findElement(By.xpath(".//*[@class='flight-company']")).getText();
//		 String fareTypeSelected=flightCard.findElement(By.xpath(".//*[contains(@class,'flight-company-secondary')]")).getText();
//		 String flightCode=flightCard.findElement(By.xpath(".//*[contains(@class,'flight-number')]")).getText();
//		 String departureTime=flightCard.findElement(By.xpath(".//*[contains(@class,'title')]")).getText();
//		 String departureDate=flightCard.findElement(By.xpath(".//*[contains(@class,'fs-12')]")).getText();
//		 String fromLocation=flightCard.findElement(By.xpath("(.//*[contains(@class,'fs-10')])[2]")).getText();
//		 String duration=flightCard.findElement(By.xpath(".//*[contains(@class,'fs-12 fw-500')]")).getText();
//		 String stops=flightCard.findElement(By.xpath("*//.[contains(@class,'fs-8')]")).getText();
//		 String arrivalTime=flightCard.findElement(By.xpath("(.//*[contains(@class,'title fw-600')])[2]")).getText();
//		 String arrivalDate=flightCard.findElement(By.xpath("(.//*[contains(@class,'fs-12')])[3]")).getText();
//		 String destinationLocation=flightCard.findElement(By.xpath("(.//*[contains(@class,'fs-10')])[3]")).getText();
//		 
//	 }
	 
	 public Map<String, String> selectFlightBasedOnIndex(int index, ExtentTest test) {
		    Map<String, String> flightDetails = new HashMap<>();
		    try {
		        WebElement flightCard = driver.findElement(By.xpath("(//*[contains(@class,'one-way-new-result-card')])[" + index + "]"));
		        
		        // Scroll flight card into view before extracting details
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", flightCard);

		        flightDetails.put("airlineName", flightCard.findElement(By.xpath(".//*[@class='flight-company']")).getText());
		        flightDetails.put("fareTypeSelected", flightCard.findElement(By.xpath(".//*[contains(@class,'flight-company-secondary')]")).getText());
		        flightDetails.put("flightCode", flightCard.findElement(By.xpath(".//*[contains(@class,'flight-number')]")).getText());
		        flightDetails.put("departureTime", flightCard.findElement(By.xpath(".//*[contains(@class,'title')]")).getText());
		        flightDetails.put("departureDate", flightCard.findElement(By.xpath(".//*[contains(@class,'fs-12')]")).getText());
		        flightDetails.put("fromLocation", flightCard.findElement(By.xpath("(.//*[contains(@class,'fs-10')])[2]")).getText());
		        flightDetails.put("duration", flightCard.findElement(By.xpath(".//*[contains(@class,'fs-12 fw-500')]")).getText());
		        flightDetails.put("stops", flightCard.findElement(By.xpath(".//*[contains(@class,'fs-8')]")).getText());
		        flightDetails.put("arrivalTime", flightCard.findElement(By.xpath("(.//*[contains(@class,'title fw-600')])[2]")).getText());
		        flightDetails.put("arrivalDate", flightCard.findElement(By.xpath("(.//*[contains(@class,'fs-12')])[3]")).getText());
		        flightDetails.put("destinationLocation", flightCard.findElement(By.xpath("(.//*[contains(@class,'fs-10')])[3]")).getText());

		        // Extract fare and supplier info and log it
		      //  fareAndSupplier(index, test);

		    } catch (Exception e) {
		        e.printStackTrace();
		        flightDetails.put("error", "Something went wrong: " + e.getMessage());
		        test.log(Status.FAIL, "Exception in selectFlightBasedOnIndex: " + e.getMessage());
		    }
		    return flightDetails;
		}
	/* 
	 public void validateFlightDetails(Map<String, String> userDetails, Map<String, String> flightDetails, ExtentTest test,int index) {
		    try {
		        // Getting user-entered data
		        String userEnteredFromLocation = userDetails.get("fromLocation");
		        String userEnteredToLocation = userDetails.get("toLocation");
		        String userEnteredDepartDate = userDetails.get("departDate");
		        String userEnteredTravellerClass = userDetails.get("travellerClass");

		        // Getting flight result data
		        String fromLocation = flightDetails.get("fromLocation");
		        String toLocation = flightDetails.get("destinationLocation");
		        String departureDate = flightDetails.get("departureDate");

		        boolean fromMatch = userEnteredFromLocation.equalsIgnoreCase(fromLocation);
		        boolean toMatch = userEnteredToLocation.equalsIgnoreCase(toLocation);
		        boolean dateMatch = userEnteredDepartDate.equals(departureDate);

		        if (fromMatch && toMatch && dateMatch) {
		            test.log(Status.PASS, "Flight search results match user input.");
		            test.log(Status.PASS, "From Location: " + userEnteredFromLocation + " == " + fromLocation);
		            test.log(Status.PASS, "To Location: " + userEnteredToLocation + " == " + toLocation);
		            test.log(Status.PASS, "Departure Date: " + userEnteredDepartDate + " == " + departureDate);
		            //
		            // Extract fare and supplier info and log it
			        fareAndSupplier(index, test);
			        
		        } else {
		            test.log(Status.FAIL, "Mismatch in flight search results:");

		            if (!fromMatch)
		                test.log(Status.FAIL, "From Location mismatch: expected '" + userEnteredFromLocation + "', but got '" + fromLocation + "'");

		            if (!toMatch)
		                test.log(Status.FAIL, "To Location mismatch: expected '" + userEnteredToLocation + "', but got '" + toLocation + "'");

		            if (!dateMatch)
		                test.log(Status.FAIL, "Departure Date mismatch: expected '" + userEnteredDepartDate + "', but got '" + departureDate + "'");

		            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Flight details mismatch", "Mismatch in flight search results");
		            Assert.fail("Flight search details did not match user input.");
		        }
		    } catch (Exception e) {
		        test.log(Status.FAIL, "Exception during flight details validation: " + e.getMessage());
		        ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Validation error", "Exception occurred");
		        Assert.fail("Exception during validation: " + e.getMessage());
		    }
		}
		*/
	 /*
	 public void validateFlightDeatails(Map<String, String> userDetails, Map<String, String> flightDetails, ExtentTest test,int index) {
		    try {
		        // Get and normalize user-entered data
		        String userEnteredFromLocation = normalize(userDetails.get("fromLocation"));
		        System.out.println(userEnteredFromLocation);
		        String userEnteredToLocation = normalize(userDetails.get("toLocation"));
		        System.out.println(userEnteredToLocation);
		        String userEnteredDepartDate = normalizeDate(userDetails.get("departDate"));
                System.out.println(userEnteredDepartDate);
                
		        // Get and normalize actual flight data
		        String fromLocation = normalize(flightDetails.get("fromLocation"));
		        System.out.println(fromLocation);
		        String toLocation = normalize(flightDetails.get("destinationLocation"));
		        System.out.println(toLocation);
		        String departureDate = normalizeDate(flightDetails.get("departureDate"));
		        System.out.println(departureDate);

		        boolean fromMatch = userEnteredFromLocation.equalsIgnoreCase(fromLocation);
		        boolean toMatch = userEnteredToLocation.equalsIgnoreCase(toLocation);
		        boolean dateMatch = userEnteredDepartDate.equalsIgnoreCase(departureDate);

		        if (fromMatch && toMatch && dateMatch) {
		            test.log(Status.PASS, "Flight search results match user input.");
		            test.log(Status.PASS, "From Location: " + userDetails.get("fromLocation") + " == " + flightDetails.get("fromLocation"));
		            test.log(Status.PASS, "To Location: " + userDetails.get("toLocation") + " == " + flightDetails.get("destinationLocation"));
		            test.log(Status.PASS, "Departure Date: " + userDetails.get("departDate") + " == " + flightDetails.get("departureDate"));
		            
		            // Extract fare and supplier info and log it
			        fareAndSupplier(index, test);
		        } else {
		            test.log(Status.FAIL, "Mismatch in flight search results:");

		            if (!fromMatch)
		                test.log(Status.FAIL, "From Location mismatch: expected '" + userDetails.get("fromLocation") + "', but got '" + flightDetails.get("fromLocation") + "'");

		            if (!toMatch)
		                test.log(Status.FAIL, "To Location mismatch: expected '" + userDetails.get("toLocation") + "', but got '" + flightDetails.get("destinationLocation") + "'");

		            if (!dateMatch)
		                test.log(Status.FAIL, "Departure Date mismatch: expected '" + userDetails.get("departDate") + "', but got '" + flightDetails.get("departureDate") + "'");

		            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Flight details mismatch", "Mismatch in flight search results");
		            Assert.fail("Flight search details did not match user input.");
		        }
		    } catch (Exception e) {
		        test.log(Status.FAIL, "Exception during flight details validation: " + e.getMessage());
		        ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Validation error", "Exception occurred");
		        Assert.fail("Exception during validation: " + e.getMessage());
		    }
		}

		// Helper to clean and normalize text values
		private String normalize(String input) {
		    return input == null ? "" : input.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
		}

		// Helper to clean date values (e.g., remove commas, extra spaces)
		private String normalizeDate(String input) {
		    return input == null ? "" : input.replaceAll("[,]", "").replaceAll("\\s+", " ").trim().toLowerCase();
		}
*/
	 

	     // Main validation method
	     public void validateFlightDeatails(Map<String, String> userDetails, Map<String, String> flightDetails, ExtentTest test, int index) {
	         try {
	             // Normalize user-entered data
	             String userEnteredFromLocation = mapLocation(userDetails.get("fromLocation"));
	             System.out.println("User From: " + userEnteredFromLocation);
	             String userEnteredToLocation = mapLocation(userDetails.get("toLocation"));
	             System.out.println("User To: " + userEnteredToLocation);
	             String userEnteredDepartDate = formatDate(userDetails.get("departDate"));
	             System.out.println("User Date: " + userEnteredDepartDate);

	             // Normalize flight data
	             String fromLocation = mapLocation(flightDetails.get("fromLocation"));
	             System.out.println("Flight From: " + fromLocation);
	             String toLocation = mapLocation(flightDetails.get("destinationLocation"));
	             System.out.println("Flight To: " + toLocation);
	             String departureDate = formatDate(flightDetails.get("departureDate"));
	             System.out.println("Flight Date: " + departureDate);

	             boolean fromMatch = userEnteredFromLocation.equalsIgnoreCase(fromLocation);
	             boolean toMatch = userEnteredToLocation.equalsIgnoreCase(toLocation);
	             boolean dateMatch = userEnteredDepartDate.equalsIgnoreCase(departureDate);

	             if (fromMatch && toMatch && dateMatch) {
	                 test.log(Status.PASS, "Flight search results match user input.");
	                 test.log(Status.PASS, "From Location: " + userDetails.get("fromLocation") + " == " + flightDetails.get("fromLocation"));
	                 test.log(Status.PASS, "To Location: " + userDetails.get("toLocation") + " == " + flightDetails.get("destinationLocation"));
	                 test.log(Status.PASS, "Departure Date: " + userDetails.get("departDate") + " == " + flightDetails.get("departureDate"));

	              // Extract fare and supplier info and log it
	                 fareAndSupplier(index, test); 
	             } else {
	                 test.log(Status.FAIL, "Mismatch in flight search results:");

	                 if (!fromMatch)
	                     test.log(Status.FAIL, "From Location mismatch: expected '" + userDetails.get("fromLocation") + "', but got '" + flightDetails.get("fromLocation") + "'");

	                 if (!toMatch)
	                     test.log(Status.FAIL, "To Location mismatch: expected '" + userDetails.get("toLocation") + "', but got '" + flightDetails.get("destinationLocation") + "'");

	                 if (!dateMatch)
	                     test.log(Status.FAIL, "Departure Date mismatch: expected '" + userDetails.get("departDate") + "', but got '" + flightDetails.get("departureDate") + "'");

	                 ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Flight details mismatch", "Mismatch in flight search results");
	                 Assert.fail("Flight search details did not match user input.");
	             }

	         } catch (Exception e) {
	             test.log(Status.FAIL, "Exception during flight details validation: " + e.getMessage());
	             ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Validation error", "Exception occurred");
	             Assert.fail("Exception during validation: " + e.getMessage());
	         }
	     }

	     // Normalize and map known location aliases
	     private String mapLocation(String loc) {
	         if (loc == null) return "";

	         // Normalize: remove special characters and digits
	         String cleaned = loc.toLowerCase().trim().replaceAll("[^a-z]", "");

	         // Map common aliases
	         Map<String, String> locationMap = new HashMap<>();
	         locationMap.put("bangalore", "bengaluru");
	         locationMap.put("blr", "bengaluru");
	         locationMap.put("coimbatore", "coimbatore");
	         locationMap.put("delhi", "delhi");
	         locationMap.put("chennai", "chennai");

	         return locationMap.getOrDefault(cleaned, cleaned);
	     }

	     // Format various date formats to standard 'dd MMM yyyy'
	     private String formatDate(String rawDate) {
	         if (rawDate == null || rawDate.isEmpty()) return "";

	         List<String> possibleFormats = Arrays.asList("dd MMM yy", "dd MMM yyyy", "dd MMM, yyyy");

	         for (String format : possibleFormats) {
	             try {
	                 SimpleDateFormat parser = new SimpleDateFormat(format, Locale.ENGLISH);
	                 Date parsedDate = parser.parse(rawDate);
	                 return new SimpleDateFormat("dd MMM yyyy").format(parsedDate);
	             } catch (Exception ignored) {
	             }
	         }

	         return rawDate.trim().toLowerCase(); // fallback
	     }

	    
	 


	 /*
		public void fareAndSupplier(int index, ExtentTest test) {
		    try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		        WebElement flightCard = driver.findElement(By.xpath("(//*[contains(@class,'one-way-new-result-card')])[" + index + "]"));
		        
		        // Scroll flight card into view before interacting
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", flightCard);

		        Thread.sleep(2000);
		        // Try to click on "More Fares" if available
		        List<WebElement> moreFaresList = flightCard.findElements(By.xpath(".//*[@xmlns='http://www.w3.org/2000/svg']/parent::div/span"));
		        if (!moreFaresList.isEmpty()) {
		            WebElement moreFaresElement = moreFaresList.get(moreFaresList.size() - 1);

		            // Scroll "More Fares" into view before clicking
		            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", moreFaresElement);

		            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", moreFaresElement);
		            
		            test.log(Status.INFO, "Clicked on More Fares for flight card index: " + index);
		        } else {
		            test.log(Status.INFO, "More fares not available for flight card index: " + index);
		            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.INFO, "Flight Fare Info", "More Fares Not Avaliable");
		        }
                   Thread.sleep(5000);
		        // Wait for fare info card to be visible
		        WebElement fareInfoCard = wait.until(ExpectedConditions.visibilityOf(
		            flightCard.findElement(By.xpath(".//*[contains(@class,'fare-info_details')]"))));

		        if (fareInfoCard.isDisplayed()) {

		            List<WebElement> supplierNames = fareInfoCard.findElements(By.xpath(".//*[@class='fare-component position-relative']//div[@class='fare-component_supplier-name']"));
		            List<WebElement> fares = fareInfoCard.findElements(By.xpath(".//*[@class='fare-component position-relative']//*[contains(@class,'fs-12 text-capitalize')]"));
		            List<WebElement> prices = fareInfoCard.findElements(By.xpath(".//*[@class='fare-component position-relative']//*[contains(@class,'fs-16 fw-600')]"));
		            List<WebElement> seatAvailable = fareInfoCard.findElements(By.xpath(".//*[@class='fare-component position-relative']//*[@class='seats-left']//*[contains(@class,'seats-left_text')]"));
		            List<WebElement> cabinBaggage = fareInfoCard.findElements(By.xpath(".//*[@class='fare-component position-relative']//*[@class='cabin-baggage']//*[contains(@class,'seats-left_text')]"));
		            List<WebElement> refundableFares = fareInfoCard.findElements(By.xpath(".//*[@class='fare-component position-relative']//*[contains(@class,'refundable-info')]"));

		            int fareCount = supplierNames.size(); // Assuming all lists align in size
		            for (int i = 0; i < fareCount; i++) {
		                String supplier = supplierNames.get(i).getText();
		                String fareType = i < fares.size() ? fares.get(i).getText() : "N/A";    // Ternary operator for safety
		                String priceText = i < prices.size() ? prices.get(i).getText() : "N/A";
		                String seatText = i < seatAvailable.size() ? seatAvailable.get(i).getText() : "N/A";
		                String baggage = i < cabinBaggage.size() ? cabinBaggage.get(i).getText() : "N/A";
		                String refundable = i < refundableFares.size() ? refundableFares.get(i).getText() : "N/A";

		                String logMessage = String.format(
		                    "Supplier: %s | Fare: %s | Price: %s | Seats Left: %s | Cabin Baggage: %s | Refundable: %s",
		                    supplier, fareType, priceText, seatText, baggage, refundable
		                );

		                test.log(Status.INFO, logMessage);
		               
		                // ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.PASS, "Flight Fare Info", logMessage);
		            }

		        } else {
		            test.log(Status.INFO, "Fare info card not visible for flight card index: " + index);
		            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.INFO, "Fare info card not visible for flight card index", "noFare");
		        }

		    } catch (Exception e) {
		        test.log(Status.FAIL, "Exception occurred while extracting fare info: " + e.getMessage());
		        ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Exception","Exception");
		        e.printStackTrace();
		        Assert.fail("fareAndSupplier failed: " + e.getMessage());
		    }
		}
		*/
	 private String safeGetText(List<WebElement> elements, int index) {
		    return (index < elements.size()) ? elements.get(index).getText() : "N/A";
		}
/*
		public void fareAndSupplier(int index, ExtentTest test) {
		    try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        WebElement flightCard = wait.until(ExpectedConditions.presenceOfElementLocated(
		            By.xpath("(//*[contains(@class,'one-way-new-result-card')])[" + index + "]")));

		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", flightCard);
		        test.log(Status.INFO, "Scrolled to flight card index: " + index);
                 Thread.sleep(3000);
		        List<WebElement> moreFaresList = flightCard.findElements(By.xpath(".//*[@xmlns='http://www.w3.org/2000/svg']/parent::div/span"));
		        if (!moreFaresList.isEmpty()) {
		            WebElement moreFaresElement = moreFaresList.get(moreFaresList.size() - 1);
		            
		            Thread.sleep(2000);
		            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", moreFaresElement);
		            
		            test.log(Status.INFO, "Clicked on More Fares for flight card index: " + index);
		        } else {
		            String noFareInfoMessage = "More fares not available for flight card index: " + index;
		            test.log(Status.INFO, noFareInfoMessage);
		            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.INFO, "Flight Fare Info", noFareInfoMessage); 
		        }
                  Thread.sleep(5000);
		        WebElement fareInfoCard = wait.until(ExpectedConditions.visibilityOf(
		            flightCard.findElement(By.xpath(".//*[contains(@class,'fare-info_details')]"))));

		        if (fareInfoCard.isDisplayed()) {
		            List<WebElement> supplierNames = fareInfoCard.findElements(By.xpath(".//*[@class='fare-component position-relative']//div[@class='fare-component_supplier-name']"));
		            List<WebElement> fares = fareInfoCard.findElements(By.xpath(".//*[@class='fare-component position-relative']//*[contains(@class,'fs-12 text-capitalize')]"));
		            List<WebElement> prices = fareInfoCard.findElements(By.xpath(".//*[@class='fare-component position-relative']//*[contains(@class,'fs-16 fw-600')]"));
		            List<WebElement> seatAvailable = fareInfoCard.findElements(By.xpath(".//*[@class='fare-component position-relative']//*[@class='seats-left']//*[contains(@class,'seats-left_text')]"));
		            List<WebElement> cabinBaggage = fareInfoCard.findElements(By.xpath(".//*[@class='fare-component position-relative']//*[@class='cabin-baggage']//*[contains(@class,'seats-left_text')]"));
		            List<WebElement> refundableFares = fareInfoCard.findElements(By.xpath(".//*[contains(@class,'refundable-info')]"));

		            int fareCount = supplierNames.size();

		            // Track fare+price per supplier to detect duplicates
		            Map<String, Set<String>> supplierFarePriceMap = new HashMap<>();

		            for (int i = 0; i < fareCount; i++) {
		                String supplier = supplierNames.get(i).getText().trim();
		                String fareType = safeGetText(fares, i).trim();
		                String priceText = safeGetText(prices, i).trim();
		                String seatText = safeGetText(seatAvailable, i).trim();
		                String baggage = safeGetText(cabinBaggage, i).trim();
		                String refundable = safeGetText(refundableFares, i).trim();

		                String farePriceKey = fareType + "|" + priceText;

		                supplierFarePriceMap.putIfAbsent(supplier, new HashSet<>());

		                if (!supplierFarePriceMap.get(supplier).contains(farePriceKey)) {
		                    supplierFarePriceMap.get(supplier).add(farePriceKey);

		                    String logMessage = String.format(
		                        "Supplier: %s | Fare: %s | Price: %s | Seats Left: %s | Cabin Baggage: %s | Refundable: %s",
		                        supplier, fareType, priceText, seatText, baggage, refundable);

		                    test.log(Status.INFO, logMessage);
		                  //  ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.PASS, "Flight Fare Info", "Flight Fare Info");
		                } else {
		                    // Duplicate fare and price for same supplier found
		                    String duplicateMessage = String.format(
		                        "Duplicate fare and price for same supplier: Supplier: %s | Fare: %s | Price: %s",
		                        supplier, fareType, priceText);

		                    test.log(Status.WARNING, duplicateMessage);
		                    // Optional: capture screenshot for duplicates
		                     ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Duplicate Fare Info", duplicateMessage);
		                     Assert.fail();
		                }
		            }
		        } else {
		            String noFareInfoMessage = "Fare info card not visible for flight card index: " + index;
		            test.log(Status.INFO, noFareInfoMessage);
		            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.INFO, "Flight Fare Info", noFareInfoMessage);
		            Assert.fail();
		        }

		    } catch (Exception e) {
		        test.log(Status.FAIL, "Exception occurred while extracting fare info: " + e.getMessage());
		        ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Exception","Exception");
		        e.printStackTrace();
		        Assert.fail("fareAndSupplier failed: " + e.getMessage());
		    }
		}
		*/
		public void fareAndSupplier(int index, ExtentTest test) {
		    try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        WebElement flightCard = wait.until(ExpectedConditions.presenceOfElementLocated(
		            By.xpath("(//*[contains(@class,'one-way-new-result-card')])[" + index + "]")));
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", flightCard);
		        test.log(Status.INFO, "Scrolled to flight card index: " + index);
		        Thread.sleep(2000);

		        // Click on 'More Fares' if available
		        List<WebElement> moreFaresButtons = flightCard.findElements(By.xpath(".//*[@xmlns='http://www.w3.org/2000/svg']/parent::div/span"));
		        if (!moreFaresButtons.isEmpty()) {
		            WebElement lastMoreFares = moreFaresButtons.get(moreFaresButtons.size() - 1);
		            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", lastMoreFares);
		            test.log(Status.INFO, "Clicked on 'More Fares' for index: " + index);
		            Thread.sleep(2000);
		        } else {
		            test.log(Status.INFO, "No 'More Fares' available for index: " + index);
		            return;
		        }

		        WebElement fareInfoCard = wait.until(ExpectedConditions.visibilityOf(
		            flightCard.findElement(By.xpath(".//*[contains(@class,'fare-info_details')]"))));
		        Thread.sleep(2000);

		        List<WebElement> suppliers = fareInfoCard.findElements(By.xpath(".//div[@class='fare-component_supplier-name']"));
		        List<WebElement> fareTypes = fareInfoCard.findElements(By.xpath(".//*[contains(@class,'fs-12 text-capitalize')]"));
		        List<WebElement> prices = fareInfoCard.findElements(By.xpath(".//*[contains(@class,'fs-16 fw-600')]"));

		        // To track duplicates for each supplier
		        Map<String, Set<String>> supplierFareSet = new HashMap<>();

		        for (int i = 0; i < suppliers.size(); i++) {
		            String supplier = (i < suppliers.size()) ? suppliers.get(i).getText().trim() : "Unknown Supplier";
		            String fare = (i < fareTypes.size()) ? fareTypes.get(i).getText().trim() : "N/A";
		            String price = (i < prices.size()) ? prices.get(i).getText().trim() : "N/A";

		            String fareKey = fare + "|" + price;

		            // Log all fare details
		            test.log(Status.INFO, "Supplier: " + supplier + " | Fare: " + fare + " | Price: " + price);

		            // Initialize fare set for supplier if not present
		            supplierFareSet.putIfAbsent(supplier, new HashSet<>());

		            // Validate duplicates (allowed only for "Fare Coupons")
		            if (!fare.equalsIgnoreCase("Fare Coupons")) {
		                if (supplierFareSet.get(supplier).contains(fareKey)) {
		                    // Duplicate found for same supplier — this is not allowed
		                    String error = "❌ Duplicate Fare & Price found for same Supplier: " + supplier + " | " + fareKey;
		                    test.log(Status.FAIL, error);
		                    ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Duplicate Fare", error);
		                    Assert.fail(error);
		                }
		            }

		            // Add fare key to supplier's set (for tracking)
		            supplierFareSet.get(supplier).add(fareKey);
		        }

		    } catch (Exception e) {
		        test.log(Status.FAIL, "Exception: " + e.getMessage());
		        ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Exception", "Something went wrong");
		        Assert.fail("fareAndSupplier failed: " + e.getMessage());
		    }
		}

/*
		public Map<String, String> clickRandomTboFareAndReturnDetails(ExtentTest test) {
		    Map<String, String> fareDetails = new HashMap<>();
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    JavascriptExecutor js = (JavascriptExecutor) driver;

		    try {
		        // Wait for fare blocks to load
		        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'fare-info_details')]")));

		        // Get all fare blocks that have supplier name as "TBO"
		        List<WebElement> allTboFares = driver.findElements(By.xpath(
		            "//div[contains(@class, 'fare-component')][.//div[contains(@class,'fare-component_supplier-name') and normalize-space()='TBO']]"
		        ));

		        if (allTboFares.isEmpty()) {
		            test.log(Status.FAIL, "No TBO fares found on the page.");
		            Assert.fail("No TBO fares found.");
		        }

		        // Pick random TBO fare
		        int randomIndex = new Random().nextInt(allTboFares.size());

		        WebElement selectedFare = allTboFares.get(randomIndex);

		        // Scroll into view
		        js.executeScript("arguments[0].scrollIntoView(true);", selectedFare);
		        Thread.sleep(500);

		        // Retry logic to handle stale element
		        int attempts = 0;
		        boolean clicked = false;

		        while (attempts < 3 && !clicked) {
		            try {
		                // Refetch element to avoid stale reference
		                allTboFares = driver.findElements(By.xpath(
		                    "//div[contains(@class, 'fare-component')][.//div[contains(@class,'fare-component_supplier-name') and normalize-space()='TBO']]"
		                ));
		                selectedFare = allTboFares.get(randomIndex);

		                WebElement radioButton = selectedFare.findElement(By.xpath(".//input[@type='radio']"));
		                js.executeScript("arguments[0].click();", radioButton);
		                clicked = true;
		            } catch (StaleElementReferenceException e) {
		                Thread.sleep(500);
		                attempts++;
		            }
		        }

		        // Fetch fare details from XPath
		        fareDetails.put("Fare Name", getSafeTextFromXPath(selectedFare, ".//span[contains(@class,'text-capitalize')]"));
		        fareDetails.put("Price", getSafeTextFromXPath(selectedFare, ".//span[contains(@class,'fs-16')]"));
		        fareDetails.put("Supplier", getSafeTextFromXPath(selectedFare, ".//div[contains(@class,'fare-component_supplier-name')]"));
		        fareDetails.put("Seats Left", getSafeTextFromXPath(selectedFare, ".//span[@class='seats-left_text']"));
		        fareDetails.put("Cabin Baggage", getSafeTextFromXPath(selectedFare, ".//span[contains(@class,'cabin-baggage')]//span[@class='seats-left_text']"));
		        fareDetails.put("Refundable", getSafeTextFromXPath(selectedFare, ".//span[contains(@class,'refundable-info')]"));

		        test.log(Status.PASS, "Selected Random TBO Fare: " + fareDetails);

		    } catch (Exception e) {
		        test.log(Status.FAIL, "Exception while selecting TBO fare: " + e.getMessage());
		        ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "TBO Fare Error", e.getMessage());
		        Assert.fail("clickRandomTboFareAndReturnDetails() failed: " + e.getMessage());
		    }

		    return fareDetails;
		}
*/
		public Map<String, String> clickTboFareForFlightIndex(int index, ExtentTest test) {
		    Map<String, String> fareDetails = new HashMap<>();
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    JavascriptExecutor js = (JavascriptExecutor) driver;

		    try {
		        // Get the flight card by index
		        WebElement flightCard = wait.until(ExpectedConditions.presenceOfElementLocated(
		            By.xpath("(//*[contains(@class,'one-way-new-result-card')])[" + index + "]")
		        ));
		        js.executeScript("arguments[0].scrollIntoView(true);", flightCard);
		        Thread.sleep(1000);

		        // Expand more fares if available
		        List<WebElement> moreFaresBtns = flightCard.findElements(By.xpath(".//*[@xmlns='http://www.w3.org/2000/svg']/parent::div/span"));
		        if (!moreFaresBtns.isEmpty()) {
		            WebElement lastMoreFares = moreFaresBtns.get(moreFaresBtns.size() - 1);
		            js.executeScript("arguments[0].click();", lastMoreFares);
		            test.log(Status.INFO, "Clicked on 'More Fares' for flight index: " + index);
		            Thread.sleep(1500);
		        } else {
		            test.log(Status.INFO, "No 'More Fares' button found for index: " + index);
		        }

		        // Get fare container inside this flight card
		        WebElement fareInfoCard = wait.until(ExpectedConditions.visibilityOf(
		            flightCard.findElement(By.xpath(".//*[contains(@class,'fare-info_details')]"))
		        ));
		        Thread.sleep(500);

		        // Find all TBO fares inside this fare card
		        List<WebElement> tboFares = fareInfoCard.findElements(By.xpath(
		            ".//div[contains(@class, 'fare-component')][.//div[contains(@class,'fare-component_supplier-name') and normalize-space()='TBO']]"
		        ));

		        if (tboFares.isEmpty()) {
		            test.log(Status.FAIL, "No TBO fares found for flight index " + index);
		            Assert.fail("No TBO fares found for index " + index);
		        }

		        // Select random or first TBO fare
		        WebElement selectedFare = tboFares.get(0); // use Random if needed

		        // Scroll and click radio button
		        js.executeScript("arguments[0].scrollIntoView(true);", selectedFare);
		        Thread.sleep(500);

		        int attempts = 0;
		        boolean clicked = false;
		        while (attempts < 3 && !clicked) {
		            try {
		                selectedFare = fareInfoCard.findElements(By.xpath(
		                    ".//div[contains(@class, 'fare-component')][.//div[contains(@class,'fare-component_supplier-name') and normalize-space()='TBO']]"
		                )).get(0);

		                WebElement radio = selectedFare.findElement(By.xpath(".//input[@type='radio']"));
		                js.executeScript("arguments[0].click();", radio);
		                clicked = true;
		            } catch (StaleElementReferenceException e) {
		                Thread.sleep(500);
		                attempts++;
		            }
		        }

		        // Extract fare details from within selected fare block
		        fareDetails.put("Fare Name", getSafeTextFromXPath(selectedFare, ".//span[contains(@class,'text-capitalize')]"));
		        fareDetails.put("Price", getSafeTextFromXPath(selectedFare, ".//span[contains(@class,'fs-16')]"));
		        fareDetails.put("Supplier", getSafeTextFromXPath(selectedFare, ".//div[contains(@class,'fare-component_supplier-name')]"));
		        fareDetails.put("Seats Left", getSafeTextFromXPath(selectedFare, ".//span[@class='seats-left_text']"));
		        fareDetails.put("Cabin Baggage", getSafeTextFromXPath(selectedFare, ".//span[contains(@class,'cabin-baggage')]//span[@class='seats-left_text']"));
		        fareDetails.put("Refundable", getSafeTextFromXPath(selectedFare, ".//span[contains(@class,'refundable-info')]"));

		        test.log(Status.PASS, "TBO fare selected for index " + index + ": " + fareDetails);

		    } catch (Exception e) {
		        test.log(Status.FAIL, "Exception while selecting TBO fare at index " + index + ": " + e.getMessage());
		        ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "TBO Fare Error", e.getMessage());
		        Assert.fail("clickTboFareForFlightIndex() failed: " + e.getMessage());
		    }

		    return fareDetails;
		}

		private String getSafeTextFromXPath(WebElement parent, String xpath) {
		    try {
		        return parent.findElement(By.xpath(xpath)).getText().trim();
		    } catch (Exception e) {
		        return "";
		    }
		}

		public void clickOnFlightDetails(ExtentTest test, int index) {
		    try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		        By flightDetailsLocator = By.xpath("(//*[text()='Flight Details'])[" + index + "]");

		        // Wait for presence
		        wait.until(ExpectedConditions.presenceOfElementLocated(flightDetailsLocator));

		        WebElement flightDetails = driver.findElement(flightDetailsLocator);

		        // Check visibility before clicking
		        if (flightDetails.isDisplayed()) {
		            flightDetails.click();
		            test.log(Status.PASS, "Clicked on Flight Details at index: " + index);
		        } else {
		            test.log(Status.INFO, "Flight Details element found but not visible at index: " + index);
		            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.INFO, "Flight Details not visible", "Element is present but not visible");
		        }

		    } catch (TimeoutException te) {
		        test.log(Status.FAIL, "Flight Details element not found at index " + index + " within timeout.");
		        ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Flight Details not found", "Element did not appear in time");
		    } catch (Exception e) {
		        test.log(Status.FAIL, "Exception while clicking Flight Details at index " + index + ": " + e.getMessage());
		        ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Flight Details click failed", "Unexpected error occurred");
		    }
		}



	 
}
