import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

public class Framework {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ParseException, IOException {
        //File file = Paths.get("jokes.txt").toFile();
        new ObjectMapperJson().readValue("jokes.txt", Joke.class);

    }
}
