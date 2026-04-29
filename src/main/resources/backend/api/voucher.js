/**
 * 优惠券相关API
 */

// 查询列表接口
const getVoucherPage = (params) => {
    return $axios({
        url: '/voucher/page',
        method: 'get',
        params
    })
}
// 删除接口
const deleteVoucher = (ids) => {
    return $axios({
        url: '/voucher',
        method: 'delete',
        params: { ids }
    })
}
// 新增接口
const addVoucher = (params) => {
    return $axios({
        url: '/voucher',
        method: 'post',
        data: { ...params }
    })
}
// 查询详情
const queryVoucherById = (id) => {
    return $axios({
        url: `/voucher/${id}`,
        method: 'get'
    })
}
// 查菜品列表的接口
const queryVoucherList = (params) => {
    return $axios({
        url: '/voucher/list',
        method: 'get',
        params
    })
}

// 文件down预览
const commonDownload = (params) => {
    return $axios({
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        url: '/common/download',
        method: 'get',
        params
    })
}

// 起用停用---批量起用停用接口
const voucherStatusByStatus = (params) => {
    return $axios({
        url: `/voucher/status/${params.status}`,
        method: 'post',
        params: { ids: params.id }
    })
}

// 发放新人优惠券给指定用户
const issueNewUserCoupon = (params) => {
    return $axios({
        url: '/api/user/voucher/issue/new-user',
        method: 'post',
        data: { ...params }
    })
}

// 批量发放新人优惠券
const batchIssueNewUserCoupons = (params) => {
    return $axios({
        url: '/api/user/voucher/issue/batch',
        method: 'post',
        data: { ...params }
    })
}

// 定义为全局函数
window.getVoucherPage = getVoucherPage;
window.deleteVoucher = deleteVoucher;
window.addVoucher = addVoucher;
window.queryVoucherById = queryVoucherById;
window.queryVoucherList = queryVoucherList;
window.commonDownload = commonDownload;
window.voucherStatusByStatus = voucherStatusByStatus;
window.issueNewUserCoupon = issueNewUserCoupon;
window.batchIssueNewUserCoupons = batchIssueNewUserCoupons;