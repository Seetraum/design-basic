package test.lambda;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrayInit {
    public static void main(String[] args) {
        var arr = new int[10];
        Arrays.parallelSetAll(arr,i -> i);
        System.out.println(Arrays.toString(arr));
        var list = IntStream.range(1,10).boxed().collect(Collectors.toList());
        System.out.println(Arrays.toString(list.toArray()));
    }
}
