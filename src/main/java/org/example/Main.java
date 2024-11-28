package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String expression = "((3 + 5) * 5 + -10) * 10 / 5";

        //String expression = "-(10 + 5)";

        Calc calc = new Calc();
        System.out.println(calc.run(expression));
    }
}