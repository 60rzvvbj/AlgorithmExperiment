package util.consoleUtil;

public class ColorChar {
    public static final String DEFAULT = "default";
    public static final String BLACK = "30";
    public static final String RED = "31";
    public static final String GREEN = "32";
    public static final String YELLOW = "33";
    public static final String BLUE = "34";
    public static final String PURPLE = "35";
    public static final String SKYBLUE = "36";
    public static final String GREY = "37";
    public static final String WHITE = "39";

    private final char value;
    private final String color;

    public ColorChar(char value, String color) {
        this.value = value;
        this.color = color;
    }

    public char getValue() {
        return value;
    }

    public String getColor() {
        return color;
    }

    public void show() {
        if (this.color == ColorChar.DEFAULT) {
            System.out.print(this.value);
        } else {
            System.out.print("\033[1;" + this.color + "m" + this.value + "\033[0m");
        }
    }
}
