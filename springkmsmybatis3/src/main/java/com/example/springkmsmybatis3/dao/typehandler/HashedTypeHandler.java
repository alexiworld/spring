package com.example.springkmsmybatis3.dao.typehandler;

import com.example.springkmsmybatis3.model.HashedString;
import com.example.springkmsmybatis3.utils.DataEncryptDecrypt;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@MappedJdbcTypes(JdbcType.VARCHAR)
public class HashedTypeHandler extends BaseTypeHandler<HashedString> {

    @Autowired
    private DataEncryptDecrypt encryptDecrypt;

    @SneakyThrows
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, HashedString parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter != null ? encryptDecrypt.hash(parameter.toString()) : null);
    }

    @Override
    public HashedString getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            String hashed = rs.getString(columnName);
            return HashedString.to(hashed);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public HashedString getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            String hashed = rs.getString(columnIndex);
            return HashedString.to(hashed);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public HashedString getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            String hashed = cs.getString(columnIndex);
            return HashedString.to(hashed);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }
}