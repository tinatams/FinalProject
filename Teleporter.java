/**
    Teleporter Class implements Collidable. Decides where players get placed if they are in the radius of the teleporter 
    (includes serperate maps and where the player is facing).

	@author Martina Amale M. Llamas (242648); Zoe Angeli G. Uy (246707)
	@version May 19, 2025
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.

    

**/

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Teleporter implements Collidable{
    private int teleportTo;

    private int worldX, worldY, width, height;
    private int putPlayerX, putPlayerY;
    private int directionMove;

    private Rectangle hitBox;
    
    public Teleporter(int x, int y, int w, int h, int map, int newX, int newY, int direction){ //constructor
        teleportTo = map;
        worldX = x;
        worldY = y;
        width = w;
        height = h;

        putPlayerX = newX;
        putPlayerY = newY;

        directionMove = direction;

        hitBox = new Rectangle(worldX, worldY, width, height);
    }

    public void draw(Graphics2D g2d){ //draws the teleporter location
        g2d.setColor(Color.BLUE);
        g2d.fill(hitBox);
    }

    public int teleportToMap(){ //getter for map where players will get teleported to
        return teleportTo;
    }

    public int teleportPlayerX(){ //getter for x-coordinates where players will get teleported to
        return putPlayerX;
    }

    public int teleportPlayerY(){ //getter for y-coordinates where players will get teleported to
        return putPlayerY;
    }

    @Override
    public int getSpriteW() { //getter for teleporter width
        return width;
    }

    @Override
    public int getSpriteH() { //getter for teleporter height
        return height;
    }
    @Override
    public int getWorldX() { //getter for teleporter x-coordinates
        return worldX;
    }

    @Override
    public int getWorldY() { //getter for teleporter y-coordinates
        return worldY;
    }

    @Override
    public Rectangle getHitBox() { //getter for hitbox
        return hitBox;
    }

    public int getDirection(){ //getter for direction needed to activate teleporter
        return directionMove;
    }
}