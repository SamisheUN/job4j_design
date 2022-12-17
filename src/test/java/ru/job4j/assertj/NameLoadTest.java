package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameLoadTest {
    @Test
    void checkEmpty() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::getMap)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("no data");
    }

    @Test
    void noEqualSignInName() {
        NameLoad nameLoad = new NameLoad();
        String name = "srstscnd";
        assertThatThrownBy(() -> nameLoad.parse(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("symbol")
                .hasMessageContaining(name);
    }

    @Test
    void noKeyInName() {
        NameLoad nameLoad = new NameLoad();
        String name = "=srstscnd";
        assertThatThrownBy(() -> nameLoad.parse(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("key")
                .hasMessageContaining(name);
    }

    @Test
    void noValueInName() {
        NameLoad nameLoad = new NameLoad();
        String name = "srstscnd=";
        assertThatThrownBy(() -> nameLoad.parse(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("value")
                .hasMessageContaining(name);
    }
}