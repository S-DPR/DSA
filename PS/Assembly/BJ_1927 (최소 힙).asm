/*
1927번 최소 힙

두 가지 쿼리를 처리해보자.
1 x : 배열에 x를 넣는다.
2 : 배열에 있는 가장 작은 수를 출력하고 제거한다. 배열이 비어있을 경우 0을 출력한다.

옛날 2년전에 열심히 짰던 최소 힙을 어셈블리로 구현하기.
메모리에러 계속나서 고통스러웠던 기억이 있습니다.
C가 나왔을때 왜 편해졌다고 했는지 알게 해줬던 아주 고마운 문제..
*/
section .bss
	heap: resq 100005
	sz: resq 1
	idx: resq 1
	comp: resq 1
	left: resq 1
	right: resq 1
	Q: resq 1
	x: resq 1
	i: resq 1

section .data
	input: db "%d", 10, 0
	output: db "%d", 10, 0
 
section .text
	global main
	extern printf
	extern scanf

main:
	push rbp

	mov rdi, input
	mov rsi, Q
	xor rax, rax
	call scanf

	mov qword [sz], 0
	loop:
		cmp dword [Q], 0
		je loop_end

		mov rdi, input
		mov rsi, x
		xor rax, rax
		call scanf

		cmp dword [x], 0
		je t
		jmp f
		t:
			call heap_pop
			mov rdi, output
			mov rsi, rax
			call printf
			jmp next
		f:
			mov rdi, x
			call heap_push
			jmp next
		next:

		dec qword [Q]
		jmp loop
	loop_end:

	pop rbp
	mov rax, 0
	ret

heap_push:
	push rbp
	mov rbp, rsp

	inc qword [sz]
	mov rax, [rdi]
	mov qword [x], rax
	mov rax, qword [sz]
	mov qword [idx], rax			; idx는 현재 위치

	heap_push_loop:
		cmp qword [idx], 1
		je heap_push_loop_end		; 루트노드까지 올라왔으면 종료

		mov rax, qword [idx]
		mov qword [comp], rax
		shr qword [comp], 1			; comp는 부모노드의 위치

		mov rax, qword [comp]
		lea rax, [heap + rax * 8]
		mov rax, [rax]
		cmp rax, qword [x]
		jle heap_push_loop_end		; 부모노드 <= 현재노드 then break

		mov rax, qword [comp]
		mov rbx, qword [idx]
		lea rax, qword [heap + rax * 8]
		lea rbx, qword [heap + rbx * 8]
		mov rax, [rax]
		mov qword [rbx], rax		; 현재 노드에 부모노드 값 넣기

		mov rax, qword [comp]
		mov qword [idx], rax
		jmp heap_push_loop			; 현재 노드에 부모노드 넣고 loop
	heap_push_loop_end:
	
	mov rax, qword [idx]
	lea rax, qword [heap+rax*8]
	mov rbx, qword [x]
	mov qword [rax], rbx

	pop rbp
	ret

heap_pop:
	push rbp
	mov rbp, rsp
	
	cmp qword [sz], 0
	je zero_heap
	jmp exist
	zero_heap:
		mov qword [x], 0
		jmp return
		
	exist:
	lea rax, [heap+8]
	mov rax, [rax]
	mov qword [x], rax

	mov rax, qword [sz]
	lea rax, [heap + rax * 8]
	mov rax, [rax]
	mov r15, rax					; 비교 하는 수

	mov qword [idx], 1
	dec qword [sz]

	heap_pop_loop:
		mov rax, qword [idx]
		mov qword [left], rax
		shl qword [left], 1			; 좌측 자식 노드
		mov rax, qword [left]

		cmp qword [sz], rax
		jl heap_pop_loop_end		; 루트노드까지 왔으면 break

		mov rax, qword [left]
		mov qword [right], rax
		inc qword [right]			; 오른쪽 자식

		mov rax, qword [sz]
		cmp rax, qword [right]
		jl heap_pop_select_left		; 오른쪽 자식이 없음

		mov rax, qword [left]
		mov rbx, qword [right]
		lea rax, qword [heap + rax * 8]
		lea rbx, qword [heap + rbx * 8]
		mov rax, [rax]
		mov rbx, [rbx]
		cmp rax, rbx
		jle heap_pop_select_left
		jmp heap_pop_select_right	; 우측과 좌측 비교 후 작은쪽 선택

		heap_pop_select_left:		; 왼쪽 선택
			mov rax, qword [left]
			mov qword [comp], rax
			jmp heap_pop_next
		heap_pop_select_right:		; 오른쪽 선택
			mov rax, qword [right]
			mov qword [comp], rax
			jmp heap_pop_next
		heap_pop_next:

		mov rax, qword [comp]
		lea rax, [heap + rax * 8]
		mov rax, [rax]
		cmp rax, r15
		jge heap_pop_loop_end		; 자식 노드 >= 현재 비교값 then break

		mov rbx, qword [idx]
		lea rbx, [heap + rbx * 8]
		mov qword [rbx], rax		; 선택된 값을 부모노드로 옮기기

		mov rax, qword [comp]
		mov qword [idx], rax
		jmp heap_pop_loop
	heap_pop_loop_end:
	
	mov rax, qword [idx]
	lea rax, [heap + rax * 8]
	mov qword [rax], r15

	return:
	mov rax, qword [x]
	pop rbp
	ret
