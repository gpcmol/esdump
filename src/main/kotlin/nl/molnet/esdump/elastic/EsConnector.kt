package nl.molnet.esdump.elastic

import nl.molnet.esdump.EsDumpConfig
import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient

object EsConnector {

  val client: RestHighLevelClient by lazy {
    init()
  }

  fun init(): RestHighLevelClient {
    return RestHighLevelClient(RestClient.builder(HttpHost(EsDumpConfig.host, EsDumpConfig.port, EsDumpConfig.schema)))
  }

  fun close() {
    if (client != null) {
      client.close()
    }
  }

}
