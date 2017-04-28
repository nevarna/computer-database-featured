package com.navarna.computerdb.controller;

import org.openqa.selenium.firefox.FirefoxDriver;
public class DashboardTest {

    public DashboardTest() {
        // TODO Auto-generated constructor stub
    }
    
    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver","/home/excilys/Documents/Projet/geckodriver-master");
        FirefoxDriver driver = new FirefoxDriver();
        driver.get("https://github.com/excilys/training-java");
        //Get the current page URL and store the value in variable 'str'
        String str = driver.getCurrentUrl();

        //Print the value of variable in the console
        System.out.println("The current URL is " + str);
        driver.quit();
    }

}
