import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class Pathfinder extends Thread{
	protected static Cell[][] cells = new Cell[Main.gridSize][Main.gridSize];
	
	protected static HashSet<Cell> openSet = new HashSet<Cell>();
	protected static HashSet<Cell> closedSet = new HashSet<Cell>();
	protected static HashSet<Cell> path = new HashSet<Cell>();
	protected static HashSet<Cell> obstacles = new HashSet<Cell>();
	
	protected static Cell start;
	protected static Cell target;
	
	private int sleep = Main.threadSleep;
	private int _pathCells = 0;
	
	public Pathfinder() {
		initialize();
	}
	
	public void run() {
		while(!interrupted()) {
			try {
				sleep(sleep);
				processPath();
				Main.painter.draw();
			} catch (InterruptedException e) {
				System.out.println("Pathfinder is interrupted!");
			}
		}
	}
	
	private void processPath() {
		
		if(openSet.size() > 0) {
			Iterator<Cell> first = openSet.iterator();
			Cell current = first.next();
			
			for(Iterator<Cell> iter = openSet.iterator(); iter.hasNext();) {
				Cell temp = iter.next();
				if(temp.getF() < current.getF()) {
					current = temp;
				}
			}
			
			if(current.equals(target) == 0) {
				System.out.println("Shortest way found!");
				createPath(current);
				this.interrupt();
			}
			
			openSet.remove(current);
			closedSet.add(current);
			
			expandKnot(current);
		}else {
			System.out.println("No way found!");
			this.interrupt();
		}
	}
	
	private void expandKnot(Cell cell) {
		double tentativeG;
		for(Cell neighbor: cell.getNeighbors()) {
			if(!closedSet.contains(neighbor)) {
				tentativeG = cell.getG() + 1;
				if( !openSet.contains(neighbor) || (openSet.contains(neighbor) && tentativeG < neighbor.getG())) {
					neighbor.setPrevious(cell);
					neighbor.setG(tentativeG);
					neighbor.setF(neighbor.getH() + neighbor.getG());
					
					if(!openSet.contains(neighbor)) {
						openSet.add(neighbor);
					}
				}
			}
		}
	}
	
	private void createPath(Cell current) {
		while(current.getPrevious() != null) {
			path.add(current);
			current = current.getPrevious();
			_pathCells++;
		}
		System.out.println("Way is " + _pathCells + " cells long.");
	}
	
	private void initialize() {
		for(int row = 0; row < cells.length; row++) {
			for(int col = 0; col < cells[0].length; col++) {
				cells[row][col] = new Cell(row, col);
			}
		}
		
		start = cells[0][0];
		target = cells[Main.gridSize-1][Main.gridSize-1];
		
		openSet.add(start);
		
		for(int i = 0; i < Main.obstacleCount; i++) {
			int row = ThreadLocalRandom.current().nextInt(0, Main.gridSize);
			int col = ThreadLocalRandom.current().nextInt(0, Main.gridSize);
			
			Cell temp = cells[row][col];
			
			if(checkCell(temp)) {
				temp.setObstacle(true);
				obstacles.add(temp);
			}
		}
	}
	
	private boolean checkCell(Cell cell) {
		if(cell.equals(start) == 0 || cell.equals(target) == 0) {
			return false;
		}
		return true;
	}

}
