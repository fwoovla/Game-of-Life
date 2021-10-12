import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class World {

	private Random random;
	private boolean[][] world;
	private boolean[][] new_world;
	private int cell_size;
	private int width, height;
	
	public World(int width, int height, int cell_size) {
		random = new Random();
			
		this.setCell_size(cell_size);
		this.width = width;
		this.height = height;
		
		this.world = new boolean[width / cell_size][height / cell_size];
		this.new_world = new boolean[width / cell_size][height / cell_size];
		
		for (int x = 0; x < width / this.cell_size; x++) {
			for (int y = 0; y < height / this.cell_size; y++) {
				//this.world[x][y]= false; 
				this.world[x][y] = random.nextBoolean(); 
			}
		}
	}
	
	public void tick() {
		
		for (int x = 1; x < (width / this.cell_size) - 1; x++) {
			for (int y = 1; y < (height / this.cell_size) - 1; y++) {
				
				int neighbors =  checkNeighbors(x, y);
				
				//if dead cell
				if (world[x][y] == false) {
					if (neighbors == 3) { //new cell is born
						new_world[x][y] = true; 
					}					
				}
				
				// if alive cell
				if (world[x][y] == true ) {
					if (neighbors < 2) { //die
						new_world[x][y] = false; 
					}					
					if (neighbors > 1 && neighbors < 4) { // survive
						new_world[x][y] = true;
					}
					if (neighbors > 3) { // die
						new_world[x][y] = false;
					}
				}
			}
		}
		//world = new_world.clone();
		
		for (int x = 0; x < width / cell_size; x++) {
			for (int y = 0; y < height / cell_size; y++) {
				world[x][y] =  new_world[x][y];				
			}
		}
		for (int x = 0; x < width / cell_size; x++) {
			for (int y = 0; y < height / cell_size; y++) {
				new_world[x][y] = false;				
			}
			
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.green);
		
		for (int x = 0; x < width / this.cell_size; x++) {
			for (int y = 0; y < height / this.cell_size; y++) {
				if (world[x][y] == true ) {
					g.fillRect(x * this.cell_size, y *this.cell_size, this.cell_size, this.cell_size);
				}
			}
		}
	}
	
	public int checkNeighbors(int x, int y) {
		int _neighbors = 0;
		
		if (world[x - 1][y - 1] == true ) { 
			_neighbors++;
			
		}
		if (world[x][y - 1] == true ) {
			_neighbors++;

		}
		if (world[x + 1][y - 1] == true ) {
			_neighbors++;

		}
		if (world[x - 1][y] == true ) {
			_neighbors++;

		}
		if (world[x + 1][y] == true ) {
			_neighbors++;

		}
		if (world[x - 1][y + 1] == true ) {
			_neighbors++;

		}
		if (world[x][y + 1] == true ) {
			_neighbors++;

		}
		if (world[x + 1][y + 1] == true ) {
			_neighbors++;

		}
		return _neighbors;
	}
	
	public int getCell_size() {
		return cell_size;
	}
	public void setCell_size(int cell_size) {
		this.cell_size = cell_size;
	}
	public boolean[][] getWorld() {
		return world;
	}
	public void setWorld(boolean[][] world) {
		this.world = world;
	}

}
