package com.pai.base.core.doc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.itextpdf.text.Annotation;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pai.base.core.doc.bean.ConfigBean;
import com.pai.base.core.doc.bean.DocBean;
import com.pai.base.core.doc.bean.ParamBean;

/**
 * pdf生成工具
 * 
 */
public class ITextPDFUtil {

	/**
	 * 创建PDF文件
	 * 
	 */
	public static void createPDF(List<DocBean> beanList, ConfigBean configBean) {
		System.out.println(">>>>生成pdf文档...");
		
		String version = "v" + XmlUtil.getVersion();

		String project = XmlUtil.getProject();

		long start = System.currentTimeMillis();

		String docName = "服务端接口文档-" + project;

		String filePath = configBean.getFilePath();
		Document document = null;
		PdfWriter writer = null;
		try {
			document = new Document();
			writer = PdfWriter.getInstance(document, new FileOutputStream(new File(filePath)));

			// 摘要 在open方法前
			document.addTitle(docName);
			document.addSubject(docName);
			document.addAuthor("ddjf");
			document.addCreator("autodoc");
			document.addCreationDate();
			document.addHeader(version, docName);

			document.open();

			writer.setViewerPreferences(PdfWriter.PageModeUseOutlines);// 显示大纲

			Map<String, List<DocBean>> catalogMap = buildCatalog(beanList, configBean);

			Map<String, List<DocBean>> chapterMap = new HashMap<String, List<DocBean>>(50);
			for (Map.Entry<String, List<DocBean>> entry : catalogMap.entrySet()) {
				String key = entry.getKey();
				List<DocBean> valueList = entry.getValue();
				if (!valueList.isEmpty()) {
					chapterMap.put(key, valueList);
				}
			}

			BaseFont baseFontChinese = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);

			float fontSize = 15f;
			Font chapterFont = new Font(baseFontChinese, fontSize, Font.ITALIC);// 章节字体
			Font sectionFont = new Font(baseFontChinese, fontSize - fontSize * 0.25f, Font.ITALIC);// 子区域字体
			Font contentFont = new Font(baseFontChinese, fontSize - fontSize * 0.35f, Font.NORMAL);
			float leading = fontSize;// 行间距

			int i = 1;
			for (Map.Entry<String, List<DocBean>> entry : chapterMap.entrySet()) {
				String key = entry.getKey();
				List<DocBean> valueList = entry.getValue();
				Collections.sort(valueList, new Comparator<DocBean>(){
					@Override
					public int compare(DocBean arg0, DocBean arg1) {
						return arg0.getModelName().compareTo(arg1.getModelName());
					}
				});
				Collections.sort(valueList, new Comparator<DocBean>(){
					@Override
					public int compare(DocBean arg0, DocBean arg1) {
						return String.valueOf(arg0.getSort()).compareTo(String.valueOf(arg1.getSort()));
					}
				});
				Paragraph chapterTitle = new Paragraph(key, chapterFont);
				Chapter chapter = new Chapter(chapterTitle, i++);

				for (DocBean value : valueList) {
					Paragraph sectionTitle = new Paragraph(leading * 2, value.getActionName(), sectionFont);

					if (StringUtils.isNotBlank(value.getUpdateVersion())
							&& value.getUpdateVersion().equals(configBean.getCurrentVersion().getDes())) {
						Chunk versionChunk = new Chunk("(" + value.getUpdateVersion() + ")", sectionFont);
						sectionTitle.add(versionChunk);

						versionChunk = new Chunk("【修改】",
								new Font(baseFontChinese, fontSize - fontSize * 0.45f, Font.BOLD, BaseColor.BLUE));
						versionChunk.setTextRise(-1.5f);
						sectionTitle.add(versionChunk);
					}

					if (value.getVersion().equals(configBean.getCurrentVersion().getDes())) {
						Chunk versionChunk = new Chunk("(" + value.getVersion() + ")", sectionFont);
						sectionTitle.add(versionChunk);

						versionChunk = new Chunk("【新增】",
								new Font(baseFontChinese, fontSize - fontSize * 0.45f, Font.BOLD, BaseColor.RED));
						versionChunk.setTextRise(-1.5f);
						sectionTitle.add(versionChunk);
					}

					// 标题
					Section section = chapter.addSection(sectionTitle);
					// 描述
					Paragraph desc = new Paragraph(leading, value.getActionDesc(),
							new Font(baseFontChinese, contentFont.getSize(), Font.ITALIC, BaseColor.GRAY));
					section.add(desc);

					// 备注
					if (value.getModifyRecodes() != null && value.getModifyRecodes()[0] != null
							&& value.getModifyRecodes()[0].length() > 0) {
						Annotation note = new Annotation(value.getActionName(), value.showModifyRecods());
						section.add(note);
					}

					// 表格
					PdfPTable table = tableRender(contentFont, leading, value, section, configBean);

					section.add(table);
				}

				document.add(chapter);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != document)
				document.close();
			if (null != writer)
				writer.close();
		}

		long end = System.currentTimeMillis();

		System.out.println(">>>>生成文档完毕，花费时间：" + (end - start) + "ms");
		System.out.println(">>>>文档位置：" + filePath);
	}
	
	private static Map<String, List<DocBean>> buildCatalog(List<DocBean> beanList, ConfigBean configBean) {
		// 构建章节(list -> map)
		Map<String, List<DocBean>> tempMap = new HashMap<String, List<DocBean>>(50);
		for (DocBean bean : beanList) {
			List<DocBean> list = tempMap.get(bean.getModelName().trim());
			if (list == null) {
				list = new ArrayList<DocBean>();
				tempMap.put(bean.getModelName().trim(), list);
			}

			if (!configBean.isShowAllVersion()) {
				if (!bean.getVersion().equals(configBean.getCurrentVersion().getDes())) {
					if (StringUtils.isNotBlank(bean.getUpdateVersion())) {
						if (!bean.getUpdateVersion().equals(configBean.getCurrentVersion().getDes())) {
							continue;
						}
					} else {
						continue;
					}
				}
			}

			if (!configBean.isShowDeprecated()) {
				if (bean.isDeprecated()) {
					continue;
				}
				if (StringUtils.isNotBlank(bean.getStopVersion())) {
					if (!bean.getStopVersion().equals(configBean.getCurrentVersion().getDes())) {
						continue;
					}
				}
			}
			list.add(bean);
		}
		return tempMap;
	}

	/**
	 * table渲染样式
	 */
	private static PdfPTable tableRender(Font contentFont, float leading, DocBean docBean, Section session,
			ConfigBean configBean) throws DocumentException {

		PdfPTable table = new PdfPTable(2);

		initTable(table, leading);

		if (docBean.getAuthor() != null) {
			appendRows(table, "作者：", docBean.getAuthor(), leading, contentFont);
		}

		if (StringUtils.isNotBlank(docBean.getCreateTime())) {
			appendRows(table, "创建时间：", docBean.getCreateTime(), leading, contentFont);
		}

		if (StringUtils.isNotBlank(docBean.getVersion())) {
			appendRows(table, "创建版本：", docBean.getVersion(), leading, contentFont);
		}

		if (StringUtils.isNotBlank(docBean.getUpdateVersion())) {
			appendRows(table, "修改版本：", docBean.getUpdateVersion(), leading, contentFont);
		}

		if (StringUtils.isNotBlank(docBean.getUpdateTime())) {
			appendRows(table, "修改时间：", docBean.getUpdateTime(), leading, contentFont);
		}

		if (StringUtils.isNotBlank(docBean.getUpdateBy())) {
			appendRows(table, "修改人：", docBean.getUpdateBy(), leading, contentFont);
		}

		appendRows(table, "接口状态：", docBean.isDeprecated() ? "已停用" : "使用中", leading, contentFont);

		// 接口停用版本
		if (StringUtils.isNotBlank(docBean.getStopVersion())) {
			appendRows(table, "停用版本：", docBean.getStopVersion(), leading, contentFont);
		}

		// 请求地址
		if (StringUtils.isNotBlank(docBean.getActionUrl())) {
			appendRows(table, "请求地址：", docBean.getActionUrl(), leading, contentFont);
		}

		if (StringUtils.isNotBlank(docBean.getReqMethod())) {
			appendRows(table, "请求方式：", docBean.getReqMethod(), leading, contentFont);
		}

		if (StringUtils.isNotBlank(docBean.getReqType())) {
			appendRows(table, "请求类型：", docBean.getReqType(), leading, contentFont);
		}

		String reqParams = docBean.showParam();
		ParamBean request = docBean.getRequest();

		// 请求参数
		if (StringUtils.isNotBlank(reqParams) || request != null) {
			String content = "";
			if (request != null) {
				content = request.toString() + "\r\n";
			}
			appendRows(table, "请求参数：", content + reqParams, leading, contentFont);
		}

		// 响应参数
		if (docBean.getResponse() != null) {
			appendRows(table, "响应参数：", docBean.getResponse().toString(), leading, contentFont);
		}
		// 备注
		if (docBean.getModifyRecodes() != null && StringUtils.isNotBlank(docBean.getModifyRecodes()[0])) {
			appendRows(table, "备注：", docBean.showModifyRecods(), leading, contentFont);
		}

		return table;
	}

	private static void initTable(PdfPTable table, float leading) throws DocumentException {
		table.setWidthPercentage(100f); // 占全屏
		float[] f = { 0.15f, 0.85f };
		table.setWidths(f); // 列比例
		table.setSpacingBefore(leading - leading * 0.4f);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		table.getDefaultCell().setLeading(leading - leading * 0.13f, 0);
		table.setSplitLate(false);
		table.setSplitRows(true);
	}

	private static void appendRows(PdfPTable table, String name, String content, float leading, Font font) {
		PdfPCell leftCell = new PdfPCell(new Paragraph(name, font));
		leftCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		leftCell.setMinimumHeight(leading + 0.2f * leading);
		PdfPCell rightCell = new PdfPCell(new Paragraph(content, font));
		table.addCell(leftCell);
		table.addCell(rightCell);
	}

}
