package com.mixi.user.chain;

import com.mixi.common.exception.ServeException;
import com.mixi.user.domain.RedisGateway;
import io.github.servicechain.annotation.Chain;
import io.github.servicechain.chain.AbstractFilterChain;

import javax.annotation.Resource;
import java.util.List;

import static com.mixi.user.constants.ChainConstant.PIC_CODE_VERIFY;
import static com.mixi.user.constants.RedisKeyConstant.PIC_CODE_KEY;
import static com.mixi.user.constants.ServeCodeConstant.PIC_CODE_VERIFY_ERROR;

@Chain(PIC_CODE_VERIFY)
public class PicCodeVerifyChain extends AbstractFilterChain<String[]> {

    @Resource
    private RedisGateway redisGateway;
    @Override
    public List<ServicePoint> servicePoints() {
        return null;
    }

    @Override
    public boolean filter(String[] value) {
        if(value.length==2){
            String cachePicCode = redisGateway.get(PIC_CODE_KEY, value[0]);
            if(!value[1].equals(cachePicCode)){
                throw ServeException.of(PIC_CODE_VERIFY_ERROR);
            }
            return true;
        }
        throw ServeException.of(PIC_CODE_VERIFY_ERROR);
    }
}
