package com.mixi.user.designpattern.chain.ext;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mixi.common.constant.enums.AppHttpCodeEnum;
import com.mixi.common.exception.ServeException;
import com.mixi.user.designpattern.chain.ApproveChain;
import com.mixi.user.handler.SystemException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

import static com.mixi.user.constants.CommonConstant.NOT_FIND_OBJECT;

/**
 * @NAME: MysqlQueryExistCheck
 * @USER: yuech
 * @Description:数据库中某字段的值是否存在
 * @DATE: 2024/7/9
 */
@Scope("prototype")
@Component("MysqlQueryExistCheck")
@RequiredArgsConstructor
@Slf4j
public class MysqlQueryExistCheck<ENTITY,MAPPER extends BaseMapper<ENTITY>>  extends ApproveChain {

    protected MAPPER mapper;
    @Resource
    public void setMapper(MAPPER mapper){
        this.mapper = mapper;
    }

    @Override
    public Object process() {
        ENTITY user = mapper.selectOne(new QueryWrapper<ENTITY>().eq(getParams()[0], getParams()[1]));
        if (Objects.isNull(user)){
            log.info("无匹配对象");
            throw new SystemException(NOT_FIND_OBJECT);
        }
        return user;
    }

    @Override
    public void setNAME() {
        setNAME("MysqlQueryExistCheck");
    }
}