/**
 * @file Proj3.java
 * @description This program implements and analyzes various sorting algorithms
 *              (Bubble Sort, Merge Sort, Quick Sort, Heap Sort, and Odd-Even
 *              Transposition Sort) on GDP data, measuring their performance.
 * @author Ravi Ingle
 * @date November 13, 2025
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Proj3 {

    /**
     * Merge Sort - Recursively divides and sorts the array
     * @param a The ArrayList to sort
     * @param left The left index
     * @param right The right index
     */
    public static <T extends Comparable<T>> void mergeSort(ArrayList<T> a, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(a, left, mid);
            mergeSort(a, mid + 1, right);
            merge(a, left, mid, right);
        }
    }

    /**
     * Merge helper method - Merges two sorted subarrays
     * @param a The ArrayList to merge
     * @param left The left index
     * @param mid The middle index
     * @param right The right index
     */
    public static <T extends Comparable<T>> void merge(ArrayList<T> a, int left, int mid, int right) {
        ArrayList<T> temp = new ArrayList<>();
        int i = left;
        int j = mid + 1;

        // Merge the two halves
        while (i <= mid && j <= right) {
            if (a.get(i).compareTo(a.get(j)) <= 0) {
                temp.add(a.get(i));
                i++;
            } else {
                temp.add(a.get(j));
                j++;
            }
        }

        // Copy remaining elements from left half
        while (i <= mid) {
            temp.add(a.get(i));
            i++;
        }

        // Copy remaining elements from right half
        while (j <= right) {
            temp.add(a.get(j));
            j++;
        }

        // Copy back to original array
        for (int k = 0; k < temp.size(); k++) {
            a.set(left + k, temp.get(k));
        }
    }

    /**
     * Quick Sort - Sorts using divide and conquer with partitioning
     * @param a The ArrayList to sort
     * @param left The left index
     * @param right The right index
     */
    public static <T extends Comparable<T>> void quickSort(ArrayList<T> a, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(a, left, right);
            quickSort(a, left, pivotIndex - 1);
            quickSort(a, pivotIndex + 1, right);
        }
    }

    /**
     * Partition helper method - Partitions array around a pivot
     * @param a The ArrayList to partition
     * @param left The left index
     * @param right The right index
     * @return The final position of the pivot
     */
    public static <T extends Comparable<T>> int partition(ArrayList<T> a, int left, int right) {
        T pivot = a.get(right);
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (a.get(j).compareTo(pivot) <= 0) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, right);
        return i + 1;
    }

    /**
     * Swap helper method - Swaps two elements in the ArrayList
     * @param a The ArrayList
     * @param i First index
     * @param j Second index
     */
    static <T> void swap(ArrayList<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    /**
     * Heap Sort - Sorts using heap data structure
     * @param a The ArrayList to sort
     * @param left The left index
     * @param right The right index
     */
    public static <T extends Comparable<T>> void heapSort(ArrayList<T> a, int left, int right) {
        int n = right - left + 1;

        // Build max heap
        for (int i = left + n / 2 - 1; i >= left; i--) {
            heapify(a, left, i, right);
        }

        // Extract elements from heap one by one
        for (int i = right; i > left; i--) {
            swap(a, left, i);
            heapify(a, left, left, i - 1);
        }
    }

    /**
     * Heapify helper method - Maintains heap property
     * @param a The ArrayList
     * @param start The start index of the heap
     * @param i The index to heapify
     * @param end The end index of the heap
     */
    public static <T extends Comparable<T>> void heapify(ArrayList<T> a, int start, int i, int end) {
        int largest = i;
        int left = start + 2 * (i - start) + 1;
        int right = start + 2 * (i - start) + 2;

        if (left <= end && a.get(left).compareTo(a.get(largest)) > 0) {
            largest = left;
        }

        if (right <= end && a.get(right).compareTo(a.get(largest)) > 0) {
            largest = right;
        }

        if (largest != i) {
            swap(a, i, largest);
            heapify(a, start, largest, end);
        }
    }

    /**
     * Bubble Sort - Sorts by repeatedly swapping adjacent elements
     * @param a The ArrayList to sort
     * @param size The size of the ArrayList
     * @return The number of comparisons made
     */
    public static <T extends Comparable<T>> int bubbleSort(ArrayList<T> a, int size) {
        int comparisons = 0;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                comparisons++;
                if (a.get(j).compareTo(a.get(j + 1)) > 0) {
                    swap(a, j, j + 1);
                }
            }
        }
        return comparisons;
    }

    /**
     * Odd-Even Transposition Sort - Parallel sorting algorithm
     * @param a The ArrayList to sort
     * @param size The size of the ArrayList
     * @return The number of comparison rounds made (parallel analysis)
     */
    public static <T extends Comparable<T>> int transpositionSort(ArrayList<T> a, int size) {
        int comparisons = 0;
        boolean isSorted = false;

        while (!isSorted) {
            isSorted = true;
            comparisons++;

            // Odd phase
            for (int i = 1; i < size - 1; i += 2) {
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    isSorted = false;
                }
            }

            // Even phase
            for (int i = 0; i < size - 1; i += 2) {
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    isSorted = false;
                }
            }
        }
        return comparisons;
    }

    /**
     * Reads GDP data from CSV file
     * @param filename The name of the CSV file
     * @param numLines The number of lines to read
     * @return ArrayList of gdp2025 objects
     */
    public static ArrayList<gdp2025> readGDPData(String filename, int numLines) throws IOException {
        ArrayList<gdp2025> data = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        int count = 0;

        // Skip header if present
        line = br.readLine();

        while ((line = br.readLine()) != null && count < numLines) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                String country = parts[0].trim();
                int gdp = Integer.parseInt(parts[1].trim());
                data.add(new gdp2025(country, gdp));
                count++;
            }
        }
        br.close();
        return data;
    }

    /**
     * Creates a deep copy of the ArrayList
     * @param original The original ArrayList
     * @return A copy of the ArrayList
     */
    public static ArrayList<gdp2025> copyList(ArrayList<gdp2025> original) {
        ArrayList<gdp2025> copy = new ArrayList<>();
        for (gdp2025 item : original) {
            copy.add(new gdp2025(item));
        }
        return copy;
    }

    /**
     * Main method - Runs the sorting algorithms and generates output
     */
    public static void main(String[] args) throws IOException {
        // Check command line arguments
        if (args.length != 3) {
            System.out.println("Usage: java Proj3 {dataset-file} {sorting-algorithm-type} {number-of-lines}");
            System.out.println("Algorithm types: bubble, merge, quick, heap, transposition");
            return;
        }

        String filename = args[0];
        String algorithm = args[1].toLowerCase();
        int numLines = Integer.parseInt(args[2]);

        // Read data
        ArrayList<gdp2025> originalData = readGDPData(filename, numLines);

        // Prepare three versions: sorted, shuffled, reversed
        ArrayList<gdp2025> sortedData = copyList(originalData);
        Collections.sort(sortedData);

        ArrayList<gdp2025> shuffledData = copyList(originalData);
        Collections.shuffle(shuffledData);

        ArrayList<gdp2025> reversedData = copyList(originalData);
        Collections.sort(reversedData, Collections.reverseOrder());

        // Prepare output files
        PrintWriter sortedOut = new PrintWriter(new FileWriter("sorted.txt"));
        PrintWriter analysisOut = new PrintWriter(new FileWriter("analysis.txt", true));

        // Variables for timing and comparisons
        long startTime, endTime, elapsedTime;
        int comparisons;

        System.out.println("========================================");
        System.out.println("Sorting Algorithm Performance Analysis");
        System.out.println("========================================");
        System.out.println("Algorithm: " + algorithm);
        System.out.println("Dataset size: " + numLines);
        System.out.println();

        // Run sorting based on algorithm type
        switch (algorithm) {
            case "bubble":
                // Sorted
                ArrayList<gdp2025> bubbleSorted = copyList(sortedData);
                startTime = System.nanoTime();
                comparisons = bubbleSort(bubbleSorted, bubbleSorted.size());
                endTime = System.nanoTime();
                elapsedTime = endTime - startTime;

                System.out.println("Bubble Sort - Already Sorted:");
                System.out.println("  Time: " + elapsedTime + " ns");
                System.out.println("  Comparisons: " + comparisons);
                analysisOut.println("bubble,Sorted," + numLines + ",Time," + elapsedTime);
                analysisOut.println("bubble,Sorted," + numLines + ",Comparisons," + comparisons);

                // Shuffled
                ArrayList<gdp2025> bubbleShuffled = copyList(shuffledData);
                startTime = System.nanoTime();
                comparisons = bubbleSort(bubbleShuffled, bubbleShuffled.size());
                endTime = System.nanoTime();
                elapsedTime = endTime - startTime;

                System.out.println("Bubble Sort - Shuffled:");
                System.out.println("  Time: " + elapsedTime + " ns");
                System.out.println("  Comparisons: " + comparisons);
                analysisOut.println("bubble,Shuffled," + numLines + ",Time," + elapsedTime);
                analysisOut.println("bubble,Shuffled," + numLines + ",Comparisons," + comparisons);

                // Reversed
                ArrayList<gdp2025> bubbleReversed = copyList(reversedData);
                startTime = System.nanoTime();
                comparisons = bubbleSort(bubbleReversed, bubbleReversed.size());
                endTime = System.nanoTime();
                elapsedTime = endTime - startTime;

                System.out.println("Bubble Sort - Reversed:");
                System.out.println("  Time: " + elapsedTime + " ns");
                System.out.println("  Comparisons: " + comparisons);
                analysisOut.println("bubble,Reversed," + numLines + ",Time," + elapsedTime);
                analysisOut.println("bubble,Reversed," + numLines + ",Comparisons," + comparisons);

                // Write sorted output
                sortedOut.println("=== Bubble Sort Results ===");
                for (gdp2025 item : bubbleSorted) {
                    sortedOut.println(item);
                }
                break;

            case "merge":
                // Sorted
                ArrayList<gdp2025> mergeSorted = copyList(sortedData);
                startTime = System.nanoTime();
                mergeSort(mergeSorted, 0, mergeSorted.size() - 1);
                endTime = System.nanoTime();
                elapsedTime = endTime - startTime;

                System.out.println("Merge Sort - Already Sorted:");
                System.out.println("  Time: " + elapsedTime + " ns");
                analysisOut.println("merge,Sorted," + numLines + ",Time," + elapsedTime);

                // Shuffled
                ArrayList<gdp2025> mergeShuffled = copyList(shuffledData);
                startTime = System.nanoTime();
                mergeSort(mergeShuffled, 0, mergeShuffled.size() - 1);
                endTime = System.nanoTime();
                elapsedTime = endTime - startTime;

                System.out.println("Merge Sort - Shuffled:");
                System.out.println("  Time: " + elapsedTime + " ns");
                analysisOut.println("merge,Shuffled," + numLines + ",Time," + elapsedTime);

                // Reversed
                ArrayList<gdp2025> mergeReversed = copyList(reversedData);
                startTime = System.nanoTime();
                mergeSort(mergeReversed, 0, mergeReversed.size() - 1);
                endTime = System.nanoTime();
                elapsedTime = endTime - startTime;

                System.out.println("Merge Sort - Reversed:");
                System.out.println("  Time: " + elapsedTime + " ns");
                analysisOut.println("merge,Reversed," + numLines + ",Time," + elapsedTime);

                // Write sorted output
                sortedOut.println("=== Merge Sort Results ===");
                for (gdp2025 item : mergeSorted) {
                    sortedOut.println(item);
                }
                break;

            case "quick":
                // Sorted
                ArrayList<gdp2025> quickSorted = copyList(sortedData);
                startTime = System.nanoTime();
                quickSort(quickSorted, 0, quickSorted.size() - 1);
                endTime = System.nanoTime();
                elapsedTime = endTime - startTime;

                System.out.println("Quick Sort - Already Sorted:");
                System.out.println("  Time: " + elapsedTime + " ns");
                analysisOut.println("quick,Sorted," + numLines + ",Time," + elapsedTime);

                // Shuffled
                ArrayList<gdp2025> quickShuffled = copyList(shuffledData);
                startTime = System.nanoTime();
                quickSort(quickShuffled, 0, quickShuffled.size() - 1);
                endTime = System.nanoTime();
                elapsedTime = endTime - startTime;

                System.out.println("Quick Sort - Shuffled:");
                System.out.println("  Time: " + elapsedTime + " ns");
                analysisOut.println("quick,Shuffled," + numLines + ",Time," + elapsedTime);

                // Reversed
                ArrayList<gdp2025> quickReversed = copyList(reversedData);
                startTime = System.nanoTime();
                quickSort(quickReversed, 0, quickReversed.size() - 1);
                endTime = System.nanoTime();
                elapsedTime = endTime - startTime;

                System.out.println("Quick Sort - Reversed:");
                System.out.println("  Time: " + elapsedTime + " ns");
                analysisOut.println("quick,Reversed," + numLines + ",Time," + elapsedTime);

                // Write sorted output
                sortedOut.println("=== Quick Sort Results ===");
                for (gdp2025 item : quickSorted) {
                    sortedOut.println(item);
                }
                break;

            case "heap":
                // Sorted
                ArrayList<gdp2025> heapSorted = copyList(sortedData);
                startTime = System.nanoTime();
                heapSort(heapSorted, 0, heapSorted.size() - 1);
                endTime = System.nanoTime();
                elapsedTime = endTime - startTime;

                System.out.println("Heap Sort - Already Sorted:");
                System.out.println("  Time: " + elapsedTime + " ns");
                analysisOut.println("heap,Sorted," + numLines + ",Time," + elapsedTime);

                // Shuffled
                ArrayList<gdp2025> heapShuffled = copyList(shuffledData);
                startTime = System.nanoTime();
                heapSort(heapShuffled, 0, heapShuffled.size() - 1);
                endTime = System.nanoTime();
                elapsedTime = endTime - startTime;

                System.out.println("Heap Sort - Shuffled:");
                System.out.println("  Time: " + elapsedTime + " ns");
                analysisOut.println("heap,Shuffled," + numLines + ",Time," + elapsedTime);

                // Reversed
                ArrayList<gdp2025> heapReversed = copyList(reversedData);
                startTime = System.nanoTime();
                heapSort(heapReversed, 0, heapReversed.size() - 1);
                endTime = System.nanoTime();
                elapsedTime = endTime - startTime;

                System.out.println("Heap Sort - Reversed:");
                System.out.println("  Time: " + elapsedTime + " ns");
                analysisOut.println("heap,Reversed," + numLines + ",Time," + elapsedTime);

                // Write sorted output
                sortedOut.println("=== Heap Sort Results ===");
                for (gdp2025 item : heapSorted) {
                    sortedOut.println(item);
                }
                break;

            case "transposition":
                // Sorted
                ArrayList<gdp2025> transpSorted = copyList(sortedData);
                comparisons = transpositionSort(transpSorted, transpSorted.size());

                System.out.println("Odd-Even Transposition Sort - Already Sorted:");
                System.out.println("  Comparisons: " + comparisons);
                analysisOut.println("transposition,Sorted," + numLines + ",Comparisons," + comparisons);

                // Shuffled
                ArrayList<gdp2025> transpShuffled = copyList(shuffledData);
                comparisons = transpositionSort(transpShuffled, transpShuffled.size());

                System.out.println("Odd-Even Transposition Sort - Shuffled:");
                System.out.println("  Comparisons: " + comparisons);
                analysisOut.println("transposition,Shuffled," + numLines + ",Comparisons," + comparisons);

                // Reversed
                ArrayList<gdp2025> transpReversed = copyList(reversedData);
                comparisons = transpositionSort(transpReversed, transpReversed.size());

                System.out.println("Odd-Even Transposition Sort - Reversed:");
                System.out.println("  Comparisons: " + comparisons);
                analysisOut.println("transposition,Reversed," + numLines + ",Comparisons," + comparisons);

                // Write sorted output
                sortedOut.println("=== Odd-Even Transposition Sort Results ===");
                for (gdp2025 item : transpSorted) {
                    sortedOut.println(item);
                }
                break;

            default:
                System.out.println("Invalid algorithm type. Use: bubble, merge, quick, heap, or transposition");
        }

        System.out.println();
        System.out.println("Results written to sorted.txt and analysis.txt");

        // Close output files
        sortedOut.close();
        analysisOut.close();
    }
}