package com.iween.pageObjects;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.iween.utilities.ScreenshotUtil;

public class FlightDetailsPage extends BasePage{

	 // Constructor of loginPage calls the BasePage constructor with driver
    public FlightDetailsPage(WebDriver driver) {
        super(driver);// calls BasePage constructor
    }
        /*
    public void flightInformation(Map<String, String> userDetails, Map<String, String> flightDetail,Map<String, String>fareDetailSelected, ExtentTest test)
    {
    	String selectedTrip=userDetails.get("trip");
    	String fromLocation=userDetails.get("fromLocation");
    	String toLoaction=userDetails.get("toLocation");
    	String departDate=flightDetail.get("departureDate");
    	String airlineNames=flightDetail.get("airlineName");
    	String flightCode=flightDetail.get("flightCode");
    	String departureTime=flightDetail.get("departureTime");
    	String arrivalTime=flightDetail.get("arrivalTime");
    	String departureLocations=flightDetail.get("fromLocation");
    	String destinationLocation=flightDetail.get("destinationLocation");
    	String arrivalTimes=flightDetail.get("arrivalTime");
    	String arrivalDates=flightDetail.get("arrivalDate");
 
  
    	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        By flightDetailsLocator = By.xpath("//*[@class='selected-flight-details__trip-details']");
	        // Wait for presence
	        wait.until(ExpectedConditions.presenceOfElementLocated(flightDetailsLocator));
	        WebElement flightDetails = driver.findElement(flightDetailsLocator);
	        
	        if(flightDetails.isDisplayed())
	        {
	        	String fullText = driver.findElement(By.xpath("//*[@class='sector-span']")).getText(); 
	        	// e.g., "Ahmedabad(AMD) -> New Delhi(DEL)"

	        	// Split on "->"
	        	String[] parts = fullText.split("->");

	        	// Extract origin and destination with trimming and cleanup
	        	String origin = parts[0].trim().replaceAll("\\(.*?\\)", "").trim();      // Removes (AMD)
	        	String destination = parts[1].trim().replaceAll("\\(.*?\\)", "").trim(); // Removes (DEL)

	        	System.out.println("Origin: " + origin);         // Output: Ahmedabad
	        	System.out.println("Destination: " + destination); // Output: New Delhi

	        	String oneWayAndDate = driver.findElement(By.xpath("//*[@class='date-span']")).getText().trim();

	        	// Split by the first space-digit boundary (using regex)
	        	String[] part = oneWayAndDate.split("(?=\\d{2}\\s[A-Za-z]{3})");

	        	String tripType = part[0].trim();       // "One Way"
	        	String departureDate = part[1].trim();  // "04 Aug, 2025"

	        	System.out.println("Trip Type: " + tripType);       // Output: One Way
	        	System.out.println("Departure Date: " + departureDate); // Output: 04 Aug, 2025
	        	
	        	
	        	String airlineName=driver.findElement(By.xpath("//*[@class='primary-color']")).getText();
	        	String secterCode=driver.findElement(By.xpath("(//*[@class='primary-color']/following-sibling::span)[1]")).getText();
	        	String bookingClass=driver.findElement(By.xpath("(//*[@class='primary-color']/following-sibling::span)[1]")).getText();
	            String fare=driver.findElement(By.xpath("(//*[@class='primary-color']/parent::div/following-sibling::div)[1]")).getText();
	            String travelClass=driver.findElement(By.xpath("((//*[@class='primary-color']/parent::div/following-sibling::div)[2]/span)[1]")).getText();
	           String travelClassCode= driver.findElement(By.xpath("((//*[@class='primary-color']/parent::div/following-sibling::div)[2]/span)[2]")).getText();
	           
	           String text = driver.findElement(By.xpath("//*[@class='dark-grey-color fs-14 fw-600']")).getText();
	        // Example: "23:15 (04 Aug) - Ahmedabad - T1"

	        // Step 1: Extract time (everything before first space)
	        String departuretime = text.split(" ")[0];  // "23:15"

	        // Step 2: Extract date inside parentheses
	        String departuredate = text.substring(text.indexOf('(') + 1, text.indexOf(')'));  // "04 Aug"

	        // Step 3: Extract location (everything after ") - ")
	        String departurelocation = text.substring(text.indexOf(") - ") + 4).trim();  // "Ahmedabad - T1"

	        System.out.println("Time: " + departuretime);
	        System.out.println("Date: " + departuredate );
	        System.out.println("Location: " + departurelocation);

	        // Locate last element with given class
	        WebElement lastElement = driver.findElement(By.xpath("(//*[@class='dark-grey-color fs-14 fw-600'])[last()]"));
	        String texts = lastElement.getText();  // e.g. "23:15 (04 Aug) - Ahmedabad - T1"
	        
	        // Extract time
	        String arrivaltimes = texts.split(" ")[0]; 
	        
	        // Extract date between parentheses
	        String arrivaldates = texts.substring(texts.indexOf('(') + 1, texts.indexOf(')'));
	        
	        // Extract location after ") - "
	        String  destinationLocations = texts.substring(texts.indexOf(") - ") + 4).trim();
	        
	        String durationText = driver.findElement(By.xpath("//*[@class='fs-12 grey-color mb-2']")).getText();
	     // Example: "Travel Time: 1h 55m"

	     // Split on colon and take second part, then trim
	     String travelDuration = durationText.split(":")[1].trim();

	     System.out.println("Travel Duration: " + travelDuration);  // Output: 1h 55m

	           String departureFullLocation= driver.findElement(By.xpath("//*[@class='fs-10 mb-2']")).getText();
	           String arrivalFullLocation= driver.findElement(By.xpath("(//*[@class='fs-10'])[last()]")).getText();
	           
	           String checkIn=driver.findElement(By.xpath("//*[@class='selected-flight-details__flight-leg_amenities']/span")).getText();
	           
	        	if(origin.equals(fromLocation) && destination.equals(toLoaction) && tripType.equals(selectedTrip) && departureDate.equals(departDate) )
	        	{
	        		 test.log(Status.PASS, "Flights Information In Header Displaying Properly ");
	        		
	        	}
	        	else
	        	{
	        		
	        	}
	           
	        	if(airlineName.equals(airlineNames) && secterCode.equals(flightCode) && departuretime.equals(departureTime) &&  
	        			arrivaltimes.equals(arrivalTime) && departuredate.equals(departDate) && 	arrivalDates.equals(arrivaldates)
	        			&& origin.equals(departureLocations) && destination.equals(destinationLocation)
	        			)
	        	{
	        		test.log(Status.PASS, "Flights Information In  Displaying Properly ");
	        	}
	        	else
	        	{
	        		 System.out.println("Flights are not displaying for selected route");
			            test.log(Status.FAIL, "Flights are not displaying for selected route");
			            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "No flights found", "Flights list is empty");
			            Assert.fail();
	        	}
	        }
	        else
	        {
	        	
	        }
	        

    }
    */
    public void flightInformation(
            Map<String, String> userDetails,
            Map<String, String> flightDetail,
            Map<String, String> fareDetailSelected,
            ExtentTest test) {

        try {
            // Extract user-provided details
            String selectedTrip = userDetails.get("trip");
            String fromLocation = userDetails.get("fromLocation");
            String toLocation = userDetails.get("toLocation");
            String expectedDepartDate = flightDetail.get("departureDate");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            By flightDetailsLocator = By.xpath("//*[@class='selected-flight-details__trip-details']");
            wait.until(ExpectedConditions.presenceOfElementLocated(flightDetailsLocator));
            WebElement flightDetails = driver.findElement(flightDetailsLocator);

            if (!flightDetails.isDisplayed()) {
                test.log(Status.FAIL, "Flight details section not visible.");
                return;
            }

            // Extract displayed data
            String[] routeParts = driver.findElement(By.xpath("//*[@class='sector-span']")).getText().split("->");
            String origin = routeParts[0].trim().replaceAll("\\(.*?\\)", "").trim();
            String destination = routeParts[1].trim().replaceAll("\\(.*?\\)", "").trim();
            

            String[] tripInfo = driver.findElement(By.xpath("//*[@class='date-span']")).getText().trim()
                    .split("(?=\\d{2}\\s[A-Za-z]{3})");
            String tripType = tripInfo[0].trim();
            String actualDepartDate = tripInfo[1].trim();

            String airlineName = driver.findElement(By.xpath("//*[@class='primary-color']")).getText();
            //Secter code
            List<WebElement> flightCodeElements = driver.findElements(By.xpath("//*[@class='primary-color']/following-sibling::span[1]"));
            StringBuilder flightsCodeBuilder = new StringBuilder();

            for (int i = 0; i < flightCodeElements.size(); i++) { // AI-2811 + AI-2732


                String code = flightCodeElements.get(i).getText().trim();
                flightsCodeBuilder.append(code);

                if (i < flightCodeElements.size() - 1) {
                    flightsCodeBuilder.append(", ");
                }
            }

            String flightsCode = flightsCodeBuilder.toString();//AI-2811, AI-2732
            System.out.println("All Flight Codes: " + flightsCode);


            String departureText = driver.findElement(By.xpath("//*[@class='dark-grey-color fs-14 fw-600']")).getText();
            String departureTime = departureText.split(" ")[0];
            String departureDate = departureText.substring(departureText.indexOf('(') + 1, departureText.indexOf(')'));
          //  String departureLocation = departureText.substring(departureText.indexOf(") - ") + 4).trim();
            String departureLocation = departureText.split("\\) - ")[1];
            System.out.println(departureLocation);

            WebElement lastElement = driver.findElement(By.xpath("(//*[@class='dark-grey-color fs-14 fw-600'])[last()]"));
            String arrivalText = lastElement.getText();
            String arrivalTime = arrivalText.split(" ")[0];
            String arrivalDate = arrivalText.substring(arrivalText.indexOf('(') + 1, arrivalText.indexOf(')'));
            String arrivalLocation = arrivalText.substring(arrivalText.indexOf(") - ") + 4).trim();

            // === Header Validation ===
            boolean headerMatch = true;
            if (!origin.equalsIgnoreCase(fromLocation)) {
                test.log(Status.FAIL, "From Location mismatch: expected '" + fromLocation + "', but got '" + origin + "'");
                headerMatch = false;
            }
            if (!destination.equalsIgnoreCase(toLocation)) {
                test.log(Status.FAIL, "To Location mismatch: expected '" + toLocation + "', but got '" + destination + "'");
                headerMatch = false;
            }
            if (!tripType.equalsIgnoreCase(selectedTrip)) {
                test.log(Status.FAIL, "Trip Type mismatch: expected '" + selectedTrip + "', but got '" + tripType + "'");
                headerMatch = false;
            }
            if (!actualDepartDate.equalsIgnoreCase(expectedDepartDate)) {
                test.log(Status.FAIL, "Departure Date mismatch: expected '" + expectedDepartDate + "', but got '" + actualDepartDate + "'");
                headerMatch = false;
            }

            if (headerMatch) {
                test.log(Status.PASS, "Flight header info matches user selection.");
            }

            // === Detailed Info Validation ===
            boolean detailMatch = true;

            if (!airlineName.equalsIgnoreCase(flightDetail.get("airlineName"))) {
                test.log(Status.FAIL, "Airline Name mismatch: expected '" + flightDetail.get("airlineName") + "', but got '" + airlineName + "'");
                detailMatch = false;
            }
            
            System.out.println(flightsCode +" "+flightDetail.get("flightCode"));
            if (!flightsCode.equalsIgnoreCase(flightDetail.get("flightCode"))) {
                test.log(Status.FAIL, "Flight Code mismatch: expected '" + flightDetail.get("flightCode") + "', but got '" + flightsCode + "'");
                detailMatch = false;
            }
            if (!departureTime.equalsIgnoreCase(flightDetail.get("departureTime"))) {
                test.log(Status.FAIL, "Departure Time mismatch: expected '" + flightDetail.get("departureTime") + "', but got '" + departureTime + "'");
                detailMatch = false;
            }
            if (!arrivalTime.equalsIgnoreCase(flightDetail.get("arrivalTime"))) {
                test.log(Status.FAIL, "Arrival Time mismatch: expected '" + flightDetail.get("arrivalTime") + "', but got '" + arrivalTime + "'");
                detailMatch = false;
            }
            System.out.println(departureDate+" "+flightDetail.get("departureDate"));
            if (!departureDate.equalsIgnoreCase(flightDetail.get("departureDate"))) {
                test.log(Status.FAIL, "Departure Date mismatch: expected '" + flightDetail.get("departureDate") + "', but got '" + departureDate + "'");
                detailMatch = false;
            }
            System.out.println(arrivalDate+" "+flightDetail.get("arrivalDate"));
            if (!arrivalDate.equalsIgnoreCase(flightDetail.get("arrivalDate"))) {
                test.log(Status.FAIL, "Arrival Date mismatch: expected '" + flightDetail.get("arrivalDate") + "', but got '" + arrivalDate + "'");
                detailMatch = false;
            }
            System.out.println(departureLocation+" "+flightDetail.get("fromLocation"));
            if (!departureLocation.equalsIgnoreCase(flightDetail.get("fromLocation"))) {
                test.log(Status.FAIL, "Flight From Location mismatch: expected '" + flightDetail.get("fromLocation") + "', but got '" + origin + "'");
                detailMatch = false;
            }
            System.out.println(arrivalLocation+" "+flightDetail.get("destinationLocation"));
            if (!arrivalLocation.equalsIgnoreCase(flightDetail.get("destinationLocation"))) {
                test.log(Status.FAIL, "Flight Destination Location mismatch: expected '" + flightDetail.get("destinationLocation") + "', but got '" + destination + "'");
                detailMatch = false;
            }

            if (detailMatch) {
                test.log(Status.PASS, "Detailed flight information matches expected values.");
            } else {
                ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Flight info mismatch", "One or more flight details did not match.");
                Assert.fail("Mismatch in detailed flight information.");
            }

        } catch (Exception e) {
            test.log(Status.FAIL, "Exception while validating flight information: " + e.getMessage());
            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Exception occurred", "Error while checking flight details.");
            Assert.fail("Exception in flightInformation(): " + e.getMessage());
        }
    }

        
        
        
    }

