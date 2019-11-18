package nl.molnet.esdump

import me.tongfei.progressbar.ProgressBar
import nl.molnet.esdump.elastic.EsConnector
import nl.molnet.esdump.elastic.QueryHelper
import org.elasticsearch.action.ActionRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.action.search.SearchScrollRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.search.Scroll
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.search.slice.SliceBuilder
import org.tinylog.kotlin.Logger
import java.util.stream.IntStream

object Dumper {
  val logger = Logger.tag("FILE")

  fun pullData() {
    val totalCount = QueryHelper.countQuery(EsDumpConfig.index, EsDumpConfig.query)
    val progress = ProgressBar("Pull data", totalCount)

    val scrollIds: MutableSet<String> = mutableSetOf()

    val queryBuilder = QueryHelper.queryFromString(EsDumpConfig.query)

    IntStream.range(0, EsDumpConfig.slices).parallel().forEach {
      val searchSourceBuilder = SearchSourceBuilder()
        .size(EsDumpConfig.scrollSize)
        .trackTotalHits(true)
        .query(queryBuilder.query())
        .sort("_doc")

      if (EsDumpConfig.fields == null || EsDumpConfig.fields.isEmpty()) {
        searchSourceBuilder.fetchSource(false)
      } else {
        searchSourceBuilder.fetchSource(EsDumpConfig.fields, null)
      }

      val sliceBuilder = SliceBuilder(it, EsDumpConfig.slices)
      val scroll = Scroll(EsDumpConfig.scrollTtlMin)
      val searchRequest = SearchRequest(EsDumpConfig.index)
        .scroll(scroll)
        .source(searchSourceBuilder.slice(sliceBuilder))
        .preference("_local")

      var searchResponse = performSearchRequest(searchRequest, progress)
      var scrollId = searchResponse.scrollId
      scrollIds.add(scrollId)
      var searchHits: Array<SearchHit> = searchResponse.hits.hits

      while (searchHits != null && searchHits.isNotEmpty()) {
        val scrollRequest = SearchScrollRequest(scrollId)
        logger.info(it)
        scrollRequest.scroll(scroll)

        searchResponse = performSearchRequest(scrollRequest, progress)
        scrollId = searchResponse.scrollId
        scrollIds.add(scrollId)
        searchHits = searchResponse.hits.hits
      }
    }

    progress.close()

    QueryHelper.closeScroll(scrollIds)

    EsConnector.close()
  }

  fun performSearchRequest(searchRequest: ActionRequest, progress: ProgressBar): SearchResponse {
    var searchResponse = SearchResponse()

    when (searchRequest) {
      is SearchRequest -> {
        searchResponse = EsConnector.client.search(searchRequest, RequestOptions.DEFAULT)
      }
      is SearchScrollRequest -> {
        searchResponse = EsConnector.client.scroll(searchRequest, RequestOptions.DEFAULT)
      }
    }

    processHits(searchResponse.hits.hits, progress)

    return searchResponse
  }

  fun processHits(hits: Array<SearchHit>, progress: ProgressBar) {
    logger.info(hits.contentToString())
    progress.stepBy(hits.size.toLong())
  }

}
