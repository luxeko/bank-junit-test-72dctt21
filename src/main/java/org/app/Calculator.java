package org.app;

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class Calculator {

    static double add(double... operands) {
        return DoubleStream.of(operands)
                .sum();
    }

    static double multiply(double... operands) {
        System.out.println(Arrays.toString(operands));
        /*
         1: result = identify = 1, element = operands[0] => new_result = result * operand[0]
         2: result = new_result, element = operands[1] => new_result = result * operand[1]
         ......
        */
        return DoubleStream.of(operands)
                .reduce(1, (result, element) -> result * element);
    }
}