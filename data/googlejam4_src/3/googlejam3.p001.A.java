package googlejam3.p001;
import static java.lang.Math.*;
import static java.util.Arrays.*;

import java.io.*;
import java.util.*;

public class A {
	
	PrintWriter out;
	
	int R, C, W;
	
	public static int __ID = 0;
	public static boolean __ERROR = false;
	
	public static void main(String[] args) {
		int pN = 1;
		if (args.length == 0) {
			try {
				System.setIn(new BufferedInputStream(new FileInputStream(A.class.getName() + ".in")));
			} catch (Exception e) {
			}
		} else {
			pN = Integer.parseInt(args[0]);
		}
		PrintStream out = System.out;
		System.setOut(null);
		Scanner sc = new Scanner(System.in);
		final int caseN = sc.nextInt();
		final A[] solvers = new A[caseN];
		StringWriter[] outs = new StringWriter[caseN];
		for (int i = 0; i < caseN; i++) {
			solvers[i] = new A();
			outs[i] = new StringWriter();
			solvers[i].out = new PrintWriter(outs[i]);
			solvers[i].out.printf("Case #%d: ", i + 1);
			A r = solvers[i];
			r.R = sc.nextInt();
			r.C = sc.nextInt();
			r.W = sc.nextInt();
		}
		Thread[] ts = new Thread[pN];
		for (int i = 0; i < pN; i++) {
			ts[i] = new Thread() {
				@Override
				public void run() {
					for (;;) {
						int id;
						synchronized (A.class) {
							if (__ID == caseN) return;
							id = __ID++;
						}
						try {
							A r = solvers[id];
							int res = r.R * (r.C / r.W);
							if (r.C % r.W > 0) res++;
							res += r.W - 1;
							r.out.println(res);
						} catch (RuntimeException e) {
							__ERROR = true;
							System.err.printf("Error in case %d:%n", id + 1);
							e.printStackTrace();
						}
						solvers[id].out.flush();
						solvers[id] = null;
					}
				}
			};
			ts[i].start();
		}
		for (int i = 0; i < pN; i++) {
			try {
				ts[i].join();
			} catch (InterruptedException e) {
				i--;
				continue;
			}
		}
		for (int i = 0; i < caseN; i++) {
			out.print(outs[i].toString());
		}
		if (__ERROR) out.printf("%nError occured!!!%n");
	}
	
}
