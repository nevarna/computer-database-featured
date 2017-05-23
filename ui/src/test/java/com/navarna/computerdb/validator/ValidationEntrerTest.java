package com.navarna.computerdb.validator;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    
    @Test 
    public void testRegex () {
        String date = "01/02/2000";
        String date2 = "1999-01-01";
        String date3 = "12-20-1999";
        String regexDateFrancaise ="^(?:(?:31(-)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(-)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(-)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(-)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})";
        String regexDateFrancaise2 ="^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])";
        String regexDateAnglaise3 = "^(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])-[0-9]{4}";
        Pattern p = Pattern.compile(regexDateFrancaise);
        Pattern p2 = Pattern.compile(regexDateFrancaise2);
                
        Matcher m = p.matcher(date2);
        Matcher m2 = p.matcher(date);
        if(m.matches()) {
            fail();
        }
        if(m2.matches()) {
            fail();
        }
        if(!p2.matcher(date2).matches()) {
            fail();
        }
        if(!Pattern.matches(regexDateAnglaise3, date3)) {
            fail();
        }
      }
}
