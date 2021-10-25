package week1;

/**
 * 问题提出：公元前5世纪末，中国古代数学家张丘建在他的《算经》中提出了著名的 “百钱买百鸡问题”：
 * 鸡翁一，值钱五，鸡母一，值钱三，鸡雏三，值钱一，百钱买百鸡，问翁、母、雏各几何？
 * 即一百个铜钱买了一百只鸡，其中公鸡一只5钱、母鸡一只3钱，雏鸡一钱3只，问一百只鸡中公鸡、母鸡、雏鸡各多少?
 * 算法的伪代码如下：
 * `for x = 0 to 100
 * `    for y = 0 to 100
 * `        for z = 0 to 100
 * `            if  (x+y+z=100)  and  (5*x + 3*y + z/3 = 100)  then
 * `                System.out.println("  "+x+"  "+y+"  "+z)
 * `            end if
 * 实验要求：对上述算法做出改进以提高算法的效率，要求将算法的时间复杂性由Ο(n3)降为 Ο(n2)，并将改进的算法编程实现。
 */
public class Week1_2 {
    public static void main(String[] args) {
        for (int z = 0; z <= 100; z += 3) {
            for (int y = 0; y <= 100 - z; y++) {
                int x = 100 - y - z;
                if (5 * x + 3 * y + z / 3 == 100) {
                    System.out.println(x + " " + y + " " + z);
                }
            }
        }
    }
}
