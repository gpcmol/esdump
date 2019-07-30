package nl.molnet.esdump

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.status.StatusLogger
import org.tinylog.kotlin.Logger

fun main(args: Array<String>) = mainBody {

  StatusLogger.getLogger().level = Level.OFF

  ArgParser(args).parseInto(::Arguments).run {
    LogConfig.init()

    var index1 = "companydatabase"
    var slices1 = "10"
    var fields = "*"

    EsDumpConfig.init(host, port, index1, slices1, file, query, window, ttlMin, fields)

    Logger.tag("CONSOLE").info("Host, ${host}!")
    Logger.info("Port, ${port}!")
    Logger.info("Index, ${index}!")
    Logger.info("Slices, ${slices}!")
    Logger.info("File, ${file}!")
    Logger.info("Query, ${query}!")
    Logger.info("Window, ${window}!")
    Logger.info("TtlMin, ${ttlMin}!")

    Dumper.pullData()

    return@run
  }

  println("here")
}
