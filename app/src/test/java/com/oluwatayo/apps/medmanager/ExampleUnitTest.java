package com.oluwatayo.apps.medmanager;

import com.oluwatayo.apps.medmanager.utils.DateUtils;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void dateCorrect()throws Exception{
        long date = DateUtils.getDateInMilliseconds("2018/04/13 11:59");
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        long today = calendar.getTimeInMillis();

        assertEquals(String.valueOf(date).length(), String.valueOf(today).length());
    }
}