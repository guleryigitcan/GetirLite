package com.example.getirlite.view.sheet.help.model

import com.example.getirlite.model.extension.string
import com.example.getirlite.model.extension.stringRes
import com.example.getirlite.model.extension.title

enum class FAQItem {
    orderTime, orderCancel,
    paymentFail, paymentReturn,
    offerCoupon, offerCode,
    deliveryTime,
    forgetPassword, updateAccountInfo;

    val question: String get() = "FAQItemQuestion${name.title}".stringRes.string
    val answer: String get() = "FAQItemAnswer${name.title}".stringRes.string
}