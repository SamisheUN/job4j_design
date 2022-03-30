package ru.job4j.io;

import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ConfigTest {

    /**
     * name=Petr Arsentev
     */
    @Test
    public void whenPairWithoutComment() {
        String path = "./data/pair_without_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name"), is("Petr Arsentev"));
        assertThat(config.value("surname"), is(Matchers.nullValue()));
    }

    /**
     * #привет мир!
     * <p>
     * name=Andrey S, = no, not =
     * <p>
     * #
     * <p>
     * age=33
     */
    @Test
    public void whenPairWithCommentAndEmptyLine() {
        String path = "./data/pair_with_comments_and_empty_lines.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name"), is("Andrey S, "));
        assertThat(config.value("age"), is("33"));
    }

    /**
     * name=
     * Ivan
     * =Ivan
     * name=Andrey S, = no, not =
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenPairWithWrongPattern() {
        String path = "./data/pair_with_incorrect_pattern.properties";
        Config config = new Config(path);
        config.load();
    }

    /**
     * name=
     * Ivan
     * =Ivan
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenOnlyWrongPattern() {
        String path = "./data/only_incorrect_pattern.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name"), is("Andrey"));
    }
}