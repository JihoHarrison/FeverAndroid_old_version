package com.example.matchcubeandroid.model
data class PlayerDetailModel (
    val statusCode: Int,
    val responseMessage: String,
    val data: PlayerDetail
    )
data class PlayerDetail(
    var nickName: String, // 선수 닉네임
    var image: String, // 이미지 URL
    var categoryName: String, // 종목 정보
    var height: Float, // 키
    var weigth: Float, // 몸무게
    var discloseWeight: String, // 몸무게 공개 여부
    var area: String, // 활동 지역 ...구
    var intro: String, // 선수 소개 글
    var position: String, // 해당 포지션 정보
    var car: Boolean, // 자차 소유 여부
    var athlete: Boolean, // 선출 여부
    var uniform: Boolean // 유니폼 여부
)