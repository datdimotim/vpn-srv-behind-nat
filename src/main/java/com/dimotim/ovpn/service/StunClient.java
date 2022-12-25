package com.dimotim.ovpn.service;

import com.dimotim.ovpn.model.MappedAddress;
import com.dimotim.ovpn.config.StunConfig;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.ice4j.Transport;
import org.ice4j.TransportAddress;
import org.ice4j.socket.IceUdpSocketWrapper;
import org.ice4j.stunclient.SimpleAddressDetector;

import java.net.DatagramSocket;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StunClient {
    StunConfig config;

    public MappedAddress resolveMappedAddress(int localPort) throws Exception {
        SimpleAddressDetector detector = new SimpleAddressDetector(new TransportAddress(config.getStunHost(), config.getStunPort(), Transport.UDP));
        IceUdpSocketWrapper socket = null;
        try {
            socket = new IceUdpSocketWrapper(new DatagramSocket(localPort));
            detector.start();
            TransportAddress address = detector.getMappingFor(socket);
            return new MappedAddress(address.getAddress().getHostAddress(), address.getPort());
        } finally {
            if (socket != null) {
                socket.close();
            }
            detector.shutDown();
        }
    }
}
