import {action, makeObservable, observable} from "mobx";


export default class UserStore {
    _isAuth = false
    _user = {};

    constructor() {
        makeObservable(this, {
            _isAuth: observable,
            _user: observable,
            setIsAuth: action,
            setUser: action
        });
    }

    setIsAuth(isAuth: boolean) {
        this._isAuth = isAuth;
    }

    setUser(user: any) {
        this._user = user;
    }

    getUser() {
        return this._user;
    }

    getIsAuth() {
        return this._isAuth;
    }

}