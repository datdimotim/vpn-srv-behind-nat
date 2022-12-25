cd /opt/my-vpn/easy-rsa/easyrsa3
./easyrsa init-pki
./easyrsa build-ca nopass
./easyrsa gen-crl
./easyrsa gen-req server nopass
./easyrsa sign-req server server
./easyrsa gen-dh
openvpn --genkey --secret ta.key

./easyrsa gen-req client0 nopass
./easyrsa sign-req client client0


cd /etc/ovpn/pki

cp /opt/my-vpn/easy-rsa/easyrsa3/ta.key .
cp -R /opt/my-vpn/easy-rsa/easyrsa3/pki/* .

#cp /opt/my-vpn/easy-rsa/easyrsa3/pki/ca.crt .
#cp /opt/my-vpn/easy-rsa/easyrsa3/pki/dh.pem .
#cp /opt/my-vpn/easy-rsa/easyrsa3/ta.key .
#cp /opt/my-vpn/easy-rsa/easyrsa3/pki/private/server.key .
#cp /opt/my-vpn/easy-rsa/easyrsa3/pki/issued/server.crt .

#cp /opt/my-vpn/easy-rsa/easyrsa3/pki/private/client0.key .
#cp /opt/my-vpn/easy-rsa/easyrsa3/pki/issued/client0.crt .

