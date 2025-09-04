package dp.ai;

import java.util.HashMap;
import java.util.Map;

public class RodCut {
    public static void main(String[] args) {
        System.out.println(findMaxRevenue(new int[]{1, 5, 8, 9, 10, 17, 17, 20, 24, 30}, 7));
        System.out.println(findMaxRevenue(new int[]{1, 5, 8, 9, 10, 17, 17, 20, 24, 30}, 4));
        System.out.println(findMaxRevenue(new int[]{1, 5, 8, 9, 10, 17, 17, 20, 24, 30}, 5));
        System.out.println(findMaxRevenue(new int[]{1, 5, 8, 9, 10, 17, 17, 20, 24, 30}, 8));
        System.out.println(findMaxRevenueWMem(new int[]{1, 5, 8, 9, 10, 17, 17, 20, 24, 30}, 5, new HashMap<>()));
        System.out.println(findMaxRevenueWTab(new int[]{1, 5, 8, 9, 10, 17, 17, 20, 24, 30}, 5));
        Map<Integer, int[]> paths = new HashMap<>();
        System.out.println(findMaxRevenueWTabPath(new int[]{1, 5, 8, 9, 10, 17, 17, 20, 24, 30}, 5, paths));
        paths.forEach((pathx, value) -> {
            System.out.println("path: "+pathx+" 1:"+value[0]+", 2:"+value[1]);
        });
    }

    private static int findMaxRevenue(int[] prices, int rodLength) {
        if (rodLength <= 0) {
            return 0;
        }
        int maxRevenue = 0;
        for (int i = 1; i <= rodLength; i++) {
            maxRevenue = Integer.max(maxRevenue, prices[i - 1] + findMaxRevenue(prices, rodLength - i));
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
            maxRevenue = Integer.max(maxRevenue, prices[i - 1] + findMaxRevenue(prices, rodLength - i));
        }
        return maxRevenue;
    }

    private static int findMaxRevenueWTab(int[] prices, int rodLength) {
        int[] revenueStore = new int[rodLength + 1];
        for (int currLen = 1; currLen <= rodLength; currLen++) {
            int currLenMaxRevenue = Integer.MIN_VALUE;
            for (int cutAt = 1; cutAt <= currLen; cutAt++) {
                currLenMaxRevenue = Integer.max(currLenMaxRevenue, prices[currLen - cutAt] + revenueStore[cutAt - 1]);
            }
            revenueStore[currLen] = currLenMaxRevenue;
        }
        return revenueStore[rodLength];
    }

    private static int findMaxRevenueWTabPath(int[] prices, int rodLength, Map<Integer, int[]> paths) {
        int[] revenueStore = new int[rodLength + 1];
        for (int currLen = 1; currLen <= rodLength; currLen++) {
            int currLenMaxRevenue = Integer.MIN_VALUE;
            for (int cutAt = 1; cutAt <= currLen; cutAt++) {
                int firstPiece = currLen - cutAt + 1;
                int secondPiece = cutAt - 1;
                int currRevenue = prices[firstPiece - 1] + revenueStore[secondPiece];
                if (currLenMaxRevenue < currRevenue) {
                    currLenMaxRevenue = currRevenue;
                    paths.put(currLen, new int[]{firstPiece, secondPiece});
                }
            }
            revenueStore[currLen] = currLenMaxRevenue;
        }
        System.out.println(paths);
        return revenueStore[rodLength];
    }
}