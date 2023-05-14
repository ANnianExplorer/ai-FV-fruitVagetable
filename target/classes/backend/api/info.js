// 查询列表接口
const getInfoPage = (params) => {
    return $axios({
        url: '/info/page',
        method: 'get',
        params
    })
}


// 编辑页面反查详情接口
const queryInfoById = (id) => {
    return $axios({
        url: `/info/${id}`,
        method: 'get'
    })
}

// 删除当前列的接口
const deleInfo = (id) => {
    return $axios({
        url: '/info',
        method: 'delete',
        params: { id }
    })
}

// 新增接口
const addInfo = (params) => {
    return $axios({
        url: '/info',
        method: 'post',
        data: { ...params }
    })
}