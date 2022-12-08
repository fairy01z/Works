function getNoteList (params) {
    return $axios({
        url: '/notes/page',
        method: 'get',
        params
    })
}


// 新增---添加笔记
function addNotes (params) {
    return $axios({
        url: '/notes',
        method: 'post',
        data: { ...params }
    })
}

// 修改---修改笔记
function editNotes (params) {
    return $axios({
        url: '/notes',
        method: 'put',
        data: { ...params }
    })
}

function  deleteNote(id) {
    return $axios({
        url: `/notes/delete/${id}`,
        method: 'post',
    })
}
