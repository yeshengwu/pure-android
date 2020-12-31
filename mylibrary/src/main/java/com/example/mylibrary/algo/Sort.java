package com.example.mylibrary.algo;

public class Sort {

    public void swap(int a[], int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public int partition(int a[], int low, int high) {
        int pivot, p_pos, i;
        p_pos = low;
        pivot = a[p_pos];
        for (i = low + 1; i <= high; i++) {
            if (a[i] > pivot) {
                p_pos++;
                swap(a, p_pos, i);
            }
        }
        swap(a, low, p_pos);
        return p_pos;
    }

    public void quicksort(int a[], int low, int high) {
        int pivot;
        if (low < high) {
            pivot = partition(a, low, high);
            quicksort(a, low, pivot - 1);
            quicksort(a, pivot + 1, high);
        }

    }

    public static void main(String args[]) {
        int vec[] = new int[] { 37, 47, 23, -5, 19, 56 };
        int temp;
        // 选择排序法(Selection Sort)
        long begin = System.currentTimeMillis();
        for (int k = 0; k < 1000000; k++) {
            for (int i = 0; i < vec.length; i++) {
                for (int j = i; j < vec.length; j++) {
                    if (vec[j] > vec[i]) {
                        temp = vec[i];
                        vec[i] = vec[j];
                        vec[j] = temp;
                    }
                }

            }
        }
        long end = System.currentTimeMillis();
        System.out.println("选择法用时为：" + (end - begin));
        // 打印排序好的结果
        for (int i = 0; i < vec.length; i++) {
            System.out.println(vec[i]);
        }
        // 冒泡排序法(Bubble Sort)
        begin = System.currentTimeMillis();
        for (int k = 0; k < 1000000; k++) {
            for (int i = 0; i < vec.length; i++) {
                for (int j = i; j < vec.length - 1; j++) {
                    if (vec[j + 1] > vec[j]) {
                        temp = vec[j + 1];
                        vec[j + 1] = vec[j];
                        vec[j] = temp;
                    }
                }

            }
        }
        end = System.currentTimeMillis();
        System.out.println("冒泡法用时为：" + (end - begin));
        // 打印排序好的结果
        for (int i = 0; i < vec.length; i++) {
            System.out.println(vec[i]);
        }

        // 插入排序法(Insertion Sort)
        begin = System.currentTimeMillis();
        for (int k = 0; k < 1000000; k++) {
            for (int i = 1; i < vec.length; i++) {
                int j = i;
                while (vec[j - 1] < vec[i]) {
                    vec[j] = vec[j - 1];
                    j--;
                    if (j <= 0) {
                        break;
                    }
                }
                vec[j] = vec[i];
            }
        }
        end = System.currentTimeMillis();
        System.out.println("插入法用时为：" + (end - begin));
        // 打印排序好的结果
        for (int i = 0; i < vec.length; i++) {
            System.out.println(vec[i]);
        }

        // 快速排序法(Quick Sort)

        Sort s = new Sort();
        begin = System.currentTimeMillis();
        for (int k = 0; k < 1000000; k++) {
            s.quicksort(vec, 0, 5);
        }
        end = System.currentTimeMillis();
        System.out.println("快速法用时为：" + (end - begin));
        // 打印排序好的结果
        for (int i = 0; i < vec.length; i++) {
            System.out.println(vec[i]);
        }
    }

}
