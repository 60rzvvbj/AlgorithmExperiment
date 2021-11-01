package util.consoleUtil;

public class ColorChar {
    private final char value;
    private final ConsoleColor color;

    public ColorChar(char value, ConsoleColor color) {
        this.value = value;
        this.color = color;
    }

    public char getValue() {
        return value;
    }

    public ConsoleColor getColor() {
        return color;
    }

    public void show() {
        if (this.color == ConsoleColor.DEFAULT) {
            System.out.print(this.value);
        } else {
            System.out.print("\033[1;" + this.color.getValue() + "m" + this.value + "\033[0m");
        }
    }
}
