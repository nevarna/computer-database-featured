package com.navarna.computerdb.validator;

import static org.junit.Assert.*;

import org.junit.Test;


public class ValidationNavigationTest {

    @Test
    public void testVerificationPage() {
        String correct = "61";
        String faux = "-1";
        String faux2 = null;
        assertEquals(true , ValidationNavigation.verificationPage(correct));
        assertEquals(false, ValidationNavigation.verificationPage(faux));
        assertEquals(false , ValidationNavigation.verificationPage(faux2));
    }

    @Test
    public void testVerificationNbElement() {
        String correct = "50";
        String faux = "12";
        String faux2 = null;
        assertEquals(true,ValidationNavigation.verificationNbElement(correct));
        assertEquals(false,ValidationNavigation.verificationNbElement(faux));
        assertEquals(false,ValidationNavigation.verificationNbElement(faux2));
    }

    @Test
    public void testEnleverCaractereInterdit() {
        String test = "aéà%&$aa";
        test = ValidationNavigation.EnleverCaractereInterdit(test);
        assertEquals("aea   aa", test);
        
    }

    @Test
    public void testVerificationTypeSearch() {
        String correct  = "company.name";
        String correct2 = "computer.name";
        String faux = "Coamp";
        String faux2 = null;
        assertEquals(true, ValidationNavigation.verificationTypeSearch(correct));
        assertEquals(true, ValidationNavigation.verificationTypeSearch(correct2));
        assertEquals(false, ValidationNavigation.verificationTypeSearch(faux));
        assertEquals(false, ValidationNavigation.verificationTypeSearch(faux2));
    }

    @Test
    public void testVerificationSearch() {
        boolean correct  = ValidationNavigation.verificationSearch("tre", "computer.name");
        boolean faux = ValidationNavigation.verificationSearch(null, "Computer");
        boolean faux2 = ValidationNavigation.verificationSearch("", "Computer");
        boolean faux3 = ValidationNavigation.verificationSearch("moi", "COmpute");
        assertEquals(true,correct);
        assertEquals(false, faux);
        assertEquals(false, faux2);
        assertEquals(false, faux3);
    }
}
