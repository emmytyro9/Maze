import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bubblebitoey on 12/13/2016 AD.
 */
public class Main {
	Graph g;
	int[] levels;
	int[] parents;
	char[] character;
	char tmp;
	int row, column;
	int id = 0;
	int begin, finish;
	String line;
	
	public static void main(String[] args) throws IOException {
		Main m = new Main();
		m.readInput();
	}
	
	private void readInput() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String[] arr = reader.readLine().split(" ");
		row = Integer.parseInt(arr[0]);
		column = Integer.parseInt(arr[1]);
		character = new char[row * column];
		g = new Graph(row * column);
		
		for (int i = 0; i < row; i++) {
			line = reader.readLine();
			for (int j = 0; j < column; j++) {
				tmp = line.charAt(j);
				character[id] = tmp;
				if (j < column - 1) if (character[id] != '#' && character[id + 1] != '#') g.addEdge(id, id + 1);
				if (i < row - 1) if (character[id] != '#' && character[id + column] != '#') g.addEdge(id, id + column);
				if (character[id] == 'O') {
					begin = id;
				} else if (character[id] == 'X') {
					finish = id;
				}
				id++;
			}
		}
		
		breadthFirstSearch(begin);
		
		System.out.println(levels[finish]);
		int current = parents[finish];
		int before = finish;
		
		
		while (current != begin) {
			if (current == before + 1) character[current] = '<';
			else if (current == before - 1) character[current] = '>';
			else if (current == before + column) character[current] = '^';
			else if (current == before - column) character[current] = 'v';
			
			before = current;
			current = parents[current];
		}
		
		for (int i = 0; i < row * column; i++) {
			if (i % column == 0 && i != 0) {
				System.out.println();
			}
			System.out.print(character[i]);
		}
		
	}
	
	void breadthFirstSearch(int s) {
		List<Integer> nextLevel = new ArrayList<Integer>();
		levels = new int[g.getN()];
		parents = new int[g.getN()];
		
		for (int u = 0; u < g.getN(); u++) {
			levels[u] = -1;
			parents[u] = -1;
		}
		levels[s] = 0;
		nextLevel.add(s);
		
		while (!nextLevel.isEmpty()) {
			List<Integer> currentLevel = nextLevel;
			nextLevel = new ArrayList<Integer>();
			
			for (int u : currentLevel) {
				for (int v : g.getAdjList(u)) {
					if (levels[v] == -1) {
						levels[v] = levels[u] + 1;
						parents[v] = u;
						nextLevel.add(v);
					}
				}
			}
		}
	}
}
