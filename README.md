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
   --index, -i "documents"      Index to dump, mandatory
   --slices, -s "2"             Number of slices, same as number of shards, default 2
   --file, -f                   Target file to dump the data into
   --outputfields, -o           Fields to be outputted
   --query, -q                  Custom query, defaults to match all, omit query, e.g. --query='{\"match_all\":{}}'
   --window, -w "1000"          Batch size, default 1000
   --ttl, t                     Time to live for scroll, default 1 minute
   --targethost                 Elastic search target host, default localhost
   --targetport                 Elastic search target port, default 9200
   --targetindex                Target index, optional
   --targettype                 Target type (deprecated, optional), default _doc
```

Dumping two indexes to a directory:
```
./esdump --url=localhost --port=9200 --index=companydatabase --slices=5 --file=/tmp/dump2.json --query='{\"match_all\":{}}' --outputfields=Salary,Gender --window=10000 --ttl=1 --targethost=127.0.0.1 --targetindex=dumpindex
```
