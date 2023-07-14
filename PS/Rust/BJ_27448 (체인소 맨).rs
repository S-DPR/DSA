use std::fmt::Write;
use std::io;
/*
27448번 체인소 맨

직사각형에 칼질을 해 원하는 모양대로 만들려 한다.
단, 원하는 모양에는 칼질이 나있으면 안되며, 칼질에는 두 방법이 존재한다.
1. 그냥 테두리대로 자른다. 길이 1당 힘 1이 든다.
2. 직사각형 바깥부터 원하는데까지 자른다. 한 번 시도에 힘 F가 든다.
이 때, 원하는 모양을 만들기 위해 필요한 최소 힘을 구해보자.

부분적 투포인터 느낌으로 풀었습니다. 그리고, 오랜만에 노션에 구현태그 넣겠네요.
먼저 가로로 보면, (i, j)에 대해 (i-1, j)도 봅니다. 그리고 #의 개수를 (i, j)배열에 적어줍니다.
세로로도 해줍니다. 단, (i, j-1)이 되겠죠.

그리고 제일 끝부분에서 한번에 자를 때 테두리가 얼마나 많은지 체크해봅니다.
이후 제일 좌측, 우측에서 했다면 힘 F와 비교해서 작은값을 답에 넣습니다.
다음으로 좌측과 우측 사이에 1의 개수를 세어 그건 그냥 넣어줍니다. 어차피 중간은 F로 밀 수가 없어서요.
이걸 세로로도 똑같이 해주면 AC.
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace();//.flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();
	
	let Y = input().parse::<usize>().unwrap();
	let X = input().parse::<usize>().unwrap();
	let F = input().parse::<i32>().unwrap();
	let mut M = Vec::new();
	for i in 0..Y {
		M.push(Vec::new());
		let item = input();
		for j in item.chars() {
			M[i].push(j);
		}
	}
	let mut sliceX = [[0; 81]; 81];
	let mut sliceY = [[0; 81]; 81];
	for i in 1..Y {
		for j in 0..X {
			let u = M[i-1][j] == '#';
			let d = M[i][j] == '#';
			sliceX[i][j] += if u {1} else {0};
			sliceX[i][j] += if d {1} else {0};
		}
	}
	for i in 0..Y {
		for j in 1..X {
			let u = M[i][j-1] == '#';
			let d = M[i][j] == '#';
			sliceY[i][j] += if u {1} else {0};
			sliceY[i][j] += if d {1} else {0};
		}
	}
	let mut ret = 0;
	for i in 1..Y {
		let mut left = 0;
		let mut right = X-1;
		let mut leftF = 0;
		let mut rightF = 0;
		let mut midF = 0;
		while sliceX[i][left] != 2 && left < X {
			if sliceX[i][left] == 1 {
				leftF += 1;
			}
			left += 1;
		}
		ret += std::cmp::min(F, leftF);
		while sliceX[i][right] != 2 && right > left {
			if sliceX[i][right] == 1 {
				rightF += 1;
			}
			right -= 1;
		}
		ret += std::cmp::min(F, rightF);
		for j in left..=right {
			if sliceX[i][j] == 1 {
				midF += 1
			}
		}
		ret += midF;
	}
	for i in 1..X {
		let mut up = 0;
		let mut down = Y-1;
		let mut upF = 0;
		let mut downF = 0;
		let mut midF = 0;
		while sliceY[up][i] != 2 && up < Y {
			if sliceY[up][i] == 1 {
				upF += 1;
			}
			up += 1;
		}
		ret += std::cmp::min(F, upF);
		while sliceY[down][i] != 2 && down > up {
			if sliceY[down][i] == 1 {
				downF += 1;
			}
			down -= 1;
		}
		ret += std::cmp::min(F, downF);
		for j in up..=down {
			if sliceY[j][i] == 1 {
				midF += 1
			}
		}
		ret += midF;
	}
	println!("{}", ret);
}
