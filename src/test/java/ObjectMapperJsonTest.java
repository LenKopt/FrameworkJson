import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import static org.assertj.core.api.Java6Assertions.assertThat;

class ObjectMapperJsonTest {

    @Test
    void schould_create_new_object_of_type_joke_successfully() throws ParseException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //when
        List<Object> newListOfJokes = new ObjectMapperJson().readValue("src/main/resources/1.json", Joke.class);
        //then
        assertThat(newListOfJokes.size()).isEqualTo(1);
        assertThat(newListOfJokes.get(0).getClass()).isEqualTo(Joke.class);
    }
}