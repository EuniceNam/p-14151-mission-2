package com.back;

import java.util.Arrays;

public class Calc {

    public static int run(String s0) {
        String s = s0.replaceAll("\\(","( " ).replaceAll("\\)", " )");
        String[] items = s.split(" ");

        return calc(items);
    }

    private static int calc(String[] items) {
        int lop = nextInt(items);
        String[] nextExp = null;
        for (int i = 0; i < items.length; i++) {
            Op o = Op.from(items[i]);
            if (o != null) {
                nextExp = Arrays.copyOfRange(items, i + 1, items.length);
                switch (o) {
                    case ADD -> {
                        if (Op.ADD.priorLv() >= nexOpPLv(nextExp)) {
                            lop += nextInt(nextExp);
                        } else {
                            return lop + calc(nextExp);
                        }
                    }
                    case SUB -> {
                        if (Op.SUB.priorLv() >= nexOpPLv(nextExp)) {
                            lop -= nextInt(nextExp);
                        } else {
                            return lop - calc(nextExp);
                        }
                    }
                    case MULT -> {
                        if (Op.MULT.priorLv() >= nexOpPLv(nextExp)) {
                            lop *= nextInt(nextExp);
                        } else {
                            return lop * calc(nextExp);
                        }
                    }
                    case LP -> { return calc(nextExp);}
                    case RP -> { /*return lop;*/}
                    default -> {} // number
                }
            }
        }
        return lop;
    }

    private static int nextInt(String[] exp) {
        for(String s: exp) {
            if(s.matches("-?\\d+")) {
                return Integer.parseInt(s);
            }
        }
        return Integer.MIN_VALUE;
    }

    private static int nexOpPLv(String[] exp) {
        for(String s: exp) {
            Op o = Op.from(s);
            if (o != null) { return o.priorLv();}
        }
        return 0; // exp의 끝
    }

    private enum Op {
        ADD("+"),SUB("-"), MULT ("*"), LP("("), RP(")");

        private final String symbol;
        Op(String symbol) { this.symbol = symbol;}

        public static Op from(String s) {
            for (Op o: values()) {
                if (o.symbol.equals(s)) { return o;}
            }
            return null;
        }
        // priorLv - 시작, ): 0, +|-: 1, *: 2, (: 3
        public int priorLv() {
            return switch (this) {
                case ADD, SUB -> 1;
                case MULT -> 2;
                case LP -> 3;
                case RP -> 0;
            };
        }
    }
}
