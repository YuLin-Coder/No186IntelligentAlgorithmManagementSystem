import request from '../utils/request';

export const fetchData = query => {
    return request({
        url: './table.json',
        method: 'get',
        params: query
    });
};

export const fetchAlgData = query => {
    return request({
        url: './algorithms.json',
        method: 'get',
        params: query
    });
};

export const fetchAllAlg = query => {
    return request({
        url: 'http://127.0.0.1:8080/findAllAlg',
        method: 'get',
        params: query
    });
};

export const fetchAddAlg = query => {
    return request({
        url: 'http://127.0.0.1:8080/addalg',
        method: 'post',
        params: query
    });
};

export const fetchDelAlg = query => {
    return request({
        url: 'http://127.0.0.1:8080/delalg',
        method: 'post',
        params: query
    });
};

export const fetchUpdAlg = form => {
    return request({
        url: 'http://127.0.0.1:8080/updalg',
        method: 'post',
        params: form
    });
};


export const fetchAllMod = query => {
    return request({
        url: 'http://127.0.0.1:8080/findAllMod',
        method: 'get',
        params: query
    });
};

export const fetchAddMod = query => {
    return request({
        url: 'http://127.0.0.1:8080/addMod',
        method: 'post',
        params: query
    });
};

export const fetchDelMod = query => {
    return request({
        url: 'http://127.0.0.1:8080/delMod',
        method: 'post',
        params: query
    });
};

export const fetchUpdMod = form => {
    return request({
        url: 'http://127.0.0.1:8080/updMod',
        method: 'post',
        params: form
    });
};


export const ReleaseMod = query => {
    return request({
        url: 'http://127.0.0.1:8080/relMod',
        method: 'post',
        params: query
    });
};
export const fetchLoadSSD = query => {
    return request({
        url: 'http://127.0.0.1:8080/load_modelBySSD',
        method: 'post',
        params: query
    });
};
export const fetchLoadYolo = query => {
    return request({
        url: 'http://127.0.0.1:8080/load_modelByYolo',
        method: 'post',
        params: query
    });
};
export const fetchPredictSSD = query => {
    return request({
        url: 'http://127.0.0.1:8080/detectionBySSD',
        method: 'post',
        params: query
    });
};
export const fetchPredictYolo = query => {
    return request({
        url: 'http://127.0.0.1:8080/predictByYolo',
        method: 'post',
        params: query
    });
};
export const fetchTrianSSD = form => {
    return request({
        url: 'http://127.0.0.1:8080/trainBySSD',
        method: 'post',
        params: form
    });
};
export const fetchTrianYolo = form => {
    return request({
        url: 'http://127.0.0.1:8080/trainByYolo',
        method: 'post',
        params: form
    });
};