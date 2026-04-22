package com.back;

public class Calc {
    static final String ADD = "+";
    static final String SUB = "-";
    static final String MULT = "*";

    public static int run(String s) {
        String[] items = s.split(" ");
        int len = items.length;
        int lop = Integer.parseInt(items[0]);
        int rop;
        for(int i=1; i<=len/2; i++) {
            rop = Integer.parseInt(items[i * 2]);
            switch (items[i*2-1]) {
                case ADD -> lop += rop;
                case SUB -> lop -= rop;
                case MULT -> lop *= rop;
                default -> {}
            }
        }
        return lop;
    }
}
