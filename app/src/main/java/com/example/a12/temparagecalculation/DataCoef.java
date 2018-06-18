package com.example.a12.temparagecalculation;

public class DataCoef {
    private static final double[] shtabelDeliver = {0.11,	0.1,	0.08,	0.07,	0.07,
            0.06,	0.05,	0.05,	0.04,	0.04,
            0.03,	0.03,	0.03,	0.02,	0.02,
            0.02,	0.02,	0.01,	0.01,	0.01,
            0.01,	0.01,	0.01,	0.01,	0.01,	0.01};

    private static final double[] stelajDeliver = {0.14,     0.12,	0.1,	0.08,	0.07,
            0.06,   0.05,	0.05,	0.04,	0.03,
            0.03,   0.02,	0.02,	0.02,	0.02,
            0.01,   0.01,   0.01,   0.01,   0.01,
            0.01,   0.0,    0.0,    0.0,    0.0,    0.0};

    private static final double[] bmDeliver  =       {0.19,	0.15,	0.12,	0.09,	0.07,
            0.06,	0.05,	0.04,	0.03,	0.02,
            0.02,	0.01,	0.01,	0.01,	0.01,
            0.01,   0.0,    0.0,    0.0,    0.0,
            0.0,    0.0,    0.0,    0.0,    0.0,    0.0};

    private static final double[] shtabelKeeping =  {0.02,	0.04,	0.06,	0.07,	0.08,	0.1,
            0.12,	0.14,	0.14,	0.16,	0.18,	0.19};

    private static final double[] stelajKeeping =  {0.03, 	0.05,	0.06,	0.1,	0.12,	0.14,
            0.17,	0.19,	0.2,	0.23,	0.25,	0.26};

    private static final double[] bmKeeping =      {0.04,	0.08,   0.11,	0.15,	0.18,	0.21,
            0.24,	0.27,	0.3,	0.3,	0.36,	0.36};




    public static double[] getShtabelDeliver() {
        return shtabelDeliver;
    }

    public static double[] getStelajDeliver() {
        return stelajDeliver;
    }

    public static double[] getBmDeliver() {
        return bmDeliver;
    }

    public static double[] getShtabelKeeping() {
        return shtabelKeeping;
    }

    public static double[] getStelajKeeping() {
        return stelajKeeping;
    }

    public static double[] getBmKeeping() {
        return bmKeeping;
    }
}
