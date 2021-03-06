package com.elsealabs.xshot.component;

import com.elsealabs.xshot.math.PointRectangle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ComponentSelection
{
	private boolean   decorated;
	private Rectangle decorShape;
	private Color     decorForegroundColor;
	private Color     decorBackgroundColor;

	private Stroke strokeForeground;
	private Color  strokeForegroundColor;
	private Stroke strokeBackground;
	private Color  strokeBackgroundColor;

	private Stroke resetStroke;

	public static ComponentSelection DEFAULT_MODERN = new ComponentSelection(
			new Rectangle(7, 7),      // Decoration Shape
			Color.WHITE,              // Decoration Foreground Color
			Color.BLACK,              // Decoration Background Color
			generateStroke(1.0f, 4f), // Foreground Stroke
			Color.BLACK,              // Foreground Stroke Color
			generateStroke(1.0f),     // Background Stroke
			Color.WHITE               // Background Stroke Color
	);
	public static ComponentSelection DEFAULT_SNIPPINGTOOL;
	public static ComponentSelection DEFAULT_SMOOTH;

	/**
	 * Create a Component Selection object with the properties described as arguments.
	 *
	 * @param decorShape The shape of the selection decorations
	 * @param decorForeground The foreground/border color of the selection decorations
	 * @param decorBackground The background color of the selection decorations
	 * @param strokeForeground The front-most stroke
	 * @param strokeForegroundColor The front-most stroke's color
	 * @param strokeBackground The back-most stroke
	 * @param strokeBackgorundColor The back-most stroke's color
	 */
	public ComponentSelection(
			Rectangle decorShape,
			Color decorForeground,
			Color decorBackground,
			Stroke strokeForeground,
			Color strokeForegroundColor,
			Stroke strokeBackground,
			Color strokeBackgorundColor
	) {

		this.decorated       = true;
		this.decorShape      = decorShape;
		this.decorBackgroundColor = decorBackground;
		this.decorForegroundColor = decorForeground;

		this.strokeForeground      = strokeForeground;
		this.strokeForegroundColor = strokeForegroundColor;
		this.strokeBackground      = strokeBackground;
		this.strokeBackgroundColor = strokeBackgorundColor;

		this.resetStroke = generateStroke(1f);
	}

	public static Stroke generateStroke(float width, float dashWidth)
	{
		return new BasicStroke(
			width,
			BasicStroke.CAP_BUTT,
			BasicStroke.JOIN_MITER,
			10.0f,
			new float[] { dashWidth },
			0.0f
		);
	}

	public static Stroke generateStroke(float width)
	{
		return new BasicStroke(width);
	}

	public void paint(Graphics2D g, PointRectangle rec, BufferedImage img)
	{
		// Draw image
		g.drawImage(img.getSubimage(rec.x, rec.y, rec.width, rec.height), rec.x, rec.y, null);

		// Draw background stroke
		g.setStroke(strokeBackground);
		g.setColor(strokeBackgroundColor);
		g.draw(rec);

		// Draw foreground stroke
		g.setStroke(strokeForeground);
		g.setColor(strokeForegroundColor);
		g.draw(rec);

		// Draw decorations
		if (decorated)
		{
			g.setStroke(resetStroke);

			rec.getPointsAsArray().stream()
			.forEach(a ->
			{
				g.setColor(decorBackgroundColor);
				g.fillRect(a.x - (decorShape.width / 2), a.y - (decorShape.height / 2), decorShape.width, decorShape.height);

				g.setColor(decorForegroundColor);
				g.drawRect(a.x - (decorShape.width / 2), a.y - (decorShape.height / 2), decorShape.width, decorShape.height);
			});
		}
	}

}