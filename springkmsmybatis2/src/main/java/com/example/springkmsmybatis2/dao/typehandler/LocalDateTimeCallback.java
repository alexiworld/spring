package com.example.springkmsmybatis2.dao.typehandler;

import com.example.springkmsmybatis2.model.HashedString;
import com.example.springkmsmybatis2.utils.DataEncryptDecrypt;
import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateTimeCallback implements TypeHandlerCallback {

    @Override
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        try {
            setter.setTimestamp(Timestamp.valueOf((LocalDateTime) parameter));
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Object getResult(ResultGetter getter) throws SQLException {
        try {
            //String trim = getter.getString().substring(0, 23);
            //DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            //return LocalDateTime.parse(trim, df);
            return getter.getTimestamp().toLocalDateTime();
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Object valueOf(String s) {
        return s;
    }
}

