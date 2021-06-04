import axios from "axios";
import instance from "@/api/instance";

/* 시스템 - 업무구분 콤보박스 */
export const getCtmmnySys = {
    getRes() {
        return instance.get("/api/cust/sys");
    },
};

export const getchttrooms = {
    getRes() {
        return instance.get("/api/cust/chttrooms");
    },
};
export const createChttrooms = {
    getRes(requestBody) {
        const postInstance = axios.create({
            baseURL: "http://13.125.164.214:9090",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
                "Content-Type": "application/json",
            },
        });
        return postInstance.post("/api/cust/chttrooms", requestBody);
    },
};

export const updateChttroomsById = {
    getRes(requestBody, id) {
        const postInstance = axios.create({
            baseURL: "http://13.125.164.214:9090",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
                "Content-Type": "application/json",
            },
        });
        return postInstance.post(`/api/cust/chttrooms/${id}`, requestBody);
    },
};

/* sr리스트 가져오기 */
export const getSrDatas = {
    getRes(sysOption) {
        return instance.get("/api/cust/srs/mainSrCounts", {
            params: {
                name: sysOption,
            },
        });
    },
};

export const getOperSrDatas = {
    getRes(req) {
        return instance.get("/api/oper/srs/SrCounts", {
            params: req,
        });
    },
};

export const getTreatCounts = {
    getRes(req) {
        return instance.get("/api/oper/srs/SrTreatTimes", {
            params: req,
        });
    },
};

export const getOperInCompleteUserDatas = {
    getRes() {
        return instance.get("/api/oper/srs/mainIncompleteUserSrList");
    },
};

export const getOperProceedingSrDatas = {
    getRes() {
        return instance.get("/api/oper/srs/mainProceedingSrList");
    },
};

/* 요청한 sr이 미완료인 userList */
export const getInCompleteUserDatas = {
    getRes() {
        return instance.get("/api/cust/srs/mainIncompleteUserSrList");
    },
};

/* 요청한 sr이 미완료인 고객사 List */
export const getInCompleteCtmmnyDatas = {
    getRes() {
        return instance.get("/api/cust/srs/mainIncompleteCtmmnySrList");
    },
};

/* 등록되어 있는 모든 시스템 종류 가져오기 */
export const getCtmmnyUsingSystems = {
    getRes() {
        return instance.get("/api/cust/srs/mainComboBox");
    },
};

/* 고객사 랭크를 위한 data 반환 */
export const getCtmmnyRankDatas = {
    getRes(sysOption) {
        return instance.get("/api/oper/sr/rank", {
            params: {
                name: sysOption,
            },
        });
    },
};

export const saveSr = {
    getRes(requstBody) {
        const postInstance = axios.create({
            baseURL: "http://13.125.164.214:9090",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
                "Content-Type": "application/json",
            },
        });
        return postInstance.post("/api/cust/srs/reg", requstBody);
    },
};

export const saveCtmmny = {
    getRes(requstBody) {
        const postInstance = axios.create({
            baseURL: "http://13.125.164.214:9090",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
                "Content-Type": "application/json",
            },
        });
        return postInstance.post("/api/admin/ctmmny/registing", requstBody);
    },
};

export const saveUser = {
    getRes(requstBody) {
        const postInstance = axios.create({
            baseURL: "http://13.125.164.214:9090",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
                "Content-Type": "application/json",
            },
        });
        console.log(requstBody);
        return postInstance.post("/api/admin/user/registing", requstBody);
    },
};

export const findSrListWithCondition = {
    getRes(data) {
        return instance.get("/api/cust/srs/condition", {
            params: data,
        });
    },
};
export const findChatListWithCondition = {
    getRes(data) {
        return instance.get("/api/cust/chttrooms/condition", {
            params: data,
        });
    },
};

export const getCtmmnyComboBox = {
    getRes() {
        return instance.get("/api/oper/ctmmny");
    },
};

export const getSrStatusList = {
    getRes(req) {
        return instance.get("/api/oper/srs/status/ctmmnyNm", {
            params: req,
        });
    },
};

export const getTreatStatusList = {
    getRes(req) {
        return instance.get("/api/oper/treat/status/ctmmnyNm", {
            params: req,
        });
    },
};

export const getCtmmnyInfoList = {
    getRes(req) {
        return instance.get("/api/admin/ctmmny/list", {
            params: req,
        });
    },
};

export const getUserInfo = {
    getRes() {
        return instance.get("/api/user/");
    },
};

export const saveRcpt = {
    getRes(json, srId) {
        const postInstance = axios.create({
            baseURL: "http://13.125.164.214:9090",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
                "Content-Type": "application/json",
            },
        });
        return postInstance.post(`/api/oper/srs/${srId}`, json);
    },
};

export const login = {
    getRes(json) {
        const postInstance = axios.create({
            baseURL: "http://13.125.164.214:9090",
            headers: {
                "Content-Type": "application/json",
            },
        });
        return postInstance.post("/api/authenticate", json);
    },
};

export const getSrInfo = {
    getRes(srId) {
        return instance.get(`/api/cust/srs/${srId}`);
    },
};

export const getCtmmnyInfo = {
    getRes(ctmmnyId) {
        return instance.get(`/api/admin/ctmmnys/${ctmmnyId}`);
    },
};

export const getUserInfoList = {
    getRes(req) {
        return instance.get("/api/admin/users/list", {
            params: req,
        });
    },
};

export const getUsersInfo = {
    getRes(userId) {
        return instance.get(`/api/admin/users/${userId}`);
    },
};

export const getUserRoles = {
    getRes() {
        return instance.get(`/api/roles`);
    },
};

export const getCtmmnyCdAndNm = {
    getRes() {
        return instance.get(`/api/admin/ctmmnys`);
    },
};
export const updateCtmmnyInfo = {
    getRes(data) {
        const postInstance = axios.create({
            baseURL: "http://13.125.164.214:9090",
            headers: {
                "Content-Type": "application/json",
            },
        });
        return postInstance.post("/api/admin/ctmmnys", data);
    },
};

export const updateUserInfo = {
    getRes(data) {
        const postInstance = axios.create({
            baseURL: "http://13.125.164.214:9090",
            headers: {
                "Content-Type": "application/json",
            },
        });
        return postInstance.post("/api/admin/users", data);
    },
};
