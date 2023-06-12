import java.io.*
import java.util.*
/*
2014번 소수의 곱

소수가 K개 주어진다. K개의 소수중 원하는 만큼 골라서 곱한 값을 t라고 하자.
모든 경우의 수를 나열했을 때, 중복을 제외하고 N번째로 작은 값을 출력해보자.

이거 어떻게푸는고.. 하다 생각난 우선순위 큐
제일 작은값과 다 곱하고, 넣고.
단, 현재 사이즈가 N을 넘는다면 MAX값과 비교해서 넣어줘야합니다.
MAX값은 현재 우선순위큐에 들어있는 최댓값입니다. 이 이후는 N번째 이후의 수이므로 필요 없습니다.

난이도가 골드 1인 이유는 메모리초과 이슈떄문이라고 합니다. 왜인진 모르겠어요.
근데 굳이 왜 소수의 곱으로 한거지..?
*/
fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    var (K, N) = br.readLine().split(" ").map {it.toLong()}
    val arr = br.readLine().split(" ").map {it.toLong()}
    val pq = TreeSet(arr)
    var MAX = arr.max()
    while (--N > 0) {
        val cur = pq.pollFirst()!!
        arr.forEach {
            if (N < pq.size && MAX < cur*it) return@forEach
            pq.add(cur * it)
            MAX = Math.max(MAX, cur*it)
        }
    }
    println(pq.pollFirst()!!)

    br.close()
    bw.flush()
    bw.close()
}
/*
C++코드
#include <bits/stdc++.h>
#define endl "\n"
#define ll long long
#define fast ios::sync_with_stdio(0), cin.tie(nullptr), cout.tie(nullptr)
#define INF 1000000000
using namespace std;

int main() {
    fast;
    set<ll> pq;
    ll arr[100], MAX = 0;
    int K, N; cin >> K >> N;
    for (int i = 0; i < K; i++) {
        cin >> arr[i];
        pq.insert(arr[i]);
        MAX = max(MAX, arr[i]);
    }
    while (--N) {
        ll cur = *pq.begin(); pq.erase(cur);
        for (int i = 0; i < K; i++) {
            if (N < pq.size() && MAX < cur * arr[i]) continue;
            pq.insert(cur * arr[i]);
            MAX = max(MAX, cur * arr[i]);
        }
    }
    cout << *pq.begin();
}
*/