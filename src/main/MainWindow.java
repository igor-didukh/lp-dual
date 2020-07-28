package main;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.border.EtchedBorder;

import data.Constraint;
import data.Function;
import data.ProblemCollection;
import data.Problem;
import input.InputDialog;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

public class MainWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final String EDIT_FUNCTION = "EDIT_FUNCTION", CREATE_DUAL = "CREATE_DUAL", SET_AMOUNT = "SET_AMOUNT", EXIT = "EXIT", GET_PROBLEM= "GET_PROBLEM", 
			ADD_CONSTRAINT = "ADD_CONSTRAINT", EDIT_CONSTRAINT = "EDIT_CONSTRAINT", DELETE_CONSTRAINT = "DELETE_CONSTRAINT", SOLVE_MAIN = "SOLVE_MAIN", SOLVE_DUAL = "SOLVE_DUAL";
	private final int YES = JOptionPane.YES_OPTION; 
	
	public static Function fnFromDialog;
	
	private Function functionMain, functionDual;
	private JPanel contentPane, panelData, panelTitle;
	private JTextField txtAmount;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JLabel lblFMain, lblVMain, lblFDual, lblVDual;
	private JButton btnCreateDual, btnAddConstraint, btnEditConstraint, btnDeleteConstraint, btnSolveMain, btnSolveDual; 
	
	private final DefaultListModel<Constraint> listModelMain = new DefaultListModel<Constraint>();
	private final DefaultListModel<Constraint> listModelDual = new DefaultListModel<Constraint>();
	private JList<Constraint> listMainConstraints;
	private JComboBox<String> comboProblem;
	private ButtonGroup grpFormat;
	
	// Main window
	public MainWindow() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				closeFrame(evt);
			}
		});
		
		setTitle("Solving of the linear programming problem (dual problem method)");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		setBounds(100, 100, 1006, 488);
		
		contentPane = new JPanel();
		contentPane.setBackground( new Color(238, 238, 238) );
		contentPane.setBorder( new BevelBorder(BevelBorder.LOWERED, null, null, null, null) );
		contentPane.setLayout( new BorderLayout(0, 0) );
		setContentPane(contentPane);
		
		menuBar = new JMenuBar();
		contentPane.add(menuBar, BorderLayout.NORTH);
		
		mnFile = new JMenu("File");
		
		JMenuItem mntmAmount = new JMenuItem("Variables");
		mntmAmount.setActionCommand(SET_AMOUNT);
		mntmAmount.addActionListener(this);
		mnFile.add(mntmAmount);
		
		JMenuItem mntmSolveMain = new JMenuItem("Solve main");
		mntmSolveMain.setActionCommand(SOLVE_MAIN);
		mntmSolveMain.addActionListener(this);
		mnFile.add(mntmSolveMain);
		
		JMenuItem mntmSolveDual = new JMenuItem("Solve dual");
		mntmSolveDual.setActionCommand(SOLVE_DUAL);
		mntmSolveDual.addActionListener(this);
		mnFile.add(mntmSolveDual);
		
		mnFile.addSeparator();
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setActionCommand(EXIT);
		mntmExit.addActionListener(this);
		mnFile.add(mntmExit);
		
		menuBar.add(mnFile);
		
		panelData = new JPanel();
		contentPane.add(panelData);
		panelData.setLayout(null);
		
		panelTitle = new JPanel();
		panelTitle.setBounds(238, 5, 539, 58);
		panelTitle.setLayout( new BorderLayout(0, 0) );
		panelData.add(panelTitle);
		
		JLabel lblTitle = new JLabel("Solving of the linear programming problem");
		lblTitle.setVerticalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(new Color(0, 0, 128));
		lblTitle.setFont(new Font("Arial", Font.PLAIN, 28));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitle.add(lblTitle, BorderLayout.EAST);
		
		JLabel lblMethod = new JLabel("(dual problem method)");
		lblMethod.setForeground(new Color(0, 0, 128));
		lblMethod.setFont( new Font("Tahoma", Font.PLAIN, 20));
		lblMethod.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitle.add(lblMethod, BorderLayout.SOUTH);
		
		JPanel panelProblem = new JPanel();
		panelProblem.setBounds(5, 80, 987, 32);
		panelData.add(panelProblem);
		
		JLabel lblProblem = new JLabel("Problem:");
		panelProblem.add(lblProblem);
		lblProblem.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		comboProblem = new JComboBox<String>();
		panelProblem.add(comboProblem);
		comboProblem.setForeground(new Color(128, 0, 0));
		comboProblem.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboProblem.setModel(new DefaultComboBoxModel<String>( ProblemCollection.getTitles() ));
		comboProblem.setSelectedIndex( ProblemCollection.getInitProblemNo() );
		
		JLabel lblAmount = new JLabel("Number of variables:");
		panelProblem.add(lblAmount);
		lblAmount.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		txtAmount = new JTextField();
		panelProblem.add(txtAmount);
		txtAmount.setForeground(new Color(128, 0, 0));
		txtAmount.setEditable(false);
		txtAmount.setHorizontalAlignment(SwingConstants.CENTER);
		txtAmount.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtAmount.setColumns(2);
		
		JButton btnSetAmount = new JButton("Set...");
		panelProblem.add(btnSetAmount);
		btnSetAmount.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSetAmount.setActionCommand(SET_AMOUNT);
		btnSetAmount.addActionListener(this);
		
		btnCreateDual = new JButton("Create dual problem");
		btnCreateDual.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCreateDual.setBounds(400, 130, 206, 23);
		btnCreateDual.setActionCommand(CREATE_DUAL);
		btnCreateDual.addActionListener(this);
		panelData.add(btnCreateDual);
		
		JLabel lblFMainTitle = new JLabel("Main function:");
		lblFMainTitle.setBounds(195, 135, 120, 14);
		panelData.add(lblFMainTitle);
		lblFMainTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFMainTitle.setHorizontalAlignment(SwingConstants.LEFT);
		
		JButton btnEditFunction = new JButton("Edit...");
		btnEditFunction.setBounds(293, 130, 71, 23);
		panelData.add(btnEditFunction);
		btnEditFunction.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEditFunction.setActionCommand(EDIT_FUNCTION);
		btnEditFunction.addActionListener(this);
		
		JLabel lblDualFunction = new JLabel("Dual function:");
		lblDualFunction.setHorizontalAlignment(SwingConstants.LEFT);
		lblDualFunction.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDualFunction.setBounds(697, 135, 94, 14);
		panelData.add(lblDualFunction);
		
		JPanel panelMain = new JPanel();
		panelMain.setBounds(5, 156, 492, 231);
		panelData.add(panelMain);
		panelMain.setLayout(new BorderLayout(0, 0));
		
		JPanel panelFMain = new JPanel();
		panelMain.add(panelFMain, BorderLayout.NORTH);
		panelFMain.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		lblFMain = new JLabel();
		panelFMain.add(lblFMain);
		lblFMain.setForeground(new Color(128, 0, 0));
		lblFMain.setHorizontalAlignment(SwingConstants.CENTER);
		lblFMain.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JPanel panelSMain = new JPanel();
		panelMain.add(panelSMain, BorderLayout.CENTER);
		panelSMain.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelSMain.setLayout(new BorderLayout(0, 0));
		
		JLabel lblSMain = new JLabel("Main constraint system:");
		lblSMain.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSMain.setHorizontalAlignment(SwingConstants.CENTER);
		panelSMain.add(lblSMain, BorderLayout.NORTH);
		
		listMainConstraints = new JList<>(listModelMain);
		listMainConstraints.setForeground(new Color(128, 0, 0));
		listMainConstraints.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panelSMain.add(listMainConstraints, BorderLayout.CENTER);
		
		JPanel panelActions = new JPanel();
		panelSMain.add(panelActions, BorderLayout.EAST);
		panelActions.setLayout(new GridLayout(4, 1, 0, 0));
		
		btnAddConstraint = new JButton("+");
		btnAddConstraint.setActionCommand(ADD_CONSTRAINT);
		btnAddConstraint.addActionListener(this);
		panelActions.add(btnAddConstraint);
		
		btnEditConstraint = new JButton("*");
		btnEditConstraint.setActionCommand(EDIT_CONSTRAINT);
		btnEditConstraint.addActionListener(this);
		panelActions.add(btnEditConstraint);
		
		btnDeleteConstraint = new JButton("X");
		btnDeleteConstraint.setActionCommand(DELETE_CONSTRAINT);
		btnDeleteConstraint.addActionListener(this);
		panelActions.add(btnDeleteConstraint);
		
		JPanel panelVMain = new JPanel();
		panelVMain.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelMain.add(panelVMain, BorderLayout.SOUTH);
		
		lblVMain = new JLabel();
		lblVMain.setHorizontalAlignment(SwingConstants.CENTER);
		lblVMain.setForeground(new Color(128, 0, 0));
		lblVMain.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelVMain.add(lblVMain);
		
		JPanel panelDual = new JPanel();
		panelDual.setBounds(500, 156, 492, 231);
		panelData.add(panelDual);
		panelDual.setLayout(new BorderLayout(0, 0));
		
		JPanel panelFDual = new JPanel();
		panelDual.add(panelFDual, BorderLayout.NORTH);
		panelFDual.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		lblFDual = new JLabel();
		lblFDual.setHorizontalAlignment(SwingConstants.CENTER);
		lblFDual.setForeground(new Color(128, 0, 0));
		lblFDual.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelFDual.add(lblFDual);
		
		JPanel panelSDual = new JPanel();
		panelDual.add(panelSDual, BorderLayout.CENTER);
		panelSDual.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelSDual.setLayout(new BorderLayout(0, 0));
		
		JLabel lblSDual = new JLabel("Dual constraint system:");
		lblSDual.setHorizontalAlignment(SwingConstants.CENTER);
		lblSDual.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelSDual.add(lblSDual, BorderLayout.NORTH);
		
		JList<Constraint> listDualConstraints = new JList<Constraint>(listModelDual);
		listDualConstraints.setSelectedIndex(0);
		listDualConstraints.setForeground(new Color(128, 0, 0));
		listDualConstraints.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panelSDual.add(listDualConstraints, BorderLayout.CENTER);
		
		JPanel panelVDual = new JPanel();
		panelVDual.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelDual.add(panelVDual, BorderLayout.SOUTH);
		
		lblVDual = new JLabel("?");
		lblVDual.setHorizontalAlignment(SwingConstants.CENTER);
		lblVDual.setForeground(new Color(128, 0, 0));
		lblVDual.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelVDual.add(lblVDual);
		
		btnSolveMain = new JButton("Solve");
		btnSolveMain.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSolveMain.setBounds(168, 390, 162, 26);
		btnSolveMain.setActionCommand(SOLVE_MAIN);
		btnSolveMain.addActionListener(this);
		panelData.add(btnSolveMain);
		
		btnSolveDual = new JButton("Solve");
		btnSolveDual.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSolveDual.setBounds(666, 390, 162, 26);
		btnSolveDual.setActionCommand(SOLVE_DUAL);
		btnSolveDual.addActionListener(this);
		panelData.add(btnSolveDual);
		
		grpFormat = new ButtonGroup();
		
		JRadioButton rbTxt = new JRadioButton("Plain text");
		rbTxt.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbTxt.setBounds(466, 390, 79, 19);
		rbTxt.setActionCommand(Common.TXT);
		rbTxt.setSelected(false);
		panelData.add(rbTxt);
		grpFormat.add(rbTxt);
		
		JRadioButton rbHtml = new JRadioButton("HTML");
		rbHtml.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rbHtml.setBounds(466, 410, 57, 19);
		rbHtml.setActionCommand(Common.HTML);
		rbHtml.setSelected(true);
		grpFormat.add(rbHtml);
		panelData.add(rbHtml);
		
		comboProblem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getProblem();
			}
		});
		
		getProblem();
	}
	
	// Truncate 2 right symbols 
	private String truncVarsStr(String s) {
		return s.length() == 0 ? " " : s.substring(0, s.length() - 2);
	}
	
	// Set problem data values to form fields
	private void setFields() {
		String sMainVars = "";
		for (int i = 0; i < functionMain.getVarsAmount(); i++)
			sMainVars += "x" + (i+1) + " >= 0; ";
		
		txtAmount.setText( "" + functionMain.getVarsAmount() );
		lblFMain.setText( functionMain.toString() );
		lblVMain.setText( truncVarsStr(sMainVars) );
		listMainConstraints.setSelectedIndex(0);
	}
	
	// Set buttons availability
	private void checkButtons() {
		functionDual = null;
		lblFDual.setText("<?>");
		lblVDual.setText("<?>");
		listModelDual.clear();
		
		boolean constrListNotEmpty = (listModelMain.getSize() != 0);
		btnCreateDual.setEnabled(constrListNotEmpty);
		btnEditConstraint.setEnabled(constrListNotEmpty);
		btnDeleteConstraint.setEnabled(constrListNotEmpty);
		
		btnSolveMain.setEnabled(listModelMain.getSize() >= 2);
		btnSolveDual.setEnabled(false);
	}
	
	// Get problem data from combo
	private void getProblem() {
		listModelMain.clear();
		
		Problem problem = ProblemCollection.getProblem( comboProblem.getSelectedIndex() );
		functionMain = problem.getF();
		
		Constraint[] constraints = problem.getConstraints();
		for (Constraint constraint : constraints)
			listModelMain.addElement(constraint);
		
		setFields();
		checkButtons();
	}
	
	// Show dialog to change vars amount
	private void changeVarsAmount() {
		int amount = functionMain.getVarsAmount();
		int n = Common.showNumberDialog(this, "Select number of variables", "Number of variables", amount);
		
		if ( (n <= 0) || (n == amount) ) return;
		
		if ( Common.showConfirmDialog(this, "Change number of variables from " + amount + " to " + n + "?", "Change number of variables") == YES ) {
			double[] c = new double[n];
			for (int i = 0; i < n; i++)
				c[i] = 1;
			
			functionMain = new Function(c, Common.F_MAX);
			
			listModelMain.clear();
			setFields();
			checkButtons();
		}
	}
	
	// Create dual problem from main problem
	private void createDualProblem() {
		listModelDual.clear();
		
		int m = listModelMain.size();
		int n = functionMain.getVarsAmount();
		String sDualVars = "";
		double[][] y = new double[m][n+1];
		
		String fMainDir = functionMain.getDir();
		
		for (int i = 0; i < m; i++) {
			Constraint cons = listModelMain.getElementAt(i);
			sDualVars += (cons.getDir() == Common.C_EQU) ? "" : "y" + (i+1) + " >= 0; ";
			double[] cx = cons.getCoefs();
			
			int sign = 1;
			if (fMainDir == Common.F_MAX)
				sign = (cons.getDir() == Common.C_GOE) ? -1: 1;
			else
				sign = (cons.getDir() == Common.C_LOE) ? -1: 1;
			
			for (int j = 0; j < n; j++)
				y[i][j] = sign * cx[j];
			
			y[i][n] = sign * cons.getC();
		}
		
		double[] cx = functionMain.getCoefs();
		String dir = fMainDir == Common.F_MAX ? Common.F_MIN : Common.F_MAX;
			
		for (int j = 0; j < n; j++) {
			double[] cy = new double[m];
			int sign = 1;
			
			String cDir = dir == Common.F_MAX ? Common.C_LOE : Common.C_GOE;
			
			sign = 1;
			
			if (cx[j] < 0) {
				sign = -1;
				cDir = cDir == Common.C_LOE ? Common.C_GOE : Common.C_LOE;
			}
			
			for (int i = 0; i < m; i++)
				cy[i] = sign * y[i][j];
			
			listModelDual.addElement( new Constraint(cy, "y", cDir, sign * cx[j]) );
		}
		
		double[] cy = new double[m];
		for (int i = 0; i < m; i++)
			cy[i] = y[i][n];
			
		functionDual = new Function(cy, "y", dir);
		lblFDual.setText( functionDual.toString() );
		lblVDual.setText( truncVarsStr(sDualVars) );
		btnSolveDual.setEnabled(listModelDual.getSize() >= 2);
	}
	
	// Pack data to <Problem> object 
	private Problem makeProblem(String title, Function f, DefaultListModel<Constraint> lm, JLabel lbl) {
		Constraint[] cons = new Constraint[ lm.size() ]; 
		
		for (int i = 0; i < lm.size(); i++)
			cons[i] = lm.getElementAt(i);
		
		Problem res = new Problem(title, f, cons);
		res.setRestrictions( lbl.getText() );
		return res;
	}
	
	// Solve selected problem
	private void solve(Problem problem, String outputMode) {
		Solution sol = new Solution(problem, outputMode);
    	if ( sol.initSolution() )
    		sol.solve();
    	else
    		Common.showErrorMessage(this, "Error while initialization solution!");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch (action) {
		case GET_PROBLEM:
			getProblem();
			break;
		
		case SET_AMOUNT:
			changeVarsAmount();
			break;
		
		case EDIT_FUNCTION:
			Common.showFrame( new InputDialog(functionMain) );
			
			if (fnFromDialog != null) {
				functionMain = fnFromDialog;
				lblFMain.setText( functionMain.toString() );
				checkButtons();
			}
			
			break;
		
		case ADD_CONSTRAINT:
			Common.showFrame( new InputDialog( functionMain.getVarsAmount() ) );
			
			if (fnFromDialog != null) {
				listModelMain.addElement( (Constraint) fnFromDialog );
				listMainConstraints.setSelectedIndex( listModelMain.getSize()-1 );
				checkButtons();
			}
        	
			break;
			
		case EDIT_CONSTRAINT:
			Constraint selectedConstraint = listModelMain.get( listMainConstraints.getSelectedIndex() );
			Common.showFrame( new InputDialog(selectedConstraint) );
			
			if (fnFromDialog != null) {
				listModelMain.set( listMainConstraints.getSelectedIndex(), (Constraint) fnFromDialog );
				checkButtons();
			}
        	
			break;
        	
		case DELETE_CONSTRAINT:
			selectedConstraint = listModelMain.get( listMainConstraints.getSelectedIndex() );
			
			if ( Common.showConfirmDialog(this, "You really want to delete constraint: " + selectedConstraint.toString() + "?", "Delete") == YES ) {
				listModelMain.removeElementAt( listMainConstraints.getSelectedIndex() );
				if (listModelMain.getSize() != 0)
					listMainConstraints.setSelectedIndex(0);
				checkButtons();
			}
        	
			break;
        
		case CREATE_DUAL:
			createDualProblem();
			break;
			
        case SOLVE_MAIN:
        	solve(
        			makeProblem("Main problem", functionMain, listModelMain, lblVMain),
        			grpFormat.getSelection().getActionCommand()
        	);
            
        	break;
        	
        case SOLVE_DUAL:
        	solve(
        			makeProblem("Dual problem", functionDual, listModelDual, lblVDual),
        			grpFormat.getSelection().getActionCommand()
        	);
            
        	break;
            
        case EXIT:
        	closeFrame(e);
        	break;
		}
	}
	
	// Action on close main window
	private void closeFrame(java.awt.AWTEvent evt) {
		//if ( Common.showConfirmDialog(this, "You really want to exit?", "Exit") == YES )
			System.exit(0);
    }
}
