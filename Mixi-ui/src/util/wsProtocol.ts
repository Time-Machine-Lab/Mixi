/*
 * @Author: Dhx
 * @Date: 2024-07-28 18:47:52
 * @Description: 
 * @FilePath: \Mixi\Mixi-ui\src\util\wsProtocol.ts
 */
import Bytes from '@/util/byteUtil'
const VERSION_1 = 0x01
type SocketHeader = {
    type:number;
    data:string;
}
class SocketProtocol {
    private version:number
    private isHeartBeat:boolean = false
    private command:number
    private headers:SocketHeader[] = []
    private body:string = ''
    constructor(version:number,isHeartBeat:boolean,command:number,headers:SocketHeader[],body:string){
        this.version = version
        this.isHeartBeat = isHeartBeat
        this.command = command
        this.body = body
        this.headers = headers
    }
    decodeMessage(bytes:Bytes) {
        return this.decode(this.version,bytes)
    }
    encodeMessage() {
        return this.encode(this.version)
    }
    private encode(version:number){
        switch(version){
            case VERSION_1:{
                return this.encode_v1()
                break;
            }
            default: {
                console.error('version')
            }
        }
    }
    private decode(version:number,bytes:Bytes){
        switch(version){
            case VERSION_1:{
                return this.decode_v1(bytes)
                break;
            }
            default: {
                console.error('version')
            }
        }
    }
    private decode_v1(bytes:Bytes){
        console.log(bytes.buffer())
        this.version = bytes.readNumber(1)
        this.isHeartBeat = bytes.readBoolean()
        this.command = bytes.readNumber(1)
        let length = bytes.readVarInt()
        const headerCount = bytes.readNumber(1)
        for(let i=0;i<headerCount;i++){
            const headerDataLength = bytes.readVarInt()
            const headerType = bytes.readNumber(1)
            if(bytes.length()<headerDataLength){
                console.error('error')
                return null
            }
            let str = bytes.readString(headerDataLength)
            console.log(str)
            const header = JSON.parse(str)
            this.headers.push(header)
            length -=(bytes.computeVarInt32Size(headerDataLength)+headerDataLength+1)
        }
        if(length!=0){
            this.body = JSON.parse(bytes.readString(length))
        }
        return {
            headers:this.headers,
            body:this.body
        }
    }
    private encode_v1(){
        const headerBytes = new Bytes('')
        for(let i=0;i<this.headers.length;i++){
            headerBytes.writeVarInt(this.headers[i].data.length)
            headerBytes.write8bitsData(this.headers[i].type)
            headerBytes.writeString(this.headers[i].data)
        }
        const bodyBytes = new Bytes('')
        bodyBytes.writeString(this.body)
        const messageLen:number = headerBytes.length() + bodyBytes.length()
        const ret = new Bytes('');
        ret.write8bitsData(VERSION_1)
        ret.write8bitsData(this.isHeartBeat)
        ret.write8bitsData(this.command)
        ret.writeVarInt(messageLen)
        ret.write8bitsData(this.headers.length)
        ret.writeBytes(headerBytes)
        ret.writeBytes(bodyBytes)
        return ret.buffer()
    }
}
export { SocketProtocol };
export type { SocketHeader };
