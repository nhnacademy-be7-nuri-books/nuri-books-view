main();

// 토스 payments 결제
async function main() {

    // 가격 계산을 위한 데이티
    const totalCount = countBooks();
    let totalPrice = calculateTotalPrice();

    const button = document.getElementById("payment-button");
    const coupon = document.getElementById("order-coupon-list");
    document.getElementById("total-items").textContent = totalCount;

    const amount = {
        currency: "KRW",
        value: totalPrice,
    };

    // ------  결제위젯 초기화 ------
    // clientKey는 개발자센터의 결제위젯 연동 키 > 클라이언트 키로 바꾸세dy. (사업자 등록 필요)
    // @docs https://docs.tosspayments.com/sdk/v2/js#토스페이먼츠-초기화
    const clientKey = "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm";
    //const customerKey = generateRandomString();
    const tossPayments = TossPayments(clientKey);

    // 회원 결제
    // const widgets = tossPayments.widgets({
    //     customerKey,
    // });

    // 비회원 결제
    const widgets = tossPayments.widgets({customerKey: TossPayments.ANONYMOUS});

    // ------  주문서의 결제 금액 설정 ------
    // renderPaymentMethods, renderAgreement, requestPayment 보다 반드시 선행되어야 합니다.
    await widgets.setAmount(amount);

    // ------  결제 UI 렌더링 ------
    // @docs https://docs.tosspayments.com/sdk/v2/js#widgetsrenderpaymentmethods
    await widgets.renderPaymentMethods({
        selector: "#payment-method",
        // 렌더링하고 싶은 결제 UI의 variantKey
        // 결제 수단 및 스타일이 다른 멀티 UI를 직접 만들고 싶다면 계약이 필요해요.
        // @docs https://docs.tosspayments.com/guides/v2/payment-widget/admin#새로운-결제-ui-추가하기
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

        // 주문자 정보
        let name = document.getElementById("customer-name").value;
        let phoneNumber = document.getElementById("customer-phone").value;
        let email = document.getElementById("customer-email").value;
        let password = document.getElementById("customer-password").value;
        let repeatPassword = document.getElementById("customer-repeat-password").value;

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
        if (!name || !shippingName) {
            errorMessage.textContent = "주문자 이름을 입력하세요.";
            valid = false;
        }

        // 주문자 비밀번호 검증 (비어 있으면)
        if (!password) {
            errorMessage.textContent = "비밀번호를 입력하세요.";
            valid = false;
        } else if (password.length < 6) {
            errorMessage.textContent = "비밀번호는 6자 이상이어야 합니다.";
            valid = false;
        }

        // 비밀번호 확인 검증 (비어 있으면, 일치하지 않으면)
        if (!repeatPassword) {
            errorMessage.textContent = "비밀번호를 한 번 더 입력하세요.";
            valid = false;
        } else if (password !== repeatPassword) {
            errorMessage.textContent = "비밀번호가 일치하지 않습니다.";
            valid = false;
        }

        // 전화번호 검증
        const phonePattern = /^\d{10,13}$/; // 10~13자리 숫자
        if (!phoneNumber || !shippingPhoneNumber) {
            errorMessage.textContent = "전화번호를 입력하세요.";
            valid = false;
        } else if (!phonePattern.test(phoneNumber)) {
            errorMessage.textContent = "전화번호 형식이 올바르지 않습니다.";
            valid = false;
        } else if (!phonePattern.test(shippingPhoneNumber)) {
            errorMessage.textContent = "전화번호 형식이 올바르지 않습니다.";
            valid = false;
        }

        // 이메일 검증
        const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        if (!email) {
            errorMessage.textContent = "이메일을 입력하세요.";
            valid = false;
        } else if (!emailPattern.test(email)) {
            errorMessage.textContent = "이메일 형식이 올바르지 않습니다.";
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
            isPaymentInProgress = false;
            return;
        }

        // 결제를 요청하기 전에 orderId, amount를 서버에 저장하세요.
        // 결제 과정에서 악의적으로 결제 금액이 바뀌는 것을 확인하는 용도입니다.
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
            senderName: document.getElementById('customer-name').value,
            senderPhoneNumber: document.getElementById('customer-phone').value
        };
        // 3. 회원 정보
        const customerRegister = {
            name: document.getElementById('customer-name').value,
            password: document.getElementById('customer-password').value,
            email: document.getElementById('customer-email').value,
            phoneNumber: document.getElementById('customer-phone').value
        }

        // 임시 주문 정보
        const orderData = {
            paymentPrice: totalPrice,  // 총 결제 가격
            paymentBooks: getAllSalePrices(),  // 순수 도서 가격
            expectedDeliveryAt: selectedDate,
            orderDetails: bookList,  // 주문 상세
            shippingRegister: shippingRegister,  // 배송 정보
            customerRegister: customerRegister, // 회원 정보
            usingPoint: 0,
            allAppliedCoupon: null,
            wrapping: document.getElementById('packagingId').value, // 포장 ID
            wrappingList: document.getElementById("selectedBooksHidden").value
        };

        console.log(JSON.stringify(orderData));

        let result;

        try {

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
                    orderId: "NB-C-ORDER-0000000" + +result.orderId,
                    orderName: result.orderName,
                    successUrl: window.location.origin + "/payments/success",
                    failUrl: window.location.origin + "/payments/fail",
                    customerEmail: document.getElementById('customer-email').value,
                    customerName: document.getElementById('customer-name').value,
                    customerMobilePhone: document.getElementById('customer-phone').value,
                });
            } else {
                // 에러 메시지 출력
                console.error("에러 코드:", result.status);
                console.error("에러 메시지:", result.message);

                // 사용자에게 에러 메시지 표시
                alert(`주문 정보를 저장하는 데 실패했습니다. 사유: ${result.message}`);
                throw new Error(`주문 정보 저장 실패: ${result.message}`);
            }
        } catch {
            alert(`주문 정보를 저장하는 데 실패했습니다. 사유: ${result.message}`);
        } finally {
            isPaymentInProgress = false;  // 결제 처리 완료
        }

    });
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
    const bookListPrice = getAllSalePrices();  // 책값
    const shippingPrice = document.getElementById('shipping-cost').textContent // 배송비
    const wrappingPrice = document.getElementById('wrapping-amount').textContent // 포장비
    let totalPrice = bookListPrice + parseInt(shippingPrice, 10) + parseInt(wrappingPrice, 10); // 총 결제 금액 계산

    // 총 결제 금액을 input 필드에 넣기
    document.getElementById('book-list-amount').textContent = bookListPrice;
    document.getElementById('total-amount').textContent = totalPrice; // 최종값이라 쿠폰, 배송비도 지원할 것

    console.log("포장비: " + wrappingPrice);
    console.log("배송비: " + shippingPrice);
    console.log("총 결제 가격: " + totalPrice);
    return totalPrice;
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

// 도서 목록
function processOrderDetails() {
    // 새로운 리스트 만들기 예시: 총합 가격 계산
    return orderDetails.map(function (book) {
        return {
            bookId: book.bookId,
            count: book.quantity,
            unitPrice: book.salePrice,
            isWrapped: book.isPackageable,
            BookAppliedCouponRequestStub: null
        };
    });
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