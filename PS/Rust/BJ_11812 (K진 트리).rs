use std::fmt::Write;
use std::io;
/*
11812번 K진 트리

노드의 개수 N, 한 노드가 가질 수 있는 최대 자식 노드의 개수 K, 질의의 개수 Q가 주어진다.
이후 각 질의마다 x, y가 주어진다. 노드 x와 y의 거리를 출력하자.
주어지는 트리는 루트가 1이고 자연수 순서대로 노드가 있는 완전K진트리이다.
(1 <= N <= 10^15, 1 <= K <= 1000, 1 <= Q <= 100000)

이게 트리지 ㅋㅋ
상당히 흥미로운 문제였습니다.
쭉 생각해보다가 K진트리의 자식이 어떻더라.. 하고 알아봤고,
이후에는 자식이아니라 거슬러올라가야겠는데.. 라는 생각이 들어 (cur + K - 2) // K라는 부모노드 번호의 공식을 알아내고,
마지막으로 K=1일때는 답이없네.. 하고 예외처리해서 끝냈습니다.

K=1일때 다들 어떻게했나 했더니 저랑 똑같이 했더라고요. 휴~
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
    let mut output = String::new();

	let (N, K, Q) = (input(), input(), input());
	for _ in 0..Q {
		let (mut x, mut y) = (input(), input());
		let mut cnt: i64 = 0;
		while x != y && K != 1{
			if x > y { x = (x+K-2) / K; }
			else { y = (y+K-2) / K; }
			cnt += 1;
		}
		if K == 1 {
			cnt = (x-y).abs();
		}
		write!(output, "{}\n", cnt);
	}
	println!("{output}");
}
