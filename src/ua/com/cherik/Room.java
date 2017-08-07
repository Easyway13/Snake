package ua.com.cherik;


import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Room {
    private int width;
    private int height;
    private Snake snake;
    private Mouse mouse;

    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
    }

    public Snake getSnake() {
        return snake;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    /**
     Main cycle of program
     */
    public void run() {
        //Create an object "observe the keyboard" and start it
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        //till snake is alive
        while (snake.isAlive()) {
            //Does the observer include an event of clicking buttons?
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                //If key equals 'q' - exit the game.
                if (event.getKeyChar() == 'q') return;

                //If "LEFT" key - move the figure to the left
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    snake.setDirection(SnakeDirection.LEFT);
                    //If "RIGHT" key - move the figure to the right
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    snake.setDirection(SnakeDirection.RIGHT);
                    //If "UP" key - move the figure up
                else if (event.getKeyCode() == KeyEvent.VK_UP)
                    snake.setDirection(SnakeDirection.UP);
                    //If "DOWN" key - move the figure down
                else if (event.getKeyCode() == KeyEvent.VK_DOWN)
                    snake.setDirection(SnakeDirection.DOWN);
            }

            snake.move();   //move a snake
            print();        //to show the current stay of game
            sleep();        //pause between moves
        }

        //Show the message "Game Over"
        System.out.println("Game Over!");
    }

    /**
     * Show on display the current state of game
     */
    public void print() {
        //Create the array where we can draw current state of game
        int[][] matrix = new int[height][width];

        //Draw all pieces of a snake
        ArrayList<SnakeSection> sections = new ArrayList<SnakeSection>(snake.getSections());
        for (SnakeSection snakeSection : sections) {
            matrix[snakeSection.getY()][snakeSection.getX()] = 1;
        }

        // Draw a head of a snake(4 - if a snake is died)
        matrix[snake.getY()][snake.getX()] = snake.isAlive() ? 2 : 4;

        //Draw a mouse
        matrix[mouse.getY()][mouse.getX()] = 3;

        //Show everything on a display
        String[] symbols = {" . ", " x ", " X ", "^_^", "RIP"};
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(symbols[matrix[y][x]]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    /**
     * Method call when mouse has been eaten
     */
    public void eatMouse() {
        createMouse();
    }

    /**
     * Create a new mouse
     */
    public void createMouse() {

        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);

        System.out.print(x + " " + y + "; ");
        mouse = new Mouse(x, y);


    }


    public static Room game;

    public static void main(String[] args) {
        game = new Room(20, 20, new Snake(10, 10));
        game.snake.setDirection(SnakeDirection.DOWN);
        game.createMouse();
        game.run();
    }


    private int initialDelay = 520;
    private int delayStep = 20;

    /**
     * The program make a pause. The length of pause are depend on the length of snake.
     */
    public void sleep() {
        try {
            int level = snake.getSections().size();
            int delay = level < 15 ? (initialDelay - delayStep * level) : 200;
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
    }
}
