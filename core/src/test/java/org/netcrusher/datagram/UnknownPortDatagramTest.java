package org.netcrusher.datagram;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.netcrusher.core.nio.NioUtils;
import org.netcrusher.core.reactor.NioReactor;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UnknownPortDatagramTest {

    private static final int PORT_CRUSHER = 10283;

    private static final int PORT_CONNECT = 10284;

    private static final String HOSTNAME = "127.0.0.1";

    private NioReactor reactor;

    private DatagramCrusher crusher;

    @Before
    public void setUp() throws Exception {
        reactor = new NioReactor();

        crusher = DatagramCrusherBuilder.builder()
            .withReactor(reactor)
            .withBindAddress(HOSTNAME, PORT_CRUSHER)
            .withConnectAddress(HOSTNAME, PORT_CONNECT)
            .buildAndOpen();
    }

    @After
    public void tearDown() throws Exception {
        if (crusher != null) {
            crusher.close();
        }

        if (reactor != null) {
            reactor.close();
        }
    }

    @Test
    public void test() throws Exception {
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(true);
        channel.connect(new InetSocketAddress(HOSTNAME, PORT_CRUSHER));

        try {
            ByteBuffer bb = ByteBuffer.allocate(1024);
            bb.limit(800);
            bb.position(0);

            channel.write(bb);

            Thread.sleep(1001);
        } finally {
            NioUtils.close(channel);
        }
    }
}
