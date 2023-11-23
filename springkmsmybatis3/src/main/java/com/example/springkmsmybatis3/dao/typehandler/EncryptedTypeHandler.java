package com.example.springkmsmybatis3.dao.typehandler;

import com.example.springkmsmybatis3.model.EncryptedString;
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
public class EncryptedTypeHandler extends BaseTypeHandler<EncryptedString> {

    @Autowired
    private DataEncryptDecrypt encryptDecrypt;

    @SneakyThrows
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    EncryptedString parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter != null ? encryptDecrypt.encrypt(parameter.toString()) : null);
    }

    @Override
    public EncryptedString getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            String encrypted = rs.getString(columnName);
            return EncryptedString.to(encryptDecrypt.decrypt(encrypted));
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public EncryptedString getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            String encrypted = rs.getString(columnIndex);
            return EncryptedString.to(encryptDecrypt.decrypt(encrypted));
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public EncryptedString getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            String encrypted = cs.getString(columnIndex);
            return EncryptedString.to(encryptDecrypt.decrypt(encrypted));
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }
}