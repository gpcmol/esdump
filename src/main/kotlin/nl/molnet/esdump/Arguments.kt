package nl.molnet.esdump

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default

class Arguments(parser: ArgParser) {

  val verbose by parser.flagging(
    "-v", "--verbose",
    help = "enable verbose mode")

  val host by parser.storing(
    "-u", "--url",
    help = "elasticsearch host e.g. localhost").default("localhost")

  val port by parser.storing(
    "-p", "--port",
    help = "elasticsearch port e.g. 9200").default("9200")

  val index by parser.storing(
    "-i", "--index",
    help = "elasticsearch index")

  val slices by parser.storing(
    "-s", "--slices",
    help = "slices").default("2")

  val file by parser.storing(
    "-f", "--file",
    help = "file").default("")

  val query by parser.storing(
    "-q", "--query",
    help = "query").default("")

  val fields by parser.storing(
    "-o", "--outputfields",
    help = "query").default("*")

  val window by parser.storing(
    "-w", "--window",
    help = "scroll window, default 1000").default("1000")

  val ttlMin by parser.storing(
    "-t", "--ttl",
    help = "ttl in minutes, default 1 min.").default("1")

  val targetHost by parser.storing(
    "--targethost",
    help = "elasticsearch targetHost e.g. localhost").default("localhost")

  val targetPort by parser.storing(
    "--targetport",
    help = "elasticsearch targetPort e.g. 9200").default("9200")

  val targetIndex by parser.storing(
    "--targetindex",
    help = "targetindex").default("")

  val targetType by parser.storing(
    "--targettype",
    help = "targettype").default("_doc")
}
