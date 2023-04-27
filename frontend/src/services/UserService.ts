import axios from 'axios';
import {IAuthenticationRequest} from "../types/IAuthenticationRequest";
import {IUserDetails} from "../types/IUserDetails";
import {IRegisterRequest} from "../types/IRegisterRequest";
import {sha256} from "js-sha256";
export default class UserService {
    private baseUrl: string;
    private privateKey: string;

    constructor() {
        this.baseUrl = "http://".concat(process.env.REACT_APP_SERVER_IP as string).concat(":8081/auth/");
        this.privateKey = "2ed06766795d58a4f22d511a672f20a6b096d3fe5b56af3a744678a9a356fd82";
    }

    authenticateUser(authRequest: IAuthenticationRequest): Promise<IUserDetails> {
        authRequest.password = sha256.hex(this.privateKey.concat(authRequest.password));
        return axios.post(this.baseUrl.concat('authenticate'), authRequest).then(res => res.data);
    }

    registerUser(registerRequest: IRegisterRequest): Promise<IRegisterRequest> {
        registerRequest.password = sha256.hex(this.privateKey.concat(registerRequest.password));
        return axios.post(this.baseUrl.concat('register'), registerRequest).then(res => res.data);
    }

}
