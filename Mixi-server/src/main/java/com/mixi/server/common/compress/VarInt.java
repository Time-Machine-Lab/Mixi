package com.mixi.server.common.compress;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.CorruptedFrameException;

/**
 * @Description
 * @Author welsir
 * @Date 2024/6/26 14:32
 */
public class VarInt {

    public static int readVarInt32(ByteBuf buffer) {
        if(!buffer.isReadable()){
            buffer.resetReaderIndex();
            return 0;
        }
        buffer.markReaderIndex();
        byte cur = buffer.readByte();
        int res;
        if(cur>=0){
            return cur;
        }else{
            res = cur & 127;
            if(!buffer.isReadable()){
                buffer.resetReaderIndex();
                return 0;
            }
            if((cur=buffer.readByte())>=0){
                res|=cur<<7;
            }else{
                res|=(cur&127)<<7;
                if(!buffer.isReadable()){
                    buffer.resetReaderIndex();
                    return 0;
                }
                if((cur=buffer.readByte())>=0){
                    res|=cur<<14;
                }else{
                    res|=(cur&127)<<14;
                    if(!buffer.isReadable()){
                        buffer.resetReaderIndex();
                        return 0;
                    }
                    if((cur=buffer.readByte())>=0){
                        res|=cur<<21;
                    }else{
                        res|=(cur&127)<<21;
                        if(!buffer.isReadable()){
                            buffer.resetReaderIndex();
                            return 0;
                        }
                        res |= (cur = buffer.readByte()) << 28;
                        if (cur < 0) {
                            throw new CorruptedFrameException("malformed varint.");
                        }
                    }
                }
            }
        }
        return res;
    }

    public static void writeVarInt32(ByteBuf out, int value) {
        while (true) {
            if ((value & ~0x7F) == 0) {
                out.writeByte(value);
                return;
            } else {
                out.writeByte((value & 0x7F) | 0x80);
                value >>>= 7;
            }
        }
    }

    public static int computeVarInt32Size(final int value) {
        if ((value & (0xffffffff << 7)) == 0) {
            return 1;
        }
        if ((value & (0xffffffff << 14)) == 0) {
            return 2;
        }
        if ((value & (0xffffffff << 21)) == 0) {
            return 3;
        }
        if ((value & (0xffffffff << 28)) == 0) {
            return 4;
        }
        return 5;
    }

}
