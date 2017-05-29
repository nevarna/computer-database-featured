package com.navarna.computerdb.validator;

import static org.junit.Assert.*;

import org.junit.Test;

import com.navarna.computerdb.dto.NavigationDashboardDTO;


public class NavigationValidatorTest {

    @Test
    public void testVerificationNbElement() {
        int correct = 50;
        int faux = 12;
        assertEquals(true,NavigationValidator.verificationNbElement(correct));
        assertEquals(false,NavigationValidator.verificationNbElement(faux));
    }


    @Test
    public void testVerificationTypeSearch() {
        String correct  = "company.name";
        String correct2 = "name";
        String faux = "Coamp";;
        assertEquals(true, NavigationValidator.verificationType(correct));
        assertEquals(true, NavigationValidator.verificationType(correct2));
        assertEquals(false, NavigationValidator.verificationType(faux));
    }
    
    @Test
    public void testVerificationValidator() {
        boolean correct2 =  NavigationValidator.verificationSearch("");
        boolean correct3 =  NavigationValidator.verificationSearch("name");
        boolean faux  =  NavigationValidator.verificationSearch("name#");
        assertEquals(true,correct2);
        assertEquals(true,correct3);
        assertEquals(false,faux);
    }
    
    @Test
    public void testAttributVide () {
        NavigationDashboardDTO navigation = new NavigationDashboardDTO();
        boolean correct  = NavigationValidator.attributVide(navigation);
        navigation.setOrder(null);
        boolean faux = NavigationValidator.attributVide(navigation);
        navigation.setOrder("la");
        navigation.setType(null);
        boolean faux2 = NavigationValidator.attributVide(navigation);
        navigation.setType("ASC");
        navigation.setSearch(null);
        boolean faux3 = NavigationValidator.attributVide(navigation);
        assertEquals(correct,false);
        assertEquals(faux,true);
        assertEquals(faux2,true);
        assertEquals(faux3,true);
    }
}
