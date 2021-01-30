package com.example.localunittesting;

import org.junit.Test;

import static com.example.localunittesting.FirstNameExtractor.extractFirstName;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final String JOE = "Joe";

    @Test
    public void extractFirstName_NullInput_ReturnEmptyString(){
        assertThat(extractFirstName(null), is(""));
    }

    @Test
    public void extractFirstName_EmptyString_ReturnEmptyString(){
        assertThat(extractFirstName(""), is(""));
    }

    @Test
    public void extractFirstName_FullName_ReturnCorrect(){
        assertThat(extractFirstName("Joe Bidens"), is(JOE));
    }

    @Test
    public void extractFirstName_FullNameAroundWithWhiteSpace_ReturnCorrect(){
        assertThat(extractFirstName("Joe  Bidens"), is(JOE));
        assertThat(extractFirstName("Joe Bidens "), is(JOE));
        assertThat(extractFirstName("  Joe Bidens"), is(JOE));
        assertThat(extractFirstName("Joe   Bidens"), is(JOE));
        assertThat(extractFirstName("Joe Bidens  "), is(JOE));
    }

    @Test
    public void extractFirstName_FullNameThreeWords_ReturnCorrect(){
        assertThat(extractFirstName("Joe Noel Bidens"), is(JOE));
    }

    @Test
    public void extractFirstName_FullNameThreeWordsAroundSpace_ReturnCorrect(){
        assertThat(extractFirstName("Joe Noel Bidens"), is(JOE));
        assertThat(extractFirstName("Joe   Noel Bidens"), is(JOE));
        assertThat(extractFirstName("Joe Noel   Bidens"), is(JOE));
        assertThat(extractFirstName("Joe Noel Bidens "), is(JOE));
        assertThat(extractFirstName("  Joe Noel Bidens"), is(JOE));
    }
}