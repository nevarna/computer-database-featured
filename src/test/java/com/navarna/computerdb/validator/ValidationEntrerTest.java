package com.navarna.computerdb.validator;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class ValidationEntrerTest {

    @Test
    public void testVerificationFormatDate() {
        String correct = "10-11-2000";
        String correct2 = "2000-12-12";
        String correct3 = "12-12-2000 12:12:13";
        String correct4 = "2000-12-12 12:12:13";
        assertEquals(true, ValidationEntrer.verificationFormatDate(correct));
        assertEquals(true, ValidationEntrer.verificationFormatDate(correct2));
        assertEquals(false, ValidationEntrer.verificationFormatDate(correct3));
        assertEquals(false, ValidationEntrer.verificationFormatDate(correct4));
    }

    @Test
    public void testStringEnIntPositif() {
        String correct = "1";
        String faux = "-2";
        String faux2 = "a";
        String faux3 = null;
        assertEquals(1, ValidationEntrer.stringEnIntPositif(correct));
        assertEquals(-1, ValidationEntrer.stringEnIntPositif(faux));
        assertEquals(-1, ValidationEntrer.stringEnIntPositif(faux2));
        assertEquals(-1, ValidationEntrer.stringEnIntPositif(faux3));
    }

    @Test
    public void testEntrerValide() {
        LocalDate dateMin = LocalDate.MIN;
        LocalDate dateMax = LocalDate.MAX;
        String name = "test";
        int idCompany = 3;
        assertEquals(true, ValidationEntrer.entrerValide(name, dateMin, dateMax, idCompany));
        assertEquals(false, ValidationEntrer.entrerValide(name, dateMin, dateMax, -1));
        assertEquals(false, ValidationEntrer.entrerValide(name, dateMax, dateMin, idCompany));
        assertEquals(false, ValidationEntrer.entrerValide(null, dateMin, dateMax, idCompany));
    }

    @Test
    public void testDateLogique() {
        LocalDate dateMin = LocalDate.MIN;
        LocalDate dateMax = LocalDate.MAX;
        assertEquals(true, ValidationEntrer.dateLogique(dateMin, dateMax));
        assertEquals(false, ValidationEntrer.dateLogique(dateMax, dateMin));
        assertEquals(true, ValidationEntrer.dateLogique(dateMin, null));
        assertEquals(false, ValidationEntrer.dateLogique(null, dateMax));
        assertEquals(true, ValidationEntrer.dateLogique(null, null));
        assertEquals(true, ValidationEntrer.dateLogique(dateMax, dateMax));
    }
}
