package ru.job4j.io;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

public class BBBTest {
    @Test
    public void bbb() {
        double expected = 0.16;
        int p = 2;
        double k = 4;
        double out = p * k;
        assertThat(out, is(closeTo(1.0, 0.03)));
    }
}
