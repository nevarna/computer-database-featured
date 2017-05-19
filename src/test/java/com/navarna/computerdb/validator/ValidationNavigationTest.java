package com.navarna.computerdb.validator;

import static org.junit.Assert.*;

import org.junit.Test;


public class ValidationNavigationTest {

    @Test
    public void testVerificationPage() {
        int correct = 61;
        int  faux = -1;
        assertEquals(true , ValidationNavigation.verificationPage(correct));
        assertEquals(false, ValidationNavigation.verificationPage(faux));
    }

    @Test
    public void testVerificationNbElement() {
        int correct = 50;
        int faux = 12;
        assertEquals(true,ValidationNavigation.verificationNbElement(correct));
        assertEquals(false,ValidationNavigation.verificationNbElement(faux));
    }

    @Test
    public void testEnleverCaractereInterdit() {
        String test = "aéà%&$aa";
        test = ValidationNavigation.enleverCaractereInterdit(test);
        assertEquals("aea%&$aa", test);
        
    }

    @Test
    public void testVerificationTypeSearch() {
        String correct  = "company.name";
        String correct2 = "computer.name";
        String faux = "Coamp";;
        assertEquals(true, ValidationNavigation.verificationTypeSearch(correct));
        assertEquals(true, ValidationNavigation.verificationTypeSearch(correct2));
        assertEquals(false, ValidationNavigation.verificationTypeSearch(faux));
    }

    @Test
    public void testVerificationSearch() {
        boolean correct  = ValidationNavigation.verificationSearch("tre", "computer.name");
        boolean faux2 = ValidationNavigation.verificationSearch("", "Computer");
        boolean faux3 = ValidationNavigation.verificationSearch("moi", "COmpute");
        assertEquals(true,correct);
        assertEquals(false, faux2);
        assertEquals(false, faux3);
    }
    
    @Test
    public void testVerificationValidator() {
        boolean correct = NavigationValidator.verificationSearch(null);
        boolean correct2 =  NavigationValidator.verificationSearch("");
        boolean correct3 =  NavigationValidator.verificationSearch("name");
        boolean faux  =  NavigationValidator.verificationSearch("name#");
        assertEquals(true,correct);
        assertEquals(true,correct2);
        assertEquals(true,correct3);
        assertEquals(false,faux);
    }
}
