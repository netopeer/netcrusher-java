package org.netcrusher.core.filter;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class LoggingFilterTest {

    @Test
    public void test() throws Exception {
        LoggingFilter filter = new LoggingFilter("dump.test", LoggingFilterLevel.DEBUG);

        ByteBuffer bb = ByteBuffer.allocate(1024);
        bb.put(new byte[] { (byte) 1, (byte) 2, (byte) 44, (byte) 0xFF });
        bb.flip();

        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8080);

        filter.transform(address, bb);
    }
}