package util.consoleUtil;

import java.util.Arrays;

public class ConsoleCanvas {
    private final ColorChar DEFAULT_CHAR = new ColorChar(' ', ConsoleColor.DEFAULT);
    private ColorChar[][] chars;
    private int[][] layer;
    private int nowLayer = 0;
    private ConsoleColor nowColor = ConsoleColor.DEFAULT;
    private final char lineFill = '\0';
    private int dottedInterval = 2;
    private boolean dottedFlag = false;
    private int dottedStatus = 0;

    public ConsoleCanvas(int width, int height) {
        chars = new ColorChar[height][width];
        layer = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                setChar(i, j, DEFAULT_CHAR);
            }
        }
    }

    public void setColor(ConsoleColor color) {
        this.nowColor = color;
    }

    public void setDottedInterval(int dottedInterval) {
        this.dottedInterval = dottedInterval;
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        this.drawLine(x1, y1, x2, y2, "solid");
    }

    public void drawLine(int x1, int y1, int x2, int y2, String type) {
        nowLayer++;
        if (type.equals("dotted")) {
            dottedFlag = true;
            dottedStatus = 0;
        }

        double w = Math.abs(x2 - x1) + 1;
        double h = Math.abs(y2 - y1) + 1;
        int wd = x2 - x1 > 0 ? 1 : -1;
        int hd = y2 - y1 > 0 ? 1 : -1;

        // 画线
        if (w == 1 || h == 1) {
            if (w == 1 && h == 1) {
                setLineDot(x1, y1);
            } else if (w == 1) {
                for (int i = y1; i != y2; i += hd) {
                    setLineDot(x1, i);
                }
            } else {
                for (int i = x1; i != x2; i += wd) {
                    setLineDot(i, y1);
                }
            }
        } else {
            int nx = x1;
            int ny = y1;
            boolean wh = w > h;
            double bl = wh ? h / w : w / h;
            int count = (int) (wh ? w : h);

            for (int i = 0; i < count - 1; i++) {
                setLineDot(nx, ny);
                if (wh) {
                    nx += wd;
                } else {
                    ny += hd;
                }
                double nw = Math.abs(x2 - nx) + 1;
                double nh = Math.abs(y2 - ny) + 1;
                double nowBl = wh ? (nh / nw) : (nw / nh);
                double chBl = wh ? (nh - 1) / nw : (nw - 1) / nh;
                if (Math.abs(nowBl - bl) > Math.abs(chBl - bl)) {
                    if (wh) {
                        ny += hd;
                    } else {
                        nx += wd;
                    }
                }
            }
            setLineDot(x2, y2);
        }

        if (type.equals("dotted")) {
            dottedFlag = false;
        }
    }

    private void setLineDot(int x, int y) {
        if (dottedFlag) {
            if (dottedStatus == 0) {
                setChar(x, y, new ColorChar(lineFill, nowColor));
            }
            dottedStatus = (dottedStatus + 1) % dottedInterval;
        } else {
            setChar(x, y, new ColorChar(lineFill, nowColor));
        }
    }

    public void drawRectangle(int x1, int y1, int x2, int y2) {
        nowLayer++;
        int t;
        if (x1 > x2) {
            t = x1;
            x1 = x2;
            x2 = t;
        }
        if (y1 > y2) {
            t = y1;
            y1 = y2;
            y2 = t;
        }
        for (int i = x1; i <= x2; i++) {
           for (int j = y1; j <= y2; j++) {
               setChar(i, j, new ColorChar(lineFill, nowColor));
           }
        }
    }

    public void writeText(int x, int y, String str) {
        nowLayer++;
        char[] chArr = str.toCharArray();
        for (int i = 0; i < chArr.length; i++) {
            writeChar(x, y + i, chArr[i]);
        }
    }

    private void writeChar(int x, int y, char ch) {
        chars[x][y] = new ColorChar(ch, nowColor);
    }

    public void clear() {
        for (ColorChar[] aChar : chars) {
            Arrays.fill(aChar, DEFAULT_CHAR);
        }
    }

    public void display() {
        for (ColorChar[] aChar : chars) {
            for (ColorChar colorChar : aChar) {
                if (colorChar.getValue() == lineFill) {
                    new ColorChar('█', colorChar.getColor()).show();
                } else {
                    colorChar.show();
                }
            }
            System.out.println();
        }
    }

    public void displayCompress() {
        for (int i = 1; i < chars.length; i += 2) {
            for (int j = 0; j < chars[i].length; j++) {
                ColorChar cc;
                ConsoleColor color;
                if (layer[i][j] == layer[i - 1][j]) {
                    if (chars[i][j].getColor() != DEFAULT_CHAR.getColor()) {
                        color = chars[i][j].getColor();
                    } else if (chars[i - 1][j].getColor() != DEFAULT_CHAR.getColor()) {
                        color = chars[i - 1][j].getColor();
                    } else {
                        color = DEFAULT_CHAR.getColor();
                    }
                } else {
                    color = layer[i][j] > layer[i - 1][j] ? chars[i][j].getColor() : chars[i - 1][j].getColor();
                }

                char value;
                if (specialChar(chars[i][j].getValue()) && specialChar(chars[i - 1][j].getValue())) {
                    if (layer[i][j] == layer[i - 1][j]) {
                        value = chars[i][j].getValue();
                    } else {
                        value = layer[i][j] > layer[i - 1][j] ? chars[i][j].getValue() : chars[i - 1][j].getValue();
                    }
                } else if (specialChar(chars[i][j].getValue())) {
                    value = chars[i][j].getValue();
                } else if (specialChar(chars[i - 1][j].getValue())) {
                    value = chars[i - 1][j].getValue();
                } else {
                    if (chars[i][j].getValue() == lineFill) {
                        if (chars[i - 1][j].getValue() == lineFill) {
                            value = '█';
                        } else {
                            value = '▄';
                        }
                    } else {
                        if (chars[i - 1][j].getValue() == lineFill) {
                            value = '▀';
                        } else {
                            value = ' ';
                        }
                    }
                }

                cc = new ColorChar(value, color);
                cc.show();
            }
            System.out.println();
        }
    }

    public void displayStretch() {
        for (ColorChar[] aChar : chars) {
            for (ColorChar colorChar : aChar) {
                if (colorChar.getValue() == lineFill) {
                    ColorChar cc = new ColorChar('█', colorChar.getColor());
                    cc.show();
                    cc.show();
                } else {
                    colorChar.show();
                    DEFAULT_CHAR.show();
                }
            }
            System.out.println();
        }
    }

    private void setChar(int x, int y, ColorChar ch) {
        chars[x][y] = ch;
        layer[x][y] = nowLayer;
    }

    private boolean specialChar(char ch) {
        return ch != lineFill && ch != DEFAULT_CHAR.getValue();
    }

    // 判断字符是否为中文
    private boolean isChineseCharacter(char c) {
        return c >= 0x4e00 && c <= 0x9fa5;
    }

    public static void main(String[] args) {
        ConsoleCanvas cc = new ConsoleCanvas(100, 40);
        cc.setColor(ConsoleColor.RED);
        cc.drawLine(1, 2, 8, 21);
        cc.setColor(ConsoleColor.YELLOW);
        cc.drawLine(10, 90, 10, 50);
        cc.setColor(ConsoleColor.WHITE);
        cc.writeText(5, 30, "console canvas");
        cc.setColor(ConsoleColor.GREEN);
        cc.drawRectangle(28, 3, 35, 60);
        cc.setColor(ConsoleColor.SKYBLUE);
        cc.drawRectangle(32, 56, 5, 64);
        cc.setColor(ConsoleColor.PURPLE);
        cc.drawLine(39, 0, 0, 99);
        cc.setColor(ConsoleColor.BLUE);
        cc.drawLine(33, 40, 13, 40);
        cc.displayCompress();
    }
}