export type Parameter = {
    roomName: string; // 房间名
    limit: number;    // 房间人数上限
    anonymityFlag: boolean // 匿名用户准入flag
}
export type callback = {
    uid: string;
    roomId: string;
    callBackName: string;
    type: boolean
}