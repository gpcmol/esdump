# esdump in kotlin
Tool to dump data from elasticsearch.

```
$ ./esdump --help
NAME:
   esdump - dump elastic data to a file

USAGE:
   command esdump [command options] [arguments...]

OPTIONS:
   --url, -u "localhost"        Elastic search host, default localhost
   --port, -p "9200"            Elastic search port, default 9200
   --index, -i "documents"      Index to dump
   --slices, -s "2"             Number of slices, same as number of shards
   --file, -f                   Target file to dump the data into
   --query, -q                  Custom query, defaults to match all
   --window, -w "1000"          Batch size, default 1000
   --ttl, t                     Time to live for scroll, default 1 minute
```

Dumping two indexes to a directory:
```
./esdump --url localhost --port 9200 --index documents --slices 2 --file /home/ubuntu/dump.json --query '{"match_all":{}}' --window 1000 -ttl 1
```
