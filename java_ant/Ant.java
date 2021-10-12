import java.util.Random;

public class Ant {

	private Random random = new Random();
	public int dir = 0;  //0=up, 1=right, 2=down, 3=left
	public int x, y;
	private World world;
	
	public Ant(int x, int y, World world) {
		this.x = x;
		this.y = y;
		this.dir = random.nextInt(3);
		this.world = world;
	}
	
	public void tick(int cell_size) {
		if (dir == 0) {
			y -= cell_size;
			if (y <= 0) {
			}
		}
		if (dir == 2) {
			y += cell_size;
			if (y + cell_size >= world.getHeight()) {
			}
		}
		if (dir == 1) {
			x += cell_size;
			if (x + cell_size >= world.getWidth()) {
				x = 0;
			}
		}
		if (dir == 3) {
			x -= cell_size;
			if (x <= 0) {
				x = world.getWidth();
			}
		}
		System.out.println("x: " + x);
		System.out.println("y: " + y);

	}
	
	public void setDir(int _d) { //-1 = left, 1 = right
		dir += _d;
		
		if (dir < 0) {
			dir = 3;			
		}
		if (dir > 3) {
			dir = 0;
		}
	}
	
	public int getDir() {
		return dir;
	}

}
