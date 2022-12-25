CLIENT=$1

ABSOLUTE_FILENAME=`readlink -f "$0"`
DIR=`dirname "$ABSOLUTE_FILENAME"`
USER=`whoami`

docker run --rm -v $DIR/pki:/etc/ovpn/pki -it $(docker build -q $DIR/src/pki/) /bin/bash -c "/opt/my-vpn/gen-client.sh $CLIENT"

sudo chown -R $USER $DIR/pki

$DIR/src/mk-opvpn.sh $DIR/clients/$CLIENT.ovpn $DIR/pki/ta.key $DIR/pki/ca.crt $DIR/pki/private/$CLIENT.key $DIR/pki/issued/$CLIENT.crt $CLIENT
