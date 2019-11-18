package nl.molnet.esdump.elastic

import nl.molnet.esdump.EsDumpConfig
import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient

object EsConnector {

  val client: RestHighLevelClient by lazy {
    init(EsDumpConfig.host, EsDumpConfig.port)
  }

  val targetClient: RestHighLevelClient by lazy {
    init(EsDumpConfig.targetHost, EsDumpConfig.targetPort)
  }

  fun init(host: String = "localhost", port:Int = 9200): RestHighLevelClient {
    return RestHighLevelClient(RestClient.builder(HttpHost(host, port, EsDumpConfig.schema)))
  }

  fun close() {
    if (client != null) {
      client.close()
    }
    if (targetClient != null) {
      targetClient.close()
    }
  }

}
