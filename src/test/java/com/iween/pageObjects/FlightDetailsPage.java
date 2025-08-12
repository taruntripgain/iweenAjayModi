package com.iween.pageObjects;

import java.time.Duration;
import java.util.HashMap;
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
    /*
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
*/
    /*
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
            // Sector code
            List<WebElement> flightCodeElements = driver.findElements(By.xpath("//*[@class='primary-color']/following-sibling::span[1]"));
            StringBuilder flightsCodeBuilder = new StringBuilder();

            for (int i = 0; i < flightCodeElements.size(); i++) {
                String code = flightCodeElements.get(i).getText().trim();
                flightsCodeBuilder.append(code);
                if (i < flightCodeElements.size() - 1) {
                    flightsCodeBuilder.append(", ");
                }
            }

            String flightsCode = flightsCodeBuilder.toString(); // e.g. "AI-2811, AI-2732"
            System.out.println("All Flight Codes: " + flightsCode);

            String departureText = driver.findElement(By.xpath("//*[@class='dark-grey-color fs-14 fw-600']")).getText();
            String departureTime = departureText.split(" ")[0];
            String departureDate = departureText.substring(departureText.indexOf('(') + 1, departureText.indexOf(')'));
            String departureLocation = departureText.split("\\) - ")[1].trim();
            System.out.println(departureLocation);

            WebElement lastElement = driver.findElement(By.xpath("(//*[@class='dark-grey-color fs-14 fw-600'])[last()]"));
            String arrivalText = lastElement.getText();
            String arrivalTime = arrivalText.split(" ")[0];
            String arrivalDate = arrivalText.substring(arrivalText.indexOf('(') + 1, arrivalText.indexOf(')'));
            String arrivalLocation = arrivalText.substring(arrivalText.indexOf(") - ") + 4).trim();

            // === Header Validation ===
            boolean headerMatch = true;
            if (!extractCityName(origin).equalsIgnoreCase(extractCityName(fromLocation))) {
                test.log(Status.FAIL, "From Location mismatch: expected '" + extractCityName(fromLocation) + "', but got '" + extractCityName(origin) + "'");
                headerMatch = false;
            }
            if (!extractCityName(destination).equalsIgnoreCase(extractCityName(toLocation))) {
                test.log(Status.FAIL, "To Location mismatch: expected '" + extractCityName(toLocation) + "', but got '" + extractCityName(destination) + "'");
                headerMatch = false;
            }
            if (!tripType.equalsIgnoreCase(selectedTrip)) {
                test.log(Status.FAIL, "Trip Type mismatch: expected '" + selectedTrip + "', but got '" + tripType + "'");
                headerMatch = false;
            }
            if (!normalizeDate(actualDepartDate).equalsIgnoreCase(normalizeDate(expectedDepartDate))) {
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

            System.out.println(flightsCode + " " + flightDetail.get("flightCode"));
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
            
            System.out.println(departureDate + " " + flightDetail.get("departureDate"));
            if (!normalizeDate(departureDate).equalsIgnoreCase(normalizeDate(flightDetail.get("departureDate")))) {
                test.log(Status.FAIL, "Departure Date mismatch: expected '" + flightDetail.get("departureDate") + "', but got '" + departureDate + "'");
                detailMatch = false;
            }
            
            System.out.println(arrivalDate + " " + flightDetail.get("arrivalDate"));
            if (!normalizeDate(arrivalDate).equalsIgnoreCase(normalizeDate(flightDetail.get("arrivalDate")))) {
                test.log(Status.FAIL, "Arrival Date mismatch: expected '" + flightDetail.get("arrivalDate") + "', but got '" + arrivalDate + "'");
                detailMatch = false;
            }
            System.out.println(departureLocation + " " + flightDetail.get("fromLocation"));
            if (!extractCityName(departureLocation).equalsIgnoreCase(extractCityName(flightDetail.get("fromLocation")))) {
                test.log(Status.FAIL, "Flight From Location mismatch: expected '" + extractCityName(flightDetail.get("fromLocation")) + "', but got '" + extractCityName(departureLocation) + "'");
                detailMatch = false;
            }
            System.out.println(arrivalLocation + " " + flightDetail.get("destinationLocation"));
            if (!extractCityName(arrivalLocation).equalsIgnoreCase(extractCityName(flightDetail.get("destinationLocation")))) {
                test.log(Status.FAIL, "Flight Destination Location mismatch: expected '" + extractCityName(flightDetail.get("destinationLocation")) + "', but got '" + extractCityName(arrivalLocation) + "'");
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

    // Helper method to extract city name from location string  eg:Mumbai - T2-->  Mumbai it will remove terminal or Bangalore - T2 Bangalore-2 
    private String extractCityName(String location) {
        if (location == null || location.isEmpty()) return "";
        return location.split("\\s*-\\s*")[0].trim();
    }

    // Helper method to normalize date string by removing year part  eg:07 Aug 07 Aug, 2025 -->07 Aug 07 Aug remove , 2025
    private String normalizeDate(String date) {
        if (date == null) return "";
        return date.replaceAll(",\\s*\\d{4}", "").trim();
    }

     */
    public void flightInformation(
            Map<String, String> userDetails,
            Map<String, String> flightDetail,
            Map<String, String> fareDetailSelected,
            ExtentTest test) {

        try {
            String selectedTrip = userDetails.get("trip");
            String fromLocation = userDetails.get("fromLocation");
            String toLocation = userDetails.get("toLocation");
            String expectedDepartDate = flightDetail.get("departureDate");
           String cabinDetails= fareDetailSelected.get("Cabin Baggage");
           System.out.println(cabinDetails);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            By flightDetailsLocator = By.xpath("//*[@class='selected-flight-details__trip-details']");
            wait.until(ExpectedConditions.presenceOfElementLocated(flightDetailsLocator));
            WebElement flightDetails = driver.findElement(flightDetailsLocator);

            if (!flightDetails.isDisplayed()) {
                test.log(Status.FAIL, "Flight details section not visible.");
                return;
            }
           //baggage
            List<WebElement> baggageElements = driver.findElements(By.xpath("//*[@class='selected-flight-details__flight-leg_amenities']/span"));

            boolean allMatched = true;

            for (int i = 0; i < baggageElements.size(); i++) {
                String fullText = baggageElements.get(i).getText().trim(); // Example: "Check In: 15 KG"

                // Extract only value part (e.g., "15 KG")
                String actualValue = fullText.contains(":") ? fullText.split(":")[1].trim() : fullText;

                if (actualValue.equalsIgnoreCase(cabinDetails)) {
                    test.log(Status.PASS, "Cabin Baggage Match [" + (i + 1) + "]: Expected = '" + cabinDetails + "', Actual = '" + actualValue + "'");
                } else {
                    test.log(Status.FAIL, "Cabin Baggage Mismatch [" + (i + 1) + "]: Expected = '" + cabinDetails + "', but got '" + actualValue + "'");
                    allMatched = false;
                }
            }

            if (!allMatched) {
                ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Cabin Baggage Mismatch", "One or more baggage values did not match.");
            } else {
                test.log(Status.PASS, "✅ All cabin baggage values matched: '" + cabinDetails + "'");
            }

            
            
            
            String[] routeParts = driver.findElement(By.xpath("//*[@class='sector-span']")).getText().split("->");
            String origin = routeParts[0].trim().replaceAll("\\(.*?\\)", "").trim();
            String destination = routeParts[1].trim().replaceAll("\\(.*?\\)", "").trim();

            String[] tripInfo = driver.findElement(By.xpath("//*[@class='date-span']")).getText().trim()
                    .split("(?=\\d{2}\\s[A-Za-z]{3})");
            String tripType = tripInfo[0].trim();
            String actualDepartDate = tripInfo[1].trim();

            String airlineName = driver.findElement(By.xpath("//*[@class='primary-color']")).getText();
            List<WebElement> flightCodeElements = driver.findElements(By.xpath("//*[@class='primary-color']/following-sibling::span[1]"));
            StringBuilder flightsCodeBuilder = new StringBuilder();

            for (int i = 0; i < flightCodeElements.size(); i++) {
                String code = flightCodeElements.get(i).getText().trim();
                flightsCodeBuilder.append(code);
                if (i < flightCodeElements.size() - 1) {
                    flightsCodeBuilder.append(", ");
                }
            }

            String flightsCode = flightsCodeBuilder.toString();
            System.out.println("All Flight Codes: " + flightsCode);

            String departureText = driver.findElement(By.xpath("//*[@class='dark-grey-color fs-14 fw-600']")).getText();
            String departureTime = departureText.split(" ")[0];
            String departureDate = departureText.substring(departureText.indexOf('(') + 1, departureText.indexOf(')'));
            String departureLocation = departureText.split("\\) - ")[1].trim();

            WebElement lastElement = driver.findElement(By.xpath("(//*[@class='dark-grey-color fs-14 fw-600'])[last()]"));
            String arrivalText = lastElement.getText();
            String arrivalTime = arrivalText.split(" ")[0];
            String arrivalDate = arrivalText.substring(arrivalText.indexOf('(') + 1, arrivalText.indexOf(')'));
            String arrivalLocation = arrivalText.substring(arrivalText.indexOf(") - ") + 4).trim();

            boolean hasFailures = false;

            // Header comparisons
            if (!logComparison("From Location", extractCityName(origin), extractCityName(fromLocation), test)) {
                hasFailures = true;
            }
            if (!logComparison("To Location", extractCityName(destination), extractCityName(toLocation), test)) {
                hasFailures = true;
            }
            if (!logComparison("Trip Type", tripType, selectedTrip, test)) {
                hasFailures = true;
            }
            if (!logComparison("Departure Date", normalizeDate(actualDepartDate), normalizeDate(expectedDepartDate), test)) {
                hasFailures = true;
            }

            // Details comparisons
            if (!logComparison("Airline Name", airlineName, flightDetail.get("airlineName"), test)) {
                hasFailures = true;
            }
            if (!logComparison("Flight Code", flightsCode, flightDetail.get("flightCode"), test)) {
                hasFailures = true;
            }
            if (!logComparison("Departure Time", departureTime, flightDetail.get("departureTime"), test)) {
                hasFailures = true;
            }
            if (!logComparison("Arrival Time", arrivalTime, flightDetail.get("arrivalTime"), test)) {
                hasFailures = true;
            }
            if (!logComparison("Departure Date", normalizeDate(departureDate), normalizeDate(flightDetail.get("departureDate")), test)) {
                hasFailures = true;
            }
            if (!logComparison("Arrival Date", normalizeDate(arrivalDate), normalizeDate(flightDetail.get("arrivalDate")), test)) {
                hasFailures = true;
            }
            if (!logComparison("Flight From Location", extractCityName(departureLocation), extractCityName(flightDetail.get("fromLocation")), test)) {
                hasFailures = true;
            }
            if (!logComparison("Flight Destination Location", extractCityName(arrivalLocation), extractCityName(flightDetail.get("destinationLocation")), test)) {
                hasFailures = true;
            }

            if (hasFailures) {
                ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Flight info mismatch", "One or more flight details did not match.");
                Assert.fail("Mismatch in flight information.");
            } else {
                test.log(Status.PASS, "All flight details matched expected values in Flight Information .");
            }

           
            
        } catch (Exception e) {
            test.log(Status.FAIL, "Exception while validating flight information: " + e.getMessage());
            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Exception occurred", "Error while checking flight details.");
            Assert.fail("Exception in flightInformation(): " + e.getMessage());
        }
    }

    // Logs comparison result with detailed reporting
    private boolean logComparison(String field, String actual, String expected, ExtentTest test) {
        if (actual == null) actual = "";
        if (expected == null) expected = "";

        if (actual.equalsIgnoreCase(expected)) {
            test.log(Status.PASS, field + " matched: Expected = '" + expected + "', Actual = '" + actual + "'");
            return true;
        } else {
            test.log(Status.FAIL, field + " mismatch: Expected = '" + expected + "', but got '" + actual + "'");
            return false;
        }
    }

    // Helper to extract city from "City - Terminal" format
    private String extractCityName(String location) {
        if (location == null || location.isEmpty()) return "";
        return location.split("\\s*-\\s*")[0].trim();
    }

    // Helper to remove ", 2025" from date strings for comparison
    private String normalizeDate(String date) {
        if (date == null) return "";
        return date.replaceAll(",\\s*\\d{4}", "").trim();
    }

    
    
    public Map<String, String> getFlightDetailsForNonStop() {
        Map<String, String> flightDetails = new HashMap<>();

        try {
            flightDetails.put("HeaderDetails", driver.findElement(By.xpath("//div[contains(@class,'selected-flight-details__trip-details')]")).getText());
            flightDetails.put("FlightName", driver.findElement(By.xpath("//*[@class='primary-color']")).getText());
            flightDetails.put("FlightCode", driver.findElement(By.xpath("(//*[@class='primary-color']/following-sibling::span)[1]")).getText());
            flightDetails.put("BookingClass", driver.findElement(By.xpath("(//*[@class='primary-color']/following-sibling::span)[2]")).getText());
            flightDetails.put("Fare", driver.findElement(By.xpath("(//*[@class='primary-color']/parent::div/following-sibling::div)[1]")).getText());
            flightDetails.put("TravelClass", driver.findElement(By.xpath("(//*[@class='primary-color']/parent::div/following-sibling::div)[2]")).getText());
            flightDetails.put("Origin", driver.findElement(By.xpath("(//*[@class='dark-grey-color fs-14 fw-600'])[1]")).getText());
            flightDetails.put("Destination", driver.findElement(By.xpath("(//*[@class='dark-grey-color fs-14 fw-600'])[2]")).getText());
            flightDetails.put("OriginLocation", driver.findElement(By.xpath("//*[@class='fs-10 mb-2']")).getText());
            flightDetails.put("DestinationLocation", driver.findElement(By.xpath("(//*[@class='dark-grey-color fs-14 fw-600'])[2]/following-sibling::span")).getText());
            flightDetails.put("Duration", driver.findElement(By.xpath("//*[@class='fs-12 grey-color mb-2']")).getText());
            flightDetails.put("Baggage", driver.findElement(By.xpath("//*[@class='selected-flight-details__flight-leg_amenities']/span")).getText());

        } catch (Exception e) {
            System.out.println("Error while fetching flight details: " + e.getMessage());
            e.printStackTrace();
        }

        return flightDetails;
    }
    
    public void clickOnFareBreakUp(ExtentTest test) {
        try {
            WebElement fareBreakUpTab = driver.findElement(By.xpath("//*[text()='Fare Breakup']"));
            
            if (fareBreakUpTab.isDisplayed() && fareBreakUpTab.isEnabled()) {
                fareBreakUpTab.click();
                test.log(Status.INFO, "Clicked on Fare Break Up Tab");
            } else {
                test.log(Status.FAIL, "Fare Breakup tab is not displayed or not clickable.");
                ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Element not interactable", "Fare Breakup tab issue.");
            }
            
        } catch (Exception e) {
            test.log(Status.FAIL, "Exception occurred while clicking on Fare Breakup tab: " + e.getMessage());
            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Exception occurred", "Error while checking flight details.");
            e.printStackTrace();
        }
    }

    public void validateFareBreakUpPage(ExtentTest test) {
        try {
            WebElement fareBreakup = driver.findElement(By.xpath("//*[@class='fare-breakup_header']"));
            
            if (fareBreakup.isDisplayed()) {
                test.log(Status.INFO, "Fare Break Up is displayed successfully.");
            } else {
                test.log(Status.FAIL, "Fare Break Up section is not visible.");
                ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Element not visible", "Fare Breakup section is not displayed.");
            }
            
        } catch (Exception e) {
            test.log(Status.FAIL, "Exception occurred while validating Fare Break Up page: " + e.getMessage());
            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Exception occurred", "Error while validating Fare Breakup page.");
            e.printStackTrace();
        }
    }

    public void validateFareBreakUpPagePrice(Map<String, String> fareDetails, ExtentTest test) {
        try {
            String farePrice = fareDetails.get("Price");
            String grandTotal = driver.findElement(By.xpath("//*[text()='Grand Total']/following-sibling::div")).getText();

            if (grandTotal.equalsIgnoreCase(farePrice)) {
                test.log(Status.PASS, "User selected fare price matches: " + grandTotal);
            } else {
                test.log(Status.FAIL, "Fare price mismatch. Expected: " + farePrice + ", Found: " + grandTotal);
                ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Price mismatch", "Fare price does not match Grand Total.");
            }

        } catch (Exception e) {
            test.log(Status.FAIL, "Exception occurred while validating fare breakup price: " + e.getMessage());
            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Exception occurred", "Error while comparing fare price.");
            e.printStackTrace();
        }
    }
  
    public void clickOnBaggage(ExtentTest test) {
        try {
            WebElement baggage = driver.findElement(By.xpath("//*[text()='Baggage']"));
            
            if (baggage.isDisplayed() && baggage.isEnabled()) {
            	baggage.click();
                test.log(Status.INFO, "Clicked on Baggage Tab");
            } else {
                test.log(Status.FAIL, "Baggage Tab is not displayed or not clickable.");
                ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Element not interactable", "Baggage Tab issue.");
            }
            
        } catch (Exception e) {
            test.log(Status.FAIL, "Exception occurred while clicking on Baggage Tab: " + e.getMessage());
            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Exception occurred", "Error while checking Baggage Tab.");
            e.printStackTrace();
        }
    }
    
    public void validateBaggagePage(ExtentTest test) {
        try {
            WebElement baggage = driver.findElement(By.xpath("//*[@class='baggage-table_value']"));
            
            if (baggage.isDisplayed()) {
                test.log(Status.INFO, "baggage page  is displayed successfully.");
            } else {
                test.log(Status.FAIL, "baggage page section is not visible.");
                ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Element not visible", "baggage page section is not displayed.");
            }
            
        } catch (Exception e) {
            test.log(Status.FAIL, "Exception occurred while validating baggage page: " + e.getMessage());
            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Exception occurred", "Error while validating baggage page.");
            e.printStackTrace();
        }
    }
/*
    public void validateBaggage(Map<String, String> fareDetails, ExtentTest test) {
        try {
            String CabinBaggage = fareDetails.get("Cabin Baggage");
            String checkinBaggageRaw = driver.findElement(By.xpath("//*[text()='Check In baggage']/following-sibling::span")).getText();
            String checkinBaggage = checkinBaggageRaw.split("-")[1].trim();  // Output: "15 KG"

                 System.out.println(checkinBaggage+" "+CabinBaggage);
            if (checkinBaggage.equalsIgnoreCase(CabinBaggage)) {
                test.log(Status.PASS, "checkin Baggage matches: " + checkinBaggage);
            } else {
                test.log(Status.FAIL, "CheckInBaggage  mismatch. Expected: " + CabinBaggage + ", Found: " + checkinBaggage);
                ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "CheckInBaggage Not Match", "CheckInBaggage Not Match.");
            }

        } catch (Exception e) {
            test.log(Status.FAIL, "Exception occurred while validating CheckInBaggage: " + e.getMessage());
            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Exception occurred", "Error while comparing CheckInBaggage.");
            e.printStackTrace();
        }
    }
    */
    /*
    public void validateBaggage(Map<String, String> fareDetails, ExtentTest test) {
        try {
            String expectedWeight = fareDetails.get("Cabin Baggage");  // e.g., "15 KG"
            String checkinBaggageRaw = driver.findElement(By.xpath("//*[text()='Check In baggage']/following-sibling::span")).getText();
            // Example: "ADT-15 KG CHD-15 KG INF-0 KG" or "ADT-15 KG CHD-15 KG" or "ADT-15 KG CHD-15 KG INF-5 KG"

            // Split by spaces to separate each segment (assuming format "ADT-15 KG CHD-15 KG INF-0 KG")
            String[] parts = checkinBaggageRaw.split("\\s+");

            Map<String, String> baggageMap = new HashMap<>();

            for (int i = 0; i < parts.length - 1; i += 2) {
                String paxAndWeight = parts[i];  // e.g., "ADT-15"
                System.out.println(paxAndWeight);
                String kg = parts[i + 1];        // e.g., "KG"
                System.out.println(kg);
                String[] paxSplit = paxAndWeight.split("-");
                if (paxSplit.length == 2) {
                    String paxType = paxSplit[0];            // e.g., "ADT"
                    System.out.println(paxType);
                    String weight = paxSplit[1] + " " + kg; // e.g., "15 KG"
                    System.out.println(weight);
                    baggageMap.put(paxType, weight);
                }
            }

            boolean allMatch = true;

            // Check Adult baggage
            String adultWeight = baggageMap.get("ADT");
            System.out.println(adultWeight);
            if (adultWeight == null || !adultWeight.equalsIgnoreCase(expectedWeight)) {
                test.log(Status.FAIL, "Adult baggage mismatch. Expected: " + expectedWeight + ", Found: " + adultWeight);
                allMatch = false;
            } else {
                test.log(Status.PASS, "Adult baggage matches: " + adultWeight);
            }

            // Check Child baggage
            String childWeight = baggageMap.get("CHD");
            if (childWeight == null || !childWeight.equalsIgnoreCase(expectedWeight)) {
                test.log(Status.FAIL, "Child baggage mismatch. Expected: " + expectedWeight + ", Found: " + childWeight);
                allMatch = false;
            } else {
                test.log(Status.PASS, "Child baggage matches: " + childWeight);
            }

            // Check Infant baggage - **must be 0 KG or no baggage allowed**
            String infantWeight = baggageMap.get("INF");
            System.out.println(infantWeight);
            if (infantWeight != null && !infantWeight.equalsIgnoreCase("0 KG")) {
                test.log(Status.FAIL, "Infant baggage NOT allowed but found: " + infantWeight);
                allMatch = false;
            } else {
                test.log(Status.PASS, "Infant baggage correctly not allowed or 0 KG.");
            }

            if (!allMatch) {
                ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "CheckInBaggage Not Match", "Baggage weights do not match expected values.");
                Assert.fail("Baggage weight validation failed.");
            }

        } catch (Exception e) {
            test.log(Status.FAIL, "Exception occurred while validating CheckInBaggage: " + e.getMessage());
            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Exception occurred", "Error while comparing CheckInBaggage.");
            e.printStackTrace();
            Assert.fail("Exception in validateBaggage: " + e.getMessage());
        }
    }
*/
    public void validateBaggage(Map<String, String> fareDetails, ExtentTest test) {
        try {
            String expectedWeight = fareDetails.get("Cabin Baggage");  // e.g., "15 KG"
            String checkinBaggageRaw = driver.findElement(By.xpath("//*[text()='Check In baggage']/following-sibling::span")).getText();
            // Example: "ADT-15 KG CHD-15 KG INF-0 KG" or "ADT-15 KG CHD-15 KG"

            String[] parts = checkinBaggageRaw.split("\\s+");
            Map<String, String> baggageMap = new HashMap<>();

            for (int i = 0; i < parts.length - 1; i += 2) {
                String paxAndWeight = parts[i];         // e.g., "ADT-15"
                String kg = parts[i + 1];               // e.g., "KG"
                String[] paxSplit = paxAndWeight.split("-");
                if (paxSplit.length == 2) {
                    String paxType = paxSplit[0];       // e.g., "ADT"
                    String weight = paxSplit[1] + " " + kg; // e.g., "15 KG"
                    baggageMap.put(paxType, weight);
                }
            }

            boolean allMatch = true;

            // Validate ADT
            String adultWeight = baggageMap.get("ADT");
            if (adultWeight == null || !adultWeight.equalsIgnoreCase(expectedWeight)) {
                test.log(Status.FAIL, "❌ Adult baggage mismatch. Expected: " + expectedWeight + ", Found: " + adultWeight);
                allMatch = false;
            } else {
                test.log(Status.PASS, "✅ Adult baggage matches: " + adultWeight);
            }

            // Validate CHD
            String childWeight = baggageMap.get("CHD");
            if (childWeight == null || !childWeight.equalsIgnoreCase(expectedWeight)) {
                test.log(Status.FAIL, "❌ Child baggage mismatch. Expected: " + expectedWeight + ", Found: " + childWeight);
                allMatch = false;
            } else {
                test.log(Status.PASS, "✅ Child baggage matches: " + childWeight);
            }

            // Validate INF only if present in UI
            String infantWeight = baggageMap.get("INF");
            if (infantWeight != null) {
                if (!infantWeight.equalsIgnoreCase("0 KG")) {
                    test.log(Status.FAIL, "❌ Infant baggage not allowed. Found: " + infantWeight);
                    allMatch = false;
                } else {
                    test.log(Status.PASS, "✅ Infant baggage correctly set to 0 KG.");
                }
            }
            
            // Final assertion and screenshot if anything fails
            if (!allMatch) {
                ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "CheckInBaggage Not Match", "Baggage weights do not match expected values.");
                Assert.fail("Baggage weight validation failed.");
            }

        } catch (Exception e) {
            test.log(Status.FAIL, "Exception occurred while validating CheckInBaggage: " + e.getMessage());
            ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Exception occurred", "Error while comparing CheckInBaggage.");
            e.printStackTrace();
            Assert.fail("Exception in validateBaggage: " + e.getMessage());
        }
    }

    
    
    public Map<String, String> getFlightDetailsForOneStop() {
        Map<String, String> flightDetails = new HashMap<>();

        try {
            flightDetails.put("HeaderDetails", driver.findElement(By.xpath("//div[contains(@class,'selected-flight-details__trip-details')]")).getText());
            flightDetails.put("FlightName", driver.findElement(By.xpath("//*[@class='primary-color']")).getText());
            flightDetails.put("FlightCode", driver.findElement(By.xpath("(//*[@class='primary-color']/following-sibling::span)[1]")).getText());
            flightDetails.put("BookingClass", driver.findElement(By.xpath("(//*[@class='primary-color']/following-sibling::span)[2]")).getText());
            flightDetails.put("Fare", driver.findElement(By.xpath("(//*[@class='primary-color']/parent::div/following-sibling::div)[1]")).getText());
            flightDetails.put("TravelClass", driver.findElement(By.xpath("(//*[@class='primary-color']/parent::div/following-sibling::div)[2]")).getText());
            flightDetails.put("Origin", driver.findElement(By.xpath("(//*[@class='dark-grey-color fs-14 fw-600'])[1]")).getText());
            flightDetails.put("Destination", driver.findElement(By.xpath("(//*[@class='dark-grey-color fs-14 fw-600'])[2]")).getText());
            flightDetails.put("OriginLocation", driver.findElement(By.xpath("//*[@class='fs-10 mb-2']")).getText());
            flightDetails.put("DestinationLocation", driver.findElement(By.xpath("(//*[@class='dark-grey-color fs-14 fw-600'])[2]/following-sibling::span")).getText());
            flightDetails.put("Duration", driver.findElement(By.xpath("//*[@class='fs-12 grey-color mb-2']")).getText());
            flightDetails.put("Baggage", driver.findElement(By.xpath("//*[@class='selected-flight-details__flight-leg_amenities']/span")).getText());

            
        //   String orginFlightName= driver.findElement(By.xpath("(//*[@class='primary-color'])[1]")).getText();
        //  String changeFlightcode= driver.findElement(By.xpath("(//*[@class='primary-color']/following-sibling::span)[3]")).getText();
          
           
           String fromFlightCode = driver.findElement(By.xpath("(//*[@class='primary-color']/following-sibling::span)[1]")).getText();
           String changeFlightcode= driver.findElement(By.xpath("(//*[@class='primary-color']/following-sibling::span)[3]")).getText();
           String fromFlightClass=driver.findElement(By.xpath("(//*[@class='primary-color']/following-sibling::span)[2]")).getText();
           String toFlightClass=driver.findElement(By.xpath("(//*[@class='primary-color']/following-sibling::span)[4]")).getText();
           String fromFare=driver.findElement(By.xpath("(//*[@class='primary-color']/parent::div/following-sibling::div)[1]")).getText();
           String changeFare=driver.findElement(By.xpath("(//*[@class='primary-color']/parent::div/following-sibling::div)[3]")).getText();
           String travelClass=driver.findElement(By.xpath("((//*[@class='primary-color']/parent::div/following-sibling::div)[2]/span)[1]")).getText();
           String travelClassNumber=driver.findElement(By.xpath("((//*[@class='primary-color']/parent::div/following-sibling::div)[2]/span)[2]")).getText();
           String changeTravelClass=driver.findElement(By.xpath("((//*[@class='primary-color']/parent::div/following-sibling::div)[4]/span)[1]")).getText();
           String changeTravelNumber=driver.findElement(By.xpath("((//*[@class='primary-color']/parent::div/following-sibling::div)[4]/span)[2]")).getText();
           String orgin=driver.findElement(By.xpath("(//*[@class='dark-grey-color fs-14 fw-600'])[1]")).getText();
           String Finaldestination =driver.findElement( By.xpath("(//*[@class='dark-grey-color fs-14 fw-600'])[last()]")).getText();
           String destination1=driver.findElement(By.xpath("(//*[@class='dark-grey-color fs-14 fw-600'])[2]")).getText();
           String secondOrgin=driver.findElement(By.xpath("(//*[@class='dark-grey-color fs-14 fw-600'])[3]")).getText();
           
           String originLocation=driver.findElement(By.xpath("//*[@class='fs-10 mb-2']")).getText();
           String secondOrginLocation=driver.findElement(By.xpath("(//*[@class='fs-10 mb-2'])[2]")).getText();
           
           String firstDestinationLocation= driver.findElement(By.xpath("(//*[@class='dark-grey-color fs-14 fw-600'])[2]/following-sibling::span")).getText();
           String finalDestinationLocation=driver.findElement(By.xpath("(//*[@class='dark-grey-color fs-14 fw-600'])[4]/following-sibling::span")).getText();
           
           String duration =driver.findElement(By.xpath("//*[@class='fs-12 grey-color mb-2']")).getText();
           String finalDuration=driver.findElement(By.xpath("(//*[@class='fs-12 grey-color mb-2'])[2]")).getText();
           
           String layOverDetails=driver.findElement(By.xpath("//*[@class='selected-flight-details__flight-leg_layover__location ms-1']")).getText();
           String checkInBaggage=driver.findElement(By.xpath("//*[@class='selected-flight-details__flight-leg_amenities']/span")).getText();
           
           
           
           
            
            
            
        } catch (Exception e) {
            System.out.println("Error while fetching flight details: " + e.getMessage());
            e.printStackTrace();
        }

        return flightDetails;
    }
    
    
    public void baggagedeatils()
    {
    	String firstBaggageFlightLocation=driver.findElement(By.xpath("(//div[@class='flex-column baggage-table_value_header mb-1'])[1]")).getText();
    	String secondBaggageFlightLocation=driver.findElement(By.xpath("(//div[@class='flex-column baggage-table_value_header mb-1'])[2]")).getText();
    	WebElement baggageTable=driver.findElement(By.xpath("//*[@class='baggage-table_value']"));
    	driver.findElement(By.xpath("(//*[@class='baggage-table_value']/span)[1]")).getText();
    	driver.findElement(By.xpath("(//*[@class='baggage-table_value']/span)[2]")).getText();
    }
    
    
    }

