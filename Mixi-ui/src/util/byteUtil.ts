import type { Type } from "typescript"

/*
 * @Author: Dhx
 * @Date: 2024-07-30 21:22:13
 * @Description: 
 * @FilePath: \Mixi\Mixi-ui\src\util\byteUtil.ts
 */
export default class Bytes {
    private data = ''
    constructor(str:string){
        this.data = str
    }
    write16bitsData(data: any) {
        this.writeData(data, 2)
    }
    write32bitsData(data: any) {
        this.writeData(data, 4)
    }
    write8bitsData(data: any) {
        this.writeData(data, 1)
    }
    private writeData(data: any, byteNums: number) {
        switch (typeof data) {
            case 'number': {
                this.writeNumber(data, byteNums)
                break;
            } case 'string': {
                this.writeStr(data, byteNums)
                break;
            } case 'boolean': {
                this.writeBoolean(data, byteNums)
                break;
            }
            default: {

                break;
            }
        }
    }
    private writeNumber(data: number, byteNums: number) {
        for (let i = byteNums - 1; i >= 0; i--) {
            this.write(String.fromCharCode(`0x${((data >> (8 * i)) & 0xff).toString(16)}` as unknown as number))
        }
    }
    private writeStr(data: string, byteNums: number) {
        this.write(byteNums == -1 ? data : data.slice(0, byteNums))
    }
    writeBytes(data: Bytes) {
        this.writeString(data.toString())
    }
    writeString(data: string) {
        this.writeStr(data, -1)
    }
    private writeBoolean(data: boolean, byteNums: number) {
        this.writeNumber(data ? 1 : 0, byteNums)
    }
    private write(data: any) {
        this.data += data
    }
    readNumber(byteNums:number){
        return this.readData(1,byteNums)
    }
    readString(byteNums:number){
        return this.readData('',byteNums)
    }
    readBoolean(){
        return this.readData(true,1)
    }
    private readData(type: any, byteNums: number) {
        switch (typeof type) {
            case 'number': {
                return this.read(byteNums).charCodeAt()
            } case 'string': {
                return this.read(byteNums)
            } case 'boolean': {
                return this.read(byteNums).charCodeAt(0)==0x00?false:true
            } default: {
                return null
            }
        }
    }
    private read(byteNums: number) {
        const ret = JSON.parse(JSON.stringify({value:this.data.slice(0, byteNums)}))
        this.data = this.data.slice(byteNums)
        return ret.value
    }
    readVarInt() {
        if(this.data.length==0)return 0
        let res:number = 0
        let shift:number = 0
        for(let i=0;i<5;i++){
            const tmp:string = this.readString(1)
            res|=(tmp.charCodeAt(0)&127)<<shift
            if(tmp.charCodeAt(0)>=0)return res
            shift+=7
        }
        return 0
    }
    writeVarInt(data: number) {
        let test = ''
        // eslint-disable-next-line no-constant-condition
        while (true) {
            if ((data & ~0x7F) == 0) {
                this.write8bitsData(data);
                test+=data
                for(let i=0;i<test.length;i++){
                    console.log(test.charCodeAt(i))
                }
                console.log('bbb')
                return;
            } else {
                this.write8bitsData((data & 0x7F) | 0x80);
                data >>>= 7;
                test+=(data & 0x7F) | 0x80
            }
        }

    }
    computeVarInt32Size(value:number) {
        let i;
        for(i=1;i<5;i++){
            if((value & (0xffffffff << 7*i)) == 0)return i
        }
        return i;
    }
    size() {
        return this.data.length * 8
    }
    length() {
        return this.data.length
    }
    buffer() {
        let array = new Uint8Array(this.data.length)
        for (let i = 0; i < this.data.length; i++) {
            // console.log(this.data.charCodeAt(i))
            array[i] = (this.data.charCodeAt(i))
        }
        return array.buffer
    }
    toString() {
        return this.data
    }
}