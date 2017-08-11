package hacking.main.programs.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.net.URL;

import javax.swing.*;

import hacking.main.GUIGame;

public class DesktopButton extends JLabel{

    private static int numOfButtons = 0;
    // TODO: add moving to new column when reaching the bottom of desktop

    private GUIProgram program;

    public DesktopButton(GUIProgram p, String name, ImageIcon icon){
	super(name);
	this.program = p;

	setHorizontalAlignment(SwingConstants.CENTER);
	setVerticalAlignment(SwingConstants.BOTTOM);
	setHorizontalTextPosition(SwingConstants.CENTER);
	setVerticalTextPosition(SwingConstants.BOTTOM);

	int offset = 32;
	int size = 72;
	int y = size * numOfButtons + offset;
	numOfButtons++;
	setLocation(offset, y);
	setSize(size, size);

	this.setFont(new Font("Default", Font.PLAIN, 10));

//	URL path = GUIGame.class.getResource("/" + name.toLowerCase() + ".png");
	setIcon(icon);

	// addListeners
	addMouseListener(new ClickHandler());
	addMouseMotionListener(new DragHandler());
    }

    private class ClickHandler extends MouseAdapter{
	@Override
	public void mouseClicked(MouseEvent e){
	    if(e.getClickCount() > 1){
		program.open();
	    }
	}
    }

    private class DragHandler extends MouseMotionAdapter{
	@Override
	public void mouseDragged(MouseEvent e){
	    int x = getX() + e.getX();
	    int y = getY() + e.getY();
	    setLocation(x - getWidth() / 2, y - getHeight() / 2);
	}
    }

    @Override
    public void paintComponent(Graphics g){
	super.paintComponent(g);
	Dimension d = getSize();
	Insets ins = getInsets();
	int x = ins.left;
	int y = ins.top;
	int w = d.width - ins.left - ins.right;
	int h = d.height - ins.top - ins.bottom;

	Graphics2D g2 = (Graphics2D)g;
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

	FontRenderContext frc = g2.getFontRenderContext();
	TextLayout tl = new TextLayout(getText(), getFont(), frc);

	AffineTransform shear = AffineTransform.getShearInstance(0.0, 0.0);
	Shape src = tl.getOutline(shear);
	Rectangle rText = src.getBounds();

	float xText = x - rText.x;
	switch(getHorizontalAlignment()){
	    case CENTER:
		xText = x + (w - rText.width) / 2;
	    break;
	    case RIGHT:
		xText = x + (w - rText.width);
	    break;
	}
	float yText = y + (h - rText.height) + tl.getAscent()/2;

	AffineTransform shift = AffineTransform.getTranslateInstance(xText, yText);
	Shape shp = shift.createTransformedShape(src);

	g2.setColor(Color.BLACK);
	g2.setStroke(new BasicStroke(0.75f));
	g2.draw(shp);

	g2.setColor(Color.WHITE);
	g2.fill(shp);
	
	//reset color
	g2.setColor(Color.BLACK);
    }
}
