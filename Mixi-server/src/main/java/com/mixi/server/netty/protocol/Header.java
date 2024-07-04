package com.mixi.server.netty.protocol;

import com.mixi.server.common.compress.VarInt;

/**
 * @Description
 * @Author welsir
 * @Date 2024/6/25 14:46
 */
public class Header {
    private int type;

    private byte[] data;

    public Header(int type, byte[] data) {
        this.type = type;
        this.data = data;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int calculateTotalLength() {
        int dataLength = calculateDataLength();
        return 1+ VarInt.computeVarInt32Size(dataLength)+dataLength;
    }

    public int calculateDataLength(){
        return data==null?0:data.length;
    }
}
