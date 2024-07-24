package com.example.springboot.easyexcel.util;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
public class CommonCellStyleStrategy {

    /**
     * 设置单元格样式(仅用于示例)
     *
     * @return 样式策略
     */
    public static HorizontalCellStyleStrategy getHorizontalCellStyleStrategy() {
        // 表头策略
        WriteCellStyle headerCellStyle = new WriteCellStyle();
        // 表头水平对齐居中
        headerCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 背景色
        headerCellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        WriteFont headerFont = new WriteFont();
        headerFont.setFontHeightInPoints((short) 15);
        headerCellStyle.setWriteFont(headerFont);
        // 自动换行
        headerCellStyle.setWrapped(Boolean.FALSE);

        // 内容策略
        WriteCellStyle contentCellStyle = new WriteCellStyle();
        // 设置数据允许的数据格式,这里49代表所有可以都允许设置
        contentCellStyle.setDataFormat((short) 49);
        // 设置背景色: 需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        contentCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        contentCellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        // 设置内容靠左对齐
        contentCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        // 设置字体
        WriteFont contentFont = new WriteFont();
        contentFont.setFontHeightInPoints((short) 12);
        contentCellStyle.setWriteFont(contentFont);
        // 设置自动换行
        contentCellStyle.setWrapped(Boolean.FALSE);
        // 设置边框样式和颜色
        contentCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        contentCellStyle.setBorderTop(BorderStyle.MEDIUM);
        contentCellStyle.setBorderRight(BorderStyle.MEDIUM);
        contentCellStyle.setBorderBottom(BorderStyle.MEDIUM);
        contentCellStyle.setTopBorderColor(IndexedColors.RED.getIndex());
        contentCellStyle.setBottomBorderColor(IndexedColors.GREEN.getIndex());
        contentCellStyle.setLeftBorderColor(IndexedColors.YELLOW.getIndex());
        contentCellStyle.setRightBorderColor(IndexedColors.ORANGE.getIndex());

        // 将格式加入单元格样式策略
        return new HorizontalCellStyleStrategy(headerCellStyle, contentCellStyle);
    }
}
