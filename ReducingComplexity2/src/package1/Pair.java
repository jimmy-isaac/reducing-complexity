package package1;

public class Pair<M, N> {
	
	private M m;
	private N n;
	
	Pair(M m, N n) {
		this.m = m;
		this.n = n;
	}

	public M getFirst() {
		return m;
	}

	public N getSecond() {
		return n;
	}
	
	
	

}
