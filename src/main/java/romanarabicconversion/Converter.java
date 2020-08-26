package romanarabicconversion;

import java.util.Arrays;

public class Converter {
    /**
     * A constant which holds a pattern of an Arabic literal.
     */
    public static final String ARABIC_PATTERN;

    /**
     * A constant which holds a pattern of a Roman literal.
     */
    public static final String ROMAN_PATTERN;

    static {
        ARABIC_PATTERN = "^([1-9])|([1-9]\\d)|([1-9]\\d{2})|([1-3]\\d{3})$";
        ROMAN_PATTERN = "^(M{0,3})?" +
                        "((CM)|(DC{0,3})|(CD)|C{0,3})?" +
                        "((XC)|(LX{0,3})|(XL)|(X{0,3}))?" +
                        "((IX)|(VI{0,3})|(IV)|(I{0,3}))?+$";
    }

    /**
     * An array which holds all possible Roman units.
     */
    private static final String[] romanUnits;

    /**
     * An array which holds all possible Roman tens.
     */
    private static final String[] romanTens;

    /**
     * An array which holds all possible Roman hundreds.
     */
    private static final String[] romanHundreds;

    /**
     * An array which holds all possible Roman thousands.
     */
    private static final String[] romanThousands;

    static {
        romanUnits = new String[]{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        romanTens = new String[]{"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        romanHundreds = new String[]{"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        romanThousands = new String[]{"", "M", "MM", "MMM"};
    }

    /**
     * An array which holds Roman numbers.
     */
    private static final char[] romanNumerals;

    /**
     * An array which holds Arabic correspondences of Roman numbers.
     */
    private static final int[] arabicCorrespondences;

    static {
        romanNumerals = new char[]{'C', 'D', 'I', 'L', 'M', 'V', 'X'};
        arabicCorrespondences = new int[]{100, 500, 1, 50, 1000, 5, 10};
    }

    /**
     * Converts a given literal to the needed literal depending on the input.
     *
     * @param toConvert
     * A String literal which represents either Arabic or Roman literal.
     *
     * @return the Object value with the result of conversion.
     *
     * @author svalyavakilia
     */
    public static Object convert(final String toConvert) {
        if (toConvert.matches(ARABIC_PATTERN))
            return convertFromArabicToRoman(Integer.parseInt(toConvert));

        return convertFromRomanToArabic(toConvert);
    }

    /**
     * Converts an Arabic literal to a Roman literal.
     *
     * @param inArabic
     * An Arabic literal of type int which will be converted
     * to a Roman literal of type String.
     *
     * @return a String value which represents the result of conversion
     * to a Roman literal.
     *
     * @author svalyavakilia
     */
    public static String convertFromArabicToRoman(final int inArabic) {
        final int thousands = inArabic / 1000;
        final int hundreds = (inArabic - thousands * 1000) / 100;
        final int tens = (inArabic - thousands * 1000 - hundreds * 100) / 10;
        final int units = inArabic - thousands * 1000 - hundreds * 100 - tens * 10;

        return romanThousands[thousands] +
               romanHundreds[hundreds] +
               romanTens[tens] +
               romanUnits[units];
    }

    /**
     * Converts an Arabic literal to a Roman literal.
     *
     * @param inRoman
     * A Roman literal of type String which will be converted
     * to an Arabic literal of type int.
     *
     * @return an int value which represents the result of conversion
     * to an Arabic literal.
     *
     * @author svalyavakilia
     */
    public static int convertFromRomanToArabic(final String inRoman) {
        int inArabic = 0;

        final char lastNumeral = inRoman.charAt(inRoman.length() - 1);
        final int lastNumeralIndex =
                Arrays.binarySearch(romanNumerals, lastNumeral);
        final int lastNumeralValue = arabicCorrespondences[lastNumeralIndex];

        inArabic += lastNumeralValue;

        int toCompareTo = lastNumeralValue;

        for (int i = inRoman.length() - 2; i >= 0; i--) {
            final char numeral = inRoman.charAt(i);
            final int numeralIndex =
                    Arrays.binarySearch(romanNumerals, numeral);
            final int numeralValue = arabicCorrespondences[numeralIndex];

            if (numeralValue < toCompareTo) {
                inArabic -= numeralValue;
            } else {
                inArabic += numeralValue;
                if (numeralValue > toCompareTo) toCompareTo = numeralValue;
            }
        }

        return inArabic;
    }
}