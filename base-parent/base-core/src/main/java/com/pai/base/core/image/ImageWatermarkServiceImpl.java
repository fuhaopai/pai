package com.pai.base.core.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pai.base.api.image.ImageWatermarkService;
import com.pai.base.core.util.string.StringUtils;

public class ImageWatermarkServiceImpl implements ImageWatermarkService {

	private float alpha = 0.3f;
	private String fontName = "Lucida Bright";
	private int fontSize = 36;
	private int fontStyle = Font.ITALIC;
	protected final transient Log logger = LogFactory.getLog(getClass());
	private String watermarkImagePath = null;

	private String watermarkText = "Watermark Demo";

	public void addWatermark(String srcImagePath, String destImagePath)
			throws IOException {
		BufferedImage image = null;
		if (watermarkImagePath != null && watermarkImagePath.length() > 2) {
			image = addImageWatermark(srcImagePath);
		} else if (watermarkText != null && watermarkText.length() > 2) {
			image = addTextWatermark(srcImagePath);
		}
		if (image != null) {
			logger.debug("Saving watermarked image file: " + srcImagePath);
			String srcImageExt = StringUtils.getFileExt(srcImagePath);
			File output = new File(destImagePath == null ? srcImagePath : destImagePath);
			ImageIO.write(image, srcImageExt, output);
		}
	}

	public BufferedImage addImageWatermark(String imagePath) throws IOException {
		return addImageWatermark(ImageIO.read(new File(imagePath)));
	}

	public BufferedImage addTextWatermark(String imagePath) throws IOException {
		return addTextWatermark(ImageIO.read(new File(imagePath)));
	}
	
	protected BufferedImage addImageWatermark(BufferedImage srcImage)
			throws IOException {
		String wpath = this.getClass().getResource("/").getPath() + watermarkImagePath;
		BufferedImage watermarkImage = ImageIO
				.read(new File(wpath));
		Graphics2D g = srcImage.createGraphics();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));
		g.drawImage(watermarkImage,
				(srcImage.getWidth() - watermarkImage.getWidth()) / 2,
				(srcImage.getHeight() - watermarkImage.getHeight()) / 2, null);
		g.dispose();
		return srcImage;
	}	
	
	protected BufferedImage addTextWatermark(BufferedImage srcImage)
			throws IOException {
		Graphics2D g = srcImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font(fontName, fontStyle, fontSize));
		g.rotate(-Math.PI / 4, srcImage.getWidth() / 2,
				srcImage.getHeight() / 2);
		TextLayout tl = new TextLayout(watermarkText, g.getFont(),
				g.getFontRenderContext());
		Rectangle2D bounds = tl.getBounds();
		double x = (srcImage.getWidth() - bounds.getWidth()) / 2
				- bounds.getX();
		double y = (srcImage.getHeight() - bounds.getHeight()) / 2
				- bounds.getY();
		Shape outline = tl.getOutline(AffineTransform.getTranslateInstance(
				x + 2, y + 1));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));
		g.setPaint(Color.WHITE);
		g.draw(outline);
		g.setPaint(new GradientPaint(0, 0, Color.WHITE, 30, 20, new Color(128,
				128, 255), true));
		tl.draw(g, (float) x, (float) y);
		g.dispose();
		return srcImage;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
	}

	public void setWatermarkImagePath(String watermarkImagePath) {
		this.watermarkImagePath = watermarkImagePath;
	}

	public void setWatermarkText(String watermarkText) {
		this.watermarkText = watermarkText;
	}



}
