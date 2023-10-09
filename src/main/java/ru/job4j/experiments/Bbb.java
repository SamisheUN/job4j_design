package ru.job4j.experiments;

public class Bbb {
    public static void main(String[] args) {
        TestPerson testPerson = new TestPerson("Benny", 25);
        var temp = testPerson;
        temp.setAge(211);
        System.out.println(testPerson);
        System.lineSeparator();
        System.out.println(temp);
    }

    public static class TestPerson {
        private int age;
        private String name;

        TestPerson(String name, int age) {
            this.age = age;
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "TestPerson{"
                    + "age=" + age
                    + ", name='" + name + '\''
                    + '}';
        }
    }

}