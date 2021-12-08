// 这个是平时做leetcode的时候用的，不用管它

import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[][] arr = {{1, 2, 5}, {2, 3, 8}, {1, 5, 10}};
        new Solution().findAllPeople(6, arr, 1);
    }
}

class Solution {
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        boolean[] flag = new boolean[n];
        flag[0] = true;
        flag[firstPerson] = true;
        Map<Integer, TimeList> map = new HashMap<>();
        for (int i = 0; i < meetings.length; i++) {
            int time = meetings[i][2];
            TimeList tl = map.get(time);
            if (tl == null) {
                tl = new TimeList(time);
                tl.list.offer(new int[]{meetings[i][0], meetings[i][1]});
                map.put(time, tl);
            } else {
                tl.list.offer(new int[]{meetings[i][0], meetings[i][1]});
            }
        }
        TimeList[] timeLists = map.values().toArray(new TimeList[0]);
        Arrays.sort(timeLists, new Comparator<TimeList>() {
            @Override
            public int compare(TimeList o1, TimeList o2) {
                return o1.time - o2.time;
            }
        });
        for (int i = 0; i < timeLists.length; i++) {
            TimeList tl = timeLists[i];
            while (true) {
                boolean s = false;
                int len = tl.list.size();
                for (int j = 0; j < len; j++) {
                    int[] arr = tl.list.poll();
                    if (arr == null) {
                        break;
                    }
                    if (flag[arr[0]] ^ flag[arr[1]]) {
                        flag[arr[0]] = true;
                        flag[arr[1]] = true;
                        s = true;
                    } else {
                        if (flag[arr[0]]) {
                            s = true;
                        } else {
                            tl.list.offer(arr);
                        }
                    }
                }
                if (!s) {
                    break;
                }
            }
        }
        List<Integer> res = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (flag[i]) {
                res.add(i);
            }
        }
        return res;
    }
}

class TimeList {
    public int time;
    public Queue<int[]> list;

    public TimeList(int time) {
        this.time = time;
        this.list = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "TimeList{" +
                "time=" + time +
                '}';
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

