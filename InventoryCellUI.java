/**
    Inventory Cell UI is a UI Component, that is used to display items inside of an inventory.
    Can be interacted with mouse. Since it is an UIComponent that sometimes acts like a button 
    extends the class UIButton. 

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
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class InventoryCellUI implements UIButton{
    private int x, y;
    private UIHandler ui;
    private EntityGenerator eg;

    private String owner;

    private BufferedImage containerIMG, highlightedIMG;
    private SuperItem contents;
    private boolean highlighted,mousePressed,mouseOver;
    private Rectangle bounds;

    /**
        Constructor that instantiates the initial value of the inventory cell

        @param xPos is the X position of the inventory cell
        @param yPos is the Y position of the inventory cell 
        @param u is the ui handler
        @param o is the owner of the inventory (Hermes or Player)

        initializes the default values for ui component
        hightlighted, mouse over and pressed to false

        initializes the images/ sprites of the button
    **/
    public InventoryCellUI(int xPos, int yPos, UIHandler u, String o){
        ui = u;
        owner = o;

        x = xPos;
        y = yPos;
        
        highlighted = false;
        mousePressed = false;
        mouseOver = false;

        eg = new EntityGenerator();

        try {
            int tileSize = GameFrame.PIXELRATIO;
            BufferedImage temp = ImageIO.read(new File(String.format("./res/uiAssets/ButtonAtlas.png")));
            containerIMG = temp.getSubimage(0, 3 * tileSize, tileSize, tileSize);
            highlightedIMG = temp.getSubimage(1 * tileSize, 3 * tileSize, tileSize, tileSize);
        } catch (IOException ex) {
        }

        bounds = new Rectangle(x, y, GameFrame.SCALED, GameFrame.SCALED);
    }

    /**
        Sets teh contents of the inventory cell 
        @param item is the item to be displayed inside of the inventory cell
    **/
    public void setContents(SuperItem item){
        contents = item;
    }

    /**
        Draws the UI component and its contents. Determines which sprite to draw depending 
        if the box is highlighted or not. Draws the contents of the inventory cell in the middle
        of the cell. As well as the amount of the item in the corner (if the item has more than 1).

        @param g2d, is the Graphics2D object that draws the components
    **/
    @Override
    public void draw(Graphics2D g2d){
        BufferedImage toDraw = containerIMG;
        if (highlighted){
            toDraw = highlightedIMG;
        }

        g2d.drawImage(toDraw, x, y, GameFrame.SCALED, GameFrame.SCALED, null);
        if (contents != null){
            contents.drawSpecific(g2d,x+ 3,y+ 3, 14 * GameFrame.SCALER, 14 * GameFrame.SCALER);

            if (contents.getAmount() > 1){
                g2d.setColor(new Color(255, 255, 255));
                g2d.setFont(UIHandler.regularFont.deriveFont(20f));
                g2d.drawString(Integer.toString(contents.getAmount()), x + 9*GameFrame.SCALER, y + 10*GameFrame.SCALER + 10);
            }
        } 
    }

    /**
        Updates the hightlighted variable depending on if the UI Component is being hovered over 
        or pressed. 
    **/
    @Override
    public void update(){
        if (mouseOver || mousePressed){
            highlighted = true;
        } else {
            highlighted = false;
        }
    }

    /**
        Gets if the UIComponent is being pressed by the mouse
        @return mousePressed
    **/
    @Override
    public boolean isMousePressed() {
        return mousePressed;
    }

    /**
        Sets the state of mousePressed
        @param mousePressed is what the new value of mousePressed will be
    **/
    @Override
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    /**
        Gets if the UIComponent is being hovered over by the mouse
        @return mouseOver
    **/
    @Override
    public boolean isMouseOver() {
        return mouseOver;
    }

    /**
        Sets the state of mouseOver
        @param mouseOver is what the new value of mouseOver will be
    **/
    @Override
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    /**
        Gets the bounds of the component
        @return bounds
    **/
    @Override
    public Rectangle getBounds(){
        return bounds;
    }

    /**
        Action that happens when the Inventory cell is clicked. 
        
        If the game is inside of the Hermes menu, then it will remove the item from the owner and switch
        it to the other entity (if hermes is the owner, then swap to player and vise versa).
    **/
    @Override
    public void clicked(){
        if (GameFrame.gameState == GameFrame.HERMES_STATE){
            if(contents != null){
                SuperItem itemToTransfer = null;
                if (contents instanceof KeyItem){
                    itemToTransfer = contents;
                } else {
                    itemToTransfer = eg.newItem(contents.getName());
                }

                if (itemToTransfer != null){
                    if (owner.equals("Player")){
                        ui.getSelectedPlayer().discardItem(itemToTransfer);
                        ((Hermes) ui.getMapHandler().getNPC(Hermes.name)).collect(itemToTransfer);
                        System.out.println("swapped P TO H");
                    } else if (owner.equals(Hermes.name)){
                        ((Hermes) ui.getMapHandler().getNPC(Hermes.name)).discardItem(itemToTransfer);
                        System.out.println("swapped H TO P");
                        ui.getSelectedPlayer().collect(itemToTransfer);
                    }
                }
            
                // System.out.println("userInv:");
                // for (SuperItem item : ui.getSelectedPlayer().getInventory()){
                    
                //     System.out.println(item.getName() + " : " + item.getAmount());
                //     if (item instanceof KeyItem){
                //         System.out.println(((KeyItem) item).getLockName());
                //     }
                // }

                // System.out.println("hermesInv:");
                // for (SuperItem item : ((Hermes) ui.getMapHandler().getNPC(Hermes.name)).getInventory()){
                    
                //     System.out.println(item.getName() + " : " + item.getAmount());
                // }
            }
        }
    }

    /**
        Method reset the values of mousePressed and mouseOver
    **/
    @Override
    public void resetBools(){
        mousePressed = false;
        mouseOver = false;
    }
}