/*
18428번 감시 피하기

T는 자신의 상하좌우에 대해 장애물에 막히기 전까지 직선상의 모든 것을 볼 수 있다.
S는 T에게서 숨으려고 장애물 3개를 준비했다.
맵은 N*N으로 이루어져있고, T와 S와 X로 이루어져있는데 X는 빈 칸이다.
X에 장애물을 놓아 T의 시야를 방해할 수 있다. 모든 S가 T에게서 숨을 수 있으면 YES, 아니면 NO를 출력하자.
N은 6 이하의 자연수이다.

그저 GOAT SWIFT
내가 스위프트에 익숙하지 않아서 코드가 복잡한줄알았는데,
오히려 간단한편인거같아. 와우!

구현문제라 그냥 하면 됩니다. 어려운거도 없어요.
그냥 구현하면서 자괴감이 좀 드는거 외에는요.
오히려 "연구소"문제가 더 어렵습니다. 거긴 BFS까지 쓰거든요. N도 크고.
*/
func cansee(arr: Array<Array<Substring>>, N: Int, teacher: Array<Array<Int>>) -> Int {
    for i in 0..<teacher.count {
        let X = teacher[i][0], Y = teacher[i][1]
        for j in X..<N {
            if (arr[Y][j] == "S") { return 0 }
            if (arr[Y][j] == "O") { break }
        }
        for j in (0..<X).reversed() {
            if (arr[Y][j] == "S") { return 0 }
            if (arr[Y][j] == "O") { break }
        }
        for j in Y..<N {
            if (arr[j][X] == "S") { return 0 }
            if (arr[j][X] == "O") { break }
        }
        for j in (0..<Y).reversed() {
            if (arr[j][X] == "S") { return 0 }
            if (arr[j][X] == "O") { break }
        }
    }
    return 1
}

let N = Int(readLine()!)!
var arr = Array<Array<Substring>>(repeating: Array<Substring>(), count: N)
for i in 0..<N {
    arr[i] = readLine()!.split(separator: " ")
}

var teacherCool = Array<Array<Int>>()
for Y in 0..<N {
    for X in 0..<N {
        if arr[Y][X] == "T" {
            teacherCool.append([X, Y])
        }
    }
}

var result = 0
for first in 0..<N*N {
    for second in first+1..<N*N {
        for third in second+1..<N*N {
            let firstX = first%N, firstY = first/N
            let secondX = second%N, secondY = second/N
            let thirdX = third%N, thirdY = third/N
            if (arr[firstY][firstX] != "X" ||
                arr[secondY][secondX] != "X" ||
                arr[thirdY][thirdX] != "X") { continue }
            arr[firstY][firstX] = "O"
            arr[secondY][secondX] = "O"
            arr[thirdY][thirdX] = "O"
            result |= cansee(arr: arr, N: N, teacher: teacherCool)
            arr[firstY][firstX] = "X"
            arr[secondY][secondX] = "X"
            arr[thirdY][thirdX] = "X"
        }
    }
}
print(result == 1 ? "YES" : "NO")