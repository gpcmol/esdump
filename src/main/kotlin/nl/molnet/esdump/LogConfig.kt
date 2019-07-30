package nl.molnet.esdump

import org.tinylog.configuration.Configuration

object LogConfig {

  fun init() {
    consoleLogConfig()
    fileLogConfig()
  }

  fun consoleLogConfig() {
    Configuration.set("writer1", "console")
    Configuration.set("writer1.tag", "CONSOLE")
    Configuration.set("writer1.level", "trace")
    Configuration.set("writer1.format", "{message}")
    Configuration.set("writer1.charset", "UTF-8")
  }

  fun fileLogConfig() {
    Configuration.set("writer", "shared file")
    Configuration.set("writer.tag", "FILE")
    Configuration.set("writer.file", EsDumpConfig.file)
    Configuration.set("writer.level", "trace")
    Configuration.set("writer.format", "{message}")
    Configuration.set("writer.charset", "UTF-8")
    Configuration.set("writer.append", "true")
  }

}
