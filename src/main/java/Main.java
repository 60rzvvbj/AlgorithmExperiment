import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
    }
}

class Solution {
    public int lengthOfLongestSubstring(String s) {
        char[] chars = s.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        int start = 0;
        int max = 0;
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            Integer index = map.get(ch);
            if (index != null) {
                max = Math.max(i - start, max);
                for (int j = start; j <= index; j++) {
                    map.remove(chars[j]);
                }
                start = index + 1;
            }
            map.put(ch, i);
        }
        max = Math.max(chars.length - start, max);
        return max;
    }
}
