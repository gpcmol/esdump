package nl.molnet.esdump

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import nl.molnet.esdump.elastic.EsConnector
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.status.StatusLogger
import org.tinylog.kotlin.Logger

fun main(args: Array<String>) = mainBody {

  StatusLogger.getLogger().level = Level.OFF

  ArgParser(args).parseInto(::Arguments).run {
    EsDumpConfig.init(host, port, index, slices, file, query, window, ttlMin, fields)

    LogConfig.init()

    val logger = Logger.tag("CONSOLE")
    logger.info("Host: ${host}!")
    logger.info("Port: ${port}!")
    logger.info("Index: ${index}!")
    logger.info("Slices: ${slices}!")
    logger.info("File: ${file}!")
    logger.info("Query: ${query}!")
    logger.info("Fields: ${fields}!")
    logger.info("Window: ${window}!")
    logger.info("TtlMin: ${ttlMin}!")

    try {
      Dumper.pullData()
    } catch (e: Exception) {
      logger.error("error connecting to Elasticsearch " + e.stackTrace)
    } finally {
      EsConnector.close()
    }

    return@run
  }

}
