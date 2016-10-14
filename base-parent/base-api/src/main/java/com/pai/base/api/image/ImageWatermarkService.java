package com.pai.base.api.image;

import java.io.IOException;

public interface ImageWatermarkService {
	/**
	 * 对图片增加水印，支持图片或文字的，通过conf配置。采用Properties配置是为方便以后扩展以提供更多配置项，如文字的位置等。
	 * 
	 * @param srcImagePath
	 * @param destImagePath
	 * @throws IOException
	 */
	public void addWatermark(String srcImagePath, String destImagePath)
			throws IOException;
}


