package org.feather.rabbit.common.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.feather.rabbit.api.Message;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.feather.rabbit.common.utils.FastJsonConvertUtil;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.common.mybatis.handler
 * @className: MessageJsonHandler
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-03-13 15:20
 * @version: 1.0
 */

public class MessageJsonHandler  extends BaseTypeHandler<Message> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Message parameter,
                                    JdbcType jdbcType) throws SQLException {

        ps.setString(i, FastJsonConvertUtil.convertObjectToJSON(parameter));
    }

    @Override
    public Message getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        String value = rs.getString(columnName);
        if(null != value && !StringUtils.isBlank(value)) {
            return FastJsonConvertUtil.convertJSONToObject(rs.getString(columnName), Message.class);
        }
        return null;
    }

    @Override
    public Message getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        if(null != value && !StringUtils.isBlank(value)) {
            return FastJsonConvertUtil.convertJSONToObject(rs.getString(columnIndex), Message.class);
        }
        return null;
    }

    @Override
    public Message getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        if(null != value && !StringUtils.isBlank(value)) {
            return FastJsonConvertUtil.convertJSONToObject(cs.getString(columnIndex), Message.class);
        }
        return null;
    }

}
