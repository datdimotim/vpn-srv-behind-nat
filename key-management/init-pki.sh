ABSOLUTE_FILENAME=`readlink -f "$0"`
DIR=`dirname "$ABSOLUTE_FILENAME"`
USER=`whoami`

mkdir -p pki
mkdir -p clients

docker run --rm -v $DIR/pki:/etc/ovpn/pki -it $(docker build -q $DIR/src/pki/) /bin/bash -c /opt/my-vpn/gen-certs.sh

sudo chown -R $USER $DIR/pki


CLIENT="client0"
$DIR/src/mk-opvpn.sh $DIR/clients/$CLIENT.ovpn $DIR/pki/ta.key $DIR/pki/ca.crt $DIR/pki/private/$CLIENT.key $DIR/pki/issued/$CLIENT.crt $CLIENT

CLIENT="server"
$DIR/src/mk-opvpn-srv.sh $DIR/clients/$CLIENT.ovpn $DIR/pki/ta.key $DIR/pki/ca.crt $DIR/pki/private/$CLIENT.key $DIR/pki/issued/$CLIENT.crt $DIR/pki/dh.pem
