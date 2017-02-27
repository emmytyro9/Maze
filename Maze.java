import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Maze {
	static Graph g ;
	static int[] levels;
	static int[] parents;
	static int row ;
	static int column;
	static char[] maze ;
	static int id = 0;
	static int start ;
	static int end ;
	
	 
	public static void main(String[] args) {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			String fLine = reader.readLine();
			String[] array = fLine.split(" ");
			row = Integer.parseInt(array[0]);
			column = Integer.parseInt(array[1]);
			maze = new char[row*column];
			g = new Graph(row*column) ;
			
			for(int i = 0; i < row; i++) {
				String line = reader.readLine();
				for(int j = 0; j < column; j++) {
					char c = line.charAt(j) ;
					maze[id] = c ;
					if(j < column - 1) {
						if(maze[id] != '#' && maze[id+1] != '#') {
							g.addEdge(id, id+1);
						}
					}
					if(i < row -1) {
						if(maze[id] != '#' && maze[id+column] != '#') {
							g.addEdge(id, id+column);
						}
					}
					if(maze[id] == 'O') {
						start = id ;
					}else if(maze[id] == 'X'){
						end = id ;
					}
					id++;
				}
			}
			
			breadthFirstSearch(start) ;
			System.out.println(levels[end]);
			int current = parents[end] ;
			int before = end ;
			char left = '<' ;
			char right = '>' ;
			char up = '^' ;
			char down = 'v' ;
			
			while(current != start) {
				if(current == before + 1) {
					maze[current] = left ;
				}else if(current == before - 1) {
					maze[current] = right ;
				}else if(current == before + column) {
					maze[current] = up ;
				}else if( current == before - column) {
					maze[current] = down ;
				}
				
				before = current ;
				current = parents[current] ;
			}
			
				
			for(int i = 0; i < row*column; i++) {
				if(i % column == 0 && i != 0) {
					System.out.println();
				}
				System.out.print(maze[i]);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
   public static void breadthFirstSearch(int s) {
        List<Integer> nextLevel = new ArrayList<Integer>();
        levels = new int[row*column] ;
        parents = new int[row*column] ;
 
        for(int u = 0; u < row*column ; u++) {
            levels[u] = -1 ;
            parents[u] = -1 ;
        }
        
        levels[s] = 0 ;
        nextLevel.add(s) ;
 
        while(! nextLevel.isEmpty()) {
            List<Integer> currentLevel = nextLevel;
            nextLevel = new ArrayList<Integer>();
 
            for(int u : currentLevel) {
                for(int v : g.getAdjList(u)) {
                    if(levels[v] == -1) {
                        levels[v] = levels[u] + 1;
                        parents[v] = u;
                        nextLevel.add(v);
                    }
                }
            }
        }
    }
     
     
   
    
}


