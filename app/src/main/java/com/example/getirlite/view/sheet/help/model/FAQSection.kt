package com.example.getirlite.view.sheet.help.model

import com.example.getirlite.model.extension.string
import com.example.getirlite.model.extension.stringRes
import com.example.getirlite.model.extension.title

enum class FAQSection {
    order, payment, offer, delivery, membership;

    val title: String get() = "FAQSection${name.title}".stringRes.string

    val items: List<FAQItem>
        get() = when (this) {
            order -> listOf(FAQItem.orderTime, FAQItem.orderCancel)
            payment -> listOf(FAQItem.paymentFail, FAQItem.paymentReturn)
            offer -> listOf(FAQItem.offerCoupon, FAQItem.offerCode)
            delivery -> listOf(FAQItem.deliveryTime)
            membership -> listOf(FAQItem.forgetPassword, FAQItem.updateAccountInfo)
        }
}