public class EqualSumPartition {
    public static int sum(int[] arr){
        int count = 0;
        for(int i =0; i<arr.length; i++){
            count = count + arr[i];
        }
        return count;
    }
    
    public static boolean find(int[] s, int sum, int n, int minus){
        // Initialisation
        if(sum==minus)
            return true;
        

        //driver code 
        if(n==0)
            return false;
        else 
            return (find(s, sum-s[n-1], n-1, minus+s[n-1])||find(s, sum, n-1, minus));
    }
    public static void main(String[] args) {
        int[] arr = {1, 5, 13, 5};
        int sum = sum(arr);
        if(sum%2!=0){
            System.out.println("Not Possible");
            return;
        }
        else{
            int minus = 0;
            System.out.println(find(arr, sum, arr.length, minus));
        }
    }
}
