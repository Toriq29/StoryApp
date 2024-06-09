package com.dicoding.picodiploma.loginwithanimation.data.pref

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
) : Parcelable