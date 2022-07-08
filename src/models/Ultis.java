package models;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Ultis {
    static public <T> List<T> mappingModelFromResultSet(ResultSet resultSet, Class<T> model) {
        List<T> results = new ArrayList<>();
        try {
            Field[] fields = model.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            int idx = 1;

            while (resultSet.next()) {
                T newObj = model.getDeclaredConstructor().newInstance();
                for (Field field : fields) {
                    if (field.getName().equals("serialVersionUID"))
                        continue;
                    field.set(newObj, resultSet.getObject(idx++));
                }
                results.add(newObj);
                idx = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}
