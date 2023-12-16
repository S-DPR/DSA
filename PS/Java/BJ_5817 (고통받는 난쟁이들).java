import java.io.*;
import java.util.*;
/*
5817번 고통받는 난쟁이들

1부터 N까지 있는 순열이 있다.
두 연산을 수행하려 한다. 그 결과를 출력해보자.
1 x y : x번째와 y번째 원소의 위치를 바꾼다.
2 x y : x부터 y까지의 수가 연속해있는지 확인한다. (정렬이 되어있지 않아도 된다)

옛날에 '이건 그냥 세그트리네 쉽지 ㅋㅋ' 하고 넣어놨는데,
막상 풀려고보니 세그트리력이 떨어졌나 허우적대다가 겨우 성공..
후..

그냥 최대/최소 세그트리 구축해서 풀면 됩니다.
그런데 여기서 세그트리 하기 귀찮다고 '아 트리맵으로 날먹 안되나..'하고..
인덱스에 값을 넣어야하는데 값에 인덱스를 넣고있고..
총체적 난국..
*/
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st = new StringTokenizer("");
    
	public static int sti() throws IOException {
		if (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Integer.parseInt(st.nextToken());
	}
	
	public static long stl() throws IOException {
		if (!st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return Long.parseLong(st.nextToken());
	}

	public static void main(String[] args) throws IOException {
		int N = sti(), Q = sti();
		int[] idx = new int[N+1];
		segT seg = new segT(N);
		for (int i = 0; i < N; i++) {
			int x = sti();
			seg.update(x, i+1);
			idx[i+1] = x;
		}
		for (int i = 0; i < Q; i++) {
			int q = sti();
			int u = sti(), v = sti();
			if (q == 1) {
				int uu = idx[u];
				int vv = idx[v];
				seg.update(vv, u);
				seg.update(uu, v);
				idx[u] = vv;
				idx[v] = uu;
			} else {
				Pair ret = seg.query(u, v);
				bw.write(ret.s - ret.f == v-u ? "YES\n" : "NO\n");
			}
		}

        bw.flush();
        bw.close();
        br.close();
    }
}

class segT {
	Pair[] seg;
	int size;
	segT(int size) {
		seg = new Pair[size*2+2];
		this.size = size;
		for (int i = 0; i <= size*2+1; i++)
			seg[i] = new Pair(1<<30, -1<<30);
	}
	public void update(int idx, int val) {
		idx += size;
		seg[idx] = new Pair(val, val);
		while (idx > 1) {
			seg[idx>>1].f = Math.min(seg[idx].f, seg[idx^1].f);
			seg[idx>>1].s = Math.max(seg[idx].s, seg[idx^1].s);
			idx >>= 1;
		}
	}
	public Pair query(int l, int r) {
		Pair ret = new Pair(1<<30, -1<<30);
		if (r < l) {
			int tmp = l;
			l = r;
			r = tmp;
		}
		l += size;
		r += size;
		while (l <= r) {
			if ((l&1) == 1) {
				ret.f = Math.min(ret.f, seg[l].f);
				ret.s = Math.max(ret.s, seg[l].s);
				l++;
			}
			if ((~r&1) == 1) {
				ret.f = Math.min(ret.f, seg[r].f);
				ret.s = Math.max(ret.s, seg[r].s);
				r--;
			}
			l >>= 1;
			r >>= 1;
		}
		return ret;
	}
}

class Pair {
	int f, s;
	Pair(int f, int s) {
		this.f = f;
		this.s = s;
	}
}
