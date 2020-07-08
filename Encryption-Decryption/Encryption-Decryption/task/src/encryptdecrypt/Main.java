package encryptdecrypt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

abstract class Input {
    String getText() {
        return readText();
    }
    abstract String readText();
}
class DataFromFile extends Input {

    String file;
    DataFromFile(String file) {
        this.file = file;
    }
    @Override
    String readText() {
        try (Scanner sc = new Scanner(new File(file))) {
            return sc.nextLine();
        } catch (FileNotFoundException e) {
            System.out.printf("Error: Couldn't find file %s!\n", file);
        }
        return null;
    }

}
class DataFromLine extends Input {
    String text;
    DataFromLine(String text) {
        this.text = text;
    }
    @Override
    String readText() {
        return text;
    }
}


abstract class Output {
    abstract void writeText(String text);
}
class DataToFile extends Output {
    String file;

    DataToFile(String file) {
        this.file = file;
    }

    @Override
    void writeText(String text) {
        try (FileWriter fw = new FileWriter(new File(file))) {
            fw.write(text);
        } catch (IOException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }
}
class DataToLine extends Output {
    @Override
    void writeText(String text) {
        System.out.println(text);
    }
}

abstract class Algorithm {
    Input in;
    Output out;
    Algorithm(Input in, Output out) {
        this.in = in;
        this.out = out;
    }
    abstract void encode();
    abstract void decode();
}
class ShiftAlgorithm extends Algorithm {
    private int key;
    private String text;
    char[] alphabet;
    ShiftAlgorithm(Input in, int key, Output out) {
        super(in, out);
        this.key = key;
        this.text = in.getText();
        alphabet = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz".toCharArray();
    }
    private String transform(boolean isEnc) {
        int shift = isEnc ? key : 26 - key;
        StringBuilder sb = new StringBuilder();
        int idx;
        boolean isUpper;
        for (char c: text.toCharArray()) {
            isUpper = Character.isUpperCase(c);
            c = Character.toLowerCase(c);
            if (Character.isLetter(c)) {
                idx = (c - 97 + shift) % 26;
                sb.append(isUpper? Character.toUpperCase(alphabet[idx]) : alphabet[idx]);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @Override
    void encode() {
        super.out.writeText(transform(true));
    }
    @Override
    void decode() {
        super.out.writeText(transform(false));
    }
}
class UnicodeAlgorithm extends Algorithm {

    private int key;
    private String text;
    UnicodeAlgorithm(Input in, int key, Output out) {
        super(in, out);
        this.key = key;
        this.text = in.getText();
    }


    private String transform(boolean isEnc) {
        int shift = key;
        if (!isEnc) {
            shift = -key;
        }
        StringBuilder sb = new StringBuilder();
        for (char c: text.toCharArray()) {
            sb.append((char) (c + shift));
        }
        return sb.toString();
    }
    @Override
    void encode() {
        super.out.writeText(transform(true));
    }

    @Override
    void decode() {
        super.out.writeText(transform(false));
    }
}

class Factory {
    Map<String, String> args;
    Algorithm alg;
    void parseArguments(String[] arguments) {
        args = new HashMap<>();
        String param;
        String value;
        for (int i = 0; i < arguments.length - 1; i += 2) {
            param = arguments[i].substring(1);
            value = arguments[i + 1];
            args.put(param, value);
        }
        if (!args.containsKey("mode")) {
            args.put("mode", "enc");
        }
        if (!args.containsKey("key")) {
            args.put("key", "0");
        }
        if (!args.containsKey("data")) {
            args.put("data", "");
        }
        if (!args.containsKey("in")) {
            args.put("in", "");
        }
        if (!args.containsKey("out")) {
            args.put("out", "");
        }
        if (!args.containsKey("alg")) {
            args.put("alg", "shift");
        }
    }
    void createAlgorithm() {
        Input in;
        Output out;
        int key = Integer.parseInt(args.get("key"));
        if (!args.get("in").isEmpty()) {
            in = new DataFromFile(args.get("in"));
            if (!args.get("out").isEmpty()) {
                out = new DataToFile(args.get("out"));
                if (args.get("alg").equals("shift")) {
                    alg = new ShiftAlgorithm(in, key, out);
                } else {
                    alg = new UnicodeAlgorithm(in, key, out);
                }
            } else {
                out = new DataToLine();
                if (args.get("alg").equals("shift")) {
                    alg = new ShiftAlgorithm(in, key, out);
                } else {
                    alg = new UnicodeAlgorithm(in, key, out);
                }
            }
        } else {
            in = new DataFromLine(args.get("in"));
            if (!args.get("out").isEmpty()) {
                out = new DataToFile(args.get("out"));
                if (args.get("alg").equals("shift")) {
                    alg = new ShiftAlgorithm(in, key, out);
                } else {
                    alg = new UnicodeAlgorithm(in, key, out);
                }
            } else {
                out = new DataToLine();
                if (args.get("alg").equals("shift")) {
                    alg = new ShiftAlgorithm(in, key, out);
                } else {
                    alg = new UnicodeAlgorithm(in, key, out);
                }
            }
        }
    }
    void executeAlgorithm() {
        if (args.get("mode").equals("enc")) {
            alg.encode();
        } else {
            alg.decode();
        }
    }
}

public class Main {
    static void stage6(String[] pairs) {
        Factory factory = new Factory();
        factory.parseArguments(pairs);
        factory.createAlgorithm();
        factory.executeAlgorithm();
    }
    public static void main(String[] args) {
        stage6(args);
    }
}
