package com.iween.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BookingPage extends BasePage {

	  public BookingPage(WebDriver driver) {
	        super(driver);// calls BasePage constructor
	       
	    }
	  
	  
	  @FindBy(xpath="//span[@class='primary-color']/parent::div//span[2]")
	    WebElement carrier;
	
	 @FindBy(xpath="//span[@class='primary-color']/parent::div//span[3]")
	    WebElement bookingClass;
	
	 @FindBy(xpath="//span[@class='primary-color']/parent::div//span[4]")
	    WebElement supplier;
	
	 @FindBy(xpath="//span[@class='primary-color']")
	    WebElement airline;
	
	 @FindBy(xpath="(//span[@class='primary-color']/parent::div//span[4]/parent::div/following-sibling::div)[1]")
	    WebElement fareType;	
	
	 @FindBy(xpath="(//span[@class='primary-color']/parent::div//span[4]/parent::div/following-sibling::div)[2]//span[1]")
	    WebElement classes;
	
	 @FindBy(xpath="(//span[@class='primary-color']/parent::div//span[4]/parent::div/following-sibling::div)[2]//span[2]")
	    WebElement aircraftModel;
	
	
	 @FindBy(xpath="//span[@class='dark-grey-color fs-14 fw-600'][1]")
	    WebElement departLocation;
	
	
	public void reviewFlight()
	{
		String carrierText = carrier.getText();
		System.out.println(carrierText);
		String airlineText = airline.getText();
		System.out.println(airlineText);
		String bookingClassText = bookingClass.getText();
		System.out.println(bookingClassText);
		String supplierText = supplier.getText();
		System.out.println(supplierText);
		String fareTypeText = fareType.getText();
		System.out.println(fareTypeText);
		String classesText = classes.getText();
		System.out.println(classesText);
		String aircraftModelText = aircraftModel.getText();
		System.out.println(aircraftModelText);
		String classAircraftModel = classesText+" "+aircraftModelText;
		System.out.println(classAircraftModel);
		String departLocationText = departLocation.getText();
		System.out.println(departLocationText);
		//23:15 (04 Aug) - Ahmedabad - T1
		String[] departTime = departLocationText.split("\\(");
		String departTimeText = departTime[0].trim();
		System.out.println(departTimeText);
		String[] departDate = departLocationText.split("\\)");
		String[] departDateText = departDate[0].split("\\(");
		String departDateGetText = departDateText[1].trim();
		System.out.println(departDateGetText);
		String[] departLocationn = departLocationText.split("-");
		String departLocationnText = departLocationn[1];
		System.out.println(departLocationnText);
		String[] departLocationTextt = departLocationnText.split("-");
		String DepartLocation = departLocationTextt[0].trim();
		System.out.println(DepartLocation);

		String[] departTerminalTextt = departLocationText.split("-");
		String DepartTerminal = departTerminalTextt[1].trim();
		System.out.println(DepartTerminal);
		String[] departTerminalTextt1 = DepartTerminal.split("-");
		String DepartTerminal1 = departTerminalTextt1[1].trim();
		System.out.println(DepartTerminal1);
		//departLocationText.split(departDateGetText)
	}
}

