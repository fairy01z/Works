// 修改---启用禁用接口
function enableOrDisableEmployee (params) {
    return $axios({
        url: '/employee',
        method: 'put',
        data: { ...params }
    })
}

//获取个人信息
function queryEmployeeById(id) {
    return $axios({
        url: `/employee/${id}`,
        method: 'get',
    })
}

//获取笔记列表


