public class SubsetSum {
    public static boolean find(int[] s, int sum, int n){
        // Initialisation
        if(sum==0)
            return true;
        if(n==0)
            return false;

        //driver code 
        if(s[n-1]<=sum){
            return(find(s, sum-s[n-1], n-1)||find(s, sum, n-1));
        }
        else{
            return(find(s, sum, n-1));
        }
    }
    public static void main(String[] args) {
        int[] s = {3, 34, 4, 12, 5, 2};
        int sum = 1;
        System.out.println(find(s, sum, s.length));

    }
}
