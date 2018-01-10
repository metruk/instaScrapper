import java.util.ArrayList;
import java.util.List;

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
 
 double getFollowersByClassText(String classText){
	 String followers  = classText;
	 followers = followers.replaceAll(" \\w+","");
	 followers = followers.replace(",",".");
	 //followers = followers.replace(".","");
	 double followersAmount = 0;
	 
	 if(followers.contains("k")){
		 followers = followers.replaceAll("k", "");
		 followersAmount = Double.parseDouble(followers)*1000;
	}else if(followers.contains("m")){
		 followers = followers.replaceAll("m", "");
		 followersAmount = Double.parseDouble(followers) * 100000;
	}else{
		if(followers.contains(".")){
			followers = followers.replace(".", "");
			followersAmount = Double.parseDouble(followers);
			
		}else{
			followersAmount = Double.parseDouble(followers);
			System.out.println("here");
			System.out.println(followersAmount);
		}
	}
	
	 return followersAmount;
	    
	 }
 }


