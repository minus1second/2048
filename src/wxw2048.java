import java.util.Random;
import java.util.Scanner;

public class wxw2048 {
	public static int[][] game = new int[4][4];//a 4*4 2d array for the game board, 0 represent the place is empty	
	public static String d;//the direction of move
	public static int step = 0;
	public static boolean invalid;
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		generateNumber();
		generateNumber();
		show();
		while(true) {//check whether the game is over after each move, if satisfies the over condition, end the infinite loop
			System.out.println("Input ‘a’, ‘s’, ‘d’, and ‘w’ for left, down, right, up: (q for quit, r for restart)");		
			while(true) {
				d = scanner.nextLine();
				if(d.equals("a") != true && d.equals("s") != true && d.equals("d") != true && d.equals("w") != true && d.equals("q") != true && d.equals("r") != true) {
					System.out.println("Wrong input, please enter valid characters:");
					continue;
				}
				break;
			}
			//input validation for 6 letters
			
			if(d.equals("q")) {
				System.out.println("Are you sure to quit?(y for yes, else for no)");
				String confirm = scanner.nextLine();
				if(confirm.equals("y")) {//ask for confirm, if not, input again
					break;
				}else {
					continue;
				}
			}
			if(d.equals("r")) {
				System.out.println("Are you sure to restart?(y for yes, else for no)");
				String confirm = scanner.nextLine();
				if(confirm.equals("y")) {//to restart, set the board to null
					for(int a = 0; a < 4; a ++) {
						for(int b = 0; b < 4; b ++) {
							game[a][b] = 0;
						}
					}
					step = 0;//clean the step count
					generateNumber();//generate two numbers to form initial condition
					generateNumber();
					continue;
				}else {
					continue;
				}
			}
			combine(d);//combine all the identical adjacent numbers
			if(invalid == true) {//if there's no identical adjacent number
				if(complete()) {//then if the board is completely full, game over
					break;
				}
				if(full(d)) {//if not completely full but move along direction d will not change the board, it's an invalid move, ask for input again
					System.out.println("Invalid move, please input again.");
					continue;
				}
			}
			arrange(d);//arrange the board to move all numbers in the direction
			step ++;//the move is valid, count ++
			generateNumber();//generate a new number after the valid move
			show();//refresh the board	
		}
		System.out.println("Game over! Your have achieved " + getMax() + " in the game. And made " + step + " valid moves.");
	}
	
	
	//print the board, the value 0 print * instead
	static void show() {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();//refreshing the board
		for(int i = 0; i < 6; i ++) {
			System.out.printf("%-5c",'-');
		}
		System.out.println();
		for(int a = 0; a < 4; a ++) {
			System.out.printf("%-5c", '|');
			for(int b = 0; b < 4; b ++) {
				if(game[a][b] == 0) {
					System.out.printf("%-5c", '*');
				}else {
					System.out.printf("%-5d", game[a][b]);
				}
			}
			System.out.printf("%-5c", '|');
			System.out.println();
		}
		for(int i = 0; i < 6; i ++) {
			System.out.printf("%-5c",'-');
		}
		System.out.println();
	}
	
	
	//the check whether the direction has empty place to move
	static boolean full(String d) {
		if(d.equals("a")) {
			for(int a = 0; a < 4; a ++) {
				if(game[a][0] == 0 && (game[a][1] != 0 || game[a][2] != 0 || game[a][3] != 0)) {
					return false;
				}
				if(game[a][1] == 0 && (game[a][2] != 0 || game[a][3] != 0)) {
					return false;
				}
				if(game[a][2] == 0 && (game[a][3] != 0)) {
					return false;
				}
			}
		}
		if(d.equals("s")) {
			for(int b = 0; b < 4; b ++) {
				if(game[3][b] == 0 && (game[2][b] != 0 || game[1][b] != 0 || game[0][b] != 0)) {
					return false;
				}
				if(game[2][b] == 0 && (game[1][b] != 0 || game[0][b] != 0)) {
					return false;
				}
				if(game[1][b] == 0 && (game[0][b] != 0)) {
					return false;
				}
			}
		}
		if(d.equals("d")) {
			for(int a = 0; a < 4; a ++) {
				if(game[a][3] == 0 && (game[a][1] != 0 || game[a][2] != 0 || game[a][0] != 0)) {
					return false;
				}
				if(game[a][2] == 0 && (game[a][1] != 0 || game[a][0] != 0)) {
					return false;
				}
				if(game[a][1] == 0 && (game[a][0] != 0)) {
					return false;
				}
			}
		}
		if(d.equals("w")) {
			for(int b = 0; b < 4; b ++) {
				if(game[0][b] == 0 && (game[2][b] != 0 || game[1][b] != 0 || game[3][b] != 0)) {
					return false;
				}
				if(game[1][b] == 0 && (game[2][b] != 0 || game[3][b] != 0)) {
					return false;
				}
				if(game[2][b] == 0 && (game[3][b] != 0)) {
					return false;
				}
			}
		}
		return true;
	}
	
	//to check whether the board is completely full
	static boolean complete() {
		for(int a = 0; a < 4; a ++) {
			for(int b = 0; b < 4; b ++) {
				if(game[a][b] == 0){
					return false;
				}
			}
		}
		return true;
	}
	
	//generate a random number at a random place, also check whether the board is full
	static void generateNumber() {
		int newNum;
		Random rand = new Random();		
			int n = rand.nextInt(10) + 1;
			if(n <= 2) {//number 4 has probability of 0.2
				newNum = 4;
			}else {
				newNum = 2;
			}
			while(true) {//keep generate random coordinates until get somewhere empty and place the new number
				int row = rand.nextInt(4);
				int column = rand.nextInt(4);
				if(game[row][column] == 0) {
					game[row][column] = newNum;
					break;
				}else {
					continue;
				}
			}
	}
	
	//to check whether the direction has two identical numbers that can be added, if has, combine them, if not throughout the board, set invalid to true
	static void combine(String d) {
		invalid = true;
		if(d.equals("a")) {
			for(int a = 0; a < 4; a ++) {
				if(game[a][0] != 0 && game[a][0] == game[a][1]) {
					game[a][0] = 2 * game[a][1];
					game[a][1] = 0;
					invalid = false;
				}else if(game[a][0] != 0 && game[a][0] == game[a][2] && game[a][1] == 0) {
					game[a][0] = 2 * game[a][2];
					game[a][2] = 0;
					invalid = false;
				}else if(game[a][0] != 0 && game[a][0] == game[a][3] && game[a][1] == 0 && game[a][2] == 0) {
					game[a][0] = 2 * game[a][3];
					game[a][3] = 0;
					invalid = false;
				}
				if(game[a][1] != 0 && game[a][1] == game[a][2]) {
					game[a][1] = 2 * game[a][2];
					game[a][2] = 0;
					invalid = false;
				}else if(game[a][1] != 0 && game[a][1] == game[a][3] && game[a][2] == 0) {
					game[a][1] = 2 * game[a][3];
					game[a][3] = 0;
					invalid = false;
				}
				if(game[a][2] != 0 && game[a][2] == game[a][3]) {
					game[a][2] = 2 * game[a][3];
					game[a][3] = 0;
					invalid = false;
				}
			}
		}
		if(d.equals("s")) {
			for(int b = 0; b < 4; b ++) {
				if(game[3][b] != 0 && game[3][b] == game[2][b]) {
					game[3][b] = 2 * game[2][b];
					game[2][b] = 0;
					invalid = false;
				}else if(game[3][b] != 0 && game[2][b] == 0 &&game[3][b] == game[1][b]) {
					game[3][b] = 2 * game[1][b];
					game[1][b] = 0;
					invalid = false;
				}else if(game[3][b] != 0 && game[3][b] == game[0][b] && game[1][b] == 0 && game[2][b] == 0) {
					game[3][b] = 2 * game[0][b];
					game[0][b] = 0;
					invalid = false;
				}
				if(game[2][b] != 0 && game[2][b] == game[1][b]) {
					game[2][b] = 2 * game[1][b];
					game[1][b] = 0;
					invalid = false;
				}else if(game[2][b] != 0 && game[1][b] == 0 && game[2][b] == game[0][b]) {
					game[2][b] = 2 * game[0][b];
					game[0][b] = 0;
					invalid = false;
				}
				if(game[1][b] != 0 && game[1][b] == game[0][b]) {
					game[1][b] = 2 * game[0][b];
					game[0][b] = 0;
					invalid = false;
				}
			}	
		}
		if(d.equals("d")) {
			for(int a = 0; a < 4; a ++) {
				if(game[a][3] != 0 && game[a][3] == game[a][2]) {
					game[a][3] = 2 * game[a][3];
					game[a][2] = 0;
					invalid = false;
				}else if(game[a][3] != 0 && game[a][3] == game[a][1] && game[a][2] == 0) {
					game[a][3] = 2 * game[a][1];
					game[a][1] = 0;
					invalid = false;
				}else if(game[a][3] != 0 && game[a][0] == game[a][3] && game[a][1] == 0 && game[a][2] == 0) {
					game[a][3] = 2 * game[a][0];
					game[a][0] = 0;
					invalid = false;
				}
				if(game[a][2] != 0 && game[a][1] == game[a][2]) {
					game[a][2] = 2 * game[a][1];
					game[a][1] = 0;
					invalid = false;
				}else if(game[a][2] != 0 && game[a][2] == game[a][0] && game[a][1] == 0) {
					game[a][2] = 2 * game[a][0];
					game[a][1] = 0;
					invalid = false;
				}
				if(game[a][1] != 0 && game[a][1] == game[a][0]) {
					game[a][1] = 2 * game[a][0];
					game[a][0] = 0;
					invalid = false;
				}
			}
		}
		if(d.equals("w")) {
			for(int b = 0; b < 4; b ++) {
				if(game[0][b] != 0 && game[0][b] == game[1][b]) {
					game[0][b] = 2 * game[1][b];
					game[1][b] = 0;
					invalid = false;
				}else if(game[0][b] != 0 && game[1][b] == 0 && game[0][b] == game[2][b]) {
					game[0][b] = 2 * game[2][b];
					game[2][b] = 0;
					invalid = false;
				}else if(game[0][b] != 0 && game[3][b] == game[0][b] && game[1][b] == 0 && game[2][b] == 0) {
					game[0][b] = 2 * game[3][b];
					game[3][b] = 0;
					invalid = false;
				}
				if(game[1][b] != 0 && game[2][b] == game[1][b]) {
					game[1][b] = 2 * game[2][b];
					game[2][b] = 0;
					invalid = false;
				}else if(game[1][b] != 0 && game[2][b] == 0 && game[1][b] == game[3][b]) {
					game[1][b] = 2 * game[3][b];
					game[3][b] = 0;
					invalid = false;
				}
				if(game[2][b] != 0 && game[2][b] == game[3][b]) {
					game[2][b] = 2 * game[3][b];
					game[3][b] = 0;
					invalid = false;
				}
			}
		}
	}
	
	//return the maximum number in the board
	static int getMax() {
		int max = game[0][0];
		for(int a = 0; a < 4; a ++) {
			for(int b = 0; b < 4; b ++) {
				if(game[a][b] > max) {
					max = game[a][b];
				}
			}
		}
		return max;
	}
	
	//arrange the board in particular direction in order to complete the move
	static void arrange(String d) {
		int t;//a temporary int for exchange values;
		if(d.equals("a")) {
			for(int a = 0; a < 4; a ++) {
				if(game[a][0] == 0) {
					if(game[a][1] != 0) {
						t = game[a][1];
						game[a][1] = 0;
						game[a][0] = t;
					}else if(game[a][2] != 0) {
						t = game[a][2];
						game[a][2] = 0;
						game[a][0] = t;
					}else if(game[a][3] != 0) {
						t = game[a][3];
						game[a][3] = 0;
						game[a][0] = t;
					}
				}
				if(game[a][1] == 0) {
					if(game[a][2] != 0) {
						t = game[a][2];
						game[a][2] = 0;
						game[a][1] = t;
					}else if(game[a][3] != 0) {
						t = game[a][3];
						game[a][3] = 0;
						game[a][1] = t;
					}
				}
				if(game[a][2] == 0) {
					if(game[a][3] != 0) {
						t = game[a][3];
						game[a][3] = 0;
						game[a][2] = t;
					}
				}
			}
		}
		if(d.equals("s")) {
			for(int b = 0; b < 4; b ++) {
				if(game[3][b] == 0) {
					if(game[2][b] != 0) {
						t = game[2][b];
						game[2][b] = 0;
						game[3][b] = t;
					}else if(game[1][b] != 0) {
						t = game[1][b];
						game[1][b] = 0;
						game[3][b] = t;
					}else if(game[0][b] != 0) {
						t = game[0][b];
						game[0][b] = 0;
						game[3][b] = t;
					}
				}
				if(game[2][b] == 0) {
					if(game[1][b] != 0) {
						t = game[1][b];
						game[1][b] = 0;
						game[2][b] = t;
					}else if(game[0][b] != 0) {
						t = game[0][b];
						game[0][b] = 0;
						game[2][b] = t;
					}
				}
				if(game[1][b] == 0) {
					if(game[0][b] != 0) {
						t = game[0][b];
						game[0][b] = 0;
						game[1][b] = t;
					}
				}
			}
		}
		if(d.equals("d")) {
			for(int a = 0; a < 4; a ++) {
				if(game[a][3] == 0) {
					if(game[a][2] != 0) {
						t = game[a][2];
						game[a][2] = 0;
						game[a][3] = t;
					}else if(game[a][1] != 0) {
						t = game[a][1];
						game[a][1] = 0;
						game[a][3] = t;
					}else if(game[a][0] != 0) {
						t = game[a][0];
						game[a][0] = 0;
						game[a][3] = t;
					}
				}
				if(game[a][2] == 0) {
					if(game[a][1] != 0) {
						t = game[a][1];
						game[a][1] = 0;
						game[a][2] = t;
					}else if(game[a][0] != 0) {
						t = game[a][0];
						game[a][0] = 0;
						game[a][2] = t;
					}
				}
				if(game[a][1] == 0) {
					if(game[a][0] != 0) {
						t = game[a][0];
						game[a][0] = 0;
						game[a][1] = t;
					}
				}
			}
		}
		if(d.equals("w")) {
			for(int b = 0; b < 4; b ++) {
				if(game[0][b] == 0) {
					if(game[1][b] != 0) {
						t = game[1][b];
						game[1][b] = 0;
						game[0][b] = t;
					}else if(game[2][b] != 0) {
						t = game[2][b];
						game[2][b] = 0;
						game[0][b] = t;
					}else if(game[3][b] != 0) {
						t = game[3][b];
						game[3][b] = 0;
						game[0][b] = t;
					}
				}
				if(game[1][b] == 0) {
					if(game[2][b] != 0) {
						t = game[2][b];
						game[2][b] = 0;
						game[1][b] = t;
					}else if(game[3][b] != 0) {
						t = game[3][b];
						game[3][b] = 0;
						game[1][b] = t;
					}
				}
				if(game[2][b] == 0) {
					if(game[3][b] != 0) {
						t = game[3][b];
						game[3][b] = 0;
						game[2][b] = t;
					}
				}
			}
		}
	}
	
}
