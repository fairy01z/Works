

//根据id获取笔记内容
function getNote(id) {
    return $axios({
        url: `/notes/${id}`,
        'method': 'get',
    })
}
//新增笔记
function addNote(params) {
    return $axios({
        'url': '/notes/addNote',
        'method': 'put',
        data: { ...params }
    })
}

//修改笔记 id指的是笔记id
function editNote(params,id) {
    return $axios({
        url: `/notes/edit/${id}`,
        method: 'post',
        data: { ...params }
    })
}
