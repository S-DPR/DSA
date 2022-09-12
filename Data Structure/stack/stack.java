package Main;

class CStack {
    int arr_size = 0;
    int[] arr;

    public CStack() {
        arr = new int[arr_size];
    }

    public CStack(int size) {
        arr = new int[size];
    }

    void push(int item) {
        arr = new int[++arr_size];
        arr[arr_size - 1] = item;
    }

    int pop() {
        if (isEmpty()) {
            System.out.println("STACK IS EMPTY!");
            return -1;
        }
        return arr[--arr_size];
    }

    int pop(int idx) {
        if (isEmpty()) {
            System.out.println("STACK IS EMPTY!");
            return -1;
        }
        if (idx < 0 || idx >= arr_size)
            return arr[--arr_size];
        int res = arr[idx];
        while (++idx < arr_size)
            arr[idx - 1] = arr[idx];
        arr_size--;
        return res;
    }

    private boolean isEmpty() {
        return arr_size == 0;
    }
}

public class Main {
    public static void main(String[] args) {
        CStack k = new CStack();
        k.push(3);
        k.push(2);
        System.out.println(k.pop());
    }
}