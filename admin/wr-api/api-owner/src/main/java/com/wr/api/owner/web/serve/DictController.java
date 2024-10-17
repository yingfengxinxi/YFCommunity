package com.wr.api.owner.web.serve;

import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.redis.service.RedisService;
import com.wr.common.security.utils.DictUtils;
import com.wr.remote.domain.SysDictData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/23 14:51
 */
@RestController
@RequestMapping("v2/dict")
public class DictController {

    @Autowired
    private RedisService redisService;

    /**
     *
     * @param dictType
     * @return
     */
    @GetMapping("/dictType/{dictType}")
    public AjaxResult dictType(@PathVariable("dictType") String dictType) {
        List<SysDictData> dictCache = DictUtils.getDictCache(dictType);
        return AjaxResult.success(dictCache);
    }
}
