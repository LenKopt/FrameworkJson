1. Implementacja interfejsu konsolowego. Wyświetlenie jednego losowego żartu z pamięci.
        2. Wyświetlenie jednego żartu z pamięci zgodnie z podaną przez użytkownika kategorją. Modyfikacja interfejsu.
        3. Dodanie możliwości pobierania jednego żartu z internetu. Modyfikacja interfejsu.

        1. Jako użytkownik chciałabym otrzymać taką aplikację, która umożliwiłaby otrzymywanie żarty
        4. Jako użytkownik chciałabym, aby była możliwość domonstracji wszystkich istniejących kategorji
        2. Jako użytkowniku, było by mi wygodnie otrzymać żart na określony temat
        3. Jako użytkownik chciałabym, żeby baza żartów się rozszeżała, bo podczas wielokrotnego korzystania z aplikacji żarty powtarzają się dość często

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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

    public List<Object> readValue(String src, Class ClassType) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ParseException, IOException {

        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = (JSONArray) new JSONParser().parse(new FileReader(src));
        } catch (ClassCastException e) {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader(src));
            jsonArray.add(jsonObject);
        }

        List<Object> arrayObjects = getListObjects(ClassType, jsonArray);

        return arrayObjects;
    }

    private static List<Object> getListObjects(Class ClassType, JSONArray jsonArray) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        List<Object> arrayObjects = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {

            JSONObject nextJSONObject = (JSONObject) jsonArray.get(i);
            Object newObject = createObjectFromJSONObject(ClassType, nextJSONObject);
            arrayObjects.add(newObject);
        }
        return arrayObjects;
    }

    private static Object createObjectFromJSONObject(Class ClassType, JSONObject jsonObject) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor constructor = ClassType.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object newObject = constructor.newInstance();

        setFields(jsonObject, newObject);
        return newObject;
    }

    private static void setFields(JSONObject jsonObject, Object newObject) throws IllegalAccessException {
        Field[] declaredFields = newObject.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);

            String nameField = field.getName();
            Class typeField = field.getType();

            if (typeField.isPrimitive()||typeField.equals(String.class)) {
                field.set(newObject, jsonObject.get(nameField));
            } else if (typeField.equals(UUID.class)) {
                field.set(newObject, UUID.fromString(jsonObject.get(nameField).toString()));
            }
        }
    }
}
