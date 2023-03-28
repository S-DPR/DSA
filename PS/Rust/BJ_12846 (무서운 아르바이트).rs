use std::fmt::Write;
use std::io;

/*
12846번 무서운 아르바이트

수열 arr이 주어진다.
sum(arr[l:r])의 최댓값을 구하여라.

또스토그램
세그트리 분할정복 해봤으니 이제 스택인데..
스택이 이해하기 제일 어려운거같네요;

먼저 배열을 입력받는데, 아래 형식으로 받습니다.
[0, (입력받기), 0]
첫 0은 '제일 앞 부분이다'를 나타내기 위한 0이고,
제일 뒤 0은 '이제 남은거 다 뺴내야한다'를 나타내기 위한 0입니다.

이제 스택을 단조증가 형태로 쌓습니다. 단, 시작부분엔 0을 넣어둡니다.
만약 이전에 들어온거보다 더 작다면, 이번에 넣을거와 같거나 작은게 나올때까지 빼내는데,
직사각형의 길이를 더해주며 빼줄겁니다.
스택에는 값 자체를 넣는 대신 각 인덱스가 들어가있습니다.

뺄 때는 아래의 과정을 거쳐 너비를 계산해보게됩니다.
width를 먼저 계산해봅시다. (현재 인덱스)-1-(스택 뒤에서 두번째 인덱스) 가 너비가 됩니다.
왜 뒤에서 두번째를 사용하냐면, 이전에 있던거는 '이번에 빠지는 값보다 컸다'라는 보장이 서기 때문입니다.
0 1 4 --[3 추가]-> 0 1 3 --[3 추가]-> 0 1 3 3
이렇게 되었을때 4의 정보를 갖고있는거는 1밖에 없으니까요.

이제 height를 계산합니다. 이건 그냥 arr에서 들고오면 끝납니다.
이거를 반복하여 O(N) 스택으로 문제를 끝낼 수 있습니다.
*/

fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	
	let N = input();
	let mut arr = Vec::new();
	arr.push(0);
	for i in 0..N {
		arr.push(input());
	}
	arr.push(0);
	let mut res = i64::from(0);
	let mut stack = [0; 100002];
	let mut top = 0;
	for i in 1..=N+1 {
		while top != 0 && arr[stack[top]] > arr[i as usize] {
			let height: i64 = arr[stack[top]] as i64;
			let width: i64 = i-1-stack[top-1] as i64;
			res = std::cmp::max(res, width*height);
			top -= 1;
		}
		top += 1;
		stack[top] = i as usize;
	}
	println!("{}", res);
}
