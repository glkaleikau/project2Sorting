package sorting;

import java.io.*;
import java.util.LinkedList;
import java.util.Iterator;

/**  A class that implements SortingInterface. Has various methods
 *   to sort a list of elements. */
public class SortingImplementation implements SortingInterface {
//test to push code
    /**
     * Sorts the sublist of the given list (from lowindex to highindex)
     * using insertion sort
     * @param array array of Comparable-s
     * @param lowindex the beginning index of a sublist
     * @param highindex the end index of a sublist
     * @param reversed if true, the list should be sorted in descending order
     */
    @Override
    public void insertionSort(Comparable[] array, int lowindex, int highindex, boolean reversed) {
        Comparable current;
        int j;
        //puts the list in descending order
        while(!reversed){
            for(int i =lowindex; i<highindex+1; i++){
                current = array[i];
                j = i - 1;
                while(j>=0 && array[j].compareTo(current) < 0){
                    array[j+1] = array[j];
                    j--;
                }
                array[j+1] = current;
            }
            reversed = true;
        }
        //if list in descending order, reverse
        if(reversed){
            int y;
            for(int x=0; x<highindex+1; x++){
                current = array[x];
                y = x-1;
                while(y>=0 && array[y].compareTo(current) > 0){
                    array[y+1] = array[y];
                    y--;
                }
                array[y+1] = current;
            }
        }
    }
    /**
     * Sorts a given array of 2^k elements using iterative
     * (non-recursive) merge sort.
     * @param array array to sort
     */
    @Override
    public void iterativeMergeSort(Comparable[] array){
        Comparable[] second = new Comparable[array.length];
        int low, high, mid;
        low=0;
        high = array.length -1;
        if(low>=high)
            return;
        for (int i = 1; i <= array.length-1; i = 2*i)
        {
            for (low = 0; low < array.length-1; low += 2*i)
            {
                mid = Math.min(low + i - 1, array.length-1);
                high = Math.min(low + 2*i - 1, array.length-1);
                merge(array, second, low, mid, high);
            }
        }
    }
    public static void merge(Comparable[] arr, Comparable[] temp, int low, int mid, int high) {
        int k = low;
        int i = low;
        int j = mid + 1;
        while (k <= high) {
            if (i > mid) {// ran out of elements in the i sublist
                temp[k] = arr[j];
                k++;
                j++;
            } else if (j > high) {// ran out of elements in the j sublist
                temp[k] = arr[i];
                k++;
                i++;
            } else if (arr[i].compareTo(arr[j]) < 0) { // place arr[i] in temp, move i
                temp[k] = arr[i];
                k++;
                i++;
            } else {
                temp[k] = arr[j]; // place arr[j] in temp, move j
                k++;
                j++;
            }
        }
        // copy the result from temp back to arr
        for (k = low; k <= high; k++)
            arr[k] = temp[k];
    }



    /**
     * Sorts the sublist of the given list (from lowindex to highindex)
     * using the randomizedQuickSort
     * @param array array to sort
     * @param lowindex the beginning index of a sublist
     * @param highindex the end index of a sublist
     */
    @Override
    public void randomizedQuickSort(Comparable[] array, int lowindex, int highindex) {
        int pivot;
        if(lowindex<highindex){
            pivot = partition(array, lowindex, highindex);
            randomizedQuickSort(array, lowindex, pivot-1);
            randomizedQuickSort(array,pivot+1, highindex);
        }
    }
    static int partition(Comparable arr[], int low, int high) {
        int mid = (low + high) / 2; // index of the pivot
        // System.out.println("pivot:" + arr[mid]);

        // swap pivot in the last position (high)
        Comparable pivot  = arr[mid];
        arr[mid] = arr[high];
        arr[high] = pivot;

        int i = low;
        int j = high - 1;
        while (i <= j) {
            while (i < high && arr[i].compareTo(pivot) < 0){
                i++;
            }

            while (j >= low && arr[j].compareTo(pivot) >= 0 ) {
                j--;
            }

            if (i < j) {
                // swap values at indices i and j
                Comparable tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
                j--;
            }
        }

        // swap pivot back to index i
        Comparable tmp = arr[i];
        arr[i] = pivot;
        arr[high] = tmp;
        return i;
    }

    /**
     * Sorts a given sublist using hybrid sort, where the list is sorted
     * using randomized quick sort; when sublists get small (size=10 ?),
     * they are sorted using insertion sort.
     * @param array array of Comparable-s to sort
     * @param lowindex the beginning index of the sublist
     * @param highindex the end index of the sublist
     */
    @Override
    public void hybridSort(Comparable[] array, int lowindex, int highindex) {
        while(lowindex < highindex){
            if(highindex - lowindex < 10){
                insertionSort(array, lowindex, highindex, false);
                return;
            }
            else{
                int piv = partition(array, lowindex, highindex);
                if(piv - lowindex < highindex - piv){
                    hybridSort(array, lowindex, piv - 1);
                    lowindex = piv + 1;
                }
                else{
                    hybridSort(array, piv + 1, highindex);
                    highindex = piv - 1;
                }
            }
        }
    }

    /**
     * Sorts a sub-array of records using bucket sort.
     * @param array array of records
     * @param lowindex the beginning index of the sublist to sort
     * @param highindex the end index of the sublist to sort
     * @param reversed if true, sort in descending (decreasing) order, otherwise ascending
     */
    @Override
    public void bucketSort(Elem[] array, int lowindex, int highindex, boolean reversed) {
        Elem[] temp = new Elem[array.length];
        int highest = array[0].key();
        int lowest = highest;
        int i;
        for (i = 0; i <= highindex; i++) {
            if (array[i].key() > highest) {
                highest = array[i].key();
            } else if (array[i].key() < lowest)
                lowest = array[i].key();
        }
        int bucketNum = (highest / 10) + 1;
        LinkedList[] buckets = new LinkedList[bucketNum];
        for (i = 0; i < bucketNum; i++) {
            buckets[i] = new LinkedList<Integer>();
        }
        for (i = 0; i < array.length; i++) {
            buckets[array[i].key()/10].addLast(array[i]);
        }
        //use counter from lowest to highest to insert back into array
        int insertIndex = 0;
        for (i = 0; i < bucketNum; i++) {
            Iterator<Elem> it = buckets[i].iterator();
            while (it.hasNext()){
                Elem elem = it.next();
                temp[insertIndex] = elem;
                insertIndex++;
            }
        }
        boolean sorted = false;
        while(!sorted){
            for(int j =0; j<array.length; j++) {
                for (i = 0; i < array.length; i++) {
                    if(temp[i].key() <= lowest){
                        array[j] = temp[i];
                    }
                }
            }
            sorted = true;
        }
    }

    /**
     * Implements external sort method
     * @param inputFile The file that contains the input list
     * @param outputFile The file where to output the sorted list
     * @param k number of elements that fit into memory at once
     * @param m number of chunks
     */
    public void externalSort(String inputFile, String outputFile, int k, int m) {
        PrintWriter[] writers = new PrintWriter[m];
        try(BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            for(int i=0; i<m; i++){
                try (PrintWriter pw = new PrintWriter(outputFile)){
                    writers[i] = pw;
                    for(int j=0; j<k; j++){
                        line = br.readLine();
                        pw.println(line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read from the file: " + inputFile);
        }

    }
}