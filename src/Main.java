import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class Main extends Application{
	
	protected static final boolean diagonalPathing = true;
	
	protected static final int gridSize = 50;
	protected static final double width = 1000;
	protected static final double height = 1000;
	
	protected static final int obstacleCount = 2000;
	
	protected static int threadSleep = 20;
	
	protected static Painter painter;
	protected static Pathfinder pathfinder;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		pathfinder = new Pathfinder();
		Canvas canvas = new Canvas(width, height);
		painter = new Painter(canvas.getGraphicsContext2D());
		
		initialize();
		
		Group root = new Group();
		root.getChildren().add(canvas);
		
		stage.setScene(new Scene(root));
		stage.setTitle("A* - Pathfinder");
		stage.show();
		
		stage.setOnCloseRequest(event -> {
			System.exit(0);
		});
	}
	
	private void initialize() {
		pathfinder.start();
	}

}
