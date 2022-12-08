// 修改---启用禁用接口
function enableOrDisableEmployee (params) {
    return $axios({
        url: '/employee',
        method: 'put',
        data: { ...params }
    })
}
// 修改页面反查详情接口
function queryEmployeeById (id) {
    return $axios({
        url: `/employee/${id}`,
        method: 'get'
    })
}

// 修改个人信息
function editEmployee (params,id) {
    return $axios({
        url: `/employee/second/${id}`,
        method: 'put',
        data: { ...params }
    })
}