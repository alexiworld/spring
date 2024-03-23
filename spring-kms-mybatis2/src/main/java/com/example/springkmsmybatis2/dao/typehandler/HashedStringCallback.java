package com.example.springkmsmybatis2.dao.typehandler;

import com.example.springkmsmybatis2.ApplicationContextProvider;
import com.example.springkmsmybatis2.model.HashedString;
import com.example.springkmsmybatis2.utils.DataEncryptDecrypt;
import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class HashedStringCallback implements TypeHandlerCallback {

    // Callbacks unfortunately are not instantiated as beans by MyBatis 2, and these fields are null.
    // This is the reason for using DataEncryptDecrypt.getInstance() to pull the bean from the context.
    @Autowired
    private DataEncryptDecrypt encryptDecrypt;

    @Override
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        try {
            setter.setString(parameter != null ? DataEncryptDecrypt.getInstance().hash(parameter.toString()) : null);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Object getResult(ResultGetter getter) throws SQLException {
        try {
            return HashedString.to(getter.getString());
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Object valueOf(String s) {
        return s;
    }
}

