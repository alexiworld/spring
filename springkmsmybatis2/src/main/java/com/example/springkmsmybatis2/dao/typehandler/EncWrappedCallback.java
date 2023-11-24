package com.example.springkmsmybatis2.dao.typehandler;

import com.example.springkmsmybatis2.ApplicationContextProvider;
import com.example.springkmsmybatis2.utils.DataEncryptDecrypt;
import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class EncWrappedCallback implements TypeHandlerCallback {

    // Callbacks unfortunately are not instantiated as beans by MyBatis 2, and these fields are null.
    // This is the reason for using DataEncryptDecrypt.getInstance() to pull the bean from the context.
    @Autowired
    private DataEncryptDecrypt encryptDecrypt;

    @Override
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        try {
            setter.setString(parameter != null ? "enc(" + DataEncryptDecrypt.getInstance().encrypt(parameter.toString()) + ")": null);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Object getResult(ResultGetter getter) throws SQLException {
        try {
            String value = getter.getString();
            if (value!= null && value.startsWith("enc(") && value.endsWith(")")) {
                String pattern = "(enc\\()(.*)(\\))";
                String encrypted = value.replaceAll(pattern, "$2"); // value.substring(4, source.length-5)
                String decrypted = DataEncryptDecrypt.getInstance().decrypt(encrypted);
                return decrypted;
            }
            return value;
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Object valueOf(String s) {
        return s;
    }
}
