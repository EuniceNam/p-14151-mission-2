package com.back;

import java.util.Arrays;

public class Calc {
    static final char ADD = '+';
    static final char MULT = '*';
    static final char LP = '(';
    static final char RP = ')';

    public static int run(String s) {
        String exp = s.replace(" - ", " + -");// 뺄셈 > 음수의 덧셈
        return parsP(exp);
    }
    // 괄호 처리
    private static int parsP(String exp) {
        int lpidx = exp.indexOf(LP);
        if (lpidx != -1){ // 괄호 있음
            int rpidx = lpidx + getrpidx(exp.substring(lpidx));
            // 괄호 안의 식을 계산하여 대체한 스트링 반환
            return calc(exp.substring(0, lpidx)+calc(exp.substring(lpidx+1, rpidx))+exp.substring(rpidx+1));
        } else { // 괄호 없음
            return calc(exp);
        }
    }
    // 괄호 분리 및 처리 > 덧셈 양쪽 분리 > 곱셈 처리 > 덧셈 처리
    private static int calc(String exp) {
        int lpidx = exp.indexOf(LP);
        if (lpidx != -1){ // 괄호 있음
            return parsP(exp);
        } else { // 괄호 없음
            int addidx = exp.indexOf(ADD);
            if(addidx != -1) { // 덧셈 있음
                String lop = exp.substring(0, addidx);
                String rop = exp.substring(addidx + 1);

                if (lop.indexOf(MULT) < 0) { // 왼쪽 항에 곱셈 없음
                    if (rop.indexOf(MULT) < 0) { // 오른쪽 항에 곱셈 없음
                        return Arrays.stream(exp.trim().split(" \\+ ")).mapToInt(Integer::parseInt).sum();
                    } else { // 오른쪽 항에 곱셈 있음
                        return Integer.parseInt(lop.trim()) + calc(rop);
                    }
                } else { // 왼쪽 항에 곱셈 있음
                    if (rop.indexOf(MULT) < 0) { // 오른쪽 항에 곱셈 없음
                        return calc(lop) + Arrays.stream(rop.trim().split(" \\+ ")).mapToInt(Integer::parseInt).sum();
                    } else { // 오른쪽 항에 곱셈 있음
                        return calc(lop) + calc(rop);
                    }
                }
            } else { // 덧셈 없음
                return Arrays.stream(exp.trim().split(" \\* ")).mapToInt(Integer::parseInt).reduce(1, (a, b)-> a * b);
            }
        }
    }
    // 괄호 오른쪽의 인덱스를 찾기
    private static int getrpidx (String exp) {
        int pcnt = 0;
        for (int i=0; i<exp.length(); i++) {
            if(exp.charAt(i) == LP) { pcnt++;}
            else if(exp.charAt(i) == RP) {
                pcnt--;
                if (pcnt == 0) { return i;}
            }
        }
        return -1;
    }
}
