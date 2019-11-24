package pacman;

import org.junit.Assert;
import org.junit.Test;

public class JdkTest {

    @Test
    public void version() {
        // If you fail this test then you will probably have major issues
        // during semester. Please see a tutor to fix this.
        String version = System.getProperty("java.version");
        Assert.assertEquals("11", version.split("\\.")[0]);
    }
}
