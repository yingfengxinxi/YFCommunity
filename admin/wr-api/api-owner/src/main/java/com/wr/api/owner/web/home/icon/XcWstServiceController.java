package com.wr.api.owner.web.home.icon;

import com.wr.api.owner.entity.home.icon.WstIconHome;
import com.wr.api.owner.entity.home.icon.XcIconType;
import com.wr.api.owner.entity.home.icon.XcWstService;
import com.wr.api.owner.entity.home.icon.XcWstServiceType;
import com.wr.api.owner.service.home.icon.IXcIconTypeService;
import com.wr.api.owner.service.home.icon.IXcWstServiceService;
import com.wr.common.core.domain.R;
import com.wr.common.core.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * icon服务Controller
 *
 * @author ruoyi
 * @date 2023-03-07
 */
@Api(tags = "服务接口")
@RestController
@RequestMapping("/wstService")
public class XcWstServiceController extends BaseController {
    @Autowired
    private IXcIconTypeService iconTypeService;

    @Autowired
    private IXcWstServiceService wstServiceService;

    /**
     * 查询icon服务列表
     */
//    @ApiOperation("icon服务接口(requestType:serviceNameSearch是服务名称搜索,name,pageNum,pageSize为0是查询全部；serviceHotSearch是热门搜索；serviceMoreSearch是更多搜索)")
//    @PostMapping(value = "/list")
//    public R<List<WstService>> list(@RequestBody WstService wstService) {
//
//        String requestType = wstService.getRequestType();
//        WstService queryWstService = new WstService();
//        List<SearchHit<WstService>> wstServiceList = null;
//        switch (requestType) {
//            case "serviceNameSearch":
//                queryWstService.setName(wstService.getName());
//                queryWstService.setPageSize(wstService.getPageSize());
//                if (wstService.getPageSize() == 0) {
//                    wstService.setPageSize(1000);
//                }
//
//                queryWstService.setPageNum(wstService.getPageNum());
//                wstServiceList = this.serviceNameSearch(wstService.getName(), wstService.getPageNum(), wstService.getPageSize());
//
//                break;
//            case "serviceHotSearch":
//                wstServiceList = this.serviceHotSearch();
//                break;
//            case "serviceMoreSearch":
//                wstServiceList = this.serviceMoreSearch();
//        }
//        return R.ok((List<WstService>) wstServiceList.stream().map(one -> {
//            WstService wstService1 = one.getContent();
//            String highlightField = one.getHighlightField("name").stream().collect(Collectors.joining());
//            if (StringUtils.isNotEmpty(highlightField)) {
//                wstService1.setName(highlightField);
//            }
//            return wstService1;
//        }).collect(Collectors.toList()));
//    }
//
//    private List<SearchHit<WstService>> serviceNameSearch(String key, Integer pageNum, Integer pageSize) {
//        List<SortBuilder<?>> orderSet = new ArrayList<SortBuilder<?>>();
//        orderSet.add(SortBuilders.fieldSort("classifyOrder").order(SortOrder.ASC));
//        orderSet.add(SortBuilders.fieldSort("sort").order(SortOrder.ASC));
//        IndicesOptions indicesOptions = new IndicesOptions(EnumSet.of(IndicesOptions.Option.IGNORE_UNAVAILABLE, IndicesOptions.Option.ALLOW_NO_INDICES), EnumSet.of(IndicesOptions.WildcardStates.OPEN));
//
//
//        QueryStringQueryBuilder queryBuilders = null;
//        if (StringUtils.isNotEmpty(key)) {
//            queryBuilders = QueryBuilders.queryStringQuery(key).defaultField("name");
//        }
//
//        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//                .withIndicesOptions(indicesOptions)
//                .withIndicesBoost(new IndexBoost(BaseCommonESIndexNameConstant.wstService, 1))
//                //查询条件
//                .withQuery(queryBuilders)
//                //分页
//                .withPageable(PageRequest.of(pageNum - 1, pageSize))
//                //排序
//                .withSorts(orderSet)
//                //高亮字段显示
//                .withHighlightFields(new HighlightBuilder.Field("name").preTags("<span color=\"red\">").postTags("</span>"))
//                .build();
//        Mono<SearchPage<WstService>> mono = reactiveElasticsearchOperations.searchForPage(nativeSearchQuery, WstService.class);
//        return mono.block().getSearchHits().getSearchHits();
//    }
//
//    private List<SearchHit<WstService>> serviceHotSearch() {
//        List<SortBuilder<?>> orderSet = new ArrayList<SortBuilder<?>>();
//        orderSet.add(SortBuilders.fieldSort("classifyOrder").order(SortOrder.ASC));
//        orderSet.add(SortBuilders.fieldSort("sort").order(SortOrder.ASC));
//        IndicesOptions indicesOptions = new IndicesOptions(EnumSet.of(IndicesOptions.Option.IGNORE_UNAVAILABLE, IndicesOptions.Option.ALLOW_NO_INDICES), EnumSet.of(IndicesOptions.WildcardStates.OPEN));
//
//        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//                .withIndicesOptions(indicesOptions)
//                .withIndicesBoost(new IndexBoost(BaseCommonESIndexNameConstant.wstService, 1))
//                //查询条件
//                .withQuery(QueryBuilders.termQuery("isHot", 1))
//                //排序
//                .withSorts(orderSet)
//                //高亮字段显示
//                .withHighlightFields(new HighlightBuilder.Field("name").preTags("<span color=\"red\">").postTags("</span>"))
//                .build();
//        Mono<SearchPage<WstService>> mono = reactiveElasticsearchOperations.searchForPage(nativeSearchQuery, WstService.class);
//        return mono.block().getSearchHits().getSearchHits();
//    }
//
//    private List<SearchHit<WstService>> serviceMoreSearch() {
//        List<SortBuilder<?>> orderSet = new ArrayList<SortBuilder<?>>();
//        orderSet.add(SortBuilders.fieldSort("classifyOrder").order(SortOrder.ASC));
//        orderSet.add(SortBuilders.fieldSort("sort").order(SortOrder.ASC));
//        IndicesOptions indicesOptions = new IndicesOptions(EnumSet.of(IndicesOptions.Option.IGNORE_UNAVAILABLE, IndicesOptions.Option.ALLOW_NO_INDICES), EnumSet.of(IndicesOptions.WildcardStates.OPEN));
//
//        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//                .withIndicesOptions(indicesOptions)
//                .withIndicesBoost(new IndexBoost(BaseCommonESIndexNameConstant.wstService, 1))
//                //排序
//                .withSorts(orderSet)
//                //高亮字段显示
//                .withHighlightFields(new HighlightBuilder.Field("name").preTags("<span color=\"red\">").postTags("</span>"))
//                .build();
//        Mono<SearchPage<WstService>> mono = reactiveElasticsearchOperations.searchForPage(nativeSearchQuery, WstService.class);
//        return mono.block().getSearchHits().getSearchHits();
//    }


    @ApiOperation("首页使用 热门应用及热门服务")
    @GetMapping("/icon/home")
    public R<WstIconHome> iconHome() {

        // 查询热门服务
        XcIconType iconType = new XcIconType();
        iconType.setIsHot(1);
        iconType.setStatus(1L);
        List<XcIconType> iconTypeList = iconTypeService.selectXcIconTypeList(iconType);

        XcWstService service = new XcWstService();
        service.setStatus(1L);
        service.setIsHot(1L);
        List<XcWstService> serviceList = wstServiceService.selectXcWstServiceList(service);

        return R.ok(new WstIconHome(iconTypeList, serviceList));
    }


    @ApiOperation(("更多服务"))
    @GetMapping("/icon/more")
    public R<List<XcWstServiceType>> iconMore() {

        List<XcWstServiceType> list = wstServiceService.selectWstServiceListByGroup();

        return R.ok(list);

    }

}
