function InfoPagingApi(data){
    return $axios({
        'url': `/info/list`,
        'method': 'get',
        params:{...data}
    })
}