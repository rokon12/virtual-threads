package ca.bazlur;

import java.util.regex.Pattern;

public class Utils {

    private static final Pattern POOL_NAME_PATTERN = Pattern.compile("(?<=@)(\\w+)");
    private static final Pattern THREAD_NAME_PATTERN = Pattern.compile("worker-\\d+");

    static String readWorkerName() {
        var name = Thread.currentThread().toString();
        var matcher = THREAD_NAME_PATTERN.matcher(name);
        if (matcher.find()) {
            return matcher.group();
        }
        return "Not found";
    }

    static String readPoolName() {
        var name = Thread.currentThread().toString();
        var matcher = POOL_NAME_PATTERN.matcher(name);
        if (matcher.find()) {
            return matcher.group();
        }
        return "Not found";
    }
}
