import java.awt.*;
import java.awt.event.*;

class SpaceGame extends Frame implements KeyListener, Runnable {

    int ballX = 200, ballY = 100;
    int ballDX = 3, ballDY = 3;

    int paddleX = 160;
    int score = 0;

    Thread t;

    SpaceGame() {
        setTitle("Bounce Game");
        setSize(400, 400);
        setVisible(true);

        addKeyListener(this);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        t = new Thread(this);
        t.start();
    }

    public void paint(Graphics g) {

        // Ball
        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, 20, 20);

        // Paddle
        g.setColor(Color.BLUE);
        g.fillRect(paddleX, 350, 80, 10);

        // Score
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 20, 50);
    }

    public void run() {
        while (true) {

            ballX += ballDX;
            ballY += ballDY;

            // Wall collision (left & right)
            if (ballX <= 0 || ballX >= 380) {
                ballDX = -ballDX;
            }

            // Top collision
            if (ballY <= 50) {
                ballDY = -ballDY;
            }

            // Paddle collision
            if (ballY >= 330 && ballX >= paddleX && ballX <= paddleX + 80) {
                ballDY = -ballDY;
                score++; // increase score
            }

            // Bottom (missed)
            if (ballY > 380) {
                score = 0; // reset score
                ballY = 100;
                ballX = 200;
            }

            repaint();

            try {
                Thread.sleep(20);
            } catch (Exception e) {}
        }
    }

    // Controls
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            paddleX -= 20;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            paddleX += 20;
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new SpaceGame();
    }
}