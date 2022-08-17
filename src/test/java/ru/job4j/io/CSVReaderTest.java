package ru.job4j.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CSVReaderTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Test
    void whenFilterTwoColumns(@TempDir Path folder) throws Exception {
        String data = String.join(
                System.lineSeparator(),
                "name;age;last_name;education",
                "Tom;20;Smith;Bachelor",
                "Jack;25;Johnson;Undergraduate",
                "William;30;Brown;Secondary special"
        );
        File file = folder.resolve("source.csv").toFile();
        File target = folder.resolve("target.csv").toFile();
        ArgsName argsName = ArgsName.of(new String[]{
                "-path=" + file.getAbsolutePath(), "-delimiter=;",
                "-out=" + target.getAbsolutePath(), "-filter=name,education"});
        Files.writeString(file.toPath(), data);
        String expected = String.join(
                System.lineSeparator(),
                "name;education",
                "Tom;Bachelor",
                "Jack;Undergraduate",
                "William;Secondary special"
        ).concat(System.lineSeparator());
        CSVReader.handle(argsName);
        assertThat(Files.readString(target.toPath())).isEqualTo(expected);
    }

    @Test
    void whenFilterThreeColumns(@TempDir Path folder) throws Exception {
        String data = String.join(
                System.lineSeparator(),
                "name;age;last_name;education",
                "Tom;20;Smith;Bachelor",
                "Jack;25;Johnson;Undergraduate",
                "William;30;Brown;Secondary special"
        );
        File file = folder.resolve("source.csv").toFile();
        File target = folder.resolve("target.csv").toFile();
        ArgsName argsName = ArgsName.of(new String[]{
                "-path=" + file.getAbsolutePath(), "-delimiter=;",
                "-out=" + target.getAbsolutePath(), "-filter=education,age,last_name"
        });
        Files.writeString(file.toPath(), data);
        String expected = String.join(
                System.lineSeparator(),
                "education;age;last_name",
                "Bachelor;20;Smith",
                "Undergraduate;25;Johnson",
                "Secondary special;30;Brown"
        ).concat(System.lineSeparator());
        CSVReader.handle(argsName);
        assertThat(Files.readString(target.toPath())).isEqualTo(expected);
    }

    @Test
    void whenFilterOneColumn(@TempDir Path folder) throws Exception {
        String data = String.join(
                System.lineSeparator(),
                "name",
                "Tom",
                "Jack",
                "William"
        );
        File file = folder.resolve("source.csv").toFile();
        File target = folder.resolve("target.csv").toFile();
        ArgsName argsName = ArgsName.of(new String[]{
                "-path=" + file.getAbsolutePath(), "-delimiter=;",
                "-out=" + target.getAbsolutePath(), "-filter=name"
        });
        Files.writeString(file.toPath(), data);
        String expected = String.join(
                System.lineSeparator(),
                "name",
                "Tom",
                "Jack",
                "William"
        ).concat(System.lineSeparator());
        CSVReader.handle(argsName);
        assertThat(Files.readString(target.toPath())).isEqualTo(expected);
    }

    @Test
    void whenIncorrectPath(@TempDir Path folder) {
        assertThatThrownBy(() -> {
            File file = new File(String.valueOf(folder.resolve("source.csv")));
            File target = folder.resolve("target.csv").toFile();
            ArgsName argsName = ArgsName.of(new String[]{
                    "-path=" + file.getAbsolutePath(), "-delimiter=;",
                    "-out=" + target.getAbsolutePath(), "-filter=name,education"});
            CSVReader.handle(argsName);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Not exist");
    }

    @Test
    void whenIncorrectDelimiter(@TempDir Path folder) {
        assertThatThrownBy(() -> {
            File file = new File(String.valueOf(folder.resolve("source.csv")));
            file.createNewFile();
            File target = folder.resolve("target.csv").toFile();
            ArgsName argsName = ArgsName.of(new String[]{
                    "-path=" + file.getAbsolutePath(), "-delimiter=boo",
                    "-out=" + target.getAbsolutePath(), "-filter=name,education"});
            CSVReader.handle(argsName);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("delimiter");
    }

    @Test
    void whenIncorrectFilter(@TempDir Path folder) {
        assertThatThrownBy(() -> {
            File file = new File(String.valueOf(folder.resolve("source.csv")));
            File target = folder.resolve("target.csv").toFile();
            ArgsName argsName = ArgsName.of(new String[]{
                    "-path=" + file.getAbsolutePath(), "-delimiter=;",
                    "-out=" + target.getAbsolutePath(), "-filter="});
            CSVReader.handle(argsName);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("filter");
    }

    @Test
    void whenOutStdout(@TempDir Path folder) throws Exception {
        System.setOut(new PrintStream(outContent));
        String data = String.join(
                System.lineSeparator(),
                "name;age;last_name;education",
                "Tom;20;Smith;Bachelor"
        );
        File file = folder.resolve("source.csv").toFile();
        ArgsName argsName = ArgsName.of(new String[]{
                "-path=" + file.getAbsolutePath(), "-delimiter=;",
                "-out=stdout", "-filter=name"
        });
        Files.writeString(file.toPath(), data);
        String expected = String.join(
                System.lineSeparator(),
                "name" + System.lineSeparator()
                        + "Tom"
        ).concat(System.lineSeparator());
        CSVReader.handle(argsName);
        System.setOut(originalOut);
        Assertions.assertEquals(expected, outContent.toString());
    }
}