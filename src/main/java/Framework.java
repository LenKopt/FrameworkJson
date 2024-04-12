import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

public class Framework {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ParseException, IOException {

        new ObjectMapperJson().readValue("src/main/resources/1.json", Joke.class);
        new ObjectMapperJson().readValue("src/main/resources/jokes.txt", Joke.class);

    }
}
