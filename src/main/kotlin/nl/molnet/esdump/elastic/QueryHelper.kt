package nl.molnet.esdump.elastic

import org.elasticsearch.action.search.ClearScrollRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.core.CountRequest
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder

object QueryHelper {
  const val MATCH_ALL_QUERY = "{\"match_all\":{}}"

  fun countQuery(index: String, query: String?): Long {
    val query = query ?: MATCH_ALL_QUERY
    val queryBuilder = queryFromString(query)

    val countRequest = CountRequest()
      .indices(index)
      .source(queryBuilder)

    val countResponse = EsConnector.client.count(countRequest, RequestOptions.DEFAULT)
    return countResponse.count
  }

  fun queryFromString(query: String?): SearchSourceBuilder {
    val query = query ?: MATCH_ALL_QUERY
    return SearchSourceBuilder().query(
      QueryBuilders.wrapperQuery(query)
    )
  }

  fun closeScroll(scrollIds: Set<String>) {
    val clearScrollRequest = ClearScrollRequest()
    clearScrollRequest.scrollIds(scrollIds.toList())
    EsConnector.client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT)
    //Logger.debug("Cleared ${scrollIds.size} scrollIds")
  }

}
