package org.example;

public class Main {
    public static void main(String[] args) {
        String expression = "((3 + 5) * 5 + -10) * 10 / 5";

        //String expression = "-(10 + 5)";

//        Calc calc = new Calc();
//        System.out.println(calc.run(expression));

        Calc_Recursion calcRecursion = new Calc_Recursion();
        System.out.println(calcRecursion.run(expression));
    }
}

//  코드 흐름
//  ((3 + 5) * 5 + -10) * 10 / 5    ->  식 입력
//  토큰화 진행
//  [(, (, 3, +, 5, ), *, 5, +, -10, ), *, 10, /, 5]    ->  다음과 같이 분리
//  재귀 함수에 index=0 과 토큰화된 수식을 넣어줌
//  (를 만나면 index++ 후 재귀함수 호출
//  숫자는 numList, 기호는 operatorList에 넣어줌.
//  만약 -기호 이면서 이전의 문자가 기호였으면, operatorList에 "neg"를 넣어줌  ->  "neg"는 연산처리시 numList의 -1 인덱스 숫자에 -를 붙여줌
//  특정 조건마다 pop해서 연산처리
