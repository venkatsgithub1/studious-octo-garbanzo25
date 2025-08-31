package dp.ai;

import java.util.HashMap;
import java.util.Map;

public class RodCut {
    public static void main(String[] args) {
        System.out.println(findMaxRevenue(new int[]{1,5,8,9,10,17, 17, 20, 24, 30}, 7));
        System.out.println(findMaxRevenue(new int[]{1,5,8,9,10,17, 17, 20, 24, 30}, 4));
        System.out.println(findMaxRevenue(new int[]{1,5,8,9,10,17, 17, 20, 24, 30}, 5));
        System.out.println(findMaxRevenue(new int[]{1,5,8,9,10,17, 17, 20, 24, 30}, 8));
        System.out.println(findMaxRevenueWMem(new int[]{1,5,8,9,10,17, 17, 20, 24, 30}, 5, new HashMap<>()));
    }

    private static int findMaxRevenue(int[] prices, int rodLength) {
        if (rodLength <= 0) {
            return 0;
        }
        int maxRevenue = 0;
        for (int i = 1; i <= rodLength; i++) {
            maxRevenue = Integer.max(maxRevenue, prices[i-1] + findMaxRevenue(prices, rodLength - i));
        }
        return maxRevenue;
    }

    private static int findMaxRevenueWMem(int[] prices, int rodLength, Map<Integer, Integer> lenRevStore) {
        if (rodLength <= 0) {
            return 0;
        }
        if (lenRevStore.containsKey(rodLength)) {
            return lenRevStore.get(rodLength);
        }
        var maxRevenue = computeBalancePieceRevenue(prices, rodLength);
        lenRevStore.put(rodLength, maxRevenue);
        return maxRevenue;
    }

    private static int computeBalancePieceRevenue(int[] prices, int rodLength) {
        int maxRevenue = 0;
        for (int i = 1; i <= rodLength; i++) {
            maxRevenue = Integer.max(maxRevenue, prices[i-1] + findMaxRevenue(prices, rodLength - i));
        }
        return maxRevenue;
    }
}