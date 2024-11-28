package org.example;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calc {
    public static double run(String expression) {
        System.out.println("중위식 : " + expression);
        List<String> postfix = convert(expression);
        System.out.println("후위식 : " + postfix);
        double result = calculate(postfix);
        return result;
    }

    // 중위->후위 변환
    public static List<String> convert(String expression) {
        List<String> result = new ArrayList<>();
        Stack<String> operators = new Stack<>();
        List<String> tokens = preprocessing(expression);

        boolean isPreviousOperator = true;

        Iterator<String> it = tokens.iterator();
        while(it.hasNext()) {
            String token = it.next();

            if(isNumber(token)) {
                result.add(token);
                isPreviousOperator = false;
            }
            else if(token.equals("-") && isPreviousOperator) {
                operators.push("neg");
            }
            else if(operatorOrder.containsKey(token)) {
                while(!operators.isEmpty() && !operators.peek().equals("(") && operatorOrder.get(operators.peek()) >= operatorOrder.get(token)) {
                    result.add(operators.pop());
                }
                operators.push(token);
                isPreviousOperator = true;
            }
            else if(token.equals("(")){
                operators.push(token);
                isPreviousOperator = true;
            }
            else if(token.equals(")")){
                while(!operators.isEmpty() && !operators.peek().equals("(")) {
                    result.add(operators.pop());
                }
                if(!operators.isEmpty()) {
                    operators.pop();
                }
                isPreviousOperator = false;
            }
        }

        while(!operators.isEmpty()) {
            result.add(operators.pop());
        }
        //System.out.println("후위식 전환 : " + result);
        //System.out.println("기호 스택 : " + operators);
        return result;
    }

    // 후위 계산
    public static double calculate(List<String> postfix) {
        Stack<Double> stack = new Stack<>();

        for(String token : postfix) {
            if(isNumber(token)) {
                stack.push(Double.parseDouble(token));
            }
            else if(token.equals("neg")) {
                stack.push(-stack.pop());
            }
            else {
                double num2 = stack.pop();
                double num1 = stack.pop();

                switch (token) {
                    case "+":
                        stack.push(num1 + num2);
                        break;
                    case "-":
                        stack.push(num1 - num2);
                        break;
                    case "*":
                        stack.push(num1 * num2);
                        break;
                    case "/":
                        stack.push(num1 / num2);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operator: " + token);
                }
            }
        }
        return stack.pop();
    }

    // 계산식 전처리
    public static List<String> preprocessing(String expression) {
        List<String> exp = new ArrayList<>();
        Matcher matcher = Pattern.compile("-?\\d+\\.\\d+|-?\\d+|[+\\-*/()]").matcher(expression);

        while(matcher.find()) {
            exp.add(matcher.group());
        }

        return exp;
    }

    // 연산자 우선 순위
    public static final Map<String, Integer> operatorOrder = Map.of(
            "+" , 1 ,"-" , 1, "*" , 2, "/" , 2, "neg", 3
    );

    // 숫자 판별
    public static boolean isNumber(String token) {
        try{
            Double.parseDouble(token);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

}
