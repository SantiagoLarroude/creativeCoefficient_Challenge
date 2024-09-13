public class Ej2 {
    private static char getColors(char c1, char c2) {
        if (c1 == c2) return c1;

        if ((c1 == 'R' && c2 == 'G') || (c2 == 'R' && c1 == 'G')) return 'B';
        if ((c1 == 'B' && c2 == 'G') || (c2 == 'B' && c1 == 'G')) return 'R';

        return 'G';
    }

    public static String triangle(String str) {
        if (str.isEmpty()) throw new IllegalArgumentException("The string is empty");

        if (str.length() == 1) return str;

        char c;
        StringBuilder str2 = new StringBuilder();

        for (int i = 1; i < str.length(); i++) {
            c = getColors(str.charAt(i - 1), str.charAt(i));
            str2.append(c);
        }

        return triangle(str2.toString());
    }

    public static void main(String[] args) {
        System.out.println((triangle("B").equals("B")));
        System.out.println((triangle("GB").equals("R")));
        System.out.println((triangle("RRR").equals("R")));
        System.out.println((triangle("RGBG").equals("B")));
        System.out.println((triangle("RBRGBRB").equals("G")));
        System.out.println((triangle("RBRGBRBGGRRRBGBBBGG").equals("G")));
    }
}
