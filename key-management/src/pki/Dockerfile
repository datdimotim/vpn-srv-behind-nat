FROM ubuntu:20.04

WORKDIR /opt/my-vpn

RUN apt-get update
RUN apt-get install -y openvpn git
RUN git clone https://github.com/OpenVPN/easy-rsa.git

COPY . /opt/my-vpn

RUN chmod +x *.sh

