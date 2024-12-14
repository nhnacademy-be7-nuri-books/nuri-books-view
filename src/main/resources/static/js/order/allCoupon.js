function updatePriceByAllCoupon(selectedItem) {
    const couponId = selectedItem.getAttribute('data-coupon-id');
    const couponName = selectedItem.getAttribute('data-coupon-name');
    const discount = parseFloat(selectedItem.getAttribute('data-coupon-discount'));
    const discountPrice = selectedItem.getAttribute('data-coupon-discount-price');
    const policy = selectedItem.getAttribute('data-coupon-policy');
    const dataCouponMaximumDiscount = selectedItem.getAttribute('data-coupon-maximum-discount')
    const originalPrice = getAllSalePrices(); // 원래 가격 (순수 책가격)

    let finalPrice = originalPrice - discountPrice;

    console.log("쿠폰 적용 금액: ", finalPrice);

    document.getElementById("apply-coupon-point").textContent = finalPrice.toString();
    document.getElementById("couponDropdownMenuButton").textContent = couponName;
    document.getElementById("usingAllCouponId").value = couponId;

    calculateTotalPrice();
}

function handleNoCouponSelection() {

    document.getElementById("apply-coupon-point").textContent = "0";
    document.getElementById("couponDropdownMenuButton").textContent = "적용 하지 않음";
    document.getElementById("usingAllCouponId").value = null;

    calculateTotalPrice();
}
