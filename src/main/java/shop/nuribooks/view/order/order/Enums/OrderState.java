package shop.nuribooks.view.order.order.Enums;

/**
 * 주문 상태 Enum
 *
 * <ul>
 *     <li><b>PENDING</b>: 주문 대기 상태 (주문이 생성된 상태, 결제 전)</li>
 *     <li><b>PAID</b>: 결제 완료 상태</li>
 *     <li><b>DELIVERING</b>: 배송 중 상태</li>
 *     <li><b>COMPLETED</b>: 주문 완료 상태 (배송 완료 후)</li>
 *     <li><b>RETURNED</b>: 반품 상태</li>
 *     <li><b>CANCELED</b>: 결제 취소 상태</li>
 * </ul>
 *
 * @author nuri
 */
public enum OrderState {
	PENDING(0, "주문 대기"),
	PAID(1, "결제 완료"), // 결제가 실패했을 때를 대비한 상태
	DELIVERING(2, "배송중"),
	COMPLETED(3, "주문 완료"),
	RETURNED(4, "반품"),
	CANCELED(5, "결제 취소");

	private final int code;
	private final String korName;

	/**
	 * 주문 상태 생성자
	 *
	 * @param code 주문 상세 코드
	 * @param korName 주문 상태의 한국어 이름
	 */
	OrderState(int code, String korName) {
		this.code = code;
		this.korName = korName;
	}

	/**
	 * 주문 상태의 코드 값을 반환
	 *
	 * @return 주문 상태 코드
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 주문 상태의 한국어 이름을 반환
	 *
	 * @return 주문 상태의 한국어 이름
	 */
	public String getKorName() {
		return korName;
	}

}
