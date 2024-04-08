package input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

public class LectureClavier {

    private final static InputStreamReader input = new InputStreamReader(System.in);
    private final static BufferedReader bufferedReader = new BufferedReader(input);

    public static String lireString() {
        String ligne = null;
        try {
            ligne = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("Vous devez saisir un texte");
            return lireString();
        }
        return ligne;
    }

    public static int lireInt() {
        int input = 0;
        try {
            input = Integer.parseInt(lireString());
        } catch (NumberFormatException nfe) {
            System.out.println("Vous devez saisir un nombre");
            return lireInt();
        }
        return input;
    }

    public static int lireInt(int min, int max) {
        int input = lireInt();

        if (input < min && input > max) {
            System.out.println("Vous devez saisir un nombre entre " + min + " et " + max);
            return lireInt(min, max);
        }

        return input;

    }

    public static long lireLong() {
        long input = 0;
        try {
            input = Long.parseLong(lireString());
        } catch (NumberFormatException nfe) {
            System.out.println("Vous devez saisir un nombre");
            return lireLong();
        }
        return input;
    }


    public static LocalDate lireDate() {
        String date = lireString();
        int annee = Integer.parseInt(date.substring(0, 4));
        int mois = Integer.parseInt(date.substring(4, 6));
        int jour = Integer.parseInt(date.substring(6));
        return LocalDate.of(annee, mois, jour);
    }

    public static double lireDouble() {
        return Double.parseDouble(lireString());
    }
}
