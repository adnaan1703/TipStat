package com.kryptonapps.kon_el.trial.api;

public class EthnicGroup {

    public static String map(int op) {

        switch (op) {

            case 0:
                return "Asian";
            case 1:
                return "Indian";
            case 2:
                return "African American";
            case 3:
                return "Asian American";
            case 4:
                return "European";
            case 5:
                return "British";
            case 6:
                return "Jewish";
            case 7:
                return "Latino";
            case 8:
                return "Native American";
            case 9:
                return "Arabic";
            default:
                return "none";

        }

    }

    public static boolean boolMap(int op) {

        if(op == 1)
            return true;
        else
            return false;
    }

}
