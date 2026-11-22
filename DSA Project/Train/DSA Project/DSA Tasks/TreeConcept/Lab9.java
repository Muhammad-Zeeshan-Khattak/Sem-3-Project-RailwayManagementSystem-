
import java.util.Arrays;
class MergeSort{
    
    public static void sort(int [] arr,int left,int right){
           
        if(left >= right){
            return;

        }

        int mid = left + (right - left)/2;

        sort(arr,left,mid);
        sort(arr,mid+1,right);

        merge(arr,left,mid,right);


    }

    public static void merge(int arr[],int left, int mid,int right){
            int size1 = mid - left +1;
            int size2 = right - mid;

            int n1 [] = new int[size1];
            int n2 [] = new int[size2];

            for(int i = 0;  i< size1; i++){
                n1[i] = arr[left + i];
            }

            for(int i = 0; i<size2; i++){
                n2[i] = arr[mid + 1 + i];
            }

            int i = 0; int j = 0; int k = left;

            while (i < size1 && j < size2) {

    if (n1[i] <= n2[j]) {
        arr[k] = n1[i];
        i++;
    } else {
        arr[k] = n2[j];
        j++;
    }

    k++;
}

 while(i < size1 ){
    arr[k] = n1[i];
    i++;
    k++; 
 }

 while(j < size2){
     arr[k] = n2[j];

     j++;
     k++;
 }


    }



    public static void main(String[] args) {
        int arr [] = {2,12,4,8,15,20,16,11,10};

        System.out.println("*** Before Sorting ***"); 
        System.out.println(Arrays.toString(arr) +" ");

        sort(arr,0,arr.length-1);
        
        System.out.println("*** Before Sorting ***"); 
        System.out.println(Arrays.toString(arr) +" ");


    }



}