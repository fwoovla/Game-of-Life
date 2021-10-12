import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class World {

	private Random random = new Random();
	private int width, height, cell_size, num_ants, xCells, yCells;
	
	
	int[][] cells;
	Ant[] ants;
	
	public World(int width, int height, int cell_size, int num_ants) {
		this.width = width;
		this.height = height;
		this.cell_size = cell_size;
		this.num_ants = num_ants;		
		this.xCells = width / cell_size;
		this.yCells = width / cell_size;
		
		
		cells = new int[width / cell_size][height / cell_size];
		ants = new Ant[num_ants];
		
		for (int i = 0; i < num_ants; i++) {
			ants[i]= new Ant((width / 2), (height / 2), this); 
			//ants[i]= new Ant(random.nextInt(xCells) * cell_size, random.nextInt(yCells) * cell_size);
		}
		
		for (int x = 0; x < width / cell_size; x++) {
			for (int y = 0; y < height / cell_size; y++) {
				cells[x][y] = 0; 
			}
		}
	}

	public int getxCells() {
		return xCells;
	}

	public void setxCells(int xCells) {
		this.xCells = xCells;
	}

	public int getyCells() {
		return yCells;
	}

	public void setyCells(int yCells) {
		this.yCells = yCells;
	}

	public void tick() {
		
		//loop through ants
		for (int i = 0; i < num_ants; i++) {
			if (cells[ants[i].x / cell_size][ants[i].y / cell_size] > 0) {
				cells[ants[i].x / cell_size][ants[i].y / cell_size] = 0;
				ants[i].setDir(-1);
			}
			else if (cells[ants[i].x / cell_size][ants[i].y / cell_size] == 0) {
				cells[ants[i].x / cell_size][ants[i].y / cell_size] = 1;
				ants[i].setDir(1);
			}
			ants[i].tick(cell_size);
		}

	}
	
	public void render(Graphics g) {
		
		g.setColor(Color.gray);
		for (int x = 0; x < xCells; x++) {
			g.drawLine(x * cell_size, 0, x * cell_size, height);
		}
		for (int y = 0; y < yCells; y++) {
			g.drawLine(0, y * cell_size, width, y * cell_size);
			
		}
		
		g.setColor(Color.cyan);
		
		//draw cells
		for (int x = 0; x < xCells; x++) {
			for (int y = 0; y < yCells; y++) {
				if (cells[x][y] > 0) {
					g.fillRect(x * cell_size, y * cell_size, cell_size, cell_size);				

				}
			}
		}
		
		g.setColor(Color.red);
		
		//draw ants
		for (int i = 0; i < num_ants; i++) {
			g.fillRect(ants[i].x, ants[i].y, cell_size, cell_size);
		}
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getCell_size() {
		return cell_size;
	}

	public void setCell_size(int cell_size) {
		this.cell_size = cell_size;
	}

	public int getNum_ants() {
		return num_ants;
	}

	public void setNum_ants(int num_ants) {
		this.num_ants = num_ants;
	}

	public int[][] getCells() {
		return cells;
	}

	public void setCells(int[][] cells) {
		this.cells = cells;
	}

}
