package com.github.wolfgang.operation.core.mybatis;

import com.github.wolfgang.operation.core.dal.enumeration.OperationTypeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(value = {OperationTypeEnum.class})
public class EnumToTinyintHandler extends BaseTypeHandler<EnumTypeCode> {
    private final Class<EnumTypeCode> type;

    public EnumToTinyintHandler(Class<EnumTypeCode> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        } else {
            this.type = type;
        }
    }

    public void setNonNullParameter(PreparedStatement ps, int index, EnumTypeCode parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(index, parameter.getCode());
    }

    public EnumTypeCode getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return rs.wasNull() ? null : this.convertToEnum(value);
    }

    public EnumTypeCode getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        return rs.wasNull() ? null : this.convertToEnum(value);
    }

    public EnumTypeCode getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int value = cs.getInt(columnIndex);
        return cs.wasNull() ? null : this.convertToEnum(value);
    }

    private EnumTypeCode convertToEnum(int value) {
        EnumTypeCode[] typeCodes = (EnumTypeCode[]) this.type.getEnumConstants();
        EnumTypeCode[] var3 = typeCodes;
        int var4 = typeCodes.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            EnumTypeCode typeCode = var3[var5];
            if (typeCode.getCode() == value) {
                return typeCode;
            }
        }

        return null;
    }
}
