import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;


public class Game extends Canvas implements Runnable{

	
	private int width = 500;
	private int height = 500;
	private String title = "Langston's Ant";
	private boolean isRunning = false;
	
	private World world;
	private Thread thread;
	
	public Game() {
		new Window(width, height, title, this);
		
		world = new World(width, height, 50, 1);
		
		start();
	}
	
	public void tick() {
		world.tick();
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if (bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, width, height);
		
		world.render(g);
		
		bs.show();
		g.dispose();
	}

	public void run() {
		this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 10.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        long now = 0;
        boolean canRend = false;
        
        while(isRunning) {

            now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            
            while(delta >= 1) {
                tick();
                updates++;
                delta--;
                canRend = true;
            }
            
            if(canRend) {
            	render();
            	frames++;
            	canRend = false;
            }
            
   		  	if(System.currentTimeMillis() - timer > 1000) { 
   		  		timer += 1000;
   		  		System.out.println(" Steps: " + updates);
   		  		frames = 0;
   		  		//updates = 0; 
   		  	} 
        }		
	}
	
	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public static void main(String[] args) {
		new Game();
	}
}
