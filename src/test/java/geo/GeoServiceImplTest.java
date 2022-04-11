package geo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import ru.netology.entity.*;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class GeoServiceImplTest {

    @BeforeAll
    public static void initSuite() {
        System.out.println("Running GeoServiceImplTest");
    }

    @AfterAll
    public static void completeSuite() {
        System.out.println("GeoServiceImplTest complete");
    }

    @BeforeEach
    public void initTest() {
        System.out.println("Test start");
    }

    @AfterEach
    public void finalizeTest() {
        System.out.println("Test complete");
    }

    GeoServiceImpl sut = new GeoServiceImpl();

    @ParameterizedTest
    @MethodSource("sourceGeo")
    public void locationByIpTest(String ip, Location original){
        //act
        Location result = sut.byIp(ip);

        //arrange
        assertEquals(original.getCountry(), result.getCountry());
        assertEquals(original.getBuiling(), result.getBuiling());
        assertEquals(original.getCity(), result.getCity());
        assertEquals(original.getBuiling(), result.getBuiling());
    }

    private static Stream<Arguments> sourceGeo() {
        return Stream.of(
                Arguments.of("127.0.0.1",new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11",new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149",new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.",new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.",new Location("New York", Country.USA, null,  0)),
                Arguments.of("172.0.0.2",new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.00.00.01",new Location("New York", Country.USA, null,  0))
        );
    }

    @Test
    public void locationByIpNullTest(){
        //act
        Location result = sut.byIp("122.");

        //arrange
        assertNull(result);
    }

}
