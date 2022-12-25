output=$1
ta=$2
ca=$3
key=$4
cert=$5
name=$6

ABSOLUTE_FILENAME=`readlink -f "$0"`
DIR=`dirname "$ABSOLUTE_FILENAME"`

cat $DIR/../config/client.template > $output
echo "log /var/log/ovpn-$name.log" >> $output
echo "key-direction 1" >> $output

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


#echo "remote $host $port" >> $output

