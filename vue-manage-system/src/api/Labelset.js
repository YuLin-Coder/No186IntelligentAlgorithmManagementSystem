import request from '../utils/request';

export const fetchAllLabelset = query => {
    return request({
        url: 'http://127.0.0.1:8080/findAllLabelset',
        method: 'get',
        params: query
    });
};

export const fetchAddLabelset = query => {
    return request({
        url: 'http://127.0.0.1:8080/addLabelset',
        method: 'post',
        params: query
    });
};

export const fetchDelLabelset = query => {
    return request({
        url: 'http://127.0.0.1:8080/delLabelset',
        method: 'post',
        params: query
    });
};

export const fetchUpdLabelset = form => {
    return request({
        url: 'http://127.0.0.1:8080/updLabelset',
        method: 'post',
        params: form
    });
};