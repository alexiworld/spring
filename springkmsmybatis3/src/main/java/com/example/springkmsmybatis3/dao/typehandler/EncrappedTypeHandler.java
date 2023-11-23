package com.example.springkmsmybatis3.dao.typehandler;

import com.example.springkmsmybatis3.model.EncrappedString;
import com.example.springkmsmybatis3.utils.DataEncryptDecrypt;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@MappedJdbcTypes(JdbcType.VARCHAR)
public class EncrappedTypeHandler extends BaseTypeHandler<EncrappedString> {

    @Autowired
    private DataEncryptDecrypt encryptDecrypt;

    @SneakyThrows
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    EncrappedString parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter != null ? "enc(" + encryptDecrypt.encrypt(parameter.toString()) + ")": null);
    }

    private String decryptIfEncrypted(String value) throws GeneralSecurityException {
        if (value!= null && value.startsWith("enc(") && value.endsWith(")")) {
            String pattern = "(enc\\()(.*)(\\))";
            String encrypted = value.replaceAll(pattern, "$2"); // value.substring(4, source.length-5)
            String decrypted = encryptDecrypt.decrypt(encrypted);
            return decrypted;
        }
        return value;
    }

    @Override
    public EncrappedString getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            String value = rs.getString(columnName);
            return EncrappedString.to(decryptIfEncrypted(value));
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public EncrappedString getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            String value = rs.getString(columnIndex);
            return EncrappedString.to(decryptIfEncrypted(value));
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public EncrappedString getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            String value = cs.getString(columnIndex);
            return EncrappedString.to(decryptIfEncrypted(value));
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

}