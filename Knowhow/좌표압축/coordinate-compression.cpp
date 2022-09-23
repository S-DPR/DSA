#include <bits/stdc++.h>
using namespace std;

int main() {
	vector <int> t(10);
	for (int i = 0; i < 10; i++) cin >> t[i]; // 원하는 수 10개를 입력하면
	vector <int> k = t; // k에 t를 복사하고
	sort(k.begin(), k.end()); // k를 sort한 뒤
	k.erase(unique(k.begin(), k.end()), k.end()); // k에서 중복된 수를 지웁니다.
	for (int i = 0; i < 10; i++) t[i] = ++lower_bound(k.begin(), k.end(), t[i]) - k.begin();
	// 이후, lowerbound로 좌표압축을 한 뒤
	for (int x : t) cout << x << ' '; // t를 출력합니다.
}