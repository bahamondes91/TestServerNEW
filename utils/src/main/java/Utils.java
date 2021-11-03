
public class Utils {

    public static String parseUrl(String input) {
        String[] result = input.split(" ");
        return result[1];
    }

    public static HTTPType parseHttpRequestType(String input) {
        if (input.startsWith("G"))
            return HTTPType.GET;
        else if (input.startsWith("H"))
            return HTTPType.HEAD;
        else if (input.startsWith("PO"))
            return HTTPType.POST;
        throw new RuntimeException("invalid type");
    }

    //  NEDAN ÄR HUVUD METODEN

    public Request parseHttpHeader(String input) {
        var request = new Request();
        request.type = parseHttpRequestType(input);
        request.url = parseUrl(input);
        return request;
    }

    public static Boolean handleRequest(Request request) {
        return switch (request.type) {
            case GET -> true;
            case HEAD -> false;
            case POST -> true;
        };
    }

    public String message() {
        return "Hello from utils!";
    }
}
