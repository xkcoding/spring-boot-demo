package com.example.springboot.easyexcel.util;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class GenderConverter implements Converter<Integer> {

    private static final String MAN = "男";
    private static final String WOMAN = "女";


    @Override
    public Class<?> supportJavaTypeKey() {
        // 实体类中对象属性类型
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        // Excel中对应的CellData属性类型
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty,
                                     GlobalConfiguration globalConfiguration) {
        // 从Cell中读取数据
        String gender = cellData.getStringValue();
        // 判断Excel中的值，将其转换为预期的数值
        if (MAN.equals(gender)) {
            return 0;
        } else if (WOMAN.equals(gender)) {
            return 1;
        }
        return null;
    }

    @Override
    public CellData<?> convertToExcelData(Integer integer, ExcelContentProperty excelContentProperty,
                                          GlobalConfiguration globalConfiguration) {
        // 判断实体类中获取的值，转换为Excel预期的值，并封装为CellData对象
        if (integer == null) {
            return new CellData<>("");
        } else if (integer == 0) {
            return new CellData<>(MAN);
        } else if (integer == 1) {
            return new CellData<>(WOMAN);
        }
        return new CellData<>("");
    }
}
