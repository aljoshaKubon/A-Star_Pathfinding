import java.util.ArrayList;

public class Cell {
	protected static int size = (int)Main.width/Main.gridSize - 2;

	private ArrayList<Cell> _neighbors = new ArrayList<Cell>();
	
	private boolean _obstacle;
	private final double _x, _y;
	private double _f, _g, _h;
	private Cell _previous;
	
	public Cell(int x, int y) {
		_x = x;
		_y = y;
	}
	
	protected double getX() {
		return _x;
	}
	
	protected double getY() {
		return _y;
	}
	
	protected double getF() {
		return _f;
	}
	
	protected double getG() {
		return _g;
	}
	
	protected double getH() {
		return this.calcHeuristic();
	}
	
	protected Cell getPrevious() {
		return _previous;
	}
	
	protected boolean getObstacle() {
		return _obstacle;
	}
	
	protected void setObstacle(boolean bool) {
		_obstacle = bool;
	}
	
	protected void setF(double f) {
		_f = f;
	}
	
	protected void setG(double g) {
		_g = g;
	}
	
	protected void setH(double h) {
		_h = h;
	}
	
	protected ArrayList<Cell> getNeighbors(){
		if(!(_neighbors.size() > 1)) {
			initializeNeighbors();
		}
		return _neighbors;
	}
	
	protected void setPrevious(Cell cell) {
		_previous = cell;
	}
	
	protected void initializeNeighbors() {
		int x = (int)_x;
		int y = (int)_y;
		
		if(Main.diagonalPathing) {
			setNeighbor(0, x-1,y-1);	 //OL
			setNeighbor(2, x+1,y-1);	 //RO
			setNeighbor(5, x-1,y+1);	 //LU
			setNeighbor(7, x+1,y+1);	 //RU
		}
		
		setNeighbor(1, x,y-1);		 //O
		setNeighbor(3, x-1,y);		 //L
		setNeighbor(4, x+1,y);		 //R
		setNeighbor(6, x,y+1);		 //U	
	}
	
	protected double calcHeuristic() {
		return (Math.abs(Pathfinder.target.getX() - this.getX()) + Math.abs(Pathfinder.target.getY() - this.getY()));
	}
	
	private void setNeighbor(int index, int x, int y) {
		try {
			Cell temp = Pathfinder.cells[x][y];
			if(!temp.getObstacle()) {
				_neighbors.add(temp);
			}
		}catch(ArrayIndexOutOfBoundsException e){
		}

	}

	public int equals(Cell cell) {
		return (int)( (this._x - cell._x) + (this._y - cell._y) );
	}

}
