/*
11509번 풍선 맞추기

i번째 풍선이 높이 y에 있음을 나타내는 수열 A가 주어진다.
다트를 원하는 높이에서 던질 것이다. 이 때, 다트는 직선으로 날아간다.
하지만 풍선을 하나 터뜨리면 다트는 y좌표가 1 낮아진 상태로 직진한다.
모든 풍선을 터뜨리는데 필요한 최소의 다트 개수를 구해보자.

어셈블리어로 재밌는 골드5 풀기
그냥 그리디문제입니다.
i번째 풍선을 볼 때, 그 풍선의 y좌표를 A[i]라고 합시다.
A[i]에 다트가 있을경우 그 다트의 개수중 하나를 빼 한칸 아래로 옮깁니다.
그 높이에 다트가 없다면 거기에 다트를 던집니다. 즉, 결과값에 1을 더합니다.
모든 i에 대하여 위 행위를 한 뒤 그 답을 출력하면 됩니다.
*/
section .bss
	N: resq 1
	i: resq 1
	r: resq 1
	arr: resq 1000001
	exist: resq 1000001

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
	mov rsi, N
	call scanf

	mov qword [i], 0
	init_loop:
		cmp dword [i], 1000000
		je init_loop_end

		mov rax, [i]
		lea rax, [exist+rax*8]
		mov qword [rax], 0

		inc qword [i]
		jmp init_loop
	init_loop_end:

	mov qword [i], 0
	mov qword [r], 0
	loop:
		mov rax, [N]
		cmp [i], rax
		je loop_end

		mov rax, [i]
		lea rax, [arr+rax*8]
		mov rdi, input
		mov rsi, rax
		call scanf

		mov rax, [i]
		lea rax, [arr+rax*8]
		mov rax, [rax]

		lea rbx, [exist+rax*8]
		cmp dword [rbx], 0
		je true
		jmp false
		true:
			inc qword [r]
			lea rbx, [exist+rax*8-8]
			inc qword [rbx]
			jmp next
		false:
			lea rbx, [exist+rax*8]
			dec qword [rbx]
			lea rbx, [exist+rax*8-8]
			inc qword [rbx]
			jmp next
		next:
		inc qword [i]
		jmp loop
	loop_end:
	
	mov rdi, output
	mov rsi, [r]
	call printf

	pop rbp
	mov rax, 0
	ret
