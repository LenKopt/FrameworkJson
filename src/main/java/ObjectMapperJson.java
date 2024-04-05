import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ObjectMapperJson {

    public List<Object> readValue(String src, Class tClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ParseException, IOException {

        JSONArray o = (JSONArray) new JSONParser().parse(new FileReader("src/main/resources/jokes.txt"));
        List<Object> arrayObjects = new ArrayList<>();

        for (int i = 0; i < o.size(); i++) {

            JSONObject nextObject = (JSONObject) o.get(i);
            Constructor constructor = tClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object obj = constructor.newInstance();

            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field nextField : declaredFields) {
                nextField.setAccessible(true);
                if (nextField.getType().isPrimitive()) {

                    Object n = nextObject.get(nextField.getName());
                    nextField.set(obj, nextObject.get(nextField.getName()));

                } else if (nextField.getType().equals(UUID.class)) {
                    nextField.set(obj, UUID.fromString(nextObject.get(nextField.getName()).toString()));
                }
            }
            arrayObjects.add(obj);
        }

        return arrayObjects;
    }
}
