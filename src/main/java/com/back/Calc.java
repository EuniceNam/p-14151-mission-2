package com.back;

public class Calc {
    static final String ADD = "+";
    static final String SUB = "-";
    static final String MULT = "*";

    public static int run(String s) {
        int lpidx = s.indexOf("(");
        int rpidx;
        if(lpidx != -1) {
            rpidx = s.indexOf(")");
            return calc(s.substring(lpidx+1, rpidx)); //(안의 exp만)
        } else {
            return calc(s);
        }
    }

    private static int calc(String s) {
        String[] items = s.split(" ");
        int len = items.length;
        for(int i=1; i<=len/2; i++) {
            if (items[i*2-1].equals(MULT)) {
                items[i*2] = String.valueOf(Integer.parseInt(items[i*2-2]) * Integer.parseInt(items[i*2]));
                items[i*2-1] = "+";
                items[i*2-2] = "0";
            }
        }
        int lop = Integer.parseInt(items[0]);
        int rop;
        for(int i=1; i<=len/2; i++) {
            rop = Integer.parseInt(items[i * 2]);
            switch (items[i*2-1]) {
                case ADD -> lop += rop;
                case SUB -> lop -= rop;
                default -> {}
            }
        }
        return lop;
    }
}
