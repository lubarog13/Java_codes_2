package lubarog13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sort {
    public static Integer[] mergesort(Integer[] array1) {
        Integer[] buffer1 = Arrays.copyOf(array1, array1.length);
        Integer[] buffer2 = new Integer[array1.length];
        Integer[] result = mergesortInner(buffer1, buffer2, 0, array1.length);
        return result;
    }

    public  void sort(int[] A, int p, int r) {
        if(p<r) {
            int q  = (p+r) / 2;
            sort(A, p, q);
            sort(A, q+1, r);
            merge(A, p, q, r);
        }
    }

    public static Integer[] mergesortInner(Integer[] buffer1, Integer[] buffer2,
                                       int startIndex, int endIndex) {
        if (startIndex >= endIndex - 1) {
            return buffer1;
        }

        // уже отсортирован.
        int middle = startIndex + (endIndex - startIndex) / 2;
        Integer[] sorted1 = mergesortInner(buffer1, buffer2, startIndex, middle);
        Integer[] sorted2 = mergesortInner(buffer1, buffer2, middle, endIndex);

        // Слияние
        int index1 = startIndex;
        int index2 = middle;
        int destIndex = startIndex;
        Integer[] result = sorted1 == buffer1 ? buffer2 : buffer1;
        while (index1 < middle && index2 < endIndex) {
            result[destIndex++] = sorted1[index1] < sorted2[index2]
                    ? sorted1[index1++] : sorted2[index2++];
        }
        while (index1 < middle) {
            result[destIndex++] = sorted1[index1++];
        }
        while (index2 < endIndex) {
            result[destIndex++] = sorted2[index2++];
        }
        return result;
    }

    void merge(int[] array, int p, int q, int r) {
        int left = q - p + 1;
        int right = r - q;
        int B[] = new int[left];
        int C[] = new int[right];
        System.arraycopy(array, p, B, 0, left);
        for (int i = 0; i < right; i++)
            C[i] = array[q + i + 1];
        int leftIndex = 0;
        int rightIndex = 0;
        for (int i = p; i < r + 1; i++) {
            if (leftIndex < left && rightIndex < right) {
                if (B[leftIndex] < C[rightIndex]) {
                    array[i] = B[leftIndex];
                    leftIndex++;
                } else {
                    array[i] = C[rightIndex];
                    rightIndex++;
                }
            }
            else if (leftIndex < left) {
                array[i] = B[leftIndex];
                leftIndex++;
            }
            else if (rightIndex < right) {
                array[i] = C[rightIndex];
                rightIndex++;
            }
        }
    }
}
