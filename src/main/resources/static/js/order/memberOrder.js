main();

// 토스 payments 결제
async function main() {

    const totalCount = countBooks();

    const button = document.getElementById("payment-button");
    const coupon = document.getElementById("order-coupon-list");
    document.getElementById("total-items").textContent = totalCount;

    let totalPrice = calculateTotalPrice();
    const amount = {
        currency: "KRW",
        value: totalPrice,
    };

    // ------  결제위젯 초기화 ------
    // TODO: clientKey는 개발자센터의 결제위젯 연동 키 > 클라이언트 키로 바꾸세요.
    // TODO: 구매자의 고유 아이디를 불러와서 customerKey로 설정하세요. 이메일・전화번호와 같이 유추가 가능한 값은 안전하지 않습니다.
    // @docs https://docs.tosspayments.com/sdk/v2/js#토스페이먼츠-초기화
    const clientKey = "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm";
    const customerKey = generateRandomString();
    const tossPayments = TossPayments(clientKey);

    // 회원 결제
    // const widgets = tossPayments.widgets({
    //     customerKey,
    // });

    // 비회원 결제
    const widgets = tossPayments.widgets({customerKey: TossPayments.ANONYMOUS});

    // ------  주문서의 결제 금액 설정 ------
    // TODO: 위젯의 결제금액을 결제하려는 금액으로 초기화하세요.
    // TODO: renderPaymentMethods, renderAgreement, requestPayment 보다 반드시 선행되어야 합니다.
    await widgets.setAmount(amount);

    // ------  결제 UI 렌더링 ------
    // @docs https://docs.tosspayments.com/sdk/v2/js#widgetsrenderpaymentmethods
    await widgets.renderPaymentMethods({
        selector: "#payment-method",
        variantKey: "DEFAULT",
    });

    // ------  이용약관 UI 렌더링 ------
    // @docs https://docs.tosspayments.com/reference/widget-sdk#renderagreement선택자-옵션
    await widgets.renderAgreement({selector: "#agreement", variantKey: "AGREEMENT"});

    const selectedRadio = document.querySelector('input[name="deliveryDate"]:checked');
    let selectedDate = null;

    if (selectedRadio) {
        switch (selectedRadio.value) {
            case 'deliveryTomorrow':
                selectedDate = formatDate(1);
                break;
            case 'deliveryDayAfter':
                selectedDate = formatDate(2);
                break;
            case 'deliveryTwoDaysAfter':
                selectedDate = formatDate(3);
                break;
        }
    }

    let isPaymentInProgress = false;

    // ------ '결제하기' 버튼 누르면 결제창 띄우기 ------
    // @docs https://docs.tosspayments.com/sdk/v2/js#widgetsrequestpayment
    button.addEventListener("click", async function () {

        if (isPaymentInProgress) return;
        isPaymentInProgress = true;

        totalPrice = calculateTotalPrice();

        await widgets.setAmount({
            currency: "KRW",
            value: totalPrice,
        });

        // 검증
        let valid = true;

        // 에러 메시지 표시 영역
        const errorMessage = document.getElementById("errorMessage");
        const errorToastElement = document.getElementById("errorToast")
        const errorToast = new bootstrap.Toast(errorToastElement);
        errorToast.hide();

        //배송지 정보
        let shippingName = document.getElementById("shipping-name").value;
        let shippingPhoneNumber = document.getElementById("shipping-phone-number").value;
        let shippingAddress = document.getElementById("shipping-address").value;
        let shippingZipcode = document.getElementById("shipping-zipcode").value;


        // 주문자 이름 검증 (비어 있으면)
        if (!shippingName) {
            errorMessage.textContent = "주문자 이름을 입력하세요.";
            valid = false;
        }


        // 전화번호 검증
        const phonePattern = /^\d{10,13}$/; // 10~13자리 숫자
        if (!shippingPhoneNumber) {
            errorMessage.textContent = "전화번호를 입력하세요.";
            valid = false;
        } else if (!phonePattern.test(shippingPhoneNumber)) {
            errorMessage.textContent = "전화번호 형식이 올바르지 않습니다.";
            valid = false;
        }

        // 배송지 주소 검증 (비어 있으면)
        if (!shippingAddress) {
            errorMessage.textContent = "배송지를 입력하세요.";
            valid = false;
        }

        // 우편번호 검증 (비어 있으면)
        if (!shippingZipcode) {
            errorMessage.textContent = "우편번호를 입력하세요.";
            valid = false;
        } else if (shippingZipcode.length < 5) { // 예시로 우편번호 5자리 이상 검증
            errorMessage.textContent = "우편번호는 5자리 이상이어야 합니다.";
            valid = false;
        }

        // 유효하지 않으면 폼 제출 중지
        if (!valid) {
            errorToastElement.style.display = "block";
            errorToast.show();
            return;
        }


        // 주문 임시 저장 용 데이터
        // 1. 책 정보
        const bookList = processOrderDetails();
        // 2. 배송 정보
        const shippingRegister = {
            ShippingPolicyId: shippingId,
            recipientName: document.getElementById('shipping-name').value,
            recipientAddress: document.getElementById('shipping-address').value,
            recipientAddressDetail: document.getElementById('shipping-address-detail').value,
            recipientZipcode: document.getElementById('shipping-zipcode').value,
            recipientPhoneNumber: document.getElementById('shipping-phone-number').value,
            senderName: document.getElementById('customer-name').textContent,
            senderPhoneNumber: document.getElementById('customer-phone').textContent
        };

        // 임시 주문 정보
        let orderData = {
            paymentPrice: totalPrice,  // 총 결제 가격
            paymentBooks: getAllSalePrices(),  // 순수 도서 가격
            expectedDeliveryAt: selectedDate,
            orderDetails: bookList,  // 주문 상세
            shippingRegister: shippingRegister,  // 배송 정보
            customerRegister: null, // 회원 정보
            usingPoint: updateFinalPrice(),
            allAppliedCoupon: document.getElementById("usingAllCouponId").value,
            wrapping: document.getElementById("packagingId").value,   // 포장 비용 예시
            wrappingList: document.getElementById("selectedBooksHidden").value
        };

        console.log(orderData);
        let result;
        try {
            console.log(JSON.stringify(orderData));
            // 결제를 요청하기 전에 orderId, amount를 서버에 저장하세요.
            // 결제 과정에서 악의적으로 결제 금액이 바뀌는 것을 확인하는 용도입니다.
            const apiUrl = window.location.origin + "/orders/save";
            const response = await fetch(apiUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(orderData),
            });

            result = await response.json();

            if (response.ok) {
                await widgets.requestPayment({
                    orderId: "NB-M-ORDER-0000000" + result.orderId,
                    orderName: result.orderName,
                    successUrl: window.location.origin + "/payments/success",
                    failUrl: window.location.origin + "/payments/fail",
                    customerEmail: document.getElementById('customer-email').textContent,
                    customerName: document.getElementById('customer-name').textContent,
                    customerMobilePhone: document.getElementById('customer-phone').textContent,
                });
            } else {
                // 에러 메시지 출력
                console.error("에러 코드:", result.status);
                console.error("에러 메시지:", result.message);

                // 사용자에게 에러 메시지 표시
                throw new Error(`주문 정보 저장 실패: ${result.message}`);
            }
        } catch {
            alert(`주문 정보를 저장하는 데 실패했습니다. 사유: ${result.message}`);
        } finally {
            isPaymentInProgress = false;  // 결제 처리 완료
        }

    });
}

// 주문번호 랜덤
function generateRandomString() {
    return window.btoa(Math.random()).slice(0, 20);
}

// 결제 금액 얻어오기
function getAllSalePrices() {
    const salePriceElements = document.querySelectorAll('.book-total-price');
    const salePrices = Array.from(salePriceElements).map(element => {
        return parseInt(element.textContent.replace(/[^\d]/g, ''));
    })

    return salePrices.reduce((total, price) => total + price, 0);
}

// 총 결제 금액을 계산하고 input 필드에 값을 넣기
function calculateTotalPrice() {
    const shippingPrice = document.getElementById('shipping-cost').textContent // 배송비
    const wrappingPrice = document.getElementById('wrapping-amount').textContent // 포장비
    const usePoint = updateFinalPrice(); // 사용 포인트
    let bookListPrice = getAllSalePrices(); // 도서 가격
    let couponApplyPrice = document.getElementById("apply-coupon-point").innerText // 쿠폰 적용 금액
    let totalPrice;

    if (couponApplyPrice === "0") {
        totalPrice = bookListPrice + parseInt(shippingPrice, 10) + parseInt(wrappingPrice, 10) - usePoint; // 총 결제 금액 계산
    } else {
        totalPrice = parseInt(couponApplyPrice, 10) + parseInt(shippingPrice, 10) + parseInt(wrappingPrice, 10) - usePoint; // 총 결제 금액 계산
    }

    // 총 결제 금액을 input 필드에 넣기
    document.getElementById('book-list-amount').textContent = bookListPrice;
    document.getElementById('total-amount').textContent = totalPrice;

    console.log("사용 포인트: %d", usePoint);
    console.log("포장비: " + wrappingPrice);
    console.log("배송비: " + shippingPrice);
    console.log("총 결제 가격: " + totalPrice);
    return totalPrice;  // 포맷된 가격 반환
}

// 주문한 책 총 갯수
function countBooks() {
    let totalCount = 0;

    const itemQuantities = document.querySelectorAll('.item-quantity');

    itemQuantities.forEach(span => {
        const quantity1 = parseInt(span.textContent, 10); // 수량 가져오기
        if (isNaN(quantity1)) {
            console.error("Invalid quantity: ", span.textContent || span.value);
            return;  // 수량이 유효하지 않으면 건너뜁니다.
        }
        totalCount += quantity1; // 총 품목 수에 더하기
    });

    return totalCount;

}

// 포인트 사용
function updateFinalPrice() {
    const finalPointLabel = document.getElementById('use-point');
    const usedPointsField = document.getElementById('usedPoints');
    const availablePoints = parseInt(document.getElementById('availablePoints').textContent, 10);

    // "전액 사용" 버튼 클릭 시 사용 가능한 포인트 값을 입력
    document.getElementById('useAllPointsBtn').addEventListener('click', function () {
        usedPointsField.value = availablePoints; // 잔여 포인트를 입력 필드에 자동으로 입력
        finalPointLabel.textContent = availablePoints.toString();
        updateFinalPrice(); // 결제 금액 업데이트
        calculateTotalPrice();
    });

    // 사용을 클릭하면 잔여 포인트를 초과하지 않도록 제한
    document.getElementById('applyPointsBtn').addEventListener('click', function () {
        let value = usedPointsField.value;
        value = value.replace(/[^0-9]/g, '');
        usedPointsField.value = value;

        let usedPoints = parseInt(usedPointsField.value, 10) || 0;

        // 음수 값 입력 방지
        if (usedPoints < 0) {
            usedPointsField.value = 0;
            usedPoints = 0;
        }

        // 잔여 포인트를 초과하는 값 입력 방지
        if (usedPoints > availablePoints) {
            usedPointsField.value = availablePoints;
            usedPoints = availablePoints;
        }

        finalPointLabel.textContent = usedPoints.toString();
        // 최종 결제 금액 반영
        calculateTotalPrice();
    });

    // 최종 결제 금액 업데이트
    return parseInt(finalPointLabel.textContent, 10);

}

// 도서 목록
function processOrderDetails() {
    // 새로운 리스트 만들기 예시: 총합 가격 계산
    const list = orderDetails.map(function (book) {
        return {
            bookId: book.bookId,
            count: book.quantity,
            unitPrice: book.salePrice,
            isWrapped: book.isPackageable,
            BookAppliedCouponRequestStub: null
        };
    });
    console.log(list);
    return list;
}

// 날짜 계산 함수
function formatDate(offset) {
    const today = new Date();
    today.setDate(today.getDate() + offset);

    const year = today.getFullYear();
    const month = today.getMonth() + 1;  // 0부터 시작하므로 +1
    const day = today.getDate();

    // 날짜 형식을 YYYY-MM-DD로 설정
    return `${year}-${month < 10 ? '0' + month : month}-${day < 10 ? '0' + day : day}`;
}

// 날짜 업데이트
document.getElementById('dateTomorrow').innerText = formatDate(1);
document.getElementById('dateDayAfter').innerText = formatDate(2);
document.getElementById('dateTwoDaysAfter').innerText = formatDate(3);