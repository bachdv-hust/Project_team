package com.company;

import java.util.Scanner;

public class ScannerUtils {
    private static ScannerUtils instance;
    private Scanner scanner;

    private ScannerUtils() {
    }

    public static ScannerUtils getInstance() {
        if (instance == null) {
            instance = new ScannerUtils();
        }
        return instance;
    }


    public int nextInt(int from, int to) {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        int nextInt;
        System.out.println("Enter Int from "+from +" to " +to+" : ");
        do {
            nextInt = scanner.nextInt();
            if (nextInt < from || nextInt > to){
                System.out.println("Enter again.Invalid format: ");
            }
        } while (nextInt < from || nextInt > to);
        return nextInt;
    }

    public String nextYesNoQues() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        String yesNoAns;
        scanner.nextLine();
        System.out.println("Enter (Y/[N])?: ");
        do {
            yesNoAns = scanner.nextLine();
            if (!yesNoAns.equalsIgnoreCase("y") && !yesNoAns.equalsIgnoreCase("n")) {
                System.out.println("Enter again.Invalid format (Y/[N])? : ");
            }
        } while (!yesNoAns.equalsIgnoreCase("y") && !yesNoAns.equalsIgnoreCase("n"));
        return yesNoAns;
    }
}
