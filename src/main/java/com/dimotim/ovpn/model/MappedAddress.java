package com.dimotim.ovpn.model;

import com.formkiq.graalvm.annotations.Reflectable;
import lombok.Value;

@Value
@Reflectable
public class MappedAddress {
    String ip;
    int port;
}
