import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class PageManipulator {

 void clickElement(WebDriver driver,WebElement elementSelector){
	 
	 Actions click = new Actions(driver);
	 Action clickElement = click.moveToElement(elementSelector).click().build();
	 clickElement.perform();
	 
 }
 
 

}
