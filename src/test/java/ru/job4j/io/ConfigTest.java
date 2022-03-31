package ru.job4j.io;

import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ConfigTest {

    @Test
    public void whenPairWithoutComment() {
        String path = "./data/pair_without_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name"), is("Petr Arsentev"));
        assertThat(config.value("surname"), is(Matchers.nullValue()));
    }

    @Test
    public void whenPairWithCommentAndEmptyLine() {
        String path = "./data/pair_with_comments_and_empty_lines.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name"), is("Andrey S, = no, not ="));
        assertThat(config.value("age"), is("33"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPairWithWrongPattern() {
        String path = "./data/pair_with_incorrect_pattern.properties";
        Config config = new Config(path);
        config.load();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenOnlyWrongPattern() {
        String path = "./data/only_incorrect_pattern.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name"), is("Andrey"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLackArgument() {
        String path = "./data/lack_arg.properties";
        Config config = new Config(path);
        config.load();
    }
}