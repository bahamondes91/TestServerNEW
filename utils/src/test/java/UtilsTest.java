import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilsTest {

    @Test
    void simpleGetReturnUrl(){
String url = Utils.parseUrl("""
    GET / HTTP/1.1\r\n \
    Host: www.example.com\r\n \
       """ );
    assertThat(url).isEqualTo("/");
    }

    @Test
    void filePathGetReturnsUrl(){
        String url = Utils.parseUrl("""
                 GET /index.html HTTP/1.1\r\n \
                 Host: www.example.com\r\n \
                 \r\n \
                 """);
        assertThat(url).isEqualTo("/index.html");
    }

}
