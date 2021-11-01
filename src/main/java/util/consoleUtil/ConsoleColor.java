package util.consoleUtil;

public enum ConsoleColor {
    DEFAULT("default"),
    BLACK("30"),
    RED("31"),
    GREEN("32"),
    YELLOW("33"),
    BLUE("34"),
    PURPLE("35"),
    SKYBLUE("36"),
    GREY("37"),
    WHITE("39");

    private final String value;

    private ConsoleColor(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}