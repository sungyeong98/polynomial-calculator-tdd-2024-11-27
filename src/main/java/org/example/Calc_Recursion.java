package org.example;

import javax.swing.text.html.HTMLDocument;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calc_Recursion {
    //  "((3 + 5) * 5 + -10) * 10 / 5"
    public static int run(String expression) {
        List<String> tokens = tokenize(expression);

        return recursion(new int[]{0}, tokens);
    }

    public static int recursion(int[] index, List<String> tokens) {
        Stack<Integer> numList = new Stack<>();
        Stack<String> operatorList = new Stack<>();

        boolean isPreviousOperator = true;

        while(index[0] < tokens.size()) {
            String token = tokens.get(index[0]);

            if(isNumber(token)) {
                numList.push(Integer.parseInt(token));
                index[0]++;
                isPreviousOperator = false;
            }

            else if(token.equals("-") && isPreviousOperator) {
                operatorList.push("neg");
                index[0]++;
            }

            else if(token.equals("(")) {
                index[0]++;
                numList.push(recursion(index, tokens));
                isPreviousOperator = true;
            }

            else if(token.equals(")")) {
                index[0]++;
                isPreviousOperator = false;
                break;
            }

            else if(isOperator(token)) {
                while(!operatorList.isEmpty() && precedence(operatorList.peek()) >= precedence(token)) {
                    calculate(numList, operatorList);
                }
                operatorList.push(token);
                index[0]++;
                isPreviousOperator = true;
            }
        }

        while(!operatorList.isEmpty()) {
            calculate(numList, operatorList);
        }

        return numList.pop();
    }

    public static void calculate(Stack<Integer> numList, Stack<String> opList) {
        String op = opList.pop();

        if(op.equals("neg")){
            numList.push(-numList.pop());
        }
        else {
            int num2 = numList.pop();
            int num1 = numList.pop();
            switch(op) {
                case "+" -> numList.push(num1 + num2);
                case "-" -> numList.push(num1 - num2);
                case "*" -> numList.push(num1 * num2);
                case "/" -> numList.push(num1 / num2);
            }
        }
    }

    public static int precedence(String op) {
        return switch (op){
            case "+" -> 1;
            case "-" -> 1;
            case "*" -> 2;
            case "/" -> 2;
            case "neg" -> 3;
            default -> 0;
        };
    }

    public static List<String> tokenize(String expression) {
        List<String> exp = new ArrayList<>();
        Matcher matcher = Pattern.compile("-?\\d+\\.\\d+|-?\\d+|[+\\-*/()]").matcher(expression);

        while(matcher.find()) {
            exp.add(matcher.group());
        }

        return exp;

//        List<String> tokens = new ArrayList<>();
//        StringBuilder builder = new StringBuilder();
//
//        for(char c : expression.toCharArray()) {
//            if(c==' '){
//                continue;
//            }
//
//            if(Character.isDigit(c) || c=='+' || c=='-' || c=='*' || c=='/') {
//                if(builder.length()>0) {
//                    tokens.add(builder.toString());
//                    builder.setLength(0);
//                }
//                tokens.add(String.valueOf(c));
//            }
//        }
//        if(builder.length()>0) {
//            tokens.add(builder.toString());
//        }
//        return tokens;
    }

    public static boolean isOperator(String token) {
        return "+-*/".contains(token) || token.equals("neg");
    }

    public static boolean isNumber(String token) {
        try{
            Integer.parseInt(token);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
}
