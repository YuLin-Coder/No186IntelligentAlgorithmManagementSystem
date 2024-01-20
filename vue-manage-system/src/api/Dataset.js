import request from '../utils/request';

export const fetchAllDataset = query => {
    return request({
        url: 'http://127.0.0.1:8080/findAllDataset',
        method: 'get',
        params: query
    });
};

export const fetchAddDataset = query => {
    return request({
        url: 'http://127.0.0.1:8080/addDataset',
        method: 'post',
        params: query
    });
};

export const fetchDelDataset = query => {
    return request({
        url: 'http://127.0.0.1:8080/delDataset',
        method: 'post',
        params: query
    });
};

export const fetchUpdDataset = form => {
    return request({
        url: 'http://127.0.0.1:8080/updDataset',
        method: 'post',
        params: form
    });
};