#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <malloc.h>

struct stack {
	int *arr = (int*)malloc(sizeof(int)*1);
	int arr_size = 0;

	void push(int item) {
		arr = (int*)realloc(arr, sizeof(int*) * (++arr_size));
		arr[arr_size - 1] = item;
	}

	int pop(int idx = -1) {
		if (is_empty()) {
			printf("STACK IS EMPTY.\nTHIS -1 IS ERROR VALUE."); return -1;
		}
		if (idx >= arr_size || idx < 0) return arr[--arr_size];
		int res = arr[idx];
		while (++idx < arr_size) arr[idx - 1] = arr[idx];
		arr_size--;
		return res;
	}

	int is_empty() {
		return arr_size == 0;
	}

	void print_stack() {
		for (int i = 0; i < arr_size; i++) printf("%d ", arr[i]);
		printf("\n");
	}
};

int main() {
	stack k;
	k.push(3); k.print_stack();
	k.push(5); k.print_stack();
	k.push(7); k.print_stack();
	while (!k.is_empty()) printf("%d ", k.pop());
	k.pop();
}