export interface Profile {
    username: string;
    email: string;
    password: string;
    avatar: string;
    nickname: string;
    sex: string;
    resume: string;
}

export type linkLoginForm = {
    email:string;
    picId:string;
    picCode:string;
}
export type linkVerifyForm = {
    linkToken:string;
}