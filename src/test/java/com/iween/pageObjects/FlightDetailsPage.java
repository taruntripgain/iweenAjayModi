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

     
    }

