use std::fmt::Write;
use std::io;
use std::collections::HashSet;
/*
1071번 소트

수열을 정렬하자. 아래 조건에 맞는 것 중 가장 앞에 있는걸 출력하여라.
 - A[i-1]+1 != A[i] (0 < i < N)

난이도기여에서 골드2~플레5가 격렬하게 싸우는 중인 그리디문제.
이런류 그리디가 저런경우가 많은거같아요.

처음엔 그냥 정확히 저 조건만 맞추면서 정렬하는데,
이제 딱 한가지 경우, '남은 수의 종류가 2개'일때만 처리해주면 되는 문제.
남은 수의 종류가 2개이고 그 수의 차가 1이라면,
묻지도 따지지도 말고 무조건 큰 수를 먼저 배치해야합니다.
그거 처리해주면 AC. 생각이 쉽진 않았지만, 생각보단 빨리 처리했다..
*/
fn main() {
    let buf = io::read_to_string(io::stdin()).unwrap();
    let mut input = buf.split_ascii_whitespace().flat_map(str::parse::<i64>);
    let mut input = || input.next().unwrap();
	let mut output = String::new();

    let N = input() as usize;
    let mut A = Vec::new();
    let mut R = [0;50];
    let mut V = [false;50];
    for _ in 0..N {
        A.push(input());
    }
    A.sort();
    for idx in 0..N {
        let mut remain = [false; 1001];
        let mut remainCnt = 0;
        let mut remainMax = -1;
        let mut remainMin = 1001;
        for i in 0..N {
            if V[i] { continue; }
            if !remain[A[i] as usize] {remainCnt += 1; }
            remain[A[i] as usize] = true;
            remainMax = std::cmp::max(remainMax, A[i]);
            remainMin = std::cmp::min(remainMin, A[i]);
        }
        let check = remainMax-remainMin == 1;
        for i in 0..N {
            if V[i] { continue; }
            if idx != 0 && R[idx-1]+1 == A[i] { continue; }
            if remainCnt == 2 && check && remainMax != A[i] { continue; }
            R[idx] = A[i];
            V[i] = true;
            break;
        }
    }
    for i in 0..N {
        print!("{} ", R[i]);
    }
}
