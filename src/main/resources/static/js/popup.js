function openCategoryPopup() {
    const popupUrl = '/admin/category/popup';
    const popupOptions = 'width=600,height=400,top=100,left=100,resizable=yes,scrollbars=yes';

    // 팝업 창 열기
    window.open(popupUrl, 'categoryPopup', popupOptions);
}

function openCouponPolicyPopup() {
    const popupUrl = '/admin/coupon-policy/popup';
    const popupOptions = 'width=600,height=400,top=100,left=100,resizable=yes,scrollbars=yes';

    // 팝업 창 열기
    window.open(popupUrl, 'couponPolicyPopup', popupOptions);
}