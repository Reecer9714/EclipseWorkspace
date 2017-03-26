package jgfe.gui;

import java.awt.event.MouseEvent;

import jgfe.GameContainer;
import jgfe.Renderer;
import jgfe.gfx.ImageTile;
import jgfe.gfx.Pixel;
import jgfe.util.ShadowType;

public class Button extends AbstractGUI
{
	private int x, y, w, h;
	
	private String text;
	private ImageTile buttonImg;
	
	public Button(String text, ImageTile buttonImg, int x, int y, int w , int h)
	{
		this.text = text;
		this.name = text;
		this.buttonImg = buttonImg;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	@Override
	public void init(GameContainer gc)
	{

	}

	@Override
	public void update(GameContainer gc, float dt)
	{
		interacted = false;
		if(gc.getInput().isButtonPressed(MouseEvent.BUTTON1))
		{
			if(gc.getInput().getMouseX() > x && gc.getInput().getMouseX() < x + w)
			{
				if(gc.getInput().getMouseY() > y && gc.getInput().getMouseY() < y + h)
				{
					interacted = true;
				}
			}
		}
	}

	@Override
	public void render(GameContainer gc, Renderer r)
	{
		r.setTranslate(false);
		if(buttonImg == null)
		{
			r.drawFillRect(x, y, w, h, 0xff111111);
			r.drawRect(x, y, w, h, Pixel.GREEN, ShadowType.NONE);
			r.drawString(text, Pixel.GREEN, x + w / 2, y + h / 2, true, true);
		}
		r.setTranslate(true);
	}

}
