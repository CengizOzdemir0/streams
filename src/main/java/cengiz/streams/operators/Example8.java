package cengiz.streams.operators;

import java.util.Arrays;

public class Example8 {

    public static void main(String[] args) {

        String[][] data = new String[][]{{"a", "b"}, {"c", "d","a"}, {"e", "f"}};

		Arrays.stream(data)
                .flatMap(Arrays::stream)
                .filter(x -> "a".equals(x.toString()))
                .forEach(System.out::println);
    }

}
