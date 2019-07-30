package nl.molnet.esdump

import org.elasticsearch.common.unit.TimeValue

object EsDumpConfig {

  var schema = "http"
  var host = "localhost"
  var port: Int = 9200
  var index = ""
  var slices: Int = 2
  var query = "{\"match_all\":{}}"
  var file = "/tmp/out.json"
  var scrollSize: Int = 10000
  var scrollTtlMin = TimeValue.timeValueMinutes(1L)
  var fields: Array<String> = emptyArray()

  fun init(host: String?, port: String?, index: String?, slices: String?, file: String?, query: String?, scrollSize: String?, scrollTtlMin: String?, fields: String?) {
    if (!host.isNullOrBlank()) {
      this.host = host
    }

    if (!port.isNullOrBlank()) {
      this.port = port.toInt()
    }

    if (!index.isNullOrBlank()) {
      this.index = index
    }

    if (!slices.isNullOrBlank()) {
      this.slices = slices.toInt()
    }

    if (!query.isNullOrBlank()) {
      this.query = query
    }

    if (!file.isNullOrBlank()) {
      this.file = file
    }

    if (!scrollSize.isNullOrBlank()) {
      this.scrollSize = scrollSize.toInt()
    }

    if (!scrollTtlMin.isNullOrBlank()) {
      this.scrollTtlMin = TimeValue.timeValueMinutes(scrollTtlMin.toLong())
    }

    if (!fields.isNullOrBlank()) {
      this.fields = fields.split(",").toTypedArray()
    } else {
      this.fields = Array(1) { "*" }
    }
  }

}
