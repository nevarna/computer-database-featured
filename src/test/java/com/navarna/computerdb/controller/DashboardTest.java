package com.navarna.computerdb.controller;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
public class DashboardTest {

    public DashboardTest() {
        // TODO Auto-generated constructor stub
    }
    
    public static void main(String[] args) {
        WebDriver driver = new FirefoxDriver();
        driver.get("http://localhost:8080/ComputerDatabase/Dashboard");
        driver.manage().window().maximize();
        //Get the current page URL and store the value in variable 'str'
        String str = driver.getCurrentUrl();

        //Print the value of variable in the console
        System.out.println("The current URL is " + str);
    }

}
