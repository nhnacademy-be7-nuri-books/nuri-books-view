function updatePriceByAllCoupon(selectedItem) {
    const couponId = selectedItem.getAttribute('data-coupon-id');
    const couponName = selectedItem.getAttribute('data-coupon-name');
    const discount = parseFloat(selectedItem.getAttribute('data-coupon-discount')); // 할인액 또는 퍼센트
    const policy = selectedItem.getAttribute('data-coupon-policy'); // 할인 방식 (퍼센트 또는 금액)
    const dataCouponMaximumDiscount = selectedItem.getAttribute('data-coupon-maximum-discount')
    const originalPrice = getAllSalePrices(); // 원래 가격 (순수 책가격)

    let finalPrice = originalPrice; // 기본적으로 할인 전 가격

    console.log(policy, dataCouponMaximumDiscount);

    if (policy === '비율(%)') {
        if ((originalPrice * (discount / 100) >= dataCouponMaximumDiscount)) {
            finalPrice = originalPrice - dataCouponMaximumDiscount;
        } else {
            finalPrice = originalPrice - (originalPrice * (discount / 100));
        }
    } else {
        finalPrice = originalPrice - discount;
    }

    console.log("쿠폰 적용 금액: ", finalPrice);

    document.getElementById("apply-coupon-point").textContent = finalPrice;
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
