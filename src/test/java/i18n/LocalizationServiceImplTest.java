package i18n;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.*;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

import static ru.netology.entity.Country.*;


public class LocalizationServiceImplTest {

    LocalizationServiceImpl sut = new LocalizationServiceImpl();

    @BeforeAll
    public static void initSuite() {
        System.out.println("Running LocalizationServiceImplTest");
    }

    @AfterAll
    public static void completeSuite() {
        System.out.println("LocalizationServiceImplTest complete");
    }

    @BeforeEach
    public void initTest() {
        System.out.println("Test start");
    }

    @AfterEach
    public void finalizeTest() {
        System.out.println("Test complete");
    }

    @ParameterizedTest
    @MethodSource("source")
    public void localeTest(Country country, String expected) {
        //act
        String result = sut.locale(country);

        //arrange
        Assertions.assertEquals(result, expected);
    }

    private static Stream<Arguments> source() {
        return Stream.of(
                Arguments.of(RUSSIA, "Добро пожаловать"),
                Arguments.of(GERMANY, "Welcome"),
                Arguments.of(USA, "Welcome"),
                Arguments.of(BRAZIL, "Welcome")
        );
    }

    @Test
    public void testCatchNullPointerException() throws NullPointerException {
        Assertions.assertThrows(NullPointerException.class, () -> {
            sut.locale(null);
        });
    }
}
