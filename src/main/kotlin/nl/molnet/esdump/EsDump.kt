package nl.molnet.esdump

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import nl.molnet.esdump.elastic.EsConnector
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.status.StatusLogger
import org.tinylog.kotlin.Logger
import kotlin.system.exitProcess

fun main(args: Array<String>) = mainBody {

  StatusLogger.getLogger().level = Level.OFF

  ArgParser(args).parseInto(::Arguments).run {
    EsDumpConfig.init(host, port, index, slices, file, query, window, ttlMin, fields, targetHost, targetPort, targetIndex, targetType)

    LogConfig.init()

    val logger = Logger.tag("CONSOLE")
    logger.info("Host: ${host}")
    logger.info("Port: ${port}")
    logger.info("Index: ${index}")
    logger.info("Slices: ${slices}")
    logger.info("File: ${file}")
    logger.info("Query: ${query}")
    logger.info("Fields: ${fields}")
    logger.info("Window: ${window}")
    logger.info("TtlMin: ${ttlMin}")
    logger.info("TargetHost: ${targetHost}")
    logger.info("TargetPort: ${targetPort}")
    logger.info("TargetIndex: ${targetIndex}")
    logger.info("TargetType: ${targetType}")

    try {
      Dumper.pullData()
      logger.info("Wait for last flush...")
      Thread.sleep((EsDumpConfig.bulk_flush_sec + 5) * 1000)
    } catch (e: Exception) {
      logger.error("error connecting to Elasticsearch " + e.message)
      exitProcess(0)
    } finally {
      EsConnector.close()
      logger.info("Done")
    }

    return@run
  }

}
