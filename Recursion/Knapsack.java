public class Knapsack {
    static int max(int a, int b)  
    {  
      return (a > b) ? a : b;  
    }
    static int find(int W, int wt[], int val[], int n) 
    { 
        // Base Case 
        if (n == 0 || W == 0) 
            return 0; 
   
        if (wt[n - 1] > W) 
            return find(W, wt, val, n - 1); 
  
        else
            return max(val[n - 1] 
                       + find(W - wt[n - 1], wt, 
                                  val, n - 1), 
                       find(W, wt, val, n - 1)); 
    } 
    public static void main(String[] args) {
        int[] value = {60, 100, 120};
        int[] weight = {10, 20, 30};
        int W = 50;
        System.out.print(find(W, weight, value, value.length));
    }
}
