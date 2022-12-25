output=$1
ta=$2
ca=$3
key=$4
cert=$5
dh=$6

ABSOLUTE_FILENAME=`readlink -f "$0"`
DIR=`dirname "$ABSOLUTE_FILENAME"`

cat $DIR/../config/server.template > $output
echo "log /var/log/ovpn-server.log" >> $output
echo "key-direction 0" >> $output

echo "<tls-auth>" >> $output
cat $ta >> $output
echo "</tls-auth>" >> $output

echo "<ca>" >> $output
cat $ca >> $output
echo "</ca>" >> $output

echo "<key>" >> $output
cat $key >> $output
echo "</key>" >> $output

echo "<cert>" >> $output
cat $cert >> $output
echo "</cert>" >> $output

echo "<dh>" >> $output
cat $dh >> $output
echo "</dh>" >> $output


