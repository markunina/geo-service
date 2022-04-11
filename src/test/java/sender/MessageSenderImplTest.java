package sender;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderImplTest {

    @BeforeAll
    public static void initSuite() {
        System.out.println("Running MessageSenderImplTest");
    }

    @AfterAll
    public static void completeSuite() {
        System.out.println("MessageSenderImplTest complete");
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
    public void sendTest(String ip, Location location, String expected){
        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(ip))
                .thenReturn(location);

        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(location.getCountry()))
                .thenReturn(expected);

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService,localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);


        String result = messageSender.send(headers);

        Assertions.assertEquals(result,expected);
    }

    private static Stream<Arguments> source() {
        return Stream.of(
                Arguments.of("172.0.32.11",new Location("Moscow", Country.RUSSIA, "Lenina", 15), "Добро пожаловать"),
                Arguments.of("96.44.183.149",new Location("New York", Country.USA, " 10th Avenue", 32),"Welcome"),
                Arguments.of("172.",new Location("Moscow", Country.RUSSIA, null, 0),"Добро пожаловать"),
                Arguments.of("96.",new Location("New York", Country.USA, null,  0),"Welcome"),
                Arguments.of("172.0.0.2",new Location("Moscow", Country.RUSSIA, null, 0),"Добро пожаловать"),
                Arguments.of("96.00.00.01",new Location("New York", Country.USA, null,  0),"Welcome"),
                Arguments.of("127.0.0.1",new Location(null, null, null, 0),"Welcome")

        );
    }

    @Test
    public void testCatchNullPointerException() throws NullPointerException {

        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp("122."))
                .thenReturn(null);

        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(null))
                .thenReturn(null);

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService,localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "122.");

        Assertions.assertThrows(NullPointerException.class, () -> {
            messageSender.send(headers);
        });
    }

}
