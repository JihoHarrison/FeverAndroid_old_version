package com.example.matchcubeandroid.social

enum class SocialType {
    GOOGLE, KAKAO, NAVER
}

fun setSocialType(socialType: SocialType): String{
    var mdiaCode: String
    when(socialType){
        SocialType.GOOGLE -> mdiaCode = "G"
        SocialType.KAKAO -> mdiaCode = "K"
        SocialType.NAVER -> mdiaCode = "N"
    }

    return mdiaCode
}