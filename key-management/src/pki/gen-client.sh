CLIENT=$1

mkdir /opt/my-vpn/easy-rsa/easyrsa3/pki
cp -r /etc/ovpn/pki/* /opt/my-vpn/easy-rsa/easyrsa3/pki/

cd /opt/my-vpn/easy-rsa/easyrsa3

./easyrsa gen-req $CLIENT nopass
./easyrsa sign-req client $CLIENT

cd /etc/ovpn/pki

cp /opt/my-vpn/easy-rsa/easyrsa3/pki/private/$CLIENT.key private/
cp /opt/my-vpn/easy-rsa/easyrsa3/pki/issued/$CLIENT.crt issued/

