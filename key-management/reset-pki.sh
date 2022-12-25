ABSOLUTE_FILENAME=`readlink -f "$0"`
DIR=`dirname "$ABSOLUTE_FILENAME"`

rm -r $DIR/clients/*
rm -r $DIR/pki/*
