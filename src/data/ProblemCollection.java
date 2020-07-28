package data;

import java.util.ArrayList;

import main.Common;

public class ProblemCollection {
	private static ArrayList<Problem> problems = new ArrayList<Problem>(); 
	
	static {
		problems.add(
				new Problem(
					"Main task",
					new Function( new double[] {11, 11, 16, 7}, Common.F_MIN ), 
					new Constraint[] {
							new Constraint( new double[] {2, 1, 2, 1}, Common.C_GOE, 6 ), 
							new Constraint( new double[] {3, 3, 4, 2}, Common.C_GOE, 11 ),
					}
				)
			);
		
		problems.add(
				new Problem(
					"Main task (Lab 2)",
					new Function( new double[] {40, 100}, Common.F_MAX ), 
					new Constraint[] {
							new Constraint( new double[] {4, 4}, Common.C_LOE, 32 ), 
							new Constraint( new double[] {3, 15}, Common.C_LOE, 60 ),
							new Constraint( new double[] {8, 4}, Common.C_LOE, 52 )
					}
				)
			);
		
		problems.add(
				new Problem(
					"Lab #1", 
					new Function( new double[] {0, 1}, Common.F_MIN ),
					new Constraint[] {
							new Constraint( new double[] {3, 11}, Common.C_GOE, 15),
							new Constraint( new double[] {-0.2f, 1}, Common.C_LOE, 5),
							new Constraint( new double[] {3, 1}, Common.C_GOE, 21)
					}
				)
			);
		
		problems.add(
				new Problem(
					"Example 1", 
					new Function( new double[] {2, 3}, Common.F_MAX ), 
					new Constraint[] {
							new Constraint( new double[] {2, 2}, Common.C_LOE, 14 ),
							new Constraint( new double[] {1, 2}, Common.C_LOE, 8 ),
							new Constraint( new double[] {4, 0}, Common.C_LOE, 16 )
					}
				)
			);
		
		problems.add(
				new Problem(
					"Example 2 (sztuczna baza - 1)", 
					new Function( new double[] {240, 300, 200}, Common.F_MIN ), 
					new Constraint[] {
							new Constraint( new double[] {1, 2, 1}, Common.C_GOE, 2 ),
							new Constraint( new double[] {4, 1, 1}, Common.C_GOE, 4 ),
							new Constraint( new double[] {3, 5, 1}, Common.C_GOE, 3 )
					}
				)
			);
		
		problems.add(
				new Problem(
					"Example 3 (sztuczna baza - 2)", 
					new Function( new double[] {2, 3}, Common.F_MAX ), 
					new Constraint[] {
							new Constraint( new double[] {2, 2}, Common.C_LOE, 14 ),
							new Constraint( new double[] {1, 2}, Common.C_LOE, 8 ),
							new Constraint( new double[] {4, 0}, Common.C_LOE, 16 ),
							new Constraint( new double[] {1, 2}, Common.C_GOE, 3 )
					}
				)
			);
		
		problems.add(
				new Problem(
					"Example 4 (no solution)", 
					new Function( new double[] {2, 3}, Common.F_MAX ), 
					new Constraint[] {
							new Constraint( new double[] {2, 2}, Common.C_LOE, 14 ),
							new Constraint( new double[] {1, 2}, Common.C_LOE, 8 ),
							new Constraint( new double[] {4, 0}, Common.C_LOE, 16 ),
							new Constraint( new double[] {1, 2}, Common.C_GOE, 8 )
					}
				)
			);
		
		problems.add(
				new Problem(
					"Example 5 (alternative solution)", 
					new Function( new double[] {2, 4}, Common.F_MAX ), 
					new Constraint[] {
							new Constraint( new double[] {2, 2}, Common.C_LOE, 14 ),
							new Constraint( new double[] {1, 2}, Common.C_LOE, 8 ),
							new Constraint( new double[] {4, 0}, Common.C_LOE, 16 )
					}
				)
			);
		
		problems.add(
				new Problem(
					"Example 6 (infinite number of solutions)", 
					new Function( new double[] {2, 3}, Common.F_MAX ), 
					new Constraint[] {
							new Constraint( new double[] {1, 1}, Common.C_GOE, 3 ),
							new Constraint( new double[] {4, 0}, Common.C_LOE, 16 ),
					}
				)
			);
		
		problems.add(
				new Problem(
					"uchimatchast.ru/teory/simplex_primer1.php", 
					new Function( new double[] {2, 5, 3, 8}, Common.F_MIN ), 
					new Constraint[] {
							new Constraint( new double[] {3, 6, -4, 1}, Common.C_LOE, 12 ),
							new Constraint( new double[] {4, -13, 10, 5}, Common.C_GOE, 6 ),
							new Constraint( new double[] {3, 7, 1, 0}, Common.C_GOE, 1 )
					}
				)
			);
		
		problems.add(
				new Problem(
						"uchimatchast.ru/teory/isk_bazis_primer1.php", 
					new Function( new double[] {1, 5, 4, -3}, Common.F_MAX ), 
					new Constraint[] {
							new Constraint( new double[] {2, 7, 1, 0}, Common.C_LOE, 5 ),
							new Constraint( new double[] {1, 4, 2, 8}, Common.C_EQU, 6 ),
							new Constraint( new double[] {-1, 0, 2, 5}, Common.C_EQU, 9 )
					}
				)
			);
		
		problems.add(
				new Problem(
					"uchimatchast.ru/teory/isk_bazis_primer2.php", 
					new Function( new double[] {2, -1, 7, 11, 5}, Common.F_MIN ), 
					new Constraint[] {
							new Constraint( new double[] {2, 0, 5, 1, 8}, Common.C_EQU, 12 ),
							new Constraint( new double[] {-3, 6, 2, -2, 0}, Common.C_LOE, 5 ),
					}
				)
			);
	}
	
	public static Problem getProblem(int i) {
		return problems.get(i);
	}
	
	public static String[] getTitles() {
		String[] res = new String[ problems.size() ];
		for (int i = 0; i < problems.size(); i++)
			res[i] = problems.get(i).getTitle();
		
		return res;
	}
	
	public static int getInitProblemNo() {
		return 0;
	}

}