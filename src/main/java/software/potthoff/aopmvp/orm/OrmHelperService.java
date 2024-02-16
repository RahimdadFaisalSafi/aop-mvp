package software.potthoff.aopmvp.orm;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;

@Service
public class OrmHelperService {
    HashMap<Class, HashMap<String, Field>> typeToFields;

    @PostConstruct
    public void init() {
        typeToFields = new HashMap<>();
        Class<?>[] classesToScan = {TestEntity.class};

        for (Class<?> clazz : classesToScan) {
            HashMap<String, Field> fieldsMap = new HashMap<>();

            // Retrieve all fields from the class, including inherited ones if necessary
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true); // Make the field accessible if it's private/protected
                fieldsMap.put(field.getName(), field);
            }

            // Only add to the main map if the class has fields
            if (!fieldsMap.isEmpty()) {
                typeToFields.put(clazz, fieldsMap);
            }
        }
    }

    // Method to get the field map for a class, for demonstration
    public HashMap<String, Field> getFieldsForClass(Class<?> clazz) {
        return typeToFields.get(clazz);
    }
}
