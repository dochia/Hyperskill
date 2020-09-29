package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    private static String myText;
    private static int myWords;
    private static int mySentences;
    private static int myCharacters;
    private static int mySyllables;
    private static int myPoly;
    private static double scoreARI;
    private static double scoreFK;
    private static double scoreSMOG;
    private static double scoreCL;
    private static int[] ages = new int[]{0, 6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 24, 25, 25};
    private static int total = 0;
    private static int count = 0;

    private static void stage1(String text) {
        System.out.println(text.length() > 100 ? "HARD" : "EASY");
    }

    private static String[] getWords(String phrase) {
        return phrase.trim().split("\\W+");
    }

    private static void stage2(String text) {
        String[] phrases = text.split("\\.|\\?|!");
        String[] words;
        int count = 0;
        int total = 0;
        for (String phrase : phrases) {
            count++;
            words = getWords(phrase);
            total += words.length;
        }
        System.out.println((double) total / count > 10 ? "HARD" : "EASY");
    }

    private static void printBeautifully(String option, double score, boolean isCL) {
        int age = isCL ? ages[(int)Math.ceil(score)] : ages[(int)Math.floor(score)];
        System.out.printf("%s: %f (about %s year olds).%n", option, score, age);
    }

    private static void stage3(String text) {
        mySentences = text.split("[?!.]+").length;
        myWords = text.split("[ \\n\\t]").length;
        myCharacters = text.replaceAll("[ \\n\\t]", "").length();
        System.out.printf("The text is: %n%s%n", text);
        System.out.printf("Words: %d%n", myWords);
        System.out.printf("Sentences: %d%n", mySentences);
        System.out.printf("Characters: %d%n", myCharacters);
        double score = computeARI();
        String[] ages = new String[]{"", "5-6", "6-7", "7-9", "9-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16", "16-17", "17-18", "18-24", "24+"};
        System.out.printf("This text should be understood by %s year olds%n", ages[(int) Math.ceil(score)]);
    }

    private static double computeARI() {
        scoreARI = 4.71 * ((double) myCharacters / myWords) + 0.5 * ((double) myWords / mySentences) - 21.43;
        return scoreARI;
    }

    private static double computeFleschKincaid() {
        scoreFK = 0.39 * ((double) myWords / mySentences) + 11.8 * ((double) mySyllables / myWords) - 15.59;
        return scoreFK;
    }

    private static double computeSMOG() {
        scoreSMOG = 1.043 * Math.sqrt(myPoly * ((double) 30 / mySentences)) + 3.1291;
        return scoreSMOG;
    }

    private static double computeColemanLiau() {
        double l = (double)myCharacters * 100 / myWords;
        double s = (double)mySentences * 100 / myWords;
        scoreCL = 0.0588 * l - 0.296 * s - 15.8;
        return scoreCL;
    }

    private static void stage4(String text) {
        String[] words = text.toLowerCase().split("[ \\n\\t]");
        mySentences = text.split("[?!.]+").length;
        myWords = words.length;
        mySyllables = countSyllablesInText(words);
        myCharacters = text.replaceAll("[ \\n\\t]", "").length();
        myPoly = countPolyInText(words);

        System.out.printf("The text is: %n%s%n", text);
        System.out.printf("Words: %d%n", myWords);
        System.out.printf("Sentences: %d%n", mySentences);
        System.out.printf("Characters: %d%n", myCharacters);
        System.out.printf("Syllables: %d%n", mySyllables);
        System.out.printf("Polysyllables: %d%n", myPoly);

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");
        String option = sc.nextLine().trim();
        printBeautifully(option);
        if (option.equals("all"))
            System.out.printf("This test should be understood in average by %f year olds.%n", (double) total / count);
    }

    private static int countPolyInText(String[] words) {
        int count = 0;
        for (String word : words) {
            int syl = countSyllablesInWord(word);
            if (syl > 2) {
                count++;
            }
        }
        return count;
    }

    private static int countSyllablesInWord(String word) {
        word = word.replaceAll("e\\b", "")
                .replaceAll("you", "a")
                .replaceAll("[aeiouy]{2,}", "a")
                .replaceAll("[,.!?]", "");
        int count = word.replaceAll("[^aeiouy]", "").length();
        return count == 0 ? 1 : count;
    }

    private static int countSyllablesInText(String[] words) {
        int count = 0;
        for (String word : words) {
            count += countSyllablesInWord(word);
        }
        return count;
    }

    private static void printBeautifully(String option) {
        switch (option) {
            case "ARI":
                printBeautifully("Automated Readability Index", computeARI(), false);
                total += ages[(int) Math.floor(scoreARI)];
                count++;
                return;
            case "FK":
                printBeautifully("Flesch–Kincaid readability tests", computeFleschKincaid(), false);
                total += ages[(int) Math.floor(scoreFK)];
                count++;
                return;
            case "SMOG":
                printBeautifully("Simple Measure of Gobbledygook", computeSMOG(), false);
                total += ages[(int) Math.floor(scoreSMOG)];
                count++;
                return;
            case "CL":
                printBeautifully("Coleman–Liau index", computeColemanLiau(), true);
                total += ages[(int) Math.ceil(scoreCL)];
                count++;
                return;
            default:
                printBeautifully("ARI");
                printBeautifully("FK");
                printBeautifully("SMOG");
                printBeautifully("CL");
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            stage4(new String(Files.readAllBytes(Paths.get(args[0]))));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
