import java.util.Iterator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Painter {
	private final GraphicsContext _gc;

	protected static boolean ready = true;
	
	public Painter(GraphicsContext gc) {
		_gc = gc;
	}
	
	protected void draw() {
		_gc.clearRect(0, 0, Main.width, Main.height);
		
		drawGrid();
		drawCells();
	}
	
	private void drawCells() {
		
		_gc.setFill(Color.DARKGREEN);
		for(Iterator<Cell> iter = Pathfinder.openSet.iterator(); iter.hasNext();) {
			Cell temp = iter.next();
			double x = temp.getX() * (Main.width/Main.gridSize) + 1;
			double y = temp.getY() * (Main.height/Main.gridSize) + 1;
			_gc.fillRect(x, y, Cell.size, Cell.size);
		}
		
		
		_gc.setFill(Color.RED);
		for(Iterator<Cell> iter = Pathfinder.closedSet.iterator(); iter.hasNext();) {
			Cell temp = iter.next();
			double x = temp.getX() * (Main.width/Main.gridSize) + 1;
			double y = temp.getY() * (Main.height/Main.gridSize) + 1;
			_gc.fillRect(x, y, Cell.size, Cell.size);
		}
		
		_gc.setFill(Color.BLACK);
		for(Iterator<Cell> iter = Pathfinder.obstacles.iterator(); iter.hasNext();) {
			Cell temp = iter.next();
			double x = temp.getX() * (Main.width/Main.gridSize) + 1;
			double y = temp.getY() * (Main.height/Main.gridSize) + 1;
			_gc.fillRect(x, y, Cell.size, Cell.size);
		}
		
		_gc.setFill(Color.BLUE);
		for(Iterator<Cell> iter = Pathfinder.path.iterator(); iter.hasNext();) {
			Cell temp = iter.next();
			double x = temp.getX() * (Main.width/Main.gridSize) + 1;
			double y = temp.getY() * (Main.height/Main.gridSize) + 1;
			_gc.fillRect(x, y, Cell.size, Cell.size);
		}	
	}
	
	private void drawGrid() {
		int cellsX = (int)Main.width/Main.gridSize;
		
		for(int i = 0; i < Main.gridSize; i++) {
			_gc.strokeLine(cellsX*i, 0, cellsX*i, Main.height);
			_gc.strokeLine(0, cellsX*i, Main.width, cellsX*i);
		}
	}
}
